package com.uri.service;

import com.uri.dto.JobRequirementDTO;
import com.uri.dto.Response;

public interface RequirementService {
    Response<?> saveRequirement(JobRequirementDTO jobRequirementDTO);

    Response<?> getAllRequirements(String companyId);

    Response<?> getRequirement(Long id);

    Response<?> getRecentPost(Long companyId);

    Response<?> getAllRequirementsForAdmin();

    Response<?> getAllRequirementsForCompanyDashboard(String companyId);
}
