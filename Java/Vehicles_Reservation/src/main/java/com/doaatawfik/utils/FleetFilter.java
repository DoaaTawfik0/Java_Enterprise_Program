package com.doaatawfik.utils;

import com.doaatawfik.models.Vehicle;

import java.util.List;

public class FleetFilter {

    public static <T extends Vehicle> List<T> getReadyVehicles(List<T> vehicles) {
        return vehicles.stream()
                .filter(Vehicle::isReadyForDispatch)
                .toList();
    }
}
