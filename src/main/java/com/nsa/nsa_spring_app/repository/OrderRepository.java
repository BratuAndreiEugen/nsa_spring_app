package com.nsa.nsa_spring_app.repository;

import com.nsa.nsa_spring_app.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findAllByCustomerId(UUID customerId);
}
