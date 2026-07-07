package com.uri.controller;

import com.uri.dto.RegisterRequest;
import com.uri.dto.Response;
import com.uri.service.RecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/recruiter")
public class RecruiterController {


    @Autowired
    private RecruiterService recruiterService;

    @GetMapping("get-all")
    public ResponseEntity<?> getAllRecruiters() {
        Response<?> response=recruiterService.getAllRecruiters();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
    }

    @PostMapping("create")
    public ResponseEntity<?> createRecruiter(@RequestBody RegisterRequest registerRequest) {
        Response<?> response=recruiterService.createRecruiter(registerRequest);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
    }

}
