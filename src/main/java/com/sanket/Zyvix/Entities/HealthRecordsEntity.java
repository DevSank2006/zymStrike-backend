package com.sanket.Zyvix.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class HealthRecordsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @Column(nullable = false)
    private Double weight;
    @Column(nullable = false)
    private Double height;
    @Column(nullable = false)
    private Double bmi;
}
