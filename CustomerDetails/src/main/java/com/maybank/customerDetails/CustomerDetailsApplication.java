package com.maybank.customerDetails;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class })
@EntityScan(basePackages = { "com.maybank.customerDetails.entity" })
public class CustomerDetailsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerDetailsApplication.class, args);
	}

}
