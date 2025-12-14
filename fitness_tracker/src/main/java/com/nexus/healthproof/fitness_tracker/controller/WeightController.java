package com.nexus.healthproof.fitness_tracker.controller;

import com.nexus.healthproof.fitness_tracker.entity.Weight;
import com.nexus.healthproof.fitness_tracker.service.WeightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users/{userId}/weights")
@RequiredArgsConstructor
public class WeightController {

    private final WeightService weightService;

    @PostMapping
    public ResponseEntity<Weight> addWeight(@PathVariable UUID userId, @RequestBody Weight weight) {
        return ResponseEntity.ok(weightService.addWeight(userId, weight));
    }

    @GetMapping
    public ResponseEntity<List<Weight>> getWeightsByUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(weightService.getWeightsByUser(userId));
    }

    @PatchMapping("/{weightId}")
    public ResponseEntity<Weight> updateWeight(@PathVariable UUID userId, @PathVariable UUID weightId,
                                               @RequestBody Weight weightUpdates) {
        return ResponseEntity.ok(weightService.updateWeight(userId, weightId, weightUpdates));
    }

    @DeleteMapping("/{weightId}")
    public ResponseEntity<Void> deleteWeight(@PathVariable UUID userId, @PathVariable UUID weightId) {
        weightService.deleteWeight(userId, weightId);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAll(@PathVariable("userId") UUID userId){
        weightService.deleteAll(userId);

        return ResponseEntity.noContent().build();
    }
}
