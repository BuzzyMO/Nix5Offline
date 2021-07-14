package org.example.props;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class PropertiesMapper {

    public <T> T map(Class<T> cl, Properties properties){
        try{
            Constructor<T> constructor = cl.getConstructor();
            T instance = constructor.newInstance();

            Field[] fields = cl.getDeclaredFields();
            for (Field field : fields) {
                if(!field.isAnnotationPresent(PropertyKey.class)){
                    continue;
                }
                PropertyKey propertyKey = field.getAnnotation(PropertyKey.class);

                String key = propertyKey.key();
                if(!properties.containsKey(key)){
                    continue;
                }
                String property = properties.getProperty(key);

                Class<?> typeOfField = field.getType();
                if(typeOfField == String.class) {
                    field.set(instance, property);
                } else if(typeOfField == char.class || typeOfField == Character.class){
                    field.set(instance, property.charAt(0));
                } else if(typeOfField == int.class || typeOfField == Integer.class){
                    field.set(instance, Integer.parseInt(property));
                } else if(typeOfField == long.class || typeOfField == Long.class){
                    field.set(instance, Long.parseLong(property));
                } else if(typeOfField == float.class || typeOfField == Float.class){
                    field.set(instance, Float.parseFloat(property));
                } else if(typeOfField == double.class || typeOfField == Double.class){
                    field.set(instance, Double.parseDouble(property));
                } else if(typeOfField == boolean.class || typeOfField == Boolean.class){
                    field.set(instance, Boolean.parseBoolean(property));
                } else if(typeOfField.isEnum()){
                    field.set(instance, Enum.valueOf((Class<? extends Enum>) typeOfField, property));
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
