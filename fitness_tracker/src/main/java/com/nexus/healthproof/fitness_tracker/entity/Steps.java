package com.nexus.healthproof.fitness_tracker.entity;

import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(
        name = "steps",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"user_id", "date"})
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Steps {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private LocalDate date;

    @Positive
    private Long stepCount;

    @Positive
    private Long dailyGoal;

    @Positive
    private Double distanceCovered;

    private Duration activeMinutes;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;

    @PrePersist
    public void prePersist() {
        if (date == null) {
            date = LocalDate.now();
        }
    }

}
