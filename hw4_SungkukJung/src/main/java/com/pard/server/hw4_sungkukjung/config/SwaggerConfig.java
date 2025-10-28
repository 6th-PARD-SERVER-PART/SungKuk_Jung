package com.pard.server.hw4_sungkukjung.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.servers.Server;

@Configuration 
public class SwaggerConfig {


    @Bean 
    public OpenAPI openAPI(){
        return new OpenAPI()
                .addServersItem(new Server().url("/")) 
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo(){ 
        return new Info()
                .title("hw4")
                .description("hw4 스웨거")
                .version("1.0.0");
    }
}