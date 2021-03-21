package com.github.gadzooks.weather.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaceController {
    @GetMapping(value = "/")
    public String home() {
        return "home";
    }

}
