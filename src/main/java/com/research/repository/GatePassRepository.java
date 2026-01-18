package com.research.repository;

import com.research.model.GatePass;

import java.util.*;

public class GatePassRepository implements BaseRepository<GatePass> {
    private final Map<Integer, GatePass> storage = new HashMap<>();

    @Override
    public void save(GatePass entity) {
        storage.put(entity.getId(), entity);
    }

    @Override
    public Optional<GatePass> findById(int id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<GatePass> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void update(GatePass entity) {
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