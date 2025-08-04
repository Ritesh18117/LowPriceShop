package com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.CustomerException;

public class UnauthorizedCustomerException extends RuntimeException {
    public UnauthorizedCustomerException(String message) {
        super(message);
    }
}