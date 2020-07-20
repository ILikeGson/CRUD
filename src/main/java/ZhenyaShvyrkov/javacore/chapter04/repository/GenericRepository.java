package main.java.ZhenyaShvyrkov.javacore.chapter04.repository;

import main.java.ZhenyaShvyrkov.javacore.chapter04.Specialty;
import java.util.List;

public interface GenericRepository<T,ID> {
    void save(T t);
    void delete(T t);
    T getByID(ID id);
    List<T> getAll();
}
