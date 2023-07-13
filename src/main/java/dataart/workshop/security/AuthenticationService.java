package dataart.workshop.security;

import dataart.workshop.domain.UserAuthority;
import dataart.workshop.dto.JwtTokenDto;
import dataart.workshop.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final Logger logger = Logger.getLogger(AuthenticationService.class.getName());

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public JwtTokenDto authenticateAndGenerateToken(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        logger.info("User " + loginRequest.getEmail() + " is authenticated: " + SecurityContextHolder.getContext().getAuthentication().isAuthenticated());

        UserAuthority userAuthority = new UserAuthority(authentication.getName(), authentication.getAuthorities());
        return new JwtTokenDto(tokenService.generateToken(userAuthority));
    }
}
