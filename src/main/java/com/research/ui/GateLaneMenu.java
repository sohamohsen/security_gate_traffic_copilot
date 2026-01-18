package com.research.ui;

import com.research.model.GateLane;
import com.research.model.GateLaneStatus;
import com.research.service.GateLaneService;
import com.research.util.ConsoleUtil;

public class GateLaneMenu {
    private final GateLaneService gateLaneService;

    public GateLaneMenu(GateLaneService service) {
        this.gateLaneService = service;
    }

    public void displayMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Gate Lane Management ---");
            System.out.println("1. Add Lane");
            System.out.println("2. View All Lanes");
            System.out.println("3. Update Lane");
            System.out.println("4. Open Lane");
            System.out.println("5. Close Lane");
            System.out.println("6. View Open Lanes");
            System.out.println("0. Back to Main Menu");

            int choice = ConsoleUtil.promptInt("Select an option: ");
            try {
                switch (choice) {
                    case 1 -> addLane();
                    case 2 -> listLanes();
                    case 3 -> updateLane();
                    case 4 -> openLane();
                    case 5 -> closeLane();
                    case 6 -> viewOpenLanes();
                    case 0 -> back = true;
                    default -> System.out.println("Invalid option. Try again.");
                }
            } catch (Exception ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
            ConsoleUtil.pressEnterToContinue();
        }
    }

    private void addLane() {
        int id = ConsoleUtil.promptInt("Lane ID: ");
        int laneNumber = ConsoleUtil.promptInt("Lane Number: ");
        int capacity = ConsoleUtil.promptInt("Capacity Per Minute: ");
        GateLaneStatus status = GateLaneStatus.OPEN; // Default to OPEN
        gateLaneService.addLane(new GateLane(id, laneNumber, capacity, status));
        System.out.println("Lane added.");
    }

    private void listLanes() {
        System.out.println("=== Gate Lanes ===");
        for (GateLane l : gateLaneService.getAllLanes()) {
            System.out.printf("ID: %d, Lane#: %d, Capacity/min: %d, Status: %s%n",
                    l.getId(), l.getLaneNumber(), l.getCapacityPerMinute(), l.getStatus());
        }
    }

    private void updateLane() {
        int id = ConsoleUtil.promptInt("Enter Lane ID to update: ");
        GateLane lane = gateLaneService.getLaneById(id);

        int laneNumber = ConsoleUtil.promptInt("Lane Number [" + lane.getLaneNumber() + "]: ");
        int capacity = ConsoleUtil.promptInt("Capacity Per Minute [" + lane.getCapacityPerMinute() + "]: ");
        lane.setLaneNumber(laneNumber);
        lane.setCapacityPerMinute(capacity);

        gateLaneService.updateLane(lane);
        System.out.println("Lane updated.");
    }

    private void openLane() {
        int id = ConsoleUtil.promptInt("Enter Lane ID to open: ");
        gateLaneService.openLane(id);
        System.out.println("Lane is now OPEN.");
    }

    private void closeLane() {
        int id = ConsoleUtil.promptInt("Enter Lane ID to close: ");
        gateLaneService.closeLane(id);
        System.out.println("Lane is now CLOSED.");
    }

    private void viewOpenLanes() {
        System.out.println("=== Open Lanes ===");
        for (GateLane l : gateLaneService.getOpenLanes()) {
            System.out.printf("ID: %d, Lane#: %d, Capacity/min: %d, Status: %s%n",
                    l.getId(), l.getLaneNumber(), l.getCapacityPerMinute(), l.getStatus());
        }
    }
}