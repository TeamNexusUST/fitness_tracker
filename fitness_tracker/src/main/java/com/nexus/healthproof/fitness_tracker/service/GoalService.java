package com.nexus.healthproof.fitness_tracker.service;

import com.nexus.healthproof.fitness_tracker.entity.Goal;
import com.nexus.healthproof.fitness_tracker.entity.User;
import com.nexus.healthproof.fitness_tracker.repository.GoalRepository;
import com.nexus.healthproof.fitness_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;
    private final UserRepository userRepository;

    @Transactional
    public Goal addGoal(UUID userId, Goal goal) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        goal.setUser(user);
        return goalRepository.save(goal);
    }

    public List<Goal> getGoalsByUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return goalRepository.findByUser(user);
    }

    @Transactional
    public Goal updateGoal(UUID userId, UUID goalId, Goal goalUpdates) {
        Goal existingGoal = goalRepository.findById(goalId)
                .orElseThrow(() -> new IllegalArgumentException("Goal not found"));

        if (!existingGoal.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Goal does not belong to user");
        }

        if (goalUpdates.getGoalType() != null) {
            existingGoal.setGoalType(goalUpdates.getGoalType());
        }

        if (goalUpdates.getTargetValue() != null) {
            existingGoal.setTargetValue(goalUpdates.getTargetValue());
        }

        if (goalUpdates.getStartDate() != null) {
            existingGoal.setStartDate(goalUpdates.getStartDate());
        }

        if (goalUpdates.getEndDate() != null) {
            existingGoal.setEndDate(goalUpdates.getEndDate());
        }

        return goalRepository.save(existingGoal);
    }

    @Transactional
    public void deleteGoal(UUID userId, UUID goalId) {
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new IllegalArgumentException("Goal not found"));

        if (!goal.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Goal does not belong to user");
        }

        goalRepository.delete(goal);
    }
    @Transactional
    public void deleteAll(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        goalRepository.deleteAll(goalRepository.findByUser(user));
    }
}
