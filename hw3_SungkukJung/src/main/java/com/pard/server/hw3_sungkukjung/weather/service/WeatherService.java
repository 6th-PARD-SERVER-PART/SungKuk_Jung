package com.pard.server.hw3_sungkukjung.weather.service;

import com.pard.server.hw3_sungkukjung.weather.dto.request.WeatherRequest;
import com.pard.server.hw3_sungkukjung.weather.dto.response.WeatherResponse;
import com.pard.server.hw3_sungkukjung.weather.entity.Weather;
import com.pard.server.hw3_sungkukjung.weather.repository.WeatherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherRepository weatherRepository;
    private final OpenWeatherClient openWeatherClient;

    public void save(WeatherRequest.WeatherInfo request) {
        Weather weather = Weather.builder()
                .country(request.getCountry())
                .city(request.getCity())
                .temperature(request.getTemperature())
                .humidity(request.getHumidity())
                .description(request.getDescription())
                .build();

        weatherRepository.save(weather);
    }

    public void fetchWeather(String country, String city) {
        Map<String, Object> weatherData = openWeatherClient.getWeather(country, city);

        double temperature = ((Number) ((Map<String, Object>) weatherData.get("main")).get("temp")).doubleValue();
        int humidity = ((Number) ((Map<String, Object>) weatherData.get("main")).get("humidity")).intValue();
        String description = ((Map<String, Object>) ((List<?>) weatherData.get("weather")).get(0))
                .get("description").toString();

        WeatherRequest.WeatherInfo request = WeatherRequest.WeatherInfo.builder()
                .country(country)
                .city(city)
                .temperature(temperature)
                .humidity(humidity)
                .description(description)
                .build();

        save(request);
    }

    public Long findById(Long id) {
        return weatherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Weather data not found with ID: " + id))
                .getId();
    }

    public WeatherResponse.WeatherTemp findTemp(Long id) {
        Weather weather = weatherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Weather data not found with ID: " + id));

        return WeatherResponse.WeatherTemp.builder()
                .country(weather.getCountry())
                .city(weather.getCity())
                .temperature(weather.getTemperature())
                .humidity(weather.getHumidity())
                .build();
    }

    public WeatherResponse.WeatherDescription findDescription(Long id) {
        Weather weather = weatherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Weather data not found with ID: " + id));

        return WeatherResponse.WeatherDescription.builder()
                .country(weather.getCountry())
                .city(weather.getCity())
                .description(weather.getDescription())
                .build();
    }

    @Transactional
    public void updateById(Long id, WeatherRequest.WeatherInfo weatherRequest) {
        Weather weather = weatherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Weather data not found with ID: " + id));

        weather.update(weatherRequest);
    }

    public void deleteById(Long id) {
        if (!weatherRepository.existsById(id)) {
            throw new RuntimeException("Weather data not found with ID: " + id);
        }
        weatherRepository.deleteById(id);
    }

    public List<WeatherRequest.WeatherInfo> findAll() {
        List<Weather> weathers = weatherRepository.findAllByOrderByIdDesc();
        return weathers.stream().map( weather ->
                        WeatherRequest.WeatherInfo.builder()
                                .country(weather.getCountry())
                                .city(weather.getCity())
                                .temperature(weather.getTemperature())
                                .humidity(weather.getHumidity())
                                .description(weather.getDescription())
                                .build()
                ).toList();
    }
}
