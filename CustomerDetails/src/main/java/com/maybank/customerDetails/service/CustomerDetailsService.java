package com.maybank.customerDetails.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.maybank.customerDetails.entity.Customer;
import com.maybank.customerDetails.repository.CustomerDetailsRepository;

@Service
public class CustomerDetailsService {

	@Autowired
	CustomerDetailsRepository repo;

	@Autowired
	private ResourceLoader resourceLoader;

	public List<Customer> getCusotmers() {
		return repo.findAll();
	}

	public List<Customer> getCusotmersByAcctNo(String accountNumber) {
		return repo.findByAccountNumber(accountNumber);
	}

	public List<Customer> getCusotmersByCustId(String customerId) {
		return repo.findByCustomerId(customerId);
	}

	public List<Customer> getCusotmersByDesc(String description) {
		return repo.findByDescription(description);
	}

	public List<Customer> getCustomerByCustIDandDescandAcctNo(String accountNumber, String customerId,
			String description) {
		return repo.findByCustomerIdAndAccountNumberAndDescription(customerId, accountNumber, description);
	}

	public List<Customer> getCustomerByCustIDandAcctNo(String accountNumber, String customerId) {
		return repo.findByCustomerIdAndAccountNumber(customerId, accountNumber);
	}

	public List<Customer> getCustomerByDescandAcctNo(String description, String accountNumber) {
		return repo.findByAccountNumberAndDescription(accountNumber, description);
	}

	public List<Customer> getCustomerByCustIDandDesc(String customerId, String description) {
		return repo.findByCustomerIdAndDescription(customerId, description);
	}

	public void updateCustomer(Customer customer) {
		repo.save(customer);
	}

	public Optional<Customer> getCustomer(long id) {
		return repo.findById(id);
	}

}
