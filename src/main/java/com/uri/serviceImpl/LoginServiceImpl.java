package com.uri.serviceImpl;

import com.uri.dto.JwtResponse;
import com.uri.dto.LoginRequest;
import com.uri.dto.RegisterRequest;
import com.uri.dto.Response;
import com.uri.entity.User;
import com.uri.enums.Role;
import com.uri.repository.UserRepository;
import com.uri.security.JwtTokenProvider;
import com.uri.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Response<?> registerTalent(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return new Response<>(HttpStatus.BAD_REQUEST.value(), "User already exists", null);
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Role.ROLE_TALENT);
        user.setPhone(request.getPhone());
        userRepository.save(user);
        return new Response<>(HttpStatus.OK.value(), "User registered successfully", null);
    }

    @Override
    public Response<?> registerCompany(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return new Response<>(HttpStatus.BAD_REQUEST.value(), "Company already exists", null);
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Role.ROLE_COMPANY);
        user.setPhone(request.getPhone());
        userRepository.save(user);
        return new Response<>(HttpStatus.OK.value(), "Company registered successfully", null);
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

            JwtResponse response = new JwtResponse(
                    user.getId(), jwt, user.getUsername(),
                    user.getRoles().name(), user.getEmail(), user.getPhone()
            );

            return new Response<>(HttpStatus.OK.value(), "Login successfully", response);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response<>(HttpStatus.BAD_REQUEST.value(), "Bad credentials.", null);
        }
    }


}
