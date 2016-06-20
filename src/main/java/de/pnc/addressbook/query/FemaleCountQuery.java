package de.pnc.addressbook.query;

import java.util.List;

import de.pnc.addressbook.model.Gender;
import de.pnc.addressbook.model.Person;

/**
 * Returns number of females in data
 * */
public class FemaleCountQuery implements PersonDataQuery<Long> {
    private final List<Person> persons;

    public FemaleCountQuery(List<Person> persons) {
        this.persons = persons;
    }

    @Override
    public Long query() {
        long count = persons.stream().filter(p -> p.getGender() == Gender.Female).count();
        return count;
    }

    @Override
    public String formattedReply() {
        Long count = query();
        return String.format("There are %1$s females in provided data.", count);
    }

}
