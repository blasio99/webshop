package dev.blasio99.webshop.server.exception;

public class OrderLineException extends RuntimeException{

	public OrderLineException() {
        super();
    }

    public OrderLineException(String message) {
        super(message);
    }

    public OrderLineException(String message, Throwable cause) {
        super(message, cause);
	}
}
