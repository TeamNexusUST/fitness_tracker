package com.nexus.healthproof.fitness_tracker.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.nexus.healthproof.fitness_tracker.entity.User;
import com.nexus.healthproof.fitness_tracker.entity.Weight;
import com.nexus.healthproof.fitness_tracker.repository.WeightRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WeightService {

    private final WeightRepository weightRepository;

    public Weight addWeight(Weight weight) {
        return weightRepository.save(weight);
    }

    public List<Weight> getWeightsByUser(User user) {
        return weightRepository.findByUser(user);
    }

    public void deleteWeight(UUID id) {
        weightRepository.deleteById(id);
    }
}
