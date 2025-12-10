package com.nexus.healthproof.fitness_tracker.entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import jakarta.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

    public enum Gender { MALE, FEMALE }
    public enum ActivityLevel { SEDENTARY(1.25), LIGHTLY_ACTIVE(1.3), MODERATELY_ACTIVE(1.5), VERY_ACTIVE(1.7), EXTREMELY_ACTIVE(2.0); 
        private double value; 
        ActivityLevel(double value){ this.value=value; } 
        public double getValue(){ return value; }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="ID", columnDefinition="BINARY(16)")
    private UUID id;

    @Column(name="USERNAME", nullable=false, unique=true)
    private String username;

    @Column(name="GENDER", length=6, nullable=false)
    private String gender;

    @Column(name = "BIRTHDATE", nullable = false)
    private Date birthdate;

    @Column(name = "HEIGHT_IN_INCHES", nullable = false)
    private double heightInInches;

    @Column(name = "ACTIVITY_LEVEL", nullable = false)
    private double activityLevel;

    @Column(name = "EMAIL", length = 100, nullable = false)
    private String email;

    @Column(name = "PASSWORD_HASH", length = 100)
    private String passwordHash;

    @Column(name = "FIRST_NAME", length = 30, nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", length = 20, nullable = false)
    private String lastName;

    @Column(name = "CREATED_TIME", nullable = false)
    private Timestamp createdTime;

    @Column(name = "LAST_UPDATED_TIME", nullable = false)
    private Timestamp lastUpdatedTime;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Weight> weights = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Goal> goals = new HashSet<>();
}
