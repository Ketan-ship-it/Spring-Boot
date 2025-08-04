package com.Ketan.UberApp.services.impl;

import com.Ketan.UberApp.entities.RideRequest;
import com.Ketan.UberApp.exceptions.ResourceNotFoundException;
import com.Ketan.UberApp.repositories.RideRequestRepository;
import com.Ketan.UberApp.services.RideRequestService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideRequestServiceImpl implements RideRequestService {

    private final RideRequestRepository rideRequestRepository;

    @Override
    public RideRequest findRideRequestBy(Long RideRequestId){
        return rideRequestRepository.findById(RideRequestId).orElseThrow(
                () -> new ResourceNotFoundException("Ride Request Not found")
        );
    }

    @Override
    public RideRequest update(RideRequest rideRequest) {
        rideRequestRepository.findById(rideRequest.getId()).orElseThrow(()->new ResourceNotFoundException("Ride Request not found"));
        return rideRequestRepository.save(rideRequest);
    }
}
