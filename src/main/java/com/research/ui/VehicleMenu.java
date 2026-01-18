package com.research.ui;

import com.research.model.Vehicle;
import com.research.service.VehicleService;
import com.research.util.ConsoleUtil;

public class VehicleMenu {
    private final VehicleService vehicleService;

    public VehicleMenu(VehicleService service) {
        this.vehicleService = service;
    }

    public void displayMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Vehicle Management ---");
            System.out.println("1. Register Vehicle");
            System.out.println("2. View Vehicles");
            System.out.println("3. Update Vehicle");
            System.out.println("4. Delete Vehicle");
            System.out.println("5. Search by Plate Number");
            System.out.println("0. Back to Main Menu");

            int choice = ConsoleUtil.promptInt("Select an option: ");
            try {
                switch (choice) {
                    case 1 -> registerVehicle();
                    case 2 -> listVehicles();
                    case 3 -> updateVehicle();
                    case 4 -> deleteVehicle();
                    case 5 -> searchVehicle();
                    case 0 -> back = true;
                    default -> System.out.println("Invalid option. Try again.");
                }
            } catch (Exception ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
            ConsoleUtil.pressEnterToContinue();
        }
    }

    private void registerVehicle() {
        int id = ConsoleUtil.promptInt("Vehicle ID: ");
        String plate = ConsoleUtil.prompt("Plate Number: ");
        int ownerId = ConsoleUtil.promptInt("Owner Resident ID: ");
        // For simplicity, we'll set vehicleTypeId as 1 and isAllowed as true.
        // In a real system, you'd lookup resident and vehicle type.
        Vehicle vehicle = new Vehicle(id, plate, null, null, true);
        vehicleService.registerVehicle(vehicle);
        System.out.println("Vehicle registered.");
    }

    private void listVehicles() {
        System.out.println("=== Vehicles ===");
        for (Vehicle v : vehicleService.getAllVehicles()) {
            System.out.printf(
                    "ID: %d, Plate: %s, Allowed: %s%n",
                    v.getId(), v.getPlateNumber(), v.isAllowed() ? "YES" : "NO"
            );
        }
    }

    private void updateVehicle() {
        int id = ConsoleUtil.promptInt("Enter Vehicle ID to update: ");
        Vehicle vehicle = vehicleService.getVehicleById(id);

        String plate = ConsoleUtil.prompt("Plate Number [" + vehicle.getPlateNumber() + "]: ");
        String allowedStr = ConsoleUtil.prompt("Allowed (true/false) [" + vehicle.isAllowed() + "]: ");

        if (!plate.isBlank()) vehicle.setPlateNumber(plate);
        if (!allowedStr.isBlank()) vehicle.setAllowed(Boolean.parseBoolean(allowedStr));

        vehicleService.updateVehicle(vehicle);
        System.out.println("Vehicle updated.");
    }

    private void deleteVehicle() {
        int id = ConsoleUtil.promptInt("Enter Vehicle ID to delete: ");
        vehicleService.deleteVehicle(id);
        System.out.println("Vehicle deleted.");
    }

    private void searchVehicle() {
        String plate = ConsoleUtil.prompt("Enter plate number: ");
        Vehicle v = vehicleService.searchByPlateNumber(plate);
        System.out.printf(
                "ID: %d, Plate: %s, Allowed: %s%n",
                v.getId(), v.getPlateNumber(), v.isAllowed() ? "YES" : "NO"
        );
    }
}