package com.nexus.healthproof.fitness_tracker.controller;

import com.nexus.healthproof.fitness_tracker.entity.User;
import com.nexus.healthproof.fitness_tracker.entity.Weight;
import com.nexus.healthproof.fitness_tracker.service.UserService;
import com.nexus.healthproof.fitness_tracker.service.WeightService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/weights")
@RequiredArgsConstructor
public class WeightController {

    private final WeightService weightService;
    private final UserService userService;

    @PostMapping("/{userId}")
    public ResponseEntity<Weight> addWeight(@PathVariable UUID userId, @RequestBody Weight weight) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        weight.setUser(user);
        return ResponseEntity.ok(weightService.addWeight(weight));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Weight>> getWeightsByUser(@PathVariable UUID userId) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return ResponseEntity.ok(weightService.getWeightsByUser(user));
    }

    @DeleteMapping("/{weightId}")
    public ResponseEntity<Void> deleteWeight(@PathVariable UUID weightId) {
        weightService.deleteWeight(weightId);
        return ResponseEntity.noContent().build();
    }
}
