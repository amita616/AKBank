package com.example.bank.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bank.model.Accounts;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AccountsRepo extends JpaRepository<Accounts, Integer>{

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
