package main.java.ZhenyaShvyrkov.javacore.chapter04.controller;

import main.java.ZhenyaShvyrkov.javacore.chapter04.model.Specialty;
import main.java.ZhenyaShvyrkov.javacore.chapter04.repository.io.JavaIOSpecialtyRepositoryImpl;
import main.java.ZhenyaShvyrkov.javacore.chapter04.repository.SpecialtyRepository;

import java.util.List;

public class SpecialtyController {
    private String[] arrayOfSpecialtyNames;
    private SpecialtyRepository repository = new JavaIOSpecialtyRepositoryImpl();

    public String[] handleRequest(String request){
        request = request.trim();
        arrayOfSpecialtyNames = request.split("\\,");
        if(arrayOfSpecialtyNames.length > 1) {
            arrayOfSpecialtyNames[1] = arrayOfSpecialtyNames[1].trim();
        }
        return arrayOfSpecialtyNames;
    }
    public Specialty create(String specialtyName){
        return repository.save(new Specialty(specialtyName));
    }
    public List<Specialty> read(){
        return repository.getAll();
    }
    public Specialty read(Long id){
        return repository.getByID(id);
    }
    public Specialty update(Long id, String specialtyName){
        return repository.update(id, new Specialty(specialtyName));
    }
    public boolean delete(String specialty){
        repository.delete(new Specialty(specialty));
        return true;
    }
    public boolean delete(Long id){
        repository.deleteById(id);
        return true;
    }

}
