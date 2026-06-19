package com.sanket.Zyvix.Dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
        private Long id;
        private String name;
        private String email;
        private int age;
        private double height;
        private double weight;
        private String goal;
//        private int streakDays;
        private List<AttendanceDto>attendanceList;
        private List<HealthRecDto>healthRecords;
}
