package com.nexus.healthproof.fitness_tracker.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.nexus.healthproof.fitness_tracker.entity.Steps;
import com.nexus.healthproof.fitness_tracker.entity.User;
import com.nexus.healthproof.fitness_tracker.repository.StepsRepository;
import com.nexus.healthproof.fitness_tracker.repository.UserRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StepsService {

    private final StepsRepository stepsRepository;
    private final UserRepository userRepository;

    @Transactional
    public Steps create(UUID userId, Steps steps) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (stepsRepository.existsByUserAndDate(user, steps.getDate())) {
            throw new IllegalArgumentException("Steps already recorded for this date");
        }

        steps.setUser(user);
        return stepsRepository.save(steps);
    }

    @Transactional(readOnly = true)
    public List<Steps> read(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return stepsRepository.findByUser(user);
    }

    @Transactional(readOnly = true)
    public Steps read(UUID userId, LocalDate date) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return stepsRepository.findByUserAndDate(user, date)
                .orElseThrow(() ->
                        new IllegalArgumentException("Steps not found for date " + date));
    }

    @Transactional
    public Steps update(UUID userId, LocalDate date, Steps steps) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Steps existing = stepsRepository.findByUserAndDate(user, date)
                .orElseThrow(() ->
                        new IllegalArgumentException("Steps not found for date " + date));

        if (steps.getStepCount() != null)
            existing.setStepCount(steps.getStepCount());

        if (steps.getDailyGoal() != null)
            existing.setDailyGoal(steps.getDailyGoal());

        if (steps.getDistanceCovered() != null)
            existing.setDistanceCovered(steps.getDistanceCovered());

        if (steps.getActiveMinutes() != null)
            existing.setActiveMinutes(steps.getActiveMinutes());

        return stepsRepository.save(existing);
    }

    @Transactional
    public void delete(UUID userId, LocalDate date) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Steps steps = stepsRepository.findByUserAndDate(user, date)
                .orElseThrow(() ->
                        new IllegalArgumentException("Steps not found for date " + date));

        stepsRepository.delete(steps);
    }

    @Transactional
    public void deleteAll(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        stepsRepository.deleteAll(stepsRepository.findByUser(user));
    }
}
