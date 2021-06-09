package com.github.gadzooks.weather.controller.advice;

import com.github.gadzooks.weather.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

/**
 * Common code to handle exceptions and return the corresponding http statuses
 */
@RestControllerAdvice
class ExceptionHandlingAdvice extends ResponseEntityExceptionHandler {

    //FIXME : not sure how to combine multiple exception handling into one method
    @ResponseBody
    @ExceptionHandler({ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<?> resourceNotFoundHandler(ResourceNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @ResponseBody
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<?> noSuchElementHandler(NoSuchElementException ex) {
        return ResponseEntity.notFound().build();
    }

}