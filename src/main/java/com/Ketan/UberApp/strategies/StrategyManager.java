package com.Ketan.UberApp.strategies;

import com.Ketan.UberApp.entities.enums.PaymentMethod;
import com.Ketan.UberApp.strategies.stratagiesimplementation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class StrategyManager {

    private final DriverMatchingStrategyImpl nearestDriverMatchingStrategy;
    private final DriverMatchingByHighestRating driverMatchingByHighestRating;
    private final RideFareDefaultFareCalculation rideFareDefaultFareCalculation;
    private final RideFareSurgePricingFareCalculationStrategy rideFareSurgePricingFareCalculationStrategy;
    private final CashPaymentStrategy cashPaymentStrategy;
    private final WalletPaymentStrategy walletPaymentStrategy;

    public DriverMatchingStrategy driverMatchingStrategy(double riderRating){
        if(riderRating >= 4.5){
            return driverMatchingByHighestRating;
        }
        else{
            return nearestDriverMatchingStrategy;
        }
    }

    public RideFareCalculationStrategy rideFareCalculationStrategy(LocalTime currTime){
        //6to9

        LocalTime surgeStartTime = LocalTime.of(18,0);
        LocalTime surgeEndTime = LocalTime.of(21,0);

        boolean isSurgeTime = currTime.isAfter(surgeStartTime) && currTime.isBefore(surgeEndTime);

        if(isSurgeTime){
            return rideFareSurgePricingFareCalculationStrategy;
        }
        else{
            return rideFareDefaultFareCalculation;
        }
    }

    public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod){
        return switch (paymentMethod){
            case WALLET -> walletPaymentStrategy;
            case CASH -> cashPaymentStrategy;
        };
    }
}
