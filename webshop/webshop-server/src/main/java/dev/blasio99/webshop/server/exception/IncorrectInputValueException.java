package dev.blasio99.webshop.server.exception;

public class IncorrectInputValueException extends Exception{
	
	public IncorrectInputValueException() {
        super();
    }

    public IncorrectInputValueException(String message) {
        super(message);
    }

    public IncorrectInputValueException(String message, Throwable cause) {
        super(message, cause);
	}
}
