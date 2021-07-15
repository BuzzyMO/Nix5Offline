package org.example.csvmapper.parser;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CsvParserImpl implements CsvParser{
    private final String path;
    private final List<String[]> csvData;

    public CsvParserImpl(String path){
        this.path = path;
        csvData = read();
    }

    public String[] getHeader(){
        return csvData.get(0);
    }

    public List<String[]> getBody(){
        return csvData.subList(1, csvData.size());
    }

    private List<String[]> read(){
        try(CSVReader reader = new CSVReader(new FileReader(path))){
            return reader.readAll();
        } catch (CsvException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
