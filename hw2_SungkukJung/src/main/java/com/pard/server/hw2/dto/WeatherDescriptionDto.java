package com.pard.server.hw2.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherDescriptionDto {
    private String country;
    private String city;
    private String description;
}
