package com.Ketan.UberApp.strategies;

import com.Ketan.UberApp.entities.RideRequest;

public interface RideFareCalculationStrategy {
    public double calculateFare(RideRequest rideRequest);
}
