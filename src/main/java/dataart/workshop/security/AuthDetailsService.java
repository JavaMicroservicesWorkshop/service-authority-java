package dataart.workshop.security;

import dataart.workshop.domain.AuthUserDetails;
import dataart.workshop.domain.User;
import dataart.workshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthDetailsService implements UserDetailsService {

    private static final String USER_NOT_FOUND = "Can't find user by email: %s";

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND.formatted(email)));

        return new AuthUserDetails(user);
    }
}
