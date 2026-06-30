package com.uri.serviceImpl;

import com.uri.dto.Response;
import com.uri.entity.HiringDepartments;
import com.uri.entity.JobLocation;
import com.uri.repository.HiringDepartmentsRepository;
import com.uri.repository.JobLocationRepository;
import com.uri.service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MasterServiceImpl implements MasterService {

    @Autowired
    private HiringDepartmentsRepository hiringDepartmentsRepository;

    @Autowired
    private JobLocationRepository jobLocationRepository;

    @Override
    public Response<?> getAllHiringDepartments() {
        List<HiringDepartments> hiringDepartments = hiringDepartmentsRepository.findAll();
        return new Response<>(HttpStatus.OK.value(),"Success",hiringDepartments);
    }

    @Override
    public Response<?> getAllJobLocations() {
        List<JobLocation> jobLocations = jobLocationRepository.findAll();
        return new Response<>(HttpStatus.OK.value(),"Success",jobLocations);
    }
}
