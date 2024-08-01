package com.example.bank.dao;

import com.example.bank.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * author: amitachaudhari9062@gmail.com
 * This is a Respository class for Addresses.
 **/
@Repository
public interface AddressesRepo extends JpaRepository<Address,Integer> {
}
