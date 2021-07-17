package org.example.csvmapper.table;

import org.example.csvmapper.parser.CsvParser;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CsvTable implements Table{
    private final String[] header;
    private final List<String[]> body;

    public CsvTable(CsvParser parser){
        header = parser.getHeader();
        body = parser.getBody();
    }

    public String get(int row, String colName){
        int col = indexOfCol(colName);
        return get(row, col);
    }

    public String get(int row, int col){
        String[] fullRow = body.get(row);
        return fullRow[col];
    }

    private int indexOfCol(String colName){
        for (int i = 0; i < header.length; i++) {
            if(header[i].equals(colName))
                return i;
        }
        throw new IllegalArgumentException("Column doesn't exist: " + colName);
    }

    public List<String> getHeader(){
        return Arrays.stream(header)
                .collect(Collectors.toList());
    }

    public int bodySize(){
        return body.size();
    }
}
