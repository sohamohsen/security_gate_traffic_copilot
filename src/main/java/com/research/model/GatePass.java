package com.research.model;

import java.time.LocalDateTime;

public class GatePass {
    private final int id;
    private Vehicle vehicle;
    private GateLane lane;
    private GatePassDirection direction;
    private GatePassStatus status;
    private LocalDateTime passTime;

    public GatePass(int id, Vehicle vehicle, GateLane lane, GatePassDirection direction,
                    GatePassStatus status, LocalDateTime passTime) {
        this.id = id;
        this.vehicle = vehicle;
        this.lane = lane;
        this.direction = direction;
        this.status = status;
        this.passTime = passTime;
    }

    public int getId() {
        return id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public GateLane getLane() {
        return lane;
    }

    public GatePassDirection getDirection() {
        return direction;
    }

    public GatePassStatus getStatus() {
        return status;
    }

    public LocalDateTime getPassTime() {
        return passTime;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setLane(GateLane lane) {
        this.lane = lane;
    }

    public void setDirection(GatePassDirection direction) {
        this.direction = direction;
    }

    public void setStatus(GatePassStatus status) {
        this.status = status;
    }

    public void setPassTime(LocalDateTime passTime) {
        this.passTime = passTime;
    }
}