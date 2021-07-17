package org.example.csvmapper;

import org.example.csvmapper.parser.CsvParser;
import org.example.csvmapper.parser.CsvParserImpl;
import org.example.csvmapper.table.CsvTable;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CsvParser parser = new CsvParserImpl("csvMapper/csv/persons.csv");
        CsvTable csvTable = new CsvTable(parser);

        CsvMapper csvMapper = new CsvMapper();
        List<Person> persons = csvMapper.map(Person.class, csvTable);

        System.out.println("persons = " + persons);
    }
}
