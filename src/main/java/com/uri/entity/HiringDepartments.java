package com.uri.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hiring_departments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HiringDepartments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Boolean isActive;
}

