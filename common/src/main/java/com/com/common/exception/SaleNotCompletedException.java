package com.com.common.exception;

public class SaleNotCompletedException extends Exception {

    public SaleNotCompletedException() {
    }

    public SaleNotCompletedException(String message) {
        super(message="Your order has not been completed! Check the quantity of the product and your status.");
    }

    public SaleNotCompletedException(String message, Throwable cause) {
        super(message, cause);
    }

    public SaleNotCompletedException(Throwable cause) {
        super(cause);
    }

    public SaleNotCompletedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
