package com.nexus.healthproof.fitness_tracker.exception;

public class WeightNotFoundException extends RuntimeException {
    public WeightNotFoundException(String message) {
        super(message);
    }
}
