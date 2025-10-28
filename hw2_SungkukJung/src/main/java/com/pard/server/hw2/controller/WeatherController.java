package com.pard.server.hw2.controller;

import com.pard.server.hw2.dto.WeatherDescriptionDto;
import com.pard.server.hw2.dto.WeatherDto;
import com.pard.server.hw2.dto.WeatherTempDto;
import com.pard.server.hw2.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherController {
    private final WeatherService weatherService;

    @PostMapping("/{country}/{city}")
    public String saveData(@PathVariable String country, @PathVariable String city) {
        weatherService.fetchWeather(country, city); // 데이터를 불어 오자마자 저장해야해서 saveWeather 대신 fetchWeather 사용 (fetchWeather안에 saveWeather 부름)
        return "Weather Data Saved";
    }

    @GetMapping("/{id}")
    public WeatherDto findById(@PathVariable Long id) {
        return weatherService.findById(id);
    }

    @GetMapping("/{id}/temp")
    public WeatherTempDto findTemp(@PathVariable Long id) {
        return weatherService.findTemp(id);
    }

    @GetMapping("/{id}/description")
    public WeatherDescriptionDto findDescription(@PathVariable Long id) {
        return weatherService.findDescription(id);
    }

    @PatchMapping("/{id}")
    public String updateWeather(@PathVariable Long id, @RequestBody WeatherDto weatherDto) {
        weatherService.updateById(id, weatherDto);
        return "Weather Data Updated";
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable Long id) {
        weatherService.deleteById(id);
        return "Data Deleted";
    }

    @GetMapping("")
    public List<WeatherDto> findAll() {
        return weatherService.findAll();
    }
}
