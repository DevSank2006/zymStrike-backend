package com.sanket.Zyvix.Entities;

import com.sanket.Zyvix.Enums.AttendanceStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;
   @ManyToOne()
   @JoinColumn(name = "user_id")
    private UserEntity user;
}
