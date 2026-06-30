package com.uri.service;

import com.uri.dto.Response;

public interface MasterService {
    Response<?> getAllHiringDepartments();

    Response<?> getAllJobLocations();
}
