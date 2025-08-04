package com.Ketan.UberApp.repositories;

import com.Ketan.UberApp.entities.Driver;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver,Long> {

    //ST_Distance(point1,point2)
    //ST_DWithin(POINT1,10000)

    @Query(value = "SELECT d.*, ST_Distance(d.current_location, :pickUpLocation) AS distance " +
            "FROM driver d " +
            "WHERE d.available = true AND ST_DWithin(d.current_location, :pickUpLocation, 10000) "+
            "ORDER BY distance " +
            "LIMIT 10",nativeQuery = true)
    List<Driver> findMatchingDrivers(Point pickUpLocation);

    @Query(value = "SELECT d.*, d.rating AS rat " +
            "FROM driver d " +
            "WHERE d.available = true AND ST_DWithin(d.current_location, :pickUpLocation, 15000) "+
            "ORDER BY rat DESC " +
            "LIMIT 10",nativeQuery = true)
    List<Driver> findMatchingDriversByRating(Point pickUpLocation);

}
