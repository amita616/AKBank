package com.example.bank.model;


import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name = "CUSTOMER")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int customerId;
    @Column
    private String customerName;

    @Column
    private int accountNumber;
    @JsonManagedReference
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    private Set<Address> addresses;

    @Column
    private long phoneNumber;

    @Column
    private String password;
    public Customer(){

    }

    public Customer(int customerId, String customerName, int accountNumber, long phoneNumber, Set<Address> addresses
            , String password) {
        super();
        this.customerId = customerId;
        this.customerName = customerName;
        this.accountNumber = accountNumber;
        this.addresses = addresses;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, addresses, customerId, customerName, password, phoneNumber);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Customer other = (Customer) obj;
        return accountNumber == other.accountNumber && Objects.equals(addresses, other.addresses)
                && customerId == other.customerId && Objects.equals(customerName, other.customerName)
                && Objects.equals(password, other.password) && phoneNumber == other.phoneNumber;
    }

    @Override
    public String toString() {
        return "Customer [customerId=" + customerId + ", customerName=" + customerName + ", accountNumber="
                + accountNumber + ", address=" + addresses + ", phoneNumber=" + phoneNumber + ", password=" + password
                + "]";
    }


}
