package com.example.redisHandson.Service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class kafkaRedisConsumer {
    private final StringRedisTemplate redisTemplate;
    public kafkaRedisConsumer(StringRedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @KafkaListener(topics = "redis-order-events", groupId = "kafka-redis-demo")
    public void consume(ConsumerRecord<String, String> record){
        String customerId = record.key();
        String orderId = record.value();
        String redisKey = "customer:"+customerId;
        redisTemplate.opsForHash()
                .increment(redisKey, "orderCount", 1);
        redisTemplate.opsForHash()
                .put(redisKey, "lastOrderId", orderId);

        System.out.println(
                "Kafka → Redis"
                        + " | Partition: " + record.partition()
                        + " | Offset: " + record.offset()
                        + " | Customer: " + customerId
                        + " | Order: " + orderId
        );
    }

}
