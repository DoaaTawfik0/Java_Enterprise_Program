package com.doaatawfik.models;

import com.doaatawfik.exceptions.BatteryException;
import com.doaatawfik.exceptions.InvalidVehicleData;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EconomyCar extends Vehicle {

    private final double baseFare;
    private final double perKilometerRate;

    public EconomyCar(String serialID, boolean available,
                      int battery, double baseFare,
                      double perKilometerRate) {
        super(serialID, available, battery);
        validateFare(baseFare, "Base fare");
        validateFare(perKilometerRate, "Per-Kilometer rate");

        this.baseFare = baseFare;
        this.perKilometerRate = perKilometerRate;
    }

    private void validateFare(double fare, String fieldName) {
        if (fare < 0) {
            throw new InvalidVehicleData("Fare is invalid:  cannot be negative");
        }
    }

    @Override
    public double calculateFare(double distance, long durationMinutes) {
       if(distance < 0)
       {
           throw new IllegalArgumentException("Distance cannot be negative");
       }
        return getBaseFare() + (getPerKilometerRate() * distance);
    }

    @Override
    protected int getMinimumBatteryLevel() {
        return 10;
    }

    @Override
    public void validateBatteryForTrip() {
        if (getBattery() < getMinimumBatteryLevel()) {
            throw new BatteryException("Economy car battery level is below 10%");
        }
    }
}
