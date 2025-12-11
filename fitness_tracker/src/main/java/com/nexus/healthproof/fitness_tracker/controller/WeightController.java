package com.nexus.healthproof.fitness_tracker.controller;

import com.nexus.healthproof.fitness_tracker.entity.Weight;
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

    // Add weight entry for a specific user
    @PostMapping("/{userId}")
    public ResponseEntity<Weight> addWeight(@PathVariable UUID userId, @RequestBody Weight weight) {
        Weight savedWeight = weightService.addWeight(userId, weight);
        return ResponseEntity.ok(savedWeight);
    }

    // Get all weight entries of a user
    @GetMapping("/{userId}")
    public ResponseEntity<List<Weight>> getWeightsByUser(@PathVariable UUID userId) {
        List<Weight> weights = weightService.getWeightsByUser(userId);
        return ResponseEntity.ok(weights);
    }

    // Delete a weight entry
    @DeleteMapping("/{weightId}")
    public ResponseEntity<Void> deleteWeight(@PathVariable UUID weightId) {
        weightService.deleteWeight(weightId);
        return ResponseEntity.noContent().build();
    }
}
