package com.research.repository;

import com.research.model.VisitReservation;

import java.util.*;

public class VisitReservationRepository implements BaseRepository<VisitReservation> {
    private final Map<Integer, VisitReservation> storage = new HashMap<>();

    @Override
    public void save(VisitReservation entity) {
        storage.put(entity.getId(), entity);
    }

    @Override
    public Optional<VisitReservation> findById(int id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<VisitReservation> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void update(VisitReservation entity) {
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

    public Optional<VisitReservation> findByVehiclePlate(String plate) {
        return storage.values().stream()
                .filter(res -> res.getVehiclePlate().equalsIgnoreCase(plate))
                .findFirst();
    }
}