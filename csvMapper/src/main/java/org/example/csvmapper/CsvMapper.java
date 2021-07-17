package org.example.csvmapper;

import org.example.csvmapper.table.CsvTable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class CsvMapper {

    public <T> List<T> map(Class<T> cl, CsvTable table){
        List<T> mappedInstances = new ArrayList<>();
        for (int i = 0; i < table.bodySize(); i++) {
            T instance = mapRow(cl, table, i);
            mappedInstances.add(instance);
        }
        return mappedInstances;
    }

    private <T> T mapRow(Class<T> cl, CsvTable table, int numOfRow){
        try{
            Constructor<T> constructor = cl.getConstructor();
            T instance = constructor.newInstance();

            Field[] fields = cl.getDeclaredFields();
            for (Field field : fields) {
                if(!field.isAnnotationPresent(CsvColumn.class)){
                    continue;
                }
                CsvColumn csvColumn = field.getAnnotation(CsvColumn.class);
                String colName = csvColumn.colName();

                String value = table.get(numOfRow, colName);
                Class<?> typeOfField = field.getType();
                if(typeOfField == String.class) {
                    field.set(instance, value);
                } else if(typeOfField == char.class || typeOfField == Character.class){
                    field.set(instance, value.charAt(0));
                } else if(typeOfField == int.class || typeOfField == Integer.class){
                    field.set(instance, Integer.parseInt(value));
                } else if(typeOfField == long.class || typeOfField == Long.class){
                    field.set(instance, Long.parseLong(value));
                } else if(typeOfField == float.class || typeOfField == Float.class){
                    field.set(instance, Float.parseFloat(value));
                } else if(typeOfField == double.class || typeOfField == Double.class){
                    field.set(instance, Double.parseDouble(value));
                } else if(typeOfField == boolean.class || typeOfField == Boolean.class){
                    field.set(instance, Boolean.parseBoolean(value));
                } else if(typeOfField.isEnum()){
                    field.set(instance, Enum.valueOf((Class<? extends Enum>) typeOfField, value));
                } else {
                    throw new RuntimeException("Annotated field is not supported");
                }
            }
            return instance;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException ex) {
            throw new RuntimeException(ex);
        }
    }
}
