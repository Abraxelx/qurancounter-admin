package com.digiduty.qurancounteradmin.util;

import java.lang.reflect.Field;
import java.util.Collection;

public class IObjectUtil {
    public static <T> boolean areAllFieldsNullOrEmpty(T object) {
        if (object == null) {
            return true;
        }
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(object);
                if (value instanceof Collection<?>) {
                    if (!((Collection<?>) value).isEmpty()) {
                        return false;
                    }
                }
                if (!(value instanceof Collection<?>) && value != null && !value.toString().trim().isEmpty()) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
