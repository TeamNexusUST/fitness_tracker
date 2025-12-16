package com.nexus.healthproof.fitness_tracker.service;

import com.nexus.healthproof.fitness_tracker.entity.User;
import com.nexus.healthproof.fitness_tracker.entity.Weight;
import com.nexus.healthproof.fitness_tracker.exception.UserNotFoundException;
import com.nexus.healthproof.fitness_tracker.exception.WeightNotFoundException;
import com.nexus.healthproof.fitness_tracker.repository.UserRepository;
import com.nexus.healthproof.fitness_tracker.repository.WeightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WeightService {

    private final WeightRepository weightRepository;
    private final UserRepository userRepository;

    @Transactional
    public Weight addWeight(UUID userId, Weight weight) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        weight.setUser(user);
        return weightRepository.save(weight);
    }

    public List<Weight> getWeightsByUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return weightRepository.findByUser(user);
    }

    @Transactional
    public Weight updateWeight(UUID userId, UUID weightId, Weight weightUpdates) {
        Weight existingWeight = weightRepository.findById(weightId)
                .orElseThrow(() -> new WeightNotFoundException("Weight entry not found with id: " + weightId));

        if (!existingWeight.getUser().getId().equals(userId)) {
            throw new WeightNotFoundException("Weight entry does not belong to user: " + userId);
        }

        if (weightUpdates.getDate() != null) existingWeight.setDate(weightUpdates.getDate());
        if (weightUpdates.getPounds() != null) existingWeight.setPounds(weightUpdates.getPounds());

        return weightRepository.save(existingWeight);
    }

    @Transactional
    public void deleteWeight(UUID userId, UUID weightId) {
        Weight weight = weightRepository.findById(weightId)
                .orElseThrow(() -> new WeightNotFoundException("Weight entry not found with id: " + weightId));

        if (!weight.getUser().getId().equals(userId)) {
            throw new WeightNotFoundException("Weight entry does not belong to user: " + userId);
        }

        weightRepository.delete(weight);
    }

    @Transactional
    public void deleteAll(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        weightRepository.deleteAll(weightRepository.findByUser(user));
    }
}
