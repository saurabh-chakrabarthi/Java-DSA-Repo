package com.java.poc.design_collection;


import java.util.HashMap;
import java.util.Map;

class Vehicle {
    private String licensePlate; // License plate of the vehicle
    private String type; // Type of vehicle (e.g., car, motorcycle)

    public Vehicle(String licensePlate, String type) {
        this.licensePlate = licensePlate;
        this.type = type;
    }

    public String getLicensePlate() {
        return licensePlate; // Get license plate
    }

    public String getType() {
        return type; // Get vehicle type
    }
}

class ParkingSpace {
    private int id; // Unique identifier for the parking space
    private Vehicle vehicle; // Vehicle occupying the space

    public ParkingSpace(int id) {
        this.id = id;
        this.vehicle = null; // Initially, the space is empty
    }

    public int getId() {
        return id; // Get parking space ID
    }

    public boolean isOccupied() {
        return vehicle != null; // Check if the space is occupied
    }

    public void parkVehicle(Vehicle vehicle) {
        this.vehicle = vehicle; // Park the vehicle in the space
    }

    public Vehicle removeVehicle() {
        Vehicle temp = vehicle; // Store the current vehicle
        vehicle = null; // Remove the vehicle from the space
        return temp; // Return the removed vehicle
    }

    public Vehicle getVehicle() {
        return vehicle; // Get the vehicle in the space
    }
}

class MyParkingLot {
    private ParkingSpace[] spaces; // Array of parking spaces
    private Map<String, ParkingSpace> parkedVehicles; // Map to track parked vehicles by license plate

    public MyParkingLot(int capacity) {
        spaces = new ParkingSpace[capacity]; // Initialize parking spaces
        parkedVehicles = new HashMap<>(); // Initialize map for parked vehicles
        for (int i = 0; i < capacity; i++) {
            spaces[i] = new ParkingSpace(i + 1); // Create parking spaces
        }
    }

    // Park a vehicle in the parking lot
    public boolean parkVehicle(Vehicle vehicle) {
        for (ParkingSpace space : spaces) {
            if (!space.isOccupied()) { // Find an empty space
                space.parkVehicle(vehicle); // Park the vehicle
                parkedVehicles.put(vehicle.getLicensePlate(), space); // Track parked vehicle
                System.out.println("Vehicle " + vehicle.getLicensePlate() + " parked in space " + space.getId());
                return true; // Successful parking
            }
        }
        System.out.println("No available parking spaces for vehicle " + vehicle.getLicensePlate());
        return false; // No space available
    }

    // Remove a vehicle from the parking lot
    public Vehicle removeVehicle(String licensePlate) {
        ParkingSpace space = parkedVehicles.remove(licensePlate); // Get the parking space
        if (space != null) {
            Vehicle vehicle = space.removeVehicle(); // Remove the vehicle from the space
            System.out.println("Vehicle " + licensePlate + " removed from space " + space.getId());
            return vehicle; // Return the removed vehicle
        } else {
            System.out.println("Vehicle " + licensePlate + " not found in the parking lot.");
            return null; // Vehicle not found
        }
    }

    // Get the total number of available spaces
    public int getAvailableSpaces() {
        int count = 0;
        for (ParkingSpace space : spaces) {
            if (!space.isOccupied()) count++; // Count empty spaces
        }
        return count; // Return the count of available spaces
    }

    public static void main(String[] args) {
        MyParkingLot parkingLot = new MyParkingLot(5); // Create a parking lot with 5 spaces

        Vehicle car1 = new Vehicle("ABC123", "Car");
        Vehicle car2 = new Vehicle("XYZ456", "Car");
        Vehicle bike1 = new Vehicle("BIKE1", "Motorcycle");

        parkingLot.parkVehicle(car1); // Park car1
        parkingLot.parkVehicle(car2); // Park car2
        parkingLot.parkVehicle(bike1); // Park bike1

        System.out.println("Available spaces: " + parkingLot.getAvailableSpaces()); // Output available spaces

        parkingLot.removeVehicle("XYZ456"); // Remove car2
        System.out.println("Available spaces: " + parkingLot.getAvailableSpaces()); // Output available spaces

        parkingLot.removeVehicle("UNKNOWN"); // Try to remove a vehicle that does not exist
    }
}
