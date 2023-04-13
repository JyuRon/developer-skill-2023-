package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

// spring 이 async 로 동작할 수 있는 annotation 사용이 가능
@Configuration
@EnableAsync
public class AsyncConfig {
}
