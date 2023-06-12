package dataart.workshop.repository;

import dataart.workshop.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Customer> findByCustomerId(String customerId);

    boolean existsByCustomerId(String customerId);

    void deleteByCustomerId(String customerId);
}
