package com.uri.service;

import com.uri.dto.CompanyRegistrationDto;
import com.uri.dto.LoginRequest;
import com.uri.dto.RegisterRequest;
import com.uri.dto.Response;

public interface LoginService {
    Response<?> registerTalent(RegisterRequest request);

    Response<?> registerCompany(CompanyRegistrationDto request);

    Response<?> login(LoginRequest request);
}
