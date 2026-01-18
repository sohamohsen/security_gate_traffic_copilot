package com.research.model;


public class Vehicle {
    private final int id;
    private String plateNumber;
    private Resident owner;
    private compoundgatetraffic.model.VehicleType vehicleType;
    private boolean isAllowed;

    public Vehicle(int id, String plateNumber, Resident owner, compoundgatetraffic.model.VehicleType vehicleType, boolean isAllowed) {
        this.id = id;
        this.plateNumber = plateNumber;
        this.owner = owner;
        this.vehicleType = vehicleType;
        this.isAllowed = isAllowed;
    }

    public int getId() {
        return id;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public Resident getOwner() {
        return owner;
    }

    public compoundgatetraffic.model.VehicleType getVehicleType() {
        return vehicleType;
    }

    public boolean isAllowed() {
        return isAllowed;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public void setOwner(Resident owner) {
        this.owner = owner;
    }

    public void setVehicleType(compoundgatetraffic.model.VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setAllowed(boolean allowed) {
        isAllowed = allowed;
    }
}