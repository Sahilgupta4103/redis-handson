package com.example.redisHandson.Service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;

@Service
public class RedisService {
    private final StringRedisTemplate redisTemplate;
    private RedisService(StringRedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public void setValue(String key, String value){
        redisTemplate
                .opsForValue()
                .set(key,value);
    }

    public String getValue(String key){
        return redisTemplate
                .opsForValue()
                .get(key);
    }

    public void setValueWithTTl(String key, String value, long seconds){
        redisTemplate
                .opsForValue()
                .set(key, value, Duration.ofSeconds(seconds));
    }
    public Long incrementAttempts(String orderId){
        return redisTemplate
                .opsForValue()
                .increment("order:" + orderId + ":attempts");
    }

    public Long checkRateLimit(String userId){
        String key = "rate:user:" + userId;

        Long count = redisTemplate
                .opsForValue()
                .increment(key);

        if(count !=null && count == 1){
            redisTemplate
                    .expire(key, Duration.ofSeconds(60));
        }
        return count;
    }

    public void saveCustomerHash(String customerId, String name, String email, String city, String balance){
        String key = "customer:" + customerId;
        Map<String, String>  map = Map.of("name", name, "email", email, "city", city, "balance", balance);
        redisTemplate
                .opsForHash().putAll(key, map);
    }

    public Boolean accquireLock(String orderId, String instanceID){
        String key = "order:"+orderId+":lock";
        return redisTemplate.opsForValue().setIfAbsent(key, instanceID,Duration.ofSeconds(30));
    }

    public void releaseLock(String orderId, String instanceId){
        String key = "order:" + orderId + ":lock";
        String currentOwner = redisTemplate.opsForValue().get(key);
        if(currentOwner == instanceId){
            redisTemplate.delete(key);
        }
    }

}
