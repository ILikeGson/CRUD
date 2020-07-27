package main.java.ZhenyaShvyrkov.javacore.chapter04.model;

import java.util.Set;
import java.util.stream.Collectors;

public class Customer {
    private Account account;
    private Set<Specialty> specialties;
    private long id;

    public Customer(Account account, Set<Specialty> specialties){
        this.account = account;
        this.specialties = specialties;
    }

    public Account getAccount() {
        return account;
    }

    public Set<Specialty> getSpecialties() {
        return specialties;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setSpecialties(Set<Specialty> specialties) {
        this.specialties = specialties;
    }

    @Override
    public String toString() {
        String idInfo = "";
        int counter = 1;
        for(Specialty x : this.getSpecialties()){
            if(counter == getSpecialties().size()) {
                idInfo += x.getId();
            } else {
                idInfo += x.getId() + ",";
                counter++;
            }
        }
        return account.toString() + ", [" + idInfo + "]";
    }
}
