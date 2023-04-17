package com.example.logback.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "SQL_LOG1")
@RestController
public class QueryController1 {

    @GetMapping("/query1")
    public String query1(){

        log.trace("logback trace");
        log.debug("logback debug");
        log.info("logback info");
        log.warn("logback warn");
        log.error("logback error");

        return "query1";

    }
}
