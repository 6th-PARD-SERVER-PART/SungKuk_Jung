package com.pard.server.hw2.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherTempDto {
    private String country;
    private String city;
    private Double temperature;
    private int humidity;
}
