package com.pard.server.seminar6.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter(){
        // url 경로별 corsConfig 정책 등록 객체
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // corsConfig 정책 객체
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true); // 쿠키허용

        config.addAllowedOrigin("http://172.17.195.15:3000"); // 모든 도메인 허용
        config.setAllowedOrigins(List.of("*"));
        config.addAllowedHeader("*"); // 모든 http header 허용

        config.addAllowedMethod("*"); // 모든 http method 허용

        source.registerCorsConfiguration("/*", config);

        return new CorsFilter(source);
    }
}
