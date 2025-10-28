package com.pard.server.hw2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Weather {
    private long id;
    private String country;
    private String city;
    private double temperature;
    private int humidity;
    private String description;

    public void updateAll(String country, String city, double temperature, int humidity, String description) {
        this.country = country;
        this.city = city;
        this.temperature = temperature;
        this.humidity = humidity;
        this.description = description;
    }
}
