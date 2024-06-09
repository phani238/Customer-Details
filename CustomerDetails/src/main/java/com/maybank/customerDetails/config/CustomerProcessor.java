package com.maybank.customerDetails.config;

import org.springframework.batch.item.ItemProcessor;

import com.maybank.customerDetails.entity.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerProcessor implements ItemProcessor<Customer, Customer> {

	@Override
	public Customer process(Customer item) throws Exception {
		return item;
	}

}
