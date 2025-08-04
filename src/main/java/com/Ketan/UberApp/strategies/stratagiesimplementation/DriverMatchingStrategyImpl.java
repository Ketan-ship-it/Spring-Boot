package com.Ketan.UberApp.strategies.stratagiesimplementation;

import com.Ketan.UberApp.entities.Driver;
import com.Ketan.UberApp.entities.RideRequest;
import com.Ketan.UberApp.repositories.DriverRepository;
import com.Ketan.UberApp.strategies.DriverMatchingStrategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverMatchingStrategyImpl implements DriverMatchingStrategy {

    private final DriverRepository driverRepository;

    @Override
    public List<Driver> findNearByMatchingDriver(RideRequest rideRequest) {
        return driverRepository.findMatchingDrivers(rideRequest.getPickUpLocation());
    }

}
