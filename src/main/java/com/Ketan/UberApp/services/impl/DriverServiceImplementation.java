package com.Ketan.UberApp.services.impl;

import com.Ketan.UberApp.dto.*;
import com.Ketan.UberApp.entities.*;
import com.Ketan.UberApp.entities.enums.RideRequestStatus;
import com.Ketan.UberApp.entities.enums.RideStatus;
import com.Ketan.UberApp.exceptions.ConflictExceptions;
import com.Ketan.UberApp.exceptions.ResourceNotFoundException;
import com.Ketan.UberApp.repositories.DriverRepository;
import com.Ketan.UberApp.services.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DriverServiceImplementation implements DriverService {

    private final RideRequestService rideRequestService;
    private final DriverRepository driverRepository;
    private final RideService rideService;
    private final PaymentService paymentService;
    private final RatingService ratingService;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public RideDto acceptRide(Long rideRequestId) {
        RideRequest rideRequest = rideRequestService.findRideRequestBy(rideRequestId);
        if(!rideRequest.getRequestStatus().equals(RideRequestStatus.PENDING)){
            throw new RuntimeException("Ride request cannot be accepted , status already "+rideRequest.getRequestStatus());
        }

        Driver currentDriver = getCurrentDriver();
        if(!currentDriver.getAvailable()){
            throw new RuntimeException("Driver Unavailable");
        }

        Driver savedDriver = updateDriverAvailability(currentDriver,false);

        Ride savedride = rideService.createNewRide(rideRequest,savedDriver);
        paymentService.createNewPayment(savedride);
        RideDto rideDto = modelMapper.map(savedride, RideDto.class);
        rideDto.setDriver(new DriverDto(modelMapper.map(savedDriver.getUser(), UserDto.class),savedDriver.getRating(),savedDriver.getVehicleNumberID()));
        rideDto.setRiderDto(modelMapper.map(rideRequest.getRider(), RiderDto.class));
        return rideDto;
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);

        Driver driver = getCurrentDriver();

        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException();
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException();
        }

        rideService.updateRideStatus(ride,RideStatus.CANCELLED);
        updateDriverAvailability(driver,true);

        return modelMapper.map(ride, RideDto.class);
    }

    @Override
    public RideDto startRide(Long rideId, String OTP) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if(!ride.getDriver().equals(driver)){
            throw  new RuntimeException();
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException();
        }

        if(!OTP.equals(ride.getOtp())){
            throw  new RuntimeException();
        }

        ride.setStartedAt(LocalDateTime.now());
        Ride saveRide = rideService.updateRideStatus(ride,RideStatus.ONGOING);

        paymentService.createNewPayment(saveRide);
        ratingService.createNewRating(saveRide);

        RideDto rideDto = modelMapper.map(saveRide,RideDto.class);
        rideDto.setDriver(new DriverDto(modelMapper.map(driver.getUser(), UserDto.class),driver.getRating(),driver.getVehicleNumberID()));
        rideDto.setRiderDto(modelMapper.map(saveRide.getRider(), RiderDto.class));
        return rideDto;
    }

    @Override
    public RideDto endRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if(!ride.getDriver().equals(driver)){
            throw  new RuntimeException();
        }

        if(!ride.getRideStatus().equals(RideStatus.ONGOING)){
            throw new RuntimeException("Ride is not ongoing so cannot be cancelled");
        }

        ride.setCompletedAt(LocalDateTime.now());
        rideService.updateRideStatus(ride,RideStatus.ENDED);
        updateDriverAvailability(driver,true);

        paymentService.processPayment(ride);
        RideDto rideDto=modelMapper.map(ride,RideDto.class);
        rideDto.setDriver(new DriverDto(modelMapper.map(driver.getUser(), UserDto.class),driver.getRating(),driver.getVehicleNumberID()));
        rideDto.setRiderDto(modelMapper.map(ride.getRider(), RiderDto.class));
        return rideDto;
    }

    @Override
    public RatingDto rateRider(Long rideId, RatingDto ratingDto) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if (!driver.equals(ride.getDriver())){
            throw new ConflictExceptions("Driver conflict issue");
        }
        if(!ride.getRideStatus().equals(RideStatus.ENDED)){
            throw new RuntimeException("Ride has not finished yet , So cannot rate now ");
        }

        Rating rating=ratingService.rateRider(ride,ratingDto.getRating());
        ratingDto.setRiderDto(modelMapper.map(rating.getRider(),RiderDto.class));
        return ratingDto;
    }

    @Override
    public DriverDto getMyProfile() {
        Driver currentDriver = getCurrentDriver();
        return modelMapper.map(currentDriver, DriverDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Driver driver = getCurrentDriver();
        return rideService.getAllRidesOfDriver(driver.getId(),pageRequest).map(
                ride -> modelMapper.map(ride, RideDto.class)
        );
    }

    @Override
    public Driver getCurrentDriver() {
        return driverRepository.findById(2L).orElseThrow(
                ()-> new ResourceNotFoundException("Driver with id not found"));
    }

    @Override
    public Driver updateDriverAvailability(Driver driver, Boolean availability) {
        driver.setAvailable(availability);
        return driverRepository.save(driver);
    }
}
