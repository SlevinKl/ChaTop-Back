package com.chatop.factory;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.chatop.entity.UserEntity;
import com.chatop.model.request.auth.RegisterRequest;

public class UserFactory {
    public static UserEntity createUserFromRequest(RegisterRequest registerRequest, PasswordEncoder passwordEncoder) {
        UserEntity user = new UserEntity();
        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        user.setPassword(registerRequest.getPassword(), passwordEncoder);
        return user;
    }
}
