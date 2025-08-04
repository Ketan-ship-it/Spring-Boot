package com.Ketan.UberApp.strategies;

import com.Ketan.UberApp.entities.Driver;
import com.Ketan.UberApp.entities.RideRequest;

import java.util.List;

public interface DriverMatchingStrategy {
    public List<Driver> findNearByMatchingDriver(RideRequest rideRequest);
}
