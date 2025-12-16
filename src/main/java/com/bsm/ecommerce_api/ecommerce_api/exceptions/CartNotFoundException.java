package com.bsm.ecommerce_api.ecommerce_api.exceptions;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(String message) {
        super(message);
    }
}
