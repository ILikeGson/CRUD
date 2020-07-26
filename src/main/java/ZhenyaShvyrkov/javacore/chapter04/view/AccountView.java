package main.java.ZhenyaShvyrkov.javacore.chapter04.view;

import main.java.ZhenyaShvyrkov.javacore.chapter04.controller.AccountController;
import main.java.ZhenyaShvyrkov.javacore.chapter04.model.Account;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Scanner;

public class AccountView {
    private AccountController accountController = new AccountController();
    public void getRequest(){
        Scanner scanner = new Scanner(System.in);
        String accountRequest;
        while ((accountRequest = scanner.nextLine()) != null & accountRequest.length() != 0){
            String arrayOfData [] = accountController.handleRequest(accountRequest);

            if(arrayOfData[0].equals("-c")){
                String firstName = arrayOfData[1];
                String lastName = arrayOfData[2];
                int age = Integer.valueOf(arrayOfData[3]);
                report(accountController.create(firstName, lastName, age, ascertainStatus(arrayOfData[4])));

            }else if(arrayOfData[0].equals("-r")){
                if(arrayOfData.length == 1) report(accountController.read());
                else {
                    long id = Long.valueOf(arrayOfData[1]);
                    report(accountController.read(id));
                }
            }else if(arrayOfData[0].equals("-u")){
                long id = Long.valueOf(arrayOfData[1]);
                String firstName = arrayOfData[2];
                String lastName = arrayOfData[3];
                int age = Integer.valueOf(arrayOfData[4]);
                report(accountController.update(id, firstName, lastName, age, ascertainStatus(arrayOfData[5])));
            }else if(arrayOfData[0].equals("-d")){
                if(arrayOfData.length==2){
                    long id = Long.valueOf(arrayOfData[1]);
                    accountController.deleteById(id);
                }
                else {
                    String firstName = arrayOfData[1];
                    String lastName = arrayOfData[2];
                    int age = Integer.valueOf(arrayOfData[3]);
                    accountController.delete(new Account(firstName, lastName, age, ascertainStatus(arrayOfData[4])));
                }
            }else throw new UnsupportedOperationException();
        }
    }
    private void report(List<Account> list){
        list.forEach(System.out::println);
    }
    private void report(Account account){
        System.out.println(account);
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
