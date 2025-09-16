package com.example.backend.modules.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @MessageMapping("/dashboard/update")
    @SendTo("/topic/dashboard")
    public DashboardResponse updateScore(DashboardRequest request, Principal principal) {

        // Check authentication manually using Principal
        if (principal == null) {
            throw new SecurityException("User not authenticated");
        }

        // Cast Principal to Authentication to check authorities
        if (!(principal instanceof Authentication)) {
            throw new SecurityException("User not authenticated");
        }

        Authentication auth = (Authentication) principal;
        if (!auth.isAuthenticated()) {
            throw new SecurityException("User not authenticated");
        }

        // Check if user has ADMIN role
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new SecurityException("Access denied. Admin role required.");
        }

        // Update the score in the service
        dashboardService.updateScore(request.getName(), request.getScore());

        // Get updated leaderboard
        List<DashboardResponse.DashboardEntry> leaderboard = dashboardService.getLeaderboard();

        // Return the updated leaderboard to all subscribers
        return new DashboardResponse(leaderboard);
    }

    @MessageMapping("/dashboard/refresh")
    @SendToUser("/queue/dashboard")
    public DashboardResponse refreshDashboard(Principal principal) {
        List<DashboardResponse.DashboardEntry> leaderboard = dashboardService.getLeaderboard();
        return new DashboardResponse(leaderboard);
    }

}
