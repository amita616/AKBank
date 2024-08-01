package com.example.bank.controller;

import com.example.bank.dao.AddressesRepo;
import com.example.bank.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/AKBank/addresses")
public class AddressController {

    @Autowired
    private AddressesRepo addressRepository;

    // Create a new address
    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody Address address) {
        Address savedAddress = addressRepository.save(address);
        return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
    }

    // Get all addresses
    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    // Get a specific address by ID
    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable("id") int id) {
        Optional<Address> address = addressRepository.findById(id);
        if (address.isPresent()) {
            return new ResponseEntity<>(address.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update an existing address
    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable("id") int id, @RequestBody Address address) {
        if (!addressRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        address.setAddressId(id);
        Address updatedAddress = addressRepository.save(address);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

    // Delete an address by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable("id") int id) {
        if (!addressRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        addressRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
