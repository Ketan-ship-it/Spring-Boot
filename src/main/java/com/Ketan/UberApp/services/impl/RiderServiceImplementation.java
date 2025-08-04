package com.Ketan.UberApp.services.impl;

import com.Ketan.UberApp.dto.*;
import com.Ketan.UberApp.entities.*;
import com.Ketan.UberApp.entities.enums.RideRequestStatus;
import com.Ketan.UberApp.entities.enums.RideStatus;
import com.Ketan.UberApp.exceptions.ConflictExceptions;
import com.Ketan.UberApp.exceptions.ResourceNotFoundException;
import com.Ketan.UberApp.repositories.RideRequestRepository;
import com.Ketan.UberApp.repositories.RiderRepository;
import com.Ketan.UberApp.services.DriverService;
import com.Ketan.UberApp.services.RatingService;
import com.Ketan.UberApp.services.RideService;
import com.Ketan.UberApp.services.RiderService;
import com.Ketan.UberApp.strategies.StrategyManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class RiderServiceImplementation implements RiderService {

    private final ModelMapper modelMapper;
    private final StrategyManager rideStrategyManager;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;
    private final RideService rideService;
    private final DriverService driverService;
    private final RatingService ratingService;

    @Override
    @Transactional
    /*transactional makes either the whole block is executed
    otherwise all the changes are rolled back
    Without this if any statement has a failure of execution
    others will execute and there will be mismatch of data.
    e.g. -> if there occurs an error on driverMatching (Without transactional)
            the request will be saved on database but no drivers will be informed*/
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
        Rider rider = getCurrentRider();
        RideRequest rideRequest = modelMapper.map(rideRequestDto,RideRequest.class);
        rideRequest.setRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setRider(rider);

        Double fare = rideStrategyManager.rideFareCalculationStrategy(LocalTime.now()).calculateFare(rideRequest);
        rideRequest.setFare(fare);

        RideRequest savedrideRequest = rideRequestRepository.save(rideRequest);

        List<Driver> drivers = rideStrategyManager.driverMatchingStrategy(rider.getRating()).findNearByMatchingDriver(rideRequest);
//      TODO send a notification to all the drivers here

        RideRequestDto rideRequestDto1= modelMapper.map(savedrideRequest, RideRequestDto.class);
        rideRequestDto1.setRider(modelMapper.map(rider , RiderDto.class));
        return rideRequestDto1;
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        Rider rider = getCurrentRider();
        Ride ride =rideService.getRideById(rideId);

        if(!ride.getRider().equals(rider)){
            throw new RuntimeException();
        }

        if(!ride.getRideStatus().equals(RideStatus.CANCELLED)){
            throw new RuntimeException();
        }

        rideService.updateRideStatus(ride,RideStatus.CANCELLED);
        driverService.updateDriverAvailability(ride.getDriver(),true);
        return modelMapper.map(ride, RideDto.class);
    }

    @Override
    public RatingDto rateDriver(Long rideId, RatingDto ratingDto) {
        Ride ride = rideService.getRideById(rideId);
        Rider rider = getCurrentRider();

        if (!rider.equals(ride.getRider())){
            throw new ConflictExceptions("Rider conflict issue");
        }
        if(!ride.getRideStatus().equals(RideStatus.ENDED)){
            throw new RuntimeException("Ride has not finished yet , So cannot rate now ");
        }

        Rating rating=ratingService.rateDriver(ride,ratingDto.getRating());
        ratingDto.setDriverDto(modelMapper.map(rating.getDriver(),DriverDto.class));
        return ratingDto;
    }

    @Override
    public RiderDto myProfile() {
        Rider rider = getCurrentRider();
        return modelMapper.map(rider, RiderDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Rider rider = getCurrentRider();
        return rideService.getAllRidesOfRider(rider.getId(),pageRequest)
                .map(ride -> modelMapper.map(ride, RideDto.class));
    }

    @Override
    public Rider createNewRider(User user) {
        Rider rider = Rider
                .builder()
                .user(user)
                .rating(0.0)
                .build();
        return riderRepository.save(rider);
    }

    @Override
    public Rider getCurrentRider() {
            return riderRepository.findById(1L).orElseThrow(
                    ()-> new ResourceNotFoundException("Rider Not Found"));
    }
}
