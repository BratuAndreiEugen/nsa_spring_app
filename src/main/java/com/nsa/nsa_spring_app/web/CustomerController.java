package com.nsa.nsa_spring_app.web;


import com.nsa.nsa_spring_app.config.security.annot.AllowAccountantHierarchy;
import com.nsa.nsa_spring_app.model.Customer;
import com.nsa.nsa_spring_app.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping()
    public ResponseEntity<?> fetchAllCustomers() {
        try {
            return ResponseEntity.ok(customerService.getAllCustomers());
        }catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> fetchCustomerById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(customerService.getCustomerById(id));
        }catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @AllowAccountantHierarchy
    @PostMapping()
    public ResponseEntity<?> saveCustomer(@RequestBody Customer customer) {
        try {
            return ResponseEntity.ok(customerService.saveCustomer(customer));
        }catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @AllowAccountantHierarchy
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable UUID id, @RequestBody Customer customer) {
        try {
            customer.setId(id);
            customerService.updateCustomer(customer);
            return  ResponseEntity.ok(customer);
        }catch (Exception e){
            return  ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @AllowAccountantHierarchy
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable UUID id) {
        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

}
