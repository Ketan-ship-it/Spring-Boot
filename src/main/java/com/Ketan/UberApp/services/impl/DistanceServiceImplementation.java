package com.Ketan.UberApp.services.impl;

import com.Ketan.UberApp.services.DistanceService;
import lombok.Data;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class DistanceServiceImplementation implements DistanceService {

    private static final String OSRM_API_BASE_URL = "http://router.project-osrm.org/route/v1/driving/";

    @Override
    public double calculateDistance(Point source, Point destination) {

        try {

            OSRMResponseDto responseDto=RestClient.builder()
                    .baseUrl(OSRM_API_BASE_URL)
                    .build()
                    .get()
                    .uri("74.221323,28.33423123;74.1213,28.234123")
                    .retrieve()
                    .body(OSRMResponseDto.class);

            assert responseDto != null;
            return responseDto.getRoutes().get(0).getDistance() / 1000.0;
        }
        catch (Exception e){
            throw new RuntimeException("Error getting data from OSRM"+e.getMessage());
        }
    }
}
@Data
class OSRMResponseDto{
    private List<OSRMDistance> routes;
}
@Data
class OSRMDistance{
    private Double distance;
}