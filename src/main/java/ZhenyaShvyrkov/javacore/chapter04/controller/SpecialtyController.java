package main.java.ZhenyaShvyrkov.javacore.chapter04.controller;

import main.java.ZhenyaShvyrkov.javacore.chapter04.Specialty;
import main.java.ZhenyaShvyrkov.javacore.chapter04.repository.JavaIOSpecialtyRepositoryImpl;
import main.java.ZhenyaShvyrkov.javacore.chapter04.view.SpecialtyView;

public class SpecialtyController {
    private String[] arrayOfSpecialtyNames;
    private JavaIOSpecialtyRepositoryImpl repository = new JavaIOSpecialtyRepositoryImpl();
    private SpecialtyView specialtyView = new SpecialtyView();

    public void handleRequest(String request){
        request = request.trim();
        arrayOfSpecialtyNames = request.split("\\,");
        if(arrayOfSpecialtyNames.length > 1) {
            arrayOfSpecialtyNames[1] = arrayOfSpecialtyNames[1].trim();
        }


        if(request.startsWith("-c")){
            create(arrayOfSpecialtyNames[1]);
        }

        else if(request.startsWith("-r")){
            if(arrayOfSpecialtyNames.length == 1) read();
            else if(isNumber(arrayOfSpecialtyNames[1].trim()))
                read(Long.parseLong(arrayOfSpecialtyNames[1]));
        }

        else if(request.startsWith("-u")){
            if(isNumber(arrayOfSpecialtyNames[1]))
            update(Long.parseLong(arrayOfSpecialtyNames[1]), arrayOfSpecialtyNames[2].trim());
        }

        else if(request.startsWith("-d")){
            if(isNumber(arrayOfSpecialtyNames[1]))
            delete (Long.parseLong(arrayOfSpecialtyNames[1]));
            else delete(arrayOfSpecialtyNames[1]);
        }

        else throw new UnsupportedOperationException("Invalid operation");
    }
    private void create(String specialtyName){
        repository.save(new Specialty(specialtyName));
        specialtyView.response("Successfully saved");
    }
    private void read(){
        specialtyView.response(repository.getAll());

    }
    private void read(Long id){
        specialtyView.response(repository.getByID(id));
    }
    private void update(Long id, String specialtyName){
        repository.update(id, new Specialty(specialtyName));
        specialtyView.response("Successfully updated");
    }
    private void delete(String specialty){
        repository.delete(new Specialty(specialty));
        specialtyView.response("Successfully deleted");
    }
    private void delete(Long id){
        repository.delete(id);
        specialtyView.response("Successfully deleted");
    }
    public boolean isNumber(String str){
        try{
            Long.parseLong(str);
            return true;
        } catch(Exception e) {
            return false;
        }
    }
}
