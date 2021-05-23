package dev.blasio99.webshop.server.observer;

import dev.blasio99.webshop.server.model.Product;

public interface Observer {
	public void update(Product product, String email);
}