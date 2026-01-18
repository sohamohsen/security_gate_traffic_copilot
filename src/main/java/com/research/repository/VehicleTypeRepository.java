package com.research.repository;

import compoundgatetraffic.model.VehicleType;
import java.util.*;

public class VehicleTypeRepository implements BaseRepository<VehicleType> {
    private final Map<Integer, VehicleType> storage = new HashMap<>();

    @Override
    public void save(VehicleType entity) {
        storage.put(entity.getId(), entity);
    }

    @Override
    public Optional<VehicleType> findById(int id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<VehicleType> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void update(VehicleType entity) {
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
}