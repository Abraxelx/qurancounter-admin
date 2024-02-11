package com.digiduty.qurancounteradmin.util;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IObjectUtil {

    static Logger logger = Logger.getLogger(IObjectUtil.class.getName());
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
                logger.log(Level.SEVERE, e.getMessage());
            }
        }
        return true;
    }
}
