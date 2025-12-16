package com.nexus.healthproof.fitness_tracker.exception;

public class StepsNotFoundException extends RuntimeException {
    public StepsNotFoundException(String message) {
        super(message);
    }
}
