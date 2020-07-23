package main.java.ZhenyaShvyrkov.javacore.chapter04.io;

import main.java.ZhenyaShvyrkov.javacore.chapter04.model.Specialty;
import main.java.ZhenyaShvyrkov.javacore.chapter04.repository.SpecialtyRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaIOSpecialtyRepositoryImpl implements SpecialtyRepository {
    private static long counter = 1;
    public static final Path PATH = Paths.get("skills.txt");
    private String specialtyInfo = "";
    @Override
    public Specialty save(Specialty specialty) {
        try {
            specialty.setId(counter);
            String info = specialty.getId() + ": " + specialty.toString() + "\n";
            Files.write(PATH, info.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            counter++;
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
            specialtyInfo = specialtyInfo.replaceAll("\\d.+" + specialty.toString()+ "\\s*", "");
            Files.write(PATH, specialtyInfo.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(Long id){
        try {
            specialtyInfo = Files.readString(PATH);
            Files.write(PATH, "".getBytes());
            specialtyInfo = specialtyInfo.replaceAll( id  + ".+\\s*", "").trim();
            Specialty specialty = new Specialty(specialtyInfo);
            specialty.setId(id);
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
            String[] array = specialtyInfo.split("\\s+");
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
}
