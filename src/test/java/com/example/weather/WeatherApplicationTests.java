package com.example.weather;

import com.example.weather.entity.WeatherResponse;
import com.example.weather.service.IWeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
class WeatherApplicationTests {

    @Autowired
    IWeatherService weatherService;

    @Test
    void contextLoads(){

    }

    @Test
    public void testWeather(){
        Optional<Integer> temperature = weatherService.getTemperature("江苏", "苏州", "吴中");
        Assertions.assertNotNull(temperature);
    }

}
