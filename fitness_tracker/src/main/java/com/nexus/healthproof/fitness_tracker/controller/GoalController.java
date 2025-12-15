package com.nexus.healthproof.fitness_tracker.controller;

import com.nexus.healthproof.fitness_tracker.entity.Activity;
import com.nexus.healthproof.fitness_tracker.entity.Goal;
import com.nexus.healthproof.fitness_tracker.service.GoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users/{userId}/goals")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    @PostMapping
    public ResponseEntity<Goal> addGoal(@PathVariable UUID userId, @RequestBody Goal goal) {
        return ResponseEntity.ok(goalService.addGoal(userId, goal));
    }

    @GetMapping
    public ResponseEntity<List<Goal>> getGoalsByUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(goalService.getGoalsByUser(userId));
    }

    @PatchMapping("/{goalId}")
    public ResponseEntity<Goal> updateGoal(@PathVariable UUID userId, @PathVariable UUID goalId,
                                           @RequestBody Goal goalUpdates) {
        return ResponseEntity.ok(goalService.updateGoal(userId, goalId, goalUpdates));
    }

    @DeleteMapping("/{goalId}")
    public ResponseEntity<Void> deleteGoal(@PathVariable UUID userId, @PathVariable UUID goalId) {
        goalService.deleteGoal(userId, goalId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAll(@PathVariable("userId") UUID userId){
        goalService.deleteAll(userId);

        return ResponseEntity.noContent().build();
    }
}
