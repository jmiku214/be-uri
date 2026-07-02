package com.uri.repository;

import com.uri.entity.RequirementDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequirementDetailsRepository extends JpaRepository<RequirementDetails, Long> {

    @Query(value = "select * from requirement_details where role_title = :title and company_id = :companyId", nativeQuery = true)
    RequirementDetails findByJobTitleAndCompanyId(@Param("title") String title, @Param("companyId") Long companyId);

    @Query(value = "select * from requirement_details where company_id=?1 order by created_at desc", nativeQuery = true)
    List<RequirementDetails> findAllByCompanyId(String companyId);

    @Query(value = "select * from requirement_details where company_id=:companyId order by created_at desc limit 1", nativeQuery = true)
    RequirementDetails findRecentPostByCompanyId(@Param("companyId") Long companyId);
}

