package com.doaatawfik.models;

import com.doaatawfik.exceptions.BusyVehicleException;
import com.doaatawfik.exceptions.InvalidVehicleData;
import lombok.Data;

@Data
public abstract class Vehicle {

    private String serialID;
    private boolean available;
    private int battery;


    public Vehicle(String serialID, boolean available, int battery) {
        validateSerialID(serialID);
        validateBattery(battery);

        this.available = available;
        this.serialID = serialID;
        this.battery = battery;

    }

    private void validateSerialID(String serialID) {
        if (serialID == null || serialID.isBlank()) {
            throw new InvalidVehicleData("Serial ID is invalid: cannot be empty or null");
        }
    }

    private void validateBattery(int battery) {
        if (battery < 0 || battery > 100) {
            throw new InvalidVehicleData("Battery is invalid: must be between 0 and 100");
        }

    }

    public abstract void validateBatteryForTrip();

    public abstract double calculateFare(double distance, long durationMinutes);

    protected abstract int getMinimumBatteryLevel();

    public boolean isReadyForDispatch() {
        return isAvailable() && battery >= getMinimumBatteryLevel();
    }

    public void dispatch() {
        if (!isAvailable()) {
            throw new BusyVehicleException("Vehicle is already on a trip");
        }
        validateBatteryForTrip();
        available = false;
    }

    public void release() {
        available = true;
    }

}
