package com.pard.server.hw3_sungkukjung.weather.repository;

import com.pard.server.hw3_sungkukjung.weather.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {
    List<Weather> findAllByOrderByIdDesc();
}
