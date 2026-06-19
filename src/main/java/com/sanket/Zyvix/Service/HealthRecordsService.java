package com.sanket.Zyvix.Service;
import com.sanket.Zyvix.Dto.ApiResponse;
import com.sanket.Zyvix.Dto.HealthRecDto;
import com.sanket.Zyvix.Dto.HealthRecReqDto;
import com.sanket.Zyvix.Dto.UserDto;
import com.sanket.Zyvix.Entities.HealthRecordsEntity;
import com.sanket.Zyvix.Entities.UserEntity;
import com.sanket.Zyvix.Repository.HealthRecordsRepository;
import com.sanket.Zyvix.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HealthRecordsService {
    private final UserRepository userRepository;
    private final HealthRecordsRepository healthRecordsRepository;
    private final ModelMapper modelMapper;
    public ResponseEntity<ApiResponse<HealthRecDto>>addHealthRecord(long id,HealthRecReqDto healthRecReqDto){
        UserEntity doesUserExist=userRepository.findById(id).orElseThrow();
        Double bmiCal=(healthRecReqDto.getWeight()/Math.pow(healthRecReqDto.getHeight()/100.0,2));
        HealthRecordsEntity healthRecordsEntity=HealthRecordsEntity.builder()
                .user(doesUserExist).
                bmi(bmiCal).weight(healthRecReqDto.getWeight()).height(healthRecReqDto.getHeight()).date(LocalDateTime.now()).build();

       HealthRecordsEntity savedRecord= healthRecordsRepository.save(healthRecordsEntity);
        doesUserExist.getHealthRecordsList().add(savedRecord);
        HealthRecDto healthRecDto=HealthRecDto.builder().bmi(savedRecord.getBmi()).date(savedRecord.getDate())
                .height(savedRecord.getHeight()).weight(savedRecord.getWeight()).user(savedRecord.getUser().getName()).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Health record has been created",healthRecDto));
    }
    public ResponseEntity<ApiResponse<List<HealthRecDto>>>getAllRecordsById(long id){
        UserEntity user=userRepository.findById(id).orElseThrow();
        List<HealthRecDto>healthRecDtos=user.getHealthRecordsList().stream()
                .map(hr->HealthRecDto.builder().user(hr.getUser().getName())
                        .weight(hr.getWeight())
                        .height(hr.getHeight())
                        .bmi(hr.getBmi())
                        .date(hr.getDate()).build()).toList();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Health Records Fetched..",healthRecDtos));
    }
    public ResponseEntity<ApiResponse<HealthRecDto>>getRecentHealthRecordById(long id){
        UserEntity user=userRepository.findById(id).orElseThrow();
        HealthRecordsEntity healthRecordsEntity=healthRecordsRepository.findFirstByUserIdOrderByDateDesc(id);
        HealthRecDto healthRecDto=HealthRecDto.builder()
                .bmi(healthRecordsEntity.getBmi())
                .height(healthRecordsEntity.getHeight())
                .weight(healthRecordsEntity.getWeight()).user(healthRecordsEntity
                        .getUser().getName())
                .date(healthRecordsEntity.getDate()).build();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Health Record has been sent",healthRecDto));

    }
}
