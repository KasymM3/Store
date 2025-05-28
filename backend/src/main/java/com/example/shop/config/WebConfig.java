package com.example.shop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                // Разрешаем любую схему, хост и порт
                .allowedOriginPatterns("*")
                .allowedMethods("*")       // GET, POST, PUT, DELETE, OPTIONS и т.д.
                .allowedHeaders("*")       // Authorization, Content-Type и т.д.
                .allowCredentials(true);   // если нужны куки/credentials
    }
}
