package com.pard.server.hw2.repository;

import com.pard.server.hw2.entity.Weather;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class WeatherRepository {
    public Map<Long, Weather> weatherRepository = new HashMap<>();

    public void saveData(Weather weather) {
        weatherRepository.put(weather.getId(), weather);
    }

    public Weather findById(Long id) {
        return weatherRepository.get(id);
    }

    public List<Weather> findAll() {
        return weatherRepository.values().stream().toList();
    }

    public void deleteById(Long id) {
        weatherRepository.remove(id);
    }
}
