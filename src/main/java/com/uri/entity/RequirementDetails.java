package com.uri.entity;

import com.uri.enums.Priority;
import com.uri.enums.RequirementStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "requirement_details")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequirementDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roleTitle;
    private String roleDescription;

    @Lob
    @Column(name = "responsibilities", columnDefinition = "LONGTEXT")
    private String responsibilities;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(name = "job_skills")
    private String skills;

    private Date targetStartDate;
    private String niceToHaveSkills;
    private Double minSalary;
    private Double maxSalary;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "location_id")
    private JobLocation jobLocation;

    @Column(name = "interview_stages")
    private String interviewStages;

    @Lob
    @Column(name = "internal_notes", columnDefinition = "TEXT")
    private String internalNotes;
    private String employementTypes;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "hiring_department_id")
    private HiringDepartments hiringDepartments;

    @Column(name = "job_attachments")
    private String attachments;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "company_id")
    private CompanyMaster companyMaster;

    @Column(name = "number_of_positions")
    private Integer numberOfPositions;

    @Column(name = "created_at")
    private Date createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "requirement_status")
    private RequirementStatus status;
}
