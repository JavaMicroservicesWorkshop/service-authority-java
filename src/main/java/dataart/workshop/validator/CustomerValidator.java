package dataart.workshop.validator;

import dataart.workshop.exception.CustomerAlreadyExistException;
import dataart.workshop.exception.CustomerNotFoundException;
import dataart.workshop.exception.IncorrectPasswordException;
import dataart.workshop.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerValidator {

    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    public void validateCustomerDuplication(String email) {
        boolean alreadyExists = customerRepository.existsByEmail(email);
        if (alreadyExists) {
            throw new CustomerAlreadyExistException("User with email %s already exists".formatted(email));
        }
    }

    public void validateCustomerPresence(String customerId) {
        boolean exists = customerRepository.existsByCustomerId(customerId);
        if (!exists) {
            throw new CustomerNotFoundException("Can't find customer with id: %s".formatted(customerId));
        }
    }

    public void validatePasswordMatching(String oldPassword, String hashedPassword) {
        if (!passwordEncoder.matches(oldPassword, hashedPassword)) {
            throw new IncorrectPasswordException("Can't change password because provided password: %s does not match with User's password".formatted(oldPassword));
        }
    }
}
