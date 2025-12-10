package com.nexus.healthproof.fitness_tracker.service;

import com.nexus.healthproof.fitness_tracker.entity.User;
import com.nexus.healthproof.fitness_tracker.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // Create a new user
    public User createUser(User user) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        user.setCreatedTime(now);
        user.setLastUpdatedTime(now);
        return userRepository.save(user);
    }

    // Get user by ID
    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    // Get user by username
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Update user (supports partial updates)
    public User updateUser(UUID userId, User userUpdates) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User does not exist"));

        // Only update fields that are not null
        if (userUpdates.getUsername() != null) existingUser.setUsername(userUpdates.getUsername());
        if (userUpdates.getGender() != null) existingUser.setGender(userUpdates.getGender());
        if (userUpdates.getBirthdate() != null) existingUser.setBirthdate(userUpdates.getBirthdate());
        if (userUpdates.getHeightInInches() != 0) existingUser.setHeightInInches(userUpdates.getHeightInInches());
        if (userUpdates.getActivityLevel() != 0) existingUser.setActivityLevel(userUpdates.getActivityLevel());
        if (userUpdates.getEmail() != null) existingUser.setEmail(userUpdates.getEmail());
        if (userUpdates.getPasswordHash() != null) existingUser.setPasswordHash(userUpdates.getPasswordHash());
        if (userUpdates.getFirstName() != null) existingUser.setFirstName(userUpdates.getFirstName());
        if (userUpdates.getLastName() != null) existingUser.setLastName(userUpdates.getLastName());

        // Update timestamp
        existingUser.setLastUpdatedTime(new Timestamp(System.currentTimeMillis()));

        return userRepository.save(existingUser);
    }

    // Delete user
    @Transactional
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User does not exist");
        }
        userRepository.deleteById(id);
    }
}
