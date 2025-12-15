package com.nexus.healthproof.fitness_tracker.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nexus.healthproof.fitness_tracker.entity.Activity;
import com.nexus.healthproof.fitness_tracker.service.ActivityService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/users/{userId}/activity")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    // Add activity for a specific user
    @PostMapping
    public ResponseEntity<Activity> create(@PathVariable UUID userId,@RequestBody Activity activity){
        Activity savedActivity = activityService.create(userId,activity);

        return ResponseEntity.ok(savedActivity);
    }

    // Get all activities of a specific user
    @GetMapping
    public ResponseEntity<List<Activity>> read(@PathVariable UUID userId){
        List<Activity> readAllActivity = activityService.read(userId);

        return ResponseEntity.ok(readAllActivity);
    }

    // Get a specific activity of a user
    @GetMapping("/search")
    public ResponseEntity<List<Activity>> read(@RequestParam UUID userId,@RequestParam String name){
        List<Activity> activities = activityService.read(userId,name);

        return ResponseEntity.ok(activities);
    }

    // update a activity of a user
    @PutMapping("/{activityId}")
    public ResponseEntity<Activity> update(@PathVariable UUID userId,@PathVariable UUID activityId,@RequestBody Activity activity){
        Activity act=activityService.update(userId,activityId, activity);

        return ResponseEntity.ok(act);

    }

    // Delete all activities of a user
    @DeleteMapping("/all")
    public ResponseEntity<Activity> deleteALl(@PathVariable("userId") UUID userId){
        activityService.deleteAll(userId);

        return ResponseEntity.noContent().build();
    }

    // Delete a actvity of a user
    @DeleteMapping("/{activityId}")
    public ResponseEntity<Activity> delete(@PathVariable UUID userId,@PathVariable UUID activityId){
            
        return ResponseEntity.noContent().build();
    }
}