package com.sanket.Zyvix.Dto;

import com.sanket.Zyvix.Enums.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceReqDto {
    @NotNull
    private LocalDate date;
    @NotNull
    private AttendanceStatus status;
}
