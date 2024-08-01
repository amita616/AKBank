package com.example.bank.service;


import com.example.bank.exception.CustomerNotFoundException;
import com.example.bank.model.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.bank.dao.CustomerRepo;
import com.example.bank.model.Customer;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * author: amitachaudhari9062@gmail.com
 * This is a Customer Implementation class for Customer.
 * Using PasswordEncoder for saving password in an encoded form in database.
 * When creating a new customer an address will be created automatically.
 **/
@Service
public class CustomerServiceImpl implements CustomerService {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerRepo customerRepository;
    @Autowired
    private AccountsService accountsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Customer createNewCustomer(Customer customer) {
        if (customer.getPassword() != null) {
            customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        }
        if (customer.getAddresses() != null) {
            for (Address address : customer.getAddresses()) {
                address.setCustomer(customer);
            }
        }
        Customer cust = customerRepository.save(customer);
        LOG.debug("CustomerServiceImpl >>Customer Added successfully", cust.toString());
        return cust;
    }

    @Override
    public Customer getcustomerInfo(int accountNo) {
        Customer customer = customerRepository.findByAccountId(accountNo)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with this account No doesn't exists"));
        LOG.debug("CustomerServiceImpl >>Customer Info fetched successfully", customer.toString());
        return customer;
    }

    @Override
    public Customer getCustomerById(int customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with this account No doesn't exists"));
        LOG.debug("CustomerServiceImpl >>Customer Info fetched successfully", customer.toString());
        return customer;
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        LOG.debug("CustomerServiceImpl >>Customer Info fetched successfully", customers);
        return customers;
    }

    @Transactional
    public Customer updateCustomer(int custId, Customer customer) {
        Customer existingCustomer = customerRepository.findById(custId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with this account No doesn't exists"));
        // Updating customer fields
        existingCustomer.setCustomerName(customer.getCustomerName());
        existingCustomer.setAccountNumber(customer.getAccountNumber());
        existingCustomer.setPhoneNumber(customer.getPhoneNumber());
        existingCustomer.setPassword(customer.getPassword());

        // Remove addresses that are not in the updated list
        Set<Address> updatedAddresses = customer.getAddresses();
        Set<Integer> updatedAddressIds = updatedAddresses.stream()
                .map(Address::getAddressId)
                .collect(Collectors.toSet());

        existingCustomer.getAddresses().removeIf(address -> !updatedAddressIds.contains(address.getAddressId()));

        // Add or update addresses
        for (Address updatedAddress : updatedAddresses) {
            boolean isNewAddress = updatedAddress.getAddressId() == 0;
            if (isNewAddress) {
                // New address, add it to the list
                updatedAddress.setCustomer(existingCustomer);
                existingCustomer.getAddresses().add(updatedAddress);
            } else {
                // Existing address, update the fields
                Address existingAddress = existingCustomer.getAddresses().stream()
                        .filter(address -> address.getAddressId() == updatedAddress.getAddressId())
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Address not found"));

                existingAddress.setHouseno(updatedAddress.getHouseno());
                existingAddress.setHouseName(updatedAddress.getHouseName());
                existingAddress.setCity(updatedAddress.getCity());
                existingAddress.setState(updatedAddress.getState());
                existingAddress.setPincode(updatedAddress.getPincode());
            }
        }
        customerRepository.save(existingCustomer);
        LOG.debug("CustomerServiceImpl >>Customer updated successfully", existingCustomer.toString());
        return existingCustomer;
    }

    @Override
    public void deleteCustomer(int custId) {
        customerRepository.deleteById(custId);
        LOG.debug("CustomerServiceImpl >>Customer deleted successfully");


    }

}
