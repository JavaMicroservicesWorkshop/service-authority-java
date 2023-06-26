package dataart.workshop.service;

import dataart.workshop.converter.CustomerConverter;
import dataart.workshop.domain.Customer;
import dataart.workshop.dto.v1.ChangePasswordRequest;
import dataart.workshop.dto.v1.CustomerDto;
import dataart.workshop.dto.v1.CustomerRegistrationRequest;
import dataart.workshop.dto.v1.CustomerRegistrationResponse;
import dataart.workshop.dto.v1.PaginatedCustomerDto;
import dataart.workshop.dto.v1.UpdateCustomerRequest;
import dataart.workshop.exception.CustomerNotFoundException;
import dataart.workshop.repository.CustomerRepository;
import dataart.workshop.utils.PageUtils;
import dataart.workshop.validator.CustomerValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private static final String CANT_FIND_CUSTOMER_ERROR = "Can't find customer by id: %s";

    private final PageUtils pageUtils;
    private final CustomerValidator validator;
    private final CustomerConverter customerConverter;
    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    public PaginatedCustomerDto findAll(Integer page, Integer size) {
        Pageable pageable = pageUtils.adjustPageable(page, size);
        Page<Customer> customersPage = customerRepository.findAll(pageable);

        return customerConverter.toPaginatedDto(customersPage);
    }

    public CustomerDto findByCustomerId(String customerId) {
        Customer customer = findOrElseThrow(customerId);

        return customerConverter.toCustomerDto(customer);
    }

    public CustomerRegistrationResponse register(CustomerRegistrationRequest customerRegistrationRequest) {
        validator.validateCustomerDuplication(customerRegistrationRequest.getEmail());
        String customerId = UUID.randomUUID().toString();

        Customer registeredCustomer = customerConverter.toCustomer(customerRegistrationRequest, customerId);
        registeredCustomer.setPassword(passwordEncoder.encode(registeredCustomer.getPassword()));

        customerRepository.save(registeredCustomer);
        return new CustomerRegistrationResponse(customerId);
    }

    @Transactional
    public CustomerDto update(String customerId, UpdateCustomerRequest updateCustomerRequest) {
        Customer existingCustomer = findOrElseThrow(customerId);

        Customer updatedCustomer = customerConverter.toCustomer(updateCustomerRequest);
        updateCustomer(existingCustomer, updatedCustomer);

        return customerConverter.toCustomerDto(existingCustomer);
    }

    private void updateCustomer(Customer destination, Customer source) {
        destination.setFirstName(source.getFirstName());
        destination.setLastName(source.getLastName());
        destination.setEmail(source.getEmail());
        destination.setPhoneNumber(source.getPhoneNumber());
    }

    @Transactional
    public void changePassword(String customerId, ChangePasswordRequest changePasswordRequest) {
        Customer customer = findOrElseThrow(customerId);

        validator.validatePasswordMatching(changePasswordRequest.getOldPassword(), customer.getPassword());

        customer.setPassword(passwordEncoder.encode(changePasswordRequest.getOldPassword()));
    }

    @Transactional
    public void deleteByCustomerId(String customerId) {
        validator.validateCustomerPresence(customerId);

        customerRepository.deleteByCustomerId(customerId);
    }

    private Customer findOrElseThrow(String customerId) {
        return customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(CANT_FIND_CUSTOMER_ERROR.formatted(customerId)));
    }
}
