package com.doaatawfik.models;

import com.doaatawfik.exceptions.BatteryException;
import com.doaatawfik.exceptions.InvalidVehicleData;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class ElectricScooter extends Vehicle {

    private final double unlockFee;
    private final double perMinuteRate;


    public ElectricScooter(String serialID, boolean available,
                           int battery, double unlockFee,
                           double perMinuteRate) {
        super(serialID, available, battery);
        validateRate(unlockFee, "Unlock fee");
        validateRate(perMinuteRate, "Per-minute rate");

        this.unlockFee = unlockFee;
        this.perMinuteRate = perMinuteRate;
    }

    private void validateRate(double rate, String fieldName) {
        if (rate < 0) {
            throw new InvalidVehicleData(fieldName + " cannot be negative");
        }
    }


    @Override
    protected int getMinimumBatteryLevel() {
        return 20;
    }

    @Override
    public void validateBatteryForTrip() {
        if (getBattery() < 20) {
            throw new BatteryException("Scooter battery level is below 20%");
        }
    }

    @Override
    public double calculateFare(double distance, long durationMinutes) {
        if (durationMinutes < 0) {
            throw new IllegalArgumentException("Duration cannot be negative");
        }

        return getUnlockFee() + (durationMinutes * getPerMinuteRate());
    }
}
