package com.example.backend.modules.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @MessageMapping("/dashboard/update")
    @SendTo("/topic/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public DashboardResponse updateScore(DashboardRequest request) {
        // Update the score in the service
        dashboardService.updateScore(request.getName(), request.getScore());

        // Get updated leaderboard
        List<DashboardResponse.DashboardEntry> leaderboard = dashboardService.getLeaderboard();

        // Return the updated leaderboard to all subscribers
        return new DashboardResponse(leaderboard);
    }

    @MessageMapping("/dashboard/refresh")
    @SendTo("/topic/dashboard")
    public DashboardResponse refreshDashboard() {
        List<DashboardResponse.DashboardEntry> leaderboard = dashboardService.getLeaderboard();
        return new DashboardResponse(leaderboard);
    }

    @MessageMapping("/dashboard/user/refresh")
    @SendTo("/topic/dashboard")
    public DashboardResponse refreshDashboardForUser() {
        List<DashboardResponse.DashboardEntry> leaderboard = dashboardService.getLeaderboard();
        return new DashboardResponse(leaderboard);
    }
}
