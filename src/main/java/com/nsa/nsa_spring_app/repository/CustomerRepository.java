package com.nsa.nsa_spring_app.repository;

import com.nsa.nsa_spring_app.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
