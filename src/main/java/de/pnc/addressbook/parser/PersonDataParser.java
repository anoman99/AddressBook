package de.pnc.addressbook.parser;

import java.io.IOException;
import java.util.List;

import de.pnc.addressbook.model.Person;

/**
 * Interface to read Person Data 
 * */
public interface PersonDataParser {
    public List<Person> parse() throws IOException;
}
