package de.pnc.addressbook;

import java.io.IOException;
import java.util.List;

import de.pnc.addressbook.model.Person;
import de.pnc.addressbook.parser.CSVParser;
import de.pnc.addressbook.parser.PersonDataParser;
import de.pnc.addressbook.query.AgeDifferenceQuery;
import de.pnc.addressbook.query.FemaleCountQuery;
import de.pnc.addressbook.query.OldestPersonQuery;
import de.pnc.addressbook.query.PersonDataQuery;

public class AppMain {
    
    public static void main(String[] args) throws IOException {
        String fileLocation = "";
        
        if (args.length != 1) {
            splash();
            System.exit(0);
        }
        if (args.length == 1) {
            fileLocation = args[0];
        }
        
                
        // Parse Data
        PersonDataParser parser = new CSVParser(fileLocation);
        List<Person> persons = parser.parse();
        
        
        System.out.println(""); 
        // Do Queries
        PersonDataQuery<Long> countFemale = new FemaleCountQuery(persons);
        System.out.println("1. "+countFemale.formattedReply());
        
        PersonDataQuery<Person> oldestPerson = new OldestPersonQuery(persons);
        System.out.println("2. " +oldestPerson.formattedReply());
        
        PersonDataQuery<Integer> whoIsOlder = new AgeDifferenceQuery(persons,"Bill","Paul");
        System.out.println("3. "+whoIsOlder.formattedReply());
        
       
    }
    private static void splash() {
        System.out.println("Usage: java -jar AddressBook.jar file_location");
    }
   
}
