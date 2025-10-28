package com.pard.server.hw2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenWeatherClient {

    private final WebClient webClient = WebClient.create("https://api.openweathermap.org/data/2.5/weather");

    @Value("${openweather.api.key}")
    private String apiKey; // application.properties에 API key 저장

    public Map<String, Object> getWeather(String country, String city) {
        Mono<Map> response = webClient.get() // 요청 시작
                .uri(uriBuilder -> uriBuilder // URL 완성 및 쿼리 파라미터 설정
                        .queryParam("q", city + "," + country)
                        .queryParam("appid", apiKey)
                        .queryParam("units", "metric") // Celsius
                        .build())
                .retrieve()// HTTP 요청 실행
                .bodyToMono(Map.class); // JSON -> Map

        return response.block(); // Mono 안 데이터를 동기적으로 꺼내 Map 저장
    }
}
