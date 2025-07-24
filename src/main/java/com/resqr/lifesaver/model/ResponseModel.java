package com.resqr.lifesaver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseModel {
    private Boolean success;
    private String message;
    private Object data;

    public static ResponseModel success(String message, Object data) {
        return ResponseModel.builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static ResponseModel success(String message) {
        return ResponseModel.builder()
                .success(true)
                .message(message)
                .data(null)
                .build();
    }

    public static ResponseModel error(String message) {
        return ResponseModel.builder()
                .success(false)
                .message(message)
                .data(null)
                .build();
    }
}
