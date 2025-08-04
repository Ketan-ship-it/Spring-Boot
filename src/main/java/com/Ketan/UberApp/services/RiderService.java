package com.Ketan.UberApp.services;

import com.Ketan.UberApp.dto.*;
import com.Ketan.UberApp.entities.Rider;
import com.Ketan.UberApp.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RiderService {

    RideRequestDto requestRide(RideRequestDto rideRequestDto);

    RideDto cancelRide(Long rideId);

    RatingDto rateDriver(Long rideId ,RatingDto ratingDto);

    RiderDto myProfile();

    Page<RideDto> getAllMyRides(PageRequest pageRequest);

    Rider createNewRider(User user);

    Rider getCurrentRider();

}
