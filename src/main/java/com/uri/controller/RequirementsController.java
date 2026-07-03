package com.uri.controller;

import com.uri.dto.JobRequirementDTO;
import com.uri.dto.Response;
import com.uri.service.RequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/requirement")
public class RequirementsController {

    @Autowired
    private RequirementService requirementService;

    @PostMapping("save")
    public ResponseEntity<?> saveRequirement(@RequestBody JobRequirementDTO jobRequirementDTO){
        Response<?> response = requirementService.saveRequirement(jobRequirementDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
    }

    @GetMapping("get-all")
    public ResponseEntity<?> getAllRequirements(@RequestParam(required = true) String companyId){
        Response<?> response=requirementService.getAllRequirements(companyId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
    }

    @GetMapping("get-all/for/company-dashboard")
    public ResponseEntity<?> getAllRequirementsForCompanyDashboard(@RequestParam(required = true) String companyId){
        Response<?> response=requirementService.getAllRequirementsForCompanyDashboard(companyId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
    }
    @GetMapping("view")
    public ResponseEntity<?> getRequirement(@RequestParam(required = true) Long id){
        Response<?> response=requirementService.getRequirement(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
    }

    @GetMapping("view/recent-post")
    public ResponseEntity<?> getRecentPost(@RequestParam(required = true) Long companyId){
        Response<?> response=requirementService.getRecentPost(companyId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
    }

    @GetMapping("get-all/by/admin")
    public ResponseEntity<?> getAllRequirementsByAdmin(){
        Response<?> response=requirementService.getAllRequirementsForAdmin();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
    }
}
