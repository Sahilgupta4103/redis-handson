package com.example.redisHandson.Controller;

import com.example.redisHandson.Model.CustomerEntity;
import com.example.redisHandson.Service.CustomerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public CustomerEntity getCustomer(@PathVariable Long id){
        return customerService.getCustomer(id);
    }

    @PutMapping("/{id}")
    public CustomerEntity updateCustomer(@PathVariable Long id, @RequestBody CustomerEntity customer){
        return customerService.updateCustomer(id, customer);
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
        return "Customer Deleted";
    }

}
