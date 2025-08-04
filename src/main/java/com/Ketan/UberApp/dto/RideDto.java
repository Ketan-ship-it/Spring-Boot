package com.Ketan.UberApp.dto;

import com.Ketan.UberApp.entities.enums.PaymentMethod;
import com.Ketan.UberApp.entities.enums.RideStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideDto {
    private PointDto pickUpLocation;
    private PointDto dropOffLocation;
    private DriverDto driver;
    private RiderDto riderDto;
    private LocalDateTime createdAt;
    private PaymentMethod paymentMethod;
    private RideStatus rideStatus;
    private Double Fare;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private String OTP;
}
