package com.example.demo.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CartException extends RuntimeException {
    protected static final Logger log = LoggerFactory.getLogger(CartException.class);

    public CartException(String message) {
        super(message);
        log.warn(message);
    }
}