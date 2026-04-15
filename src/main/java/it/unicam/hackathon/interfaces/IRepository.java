package it.unicam.hackathon.repository;

public interface IRepository<T> {
    T create(T entity);
    T read(String id);
    T update(T entity);
    void delete(String id);
}
