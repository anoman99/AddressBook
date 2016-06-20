package de.pnc.addressbook.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



import de.pnc.addressbook.model.Gender;
import de.pnc.addressbook.model.Person;

/**
 * Parser to read Person data from CSV format file.
 * 
 * */

public class CSVParser implements PersonDataParser {
    public final static String DATE_FORMAT = "dd/MM/yy";
    private final static Logger logger = Logger.getLogger(CSVParser.class.getCanonicalName());
    private final static int NO_OF_FIELDS = 3;

    private final Path path;

    public CSVParser(String fileLocation) {
        this.path = Paths.get(fileLocation);
    }

    @Override
    public List<Person> parse() throws IOException {
        logger.info(String.format("Reading address book from path %s", path.normalize().toAbsolutePath()));
        List<Person> persons = new ArrayList<Person>();
        List<String> lines = Files.readAllLines(path);
        int counter = 0;
        for (String line : lines) {
            counter++;
            String[] values = line.split("[,;]");
            if (!verify(values)) {
                logger.log(Level.WARNING, "Line " + counter + " contains error. Skipping it");
                continue;
            }
            String name = values[0].trim();
            Gender gender = Gender.of(values[1].trim());
            
            LocalDate dob = LocalDate.parse(values[2].trim(), DateTimeFormatter.ofPattern(DATE_FORMAT));
            int yearIn1900 = dob.getYear() - 100;
            LocalDate dob19 = LocalDate.of(yearIn1900,dob.getMonth(),dob.getDayOfMonth());
            Person person = new Person(name, gender, dob19);
            persons.add(person);
            
        }
        logger.info("Persons:" + persons.toString());
        return persons;
    }

    private boolean verify(String[] values) {
        String formatMessage = "Valid data format is list of name, gender(Male|Female), date of birth(dd/MM/yy)";

        if (values.length != NO_OF_FIELDS) {
            logger.log(Level.WARNING, formatMessage);
            return false;
        }

        for (String givenValue : values) {
            if (givenValue == null || givenValue.isEmpty()) {
                logger.log(Level.WARNING, formatMessage);
                return false;
            }
        }
        String genderString = values[1].trim();
        String dobString = values[2].trim();

        if (Gender.of(genderString) == null) {
            logger.log(Level.WARNING, "Invalid gender field");
            return false;
        }

        try {
            LocalDate.parse(dobString, DateTimeFormatter.ofPattern("dd/MM/yy"));
        } catch (DateTimeParseException e) {
            logger.log(Level.WARNING, "Invalid date of birth (valid format dd/MM/yy)\n"+e.getMessage());
            return false;
        }
        return true;
    }

}
