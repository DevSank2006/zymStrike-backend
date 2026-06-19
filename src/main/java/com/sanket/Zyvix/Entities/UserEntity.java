package com.sanket.Zyvix.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false,length = 50)
    private String name;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false,unique = true)
    private String password;
    @Column(nullable = false)
    @Min(10)
    @Max(100)
    private Integer age;
    @Column(nullable = false)
    private Double height;
    @Column(nullable = false)
    private Double weight;
    @Column(nullable = false)
    private String goal;

//    private Integer streakDays;
    @Column(updatable = false)
    @CreatedDate
    private LocalDate createdAt;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<AttendanceEntity> attendanceList=new ArrayList<>();
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    List<HealthRecordsEntity> healthRecordsList=new ArrayList<>();
}
