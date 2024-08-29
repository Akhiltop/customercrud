package com.ecrud.employee.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ecrud.employee.dto.ExternalCustomerDTO;
import com.ecrud.employee.entity.Customer;
import com.ecrud.employee.repository.CustomerRepository;

import jakarta.persistence.criteria.Predicate;

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

    
    public Page<Customer> getCustomers(String searchField,String search, String sortField, String sortDirection, int page, int size) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Specification<Customer> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (StringUtils.hasText(search)) {
                Predicate searchPredicate = criteriaBuilder.like(root.get(searchField), "%" + search + "%");
                predicates.add(criteriaBuilder.or(searchPredicate));
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        
        return customerRepository.findAll(spec, pageable);
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
