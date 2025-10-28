package com.pard.server.hw3_sungkukjung.weather.entity;

import com.pard.server.hw3_sungkukjung.weather.dto.request.WeatherRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    private double temperature;
    private int humidity;
    private String description;

    public void update(WeatherRequest.WeatherInfo req) {
        this.country = req.getCountry();
        this.city = req.getCity();
        this.temperature = req.getTemperature();
        this.humidity = req.getHumidity();
        this.description = req.getDescription();
    }
}
