package com.research;

import com.research.repository.*;
import com.research.service.*;
import com.research.ui.*;

public class Main {
    public static void main(String[] args) {
        // Instantiate repositories
        ResidentRepository residentRepository = new ResidentRepository();
        VehicleTypeRepository vehicleTypeRepository = new VehicleTypeRepository();
        VehicleRepository vehicleRepository = new VehicleRepository();
        GateLaneRepository gateLaneRepository = new GateLaneRepository();
        GatePassRepository gatePassRepository = new GatePassRepository();
        VisitReservationRepository visitReservationRepository = new VisitReservationRepository();

        // Instantiate services
        ResidentService residentService = new ResidentService(residentRepository);
        VehicleTypeService vehicleTypeService = new VehicleTypeService(vehicleTypeRepository);
        VehicleService vehicleService = new VehicleService(vehicleRepository);
        GateLaneService gateLaneService = new GateLaneService(gateLaneRepository);
        GatePassService gatePassService = new GatePassService(gatePassRepository);
        VisitReservationService visitReservationService = new VisitReservationService(visitReservationRepository);

        // Instantiate Menus
        ResidentMenu residentMenu = new ResidentMenu(residentService);
        VehicleMenu vehicleMenu = new VehicleMenu(vehicleService);
        GateLaneMenu gateLaneMenu = new GateLaneMenu(gateLaneService);
        TrafficMenu trafficMenu = new TrafficMenu(gatePassService);
        VisitorMenu visitorMenu = new VisitorMenu(visitReservationService);

        // Main menu loop
        boolean running = true;
        while (running) {
            System.out.println("\n=== Compound Gate Traffic Management System ===");
            System.out.println("1. Resident Management");
            System.out.println("2. Vehicle Management");
            System.out.println("3. Gate Lane Management");
            System.out.println("4. Traffic Management");
            System.out.println("5. Visitor Management");
            System.out.println("0. Exit");

            int choice = getChoice();

            switch (choice) {
                case 1 -> residentMenu.displayMenu();
                case 2 -> vehicleMenu.displayMenu();
                case 3 -> gateLaneMenu.displayMenu();
                case 4 -> trafficMenu.displayMenu();
                case 5 -> visitorMenu.displayMenu();
                case 0 -> running = false;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }

        System.out.println("System exited. Goodbye!");
    }

    private static int getChoice() {
        System.out.print("\nSelect an option: ");
        try {
            return Integer.parseInt(System.console() != null
                    ? System.console().readLine()
                    : new java.util.Scanner(System.in).nextLine());
        } catch (NumberFormatException ex) {
            return -1;
        }
    }
}