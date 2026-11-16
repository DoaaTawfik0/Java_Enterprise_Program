package com.doaatawfik;

import com.doaatawfik.exceptions.BatteryException;
import com.doaatawfik.exceptions.InsufficientCreditException;
import com.doaatawfik.models.*;
import com.doaatawfik.services.CreditCardPayment;
import com.doaatawfik.services.WalletPayment;
import com.doaatawfik.utils.FleetFilter;

import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        // 1. Fleet setup
        DataStore<String, Vehicle> vehicleStore = new DataStore<>();
        DataStore<String, Trip> tripStore = new DataStore<>();

        EconomyCar car = new EconomyCar(
                "CAR-101", true, 80, 20, 5
        );

        EconomyCar secondCar = new EconomyCar(
                "CAR-102", true, 90, 20, 5
        );

        ElectricScooter scooter = new ElectricScooter(
                "SCOOTER-101", true, 15, 10, 2
        );

        vehicleStore.addData(car.getSerialID(), car);
        vehicleStore.addData(secondCar.getSerialID(), secondCar);
        vehicleStore.addData(scooter.getSerialID(), scooter);

        System.out.println("Fleet setup completed.");

        // 2. Generic fleet filter
        List<Vehicle> vehicles = List.of(car, secondCar, scooter);

        List<Vehicle> readyVehicles =
                FleetFilter.getReadyVehicles(vehicles);

        System.out.println("Ready vehicles: " + readyVehicles.size());

        // 3. Successful ride: Economy Car, 12 km, 20 minutes
        WalletPayment wallet = new WalletPayment(200);

        Trip trip = new Trip("TRIP-001", car, wallet);

        trip.startTrip();

        Thread.sleep(10000);
        trip.completeTrip(12);

        tripStore.addData(trip.getTripID(), trip);

        System.out.println("\n----- Trip Receipt -----");
        System.out.println("Trip ID: " + trip.getTripID());
        System.out.println("Vehicle: " + trip.getVehicle().getSerialID());
        System.out.println("Status: " + trip.getLifeCycle());
        System.out.println("Duration: " + trip.getDurationMinutes() + " minutes");
        System.out.println("Fare: " + trip.getTripPrice());
        System.out.println("Wallet balance: " + wallet.getBalance());
        System.out.println("------------------------");

        // 4. Low battery rejection
        System.out.println("\n----- Low Battery Test -----");

        try {
            Trip scooterTrip = new Trip(
                    "TRIP-002",
                    scooter,
                    new WalletPayment(100)
            );

            scooterTrip.startTrip();

        } catch (BatteryException exception) {
            System.out.println("Dispatch rejected: " + exception.getMessage());
        }

        // 5. Declined credit card payment
        System.out.println("\n----- Declined Payment Test -----");

        CreditCardPayment creditCard = new CreditCardPayment(10);

        Trip declinedTrip = new Trip(
                "TRIP-003",
                secondCar,
                creditCard
        );

        try {
            declinedTrip.startTrip();

            declinedTrip.completeTrip(
                    12
            );

        } catch (InsufficientCreditException exception) {
            System.out.println("Payment declined: " + exception.getMessage());

            // Release the vehicle by cancelling the unsuccessful trip.
            declinedTrip.cancelTrip();
        }

        // 6. Safe trip lookup
        System.out.println("\n----- Trip Lookup -----");

        tripStore.getData("TRIP-001")
                .ifPresentOrElse(
                        foundTrip -> System.out.println(
                                "Found trip: " + foundTrip.getTripID()
                        ),
                        () -> System.out.println("Trip not found")
                );

        tripStore.getData("FAKE-TRIP")
                .ifPresentOrElse(
                        foundTrip -> System.out.println(
                                "Found trip: " + foundTrip.getTripID()
                        ),
                        () -> System.out.println("Fake trip not found")
                );
    }
}
