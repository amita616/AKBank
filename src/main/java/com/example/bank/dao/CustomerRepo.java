package com.example.bank.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.bank.model.Customer;

import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {

 /*   @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update customer c set c.customerName =:customerName , c.phoneNumber =:phoneNumber where c.customerId =:customerId",
            nativeQuery = true)
    int updateCustomerForNameNative(@Param("customerName") String customerName, @Param("phoneNumber") long phoneNumber, @Param("customerId") int customerId );
    */
 @Query(value = "SELECT * FROM customer WHERE account_number = ?1", nativeQuery = true)
 Optional<Customer> findByAccountId(int accountNumber);
}

