package com.pard.server.hw3_sungkukjung.weather.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class WeatherRequest {
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class WeatherInfo{
        private String country;
        private String city;
        private Double temperature;
        private int humidity;
        private String description;
    }
}
