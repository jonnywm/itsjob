package br.com.ist.job.security.service.impl;

import br.com.ist.job.security.dto.JwtAuthResponseDTO;
import br.com.ist.job.security.dto.SignUpRequestDTO;
import br.com.ist.job.security.dto.SigninRequestDTO;
import br.com.ist.job.security.model.User;
import br.com.ist.job.security.repository.UserRepository;
import br.com.ist.job.security.service.AuthService;
import br.com.ist.job.security.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthResponseDTO signup(SignUpRequestDTO request) {
        var user = generateUser(request);
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthResponseDTO.builder().token(jwt).build();
    }

    private User generateUser(SignUpRequestDTO request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())).build();
        return user;
    }

    @Override
    public JwtAuthResponseDTO signin(SigninRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        return JwtAuthResponseDTO.builder()
                .token(jwt)
                .roles(
                        Objects.nonNull(user.getAuthorities())
                        ? user.getAuthorities().stream().map(authority -> authority.getAuthority()).collect(Collectors.toList())
                        : Collections.emptyList()
                )
                .build();
    }
}
