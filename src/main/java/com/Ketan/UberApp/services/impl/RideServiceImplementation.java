package com.Ketan.UberApp.services.impl;

import com.Ketan.UberApp.entities.Driver;
import com.Ketan.UberApp.entities.Ride;
import com.Ketan.UberApp.entities.RideRequest;
import com.Ketan.UberApp.entities.enums.RideRequestStatus;
import com.Ketan.UberApp.entities.enums.RideStatus;
import com.Ketan.UberApp.exceptions.ResourceNotFoundException;
import com.Ketan.UberApp.repositories.RideRepository;
import com.Ketan.UberApp.services.RideRequestService;
import com.Ketan.UberApp.services.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class RideServiceImplementation implements RideService {

    private final RideRepository rideRepository;
    private final RideRequestService rideRequestService;
    private final ModelMapper modelMapper;

    @Override
    public Ride getRideById(Long rideId) {
        return rideRepository.findById(rideId)
                .orElseThrow(()->new ResourceNotFoundException("No Such Ride was found"));
    }

    @Override
    public Ride createNewRide(RideRequest rideRequest, Driver driver) {
        rideRequest.setRequestStatus(RideRequestStatus.CONFIRMED);

        Ride ride = Ride.builder()
                .pickUpLocation(rideRequest.getPickUpLocation())
                .dropOffLocation(rideRequest.getDropOffLocation())
                .rideStatus(RideStatus.CONFIRMED)
                .driver(driver)
                .rider(rideRequest.getRider())
                .fare(rideRequest.getFare())
                .otp(generateRandomOTP())
                .paymentMethod(rideRequest.getPaymentMethod())
                .build();
//        Ride ride = modelMapper.map(rideRequest,Ride.class);
//        ride.setRideStatus(RideStatus.CONFIRMED);
//        ride.setDriver(driver);
//        ride.setOtp(generateRandomOTP());

        rideRequestService.update(rideRequest);
        return rideRepository.save(ride);
    }

    private String generateRandomOTP(){
        SecureRandom random = new SecureRandom();
        int otpInt = random.nextInt(1000,9999);
        return String.format("%04d",otpInt);
    }

    @Override
    public Ride updateRideStatus(Ride ride, RideStatus rideStatus) {
        ride.setRideStatus(rideStatus);
        return rideRepository.save(ride);
    }

    @Override
    public Page<Ride> getAllRidesOfRider(Long riderId, PageRequest pageRequest) {
        return null;
    }

    @Override
    public Page<Driver> getAllRidesOfDriver(Long driverId, PageRequest pageRequest) {
        return null;
    }
}
