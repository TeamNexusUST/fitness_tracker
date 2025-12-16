package com.nexus.healthproof.fitness_tracker.service;

import com.nexus.healthproof.fitness_tracker.entity.User;
import com.nexus.healthproof.fitness_tracker.exception.UserNotFoundException;
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

    @Transactional
    public User createUser(User user) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        user.setCreatedTime(now);
        user.setLastUpdatedTime(now);

        // Set parent references for child entities
        if (user.getGoals() != null) {
            user.getGoals().forEach(goal -> goal.setUser(user));
        }
        if (user.getWeights() != null) {
            user.getWeights().forEach(weight -> weight.setUser(user));
        }
        if (user.getActivities() != null) {
            user.getActivities().forEach(activity -> activity.setUser(user));
        }
        if (user.getSteps() != null) {
            user.getSteps().forEach(step -> step.setUser(user));
        }

        return userRepository.save(user);
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User updateUser(UUID userId, User userUpdates) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User does not exist with id: " + userId));

        if (userUpdates.getUsername() != null) existingUser.setUsername(userUpdates.getUsername());
        if (userUpdates.getGender() != null) existingUser.setGender(userUpdates.getGender());
        if (userUpdates.getBirthdate() != null) existingUser.setBirthdate(userUpdates.getBirthdate());
        if (userUpdates.getHeightInInches() != 0) existingUser.setHeightInInches(userUpdates.getHeightInInches());
        if (userUpdates.getActivityLevel() != 0) existingUser.setActivityLevel(userUpdates.getActivityLevel());
        if (userUpdates.getEmail() != null) existingUser.setEmail(userUpdates.getEmail());
        if (userUpdates.getPasswordHash() != null) existingUser.setPasswordHash(userUpdates.getPasswordHash());
        if (userUpdates.getFirstName() != null) existingUser.setFirstName(userUpdates.getFirstName());
        if (userUpdates.getLastName() != null) existingUser.setLastName(userUpdates.getLastName());

        existingUser.setLastUpdatedTime(new Timestamp(System.currentTimeMillis()));

        return userRepository.save(existingUser);
    }

    @Transactional
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User does not exist with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
