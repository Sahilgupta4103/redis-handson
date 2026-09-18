package com.example.redisHandson.Repository;

import com.example.redisHandson.Model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.function.Function;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
