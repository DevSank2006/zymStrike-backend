package com.sanket.Zyvix.Dto;

import com.sanket.Zyvix.Entities.HealthRecordsEntity;
import com.sanket.Zyvix.Entities.UserEntity;
import lombok.*;
import org.hibernate.validator.constraints.pl.REGON;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HealthRecReqDto {
    private LocalDateTime date;
    private UserEntity user;
    private Double weight;
    private Double height;
    private Double bmi;
}
