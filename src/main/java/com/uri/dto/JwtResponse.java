package com.uri.dto;

import com.uri.entity.CompanyMaster;
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
    private String logoUrl;
    private CompanyMaster companyData;

    public JwtResponse(Long id, String token, String username, String role, String email, String phone,String logoUrl,CompanyMaster companyData) {
        this.id = id;
        this.token = token;
        this.username = username;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.logoUrl = logoUrl;
        this.companyData = companyData;
    }
}

