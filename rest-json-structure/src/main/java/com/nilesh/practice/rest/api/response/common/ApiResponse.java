package com.nilesh.practice.rest.api.response.common;

import java.util.List;

public class ApiResponse<T> {
    private String status;
    private String message;
    private T data; // This will hold the response data for success cases
    private List<ErrorDetail> errors; // This will hold error details for failure cases

    // Constructors, Getters, Setters, and toString method

    public ApiResponse(String status, String message, T data, List<ErrorDetail> errors) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.errors = errors;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<ErrorDetail> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDetail> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
            "status='" + status + '\'' +
            ", message='" + message + '\'' +
            ", data=" + data +
            ", errors=" + errors +
            '}';
    }
}
