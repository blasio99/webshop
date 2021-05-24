package dev.blasio99.webshop.server.observer;

import java.util.ArrayList;

import dev.blasio99.webshop.server.model.OrderLine;
import dev.blasio99.webshop.server.model.Product;

public interface Observable {
	void addObserver(Observer observer);
	void removeObserver(Observer observer);
	void notifyObservers(OrderLine orderLine, ArrayList<Product> products, 
						 Double totalPrice, String email);
}
