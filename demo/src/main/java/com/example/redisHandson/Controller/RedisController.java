package com.example.redisHandson.Controller;

import com.example.redisHandson.Service.RedisService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class RedisController {
    private final RedisService redisService;

    public RedisController(RedisService redisService){
        this.redisService = redisService;
    }

    @PostMapping("/{key}")
    public String setValue(@PathVariable String key, @RequestParam String value){
        redisService.setValue(key,value);
        return "value stored in redis";
    }

    @GetMapping("/{key}")
    public String getValue(@PathVariable String key){
        String value = redisService.getValue(key);
        return value !=null ? value : "Key not found";
    }

    @PostMapping("/{key}/ttl")
    public String setValue(@PathVariable String key, @RequestParam String value, @RequestParam long seconds){
        redisService.setValueWithTTl(key,value,seconds);
        return "value stored in redis with TTl";
    }
    @PostMapping("/orders/{orderId}/attempt")
    public Long incrementAttempts(@PathVariable String orderId){
        return redisService.incrementAttempts(orderId);
    }
    @PostMapping("rate-limit/{userId}")
    public String rateLimit(@PathVariable String userId){
        Long count = redisService.checkRateLimit(userId);
        if(count<=5){
            return "request Allower. Count = " + count;
        }
        return "Rate Limit Exceed. Count = " + count;
    }
    @PostMapping("/customer/{id}")
    public String saveCustomer(@PathVariable String id, @RequestParam String name, @RequestParam String email, @RequestParam String city, @RequestParam String balance){
        redisService.saveCustomerHash(id, name, email, city, balance);
        return "Customer saved to redis: ";
    }

    @PostMapping("/lock/{orderId}")
    public String acquireLock(@PathVariable String orderId, @RequestParam String instanceId){
        Boolean accquire = redisService.accquireLock(orderId, instanceId);
        if(Boolean.TRUE.equals(accquire)){
            return "Lock acquired by: " + instanceId;
        }
        return "Lock already held";
    }
    @DeleteMapping("/lock/{orderId}")
    public String releaseLock(@PathVariable String orderId, @RequestParam String instanceId){
            redisService.releaseLock(orderId, instanceId);
            return "Lock released attempted";
    }


}
