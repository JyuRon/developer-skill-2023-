package com.example.logback.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DemoController {

    @GetMapping("/demo")
    public String demo(){
        log.trace("logback trace");
        log.debug("logback debug");
        log.info("logback info");
        log.warn("logback warn");
        log.error("logback error");

        return "test";
    }
}
