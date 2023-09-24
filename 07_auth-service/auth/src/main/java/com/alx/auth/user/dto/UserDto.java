package com.alx.auth.user.dto;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
