package dev.blasio99.webshop.server.observer;

import java.util.ArrayList;

import dev.blasio99.webshop.server.model.OrderLine;
import dev.blasio99.webshop.server.model.Product;

public interface Observer {
	public void update(OrderLine orderLine, ArrayList<Product> products, 
					   Double totalPrice, String email);
}