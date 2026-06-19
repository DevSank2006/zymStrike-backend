package com.sanket.Zyvix.Controller;

import com.sanket.Zyvix.Dto.ApiResponse;
import com.sanket.Zyvix.Dto.SuggestionDto;
import com.sanket.Zyvix.Dto.SuggestionReqDto;
import com.sanket.Zyvix.Entities.SuggestionEntity;
import com.sanket.Zyvix.Service.SuggestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.prefs.AbstractPreferences;

@RestController
@RequestMapping("suggestion")
@CrossOrigin(origins = { "https://zym-strike-frontend.vercel.app",
        "http://localhost:5173"})
@RequiredArgsConstructor
public class SuggestionController {
    private final SuggestionService suggestionService;
    public ResponseEntity<ApiResponse<SuggestionDto>>getSuggestion(@PathVariable String text){
        return suggestionService.getSuggestion(text);
    }
}
