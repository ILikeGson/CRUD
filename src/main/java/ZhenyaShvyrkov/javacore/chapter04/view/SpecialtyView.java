package main.java.ZhenyaShvyrkov.javacore.chapter04.view;

import main.java.ZhenyaShvyrkov.javacore.chapter04.Specialty;
import main.java.ZhenyaShvyrkov.javacore.chapter04.controller.SpecialtyController;

import java.util.List;

public class SpecialtyView {
    public void getRequest(String request){
        SpecialtyController spController = new SpecialtyController();
        spController.handleRequest(request);
    }
    public void response(String str) {
        System.out.println(str);
    }
    public void response(List<Specialty> list){
        list.forEach(System.out::println);
    }
    public void response(Specialty specialty){
        System.out.println(specialty.toString());
    }
}
