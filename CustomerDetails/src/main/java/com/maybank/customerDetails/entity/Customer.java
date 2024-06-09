package com.maybank.customerDetails.entity;

import java.sql.Time;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private long id;

	@Column(name = "account_number")
	String accountNumber;

	@Column(name = "trx_amount")
	float trxAmount;

	@Column(name = "description")
	String description;

	@Column(name = "trx_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	Date trxDate;

	@Column(name = "trx_time")
	Time trxTime;

	@Column(name = "customer_id")
	String customerId;

	public Customer() {
	}

	public Customer(long id, String accountNumber, float trxAmount, String description, Date trxDate, Time trxTime,
			String customerId) {
		super();
		this.id = id;
		this.accountNumber = accountNumber;
		this.trxAmount = trxAmount;
		this.description = description;
		this.trxDate = trxDate;
		this.trxTime = trxTime;
		this.customerId = customerId;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", accountNumber=" + accountNumber + ", trxAmount=" + trxAmount + ", description="
				+ description + ", trxDate=" + trxDate + ", trxTime=" + trxTime + ", customerId=" + customerId + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public float getTrxAmount() {
		return trxAmount;
	}

	public void setTrxAmount(float trxAmount) {
		this.trxAmount = trxAmount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTrxDate() {
		return trxDate;
	}

	public void setTrxDate(Date trxDate) {
		this.trxDate = trxDate;
	}

	public Time getTrxTime() {
		return trxTime;
	}

	public void setTrxTime(Time trxTime) {
		this.trxTime = trxTime;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

}
