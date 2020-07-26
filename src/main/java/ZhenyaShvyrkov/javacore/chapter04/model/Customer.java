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
        String allIn = specialties.stream().map(x -> x.toString()).collect(Collectors.joining(", "));
        return account.toString() + ", [" + allIn + "]";
    }
}
