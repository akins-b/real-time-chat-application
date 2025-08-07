package com.boma.backend.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.awt.*;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public WebClient webClient(@Value("${brevo.api_key") String apiKey){
        return WebClient.builder()
                .baseUrl("https://api.brevo.com/v3")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("api-key", apiKey)
                .build();

    }

    @Bean
    public Cloudinary cloudinary(@Value("$cloudinary.cloud-name") String cloudName,
                                 @Value("$cloudinary.api-key") String apiKey,
                                 @Value("$cloudinary.api-secret") String apiSecret){
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key",apiKey,
                "api_secret", apiKey
        ));
    }
}
