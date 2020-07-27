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
    private static JavaIOAccountRepositoryImpl accountRepository = new JavaIOAccountRepositoryImpl();
    private static JavaIOSpecialtyRepositoryImpl specialtyRepository = new JavaIOSpecialtyRepositoryImpl();
    private static final Path PATH = Paths.get("customers.txt");
    private String customerInfo;
    private long id;
    @Override
    public Customer save(Customer customer) {
        try {
            accountRepository.save(customer.getAccount());
            customer.getSpecialties().stream().forEach(specialtyRepository::save);
            id = findMaxId();
            customer.setId(++id);
            String info;
            if(id == 1) {
                info = id + ": " + customer.toString();
            }
            else {
                info = "\r\n" + id + ": " + customer.toString() ;
            }
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
            Optional<String> line = customerInfo.lines().filter(x -> x.startsWith(id.toString())).findAny();
            accountRepository.update(id, customer.getAccount());
            Set<Specialty> oldSpecilties = toSpecialty(line.get());
            Iterator<Specialty> iterator = customer.getSpecialties().iterator();
            int count = 0;
            if(oldSpecilties.size() < customer.getSpecialties().size()){
                for(Specialty x : oldSpecilties) {
                    Specialty specialty = iterator.next();
                    specialty.setId(x.getId());
                    specialtyRepository.update(x.getId(), specialty);
                }
                while (customer.getSpecialties().size() - oldSpecilties.size() - count > 0) {
                    Specialty specialty = iterator.next();
                    specialtyRepository.save(specialty);
                    count++;
                }
            }
            else if(oldSpecilties.size() > customer.getSpecialties().size()){
                for(Specialty x : oldSpecilties){
                    if(customer.getSpecialties().size() - count > 0) {
                        Specialty specialty = iterator.next();
                        specialty.setId(x.getId());
                        specialtyRepository.update(x.getId(), specialty);

                    } else {
                            specialtyRepository.deleteById(x.getId());
                    }
                    count++;
                }
            }
            else if(oldSpecilties.size() == customer.getSpecialties().size()){
                for(Specialty x : oldSpecilties){
                    Specialty specialty = iterator.next();
                    specialty.setId(x.getId());
                    specialtyRepository.update(x.getId(), specialty);
                }
            }
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
            Account account = customer.getAccount();
            accountRepository.delete(account);
            customer.getSpecialties().stream().forEach(specialtyRepository::delete);
            Files.write(PATH, "".getBytes());
            Optional<String> line = customerInfo.lines().filter(x -> x.contains(customer.getAccount().toString()))
            .map(x -> x.replaceAll("ACTIVE", "DELETED"))
            .map(x -> x.replaceAll("BANNED", "DELETED"))
            .map(x-> x.replaceAll("\\[.+\\]", "[]")).findAny();
            customerInfo = customerInfo.replaceAll("\\d.+" + customer.getAccount().toString() + ".+", line.get());
            Files.write(PATH, customerInfo.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void deleteById(Long id) {
        try {
            accountRepository.deleteById(id);
            customerInfo = Files.readString(PATH);
            Files.write(PATH, "".getBytes());
            Optional<String> line = customerInfo.lines().filter(str -> str.contains(id.toString() + ": ")).findAny();
            Set<Specialty> set = toSpecialty(line.get());
            set.stream().forEach(x -> specialtyRepository.deleteById(x.getId()));
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
        line = line.replaceAll("(\\[|\\])","");
        String spData [] = line.split(",");
        for(int i = 1; i < spData.length; i++){
            long id = Long.parseLong(spData[i].trim());
            Specialty specialty = specialtyRepository.getByID(id);
            specialty.setId(id);
            set.add(specialty);
        }
        return set;
    }

}
