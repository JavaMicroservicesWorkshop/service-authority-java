package dataart.workshop.security;

import dataart.workshop.domain.Customer;
import dataart.workshop.domain.SecurityCustomer;
import dataart.workshop.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtUserDetailsService implements UserDetailsService {

    private static final String CUSTOMER_NOT_FOUND = "Can't find customer by email: %s";

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(CUSTOMER_NOT_FOUND.formatted(email)));

        return new SecurityCustomer(customer);
    }
}
