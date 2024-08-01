package com.example.bank.serviceTest;
import com.example.bank.dao.CustomerRepo;
import com.example.bank.exception.CustomerNotFoundException;
import com.example.bank.model.Address;
import com.example.bank.model.Customer;
import com.example.bank.model.Accounts;
import com.example.bank.service.CustomerServiceImpl;
import com.example.bank.service.AccountsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

        @InjectMocks
        private CustomerServiceImpl customerService;

        @Mock
        private CustomerRepo customerRepository;

        @Mock
        private AccountsService accountsService;

        @Mock
        private PasswordEncoder passwordEncoder;

        private Customer customer;
        private Accounts account;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
            customer = new Customer();
            customer.setCustomerId(1);
            customer.setCustomerName("John Doe");
            customer.setAccountNumber(123456789);
            customer.setPhoneNumber(1234567890L);
            customer.setPassword("password");

            account = new Accounts();
            account.setAccountNumber(123456789);
            account.setBalance(1000.0);
            account.setAccountStatus("Active");
        }

        @Test
        void createNewCustomer() {
            when(passwordEncoder.encode(customer.getPassword())).thenReturn("encodedPassword");
            when(customerRepository.save(customer)).thenReturn(customer);

            Customer createdCustomer = customerService.createNewCustomer(customer);

            assertNotNull(createdCustomer);
            assertEquals("encodedPassword", createdCustomer.getPassword());
            verify(customerRepository).save(customer);
        }

        @Test
        void getCustomerInfo() {
            when(customerRepository.findByAccountId(123456789)).thenReturn(Optional.of(customer));
            Customer fetchedCustomer = customerService.getcustomerInfo(123456789);
            assertNotNull(fetchedCustomer);
            verify(customerRepository).findByAccountId(123456789);
        }

        @Test
        void getCustomerInfoNotFound() {
            when(accountsService.getAccountInfo(123456789)).thenReturn(account);
            when(customerRepository.findById(123456789)).thenReturn(Optional.empty());

            assertThrows(CustomerNotFoundException.class, () -> customerService.getcustomerInfo(123456789));
        }

        @Test
        void getCustomerById() {
            when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

            Customer fetchedCustomer = customerService.getCustomerById(1);

            assertNotNull(fetchedCustomer);
            assertEquals("John Doe", fetchedCustomer.getCustomerName());
            verify(customerRepository).findById(1);
        }

        @Test
        void getCustomerByIdNotFound() {
            when(customerRepository.findById(1)).thenReturn(Optional.empty());

            assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerById(1));
        }

        @Test
        void getAllCustomers() {
            List<Customer> customerList = List.of(customer);
            when(customerRepository.findAll()).thenReturn(customerList);

            List<Customer> customers = customerService.getAllCustomers();

            assertNotNull(customers);
            assertEquals(1, customers.size());
            assertEquals("John Doe", customers.get(0).getCustomerName());
            verify(customerRepository).findAll();
        }
   /* @Test
    void updateCustomer() {
        // Prepare test data
        Customer existingCustomer = new Customer();
        existingCustomer.setCustomerId(1);
        existingCustomer.setCustomerName("Jane Doe");
        existingCustomer.setAccountNumber(123456789);
        existingCustomer.setPhoneNumber(9876543210L);
        existingCustomer.setPassword("oldPassword");

        Address existingAddress = new Address();
        existingAddress.setAddressId(1);
        existingAddress.setHouseno(123);
        existingAddress.setHouseName("Doe House");
        existingAddress.setCity("City");
        existingAddress.setState("State");
        existingAddress.setPincode(123456);
        existingCustomer.setAddresses(Set.of(existingAddress));

        Address newAddress = new Address();
        newAddress.setAddressId(0); // new address
        newAddress.setHouseno(456);
        newAddress.setHouseName("New House");
        newAddress.setCity("New City");
        newAddress.setState("New State");
        newAddress.setPincode(654321);

        Customer updatedCustomer = new Customer();
        updatedCustomer.setCustomerName("Jane Doe Updated");
        updatedCustomer.setAccountNumber(123456789);
        updatedCustomer.setPhoneNumber(9876543210L);
        updatedCustomer.setAddresses(Set.of(existingAddress, newAddress));
        updatedCustomer.setPassword("newPassword");

        // Set up mocks
        when(customerRepository.findById(1)).thenReturn(Optional.of(existingCustomer));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedPassword");

        // Create expected updated customer for save
        Customer customerToSave = new Customer();
        customerToSave.setCustomerId(1);
        customerToSave.setCustomerName("Jane Doe Updated");
        customerToSave.setAccountNumber(123456789);
        customerToSave.setPhoneNumber(9876543210L);
        customerToSave.setAddresses(Set.of(existingAddress, newAddress));
        customerToSave.setPassword("encodedPassword");

        when(customerRepository.save(any(Customer.class))).thenReturn(customerToSave);

        // Perform the update
        Customer result = customerService.updateCustomer(1, updatedCustomer);

        // Verify interactions and assert results
        assertNotNull(result);
        assertEquals("Jane Doe Updated", result.getCustomerName());
        assertEquals(9876543210L, result.getPhoneNumber());
        assertEquals("encodedPassword", result.getPassword());
        assertEquals(2, result.getAddresses().size()); // Check if both addresses are present
        assertTrue(result.getAddresses().contains(newAddress));
        assertTrue(result.getAddresses().contains(existingAddress));

        verify(customerRepository).findById(1);
        verify(customerRepository).save(argThat(customer ->
                customer.getCustomerId() == 1 &&
                        "Jane Doe Updated".equals(customer.getCustomerName()) &&
                        customer.getAccountNumber() == 123456789 &&
                        customer.getPhoneNumber() == 9876543210L &&
                        "encodedPassword".equals(customer.getPassword()) &&
                        customer.getAddresses().contains(newAddress) &&
                        customer.getAddresses().contains(existingAddress)
        ));
    }
*/
    @Test
        void deleteCustomer() {
            doNothing().when(customerRepository).deleteById(1);

            customerService.deleteCustomer(1);

            verify(customerRepository).deleteById(1);
        }
    }

