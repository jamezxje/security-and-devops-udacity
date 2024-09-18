package com.example.demo.util;

import com.example.demo.util.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenUtil {
    @Autowired
    private JwtUtil jwtUtil;

    public String getUsername(String token) {
        String jwt = token.substring(7);
        return jwtUtil.extractUsername(jwt);
    }
}

