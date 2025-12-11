package com.nexus.healthproof.fitness_tracker.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexus.healthproof.fitness_tracker.entity.Goal;
import com.nexus.healthproof.fitness_tracker.entity.User;
import com.nexus.healthproof.fitness_tracker.repository.GoalRepository;
import com.nexus.healthproof.fitness_tracker.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;
    private final UserRepository userRepository;

    // Add goal for a specific user
    @Transactional
    public Goal addGoal(UUID userId, Goal goal) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Set relation
        goal.setUser(user);
        return goalRepository.save(goal);
    }

    // Get all goals of a user
    public List<Goal> getGoalsByUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return goalRepository.findByUser(user);
    }

    // Update an existing goal
    @Transactional
    public Goal updateGoal(UUID goalId, Goal goalUpdates) {
        Goal existingGoal = goalRepository.findById(goalId)
                .orElseThrow(() -> new IllegalArgumentException("Goal not found"));

        if (goalUpdates.getGoalType() != null) existingGoal.setGoalType(goalUpdates.getGoalType());
        if (goalUpdates.getTargetValue() != null) existingGoal.setTargetValue(goalUpdates.getTargetValue());
        if (goalUpdates.getStartDate() != null) existingGoal.setStartDate(goalUpdates.getStartDate());
        if (goalUpdates.getEndDate() != null) existingGoal.setEndDate(goalUpdates.getEndDate());

        return goalRepository.save(existingGoal);
    }

    // Delete a goal
    @Transactional
    public void deleteGoal(UUID id) {
        if (!goalRepository.existsById(id)) {
            throw new IllegalArgumentException("Goal not found");
        }
        goalRepository.deleteById(id);
    }
}
