package main.java.ZhenyaShvyrkov.javacore.chapter04.repository;

import main.java.ZhenyaShvyrkov.javacore.chapter04.Specialty;

import java.util.List;

public interface SpecialtyRepository extends GenericRepository<Specialty, Long> {
    @Override
    void save(Specialty specialty);

    @Override
    void delete(Specialty specialty);

    @Override
    List<Specialty> getAll();

    @Override
    Specialty getByID(Long aLong);


}
