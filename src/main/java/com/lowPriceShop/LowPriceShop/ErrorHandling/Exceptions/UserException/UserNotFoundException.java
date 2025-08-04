package com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.UserException;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}

