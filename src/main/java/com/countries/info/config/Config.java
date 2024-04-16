package com.countries.info.config;

import com.countries.info.service.FetchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {
    private static FetchService service = null;
    private static RestTemplate restTemplate = null;
    private static ObjectMapper mapper = null;

    @Bean
    public synchronized FetchService getFetchServiceInstance() {
        if (service == null) {
            service = new FetchService(getRestTemplateInstance(), getObjectMapperInstance());
        }

        return service;
    }

    @Bean
    public static synchronized RestTemplate getRestTemplateInstance() {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }

        return restTemplate;
    }

    @Bean
    public static synchronized ObjectMapper getObjectMapperInstance() {
        if (mapper == null) {
            mapper = new ObjectMapper();
        }

        return mapper;
    }
}
