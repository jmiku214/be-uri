package com.uri.controller;

import com.uri.dto.Response;
import com.uri.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("get-all")
    public ResponseEntity<?> getAllCompanies(){
        Response<?> response = companyService.getAllCompanies();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
    }

}
