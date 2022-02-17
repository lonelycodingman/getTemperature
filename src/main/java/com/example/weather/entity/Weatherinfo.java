package com.example.weather.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Weatherinfo {
    private String city;
    private String cityid;
    private String temp;
    @JsonProperty("WD")
    private String WD;
    @JsonProperty("WS")
    private String WS;
    @JsonProperty("SD")
    private String SD;
    @JsonProperty("AP")
    private String AP;
    private String njd;
    @JsonProperty("WSE")
    private String WSE;
    private String time;
    private String sm;
    private String isRadar;
    @JsonProperty("Radar")
    private String Radar;
}
