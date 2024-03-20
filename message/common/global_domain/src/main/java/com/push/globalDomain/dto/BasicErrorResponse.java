package com.push.globalDomain.dto;

import com.push.globalDomain.enums.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BasicErrorResponse {

    private String errorCode; //에러 코드
    private String message; // 에러 메시지

    @Builder
    public BasicErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public BasicErrorResponse(ErrorCode errorCode){
        this.errorCode = errorCode.getErrorCode();
        this.message = errorCode.getMessage();
    }

}
