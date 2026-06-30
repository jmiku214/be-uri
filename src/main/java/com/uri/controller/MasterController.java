package com.uri.controller;

import com.uri.dto.Response;
import com.uri.service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MasterController {

    @Autowired
    private MasterService masterService;

    @GetMapping("/api/hiring-departments")
    public ResponseEntity<?> getAllHiringDepartments() {
        Response<?> response = masterService.getAllHiringDepartments();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
    }

    @GetMapping("/api/job-locations")
    public ResponseEntity<?> getAllJobLocations() {
        Response<?> response = masterService.getAllJobLocations();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
    }
}
