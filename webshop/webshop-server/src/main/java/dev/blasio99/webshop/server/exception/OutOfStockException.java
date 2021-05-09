package dev.blasio99.webshop.server.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OutOfStockException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
}