package main.java.ZhenyaShvyrkov.javacore.chapter04;

import main.java.ZhenyaShvyrkov.javacore.chapter04.view.SpecialtyView;

public class CRUD {
    public static void main(String[] args) {
        
        SpecialtyView sp = new SpecialtyView();

        sp.getRequest("-c, Vasya");
        sp.getRequest("-c, Lflfl");
        sp.getRequest("-c, Game Developer");
        sp.getRequest("-c, gfgfgf");
        sp.getRequest("-d, Game Developer");
    }
}
