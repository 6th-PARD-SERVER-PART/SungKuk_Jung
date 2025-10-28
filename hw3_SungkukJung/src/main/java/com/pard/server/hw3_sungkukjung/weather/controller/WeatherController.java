package com.pard.server.hw3_sungkukjung.weather.controller;

import com.pard.server.hw3_sungkukjung.weather.dto.request.WeatherRequest;
import com.pard.server.hw3_sungkukjung.weather.dto.response.WeatherResponse;
import com.pard.server.hw3_sungkukjung.weather.entity.Weather;
import com.pard.server.hw3_sungkukjung.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherController {
    private final WeatherService weatherService;

    @PostMapping("/{country}/{city}")
    public void save(@PathVariable String country, @PathVariable String city) {
        weatherService.fetchWeather(country, city); // 데이터를 불어 오자마자 저장해야해서 saveWeather 대신 fetchWeather 사용 (fetchWeather안에 saveWeather 부름)
    }

    @GetMapping("/{id}")
    public ResponseEntity<Long> findById(@PathVariable Long id) {
        Long responseValue = weatherService.findById(id);
        return new ResponseEntity<>(responseValue, HttpStatus.OK);
    }

    @GetMapping("/{id}/temp")
    public ResponseEntity<?> findTemp(@PathVariable Long id) {
        try {
            WeatherResponse.WeatherTemp temperature = weatherService.findTemp(id);
            return ResponseEntity.ok(temperature);
        } catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/{id}/description")
    public ResponseEntity<?> findDescription(@PathVariable Long id) {
        try {
            WeatherResponse.WeatherDescription description = weatherService.findDescription(id);
            return ResponseEntity.ok(description);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public void updateWeather(@PathVariable Long id, @RequestBody WeatherRequest.WeatherInfo weatherRequest) {
        weatherService.updateById(id, weatherRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        weatherService.deleteById(id);
    }

    @GetMapping("")
    public List<WeatherRequest.WeatherInfo> findAll() {
        return weatherService.findAll();
    }
}
