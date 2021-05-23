package dev.blasio99.webshop.server.observer;

import dev.blasio99.webshop.server.model.Product;

public interface Observable {
	void addObserver(Observer observer);
	void removeObserver(Observer observer);
	void notifyObservers(Product product, String email);
}
