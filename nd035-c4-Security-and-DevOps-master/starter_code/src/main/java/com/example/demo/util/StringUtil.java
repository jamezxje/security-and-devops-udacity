package com.example.demo.util;

import com.example.demo.model.requests.LoginRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StringUtil {

    public static final String SECRET_KEY = "SECRET";

    public static boolean validateCreateUserRequest(LoginRequest createUserRequest) {
        if (createUserRequest.getUsername() == null || createUserRequest.getUsername().isEmpty() || createUserRequest.getUsername().length() < 6 || createUserRequest.getUsername().contains(" ")) {
            return false;
        }

        return createUserRequest.getPassword() != null && !createUserRequest.getPassword().isEmpty() && createUserRequest.getPassword().length() >= 8 && !createUserRequest.getPassword().contains(" ");
    }

    public static String mapToJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}