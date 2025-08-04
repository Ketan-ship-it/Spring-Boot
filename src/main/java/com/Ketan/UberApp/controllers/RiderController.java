package com.Ketan.UberApp.controllers;
import com.Ketan.UberApp.dto.RatingDto;
import com.Ketan.UberApp.dto.RideDto;
import com.Ketan.UberApp.dto.RideRequestDto;
import com.Ketan.UberApp.dto.RiderDto;
import com.Ketan.UberApp.services.RiderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rider")
public class RiderController {

    private final RiderService riderService;

    public RiderController(RiderService riderService) {
        this.riderService = riderService;
    }

    @PostMapping("/requestRide")
    public ResponseEntity<RideRequestDto> requestRide(@RequestBody RideRequestDto rideRequestDto){
        return ResponseEntity.ok(riderService.requestRide(rideRequestDto));
    }

    @PostMapping("/cancelRide/{rideId}")
    public ResponseEntity<RideDto> cancelRide(@PathVariable Long rideId){
        return ResponseEntity.ok(riderService.cancelRide(rideId));
    }

    @PostMapping("/rateDriver/{rideId}")
    public ResponseEntity<RatingDto> rateRider(@PathVariable Long rideId,
                                               @RequestBody RatingDto ratingDto){
        return ResponseEntity.ok(riderService.rateDriver(rideId,ratingDto));
    }

    @GetMapping(path = "/myProfile")
    public ResponseEntity<RiderDto> myProfile(){
        return ResponseEntity.ok(riderService.myProfile());
    }

    @GetMapping(path = "/getAllMyRides")
    public ResponseEntity<Page<RideDto>> allMyRides(@RequestParam (defaultValue = "0") Integer pageOffset,
                                                    @RequestParam (defaultValue = "10" , required = false) Integer pageSize
                                                    ){
        PageRequest pageRequest = PageRequest.of(pageOffset,pageSize, Sort.by(Sort.Direction.DESC,"id","createdAt"));
        return ResponseEntity.ok(riderService.getAllMyRides(pageRequest));
    }
    /*Implement two-way otp collection method
    * Here -> driver and rider both receive separate otp
    * To start a ride both driver and rider will provide otp
    * To cancel ride otp will be provided by the entity which will cancel the ride
    * To end a ride both will provide otp
    * This will help in security of the customer as the ride will only end if both provide otp's
    *  */
}
