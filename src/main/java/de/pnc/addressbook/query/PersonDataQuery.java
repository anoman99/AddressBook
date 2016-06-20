package de.pnc.addressbook.query;

/**
 * Interface to query information from Person Data
 * */
public interface PersonDataQuery<T> {
    T query();
    String formattedReply();
}
