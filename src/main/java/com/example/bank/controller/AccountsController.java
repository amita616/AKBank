package com.example.bank.controller;

import com.example.bank.exception.AccountDoesntExistException;
import com.example.bank.model.Accounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.bank.service.AccountsService;

import java.util.Optional;

@RestController
@RequestMapping("/AKBank/account")
public class AccountsController {
	
	@Autowired
	private AccountsService accountService;

	//Account will be created on creating customer
	public void createNewAccount(int acctID, int balance, String acctStatus){
		Accounts acct = new Accounts(acctID, balance, acctStatus);
		accountService.createAccount(acct);
	}

	@GetMapping("/accountInfo/{acctNo}")
	public ResponseEntity<?> getAccountInfo(@PathVariable int acctNo){
		Optional<Accounts> accountInfo = Optional.of(accountService.getAccountInfo(acctNo));
		if(!accountInfo.isPresent()){
			throw new AccountDoesntExistException("Account No is not valid");
		}
		else{
			return  new ResponseEntity<>(accountInfo,HttpStatus.OK);
		}
	}

	@GetMapping("/{acctNo}/Avlbalance")
	public ResponseEntity<Double> getAvailableBalanceByAccNo(@PathVariable int acctNo){
		double availBalance = accountService.getBalance(acctNo);
		return new ResponseEntity<>(availBalance, HttpStatus.OK);
	}

	@PutMapping("/{accNo}/deposit/{amount}")
	public ResponseEntity<Void> moneyDepositedInAccount(@PathVariable int accNo,@PathVariable double amount){
			accountService.depositMoney(accNo, amount);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@PutMapping("/{acctNo}/withdraw/{amount}")
	public ResponseEntity<Void> moneyWithdrawalFromAccount(@PathVariable int acctNo,@PathVariable double amount){
		double initialBalance = accountService.getBalance(acctNo);
		if(initialBalance>amount) {
			accountService.withdrawAmount(acctNo, amount);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else{
			throw new RuntimeException("you have less balance");
		}
	}

	@PutMapping("/{acctNo}/transfer/{destAcctNo}/{amount}")
	public ResponseEntity<Void> transferMoney(@PathVariable int acctNo, @PathVariable int destAcctNo, @PathVariable double amount){
		double initialBalance = accountService.getBalance(acctNo);
		if(initialBalance > amount) {
			accountService.transferAmount(acctNo, destAcctNo, amount);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
		else{
			throw new RuntimeException("you have less balance, please credit money before transfer");
		}
	}

	public ResponseEntity<Void> deleteAccount(int accountNo){
		accountService.deleteAccount(accountNo);
		return  new ResponseEntity<>(HttpStatus.OK);
	}


}
