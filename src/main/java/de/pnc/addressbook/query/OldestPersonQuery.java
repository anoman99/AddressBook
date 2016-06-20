package de.pnc.addressbook.query;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import de.pnc.addressbook.model.Person;

/**
 * Returns oldest person in the data. If more than one, any of them will be returned.
 * */

public class OldestPersonQuery implements PersonDataQuery<Person> {
    private final List<Person> persons;

    public OldestPersonQuery(List<Person> persons) {
        this.persons = persons;
    }

    @Override
    public Person query() {
        Optional<Person> person =  persons.stream().sorted(new AgeComparator()).findFirst();
        if (person.isPresent()) {
            return person.get();
        }
        return null;
    }

    @Override
    public String formattedReply() {
        Person p= query();
        StringBuilder sb = new StringBuilder();
        if (p==null) {
            sb.append("No oldest person found");
        }
        else {
            sb.append("The oldest person is ").append(p);
        }
        
        return sb.toString();
    }
    
    private static class AgeComparator implements Comparator<Person>{

        @Override
        public int compare(Person o1, Person o2) {
            return o1.getDateOfBirth().compareTo(o2.getDateOfBirth());
        }
        
    }

}
