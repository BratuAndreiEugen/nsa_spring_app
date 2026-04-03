package com.nsa.nsa_spring_app.web;


import com.nsa.nsa_spring_app.config.security.annot.AllowAccountantHierarchy;
import com.nsa.nsa_spring_app.model.Order;
import com.nsa.nsa_spring_app.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping()
    public ResponseEntity<?> fetchAllOrders() {
        try {
            return ResponseEntity.ok(orderService.getAllOrders());
        }catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> fetchOrderById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(orderService.getOrderById(id));
        }catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> fetchOrdersForCustomer(@PathVariable UUID customerId) {
        try {
            return ResponseEntity.ok(orderService.getAllOrdersForCustomer(customerId));
        }catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @AllowAccountantHierarchy
    @PostMapping()
    public ResponseEntity<?> saveOrder(@RequestBody Order order) {
        try {
            return ResponseEntity.ok(orderService.saveOrder(order));
        }catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @AllowAccountantHierarchy
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable UUID id, @RequestBody Order order) {
        try {
            order.setId(id);
            orderService.updateOrder(order);
            return  ResponseEntity.ok(order);
        }catch (Exception e){
            return  ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @AllowAccountantHierarchy
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable UUID id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
