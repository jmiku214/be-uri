package com.uri.repository;

import com.uri.entity.JobLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobLocationRepository extends JpaRepository<JobLocation, Long> {
}
