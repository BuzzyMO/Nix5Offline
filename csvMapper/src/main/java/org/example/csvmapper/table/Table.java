package org.example.csvmapper.table;

import java.util.List;

public interface Table {
    String get(int row, String colName);
    String get(int row, int col);
    List<String> getHeader();
    int bodySize();
}
