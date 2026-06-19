package com.sanket.Zyvix.Service;

import com.sanket.Zyvix.Dto.ApiResponse;
import com.sanket.Zyvix.Dto.SuggestionDto;
import com.sanket.Zyvix.Entities.SuggestionEntity;
import com.sanket.Zyvix.Repository.SuggestionsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuggestionService {
    private final SuggestionsRepository suggestionsRepository;
    private final ModelMapper modelMapper;
    public ResponseEntity<ApiResponse<SuggestionDto>>getSuggestion(String text){
        SuggestionEntity suggestionEntity=suggestionsRepository.findByText(text);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Suggestion sent",modelMapper.map(suggestionEntity,SuggestionDto.class)));
    }
}
