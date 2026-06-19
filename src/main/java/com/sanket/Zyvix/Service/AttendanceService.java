package com.sanket.Zyvix.Service;

import com.sanket.Zyvix.Dto.ApiResponse;
import com.sanket.Zyvix.Dto.AttendanceDto;
import com.sanket.Zyvix.Dto.AttendanceReqDto;
import com.sanket.Zyvix.Dto.UserDto;
import com.sanket.Zyvix.Entities.AttendanceEntity;
import com.sanket.Zyvix.Entities.UserEntity;
import com.sanket.Zyvix.Repository.AttendanceRepository;
import com.sanket.Zyvix.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AttendanceService {
    public final AttendanceRepository attendanceRepository;
    public final UserRepository userRepository;
    public final ModelMapper modelMapper;
    public AttendanceService(AttendanceRepository attendanceRepository,UserRepository userRepository,ModelMapper modelMapper){
        this.modelMapper=modelMapper;
        this.userRepository=userRepository;
        this.attendanceRepository=attendanceRepository;
    }
    public ResponseEntity<ApiResponse<AttendanceDto>>addAttendance(long userId, AttendanceReqDto attendanceReqDto){
        Optional<UserEntity>doesUserExist=userRepository.findById(userId);

        if(doesUserExist.isPresent()){
            UserEntity userEntity=doesUserExist.get();
            if(!attendanceRepository.existsByUserIdAndDate(userId,attendanceReqDto.getDate()) && attendanceReqDto.getDate().equals(LocalDate.now())){
                AttendanceEntity attendanceEntity=modelMapper.map(attendanceReqDto,AttendanceEntity.class);
                attendanceEntity.setUser(userEntity);
                userEntity.getAttendanceList().add(attendanceEntity);
                attendanceRepository.save(attendanceEntity);
                AttendanceDto attendanceDto=modelMapper.map(attendanceEntity,AttendanceDto.class);
                attendanceDto.setUser(attendanceEntity.getUser().getName());
                return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Attendance Marked",attendanceDto));
            }else{
                return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Cannot mark attendance of same date again or future date",null));
            }

        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("Userid not found",null));
        }
    }

    public ResponseEntity<ApiResponse<List<AttendanceDto>>>getAllAttendanceOfUser(long userId){
        Optional<UserEntity>doesUserExist=userRepository.findById(userId);
        if(doesUserExist.isPresent()){
            UserDto userDto=modelMapper.map(doesUserExist.get(),UserDto.class);
            userDto.setName(doesUserExist.get().getName());
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("User Attendance",userDto.getAttendanceList()));
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("User not found",null));

        }
    }
    public ResponseEntity<ApiResponse<Boolean>>todayCheckedIn(long id) {
        Optional<UserEntity>isUserExist=userRepository.findById(id);
        if(isUserExist.isPresent()){
            UserEntity user=isUserExist.get();
            Boolean status=attendanceRepository.existsByUserIdAndDate(id, LocalDate.now());
            return ResponseEntity.ok(new ApiResponse<>("Today checked in status",status));
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("User not found",null));
        }
    }
    public ResponseEntity<ApiResponse<Integer>>getStreak(long id){
        UserEntity user=userRepository.findById(id).orElseThrow();
        Integer streak=0;
        LocalDate today=LocalDate.now();
        Set<LocalDate> allDates=attendanceRepository.findAttendanceDatesByUserId(id);
      while(allDates.contains(today)){
          streak++;
          today=today.minusDays(1);
      }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Streak is Calculated",streak));

    }
}
