package com.Ketan.UberApp.controllers;

import com.Ketan.UberApp.dto.DriverDto;
import com.Ketan.UberApp.dto.OnBoardNewDriverDto;
import com.Ketan.UberApp.dto.SignUpDto;
import com.Ketan.UberApp.dto.UserDto;
import com.Ketan.UberApp.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signUp")
    public UserDto signUp(@RequestBody SignUpDto signUpDto){
        return authService.signUp(signUpDto);
    }

    @PostMapping("/onboardNewDriver/{userId}")
    public ResponseEntity<DriverDto> onBoardNewDriver(@PathVariable Long userId,
                                                      @RequestBody OnBoardNewDriverDto onBoardNewDriverDto){
        return new ResponseEntity<>(authService.onBoardNewDriver(userId,onBoardNewDriverDto), HttpStatus.CREATED);
    }

}
