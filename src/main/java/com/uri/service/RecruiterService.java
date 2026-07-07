package com.uri.service;

import com.uri.dto.RegisterRequest;
import com.uri.dto.Response;

public interface RecruiterService {
    Response<?> getAllRecruiters();

    Response<?> createRecruiter(RegisterRequest registerRequest);
}
