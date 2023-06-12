package dataart.workshop.security;

import dataart.workshop.domain.UserAuthority;
import dataart.workshop.dto.v1.JwtTokenDto;
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

    public JwtTokenDto authenticateAndGenerateToken(LoginRequest loginRequest) {
        var token = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);

        var userDetails = new UserAuthority(authentication.getName(), authentication.getAuthorities());
        return new JwtTokenDto(tokenService.generateToken(userDetails));
    }
}
