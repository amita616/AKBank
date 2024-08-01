package com.example.bank.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bank.model.Accounts;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * author: amitachaudhari9062@gmail.com
 * This is a Respository class for Accounts.
 * Using @Query for custom query for making transaction.
 * Using @Transactional in Spring helps manage database transactions Spring takes care of starting, committing, or rolling back the transaction based on the outcome.
 * Using @Modifying annotation signals to Spring Data JPA that the associated query method performs an update or delete operation, rather than a select operation.
 * clearAutomatically = true attribute ensures that the persistence context is cleared after the query is executed.
 */
@Repository
public interface AccountsRepo extends JpaRepository<Accounts, Integer> {

    @Query("select balance from Accounts where accountNumber = ?1")
    public Double findBalanceByAcctID(int accountNumber);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Accounts set balance = balance+?2 where accountNumber=?1")
    public void saveBalanceByAcctID(int accountNumber, double balance);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Accounts set balance = balance-?2 where accountNumber=?1")
    public void withdrawAmountByAcctID(int accountNumber, double balance);

}
