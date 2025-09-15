package com.example.backend.infrastructure.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final StringRedisTemplate redisTemplate;

    // Save a value
    public void save(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // Get a value
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // Delete a key
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    // Get all keys matching a pattern
    public Set<String> getKeys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    // Get all key-value pairs matching a pattern
    public java.util.Map<String, String> getAllByPattern(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        if (keys == null || keys.isEmpty()) {
            return java.util.Collections.emptyMap();
        }

        java.util.Map<String, String> result = new java.util.HashMap<>();
        for (String key : keys) {
            String value = redisTemplate.opsForValue().get(key);
            if (value != null) {
                result.put(key, value);
            }
        }
        return result;
    }
}
