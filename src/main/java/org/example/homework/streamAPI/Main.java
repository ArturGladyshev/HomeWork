package org.example.homework.streamAPI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        List<Person> persons = Arrays.asList(new Person("Leona", 22, "Male"),
                new Person("Anna", 21, "Female"),
                new Person("Alex", 18, "Male"),
                new Person("Rita", 34, "Female"),
                new Person("Rita", 34, "Female"));

        Map<String, Integer> resultMap = StreamAPI.of(persons).distinct()
                .filter(person -> person.getAge() > 20)
                .transform(person -> person.getName())
                .toMap(name -> name, name -> name.length());
        System.out.println(resultMap);

        StreamAPI.of(persons).sorted(Comparator.naturalOrder())
                .forEach(System.out::println);

        String[] personArray = {"1", "2", "3", "4", "5"};
        int sum = StreamAPI.of(personArray).generate(() -> 10, 10)
                .reduce((val, amount) -> amount + val, 0);
        System.out.println(sum);

        try (ObjectOutputStream objectSource = new ObjectOutputStream(
                new FileOutputStream("persons.text"))) {
            for (Person person : persons) {
                objectSource.writeObject(person);
            }
            StreamAPI.of(new FileInputStream("persons.text")).forEach(System.out::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
