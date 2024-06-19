package com.example.api.respositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.domain.address.Address;

public interface AddressRepositiry extends JpaRepository<Address, UUID> {
    
}
