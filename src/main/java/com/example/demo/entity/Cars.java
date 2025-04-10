package com.example.demo.entity;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/cars")
public class Cars {
    //https://localhost:8080/api/v1/cars
    @PostMapping("/api/v1/cars")
    public String addCars() {
        return "added";
    }
}
