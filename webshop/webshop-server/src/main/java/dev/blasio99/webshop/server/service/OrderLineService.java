package dev.blasio99.webshop.server.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.blasio99.webshop.server.exception.OrderException;
import dev.blasio99.webshop.server.model.Orders;
import dev.blasio99.webshop.server.model.OrderLine;
import dev.blasio99.webshop.server.model.User;
import dev.blasio99.webshop.server.repo.OrderLineRepository;
import dev.blasio99.webshop.server.repo.UserRepository;

@Service
public class OrderLineService  {
	
	@Autowired
	private OrderLineRepository orderLineRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderService orderService;
	
	public OrderLine addOrderLine (Orders order, String username) throws OrderException{
		OrderLine orderLine = new OrderLine();

		orderService.addOrder(order);
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

	public OrderLine addOrderToOrderLine(Orders order, String username){
		
		OrderLine orderLine = orderLineRepository.findByUsername(username);

		// if orderLine does not exist, redirect to the creation of it
		if(orderLine == null) return addOrderLine(order, username);

		orderService.addOrder(order);
		ArrayList<Long> newOrderIdList = orderLine.getOrderIdList();
		newOrderIdList.add(order.getId());
		orderLine.setOrderIdList(newOrderIdList);

		return orderLineRepository.save(orderLine);
	}
	
	public ArrayList<OrderLine> getOrderLines(){
		return (ArrayList<OrderLine>) orderLineRepository.findAll();
	}
}