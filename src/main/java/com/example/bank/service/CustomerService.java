package com.example.bank.service;

import com.example.bank.model.Customer;

import java.util.List;

public interface CustomerService {
	
	public Customer createNewCustomer(Customer customer);
	public Customer getcustomerInfo(int accountNo);
	Customer getCustomerById(int customerId);
	List<Customer> getAllCustomers();
	public Customer updateCustomer(int custId, Customer customer);
	//public Integer updateCustomerForPhoneNumber(int custId, Customer customer);
	public void deleteCustomer(int custId);

}
