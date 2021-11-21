package com.com.common.exception;

public class SaleNotFoundException extends Exception {

    public SaleNotFoundException() {
    }

    public SaleNotFoundException(String message) {
        super(message="Sale does not exist");
    }

    public SaleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SaleNotFoundException(Throwable cause) {
        super(cause);
    }

    public SaleNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
