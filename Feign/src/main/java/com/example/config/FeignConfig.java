package com.example.config;

import com.example.feign.logger.FeignCustomLogger;
import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public Logger feignLogger(){
        return new FeignCustomLogger();
    }
}
