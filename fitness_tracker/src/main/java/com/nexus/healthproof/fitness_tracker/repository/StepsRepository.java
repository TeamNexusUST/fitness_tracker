package com.nexus.healthproof.fitness_tracker.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexus.healthproof.fitness_tracker.entity.Steps;
import com.nexus.healthproof.fitness_tracker.entity.User;

public interface StepsRepository extends JpaRepository<Steps, UUID> {

    List<Steps> findByUser(User user);

    Optional<Steps> findByUserAndDate(User user, LocalDate date);

    boolean existsByUserAndDate(User user, LocalDate date);
}
