package com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.UserException;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}
