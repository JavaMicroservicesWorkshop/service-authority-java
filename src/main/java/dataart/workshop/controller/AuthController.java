package dataart.workshop.controller;

import dataart.workshop.dto.v1.JwtTokenDto;
import dataart.workshop.dto.v1.LoginRequest;
import dataart.workshop.security.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public JwtTokenDto login(@RequestBody @Valid LoginRequest loginRequest) throws AuthenticationException {
        return authenticationService.authenticateAndGenerateToken(loginRequest);
    }

}
