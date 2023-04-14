package com.example.feign.logger;

import feign.Logger;
import feign.Request;
import feign.Response;
import feign.Util;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

import static feign.Util.*;

@RequiredArgsConstructor
public class FeignCustomLogger extends Logger {

    private static final int DEFAULT_SLOW_API_TIME = 3000;
    private static final String SLOW_API_NOTICE = "Slow Api";


    /**
     * @param configKey 실행된 FeignClientClassName#method 형식으로 자동 주입
     */
    @Override
    protected void log(String configKey, String format, Object... args) {
        // log 를 남길때 어떤 형식으로 남길지 정의
        System.out.println(String.format(methodTag(configKey) + format, args));
    }

    /**
     * @param logLevel : properties 에서 값을 읽어옴
     */
    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
        System.out.println("[logRequest]" + request);
    }


    /**
     * @param configKey : 실행된 FeignClientClassName#method 형식으로 자동 주입
     * @param logLevel : properties 에서 값을 읽어옴
     * @param elapsedTime : 요청과 응답 시간간의 차이
     */
    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
        String protocolVersion = resolveProtocolVersion(response.protocolVersion());
        String reason =
                response.reason() != null && logLevel.compareTo(Level.NONE) > 0 ? " " + response.reason()
                        : "";
        int status = response.status();
        log(configKey, "<--- %s %s%s (%sms)", protocolVersion, status, reason, elapsedTime);
        if (logLevel.ordinal() >= Level.HEADERS.ordinal()) {

            for (String field : response.headers().keySet()) {
                if (shouldLogResponseHeader(field)) {
                    for (String value : valuesOrEmpty(response.headers(), field)) {
                        log(configKey, "%s: %s", field, value);
                    }
                }
            }

            int bodyLength = 0;
            if (response.body() != null && !(status == 204 || status == 205)) {
                // HTTP 204 No Content "...response MUST NOT include a message-body"
                // HTTP 205 Reset Content "...response MUST NOT include an entity"
                if (logLevel.ordinal() >= Level.FULL.ordinal()) {
                    log(configKey, ""); // CRLF
                }
                byte[] bodyData = Util.toByteArray(response.body().asInputStream());
                bodyLength = bodyData.length;
                if (logLevel.ordinal() >= Level.FULL.ordinal() && bodyLength > 0) {
                    log(configKey, "%s", decodeOrDefault(bodyData, UTF_8, "Binary data"));
                }

                // 기존 구현되되어 잇는 메소드에서 추가로 커스텀된 내용, 응답시간이 느릴 경우 별도의 로그 출력 로직
                if(elapsedTime > DEFAULT_SLOW_API_TIME){
                    log(configKey, "[%s] elapsedTime : %s", SLOW_API_NOTICE, elapsedTime);
                }

                log(configKey, "<--- END HTTP (%s-byte body)", bodyLength);
                return response.toBuilder().body(bodyData).build();
            } else {
                log(configKey, "<--- END HTTP (%s-byte body)", bodyLength);
            }
        }
        return response;
    }
}
