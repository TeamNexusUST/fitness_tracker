package com.nexus.healthproof.fitness_tracker.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.nexus.healthproof.fitness_tracker.entity.Goal;
import com.nexus.healthproof.fitness_tracker.entity.User;
import com.nexus.healthproof.fitness_tracker.repository.GoalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;

    public Goal addGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    public List<Goal> getGoalsByUser(User user) {
        return goalRepository.findByUser(user);
    }

    public void deleteGoal(UUID id) {
        goalRepository.deleteById(id);
    }
}
