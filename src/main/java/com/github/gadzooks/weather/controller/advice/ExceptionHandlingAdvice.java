package com.github.gadzooks.weather.controller.advice;

import com.github.gadzooks.weather.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

/**
 * Common code to handle exceptions and return the corresponding http statuses
 */
@RestControllerAdvice
class ExceptionHandlingAdvice {

    @ResponseBody
    @ExceptionHandler({ResourceNotFoundException.class, NoSuchElementException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<?> resourceNotFoundHandler(ResourceNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }
}