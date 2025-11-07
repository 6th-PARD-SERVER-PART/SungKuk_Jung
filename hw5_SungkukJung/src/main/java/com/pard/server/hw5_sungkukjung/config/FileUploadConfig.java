package com.pard.server.hw5_sungkukjung.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration 
public class FileUploadConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir:uploads/posts}") // 업로드된 파일 저장 기본 디렉토리
    private String uploadDir;

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        // 파일 시스템 디렉토리에서 /uploads/** 경로의 업로드된 파일 제공
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadDir + "/");
    }
}