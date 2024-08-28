package com.ecrud.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecrud.employee.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
