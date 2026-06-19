package com.sanket.Zyvix.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>>handleValidation(MethodArgumentNotValidException mv
    ){
        Map<String,String>mp=new HashMap<>();
        mv.getBindingResult().getFieldErrors().forEach(error->
                mp.put(error.getField(),error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(mp);
    }

}
