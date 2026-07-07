package com.uri.service;

import com.uri.dto.JobRequirementDTO;
import com.uri.dto.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface RequirementService {
    Response<?> saveRequirement(JobRequirementDTO jobRequirementDTO);

    Response<?> getAllRequirements(String companyId, Authentication authentication);

    Response<?> getRequirement(Long id);

    Response<?> getRecentPost(Long companyId);

    Response<?> getAllRequirementsByAdminForDashboard();

    Response<?> getAllRequirementsForCompanyDashboard(String companyId);

    Response<?> rejectRequirement(JobRequirementDTO jobRequirementDTO);

    Response<?> associateHiringManager(JobRequirementDTO jobRequirementDTO);

    Response<?> getAllRequirementsForAdmin();

    Response<?> getAllRequirementsForCompanyDashboardCount(String companyId);
}
