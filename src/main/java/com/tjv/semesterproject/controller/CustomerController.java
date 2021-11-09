package com.tjv.semesterproject.controller;

import com.tjv.semesterproject.exceptions.CustomerNotExistException;
import com.tjv.semesterproject.exceptions.OrderNotExistException;
import com.tjv.semesterproject.model.CustomerDto;
import com.tjv.semesterproject.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/")
    public ResponseEntity getCustomer(@RequestParam("customer_id") Long id) {
        try {
            return ResponseEntity.ok(customerService.getCustomer(id));
        } catch (CustomerNotExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Customer Getting Error");
        }
    }

    @GetMapping("/orders/{customer_id}/")
    public ResponseEntity getCustomerOrder(@PathVariable("customer_id") Long customer_id,
                                           @RequestParam(value = "order_id") Integer order_id) {
        try {
            return ResponseEntity.ok(customerService.getCustomerOrder(customer_id, order_id));
        } catch (CustomerNotExistException | OrderNotExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Customer Getting Error");
        }
    }

    @GetMapping()
    public ResponseEntity getAllCustomers() {
        try {
            return ResponseEntity.ok(customerService.readAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Customer Getting All Error");
        }
    }

    @GetMapping("/orders/")
    public ResponseEntity getAllCustomerOrders(@RequestParam(value = "customer_id") Long customer_id) {
        try {
            return ResponseEntity.ok(customerService.readAllOrders(customer_id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Customer Getting All Orders Error");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCustomer(@RequestBody CustomerDto customerDto, @PathVariable("id") Long id) {
        try {
            customerService.updateCustomer(customerDto, id);
            return ResponseEntity.ok("Customer has been saved");
        } catch (CustomerNotExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Customer Putting Error");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCustomer(@PathVariable("id") Long id) {
        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.ok("Customer with id: " + id + " has been deleted");
        } catch( CustomerNotExistException e){
            return ResponseEntity.badRequest().body( e.getMessage() );
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Customer Deleting Error");
        }
    }
}
