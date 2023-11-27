package br.com.ist.job.security.controller;

import br.com.ist.job.security.dto.JwtAuthResponseDTO;
import br.com.ist.job.security.dto.SigninRequestDTO;
import br.com.ist.job.security.service.AuthService;
import br.com.ist.job.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(Constants.API_VERSION + "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Login USER
     *
     * @param request
     * @return
     */
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthResponseDTO> signin(@RequestBody SigninRequestDTO request) {
        return ResponseEntity.ok(authService.signin(request));
    }
}
