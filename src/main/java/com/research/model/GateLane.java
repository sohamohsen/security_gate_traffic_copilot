package com.research.model;

public class GateLane {
    private final int id;
    private int laneNumber;
    private int capacityPerMinute;
    private GateLaneStatus status;

    public GateLane(int id, int laneNumber, int capacityPerMinute, GateLaneStatus status) {
        this.id = id;
        this.laneNumber = laneNumber;
        this.capacityPerMinute = capacityPerMinute;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getLaneNumber() {
        return laneNumber;
    }

    public int getCapacityPerMinute() {
        return capacityPerMinute;
    }

    public GateLaneStatus getStatus() {
        return status;
    }

    public void setLaneNumber(int laneNumber) {
        this.laneNumber = laneNumber;
    }

    public void setCapacityPerMinute(int capacityPerMinute) {
        this.capacityPerMinute = capacityPerMinute;
    }

    public void setStatus(GateLaneStatus status) {
        this.status = status;
    }
}