package com.nexus.healthproof.fitness_tracker.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nexus.healthproof.fitness_tracker.entity.User;
import com.nexus.healthproof.fitness_tracker.entity.Weight;

@Repository
public interface WeightRepository extends JpaRepository<Weight, UUID> {
    List<Weight> findByUser(User user);
}
