package org.example.csvmapper.table;

import org.example.csvmapper.parser.CsvParser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CsvTable implements Table{
    private final String[] header;
    private final Map<String, Integer> headerMap;
    private final List<String[]> body;

    public CsvTable(CsvParser parser){
        header = parser.getHeader();
        body = parser.getBody();
        headerMap = getHeaderMap(header);
    }

    public String get(int row, String colName){
        int colIndex = headerMap.get(colName);
        return get(row, colIndex);
    }

    public String get(int row, int col){
        String[] fullRow = body.get(row);
        return fullRow[col];
    }

    public List<String> getHeader(){
        return Arrays.stream(header)
                .collect(Collectors.toList());
    }

    public int bodySize(){
        return body.size();
    }

    private Map<String, Integer> getHeaderMap(String[] header){
        Map<String, Integer> headerMap = new HashMap<>();
        for (int i = 0; i < header.length; i++) {
            headerMap.put(header[i], i);
        }
        return headerMap;
    }
}
