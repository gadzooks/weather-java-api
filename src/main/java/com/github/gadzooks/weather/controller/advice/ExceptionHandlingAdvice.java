package com.github.gadzooks.weather.controller.advice;

import com.github.gadzooks.weather.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Common code to handle exceptions and return the corresponding http statuses
 */
@ControllerAdvice
class ExceptionHandlingAdvice {

    @ResponseBody
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<?> employeeNotFoundHandler(ResourceNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }
}