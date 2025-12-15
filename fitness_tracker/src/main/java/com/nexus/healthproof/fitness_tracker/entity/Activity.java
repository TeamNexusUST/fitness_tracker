package com.nexus.healthproof.fitness_tracker.entity;

import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "activities")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Activity {

   @Id
   @GeneratedValue(generator = "UUID")
   @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
   private UUID id;

    private String name;

    private Duration duration;

    @Positive
    private Double distance; 

    @Positive
    private Double caloriesBurned;
    
    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private IntensityLevel intensityLevel;

    public enum IntensityLevel {
        LOW, MEDIUM, HIGH
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude  // Prevent infinite recursion with User
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private User user;

}