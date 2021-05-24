package dev.blasio99.webshop.server.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.blasio99.webshop.server.enums.Payment;
import dev.blasio99.webshop.server.enums.Status;
import dev.blasio99.webshop.server.exception.IncorrectInputValueException;
import dev.blasio99.webshop.server.exception.OrderException;
import dev.blasio99.webshop.server.exception.OrderLineException;
import dev.blasio99.webshop.server.model.Orders;
import dev.blasio99.webshop.server.model.Product;
import dev.blasio99.webshop.server.model.OrderLine;
import dev.blasio99.webshop.server.model.User;
import dev.blasio99.webshop.server.observer.Observable;
import dev.blasio99.webshop.server.observer.Observer;
import dev.blasio99.webshop.server.repo.OrderLineRepository;
import dev.blasio99.webshop.server.repo.OrderRepository;
import dev.blasio99.webshop.server.repo.ProductRepository;
import dev.blasio99.webshop.server.repo.UserRepository;

@Service
public class OrderLineService implements Observable {
	
	@Autowired
	private OrderLineRepository orderLineRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductService productService;

	private List<Observer> observers = new ArrayList<>();
	private Observer emailObserver = new EmailService();

	public OrderLine addOrderLine (Orders order, String username) throws OrderException{
		OrderLine orderLine = new OrderLine();

		orderService.addOrder(order, order.getQuantity(), username);
		LocalDateTime date = LocalDateTime.now();
		orderLine.setOrderDate(date);
		
		User user = userRepository.findByUsername(username);

		ArrayList<Long> orderIdList = new ArrayList<>();
		orderIdList.add(order.getId());
		
		if(orderIdList.isEmpty()) throw new OrderException();

		orderLine.setOrderIdList(orderIdList);	
		orderLine.setUsername(user.getUsername());
		
		return orderLineRepository.save(orderLine);
	}

	public Orders checkIfProductIsInOrderLine(ArrayList<Long> orderIdList, Long productId){
		ArrayList<Orders> orders = new ArrayList<>();

		for(Long orderId : orderIdList)
			orders.add(orderRepository.findById(orderId).get());

		if(orders.isEmpty()) return null;

		for(Orders order : orders)
			if(order.getProductId() == productId) return order;

		return null;


	}

	public OrderLine setStatus (Long orderLineId, String status) throws IncorrectInputValueException, OrderLineException{
		if(!contains(status))
			throw new IncorrectInputValueException("Invalid PAYMENT input value");

		OrderLine orderLine = orderLineRepository.findById(orderLineId).get();
		if(orderLine == null)
			throw new OrderLineException("OrderLine does not exist!");

		if(orderLine.getStatus() != null){
			orderLine.setStatus(Status.valueOf(status));
			return orderLineRepository.save(orderLine);
		}
		else throw new OrderLineException("This order does not exist, or it is not placed yet");

	}

	public OrderLine addOrderToOrderLine(Orders order, String username){
		
		OrderLine orderLine = orderLineRepository.findByUsername(username);

		// if orderLine does not exist, redirect to the creation of it
		if(orderLine == null) return addOrderLine(order, username);

		
		ArrayList<Long> newOrderIdList = orderLine.getOrderIdList();

		Orders checkOrder = checkIfProductIsInOrderLine(newOrderIdList, order.getProductId());
		
		if(checkOrder != null){
			orderService.addOrder(checkOrder, order.getQuantity(), username);
			return orderLine;
		}

		orderService.addOrder(order, order.getQuantity(), username);

		newOrderIdList.add(order.getId());
		orderLine.setOrderIdList(newOrderIdList);

		return orderLineRepository.save(orderLine);
	}
	
	public ArrayList<OrderLine> getOrderLines(){
		return (ArrayList<OrderLine>) orderLineRepository.findAll();
	}

	public ArrayList<Product> getProductsFromOrderLine(OrderLine orderLine){
		ArrayList<Long> orderIdList = orderLine.getOrderIdList();
		ArrayList<Product> products = new ArrayList<>();

		for(Long orderId : orderIdList){
			Long productId = orderRepository.findById(orderId).get().getProductId();
			products.add(productRepository.findById(productId).get());
		}
		
		return products;
	}

	public Double getTotalPrice(ArrayList<Product> products){
		Double totalPrice = 0.0;

		for(Product product : products)
			totalPrice += product.getPrice() * orderRepository.findByProductId(product.getProductId()).getQuantity();
		
		return totalPrice;
	}

	public OrderLine getOrderLineByUsername(String username){
		return orderLineRepository.findByUsername(username);
	}

	public OrderLine finishOrder(String payment, String username, String email) throws IncorrectInputValueException{
		if(!contains(payment))
			throw new IncorrectInputValueException("Invalid PAYMENT input value");

		
		OrderLine orderLine = orderLineRepository.findByUsername(username);
		
		if(orderLine == null)
			throw new OrderLineException("There is no orderLine for you (yet)!");

		ArrayList<Product> products = getProductsFromOrderLine(orderLine);
		ArrayList<Integer> oldQuantities = new ArrayList<>();
		Double totalPrice = getTotalPrice(products);
		Integer index = 0;

		for(Product product : products){
			Integer quantity = orderRepository.findByProductId(product.getProductId()).getQuantity();
			oldQuantities.add(product.getQuantity());
			product.setQuantity(quantity);
		}

		addObserver(emailObserver);
		notifyObservers(orderLine, products, totalPrice, email);

		for(Product product : products){
			Integer quantity = orderRepository.findByProductId(product.getProductId()).getQuantity();
			product.setQuantity(oldQuantities.get(index++));
			productService.sellProduct(product.getName(), quantity, String.valueOf(product.getSize()));
		}
		
		orderLine.setStatus(Status.NEW);
		orderLine.setPayment(Payment.valueOf(payment));
		orderLine.setUsername(orderLine.getUsername()+"0");
		return orderLineRepository.save(orderLine);
	}

	private boolean contains(String input) {
		for (Payment c : Payment.values()) 
			if (c.name().equals(input)) 
				return true;
		for (Status c : Status.values()) 
			if (c.name().equals(input)) 
				return true;
	
		return false;
	}

	@Override
	public void addObserver(Observer observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObservers(OrderLine orderLine, ArrayList<Product> products, Double totalPrice, String email) {
		for(Observer obs : observers)
			obs.update(orderLine, products, totalPrice, email);
		
	}
}