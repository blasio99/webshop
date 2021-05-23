package dev.blasio99.webshop.server.observer;

import dev.blasio99.webshop.server.model.OrderLine;

public interface Observable {
	void addObserver(Observer observer);
	void removeObserver(Observer observer);
	void notifyObservers(OrderLine orderLine, String email);
}
