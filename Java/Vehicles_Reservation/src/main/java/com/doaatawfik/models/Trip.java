package com.doaatawfik.models;


import com.doaatawfik.enums.TripLifeCycle;
import com.doaatawfik.exceptions.InvalidTripStateException;
import com.doaatawfik.services.Payment;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
public class Trip<T> {

    private final String tripID;
    private final Vehicle vehicle;
    private final Payment payment;


    private TripLifeCycle lifeCycle;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double tripPrice;

    public Trip(String tripID, Vehicle vehicle, Payment payment) {
        validateTripId(tripID);

        if (vehicle == null || payment == null) {
            throw new IllegalArgumentException("vehicle or payment cannot be null");
        }

        this.tripID = tripID;
        this.vehicle = vehicle;
        this.payment = payment;
        this.lifeCycle = TripLifeCycle.REQUESTED;
    }


    public void startTrip() {
        if (lifeCycle != TripLifeCycle.REQUESTED) {
            throw new InvalidTripStateException("Only requested trips can be started");
        }

        vehicle.dispatch();

        startTime = LocalDateTime.now();
        lifeCycle = TripLifeCycle.IN_PROGRESS;
    }

    public void completeTrip(double distance) {
        if (lifeCycle != TripLifeCycle.IN_PROGRESS) {
            throw new InvalidTripStateException(
                    "Only in-progress trips can be completed"
            );
        }

        if (distance < 0) {
            throw new IllegalArgumentException("Distance cannot be negative");
        }

        LocalDateTime completionTime = LocalDateTime.now();

        long durationMinutes =
                Duration.between(startTime, completionTime).toMinutes();

        double calculatedFare =
                vehicle.calculateFare(distance, durationMinutes);

        // Payment must succeed before changing the trip to COMPLETED.
        payment.pay(calculatedFare);

        tripPrice = calculatedFare;
        endTime = completionTime;
        lifeCycle = TripLifeCycle.COMPLETED;
        vehicle.release();
    }

    public void cancelTrip() {
        if (lifeCycle != TripLifeCycle.REQUESTED
                && lifeCycle != TripLifeCycle.IN_PROGRESS) {
            throw new InvalidTripStateException(
                    "This trip cannot be cancelled"
            );
        }

        if (lifeCycle == TripLifeCycle.IN_PROGRESS) {
            vehicle.release();
            endTime = LocalDateTime.now();
        }

        lifeCycle = TripLifeCycle.CANCELLED;
    }

    public long getDurationMinutes() {
        if (startTime == null || endTime == null) {
            throw new InvalidTripStateException(
                    "Trip duration is not available yet"
            );
        }

        return Duration.between(startTime, endTime).toMinutes();
    }

    private void validateTripId(String tripID) {
        if (tripID == null || tripID.isEmpty()) {
            throw new IllegalArgumentException("Trip ID cannot be null or empty");
        }
    }


}
