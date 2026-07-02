package com.uri.dto;

import com.uri.entity.CompanyMaster;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class JwtResponse {

    private Long id;
    private String token;
    private String userName;
    private String role;
    private String email;
    private String phone;
    private String logoUrl;
    private CompanyMaster companyData;

}

