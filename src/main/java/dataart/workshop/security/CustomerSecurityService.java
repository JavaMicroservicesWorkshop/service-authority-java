package dataart.workshop.security;

import dataart.workshop.domain.Customer;
import dataart.workshop.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerSecurityService {

    private final CustomerRepository customerRepository;

    public boolean isTheSameUser(Authentication authentication, String customerId) {

        return customerRepository.findByEmail(authentication.getName())
                .map(Customer::getCustomerId)
                .map(id -> id.equals(customerId))
                .orElse(Boolean.FALSE);
    }
}
