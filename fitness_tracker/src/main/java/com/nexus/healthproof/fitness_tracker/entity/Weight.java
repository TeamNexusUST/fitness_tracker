package com.nexus.healthproof.fitness_tracker.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "weight")
public class Weight {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    @ToString.Exclude // Prevent infinite recursion
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private User user;

    @Column(name = "DATE_RECORDED", nullable = false)
    private Date date;

    @Column(name = "POUNDS", nullable = false)
    private Double pounds;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (date == null) {
            date = new Date();
        }
    }
}
