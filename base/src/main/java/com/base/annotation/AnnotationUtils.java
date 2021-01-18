package com.base.annotation;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class AnnotationUtils {

    public static List<String> getOnlyFieldNames(Object bean) {
        List<String> list = new ArrayList<>();
        if (bean == null) return list;
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            OnlyField onlyField = field.getAnnotation(OnlyField.class);
            if (onlyField != null) {
                list.add(field.getName());
            }
        }
        return list;
    }
}
