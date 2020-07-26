package main.java.ZhenyaShvyrkov.javacore.chapter04.repository.io;

import main.java.ZhenyaShvyrkov.javacore.chapter04.model.Account;
import main.java.ZhenyaShvyrkov.javacore.chapter04.model.Customer;
import main.java.ZhenyaShvyrkov.javacore.chapter04.model.Specialty;
import main.java.ZhenyaShvyrkov.javacore.chapter04.repository.CustomerRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaIOCustomerRepositoryImpl implements CustomerRepository {
    private static final Path PATH = Paths.get("customers.txt");
    private String customerInfo;
    private long id;
    @Override
    public Customer save(Customer customer) {
        try {
            id = findMaxId();
            customer.setId(++id);
            String info;
            if(id == 1) info = id + ": " + customer.toString();
            else info = "\r\n" + id + ": " + customer.toString() ;
            Files.write(PATH, info.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            return customer;
        }catch (IOException e) {e.printStackTrace();}
        return null;

    }
    public Customer getByID(Long id) {
        try {
            customerInfo = Files.readString(PATH);
            Pattern pattern = Pattern.compile(id + "\\:.+");
            Matcher matcher = pattern.matcher(customerInfo);
            while(matcher.find()){
                customerInfo = matcher.group().replaceAll(id+"\\: ", "").trim();
                Account account = toAccount(customerInfo);
                account.setId(id);
                Set<Specialty> set = toSpecialty(customerInfo);
                return new Customer(account,set);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Customer> getAll() {
        String fileInfo = "";
        List<Customer> list = new ArrayList<>();
        try {
            fileInfo = Files.readString(PATH);
            fileInfo = fileInfo.replaceAll("\\d\\: ", "");
            String array [] = fileInfo.split("\\n");
            for(String line : array){
                list.add(new Customer(toAccount(line),toSpecialty(line)));
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Customer update(Long id, Customer customer) {
        try {
            customerInfo = Files.readString(PATH);
            Files.write(PATH, "".getBytes());
            customerInfo = customerInfo.replaceAll( id  + "\\:.+\\s*", id + ": " + customer.toString() + "\n").trim();
            Files.write(PATH, customerInfo.getBytes());
            return customer;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void delete(Customer customer) {
        try {
            customerInfo = Files.readString(PATH);
            Optional<String> line = customerInfo.lines().filter(str -> str.contains(customer.getAccount().toString())).findAny();
            if(line.isPresent()){
                String [] array =  line.get().split("\\:");
                deleteById(Long.parseLong(array[0].trim()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void deleteById(Long id) {
        try {
            customerInfo = Files.readString(PATH);
            Files.write(PATH, "".getBytes());
            Optional<String> line = customerInfo.lines().filter(str -> str.contains(id.toString() + ": ")).findAny();
            String newCustomer = line.get().replaceAll("ACTIVE|BANNED", "DELETED").replaceAll("\\[.+\\]","[]");
            customerInfo = customerInfo.replaceAll( id  + "\\:.+", newCustomer).trim();
            Files.write(PATH, customerInfo.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long findMaxId()  {
        String allInfo = "";
        try {
            allInfo = Files.readString(PATH);
        }catch (IOException e) {e.printStackTrace();}
        if(allInfo.length() == 0) return 0;
        else {
            String array[] = allInfo.split("\\:.+\\s*");
            long [] arrayOfId = Arrays.stream(array).mapToLong(Long::parseLong).sorted().toArray();
            return arrayOfId[arrayOfId.length-1];
        }
    }
    private static Account toAccount(String line){
        String acData [] = line.replaceAll("\\,","").split("\\s+");
        if(acData.length>=4) {
            String firstName = acData[0].trim();
            String lastName = acData[1].trim();
            int age = Integer.parseInt(acData[2].trim());
            Account.AccountStatus status = null;
            if (acData[3].equals(Account.AccountStatus.ACTIVE.toString())) {
                status = Account.AccountStatus.ACTIVE;
            } else if (acData[3].equals(Account.AccountStatus.BANNED.toString())) {
                status = Account.AccountStatus.BANNED;
            } else if (acData[3].equals(Account.AccountStatus.DELETED.toString())) {
                status = Account.AccountStatus.DELETED;
            }
            return new Account(firstName, lastName, age, status);
        } else return null;
    }
    private static Set<Specialty> toSpecialty(String line){
        Set<Specialty> set = new HashSet<>();
        line = line.replaceAll(" \\[|\\]","");
        String spData [] = line.split(",");
        for(int i = 1; i < spData.length; i++){
            Specialty specialty = new Specialty(spData[i].trim());
            set.add(specialty);
        }
        return set;
    }

}
