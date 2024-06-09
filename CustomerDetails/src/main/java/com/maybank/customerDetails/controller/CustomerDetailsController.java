package com.maybank.customerDetails.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maybank.customerDetails.entity.Customer;
import com.maybank.customerDetails.service.CustomerDetailsService;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api")
public class CustomerDetailsController {

	@Autowired
	CustomerDetailsService service;

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job job;

	@GetMapping("/customer")
	public List<Customer> getCusotmersSearch(@RequestParam String accountNo, @RequestParam String custId,
			@RequestParam String desc) {
		if ((custId.isEmpty() && desc.isEmpty()) && !accountNo.isEmpty()) {
			return getCusotmersByAcctNo(accountNo);
		} else if ((accountNo.isEmpty() && desc.isEmpty()) && !custId.isEmpty()) {
			return getCusotmersByCustId(custId);
		} else if ((accountNo.isEmpty() && custId.isEmpty()) && !desc.isEmpty()) {
			return getCusotmersByDesc(desc);
		} else if (accountNo.isEmpty() && !custId.isEmpty() && !desc.isEmpty()) {
			return getCustomerByCustIDandDesc(custId, desc);
		} else if (!accountNo.isEmpty() && custId.isEmpty() && !desc.isEmpty()) {
			return getCustomerByDescandAcctNo(desc, accountNo);
		} else if (!accountNo.isEmpty() && !custId.isEmpty() && desc.isEmpty()) {
			return getCustomerByCustIDandAcctNo(accountNo, custId);
		} else if (!accountNo.isEmpty() && !custId.isEmpty() && !desc.isEmpty()) {
			return getCustomerByCustIDandDescandAcctNo(accountNo, custId, desc);
		} else {
			return service.getCusotmers();
		}
	}

	@PutMapping("/customer")
	public Optional<Customer> updateCustomer(@RequestBody Customer customer) {
		service.updateCustomer(customer);
		return service.getCustomer(customer.getId());
	}

	@GetMapping("/startBatch")
	public BatchStatus startBatch() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
			JobParametersInvalidException, JobRestartException {
		JobParameters jobParameter = new JobParametersBuilder().addLong("startAt", System.currentTimeMillis())
				.toJobParameters();
		JobExecution run = jobLauncher.run(job, jobParameter);
		return run.getStatus();
	}

	private List<Customer> getCustomerByCustIDandDescandAcctNo(String accountNo, String custId, String desc) {
		return service.getCustomerByCustIDandDescandAcctNo(accountNo, custId, desc);
	}

	private List<Customer> getCustomerByCustIDandAcctNo(String accountNo, String custId) {
		return service.getCustomerByCustIDandAcctNo(accountNo, custId);
	}

	private List<Customer> getCustomerByDescandAcctNo(String desc, String accountNo) {
		return service.getCustomerByDescandAcctNo(desc, accountNo);
	}

	private List<Customer> getCustomerByCustIDandDesc(String custId, String desc) {
		return service.getCustomerByCustIDandDesc(custId, desc);
	}

	public List<Customer> getCusotmers() {
		return service.getCusotmers();
	}

	public List<Customer> getCusotmersByAcctNo(String accountNo) {
		return service.getCusotmersByAcctNo(accountNo);
	}

	public List<Customer> getCusotmersByCustId(String custId) {
		return service.getCusotmersByCustId(custId);
	}

	public List<Customer> getCusotmersByDesc(String desc) {
		return service.getCusotmersByDesc(desc);
	}
}
