package com.example.backend.modules.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {
    private List<DashboardEntry> leaderboard;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DashboardEntry {
        private String name;
        private float score;
    }
}
