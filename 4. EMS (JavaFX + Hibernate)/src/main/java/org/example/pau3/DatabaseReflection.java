package org.example.pau3;

import jakarta.persistence.Entity;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class DatabaseReflection
{

    // Pobranie nazw pól klasy
    public static String[] getFieldNames(Class<?> clazz)
    {
        return Arrays.stream(clazz.getDeclaredFields())
                .map(Field::getName)
                .toArray(String[]::new);
    }

    // Konwersja obiektu encji na string CSV
    public static <T> String convertToCsvString(T entity)
    {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .map(field ->
                {
                    field.setAccessible(true);
                    try
                    {
                        Object value = field.get(entity);

                        if (value instanceof Collection<?>)
                        {
                            // It's a collection, process each element
                            return ((Collection<?>) value).stream()
                                    .map(item ->
                                    {
                                        try { return getEntityId(item); }
                                        catch (NoSuchFieldException | IllegalAccessException e) { throw new RuntimeException(e); }
                                    })
                                    .collect(Collectors.joining("; "));
                        }
                        else if (isEntity(value))
                        {
                            // It's a single entity, process it directly
                            return getEntityId(value);
                        }
                        return value.toString();
                    }
                    catch (IllegalAccessException | NoSuchFieldException e) { return ""; }
                })
                .collect(Collectors.joining(", "));
    }

    private static boolean isEntity(Object obj)
    {
        return obj != null && obj.getClass().isAnnotationPresent(Entity.class);
    }

    private static String getEntityId(Object entity) throws NoSuchFieldException, IllegalAccessException
    {
        if (entity == null) return "null";

        Field idField = entity.getClass().getDeclaredField("id");
        idField.setAccessible(true);
        return idField.get(entity).toString();
    }
}
