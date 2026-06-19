package com.sanket.Zyvix.Dto;

import com.sanket.Zyvix.Entities.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HealthRecDto {
    private LocalDateTime date;
    private String user;
    private Double weight;
    private Double height;
    private Double bmi;


}
