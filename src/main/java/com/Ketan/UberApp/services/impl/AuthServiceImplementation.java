package com.Ketan.UberApp.services.impl;

import com.Ketan.UberApp.dto.DriverDto;
import com.Ketan.UberApp.dto.OnBoardNewDriverDto;
import com.Ketan.UberApp.dto.SignUpDto;
import com.Ketan.UberApp.dto.UserDto;
import com.Ketan.UberApp.entities.Driver;
import com.Ketan.UberApp.entities.User;
import com.Ketan.UberApp.entities.enums.Role;
import com.Ketan.UberApp.exceptions.ConflictExceptions;
import com.Ketan.UberApp.exceptions.ResourceNotFoundException;
import com.Ketan.UberApp.repositories.DriverRepository;
import com.Ketan.UberApp.repositories.UserRepository;
import com.Ketan.UberApp.services.AuthService;
import com.Ketan.UberApp.services.RiderService;
import com.Ketan.UberApp.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImplementation implements AuthService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final RiderService riderService;
    private final WalletService walletService;

    @Override
    public String login(String email, String password) {
        return "";
    }

    @Override
    public UserDto signUp(SignUpDto signUpDto) {
        userRepository.findByEmail(signUpDto.getEmail()).orElseThrow(() ->
                        new ConflictExceptions("Cannot signUp again , User with this email "+signUpDto.getEmail()+"already exists"));

        User user1=modelMapper.map(signUpDto, User.class);
        user1.setRoles(Set.of(Role.RIDER));
        User savedUser = userRepository.save(user1);
        riderService.createNewRider(savedUser);
        walletService.createNewWallet(savedUser);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public DriverDto onBoardNewDriver(Long userId, OnBoardNewDriverDto onBoardNewDriverDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User Not Found for id : "+userId)
        );
        if (user.getRoles().contains(Role.DRIVER)){
            throw  new RuntimeException("User is already a Rider");
        }

        user.getRoles().add(Role.DRIVER);
        userRepository.save(user);


        Driver driver = Driver.builder()
                .user(user)
                .VehicleNumberID(onBoardNewDriverDto.getVehicleId())
                .rating(0.0)
                .available(true)
                .build();
        driverRepository.save(driver);
        return modelMapper.map(driver,DriverDto.class);
    }
}
