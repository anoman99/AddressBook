package de.pnc.addressbook;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;






import org.junit.BeforeClass;
import org.junit.Test;

import de.pnc.addressbook.model.Gender;
import de.pnc.addressbook.model.Person;
import de.pnc.addressbook.parser.CSVParser;
import de.pnc.addressbook.parser.PersonDataParser;
import de.pnc.addressbook.query.AgeDifferenceQuery;
import de.pnc.addressbook.query.FemaleCountQuery;
import de.pnc.addressbook.query.OldestPersonQuery;
import de.pnc.addressbook.query.PersonDataQuery;
import static org.junit.Assert.*;

public class AddressBookTest {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(CSVParser.DATE_FORMAT);
    
  
    
    @Test
    public void testCSVParserCorrectFile() throws IOException{
        PersonDataParser parser = new CSVParser("./src/test/files/ValidPersonData.csv");
        List<Person> persons =  parser.parse();
        assertFalse(persons.isEmpty());
        assertTrue("Not all lines are parsed properly despite valid data",persons.size()==5);
        
    }
    
    @Test
    public void testCSVParserInvalidFile() throws IOException{
        PersonDataParser parser = new CSVParser("./src/test/files/InvalidPersonData.csv");
        List<Person> persons =  parser.parse();
        assertFalse(persons.isEmpty());
        assertTrue("3 out of 5 persons have valid data, wrong no of persons parsed",persons.size()==3);
        
    }
    
    @Test
    public void testFemaleCountQuery() throws IOException{
        final long expectedFemaleCount = 2;
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Farya", Gender.Female, LocalDate.parse("19/09/56",dateFormatter)));
        persons.add(new Person("John", Gender.Male, LocalDate.parse("19/09/56",dateFormatter)));
        persons.add(new Person("Ken", Gender.Male, LocalDate.parse("19/09/56",dateFormatter)));
        persons.add(new Person("Christina", Gender.Female, LocalDate.parse("19/09/56",dateFormatter)));
        persons.add(new Person("Bob", Gender.Male, LocalDate.parse("19/09/56",dateFormatter)));
        
        
        FemaleCountQuery countQuery = new FemaleCountQuery(persons);
        long count = countQuery.query();
        
        assertEquals("Wrong answer to no of females in data",expectedFemaleCount,count);
    }
    
    @Test
    public void testOldestPersonQuery() throws IOException{
        
        List<Person> persons = new ArrayList<>();
        Person Farya  = new Person("Farya", Gender.Female, LocalDate.parse("01/01/56",dateFormatter));
        persons.add(Farya);
        persons.add(new Person("John", Gender.Male, LocalDate.parse("19/09/57",dateFormatter)));
        persons.add(new Person("Ken", Gender.Male, LocalDate.parse("19/09/57",dateFormatter)));
        persons.add(new Person("Christina", Gender.Female, LocalDate.parse("19/09/58",dateFormatter)));
        persons.add(new Person("Bob", Gender.Male, LocalDate.parse("19/09/56",dateFormatter)));
        
        
        OldestPersonQuery ageQuery = new OldestPersonQuery(persons);
        Person actualOldestPerson= ageQuery.query();
        assertEquals("Wrong oldest person returned",Farya, actualOldestPerson);
    }
    
    @Test
    public void testWhoIsOlder() throws IOException{
        final int expectedDayDiff = 375;
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Farya", Gender.Female, LocalDate.parse("19/09/56",dateFormatter)));
        persons.add(new Person("John", Gender.Male, LocalDate.parse("19/09/57",dateFormatter)));
        persons.add(new Person("Ken", Gender.Male, LocalDate.parse("29/09/57",dateFormatter)));
        persons.add(new Person("Christina", Gender.Female, LocalDate.parse("19/09/58",dateFormatter)));
        persons.add(new Person("Bob", Gender.Male, LocalDate.parse("19/09/56",dateFormatter)));
        
        System.out.println(persons);
        PersonDataQuery<Integer> ageQuery = new AgeDifferenceQuery(persons, "Farya", "Ken");
        Integer actualDaysDiff = ageQuery.query();
        assertEquals("Days difference is wrong",Integer.valueOf(expectedDayDiff),actualDaysDiff);
        
    }
    
    
}
