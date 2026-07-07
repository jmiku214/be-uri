package com.uri.dto;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminRequirementsDto {
    private List<JobRequirementDTO> newRequirements;
    private List<JobRequirementDTO> assignedRequirements;
    private List<JobRequirementDTO> inProgressRequirements;
    private List<JobRequirementDTO> awaitingReviewRequirements;
    private List<JobRequirementDTO> closedRequirements;
    private List<JobRequirementDTO> openRequirements;


}
