package com.pard.server.hw2.dto;

import com.pard.server.hw2.entity.Weather;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherDto {
    private Long id;
    private String country;
    private String city;
    private Double temperature;
    private int humidity;
    private String description;

    public static WeatherDto toDto(Weather weather) {
        return WeatherDto.builder()
                .id(weather.getId())
                .country(weather.getCountry())
                .city(weather.getCity())
                .temperature(weather.getTemperature())
                .humidity(weather.getHumidity())
                .description(weather.getDescription())
                .build();
    }
}
