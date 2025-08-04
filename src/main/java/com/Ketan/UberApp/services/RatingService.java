package com.Ketan.UberApp.services;

import com.Ketan.UberApp.entities.Rating;
import com.Ketan.UberApp.entities.Ride;

public interface RatingService {
    //rate Driver
    Rating rateDriver(Ride ride, Double rating);

    void createNewRating(Ride saveRide);

    //rate Rider
    Rating rateRider(Ride ride, Double rating);
}
