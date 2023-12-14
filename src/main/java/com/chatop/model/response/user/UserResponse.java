package com.chatop.model.response.user;

import java.time.LocalDateTime;

import com.chatop.entity.UserEntity;

import lombok.Data;

@Data
public class UserResponse {

    private Integer id;
    private String name;
    private String email;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public UserResponse(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.name = userEntity.getName();
        this.email = userEntity.getEmail();
        this.created_at = userEntity.getCreatedAt();
        this.updated_at = userEntity.getUpdatedAt();
    }
}