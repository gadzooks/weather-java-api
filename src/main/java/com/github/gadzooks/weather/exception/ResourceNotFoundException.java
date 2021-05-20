package com.github.gadzooks.weather.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, String id) {
        super("Could not find " + resourceName + " resource with id " + id);
    }
}
