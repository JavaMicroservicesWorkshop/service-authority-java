package dataart.workshop.eventlistener;

import dataart.workshop.domain.Customer;
import dataart.workshop.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OnApplicationStartupListener {

    private static final String ADMIN_FIRST_NAME = "STORE";
    private static final String ADMIN_LAST_NAME = "OWNER";

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void saveAdminUser() {
        Customer customer = new Customer();

        customer.setCustomerId(UUID.randomUUID().toString());
        customer.setFirstName(ADMIN_FIRST_NAME);
        customer.setLastName(ADMIN_LAST_NAME);
        customer.setEmail(adminEmail);
        customer.setPassword(passwordEncoder.encode(adminPassword));
        customer.setRole(Customer.Role.ADMIN);

        customerRepository.save(customer);
    }
}
