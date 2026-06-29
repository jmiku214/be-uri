package com.uri.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

// DTOs
@Getter
@Setter
public class LoginRequest {

    private String username;
    private String password;
}
