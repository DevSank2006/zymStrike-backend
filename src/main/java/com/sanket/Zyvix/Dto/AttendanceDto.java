package com.sanket.Zyvix.Dto;

import com.sanket.Zyvix.Entities.UserEntity;
import com.sanket.Zyvix.Enums.AttendanceStatus;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceDto {
    private LocalDate date;
    private AttendanceStatus status;
    private String user;
}
