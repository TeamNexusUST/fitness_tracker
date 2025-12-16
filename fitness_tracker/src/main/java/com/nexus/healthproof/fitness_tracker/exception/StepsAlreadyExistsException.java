package com.nexus.healthproof.fitness_tracker.exception;

public class StepsAlreadyExistsException extends RuntimeException {
    public StepsAlreadyExistsException(String message) {
        super(message);
    }
}
