package com.research.repository;

import com.research.model.Vehicle;
import com.research.repository.BaseRepository;

import java.util.*;

public class VehicleRepository implements BaseRepository<Vehicle> {
    private final Map<Integer, Vehicle> storage = new HashMap<>();

    @Override
    public void save(Vehicle entity) {
        storage.put(entity.getId(), entity);
    }

    @Override
    public Optional<Vehicle> findById(int id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Vehicle> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void update(Vehicle entity) {
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

    public Optional<Vehicle> findByPlateNumber(String plateNumber) {
        return storage.values().stream()
                .filter(vehicle -> vehicle.getPlateNumber().equalsIgnoreCase(plateNumber))
                .findFirst();
    }

    public boolean existsByPlateNumber(String plateNumber) {
        return storage.values().stream()
                .anyMatch(vehicle -> vehicle.getPlateNumber().equalsIgnoreCase(plateNumber));
    }
}