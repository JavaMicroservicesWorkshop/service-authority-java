package dataart.workshop.controller;

import dataart.workshop.dto.v1.ChangePasswordRequest;
import dataart.workshop.dto.v1.CustomerDto;
import dataart.workshop.dto.v1.CustomerRegistrationRequest;
import dataart.workshop.dto.v1.CustomerRegistrationResponse;
import dataart.workshop.dto.v1.PaginatedCustomerDto;
import dataart.workshop.dto.v1.UpdateCustomerRequest;
import dataart.workshop.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public PaginatedCustomerDto getAll(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        return customerService.findAll(page, size);
    }

    @GetMapping("/{customerId}")
    public CustomerDto getByCustomerId(@PathVariable String customerId) {
        return customerService.findByCustomerId(customerId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerRegistrationResponse register(@RequestBody @Valid CustomerRegistrationRequest customerRegistrationRequest) {
        return customerService.register(customerRegistrationRequest);
    }

    @PutMapping("/{customerId}")
    public CustomerDto update(@PathVariable String customerId, @RequestBody @Valid UpdateCustomerRequest updateCustomerRequest) {
        return customerService.update(customerId, updateCustomerRequest);
    }

    @PatchMapping("/{customerId}")
    public void changePassword(@PathVariable String customerId, @RequestBody ChangePasswordRequest changePasswordRequest) {
        customerService.changePassword(customerId, changePasswordRequest);
    }

    @DeleteMapping("/{customerId}")
    public void delete(@PathVariable String customerId) {
        customerService.deleteByCustomerId(customerId);
    }
}
