package com.research.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class VisitReservation {
    private final int id;
    private String visitorName;
    private String vehiclePlate;
    private LocalDate visitDate;
    private LocalTime visitTime;
    private int numberOfPassengers;

    public VisitReservation(int id, String visitorName, String vehiclePlate,
                            LocalDate visitDate, LocalTime visitTime, int numberOfPassengers) {
        this.id = id;
        this.visitorName = visitorName;
        this.vehiclePlate = vehiclePlate;
        this.visitDate = visitDate;
        this.visitTime = visitTime;
        this.numberOfPassengers = numberOfPassengers;
    }

    public int getId() {
        return id;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public LocalTime getVisitTime() {
        return visitTime;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public void setVisitTime(LocalTime visitTime) {
        this.visitTime = visitTime;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }
}