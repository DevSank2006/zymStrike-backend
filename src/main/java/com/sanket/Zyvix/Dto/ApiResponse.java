package com.sanket.Zyvix.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    String message;
    private T data;
    public ApiResponse(String message,T data){
        this.message=message;
        this.data=data;
    }
}
