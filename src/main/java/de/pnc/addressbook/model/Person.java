package de.pnc.addressbook.model;

import java.time.LocalDate;

/**
 * Immutable class to stores the information from the parsed file
 *   
 * */
public class Person {
    private final String name;
    private final Gender gender;
    private final LocalDate dateOfBirth;

    public Person(String name, Gender gender, LocalDate dateOfBirth) {
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getName() {
        return name;
    }
    public Gender getGender() {
        return gender;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(", ").append(gender);
        sb.append(", ").append(dateOfBirth);
        return sb.toString();
    }
}
