package dev.blasio99.webshop.server.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.blasio99.webshop.server.enums.Payment;
import dev.blasio99.webshop.server.exception.OrderException;
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

	private List<Observer> observers = new ArrayList<>();
	private Observer emailObserver = new EmailService();

	public OrderLineService(){
		addObserver(emailObserver);
	}
	
	public OrderLine addOrderLine (Orders order, String username) throws OrderException{
		OrderLine orderLine = new OrderLine();

		orderService.addOrder(order, order.getQuantity());
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

	public OrderLine addOrderToOrderLine(Orders order, String username){
		
		OrderLine orderLine = orderLineRepository.findByUsername(username);

		// if orderLine does not exist, redirect to the creation of it
		if(orderLine == null) return addOrderLine(order, username);

		
		ArrayList<Long> newOrderIdList = orderLine.getOrderIdList();

		Orders checkOrder = checkIfProductIsInOrderLine(newOrderIdList, order.getProductId());
		
		if(checkOrder != null){
			orderService.addOrder(checkOrder, order.getQuantity());
			return orderLine;
		}

		orderService.addOrder(order, order.getQuantity());

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
			products.add(productRepository.findById(orderId).get());
		}
		
		return products;
	}

	public Double getTotalPrice(ArrayList<Product> products){
		Double totalPrice = 0.0;

		for(Product product : products)
			totalPrice += product.getPrice() * product.getQuantity();

		return totalPrice;
	}

	public void finishOrder(Payment payment, String name){
		OrderLine orderLine = orderLineRepository.findByUsername(name);

		notifyObservers(orderLine, name);

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
	public void notifyObservers(OrderLine orderLine, String email) {
		for(Observer obs : observers)
			obs.update(orderLine, email);
		
	}
}