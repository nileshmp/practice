package com.nilesh.practice.rest.api.response.common;

import java.util.List;

public class FailureResponse extends ApiResponse<Object> {

    public FailureResponse(String message, List<ErrorDetail> errors) {
        super("error", message, null, errors); // error status, no data
    }

    public static void main(String[] args) {
        ErrorDetail error1 = new ErrorDetail("USER_NOT_FOUND", "The user with the provided ID does not exist.");
        ErrorDetail error2 = new ErrorDetail("INVALID_EMAIL", "The email format is incorrect.");

        List<ErrorDetail> errorDetails = List.of(error1, error2);
        FailureResponse failureResponse = new FailureResponse("Failed to process the request", errorDetails);
        System.out.println(failureResponse);
    }
}
