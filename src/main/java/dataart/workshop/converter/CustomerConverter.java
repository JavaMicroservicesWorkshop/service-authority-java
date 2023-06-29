package dataart.workshop.converter;

import dataart.workshop.domain.Customer;
import dataart.workshop.dto.v1.CustomerDto;
import dataart.workshop.dto.v1.CustomerRegistrationRequest;
import dataart.workshop.dto.v1.PaginatedCustomerDto;
import dataart.workshop.dto.v1.UpdateCustomerRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerConverter {

    public CustomerDto toCustomerDto(Customer customer) {
        CustomerDto dto = new CustomerDto();

        dto.setId(customer.getCustomerId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setEmail(customer.getEmail());
        dto.setPhoneNumber(customer.getPhoneNumber());

        return dto;
    }

    public PaginatedCustomerDto toPaginatedDto(Page<Customer> customersPage) {
        PaginatedCustomerDto dto = new PaginatedCustomerDto();

        List<CustomerDto> customerDtoList = customersPage.get()
                .map(this::toCustomerDto)
                .toList();

        dto.setData(customerDtoList);
        dto.setPage(customersPage.getNumber());
        dto.setTotalPages(customersPage.getTotalPages());
        dto.setSize(customersPage.getNumberOfElements());

        return dto;
    }

    public Customer toCustomer(UpdateCustomerRequest request) {
        Customer customer = new Customer();

        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());

        return customer;
    }

    public Customer toCustomer(CustomerRegistrationRequest customerRegistrationRequest, String customerId, Customer.Role role) {
        Customer customer = new Customer();

        customer.setCustomerId(customerId);

        customer.setEmail(customerRegistrationRequest.getEmail());
        customer.setPassword(customerRegistrationRequest.getPassword());

        customer.setFirstName(customerRegistrationRequest.getFirstName());
        customer.setLastName(customerRegistrationRequest.getLastName());

        customer.setPhoneNumber(customerRegistrationRequest.getPhoneNumber());
        customer.setRole(role);

        return customer;
    }
}
