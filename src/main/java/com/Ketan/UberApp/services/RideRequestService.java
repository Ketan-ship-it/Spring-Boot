package com.Ketan.UberApp.services;

import com.Ketan.UberApp.entities.RideRequest;
import com.Ketan.UberApp.exceptions.ResourceNotFoundException;

public interface RideRequestService {
        RideRequest findRideRequestBy(Long RideRequestId) throws ResourceNotFoundException;
        RideRequest update(RideRequest rideRequest);
}
