package com.uri.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CompanyRegistrationDto {

    private String username;
    private String email;
    private String password;
    private String phone;
    private String website;
    private String address;
    private String logoUrl;
}
