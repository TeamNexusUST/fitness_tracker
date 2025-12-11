package com.nexus.healthproof.fitness_tracker.controller;

import com.nexus.healthproof.fitness_tracker.entity.Goal;
import com.nexus.healthproof.fitness_tracker.service.GoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/goals")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    // Add goal for a specific user
    @PostMapping("/{userId}")
    public ResponseEntity<Goal> addGoal(@PathVariable UUID userId, @RequestBody Goal goal) {
        Goal savedGoal = goalService.addGoal(userId, goal);
        return ResponseEntity.ok(savedGoal);
    }

    // Get all goals of a specific user
    @GetMapping("/{userId}")
    public ResponseEntity<List<Goal>> getGoalsByUser(@PathVariable UUID userId) {
        List<Goal> goals = goalService.getGoalsByUser(userId);
        return ResponseEntity.ok(goals);
    }

    // Delete a goal
    @DeleteMapping("/{goalId}")
    public ResponseEntity<Void> deleteGoal(@PathVariable UUID goalId) {
        goalService.deleteGoal(goalId);
        return ResponseEntity.noContent().build();
    }
}
