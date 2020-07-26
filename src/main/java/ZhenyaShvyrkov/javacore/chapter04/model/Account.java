package main.java.ZhenyaShvyrkov.javacore.chapter04.model;


public class Account {
    private String firstName;
    private String lastName;
    private AccountStatus status;
    private int age;
    private long id;

    public Account(String firstName, String lastName, int age, AccountStatus status){
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.status = status;
    }
    public enum AccountStatus {
        ACTIVE,
        BANNED,
        DELETED
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getAge() {
        return age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("%s" + " " + "%s" + " " + "%s" + " " + "%s",firstName, lastName, age, status);
    }
}
