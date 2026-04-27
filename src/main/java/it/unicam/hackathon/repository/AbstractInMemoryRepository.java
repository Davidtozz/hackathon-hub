package it.unicam.hackathon.repository;

import it.unicam.hackathon.actors.BaseAttore;
import it.unicam.hackathon.interfaces.IRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Repository base in-memory: fornisce le implementazioni CRUD standard
 * usando una HashMap thread-safe. Le sottoclassi aggiungono i metodi
 * specifici (findByEmail, findByNome, etc.).
 */
public abstract class AbstractInMemoryRepository<T extends BaseAttore> implements IRepository<T> {

    protected final Map<Integer, T> storage = new ConcurrentHashMap<>();
    protected final AtomicInteger idSequence = new AtomicInteger(0);

    @Override
    public T save(T entity) {
        if (entity == null) return null;
        if (entity.getId() == null) {
            entity.setId(idSequence.incrementAndGet());
        }
        storage.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<T> findById(Integer id) {
        if (id == null) return Optional.empty();
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteById(Integer id) {
        if (id != null) storage.remove(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return id != null && storage.containsKey(id);
    }
}
