package com.pard.server.hw2.service;

import com.pard.server.hw2.dto.WeatherDescriptionDto;
import com.pard.server.hw2.dto.WeatherDto;
import com.pard.server.hw2.dto.WeatherTempDto;
import com.pard.server.hw2.entity.Weather;
import com.pard.server.hw2.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherRepository weatherRepository;
    private final OpenWeatherClient openWeatherClient;

    public void saveWeather(WeatherDto weatherDto) {
        Weather weather = Weather.builder()
                .id(weatherDto.getId())
                .country(weatherDto.getCountry())
                .city(weatherDto.getCity())
                .temperature(weatherDto.getTemperature())
                .humidity(weatherDto.getHumidity())
                .description(weatherDto.getDescription())
                .build();

        weatherRepository.saveData(weather);
    }

    public void fetchWeather(String country, String city) {
        // OpenWeather API 호출해서 데이터 받아오기
        // 받아온 결과를 Map<String, Object>으로 변환
        Map<String, Object> weatherData = openWeatherClient.getWeather(country, city);
        /**
         * {
         *   "main": { "temp": 18.93, "humidity": 86 },
         *   "weather": [ { "description": "clear sky" } ]
         * }
         */

        // weatherData.get("main") -> Map<String,Object>; key: "temp", "humidity", "description"
        double temperature = ((Number)((Map<String,Object>)weatherData.get("main")).get("temp")).doubleValue(); // get("temp") object 값 -> double (.doubleValue())
        int humidity = ((Number)((Map<String,Object>)weatherData.get("main")).get("humidity")).intValue(); // .get("humidity") object 값 -> int (.intValue())
        // weatherData.get("weather") → List, 첫 번째 요소(Map)의 "description" 값을 꺼냄
        String description = ((Map<String,Object>)((java.util.List)weatherData.get("weather")).get(0)).get("description").toString(); // .get("description") object 값 -> String (.toString())

        // 데이터를 -> DTO
        WeatherDto weatherDto = WeatherDto.builder()
                .id(System.currentTimeMillis()) // 현제 시간을 ID로
                .country(country)
                .city(city)
                .temperature(temperature)
                .humidity(humidity)
                .description(description)
                .build();

        saveWeather(weatherDto); // DTO를 repository에 저장
    }

    public WeatherDto findById(Long id) {
        Weather weather = weatherRepository.findById(id);
        return WeatherDto.toDto(weather);
    }

    public WeatherTempDto findTemp(Long id) {
        Weather weather = weatherRepository.findById(id);
        return WeatherTempDto.builder()
                .country(weather.getCountry())
                .city(weather.getCity())
                .temperature(weather.getTemperature())
                .humidity(weather.getHumidity())
                .build();
    }

    public WeatherDescriptionDto findDescription(Long id) {
        Weather weather = weatherRepository.findById(id);
        return WeatherDescriptionDto.builder()
                .country(weather.getCountry())
                .city(weather.getCity())
                .description(weather.getDescription())
                .build();
    }

    public List<WeatherDto> findAll() {
        List<WeatherDto> weatherDtos = new ArrayList<>();
        weatherRepository.findAll().forEach(weather -> {
            WeatherDto weatherDto = WeatherDto.toDto(weather);
            weatherDtos.add(weatherDto);
        });
        return weatherDtos;
    }

    public void updateById(Long id, WeatherDto weatherDto) {
        Weather weather = weatherRepository.findById(id);
        weather.updateAll(
                weatherDto.getCountry(),
                weatherDto.getCity(),
                weatherDto.getTemperature(),
                weatherDto.getHumidity(),
                weatherDto.getDescription());

        weatherRepository.saveData(weather);
    }

    public void deleteById(Long id) {
        weatherRepository.deleteById(id);
    }
}
