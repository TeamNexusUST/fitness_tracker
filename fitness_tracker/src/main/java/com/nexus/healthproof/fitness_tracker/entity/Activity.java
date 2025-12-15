package com.nexus.healthproof.fitness_tracker.entity;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;



@Entity
@Table(name = "activities")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Activity {

   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
   private UUID id;

    @NotBlank
    private String name;

    private Duration duration;

    @Min(0)
    private Double distance; 

    @Min(0)
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

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (intensityLevel == null) {
            intensityLevel = IntensityLevel.MEDIUM;
        }
    }


}