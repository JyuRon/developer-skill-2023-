package com.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AsyncService {

    private final EmailService emailService;

    /**
     * 비동기로 동작하기 위해서는 SpringFramework 의 도움의 필요하다.
     * 여기서 Spring 의 도움이란 async 하게 동작하기 위해서는 Sub Thread 에게 위임하는 과정이 필요한데 이때
     * 순수한 Bean 객체를 가져오는 것이 아닌 Proxy 객체로 Wrapping 된 객체를 반환해야 한다.
     * 즉 AsyncService 에 사용되는 EmailService 는 Spring 의 도움을 받은 Proxy 객체를 사용하게 된다.
     *
     * 추가로 자기 자신 안에 @Async 메소드가 존재하는 경우 proxy 객체가 아닌 자기 자신을 direct 로 참조하기 때문에 이 또한 Async 동작이 이루어 지지 않는다.
     *
     * asyncCall_2 의 경우 spring 에 등록된 빈이 아닌 인스턴스를 생성하여 비동기 실행 안됨
     * asyncCall_3 해당 빈 안에 다이렉트로 접근하게 되는 과정이기 때문에 wrapping 객체를 받아오지 않
     */
    public void asyncCall_1(){
        System.out.println("[asyncCall_1] :: " + Thread.currentThread().getName());
        emailService.sendMail();
        emailService.sendMailWithCustomThreadPool();
    }

    public void asyncCall_2(){
        System.out.println("[asyncCall_2] :: " + Thread.currentThread().getName());
        EmailService emailService1 = new EmailService();
        emailService1.sendMail();
        emailService1.sendMailWithCustomThreadPool();
    }

    public void asyncCall_3(){
        System.out.println("[asyncCall_3] :: " + Thread.currentThread().getName());
        sendMail();
    }

    @Async
    public void sendMail(){
        System.out.println("[sendMail] :: " + Thread.currentThread().getName());
    }
}
