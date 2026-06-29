package com.uri.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@Getter
@Setter
@NoArgsConstructor
public class JwtResponse {

    private Long id;
    private String token;
    private String username;
    private String role;
    private String email;
    private String phone;

    public JwtResponse(Long id, String token, String username, String role, String email, String phone) {
        this.id = id;
        this.token = token;
        this.username = username;
        this.role = role;
        this.email = email;
        this.phone = phone;
    }
}

