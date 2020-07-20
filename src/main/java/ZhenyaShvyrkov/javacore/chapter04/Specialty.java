package main.java.ZhenyaShvyrkov.javacore.chapter04;

import java.util.Objects;

public class Specialty {
    private String specialtyName;
    private static long counter = 1;
    private long id = counter;

    public Specialty(String specialtyName){
        this.specialtyName = specialtyName;
        counter++;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }

    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Specialty specialty = (Specialty) o;
        return Objects.equals(specialtyName, specialty.specialtyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(specialtyName);
    }

    @Override
    public String toString() {
        return specialtyName;
    }
}
