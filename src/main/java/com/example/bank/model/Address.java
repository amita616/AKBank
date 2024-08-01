package com.example.bank.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name = "ADDRESS")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int addressId;
    @Column
    private int houseno;
    @Column
    private String houseName;
    @Column
    private String city;
    @Column
    private String state;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;
    @Column
    private int pincode;

    public Address(int addressId, int houseno, String houseName, String city, String state, int pincode, Customer customer) {
        super();
        this.addressId = addressId;
        this.houseno = houseno;
        this.houseName = houseName;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.customer = customer;
    }

    public Address() {
    }
    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getHouseno() {
        return houseno;
    }

    public void setHouseno(int houseno) {
        this.houseno = houseno;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressId, city, houseName, houseno, pincode, state);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Address other = (Address) obj;
        return addressId == other.addressId && Objects.equals(city, other.city)
                && Objects.equals(houseName, other.houseName) && houseno == other.houseno && pincode == other.pincode
                && Objects.equals(state, other.state);
    }

    @Override
    public String toString() {
        return "Address [addressId=" + addressId + ", houseno=" + houseno + ", houseName=" + houseName + ", city="
                + city + ", state=" + state + ", pincode=" + pincode + "]";
    }


}
