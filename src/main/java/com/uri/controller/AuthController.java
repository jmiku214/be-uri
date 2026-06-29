package com.uri.controller;


import com.uri.dto.LoginRequest;
import com.uri.dto.RegisterRequest;
import com.uri.dto.Response;
import com.uri.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;


    @PostMapping("/register/talent")
    public ResponseEntity<?> registerTalent(@RequestBody RegisterRequest request) {
        Response<?> response = loginService.registerTalent(request);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
    }

    @PostMapping("/register/company")
    public ResponseEntity<?> registerCompany(@RequestBody RegisterRequest request) {
        Response<?> response = loginService.registerCompany(request);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Response<?> response=loginService.login(request);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
    }
}

