package com.Ketan.UberApp.advices;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponse <T>{
    @JsonFormat(pattern = "hh:mm:ss dd-mm-yyyy")
    private LocalDateTime timeStamp;
    private T data;
    private ApiError errors;

    public ApiResponse(){
        this.timeStamp=LocalDateTime.now();
    }

    public ApiResponse(T data){
        this();
        this.data=data;
    }

    public ApiResponse(ApiError errors){
        this();
        this.errors=errors;
    }

}
