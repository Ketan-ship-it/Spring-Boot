package com.Ketan.UberApp.repositories;

import com.Ketan.UberApp.entities.Driver;
import com.Ketan.UberApp.entities.Rating;
import com.Ketan.UberApp.entities.Ride;
import com.Ketan.UberApp.entities.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating,Long> {

    Optional<Rating> findByRide(Ride ride);

    List<Rating> findByDriver(Driver driver);

    List<Rating> findByRider(Rider rider);

}
