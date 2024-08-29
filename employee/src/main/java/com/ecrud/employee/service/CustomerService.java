package com.ecrud.employee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecrud.employee.dto.ExternalCustomerDTO;
import com.ecrud.employee.entity.Customer;
import com.ecrud.employee.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow();
        customer.setUuid(customerDetails.getUuid());
        customer.setFirstName(customerDetails.getFirstName());
        customer.setLastName(customerDetails.getLastName());
        customer.setStreet(customerDetails.getStreet());
        customer.setAddress(customerDetails.getAddress());
        customer.setCity(customerDetails.getCity());
        customer.setState(customerDetails.getState());
        customer.setEmail(customerDetails.getEmail());
        customer.setPhone(customerDetails.getPhone());

        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow();
    }

    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow();
        customerRepository.delete(customer);
    }
    
    public void syncCustomersFromExternalData(List<ExternalCustomerDTO> externalCustomers) {
        for (ExternalCustomerDTO externalCustomer : externalCustomers) {
            // Check if the customer with the given UUID already exists
            Customer existingCustomer = customerRepository.findByUuid(externalCustomer.getUuid()).orElse(null);

            if (existingCustomer != null) {
                // If the customer exists, update the details
                updateCustomer(existingCustomer, externalCustomer);
                customerRepository.save(existingCustomer);
            } else {
                // If the customer does not exist, create a new one
                Customer newCustomer = mapToCustomer(externalCustomer);
                customerRepository.save(newCustomer);
            }
        }
    }

    private void updateCustomer(Customer existingCustomer, ExternalCustomerDTO externalCustomer) {
        existingCustomer.setFirstName(externalCustomer.getFirst_name());
        existingCustomer.setLastName(externalCustomer.getLast_name());
        existingCustomer.setStreet(externalCustomer.getStreet());
        existingCustomer.setAddress(externalCustomer.getAddress());
        existingCustomer.setCity(externalCustomer.getCity());
        existingCustomer.setState(externalCustomer.getState());
        existingCustomer.setEmail(externalCustomer.getEmail());
        existingCustomer.setPhone(externalCustomer.getPhone());
    }

    private Customer mapToCustomer(ExternalCustomerDTO externalCustomer) {
        Customer customer = new Customer();
        customer.setUuid(externalCustomer.getUuid());
        customer.setFirstName(externalCustomer.getFirst_name());
        customer.setLastName(externalCustomer.getLast_name());
        customer.setStreet(externalCustomer.getStreet());
        customer.setAddress(externalCustomer.getAddress());
        customer.setCity(externalCustomer.getCity());
        customer.setState(externalCustomer.getState());
        customer.setEmail(externalCustomer.getEmail());
        customer.setPhone(externalCustomer.getPhone());
        return customer;
    }

    
}
