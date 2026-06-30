package com.uri.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Entity
@Table(name = "job_locations")
@Getter
@Service
public class JobLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String locationName;
}
