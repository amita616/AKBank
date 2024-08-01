package com.example.bank.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * author: amitachaudhari9062@gmail.com
 * This is an Entity class for Accounts.
 * Accounts contains account information of a customer.
 */

@Entity
@Table(name = "ACCOUNTS")
public class Accounts {
    @Id
    private int accountNumber;
    @Column
    private double balance;
    @Column
    private String accountStatus;

    public Accounts() {
    }

    public Accounts(int accountNumber, double balance, String accountStatus) {
        super();
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountStatus = accountStatus;
    }


    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, accountStatus, balance);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Accounts other = (Accounts) obj;
        return accountNumber == other.accountNumber && Objects.equals(accountStatus, other.accountStatus)
                && Double.doubleToLongBits(balance) == Double.doubleToLongBits(other.balance);
    }


}
