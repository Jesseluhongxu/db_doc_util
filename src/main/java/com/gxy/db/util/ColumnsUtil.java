package com.gxy.db.util;

import com.gxy.db.entity.Columns;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author guoxingyong
 * @since 2019/1/7 14:33
 */
public class ColumnsUtil {

    private static final HashMap<String, Field> fieldHashMap = new HashMap<>();

    static {
        Field[] declaredFields = Columns.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            fieldHashMap.put(declaredField.getName(), declaredField);
        }
    }


    public static Object findFieldByFieldName(String fieldName,Columns columns) {
        Objects.requireNonNull(fieldName);
        Objects.requireNonNull(columns);
        Field field = fieldHashMap.get(fieldName);
        if(field!=null){
            try {
              return field.get(columns);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
