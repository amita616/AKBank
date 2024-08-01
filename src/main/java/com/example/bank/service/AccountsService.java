package com.example.bank.service;

import com.example.bank.model.Accounts;

/**
 * author: amitachaudhari9062@gmail.com
 * This is a Service Interface class for Accounts.
 **/
public interface AccountsService {
    public void createAccount(Accounts account);

    public Accounts getAccountInfo(int accountNo);

    public void deleteAccount(int accountNo);

    public double getBalance(int accountNo);

    public void depositMoney(int accountNo, double amount);

    public void withdrawAmount(int accountNo, double amount);

    public void transferAmount(int accountNo, int transferBankNo, double amount);

}
