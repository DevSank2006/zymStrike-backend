package com.sanket.Zyvix.Controller;

import com.sanket.Zyvix.Dto.ApiResponse;
import com.sanket.Zyvix.Dto.AttendanceDto;
import com.sanket.Zyvix.Dto.AttendanceReqDto;
import com.sanket.Zyvix.Service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/attendance")
@CrossOrigin(origins = {
        "https://zym-strike.vercel.app/",
        "http://localhost:5173"
})
public class AttendanceController {
    public final AttendanceService attendanceService;
    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse<AttendanceDto>>addAttendance(@PathVariable long id, @RequestBody AttendanceReqDto attendanceReqDto){
        return attendanceService.addAttendance(id,attendanceReqDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<List<AttendanceDto>>>getAllAttendanceById(@PathVariable long id){
        return attendanceService.getAllAttendanceOfUser(id);
    }
    @GetMapping("/{id}/today-status")
    public ResponseEntity<ApiResponse<Boolean>>todayCheckedInStatus(@PathVariable long id){
        return attendanceService.todayCheckedIn(id);
    }
    @GetMapping("/streak/{id}")
    public ResponseEntity<ApiResponse<Integer>>getUserStreak(@PathVariable long id){
        return attendanceService.getStreak(id);
    }

}
