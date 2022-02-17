package com.example.weather.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


public class WeatherResponse implements Serializable {

    @JsonProperty("weatherinfo")
    private Weatherinfo weatherinfo;

    public Weatherinfo getWeatherInfo() {
        return weatherinfo;
    }

    public void setWeatherInfo(Weatherinfo weatherinfo) {
        this.weatherinfo = weatherinfo;
    }
}
