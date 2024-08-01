package com.example.bank.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.bank.model.Customer;

import java.util.Optional;

/**
 * author: amitachaudhari9062@gmail.com
 * This is a Respository class for Customer.
 * Using @Query for custom query for select customer where condition met.
 **/
@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {

    @Query(value = "SELECT * FROM customer WHERE account_number = ?1", nativeQuery = true)
    Optional<Customer> findByAccountId(int accountNumber);
}

