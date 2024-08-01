package com.example.bank.service;

import com.example.bank.exception.AccountDoesntExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bank.dao.AccountsRepo;
import com.example.bank.model.Accounts;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountsServiceImpl implements AccountsService {
	private static final Logger LOG = LoggerFactory.getLogger(AccountsServiceImpl.class);
	
	@Autowired
	private AccountsRepo accountRepo;

	@Override
	public void createAccount(Accounts account) {
		accountRepo.save(account);
		LOG.debug("AccountsServiceImpl >> New Account created successfully");
	}

	@Override
	public Accounts getAccountInfo(int accountNo) {
		Accounts account = accountRepo.findById(accountNo).orElseThrow(()->
				new AccountDoesntExistException("Account with this accountNo doesn't exists"));
		LOG.debug("AccountsServiceImpl >> Account Info fetched successfully with AccountNo", account.toString());
		return account;
	}

	@Override
	public void deleteAccount(int accountNo) {
		accountRepo.deleteById(accountNo);
        LOG.debug("AccountsServiceImpl >> Account Deleted successfully with accountNo ", accountNo);
	}

	@Override
	public double getBalance(int accountNo) {
		double balance =accountRepo.findBalanceByAcctID(accountNo);
		LOG.debug("AccountsServiceImpl >> Fetached successfully Available account balance-->",balance);
		return balance;
	}

	@Override
	@Transactional
	public void depositMoney(int accountNo, double amount) {
		Double balance =accountRepo.findBalanceByAcctID(accountNo);
		if (balance == null) {
			// Handle the case where the account is not found or balance is null
			throw new AccountDoesntExistException("Account not found for account number: " + accountNo);
		}
		accountRepo.saveBalanceByAcctID(accountNo,amount);
		LOG.debug("AccountsServiceImpl >>Amount deposited successfully-->",balance+amount);
	}

	@Override
	public void withdrawAmount(int accountNo, double amount) {
		double balance =accountRepo.findBalanceByAcctID(accountNo);
		 accountRepo.withdrawAmountByAcctID(accountNo,amount);
		LOG.debug("AccountsServiceImpl >>Amount created successfully-->",balance-amount);
	}

	@Override
	public void transferAmount(int accountNo, int transferBankNo, double amount) {
		accountRepo.withdrawAmountByAcctID(accountNo,amount);
		accountRepo.saveBalanceByAcctID(transferBankNo,amount);
		LOG.debug("AccountsServiceImpl >>Amount transferred successfully");
	}
	
	

}
