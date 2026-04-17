package it.unicam.hackathon.interfaces;

public interface IRepository<T> {
    T create(T entity);
    T read(String id);
    T update(T entity);
    void delete(Integer id);
}
