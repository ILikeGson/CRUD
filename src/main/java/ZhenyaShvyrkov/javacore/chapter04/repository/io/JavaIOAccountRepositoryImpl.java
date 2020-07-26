package main.java.ZhenyaShvyrkov.javacore.chapter04.repository.io;

import main.java.ZhenyaShvyrkov.javacore.chapter04.model.Account;
import main.java.ZhenyaShvyrkov.javacore.chapter04.model.Specialty;
import main.java.ZhenyaShvyrkov.javacore.chapter04.repository.AccountRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JavaIOAccountRepositoryImpl implements AccountRepository {
    private static final Path PATH = Paths.get("accounts.txt");
    private String accountInfo;
    private long id;
    public Account save(Account account) {
        try {
            id = findMaxId();
            account.setId(++id);
            String info;
            if(id == 1) info = id + ": " + account.toString();
            else info = "\r\n" + id + ": " + account.toString() ;
            Files.write(PATH, info.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            return account;
        }catch (IOException e) {e.printStackTrace();}
        return null;
    }

    @Override
    public void delete(Account account) {
        try {
            accountInfo = Files.readString(PATH);
            Files.write(PATH, "".getBytes());
            Optional<String> line = accountInfo.lines().filter(str -> str.contains(account.toString())).findAny();
            if(line.isPresent()){
                String [] array =  line.get().split("\\:");
                account.setId(Long.parseLong(array[0].trim()));
            }
            String newAccount = account.toString().replaceAll("BANNED|ACTIVE", "DELETED");
            accountInfo = accountInfo.replaceAll("\\d.+" + account.toString(), account.getId() + ": " + newAccount);
            Files.write(PATH, accountInfo.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            accountInfo = Files.readString(PATH);
            Files.write(PATH, "".getBytes());
            Optional<String> line = accountInfo.lines().filter(str -> str.contains(id.toString() + ": ")).findAny();
            String newAccount = line.get().replaceAll("ACTIVE|BANNED", "DELETED");
            accountInfo = accountInfo.replaceAll( id  + "\\:.+", newAccount).trim();
            Files.write(PATH, accountInfo.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Account update(Long id, Account account) {
        try {
            accountInfo = Files.readString(PATH);
            Files.write(PATH, "".getBytes());
            accountInfo = accountInfo.replaceAll( id  + "\\:.+\\s*", id + ": " + account.toString() + "\n").trim();
            Files.write(PATH, accountInfo.getBytes());
            return account;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Account getByID(Long id) {
        try {
            accountInfo = Files.readString(PATH);
            Pattern pattern = Pattern.compile(id + "\\:.+");
            Matcher matcher = pattern.matcher(accountInfo);
            while(matcher.find()){
                String accountInfo = matcher.group().replaceAll(id+"\\: ", "").trim();
                Account account = toAccount(accountInfo);
                account.setId(id);
                return account;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Account> getAll() {
        String fileInfo = "";
        List<Account> list = new ArrayList<>();
        try {
            fileInfo = Files.readString(PATH);
            fileInfo = fileInfo.replaceAll("\\d\\: ", "");
            String array [] = fileInfo.split("\\n");
            for(String line : array){
               list.add(toAccount(line));
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
        String acData [] = line.split("\\s+");
        if(acData.length==4) {
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
}
