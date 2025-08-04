package com.Ketan.UberApp.controllers;

import com.Ketan.UberApp.dto.*;
import com.Ketan.UberApp.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/drivers")
public class DriverController {

    private final DriverService driverService;

//    public DriverController(DriverService driverService) {
//        this.driverService = driverService;
//    }

    @PostMapping(path = "/acceptRide/{rideRequestId}")
    public ResponseEntity<RideDto> acceptRide(@PathVariable Long rideRequestId){
        return ResponseEntity.ok(driverService.acceptRide(rideRequestId));
    }

    @PostMapping(path = "/startRide/{rideRequestId}")
    public ResponseEntity<RideDto> startRide(@PathVariable Long rideRequestId,
                                             @RequestBody RideStartDto rideStartDto){
        return ResponseEntity.ok(driverService.startRide(rideRequestId, rideStartDto.getOtp()));
    }

    @PostMapping(path = "/endRide/{rideId}")
    public ResponseEntity<RideDto> endRide(@PathVariable Long rideId){
        return ResponseEntity.ok(driverService.endRide(rideId));
    }

    @PostMapping("/cancelRide/{rideId}")
    public ResponseEntity<RideDto> cancelRide(@PathVariable Long rideId){
        return ResponseEntity.ok(driverService.cancelRide(rideId));
    }

    @PostMapping("/rateRider/{rideId}")
    public ResponseEntity<RatingDto> rateRider(@PathVariable Long rideId,
                                             @RequestBody RatingDto ratingDto){
        return ResponseEntity.ok(driverService.rateRider(rideId,ratingDto));
    }

    @GetMapping(path = "/myProfile")
    public ResponseEntity<DriverDto> myProfile(){
        return ResponseEntity.ok(driverService.getMyProfile());
    }

    @GetMapping(path = "/getAllMyRides")
    public ResponseEntity<Page<RideDto>> allMyRides(@RequestParam (defaultValue = "0") Integer pageOffset,
                                                    @RequestParam (defaultValue = "10" , required = false) Integer pageSize,
                                                    @RequestParam (defaultValue = "id") String sortCriteria){
        PageRequest pageRequest = PageRequest.of(pageOffset,pageSize, Sort.by(Sort.Direction.DESC,"id","createdAt"));
        return ResponseEntity.ok(driverService.getAllMyRides(pageRequest));
    }

    /*Implement two-way otp collection method
     * Here -> driver and rider both receive separate otp
     * To start a ride both driver and rider will provide otp
     * To cancel ride otp will be provided by the entity which will cancel the ride
     * To end a ride both will provide otp
     * This will help in security of the customer as the ride will only end if both provide otp's
     *  */
}
