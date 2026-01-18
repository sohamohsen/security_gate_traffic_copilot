package com.research.ui;

import com.research.model.Resident;
import com.research.service.ResidentService;
import com.research.util.ConsoleUtil;

public class ResidentMenu {
    private final ResidentService residentService;

    public ResidentMenu(ResidentService service) {
        this.residentService = service;
    }

    public void displayMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Resident Management ---");
            System.out.println("1. Add Resident");
            System.out.println("2. View All Residents");
            System.out.println("3. Update Resident");
            System.out.println("4. Delete Resident");
            System.out.println("5. Search Resident by Email");
            System.out.println("0. Back to Main Menu");

            int choice = ConsoleUtil.promptInt("Select an option: ");

            try {
                switch (choice) {
                    case 1 -> addResident();
                    case 2 -> listResidents();
                    case 3 -> updateResident();
                    case 4 -> deleteResident();
                    case 5 -> searchResident();
                    case 0 -> back = true;
                    default -> System.out.println("Invalid option. Try again.");
                }
            } catch (Exception ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
            ConsoleUtil.pressEnterToContinue();
        }
    }

    private void addResident() {
        int id = ConsoleUtil.promptInt("ID: ");
        String name = ConsoleUtil.prompt("Full Name: ");
        String email = ConsoleUtil.prompt("Email: ");
        String phone = ConsoleUtil.prompt("Phone: ");
        String unit = ConsoleUtil.prompt("Unit Number: ");

        residentService.addResident(new Resident(id, name, email, phone, unit));
        System.out.println("Resident added.");
    }

    private void listResidents() {
        System.out.println("=== Residents ===");
        for (Resident r : residentService.getAllResidents()) {
            System.out.printf("ID: %d, Name: %s, Email: %s, Unit: %s%n",
                    r.getId(), r.getFullName(), r.getEmail(), r.getUnitNumber());
        }
    }

    private void updateResident() {
        int id = ConsoleUtil.promptInt("Enter resident ID to update: ");
        Resident existing = residentService.getResidentById(id);

        String name = ConsoleUtil.prompt("Full Name [" + existing.getFullName() + "]: ");
        String email = ConsoleUtil.prompt("Email [" + existing.getEmail() + "]: ");
        String phone = ConsoleUtil.prompt("Phone [" + existing.getPhone() + "]: ");
        String unit = ConsoleUtil.prompt("Unit Number [" + existing.getUnitNumber() + "]: ");

        if (!name.isBlank()) existing.setFullName(name);
        if (!email.isBlank()) existing.setEmail(email);
        if (!phone.isBlank()) existing.setPhone(phone);
        if (!unit.isBlank()) existing.setUnitNumber(unit);

        residentService.updateResident(existing);
        System.out.println("Resident updated.");
    }

    private void deleteResident() {
        int id = ConsoleUtil.promptInt("Enter resident ID to delete: ");
        residentService.deleteResident(id);
        System.out.println("Resident deleted.");
    }

    private void searchResident() {
        String email = ConsoleUtil.prompt("Enter email: ");
        Resident r = residentService.searchResidentByEmail(email);
        System.out.printf("ID: %d, Name: %s, Phone: %s, Unit: %s%n",
                r.getId(), r.getFullName(), r.getPhone(), r.getUnitNumber());
    }
}