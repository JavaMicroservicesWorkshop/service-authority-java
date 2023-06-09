package dataart.workshop.service;

import dataart.workshop.converter.UserConverter;
import dataart.workshop.domain.User;
import dataart.workshop.dto.v1.RegistrationRequest;
import dataart.workshop.exception.UserAlreadyExistException;
import dataart.workshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public void register(RegistrationRequest registrationRequest) {
        Optional<User> existingUserOptional = userRepository.findByUsername(registrationRequest.getUsername());

        if (existingUserOptional.isPresent()) {
            throw new UserAlreadyExistException("Registration failed. User with email %s already exists".formatted(registrationRequest.getUsername()));
        }

        User registeredUser = userConverter.toUser(registrationRequest);
        registeredUser.setPassword(passwordEncoder.encode(registeredUser.getPassword()));
        userRepository.save(registeredUser);
    }
}
