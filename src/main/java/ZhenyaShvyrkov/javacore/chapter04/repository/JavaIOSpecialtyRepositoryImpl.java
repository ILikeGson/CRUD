package main.java.ZhenyaShvyrkov.javacore.chapter04.repository;

import main.java.ZhenyaShvyrkov.javacore.chapter04.Specialty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaIOSpecialtyRepositoryImpl implements SpecialtyRepository {
    @Override
    public void save(Specialty specialty) {
        try {
            Path path = Paths.get("skills.txt");
            String info = specialty.getId() + ": " + specialty.toString() + "\n";
            Files.write(path, info.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        }catch (IOException e) {e.printStackTrace();}

    }
    @Override
    public void delete(Specialty specialty){
        Path path = Paths.get("skills.txt");
        String specialtyInfo = "";
        try {
            specialtyInfo = Files.readString(path);
            Files.write(path, "".getBytes());
            specialtyInfo = specialtyInfo.replaceAll("\\d.+" + specialty.toString()+ "\\s+", "");
            Files.write(path, specialtyInfo.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(Long id){
        Path path = Paths.get("skills.txt");
        String specialtyInfo = "";
        try {
            specialtyInfo = Files.readString(path);
            Files.write(path, "".getBytes());
            specialtyInfo = specialtyInfo.replaceAll( id  + ".+\\s+", "").trim();
            Files.write(path, specialtyInfo.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(Long id, Specialty specialty){
        Path path = Paths.get("skills.txt");
        String specialtyInfo = "";
        try {
            specialtyInfo = Files.readString(path);
            Files.write(path, "".getBytes());
            specialtyInfo = specialtyInfo.replaceAll( id  + ".+\\s+", id + ": " + specialty.toString() + "\n").trim();
            Files.write(path, specialtyInfo.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Specialty> getAll() {
        Path path = Paths.get("skills.txt");
        String specialtyInfo = "";
        List<Specialty> list = new ArrayList<>();
        try {
            specialtyInfo = Files.readString(path);
            String[] array = specialtyInfo.split("\\d.");
            for(String specialtyName: array){
                if(specialtyName.length()!= 0) {
                    list.add(new Specialty(specialtyName.trim()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Specialty getByID(Long id) {
        Path path = Paths.get("skills.txt");
        String specialtyInfo = "";
        try {
            specialtyInfo = Files.readString(path);
            String[] array = specialtyInfo.split("\\s+");
            Pattern pattern = Pattern.compile(id + ".+");
            Matcher matcher = pattern.matcher(specialtyInfo);
            while(matcher.find()){
                String specialtyName = matcher.group().replaceAll("\\d.", "").trim();
                return new Specialty(specialtyName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
