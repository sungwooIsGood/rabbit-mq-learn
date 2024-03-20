package com.push.globalDomain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@NoArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BasicResponse {
    private Object data;
    private HashMap<String, Object> error;

    @Builder
    public BasicResponse(Object data, HashMap<String, Object> error) {
        this.data = data;
        this.error = error;
    }
}
