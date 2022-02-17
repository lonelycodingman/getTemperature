package com.example.weather.controller;

import com.example.weather.service.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    IWeatherService weatherService;

    @GetMapping(value ="/getTemperature")
    public Optional<Integer> getTemperature(@RequestParam("province") String province,@RequestParam("city") String city,@RequestParam("county") String county){
        return weatherService.getTemperature(province,city,county);
    }
}
