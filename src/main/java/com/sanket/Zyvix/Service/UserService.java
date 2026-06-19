package com.sanket.Zyvix.Service;

import com.sanket.Zyvix.Dto.*;
import com.sanket.Zyvix.Entities.HealthRecordsEntity;
import com.sanket.Zyvix.Entities.UserEntity;
import com.sanket.Zyvix.Repository.HealthRecordsRepository;
import com.sanket.Zyvix.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class UserService {
    public final UserRepository userRepository;
    public final ModelMapper modelMapper;
    public final HealthRecordsService healthRecordsService;
    public final PasswordEncoder passwordEncoder;
public UserService(UserRepository userRepository, ModelMapper modelMapper, HealthRecordsService healthRecordsService, PasswordEncoder passwordEncoder){
    this.userRepository=userRepository;
    this.modelMapper=modelMapper;
    this.healthRecordsService=healthRecordsService;
    this.passwordEncoder = passwordEncoder;
}
    public ResponseEntity<ApiResponse<UserDto>>createUser(UserReqDto user){
        Optional<UserEntity> checkIfExist=userRepository.findByEmail(user.getEmail());
        if(checkIfExist.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<>("Account Already Exist",null));
        }else{
            UserEntity userEntity=modelMapper.map(user,UserEntity.class);
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userEntity.setCreatedAt(LocalDate.now());
            UserEntity savedUser=userRepository.save(userEntity);
            UserDto userDto=UserDto.builder()
                    .id(savedUser.getId())
                    .name(savedUser.getName())
                    .email(savedUser.getEmail())
                    .age(savedUser.getAge())
                    .weight(savedUser.getWeight())
                    .height(savedUser.getHeight())
                    .goal(savedUser.getGoal()).build();

            //health records

            HealthRecReqDto healthRecReqDto=HealthRecReqDto.
                    builder()
                    .user(savedUser)
                    .date(LocalDateTime.now())
                    .bmi(null)
                    .height(savedUser.getHeight())
                    .weight(savedUser.getWeight()).build();
            healthRecordsService.addHealthRecord(savedUser.getId(),healthRecReqDto);

            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Account has been created",userDto));
        }
    }
    public ResponseEntity<ApiResponse<UserDto>>getUserById(long id){
        Optional<UserEntity>user=userRepository.findById(id);
        if(user.isPresent()){
            UserEntity userFromDB=user.get();
            //health records list
            List<AttendanceDto> attendanceDto= userFromDB.getAttendanceList().stream().map(atd->AttendanceDto
                    .builder().user(atd.getUser().getName()).date(atd.getDate()).status(atd.getStatus()).build()).toList();

            //health records list
            List<HealthRecDto>healthRecDtos=userFromDB.getHealthRecordsList().stream()
                    .map(health->HealthRecDto.builder().user(health.getUser()
                                    .getName()).bmi(health.getBmi()).weight(health.getWeight())
                            .date(health.getDate()).height(health.getHeight()).build()).toList();
            UserDto userDto=UserDto.builder()
                    .id(userFromDB.getId())
                    .name(userFromDB.getName())
                    .email(userFromDB.getEmail())
                    .age(userFromDB.getAge())
                    .weight(userFromDB.getWeight())
                    .height(userFromDB.getHeight())
                    .goal(userFromDB.getGoal()).attendanceList(attendanceDto).healthRecords(healthRecDtos).
                    build();

            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("User fetched successfully",userDto));
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("User not found",null));
        }
    }

    public ResponseEntity<ApiResponse<String>>deleteUser(long id){
        Optional<UserEntity> getUserFromDB=userRepository.findById(id);
        if(getUserFromDB.isPresent()){
            userRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Account Has been deleted",null));
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("Account does not exist",null));
        }
    }
    public ResponseEntity<ApiResponse<UserDto>>updateUser(long id, UserEntity user){
        Optional<UserEntity>checkIfExist=userRepository.findById(id);
        if(checkIfExist.isPresent()){
            UserEntity updatedUser=checkIfExist.get();
          if(user.getName()!=null){
              updatedUser.setName(user.getName());
          }
            if(user.getAge() != null){
                updatedUser.setAge(user.getAge());
            }

            if(user.getGoal() != null){
                updatedUser.setGoal(user.getGoal());
            }

            if(user.getHeight()!= null){
                updatedUser.setHeight(user.getHeight());
            }


            userRepository.save(updatedUser);

            UserDto userDto=UserDto.builder()
                    .name(updatedUser.getName())
                    .email(updatedUser.getEmail())
                    .age(updatedUser.getAge())
                    .weight(updatedUser.getWeight())
                    .height(updatedUser.getHeight())
                    .goal(updatedUser.getGoal())
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Details has been updated successfully",userDto));

        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("Cannot find ID "+id+" Give valid ID",null));
        }
    }

    public ResponseEntity<ApiResponse<Long>> getIdByEmail(String email) {
        UserEntity user=userRepository.findByEmail(email).orElseThrow();

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("User id fetched",user.getId()));

    }

    public ResponseEntity<ApiResponse<UserDto>> getUserByName(String email) {
        Optional<UserEntity> user=userRepository.findByEmail(email);
        UserDto userDto=modelMapper.map(user,UserDto.class);
        userDto.setAttendanceList(null);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("user fetched..",userDto));

    }
}
