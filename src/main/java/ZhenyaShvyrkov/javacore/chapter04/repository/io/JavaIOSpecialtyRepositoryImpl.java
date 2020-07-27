package main.java.ZhenyaShvyrkov.javacore.chapter04.repository.io;

import main.java.ZhenyaShvyrkov.javacore.chapter04.model.Specialty;
import main.java.ZhenyaShvyrkov.javacore.chapter04.repository.SpecialtyRepository;
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

public class JavaIOSpecialtyRepositoryImpl implements SpecialtyRepository {
    public static final Path PATH = Paths.get("skills.txt");
    private String specialtyInfo = "";
    @Override
    public Specialty save(Specialty specialty) {
        try {
            long counter = findMaxId();
            specialty.setId(++counter);
            String info;
            if(counter == 1) {
                info = specialty.getId() + ": " + specialty.toString();
            }
            else {
                info = "\r\n" + specialty.getId() + ": " + specialty.toString() ;
            }
            Files.write(PATH, info.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            return specialty;
        }catch (IOException e) {e.printStackTrace();}
        return null;
    }
    @Override
    public void delete(Specialty specialty){
        try {
            specialtyInfo = Files.readString(PATH);
            Files.write(PATH, "".getBytes());
            Optional<String> line = specialtyInfo.lines().filter(str -> str.contains(specialty.toString())).findAny();
            if(line.isPresent()){
                String [] array =  line.get().split("\\:");
                specialty.setId(Long.parseLong(array[0].trim()));
            }
            specialtyInfo = specialtyInfo.replaceFirst("\\d.+"+ specialty.toString() + "\\s*", "");
            Files.write(PATH, specialtyInfo.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(Long id){
        try {
            specialtyInfo = Files.readString(PATH);
            Files.write(PATH, "".getBytes());
            Optional<String> line = specialtyInfo.lines().filter(str -> str.startsWith(id.toString())).findAny();
            if(line.isPresent()){
                String [] array =  line.get().split("\\:");
                Specialty specialty = new Specialty(array[1].trim());
                specialty.setId(id);
            }
            specialtyInfo = specialtyInfo.replaceFirst( id  + ".+\\s*", "").trim();
            Files.write(PATH, specialtyInfo.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Specialty update(Long id, Specialty specialty){
        try {
            specialtyInfo = Files.readString(PATH);
            Files.write(PATH, "".getBytes());
            specialtyInfo = specialtyInfo.replaceAll( id  + ".+\\s*", id + ": " + specialty.toString() + "\n").trim();
            Files.write(PATH, specialtyInfo.getBytes());
            return specialty;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Specialty> getAll() {
        List<Specialty> list = new ArrayList<>();
        Specialty specialty = null;
        try {
            specialtyInfo = Files.readString(PATH);
            String [] array = specialtyInfo.split("\\n");
            for(String line : array) {
                if (line.length() != 0) {
                    String[] parts = line.split(":.");
                    specialty = new Specialty(parts[1].trim());
                    specialty.setId(Long.parseLong(parts[0].trim()));
                    list.add(specialty);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Specialty getByID(Long id) {
        try {
            specialtyInfo = Files.readString(PATH);
            Pattern pattern = Pattern.compile(id + ".+");
            Matcher matcher = pattern.matcher(specialtyInfo);
            while(matcher.find()){
                String specialtyName = matcher.group().replaceAll("\\d.", "").trim();
                Specialty specialty = new Specialty(specialtyName);
                specialty.setId(id);
                return specialty;
            }
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
            String array[] = allInfo.split("\\D+\\s*");
            long [] arrayOfId = Arrays.stream(array).mapToLong(Long::parseLong).sorted().toArray();
            return arrayOfId[arrayOfId.length-1];
        }
    }

}
