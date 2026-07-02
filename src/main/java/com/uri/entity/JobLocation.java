package com.uri.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Entity
@Table(name = "job_locations")
@Getter
@Service
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String locationName;
}
