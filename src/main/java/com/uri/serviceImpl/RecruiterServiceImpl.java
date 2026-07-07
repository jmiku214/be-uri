package com.uri.serviceImpl;

import com.uri.dto.JwtResponse;
import com.uri.dto.RegisterRequest;
import com.uri.dto.Response;
import com.uri.entity.User;
import com.uri.enums.Role;
import com.uri.repository.UserRepository;
import com.uri.service.RecruiterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruiterServiceImpl implements RecruiterService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Response<?> getAllRecruiters() {
        List<String> types= List.of(Role.ROLE_RECRUITER.name());
        List<User> users=userRepository.findAllByRoleTypeIn(types);
        if(users.isEmpty()){
            return new Response<>(HttpStatus.OK.value(), "No users found", users);
        }
        List<JwtResponse> listDto=new ArrayList<>();
        users.forEach(user->{
            JwtResponse response=JwtResponse.builder()
                    .id(user.getId())
                    .userName(user.getName())
                    .email(user.getEmail())
                    .phone(user.getPhone()).build();
            listDto.add(response);
        });
        return new Response<>(HttpStatus.OK.value(), "All users found", listDto);
    }

    @Override
    public Response<?> createRecruiter(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return new Response<>(HttpStatus.BAD_REQUEST.value(), "Recruiter already exists", null);
        }
        User user = new User();
        user.setName(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Role.ROLE_RECRUITER);
        user.setName(request.getUsername());
        user.setPhone(request.getPhone());
        user.setLogoUrl(request.getLogoUrl());
        userRepository.save(user);
        return new Response<>(HttpStatus.OK.value(), "Recruiter added successfully", null);
    }
}
