package com.uri.serviceImpl;

import com.uri.dto.*;
import com.uri.entity.CompanyMaster;
import com.uri.entity.User;
import com.uri.enums.Role;
import com.uri.repository.CompanyMasterRepository;
import com.uri.repository.UserRepository;
import com.uri.security.JwtTokenProvider;
import com.uri.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final CompanyMasterRepository companyMasterRepository;

    @Override
    public Response<?> registerTalent(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return new Response<>(HttpStatus.BAD_REQUEST.value(), "User already exists", null);
        }

        User user = new User();
        user.setName(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Role.ROLE_TALENT);
        user.setPhone(request.getPhone());
        user.setLogoUrl(request.getLogoUrl());
        userRepository.save(user);
        return new Response<>(HttpStatus.OK.value(), "User registered successfully", null);
    }

    @Override
    public Response<?> registerCompany(CompanyRegistrationDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return new Response<>(HttpStatus.BAD_REQUEST.value(), "Company already exists", null);
        }
        CompanyMaster existingCompany = companyMasterRepository.findByCompanyNameOrCompanyEmail(request.getUsername().trim(), request.getEmail());
        if (existingCompany != null) {
            return new Response<>(HttpStatus.BAD_REQUEST.value(), "Company already exists", null);
        }
        CompanyMaster companyMaster = convertToCompanyDto(request);
        CompanyMaster savedCompany = companyMasterRepository.save(companyMaster);
        User user = new User();
        user.setName(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Role.ROLE_COMPANY);
        user.setPhone(request.getPhone());
        user.setCompanyMaster(savedCompany);
        user.setLogoUrl(request.getLogoUrl());
        userRepository.save(user);
        return new Response<>(HttpStatus.OK.value(), "Company registered successfully", null);
    }

    private CompanyMaster convertToCompanyDto(CompanyRegistrationDto request) {
        CompanyMaster companyMaster = new CompanyMaster();
        companyMaster.setCompanyAddress(request.getAddress());
        companyMaster.setCompanyName(request.getUsername());
        companyMaster.setCompanyEmail(request.getEmail());
        companyMaster.setCompanyPhone(request.getPhone());
        companyMaster.setCompanyWebsite(request.getWebsite());
        companyMaster.setLogoUrl(request.getLogoUrl());
        companyMaster.setIsActive(true);
        return companyMaster;
    }

    @Override
    public Response<?> login(LoginRequest request) {
        try {
            User user = userRepository.findByEmail(request.getUsername());
            if (user == null) {
                return new Response<>(HttpStatus.BAD_REQUEST.value(), "User not found", null);
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String jwt = jwtTokenProvider.generateToken(userDetails);
            JwtResponse response=JwtResponse.builder()
                    .id(user.getId())
                    .token(jwt)
                    .userName(user.getName())
                    .role(user.getRoles().name())
                    .email(user.getEmail())
                    .phone(user.getPhone())
                    .logoUrl(user.getLogoUrl())
                    .companyData(user.getCompanyMaster()).build();

            return new Response<>(HttpStatus.OK.value(), "Login successfully", response);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response<>(HttpStatus.BAD_REQUEST.value(), "Bad credentials.", null);
        }
    }


}
