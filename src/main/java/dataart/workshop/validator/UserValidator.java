package dataart.workshop.validator;

import dataart.workshop.exception.IncorrectPasswordException;
import dataart.workshop.exception.UserAlreadyExistException;
import dataart.workshop.exception.UserNotFoundException;
import dataart.workshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void validateUserDuplication(String email) {
        boolean alreadyExists = userRepository.existsByEmail(email);
        if (alreadyExists) {
            throw new UserAlreadyExistException("User with email %s already exists".formatted(email));
        }
    }

    public void validateUserPresence(String userId) {
        boolean exists = userRepository.existsByUserId(userId);
        if (!exists) {
            throw new UserNotFoundException("Can't find user with id: %s".formatted(userId));
        }
    }

    public void validatePasswordMatching(String oldPassword, String hashedPassword) {
        if (!passwordEncoder.matches(oldPassword, hashedPassword)) {
            throw new IncorrectPasswordException("Can't change password because provided password: %s does not match with User's password".formatted(oldPassword));
        }
    }
}
