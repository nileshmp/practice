package com.nilesh.practice.rest.api.response.common;

public class SuccessResponse<T> extends ApiResponse<T> {

    public SuccessResponse(String message, T data) {
        super("success", message, data, null); // success status, no errors
    }
}