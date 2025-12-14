package com.nexus.healthproof.fitness_tracker.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.nexus.healthproof.fitness_tracker.entity.Activity;
import com.nexus.healthproof.fitness_tracker.entity.User;
import com.nexus.healthproof.fitness_tracker.repository.ActivityRepository;
import com.nexus.healthproof.fitness_tracker.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActivityService {
    
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;

    @Transactional
    public Activity create(UUID userId,Activity activity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        activity.setUser(user);
        return activityRepository.save(activity);
    }

    @Transactional
    public List<Activity> read(UUID userId) {
       User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return activityRepository.findByUser(user);
    }

    @Transactional
    public List<Activity> read(UUID userId, String name) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Activity> activities = activityRepository.findByUserAndName(user, name);
        if (activities.isEmpty()) {
            throw new IllegalArgumentException("No activities found with name: " + name + " for user: " + userId);
        }
        return activities;
    }


    @Transactional
    public Activity update(UUID userId,UUID id, Activity activity) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return activityRepository.findById(id)
                .filter(act -> act.getUser().equals(user))
                .map(act -> {
                    if (activity.getName() != null) 
                        act.setName(activity.getName());

                    if (activity.getDuration() != null) 
                        act.setDuration(activity.getDuration());

                    if (activity.getDistance() != null) 
                        act.setDistance(activity.getDistance());

                    if (activity.getCaloriesBurned() != null) 
                        act.setCaloriesBurned(activity.getCaloriesBurned());

                    if (activity.getStartDate() != null) 
                        act.setStartDate(activity.getStartDate());

                    if (activity.getEndDate() != null) 
                        act.setEndDate(activity.getEndDate());

                    if (activity.getIntensityLevel() != null) 
                        act.setIntensityLevel(activity.getIntensityLevel());

                    return activityRepository.save(act);
                })
                .orElseThrow(() -> new IllegalArgumentException("Activity not found with id: " + id));
    }

    @Transactional
    public void deleteAll(UUID userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Activity> activities = activityRepository.findByUser(user);
        if (activityRepository.count() == 0) {
            throw new IllegalArgumentException("No activities to delete");
        }
        activityRepository.deleteAll(activities);
    }

    @Transactional
    public Boolean delete(UUID userId,UUID id) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (!activityRepository.existsById(id)) {
            throw new IllegalArgumentException("Activity not found with id: " + id);
        }
        return activityRepository.findById(id)
                                 .filter(act -> act.getUser().equals(user))
                                 .map(act -> {
                                    activityRepository.delete(act);
                                    return true;
                                 })
                                 .orElse(false);
    }
}