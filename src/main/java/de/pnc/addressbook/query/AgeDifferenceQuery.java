package de.pnc.addressbook.query;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;






import de.pnc.addressbook.model.Person;

/**
 * Returns the difference of age of two person whose names are provided.
 * Names are supposed to be unique  
 * */

public class AgeDifferenceQuery implements PersonDataQuery<Integer> {

    private final Person person1;
    private final Person person2;

    public AgeDifferenceQuery(List<Person> persons, String person1_Name, String person2_Name) {
        List<Person> person1Matches = persons.stream()
                                              .filter(p -> p.getName().contains(person1_Name)).collect(Collectors.toList());
        if (person1Matches.size() != 1) {
            throw new IllegalArgumentException("No unique person found for name " + person1_Name);
        }

        List<Person> person2Matches = persons.stream()
                                             .filter(p -> p.getName().contains(person2_Name)).collect(Collectors.toList());
        if (person2Matches.size() != 1) {
            throw new IllegalArgumentException("No unique person found for name " + person2_Name);
        }

        this.person1 = person1Matches.get(0);
        this.person2 = person2Matches.get(0);
    }

    @Override
    public Integer query() {
        LocalDate dob1 = person1.getDateOfBirth();
        LocalDate dob2 = person2.getDateOfBirth();
        return Long.valueOf(ChronoUnit.DAYS.between(dob1, dob2)).intValue();
    }

    @Override
    public String formattedReply() {
        Integer days = query();
        return String.format("%1$s is %2$d days older than %3$s", person1.getName(), days, person2.getName());
    }

}
