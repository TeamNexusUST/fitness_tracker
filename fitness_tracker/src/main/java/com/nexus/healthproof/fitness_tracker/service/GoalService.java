package com.nexus.healthproof.fitness_tracker.service;

import com.nexus.healthproof.fitness_tracker.entity.Goal;
import com.nexus.healthproof.fitness_tracker.entity.User;
import com.nexus.healthproof.fitness_tracker.exception.GoalNotFoundException;
import com.nexus.healthproof.fitness_tracker.exception.UserNotFoundException;
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
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        goal.setUser(user);
        return goalRepository.save(goal);
    }

    public List<Goal> getGoalsByUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        return goalRepository.findByUser(user);
    }

    @Transactional
    public Goal updateGoal(UUID userId, UUID goalId, Goal goalUpdates) {
        Goal existingGoal = goalRepository.findById(goalId)
                .orElseThrow(() -> new GoalNotFoundException("Goal not found with id: " + goalId));
        if (!existingGoal.getUser().getId().equals(userId)) {
            throw new GoalNotFoundException("Goal does not belong to user: " + userId);
        }
        if (goalUpdates.getGoalType() != null) existingGoal.setGoalType(goalUpdates.getGoalType());
        if (goalUpdates.getTargetValue() != null) existingGoal.setTargetValue(goalUpdates.getTargetValue());
        if (goalUpdates.getStartDate() != null) existingGoal.setStartDate(goalUpdates.getStartDate());
        if (goalUpdates.getEndDate() != null) existingGoal.setEndDate(goalUpdates.getEndDate());
        return goalRepository.save(existingGoal);
    }

    @Transactional
    public void deleteGoal(UUID userId, UUID goalId) {
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new GoalNotFoundException("Goal not found with id: " + goalId));
        if (!goal.getUser().getId().equals(userId)) {
            throw new GoalNotFoundException("Goal does not belong to user: " + userId);
        }
        goalRepository.delete(goal);
    }

    @Transactional
    public void deleteAll(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        goalRepository.deleteAll(goalRepository.findByUser(user));
    }
}
