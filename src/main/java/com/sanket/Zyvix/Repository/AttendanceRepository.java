package com.sanket.Zyvix.Repository;

import com.sanket.Zyvix.Entities.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface AttendanceRepository extends JpaRepository<AttendanceEntity,Long> {
    boolean existsByUserIdAndDate(Long userId, LocalDate date);
    @Query("select a.date from AttendanceEntity a where a.user.id=:userId")
    Set<LocalDate> findAttendanceDatesByUserId(@Param("userId") Long userId);
}
