package com.Ketan.UberApp.services;

import com.Ketan.UberApp.dto.DriverDto;
import com.Ketan.UberApp.dto.RatingDto;
import com.Ketan.UberApp.dto.RideDto;
import com.Ketan.UberApp.entities.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface DriverService {

    RideDto acceptRide(Long rideRequestId);

    RideDto cancelRide(Long riderId);

    RideDto startRide(Long riderId, String OTP);

    RideDto endRide(Long rideId);

    RatingDto rateRider(Long rideId, RatingDto ratingdto);

    DriverDto getMyProfile();

    Page<RideDto> getAllMyRides(PageRequest pageRequest);

    Driver getCurrentDriver();

    Driver updateDriverAvailability(Driver driver,Boolean availability);
}
