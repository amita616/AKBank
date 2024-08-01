package com.example.bank.dao;

import com.example.bank.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressesRepo extends JpaRepository<Address,Integer> {
}
