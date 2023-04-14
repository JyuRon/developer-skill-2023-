package com.example.feign.decoder;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class DemoFeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new Default();
    @Override
    public Exception decode(String methodKey, Response response) {

        HttpStatus httpStatus = HttpStatus.resolve(response.status());

        if(httpStatus == HttpStatus.NOT_FOUND){
            System.out.println("[DemoFeignErrorDecoder] Http Status : " + httpStatus);
            throw new RuntimeException(String.format("[RuntimeException] Http Status is %s", httpStatus));
        }

        // 특정 상태값을 제외한 error 핸들링은 기존 코드로 대체한다.
        return errorDecoder.decode(methodKey, response);
    }
}
