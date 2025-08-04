package com.Ketan.UberApp.services;

import com.Ketan.UberApp.dto.DriverDto;
import com.Ketan.UberApp.dto.OnBoardNewDriverDto;
import com.Ketan.UberApp.dto.SignUpDto;
import com.Ketan.UberApp.dto.UserDto;

public interface AuthService {
    String login(String email,String password);
    UserDto signUp(SignUpDto signUpDto);
    DriverDto onBoardNewDriver(Long UserId, OnBoardNewDriverDto onBoardNewDriverDto);
}
