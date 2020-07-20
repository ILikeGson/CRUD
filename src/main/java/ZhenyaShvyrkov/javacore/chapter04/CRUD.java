package main.java.ZhenyaShvyrkov.javacore.chapter04;

import main.java.ZhenyaShvyrkov.javacore.chapter04.controller.CustomerController;
import main.java.ZhenyaShvyrkov.javacore.chapter04.repository.JavaIOSpecialtyRepositoryImpl;
import main.java.ZhenyaShvyrkov.javacore.chapter04.view.SpecialtyView;

import java.util.Arrays;


public class CRUD {
    public static void main(String[] args) {
        String s = "-c, Vasya, Bekham, 32, ACTIVE, General Manager";
        //System.out.println(Arrays.toString(s.split("\\,")));
        //CustomerController control = new CustomerController();
        //control.createRequest(s);

        //System.out.println(sp.getAll());
        //System.out.println(sp.getByID((long)1));
        //sp.delete(new Specialty("Manager"));
        //sp.delete((long)2);
        //sp.save(new Specialty("President"));
        SpecialtyView sp = new SpecialtyView();

        //sp.getRequest("-d, 2");
        sp.getRequest("-c, Vasya");
        sp.getRequest("-c, Lflfl");
        sp.getRequest("-c, Game Developer");
        sp.getRequest("-c, gfgfgf");
        sp.getRequest("-d, Game Developer");
    }
}
