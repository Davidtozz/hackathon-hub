package it.unicam.hackathon.interfaces;

import java.util.List;
import java.util.Optional;

/**
 * Interfaccia generica per i repository in-memory.
 * Definisce le operazioni CRUD standard.
 */
public interface IRepository<T> {
    T save(T entity);
    Optional<T> findById(Integer id);
    List<T> findAll();
    void deleteById(Integer id);
    boolean existsById(Integer id);
}
