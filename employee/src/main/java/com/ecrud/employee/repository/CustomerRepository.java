package com.ecrud.employee.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ecrud.employee.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>,JpaSpecificationExecutor<Customer>  {

	Optional<Customer> findByUuid(String uuid);
	
	
}
