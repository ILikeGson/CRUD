package main.java.ZhenyaShvyrkov.javacore.chapter04.controller;

import main.java.ZhenyaShvyrkov.javacore.chapter04.model.Account;
import main.java.ZhenyaShvyrkov.javacore.chapter04.model.Customer;
import main.java.ZhenyaShvyrkov.javacore.chapter04.model.Specialty;
import main.java.ZhenyaShvyrkov.javacore.chapter04.repository.io.JavaIOAccountRepositoryImpl;
import main.java.ZhenyaShvyrkov.javacore.chapter04.repository.io.JavaIOCustomerRepositoryImpl;
import main.java.ZhenyaShvyrkov.javacore.chapter04.repository.io.JavaIOSpecialtyRepositoryImpl;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomerController {
    private JavaIOCustomerRepositoryImpl customerRepository = new JavaIOCustomerRepositoryImpl();
    public String[] handleRequest(String request){
        request = request.trim();
        String [] data = request.split("\\,");
        for(int i = 0; i < data.length; i++){
            data[i] = data[i].trim();
        }
        return data;
    }

    public Customer create(String[] data){
        Account account = new Account(data[1],data[2],Integer.parseInt(data[3]),ascertainStatus(data[4]));
        Set<Specialty> set = new HashSet<Specialty>();
        for(int i = 5; i < data.length; i++){
            Specialty specialty = new Specialty(data[i]);
            set.add(specialty);
        }
        return customerRepository.save(new Customer(account, set));
    }
    public List<Customer> read(){
        return customerRepository.getAll();
    }

    public Customer read(Long id){
        return customerRepository.getByID(id);
    }

    public Customer update(String[] data){
        Account account = new Account(data[2], data[3], Integer.parseInt(data[4]),ascertainStatus(data[5]));
        Set<Specialty> set = new HashSet<Specialty>();
        for(int i = 6; i < data.length;i++){
            set.add(new Specialty(data[i]));
        }
        return customerRepository.update(Long.valueOf(data[1]), new Customer(account, set));
    }
    public void delete(String[] data){
        Account account = new Account(data[1], data[2], Integer.parseInt(data[3]),ascertainStatus(data[4]));
        Set<Specialty> set = new HashSet<>();
        for(int i = 0; i < data.length; i++){
            Specialty specialty = new Specialty(data[i]);
            set.add(specialty);
        }
        customerRepository.delete(new Customer(account, set));
    }
    public void deleteById(Long id){
        customerRepository.deleteById(id);
    }
    private Account.AccountStatus ascertainStatus(String status){
        if(status.equalsIgnoreCase(Account.AccountStatus.ACTIVE.toString())){
            return Account.AccountStatus.ACTIVE;
        }
        else if(status.equalsIgnoreCase(Account.AccountStatus.BANNED.toString())){
            return Account.AccountStatus.BANNED;
        }
        else if(status.equalsIgnoreCase(Account.AccountStatus.DELETED.toString())){
            return Account.AccountStatus.DELETED;
        }
        else throw new InvalidParameterException();
    }
}
