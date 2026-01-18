package com.research.service;

import com.research.exception.NotFoundException;
import com.research.model.GateLane;
import com.research.model.GateLaneStatus;
import com.research.repository.GateLaneRepository;

import java.util.List;

public class GateLaneService {
    private final GateLaneRepository gateLaneRepository;

    public GateLaneService(GateLaneRepository gateLaneRepository) {
        this.gateLaneRepository = gateLaneRepository;
    }

    public void addLane(GateLane lane) {
        ValidationService.validateNotNull(lane, "Lane is required.");
        ValidationService.assertUnique(gateLaneRepository.existsById(lane.getId()), "Lane ID already exists.");
        gateLaneRepository.save(lane);
    }

    public List<GateLane> getAllLanes() {
        return gateLaneRepository.findAll();
    }

    public GateLane getLaneById(int id) {
        return gateLaneRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Lane not found: " + id));
    }

    public void updateLane(GateLane lane) {
        ValidationService.validateNotNull(lane, "Lane is required.");
        ValidationService.assertTrue(gateLaneRepository.existsById(lane.getId()), "Lane does not exist.");
        gateLaneRepository.update(lane);
    }

    public void openLane(int id) {
        GateLane lane = getLaneById(id);
        lane.setStatus(GateLaneStatus.OPEN);
        gateLaneRepository.update(lane);
    }

    public void closeLane(int id) {
        GateLane lane = getLaneById(id);
        lane.setStatus(GateLaneStatus.CLOSED);
        gateLaneRepository.update(lane);
    }

    public List<GateLane> getOpenLanes() {
        return gateLaneRepository.findAll().stream()
                .filter(l -> l.getStatus() == GateLaneStatus.OPEN)
                .toList();
    }
}