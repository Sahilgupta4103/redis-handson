package com.example.redisHandson.Service;

import com.example.redisHandson.Model.CustomerEntity;
import com.example.redisHandson.Repository.CustomerRepository;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Cacheable(value = "customers", key = "#id")
    public CustomerEntity getCustomer(Long id){
        System.out.println("Fetching customer from pgSQL");
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @CachePut(value = "customers", key = "#id")
    public CustomerEntity updateCustomer(Long id, CustomerEntity customer){
        CustomerEntity existing = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        existing.setName(customer.getName());
        existing.setEmail(customer.getEmail());
        existing.setCity(customer.getCity());
        return customerRepository.save(existing);
    }

    @CacheEvict(value = "customers", key = "#id")
    public void deleteCustomer(Long id){
        customerRepository.deleteById(id);
        System.out.println("Customer deleted and cache evicted");
    }

}