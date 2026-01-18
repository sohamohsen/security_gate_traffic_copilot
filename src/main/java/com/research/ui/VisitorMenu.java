package com.research.ui;

import com.research.model.VisitReservation;
import com.research.service.VisitReservationService;
import com.research.util.ConsoleUtil;

import java.time.LocalDate;
import java.time.LocalTime;

public class VisitorMenu {
    private final VisitReservationService visitReservationService;

    public VisitorMenu(VisitReservationService service) {
        this.visitReservationService = service;
    }

    public void displayMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Visitor Management ---");
            System.out.println("1. Create Visit Reservation");
            System.out.println("2. View Reservations");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. Validate Visitor Access");
            System.out.println("0. Back to Main Menu");

            int choice = ConsoleUtil.promptInt("Select an option: ");
            try {
                switch (choice) {
                    case 1 -> createReservation();
                    case 2 -> listReservations();
                    case 3 -> cancelReservation();
                    case 4 -> validateAccess();
                    case 0 -> back = true;
                    default -> System.out.println("Invalid option. Try again.");
                }
            } catch (Exception ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
            ConsoleUtil.pressEnterToContinue();
        }
    }

    private void createReservation() {
        int id = ConsoleUtil.promptInt("Reservation ID: ");
        String visitorName = ConsoleUtil.prompt("Visitor Name: ");
        String vehiclePlate = ConsoleUtil.prompt("Vehicle Plate: ");
        String dateInput = ConsoleUtil.prompt("Visit Date (YYYY-MM-DD): ");
        String timeInput = ConsoleUtil.prompt("Visit Time (HH:mm): ");
        int numPassengers = ConsoleUtil.promptInt("Number of Passengers: ");

        LocalDate visitDate = LocalDate.parse(dateInput);
        LocalTime visitTime = LocalTime.parse(timeInput);

        VisitReservation reservation = new VisitReservation(
                id, visitorName, vehiclePlate, visitDate, visitTime, numPassengers);
        visitReservationService.createReservation(reservation);
        System.out.println("Reservation created.");
    }

    private void listReservations() {
        System.out.println("=== Visit Reservations ===");
        for (VisitReservation r : visitReservationService.getAllReservations()) {
            System.out.printf("ID: %d, Visitor: %s, Plate: %s, Date: %s, Time: %s, Passengers: %d%n",
                    r.getId(), r.getVisitorName(), r.getVehiclePlate(), r.getVisitDate(), r.getVisitTime(), r.getNumberOfPassengers());
        }
    }

    private void cancelReservation() {
        int id = ConsoleUtil.promptInt("Reservation ID to cancel: ");
        visitReservationService.cancelReservation(id);
        System.out.println("Reservation cancelled.");
    }

    private void validateAccess() {
        String plate = ConsoleUtil.prompt("Plate: ");
        String dateInput = ConsoleUtil.prompt("Date (YYYY-MM-DD): ");
        String timeInput = ConsoleUtil.prompt("Time (HH:mm): ");
        LocalDate date = LocalDate.parse(dateInput);
        LocalTime time = LocalTime.parse(timeInput);

        boolean allowed = visitReservationService.validateVisitorAccess(plate, date, time);
        System.out.println(allowed ? "Access granted." : "Access denied.");
    }
}