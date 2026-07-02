package com.uri.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.uri.enums.Priority;
import com.uri.enums.RequirementStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobRequirementDTO {


    private String roleTitle;
    private Long department;
    private Long location;
    private String employementTypes;     // Note: keeping original spelling as per your JSON
    private Integer numberOfPositions;
    private Priority priority;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date targetStartDate;
    private Double maxSalary;
    private Double minSalary;

    private List<String> skills;
    private String niceToHaveSkills;
    private String responsibilities;

    private List<String> interviewStages;
    private List<String> attachments;
    private String internalNotes;
    private Long companyId;
    private String departmentName;
    private String locationName;
    private RequirementStatus status;
    private Date createdAt;
    private Long id;
}
