package com.sanket.Zyvix.Controller;

import com.sanket.Zyvix.Dto.ApiResponse;
import com.sanket.Zyvix.Dto.HealthRecDto;
import com.sanket.Zyvix.Dto.HealthRecReqDto;
import com.sanket.Zyvix.Service.AttendanceService;
import com.sanket.Zyvix.Service.HealthRecordsService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("health-records")
@CrossOrigin(origins ={
    "https://zym-strike-frontend.vercel.app",
        "http://localhost:5173"
})
public class HealthRecordsController {
    private final HealthRecordsService healthRecordsService;
    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse<HealthRecDto>> addHealthRecord(@PathVariable long id,@RequestBody HealthRecReqDto healthRecReqDto){
        return healthRecordsService.addHealthRecord(id,healthRecReqDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<List<HealthRecDto>>> getHealthRecordsOfUser(@PathVariable long id){
        return healthRecordsService.getAllRecordsById(id);
    }
    @GetMapping("/recent/{id}")
    public ResponseEntity<ApiResponse<HealthRecDto>>getRecentHealthRecordById(@PathVariable long id){
        return healthRecordsService.getRecentHealthRecordById(id);
    }

}
