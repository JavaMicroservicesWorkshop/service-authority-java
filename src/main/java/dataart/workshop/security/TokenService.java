package dataart.workshop.security;

import dataart.workshop.domain.UserAuthority;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenService {

    private static final Logger logger = Logger.getLogger(TokenService.class.getName());
    private final JwtEncoder encoder;

    public String generateToken(UserAuthority userAuthority) {
        Instant now = Instant.now();

        String scope = userAuthority.authorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("DataArt")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(userAuthority.username())
                .claim("scope", scope)
                .build();

        logger.info("JWT token generated");

        return encoder
                .encode(JwtEncoderParameters.from(claims))
                .getTokenValue();
    }
}
