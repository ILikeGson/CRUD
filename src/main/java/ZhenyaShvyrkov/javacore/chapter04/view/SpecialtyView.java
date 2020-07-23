package main.java.ZhenyaShvyrkov.javacore.chapter04.view;

import main.java.ZhenyaShvyrkov.javacore.chapter04.model.Specialty;
import main.java.ZhenyaShvyrkov.javacore.chapter04.controller.SpecialtyController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class SpecialtyView {
    private SpecialtyController spController = new SpecialtyController();
    public void getRequest() {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String request;
            while ((request = reader.readLine()) != null && request.length() != 0) {
                String[] arrayOfSpecialtyNames = spController.handleRequest(request);

                if (arrayOfSpecialtyNames[0].trim().equals("-c")) {
                    response(spController.create(arrayOfSpecialtyNames[1].trim()));

                } else if (request.startsWith("-r")) {
                    if (arrayOfSpecialtyNames.length == 1) response(spController.read());
                    else if (isNumber(arrayOfSpecialtyNames[1].trim()))
                        response(spController.read(Long.parseLong(arrayOfSpecialtyNames[1])));

                } else if (request.startsWith("-u")) {
                    if (isNumber(arrayOfSpecialtyNames[1].trim()))
                        response(spController.update(Long.parseLong(arrayOfSpecialtyNames[1]), arrayOfSpecialtyNames[2].trim()));

                } else if (request.startsWith("-d")) {
                    if (isNumber(arrayOfSpecialtyNames[1]))
                        response(spController.delete(Long.parseLong(arrayOfSpecialtyNames[1])));
                    else response(spController.delete(arrayOfSpecialtyNames[1]));

                } else throw new UnsupportedOperationException("Invalid operation");
            }
        }catch(IOException e) {e.printStackTrace();}
    }
    public void response(String str) {
        System.out.println(str);
    }
    public void response(List<Specialty> list){
        list.forEach(System.out::println);
    }
    public void response(Specialty specialty){
        if(specialty != null)
        System.out.println(specialty.toString());
        else System.out.println("Data could not be found");
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
