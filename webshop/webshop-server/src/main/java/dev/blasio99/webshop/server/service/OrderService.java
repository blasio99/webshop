package dev.blasio99.webshop.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.blasio99.webshop.server.exception.OrderException;
import dev.blasio99.webshop.server.exception.OutOfStockException;
import dev.blasio99.webshop.server.exception.ProductNotFoundException;
import dev.blasio99.webshop.server.model.Orders;
import dev.blasio99.webshop.server.model.Product;
import dev.blasio99.webshop.server.repo.OrderRepository;
import dev.blasio99.webshop.server.repo.ProductRepository;

@Service
public class OrderService  {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired 
	private ProductRepository productRepository;
	
	
	public Orders addOrder (Orders order) throws OrderException{
		
		Product product = productRepository.findById(order.getProductId()).get();

		if(product == null) 
			throw new ProductNotFoundException();
		if(product.getQuantity() < order.getQuantity()) 
			throw new OutOfStockException();

		product.setQuantity(product.getQuantity() - order.getQuantity());		
		
		return orderRepository.save(order);
	}
	
	public List<Orders> getOrders(){
		return orderRepository.findAll();
	}
}
