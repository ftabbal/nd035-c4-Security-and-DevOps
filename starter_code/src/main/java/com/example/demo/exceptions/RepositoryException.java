package com.example.demo.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RepositoryException extends RuntimeException{

    protected static final Logger log = LoggerFactory.getLogger(RepositoryException.class);

    public RepositoryException(String message) {
        super(message);
        log.warn(message);
    }
}
