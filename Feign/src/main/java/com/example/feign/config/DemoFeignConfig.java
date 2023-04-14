package com.example.feign.config;

import com.example.feign.decoder.DemoFeignErrorDecoder;
import com.example.feign.interceptor.DemoFeignInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoFeignConfig {

    @Bean
    public DemoFeignInterceptor feignInterceptor(){
        // @RequiredArgsConstructor(staticName = "of") 사용으로 인한 of 메소드 사용
        return DemoFeignInterceptor.of();
    }

    @Bean
    public DemoFeignErrorDecoder demoErrorDecoder(){
        return new DemoFeignErrorDecoder();
    }
}
