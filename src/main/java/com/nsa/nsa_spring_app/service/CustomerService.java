package com.nsa.nsa_spring_app.service;

import com.nsa.nsa_spring_app.model.Customer;
import com.nsa.nsa_spring_app.repository.CustomerRepository;
import com.nsa.nsa_spring_app.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Transactional("slaveTransactionManager")
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    @Transactional("slaveTransactionManager")
    public Customer getCustomerById(UUID id){
        return customerRepository.findById(id).orElse(null);
    }

    @Transactional("masterTransactionManager")
    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    @Transactional("masterTransactionManager")
    public void updateCustomer(Customer customer){
        customerRepository.save(customer);
    }

    @Transactional("masterTransactionManager")
    public void deleteCustomer(UUID id){
        customerRepository.deleteById(id);
    }

}
