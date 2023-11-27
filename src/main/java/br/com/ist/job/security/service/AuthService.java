package br.com.ist.job.security.service;

import br.com.ist.job.security.dto.JwtAuthResponseDTO;
import br.com.ist.job.security.dto.SignUpRequestDTO;
import br.com.ist.job.security.dto.SigninRequestDTO;

public interface AuthService {
    
    JwtAuthResponseDTO signup(SignUpRequestDTO request);

    JwtAuthResponseDTO signin(SigninRequestDTO request);
}
