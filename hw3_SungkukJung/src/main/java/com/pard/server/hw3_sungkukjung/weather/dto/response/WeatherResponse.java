package com.pard.server.hw3_sungkukjung.weather.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class WeatherResponse {
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class WeatherTemp {
        private String country;
        private String city;
        private Double temperature;
        private int humidity;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class WeatherDescription {
        private String country;
        private String city;
        private String description;
    }
}
