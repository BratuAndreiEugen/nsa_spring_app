package com.nsa.nsa_spring_app.service;

import com.nsa.nsa_spring_app.model.Customer;
import com.nsa.nsa_spring_app.model.Order;
import com.nsa.nsa_spring_app.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Transactional("slaveTransactionManager")
    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    @Transactional("slaveTransactionManager")
    public Order getOrderById(UUID id){
        return orderRepository.findById(id).orElse(null);
    }

    @Transactional("slaveTransactionManager")
    public List<Order> getAllOrdersForCustomer(UUID customerId){
        return orderRepository.findAllByCustomerId(customerId);
    }

    @Transactional("masterTransactionManager")
    public Order saveOrder(Order order){
        return  orderRepository.save(order);
    }

    @Transactional("masterTransactionManager")
    public void updateOrder(Order order){
        orderRepository.save(order);
    }

    @Transactional("masterTransactionManager")
    public void deleteOrder(UUID id){
        orderRepository.deleteById(id);
    }
}
