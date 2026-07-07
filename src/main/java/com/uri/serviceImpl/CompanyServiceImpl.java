package com.uri.serviceImpl;

import com.uri.dto.Response;
import com.uri.entity.CompanyMaster;
import com.uri.repository.CompanyMasterRepository;
import com.uri.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyMasterRepository companyMasterRepository;

    @Override
    public Response<?> getAllCompanies() {
        List<CompanyMaster> companyMasters = companyMasterRepository.findAll();
        return new Response<>(HttpStatus.OK.value(),"Success",companyMasters);
    }
}
