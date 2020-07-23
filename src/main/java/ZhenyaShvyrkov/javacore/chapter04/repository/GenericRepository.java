package main.java.ZhenyaShvyrkov.javacore.chapter04.repository;

import java.util.List;

public interface GenericRepository<T,ID> {
    T save(T t);
    void delete(T t);
    void deleteById(ID id);
    T update(ID id, T t);
    T getByID(ID id);
    List<T> getAll();
}
