package com.Ketan.UberApp.dto;

import com.Ketan.UberApp.entities.enums.PaymentMethod;
import com.Ketan.UberApp.entities.enums.RideRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideRequestDto {
    private Long id;
    private PointDto pickUpLocation;
    private PointDto dropOffLocation;
    private RiderDto rider;
    private PaymentMethod paymentMethod;
    private RideRequestStatus requestStatus;
    private Double fare;
    private LocalDateTime requestedTime;
}
