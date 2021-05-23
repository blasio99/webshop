package dev.blasio99.webshop.server.observer;

import dev.blasio99.webshop.server.model.OrderLine;

public interface Observer {
	public void update(OrderLine orderLine, String email);
}