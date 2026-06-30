package com.uri.repository;

import com.uri.entity.CompanyMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyMasterRepository extends JpaRepository<CompanyMaster, Long> {
    CompanyMaster findByCompanyNameAndCompanyEmail(String trim, String email);

    CompanyMaster findByCompanyNameOrCompanyEmail(String trim, String email);
}
