package org.example.csvmapper.parser;

import java.util.List;

public interface CsvParser {
    String[] getHeader();
    List<String[]> getBody();
}
