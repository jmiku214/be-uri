package com.uri.service;

import com.uri.dto.LoginRequest;
import com.uri.dto.RegisterRequest;
import com.uri.dto.Response;

public interface LoginService {
    Response<?> registerTalent(RegisterRequest request);

    Response<?> registerCompany(RegisterRequest request);

    Response<?> login(LoginRequest request);
}
