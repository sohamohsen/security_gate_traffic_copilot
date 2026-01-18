package com.research.repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {
    void save(T entity);
    Optional<T> findById(int id);
    List<T> findAll();
    void update(T entity);
    void deleteById(int id);
    boolean existsById(int id);
}