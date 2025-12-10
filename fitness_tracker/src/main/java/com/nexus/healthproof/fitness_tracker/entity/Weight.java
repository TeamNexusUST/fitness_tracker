package com.nexus.healthproof.fitness_tracker.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.util.Date;
import java.util.UUID;
import com.nexus.healthproof.fitness_tracker.entity.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


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
