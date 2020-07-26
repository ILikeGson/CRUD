package main.java.ZhenyaShvyrkov.javacore.chapter04.controller;

import main.java.ZhenyaShvyrkov.javacore.chapter04.model.Account;
import main.java.ZhenyaShvyrkov.javacore.chapter04.repository.AccountRepository;
import main.java.ZhenyaShvyrkov.javacore.chapter04.repository.io.JavaIOAccountRepositoryImpl;

import java.util.Arrays;
import java.util.List;

public class AccountController {
    private String [] arrayOfData;
    private AccountRepository acRepository = new JavaIOAccountRepositoryImpl();

    public String [] handleRequest(String request){
        request = request.trim();
        arrayOfData = request.split("\\,");
        for(int i = 0; i < arrayOfData.length; i++){
            arrayOfData[i] = arrayOfData[i].trim();
        }
        return arrayOfData;
    }
    public Account create(String firstName, String lastName, int age, Account.AccountStatus status){
        return acRepository.save(new Account(firstName, lastName, age, status));
    }
    public List<Account> read(){
        return acRepository.getAll();
    }
    public Account read(Long id){
        return acRepository.getByID(id);
    }
    public Account update(Long id, String firstName, String lastName, int age, Account.AccountStatus status){
        return acRepository.update(id, new Account(firstName, lastName, age, status));
    }
    public void deleteById(Long id){
        acRepository.deleteById(id);
    }
    public void delete(Account account){
        acRepository.delete(account);
    }
}
