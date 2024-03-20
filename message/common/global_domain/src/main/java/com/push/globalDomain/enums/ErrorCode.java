package com.push.globalDomain.enums;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 데이터 관련 - 000~
    DATA_INCORRECT("DATA_INCORRECT", HttpStatus.BAD_REQUEST, "001", "잘못된 데이터를 보냈습니다."),
    DATA_REQUIRED("DATA_REQUIRED", HttpStatus.BAD_REQUEST, "002", "파라미터 값은 필수 입니다."),
    DATA_JSON_TO_OBJECT_PARSING_FAILED("DATA_JSON_TO_OBJECT_PARSING_FAILED", HttpStatus.BAD_REQUEST, "003", "json이 Object로 파싱되지 않았습니다."),
    DATA_TIME_STRUCTURE_FAILED("DATA_TIME_STRUCTURE_FAILED", HttpStatus.BAD_REQUEST, "004", "시간 관련된 데이터 규격이 옳바르지 않습니다.");

    private final String errorName;

    private final HttpStatus status;
    private final String errorCode;
    private String message;

    ErrorCode(String errorName, HttpStatus status, String errorCode, String message) {
        this.errorName = errorName;
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }
}
