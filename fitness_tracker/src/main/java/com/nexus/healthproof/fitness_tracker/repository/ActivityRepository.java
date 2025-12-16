package com.nexus.healthproof.fitness_tracker.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nexus.healthproof.fitness_tracker.entity.Activity;
import com.nexus.healthproof.fitness_tracker.entity.User;


@Repository
public interface ActivityRepository extends JpaRepository<Activity,UUID>{
    List<Activity> findByUser(User user);

    List<Activity> findByUserAndName(User user,String name);
}