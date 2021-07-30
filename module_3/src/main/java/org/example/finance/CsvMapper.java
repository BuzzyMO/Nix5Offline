package org.example.finance;

import org.example.finance.annotation.CsvColumn;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CsvMapper {

    public <T> String[] getHeaderCsv(Class<T> cl){
        List<String> header = new ArrayList<>();
        Field[] fields = cl.getDeclaredFields();
        for(Field field : fields){
            if(!field.isAnnotationPresent(CsvColumn.class)){
                    continue;
            }
            CsvColumn csvColumn = field.getAnnotation(CsvColumn.class);
            String colName = csvColumn.colName();
            header.add(colName);
        }
        return header.toArray(String[]::new);
    }
}
