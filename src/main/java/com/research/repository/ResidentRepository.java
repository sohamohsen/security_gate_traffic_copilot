package com.research.repository;

import com.research.model.Resident;

import java.util.*;

public class ResidentRepository implements BaseRepository<Resident> {
    private final Map<Integer, Resident> storage = new HashMap<>();

    @Override
    public void save(Resident entity) {
        storage.put(entity.getId(), entity);
    }

    @Override
    public Optional<Resident> findById(int id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Resident> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void update(Resident entity) {
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

    public Optional<Resident> findByEmail(String email) {
        return storage.values().stream()
                .filter(resident -> resident.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }
}