package com.github.gadzooks.weather.controller.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// FIXME : THIS IS NOT WORKING !!

/**
 * NOTE : Catch all exceptions not caught by Spring MVC by extending ResponseEntityExceptionHandler,
 * as that handles all the spring-mvc errors. And the ones not handled
 * can then be caught using @ExceptionHandler(Exception.class)
 */
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception ex) {
        log.error(ex.toString());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}