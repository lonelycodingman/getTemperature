package com.example.weather.service.impl;

import com.example.weather.entity.WeatherResponse;
import com.example.weather.service.IWeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class WeatherServiceImpl implements IWeatherService {

    @Autowired
    RestTemplate restTemplate;

    @Override
    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 2000L, multiplier = 1.5))
    public Optional<Integer> getTemperature(String province, String city, String county) {
        String provinceId = "";
        String cityId = "";
        String countyId = "";
        WeatherResponse weatherResponse = new WeatherResponse();

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseEntity<String> provinceResponseEntity = restTemplate.getForEntity("http://www.weather.com.cn/data/city3jdata/china.html", String.class);
        String provinceResponseEntityBody = provinceResponseEntity.getBody();
        try {

            Map<String,String> provinceMap = objectMapper.readValue(provinceResponseEntityBody, Map.class);
            if(!provinceMap.containsValue(province)){
                throw new Exception("未查找到相应的省");
            }
            for (Map.Entry<String, String> entry : provinceMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (value.equals(province)) {
                    provinceId = key;
                }
            }

            ResponseEntity<String> cityResponseEntity = restTemplate.getForEntity("http://www.weather.com.cn/data/city3jdata/provshi/" + provinceId + ".html ", String.class);
            String cityResponseEntityBody = cityResponseEntity.getBody();
            Map<String,String> cityMap = objectMapper.readValue(cityResponseEntityBody, Map.class);
            if(!cityMap.containsValue(city)){
                throw new Exception("未查找到相应的市");
            }
            for (Map.Entry<String, String> entry : cityMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (value.equals(city)) {
                    cityId= key;
                }
            }

            ResponseEntity<String> stationResponseEntity = restTemplate.getForEntity("http://www.weather.com.cn/data/city3jdata/station/ " + provinceId +cityId + ".html ", String.class);
            String stationResponseEntityBody = stationResponseEntity.getBody();
            Map<String,String> stationMap = objectMapper.readValue(stationResponseEntityBody, Map.class);
            if(!stationMap.containsValue(county)){
                throw new Exception("未查找到相应的区");
            }
            for (Map.Entry<String, String> entry : stationMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (value.equals(county)) {
                    countyId= key;
                }
            }

            ResponseEntity<String> weatherInfoResponseEntity = restTemplate.getForEntity("http://www.weather.com.cn/data/sk/" + provinceId + cityId + countyId + ".html", String.class);
            weatherResponse = objectMapper.readValue(weatherInfoResponseEntity.getBody(), WeatherResponse.class);


        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] split = weatherResponse.getWeatherInfo().getTemp().split("\\.");
        return Optional.of(Integer.valueOf(split[0]));
    }
}
