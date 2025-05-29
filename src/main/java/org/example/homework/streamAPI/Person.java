package org.example.homework.streamAPI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@AllArgsConstructor
@Setter
@Getter
public class Person implements Comparable<Person>, Serializable {

    private String name;
    private int age;
    private String gender;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.getAge() && name.equals(person.getName())
                && gender.equals(person.getGender());
    }

    @Override
    public int hashCode() {
        return (((age * 31) + name.hashCode()) * 31) + gender.hashCode();
    }

    @Override
    public int compareTo(Person person) {
        return Integer.compare(age, person.getAge());
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Person {" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}').toString();
    }
}

