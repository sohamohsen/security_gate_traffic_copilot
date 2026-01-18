package com.research.repository;

import com.research.model.GateLane;

import java.util.*;

public class GateLaneRepository implements BaseRepository<GateLane> {
    private final Map<Integer, GateLane> storage = new HashMap<>();

    @Override
    public void save(GateLane entity) {
        storage.put(entity.getId(), entity);
    }

    @Override
    public Optional<GateLane> findById(int id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<GateLane> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void update(GateLane entity) {
        storage.put(entity.getId(), entity);
    }

    @Override
    public void deleteById(int id) {
        storage.remove(id);
    }

    @Override
    public boolean existsById(int id) {
        return storage.containsKey(id);
    }

    public Optional<GateLane> findByLaneNumber(int laneNumber) {
        return storage.values().stream()
                .filter(lane -> lane.getLaneNumber() == laneNumber)
                .findFirst();
    }
}