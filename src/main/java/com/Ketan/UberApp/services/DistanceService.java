package com.Ketan.UberApp.services;

import org.locationtech.jts.geom.Point;

public interface DistanceService {
    Integer PRICE_PER_KM = 10;
    double calculateDistance(Point source,Point destination);
}
