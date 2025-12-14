package com.nexus.healthproof.fitness_tracker.entity;

import lombok.*;
import jakarta.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    public enum Gender { MALE, FEMALE }

    public enum ActivityLevel {
        SEDENTARY(1.25),
        LIGHTLY_ACTIVE(1.3),
        MODERATELY_ACTIVE(1.5),
        VERY_ACTIVE(1.7),
        EXTREMELY_ACTIVE(2.0);

        private final double value;
        ActivityLevel(double value) { this.value = value; }
        public double getValue() { return value; }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, length = 6)
    private String gender;

    @Column(nullable = false)
    private Date birthdate;

    @Column(nullable = false)
    private double heightInInches;

    @Column(nullable = false)
    private double activityLevel;

    @Column(nullable = false)
    private String email;

    private String passwordHash;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Timestamp createdTime;

    @Column(nullable = false)
    private Timestamp lastUpdatedTime;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Builder.Default
    private Set<Weight> weights = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Builder.Default
    private Set<Goal> goals = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Builder.Default
    private Set<Activity> activities = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Builder.Default
    private Set<Steps> steps = new HashSet<>();
}
