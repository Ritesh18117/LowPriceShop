package com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.CustomerException;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}

