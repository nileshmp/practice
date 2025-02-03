package com.nilesh.practice.rest.api.response;

import com.nilesh.practice.rest.api.response.common.SuccessResponse;

public class User {

    private Long id;
    private String name;
    private String email;

    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static void main(String[] args) {
        User user = new User(123L, "John Doe", "johndoe@example.com");
        SuccessResponse<User> successResponse =
            new SuccessResponse<>("User details fetched successfully", user);
        System.out.println(successResponse);
    }
}

