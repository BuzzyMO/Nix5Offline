package org.example.csvmapper;

public class Person {
    @CsvColumn(colName = "first_name")
    public String firstName;
    @CsvColumn(colName = "last_name")
    public String lastName;
    @CsvColumn(colName = "year_of_birth")
    public int yearOfBirth;
    @CsvColumn(colName = "email")
    public String email;

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                ", email='" + email + '\'' +
                '}';
    }
}

