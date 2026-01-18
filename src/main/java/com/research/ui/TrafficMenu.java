package com.research.ui;


import com.research.model.GatePass;
import com.research.model.GatePassDirection;
import com.research.model.GatePassStatus;
import com.research.service.GatePassService;
import com.research.util.ConsoleUtil;

import java.time.LocalDateTime;

public class TrafficMenu {
    private final GatePassService gatePassService;

    public TrafficMenu(GatePassService service) {
        this.gatePassService = service;
    }

    public void displayMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Traffic Management ---");
            System.out.println("1. Request Entry");
            System.out.println("2. Request Exit");
            System.out.println("3. Approve Gate Pass");
            System.out.println("4. Deny Gate Pass");
            System.out.println("5. View Traffic Logs");
            System.out.println("0. Back to Main Menu");

            int choice = ConsoleUtil.promptInt("Select an option: ");
            try {
                switch (choice) {
                    case 1 -> requestGatePass(true);
                    case 2 -> requestGatePass(false);
                    case 3 -> approveGatePass();
                    case 4 -> denyGatePass();
                    case 5 -> viewTrafficLogs();
                    case 0 -> back = true;
                    default -> System.out.println("Invalid option. Try again.");
                }
            } catch (Exception ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
            ConsoleUtil.pressEnterToContinue();
        }
    }

    private void requestGatePass(boolean isEntry) {
        int id = ConsoleUtil.promptInt("GatePass ID: ");
        int vehicleId = ConsoleUtil.promptInt("Vehicle ID: ");
        int laneId = ConsoleUtil.promptInt("Lane ID: ");
        // In a real app, you'd inject the actual vehicle and lane instances.
        GatePass pass = new GatePass(id, null, null,
                isEntry ? GatePassDirection.ENTRY : GatePassDirection.EXIT,
                GatePassStatus.PENDING, LocalDateTime.now());
        gatePassService.requestGatePass(pass);
        System.out.println((isEntry ? "Entry" : "Exit") + " request submitted.");
    }

    private void approveGatePass() {
        int passId = ConsoleUtil.promptInt("GatePass ID to approve: ");
        gatePassService.approvePass(passId);
        System.out.println("GatePass approved.");
    }

    private void denyGatePass() {
        int passId = ConsoleUtil.promptInt("GatePass ID to deny: ");
        gatePassService.denyPass(passId);
        System.out.println("GatePass denied.");
    }

    private void viewTrafficLogs() {
        System.out.println("=== Traffic Logs ===");
        for (GatePass pass : gatePassService.getTrafficLogs()) {
            System.out.printf("ID: %d, Direction: %s, Status: %s, Time: %s%n",
                    pass.getId(), pass.getDirection(), pass.getStatus(), pass.getPassTime());
        }
    }
}