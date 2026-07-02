package com.uri.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "company_master")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String companyName;

    @Column(name = "address")
    private String companyAddress;

    @Column(name = "phone")
    private String companyPhone;

    @Column(name = "email")
    private String companyEmail;

    @Column(name = "website_url")
    private String companyWebsite;

    @Column(name = "logo")
    private String logoUrl;

    @Column(name = "created_at")
    private Date createdDate;

    @Column(name = "is_active")
    private Boolean isActive;


}
