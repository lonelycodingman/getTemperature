package com.example.weather.service;

import java.util.Optional;

public interface IWeatherService {
    Optional<Integer> getTemperature(String province, String city, String county);

}
