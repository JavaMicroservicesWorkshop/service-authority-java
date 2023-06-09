package dataart.workshop.service;

import dataart.workshop.domain.UserDetailsBla;
import dataart.workshop.dto.v1.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public String authenticateAndGenerateToken(LoginRequest loginRequest) {
        var token = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);

        var userDetails = new UserDetailsBla(authentication.getAuthorities(), authentication.getName());
        return tokenService.generateToken(userDetails);
    }
}
