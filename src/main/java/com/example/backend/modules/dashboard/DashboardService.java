package com.example.backend.modules.dashboard;

import com.example.backend.infrastructure.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final RedisService redisService;
    private static final String LEADERBOARD_KEY = "leaderboard";

    public void updateScore(String name, float score) {
        // Store in Redis for persistence
        redisService.save(LEADERBOARD_KEY + ":" + name, String.valueOf(score));
    }

    public List<DashboardResponse.DashboardEntry> getLeaderboard() {
        // Get all scores from Redis and sort them
        Map<String, String> redisScores = redisService.getAllByPattern(LEADERBOARD_KEY + ":*");

        return redisScores.entrySet().stream()
                .map(entry -> {
                    // Extract name from key (remove "leaderboard:" prefix)
                    String name = entry.getKey().substring(LEADERBOARD_KEY.length() + 1);
                    float score = Float.parseFloat(entry.getValue());
                    return new DashboardResponse.DashboardEntry(name, score);
                })
                .sorted((a, b) -> Float.compare(b.getScore(), a.getScore())) // Sort by score descending
                .collect(Collectors.toList());
    }

}
