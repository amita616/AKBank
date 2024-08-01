package com.example.bank.controller;

import com.example.bank.exception.CustomerAlreadyExistException;
import com.example.bank.exception.CustomerNotFoundException;
import com.example.bank.model.Address;
import com.example.bank.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.bank.service.CustomerService;

import java.util.List;
import java.util.Optional;

/**
 * author: amitachaudhari9062@gmail.com
 * This is a Controller for Customer handles all the requests related to Customer.
 **/
@RestController
@RequestMapping("/AKBank/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private AccountsController accountsController;

    @GetMapping("/getCustomerInfoByAccountNo/{accountNo}")
    public ResponseEntity<?> getCustomerInformationByAccNo(@PathVariable int accountNo) {
        try {
            Customer customer = customerService.getcustomerInfo(accountNo);
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getCustomerInfo/{custId}")
    public ResponseEntity<?> getCustomerInformationByCustomerId(@PathVariable int custId) {
        Optional<Customer> customer = Optional.of(customerService.getCustomerById(custId));
        if (!customer.isPresent()) {
            throw new CustomerNotFoundException("Customer with this Custmer Id doesn't exist");
        } else {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }
    }


    @GetMapping("/getAllCustomerInfo")
    public ResponseEntity<List<Customer>> getAllCustomersInformation() {
        List<Customer> customer = customerService.getAllCustomers();
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping("/createNewCustomer")
    public ResponseEntity<Customer> createNewCustomer(@RequestBody Customer cust) {
        Customer customer = customerService.createNewCustomer(cust);
        accountsController.createNewAccount(cust.getAccountNumber(), 0, "Active");
        return new ResponseEntity<Customer>(customer, HttpStatus.CREATED);
    }

    @PutMapping("/updateCustInfo/{id}")
    public ResponseEntity<Customer> updateCustomerInfoById(@PathVariable int id, @RequestBody Customer cust) {
        Customer customer = customerService.updateCustomer(id, cust);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @DeleteMapping("deleteCust/{custId}")
    public ResponseEntity<Void> deleteCustomerInfo(@PathVariable int custId) {
        int accountNo = customerService.getCustomerById(custId).getAccountNumber();
        customerService.deleteCustomer(custId);
        accountsController.deleteAccount(accountNo);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
