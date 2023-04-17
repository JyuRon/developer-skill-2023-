package com.example.logback.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MdcController {

    /*
        쓰레드 별로 MDC 객체(Map)에 들어있는 key,value 를 관리할 수 있다.
        멀티쓰레드 환경에서 로그를 남길때 사용하며 로그백에서 해당 저장되어 있는 값을 동적으로 가져와 출력시키기 위함
        동일 쓰레드가 정리가 되지 않은 상태에서 job 을 호출할 경우 의도치 앟은 값이 리턴될 수 있기에 페어로 사용하는 것을 권장
     */
    @GetMapping("/mdc")
    public String mdc(){
        MDC.put("job","dev");

        log.trace("logback trace");
        log.debug("logback debug");
        log.info("logback info");
        log.warn("logback warn");
        log.error("logback error");

        MDC.clear();
        return "mdc";
    }
}
