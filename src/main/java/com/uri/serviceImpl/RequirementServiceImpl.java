package com.uri.serviceImpl;

import com.uri.dto.JobRequirementDTO;
import com.uri.dto.Response;
import com.uri.entity.CompanyMaster;
import com.uri.entity.HiringDepartments;
import com.uri.entity.JobLocation;
import com.uri.entity.RequirementDetails;
import com.uri.enums.RequirementStatus;
import com.uri.repository.RequirementDetailsRepository;
import com.uri.service.RequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RequirementServiceImpl implements RequirementService {

    @Autowired
    private RequirementDetailsRepository requirementDetailsRepository;

    @Override
    public Response<?> saveRequirement(JobRequirementDTO jobRequirementDTO) {
        RequirementDetails existedRequirement = requirementDetailsRepository.findByJobTitleAndCompanyId(jobRequirementDTO.getRoleTitle().trim(), jobRequirementDTO.getCompanyId());
        if(existedRequirement != null){
            return new Response<>(HttpStatus.ALREADY_REPORTED.value(), "Requirement already exists",null);
        }
        RequirementDetails requirementDetails = RequirementDetails.builder()
                .roleTitle(jobRequirementDTO.getRoleTitle())
                .hiringDepartments(HiringDepartments.builder().id(jobRequirementDTO.getDepartment()).build())
                .jobLocation(JobLocation.builder().id(jobRequirementDTO.getLocation()).build())
                .employementTypes(jobRequirementDTO.getEmployementTypes())
                .numberOfPositions(jobRequirementDTO.getNumberOfPositions())
                .priority(jobRequirementDTO.getPriority())
                .targetStartDate(jobRequirementDTO.getTargetStartDate())
                .maxSalary(jobRequirementDTO.getMaxSalary())
                .minSalary(jobRequirementDTO.getMinSalary())
                .skills((jobRequirementDTO.getSkills()==null || jobRequirementDTO.getSkills().isEmpty())?"":String.join(", ", jobRequirementDTO.getSkills()))
                .niceToHaveSkills(jobRequirementDTO.getNiceToHaveSkills())
                .responsibilities(jobRequirementDTO.getResponsibilities())
                .interviewStages((jobRequirementDTO.getInterviewStages()==null || jobRequirementDTO.getInterviewStages().isEmpty())?"":String.join(", ", jobRequirementDTO.getInterviewStages()))
                .attachments((jobRequirementDTO.getAttachments()==null || jobRequirementDTO.getAttachments().isEmpty())?"":String.join(", ", jobRequirementDTO.getAttachments()))
                .internalNotes(jobRequirementDTO.getInternalNotes())
                .companyMaster(CompanyMaster.builder().id(jobRequirementDTO.getCompanyId()).build())
                .status(RequirementStatus.Awaiting_Review)
                .createdAt(new Date())
                .build();
        requirementDetailsRepository.save(requirementDetails);
        return new Response<>(HttpStatus.CREATED.value(), "Requirement saved successfully", requirementDetails);
    }

    @Override
    public Response<?> getAllRequirements(String companyId) {
        List<RequirementDetails> requirementDetailsList = requirementDetailsRepository.findAllByCompanyId(companyId);
        List<JobRequirementDTO> jobRequirementDTOList = requirementDetailsList.stream().filter(Objects::nonNull).map(this::convertToDTO).toList();
        return new Response<>(HttpStatus.OK.value(),  "All requirements found", jobRequirementDTOList);
    }

    @Override
    public Response<?> getRequirement(Long id) {
        RequirementDetails requirementDetails = requirementDetailsRepository.findById(id).orElse(null);
        if (requirementDetails == null) {
            return new Response<>(HttpStatus.NOT_FOUND.value(), "Requirement not found", null);
        }
        return new Response<>(HttpStatus.OK.value(), "Requirement found", convertToDTO(requirementDetails));
    }

    @Override
    public Response<?> getRecentPost(Long companyId) {
        RequirementDetails recentRequirement=requirementDetailsRepository.findRecentPostByCompanyId(companyId);
        if (recentRequirement == null) {
            return new Response<>(HttpStatus.NOT_FOUND.value(), "Recent requirement not found", null);
        }
        return new Response<>(HttpStatus.OK.value(), "Recent requirement found", convertToDTO(recentRequirement));
    }

    private JobRequirementDTO convertToDTO(RequirementDetails requirementDetails) {
        return JobRequirementDTO.builder()
                .roleTitle(requirementDetails.getRoleTitle())
                .department(requirementDetails.getHiringDepartments().getId())
                .location(requirementDetails.getJobLocation().getId())
                .employementTypes(requirementDetails.getEmployementTypes())
                .numberOfPositions(requirementDetails.getNumberOfPositions())
                .priority(requirementDetails.getPriority())
                .targetStartDate(requirementDetails.getTargetStartDate())
                .maxSalary(requirementDetails.getMaxSalary())
                .minSalary(requirementDetails.getMinSalary())
                .skills(requirementDetails.getSkills()==null || requirementDetails.getSkills().isEmpty() ? Collections.emptyList() : Arrays.asList(requirementDetails.getSkills().split(", ")))
                .niceToHaveSkills(requirementDetails.getNiceToHaveSkills())
                .responsibilities(requirementDetails.getResponsibilities())
                .interviewStages(requirementDetails.getInterviewStages()==null || requirementDetails.getInterviewStages().isEmpty() ? Collections.emptyList() : Arrays.asList(requirementDetails.getInterviewStages().split(", ")))
                .attachments(requirementDetails.getAttachments()==null || requirementDetails.getAttachments().isEmpty() ? Collections.emptyList() : Arrays.asList(requirementDetails.getAttachments().split(", ")))
                .internalNotes(requirementDetails.getInternalNotes())
                .companyId(requirementDetails.getCompanyMaster().getId())
                .departmentName(requirementDetails.getHiringDepartments().getName())
                .locationName(requirementDetails.getJobLocation().getLocationName())
                .status(requirementDetails.getStatus())
                .createdAt(requirementDetails.getCreatedAt())
                .id(requirementDetails.getId())
                .build();
    }
}
