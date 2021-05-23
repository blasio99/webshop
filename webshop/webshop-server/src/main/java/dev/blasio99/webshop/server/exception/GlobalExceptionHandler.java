package dev.blasio99.webshop.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CustomException.class)
    public void handleCustom() {}

    @ResponseStatus(value = HttpStatus.IM_USED, reason = "User exits!")
    @ExceptionHandler(UserExistsException.class)
    public void handleUserExists() {}

    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Out of stock!")
    @ExceptionHandler(OutOfStockException.class)
    public void handleOutOfStock() {}

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Product is not found. Try another one!")
    @ExceptionHandler(ProductNotFoundException.class)
    public void handleProductNotFound() {}

	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User is not found!")
    @ExceptionHandler(UserNotFoundException.class)
    public void handleUserNotFound() {}

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Invalid token!")
    @ExceptionHandler(InvalidTokenException.class)
    public void handleInvalidToken() {}

	@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Error on Order level!")
    @ExceptionHandler(OrderException.class)
    public void handleOrderError() {}

	@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Incorrect input value!")
    @ExceptionHandler(IncorrectInputValueException.class)
    public void handleIncorrectInputValue() {}
    
}