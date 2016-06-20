package de.pnc.addressbook.model;

public enum Gender {
    Male, Female;
    
    /**
     * Case insensitive match for given string 
     * */
    public static Gender of(String str) {
        for (Gender gender : Gender.values()) {
            if(gender.name().equalsIgnoreCase(str)) {
                return gender;
            }
        }
        return null;
    }
}
