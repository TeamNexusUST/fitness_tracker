package com.nexus.healthproof.fitness_tracker.controller;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RestController;

import com.nexus.healthproof.fitness_tracker.entity.Steps;
import com.nexus.healthproof.fitness_tracker.service.StepsService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/users/{userId}/steps")
@RequiredArgsConstructor
public class StepsController {

    private final StepsService stepsService;

    // Add steps for a specific user
    @PostMapping
    public ResponseEntity<Steps> create(@PathVariable UUID userId,@RequestBody Steps steps ){
        Steps savedSteps = stepsService.create(userId,steps);

        return ResponseEntity.ok(savedSteps);
    }

    // Get all steps of a specific user
    @GetMapping
    public ResponseEntity<List<Steps>> read(@PathVariable UUID userId){
        List<Steps> readAllSteps = stepsService.read(userId);
        
        return ResponseEntity.ok(readAllSteps);
    }

    // Get a specific activity of a user
    @GetMapping("/{date}")
    public ResponseEntity<Steps> read(@PathVariable UUID userId, @PathVariable LocalDate date){
        Steps step = stepsService.read(userId,date);

        return ResponseEntity.ok(step);               
    }

    // update step of a user
    @PutMapping("/{date}")
    public ResponseEntity<Steps> update(@PathVariable UUID userId,@PathVariable LocalDate date,@RequestBody Steps steps){
        Steps step = stepsService.update(userId,date, steps);
 
        return ResponseEntity.ok(step);
    }

    // Delete all steps of a user
    @DeleteMapping("/all")
    public ResponseEntity<Steps> deleteAll(@PathVariable UUID userId){
        stepsService.deleteAll(userId);

        return ResponseEntity.noContent().build();    
    }

    // Delete a step of a user
    @DeleteMapping("/{date}")
    public ResponseEntity<Steps> delete(@PathVariable UUID userId,@PathVariable LocalDate date){
            
        return  ResponseEntity.noContent().build();
    }
}