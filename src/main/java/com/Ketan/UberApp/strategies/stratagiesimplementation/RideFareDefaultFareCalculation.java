package com.Ketan.UberApp.strategies.stratagiesimplementation;

import com.Ketan.UberApp.entities.RideRequest;
import com.Ketan.UberApp.services.DistanceService;
import com.Ketan.UberApp.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.Ketan.UberApp.services.DistanceService.PRICE_PER_KM;

@Service
@RequiredArgsConstructor
public class RideFareDefaultFareCalculation implements RideFareCalculationStrategy {

    private final DistanceService distanceService;

    @Override
    public double calculateFare(RideRequest rideRequest) {
        Double distance = distanceService.calculateDistance(rideRequest.getPickUpLocation(),
                rideRequest.getDropOffLocation());
        return distance*PRICE_PER_KM;

    }
}
