package com.example.bank.serviceTest;
import com.example.bank.dao.AccountsRepo;
import com.example.bank.exception.AccountDoesntExistException;
import com.example.bank.model.Accounts;
import com.example.bank.service.AccountsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountsServiceTest {

    @InjectMocks
    private AccountsServiceImpl accountsService;

    @Mock
    private AccountsRepo accountsRepo;

    private Accounts account;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize test data
        account = new Accounts();
        account.setAccountNumber(123456789);
        account.setBalance(1000.0);
        account.setAccountStatus("Active");
    }

    @Test
    void createAccount() {
        when(accountsRepo.save(account)).thenReturn(account);

        accountsService.createAccount(account);

        verify(accountsRepo).save(account);
    }

    @Test
    void getAccountInfo() {
        when(accountsRepo.findById(123456789)).thenReturn(java.util.Optional.of(account));

        Accounts fetchedAccount = accountsService.getAccountInfo(123456789);

        assertNotNull(fetchedAccount);
        assertEquals(123456789, fetchedAccount.getAccountNumber());
        verify(accountsRepo).findById(123456789);
    }

    @Test
    void getAccountInfoNotFound() {
        when(accountsRepo.findById(123456789)).thenReturn(java.util.Optional.empty());

        assertThrows(AccountDoesntExistException.class, () -> accountsService.getAccountInfo(123456789));
    }

    @Test
    void deleteAccount() {
        doNothing().when(accountsRepo).deleteById(123456789);

        accountsService.deleteAccount(123456789);

        verify(accountsRepo).deleteById(123456789);
    }

    @Test
    void getBalance() {
        when(accountsRepo.findBalanceByAcctID(123456789)).thenReturn(1000.0);

        double balance = accountsService.getBalance(123456789);

        assertEquals(1000, balance);
        verify(accountsRepo).findBalanceByAcctID(123456789);
    }

    @Test
    void depositMoney() {
        when(accountsRepo.findBalanceByAcctID(123456789)).thenReturn(1000.0);
        doNothing().when(accountsRepo).saveBalanceByAcctID(123456789, 500);

        accountsService.depositMoney(123456789, 500);

        verify(accountsRepo).findBalanceByAcctID(123456789);
        verify(accountsRepo).saveBalanceByAcctID(123456789, 500);

    }
    @Test
    void withdrawAmount() {
        when(accountsRepo.findBalanceByAcctID(123456789)).thenReturn(1000.0);
        doNothing().when(accountsRepo).withdrawAmountByAcctID(123456789, 500);

        accountsService.withdrawAmount(123456789, 500);

        verify(accountsRepo).findBalanceByAcctID(123456789);
        verify(accountsRepo).withdrawAmountByAcctID(123456789, 500);
    }

    @Test
    void transferAmount() {
        doNothing().when(accountsRepo).withdrawAmountByAcctID(123456789, 500);
        doNothing().when(accountsRepo).saveBalanceByAcctID(987654321, 500);

        accountsService.transferAmount(123456789, 987654321, 500);

        verify(accountsRepo).withdrawAmountByAcctID(123456789, 500);
        verify(accountsRepo).saveBalanceByAcctID(987654321, 500);
    }

}
