package com.ryanshiun.springbootmall.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegisterRequest {
    // 不允許空白
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
}
