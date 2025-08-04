package com.Ketan.UberApp.services.impl;

import com.Ketan.UberApp.entities.Driver;
import com.Ketan.UberApp.entities.Rating;
import com.Ketan.UberApp.entities.Ride;
import com.Ketan.UberApp.entities.Rider;
import com.Ketan.UberApp.repositories.DriverRepository;
import com.Ketan.UberApp.repositories.RatingRepository;
import com.Ketan.UberApp.repositories.RiderRepository;
import com.Ketan.UberApp.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImplementation implements RatingService {

    private final RatingRepository ratingRepository;
    private final DriverRepository driverRepository;
    private final RiderRepository riderRepository;

    @Override
    public Rating rateDriver(Ride ride, Double rating) {
        Driver driver = ride.getDriver();;
        Rating rating1 = ratingRepository.findByRide(ride).orElseThrow(
                () -> new RuntimeException("pata nhi kyun")
                );
        if(rating1.getRating_driver()!=null ){
            throw new RuntimeException("Already rated driver for this ride ");
        }
        rating1.setRating_driver(rating);
        ratingRepository.save(rating1);

        Double newRating = ratingRepository.findByDriver(driver)
                .stream()
                .mapToDouble(Rating::getRating_driver)
                .average().orElse(0.0);
        driver.setRating(newRating);
        driverRepository.save(driver);
        return rating1;
    }

    @Override
    public Rating rateRider(Ride ride, Double rating) {
        Rider rider = ride.getRider();
        Rating rating1 = ratingRepository.findByRide(ride).orElseThrow(
                () -> new RuntimeException("pata nhi kyun")
        );
        if(rating1.getRating_rider()!=null ){
            throw new RuntimeException("Already rated rider for this ride ");
        }
        rating1.setRating_rider(rating);
        ratingRepository.save(rating1);

        Double newRating = ratingRepository.findByRider(rider)
                .stream()
                .mapToDouble(Rating::getRating_driver)
                .average().orElse(0.0);
        rider.setRating(newRating);
        riderRepository.save(rider);
        return rating1;
    }

    @Override
    public void createNewRating(Ride saveRide) {
        Rating rating = Rating
                .builder()
                .ride(saveRide)
                .rider(saveRide.getRider())
                .driver(saveRide.getDriver())
                .build();
        ratingRepository.save(rating);
    }

}
