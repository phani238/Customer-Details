package com.maybank.customerDetails.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maybank.customerDetails.entity.Customer;

@Repository
public interface CustomerDetailsRepository extends JpaRepository<Customer, Long> {

	List<Customer> findByAccountNumber(String accountNumber);

	List<Customer> findByCustomerId(String customerId);

	List<Customer> findByDescription(String description);

	List<Customer> findByCustomerIdAndAccountNumberAndDescription(String customerId, String accountNumber,
			String description);

	List<Customer> findByCustomerIdAndAccountNumber(String customerId, String accountNumber);

	List<Customer> findByAccountNumberAndDescription(String accountNumber, String description);

	List<Customer> findByCustomerIdAndDescription(String customerId, String description);
}
