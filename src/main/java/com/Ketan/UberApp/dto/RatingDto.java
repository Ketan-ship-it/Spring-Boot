package com.Ketan.UberApp.dto;

import lombok.Data;

@Data
public class RatingDto {
    private DriverDto driverDto;
    private RiderDto riderDto;
    private Double rating;
    private String feedback;
}
