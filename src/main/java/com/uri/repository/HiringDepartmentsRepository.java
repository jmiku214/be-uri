package com.uri.repository;

import com.uri.entity.HiringDepartments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HiringDepartmentsRepository extends JpaRepository<HiringDepartments, Long> {
}
