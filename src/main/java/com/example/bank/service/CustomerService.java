package com.example.bank.service;

import com.example.bank.model.Customer;

import java.util.List;

/**
 * author: amitachaudhari9062@gmail.com
 * This is a Service Interface class for Customer.
 **/
public interface CustomerService {

    public Customer createNewCustomer(Customer customer);

    public Customer getcustomerInfo(int accountNo);

    Customer getCustomerById(int customerId);

    List<Customer> getAllCustomers();

    public Customer updateCustomer(int custId, Customer customer);

    public void deleteCustomer(int custId);

}
