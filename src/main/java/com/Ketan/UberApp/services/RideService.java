package com.Ketan.UberApp.services;

import com.Ketan.UberApp.entities.Driver;
import com.Ketan.UberApp.entities.Ride;
import com.Ketan.UberApp.entities.RideRequest;
import com.Ketan.UberApp.entities.enums.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RideService {
    Ride getRideById(Long rideId);

    Ride createNewRide(RideRequest rideRequest, Driver driver);

    Ride updateRideStatus(Ride ride, RideStatus rideStatus);

    Page<Ride> getAllRidesOfRider(Long riderId, PageRequest pageRequest);

    Page<Driver> getAllRidesOfDriver(Long driverId,PageRequest pageRequest);
}
