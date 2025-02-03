package com.nilesh.practice.rest.api.response.common;

public class ErrorDetail {
    private String code;
    private String detail;

    // Constructors, Getters, Setters, and toString method

    public ErrorDetail(String code, String detail) {
        this.code = code;
        this.detail = detail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "ErrorDetail{" +
            "code='" + code + '\'' +
            ", detail='" + detail + '\'' +
            '}';
    }
}
