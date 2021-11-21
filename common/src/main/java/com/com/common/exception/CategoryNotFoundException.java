package com.com.common.exception;

public class CategoryNotFoundException extends Exception {

    public CategoryNotFoundException() {
    }

    public CategoryNotFoundException(String message) {
        super(message="Category does not exist");
    }

    public CategoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoryNotFoundException(Throwable cause) {
        super(cause);
    }

    public CategoryNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
