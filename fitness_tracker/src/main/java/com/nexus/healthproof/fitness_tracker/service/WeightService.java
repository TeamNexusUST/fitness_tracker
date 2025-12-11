package com.nexus.healthproof.fitness_tracker.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexus.healthproof.fitness_tracker.entity.User;
import com.nexus.healthproof.fitness_tracker.entity.Weight;
import com.nexus.healthproof.fitness_tracker.repository.WeightRepository;
import com.nexus.healthproof.fitness_tracker.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WeightService {

    private final WeightRepository weightRepository;
    private final UserRepository userRepository;

    // Add weight entry for a specific user
    @Transactional
    public Weight addWeight(UUID userId, Weight weight) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        weight.setUser(user);
        return weightRepository.save(weight);
    }

    // Fetch weight entries for a user
    public List<Weight> getWeightsByUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return weightRepository.findByUser(user);
    }

    // Update a weight entry
    @Transactional
    public Weight updateWeight(UUID weightId, Weight weightUpdates) {
        Weight existingWeight = weightRepository.findById(weightId)
                .orElseThrow(() -> new IllegalArgumentException("Weight entry not found"));

        if (weightUpdates.getDate() != null) existingWeight.setDate(weightUpdates.getDate());
        if (weightUpdates.getPounds() != null) existingWeight.setPounds(weightUpdates.getPounds());

        return weightRepository.save(existingWeight);
    }

    // Delete a weight entry
    @Transactional
    public void deleteWeight(UUID id) {
        if (!weightRepository.existsById(id)) {
            throw new IllegalArgumentException("Weight entry not found");
        }
        weightRepository.deleteById(id);
    }
}
