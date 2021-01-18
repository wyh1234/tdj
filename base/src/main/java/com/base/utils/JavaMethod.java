package com.base.utils;

import android.text.TextUtils;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.base.annotation.AnnotationUtils;
import com.base.retrofit.jackSon.JacksonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;

public class JavaMethod {


    /**
     * 判断一个类是JAVA类型还是用户定义类型     * @param clz     * @return
     */
    public static boolean isJavaClass(Class<?> clz) {
        return clz != null && clz.getClassLoader() == null;
    }


    /**
     * json string 转换为 map 对象
     *
     * @param map
     * @return
     */
    public static String transMap2Json(Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject(map);
        return jsonObject.toString();
    }

    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return JacksonUtils.getObjectMapper().getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    public static String transBean2Json(Object obj) {
        if (obj == null) return null;
//        Gson gson = new Gson();
//        return gson.toJson(obj);

        try {
            return JacksonUtils.getObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将json转换为Javabean
     *
     * @param jsonString
     * @param cls
     * @return javaBean对象
     */
    public static <T> T getJsonBean(String jsonString, Class<T> cls) {
        if (TextUtils.isEmpty(jsonString)) return null;
        try {
            return JacksonUtils.getObjectMapper().readValue(jsonString, cls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }





    public static <T> List<T> getJsonBean(String jsonString, JavaType cls) {
        try {
            return JacksonUtils.getObjectMapper().readValue(jsonString, cls);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json string 转换为 map 对象
     *
     * @param jsonstr
     * @return
     */
    public static Map<String, Object> transJson2Map(String jsonstr) {

        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(jsonstr);
            Iterator<String> nameItr = jsonObj.keys();
            String name;
            Map<String, Object> outMap = new HashMap<>();
            while (nameItr.hasNext()) {
                name = nameItr.next();
                outMap.put(name, jsonObj.getString(name));
            }
            return outMap;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 将Javabean转换为Map
     *
     * @param javaBean javaBean
     * @return Map对象
     */
    public static <T> Map<String, Object> transBean2Map(T javaBean) {
        return getContainsNamesValue(javaBean, null);
    }


    /**
     * 将Map赋值给javaBean
     *
     * @param bean
     * @return Map对象
     */
    public static <T> void transMap2Bean(T bean, Map<String, Object> valMap) {
        if (valMap == null || bean == null) return;
        for (String s : valMap.keySet()) {
            Object value = valMap.get(s);
            setFieldValue(bean, s, value);
        }
    }


    /**
     * 反射  方法关键字    含义
     * getDeclaredMethods() 获取所有的方法
     * getReturnType() 获得方法的放回类型
     * getParameterTypes()  获得方法的传入参数类型
     * getDeclaredMethod("方法名",参数类型.class,……) 获得特定的方法
     * getFieldValue(String);获取所有公共属性
     * getDeclaredField(String);获取所有属性
     *
     * <p/>
     * 构造方法关键字  含义
     * getDeclaredConstructors()  获取所有的构造方法
     * getDeclaredConstructor(参数类型.class,……) 获取特定的构造方法
     * <p/>
     * <p/>
     * 父类和父接口  含义
     * getSuperclass() 获取某类的父类
     * getInterfaces() 获取某类实现的接口
     * <p/>
     * //获取类
     * Class c = Class.forName("User");
     * //获取id属性
     * Field idF = c.getDeclaredField("id");
     * //实例化这个类赋给o
     * Object o = c.newInstance();
     * //打破封装
     * idF.setAccessible(true); //使用反射机制可以打破封装性，导致了java对象的属性不安全。
     * //给o对象的id属性赋值"110"
     * idF.set(o, "110"); //set
     */


    /**
     * 获取javaBean中,包含feildName的的返回值
     *
     * @param javaBean
     * @param feildName
     * @return Map对象
     */
    public static <T> Map<String, Object> getContainsNamesValue(T javaBean, String feildName) {
        if (javaBean == null) return null;
        Map<String, Object> result = new HashMap<>();
        Field[] fields = javaBean.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (TextUtils.isEmpty(feildName) || field.getName().toLowerCase().contains(feildName)) {
                field.setAccessible(true);
                try {
                    Object obj = field.get(javaBean);
         /*           if (obj != null && !obj.toString().equals("")) {
                        result.put(field.getName(), obj);
                    }*/
                    result.put(field.getName(), obj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }


    public static void copyValue(Object beanTo, Object objectFrom) {

        if (beanTo == null || objectFrom == null) beanTo = null;
        else {
            Field[] to = beanTo.getClass().getDeclaredFields();
            if (to != null && to.length > 0) {
                for (Field field : to) {
                    field.setAccessible(true);
                    Object obj = getFieldValue(objectFrom, field.getName());
                    if (obj != null) {
                        try {
                            field.set(beanTo, transformClass(field.getType(), obj));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    }

    //从javaVean中获取值，参数为属性名
    public static Object getValueFromBean(Object bean, String fildName) {
        return getFieldValue(bean, fildName);
    }


    /**
     * 从javaVean中获取方法的值
     *
     * @param dataBean
     * @param methodName
     * @param parameter  类似于int.class  String.class ,12,"测试"
     * @param <T>
     * @return
     */
    public static <T> T getMethodValue(Object dataBean, String methodName, Object... parameter) {
        Class c = dataBean.getClass();
        Object value = null;
        try {
            int len = parameter.length;
            if ((len & 1) == 1) return null;

            len = len / 2;
            Class<?>[] cls = new Class[len];
            Object[] obj = new Object[len];

            for (int i = 0; i < len; i++) {
                cls[i] = (Class<?>) parameter[i];
                obj[i] = parameter[len + i];
            }

            Method method = c.getDeclaredMethod(methodName, cls);
            if (method != null) {
                method.setAccessible(true);
                value = method.invoke(dataBean, obj);
            }

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            Log.e("JavaMethod", e.getMessage());
            value = null;
        }
        return (T) value;
    }


    public static Method getMethod(Object bean, String methodName, Class<?>... cls) throws NoSuchMethodException {
        return bean.getClass().getDeclaredMethod(methodName, cls);
    }

    /**
     * @param bean
     * @param fieldName
     * @param cls       转换的类型  没有则强转
     * @param <T>
     * @return
     */
    @SafeVarargs
    public static <T> T getFieldValue(Object bean, String fieldName, Class<T>... cls) {
        if (bean == null) return null;
        try {
            Field field = bean.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Object obj = field.get(bean);
            if (cls.length == 0) return (T) obj;
            else return transformClass(cls[0], obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }

    }

    /**
     * 获取的是静态的值
     *
     * @param classType       转换的类型  没有则强转
     * @param staticFieldName
     * @param cls
     * @param <T>
     * @return
     */
    @SafeVarargs
    public static <T> T getFieldValue(Class<?> cls, String staticFieldName, Class<T>... classType) {
        if (cls == null) return null;
        try {
            Field field = cls.getDeclaredField(staticFieldName);
            field.setAccessible(true);
            Object obj = field.get(null);
            if (classType.length == 0) return (T) obj;
            else return transformClass(classType[0], obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }

    }

    public static Field getField(Object bean, String fieldName) {
        try {
            return bean.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            // e.printStackTrace();
            return null;
        }
    }

    public static <T> void setFieldValue(T bean, String fieldName, Object value) {
        if (bean == null || TextUtils.isEmpty(fieldName)) return;
        try {
            Field field = bean.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(bean, transformClass(field.getType(), value));

        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
            //e.printStackTrace();
            Log.d("ss", e.getMessage());
        }

    }

    /**
     * 强制转换为T
     *
     * @param value
     * @param <T>
     * @return
     */
    public static <T> T transformClass(Object value) {
        return (T) value;
    }

    /**
     * 非强制转换，比如"true" String转换为boolean的true
     *
     * @param fieldType
     * @param value
     * @param <T>
     * @return
     */
    public static <T> T transformClass(Object value, Class<T> fieldType) {
        return transformClass(fieldType, value);
    }

    public static <T> T transformClass(Class<T> fieldType, Object value) {
        if (value == null) return null;
        Class cls = value.getClass();
        if (cls == fieldType) return (T) value;
        else {
            Object obj;
            if (BigDecimal.class == fieldType) {
                obj = new BigDecimal(String.valueOf(value));
            } else if (String.class == fieldType) {
                obj = String.valueOf(value);
            } else if (fieldType == Date.class) {
                obj = new Date(DateUtils.dateStringToLong(String.valueOf(value)));
            } else if (Integer.class == fieldType || int.class == fieldType) {
                obj = Integer.valueOf(value.toString());
            } else if (Long.class == fieldType || long.class == fieldType) {
                obj = Long.valueOf(String.valueOf(value));
            } else if (Double.class == fieldType || double.class == fieldType) {
                obj = Double.valueOf(String.valueOf(value));
            } else if (Float.class == fieldType || float.class == fieldType) {
                obj = Float.valueOf(String.valueOf(value));
            } else if (Boolean.class == fieldType || boolean.class == fieldType) {
                obj = Boolean.valueOf(String.valueOf(value));
            } else if (fieldType == List.class || fieldType == ArrayList.class) {
                obj = value;
            } else {
                Log.e("JavaMethod", "not supper type" + fieldType);
                obj = value;
            }
            return (T) obj;
        }
    }


    /**
     * 使用 Map类型转换
     */
    public static Map<String, Object> chagedMap(Map<String, ?> map) {
        Map<String, Object> result = new HashMap<>();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    /**
     * 获取LinkedHashMap中的头部元素（最先添加的元素）：
     */
    public static <K, V> Map.Entry<K, V> getLinkedHashMapHead(LinkedHashMap<K, V> map) {
        return map.entrySet().iterator().next();
    }

    /**
     * 获取LinkedHashMap中的末尾元素（最近添加的元素）：
     */
    public static <K, V> Map.Entry<K, V> getLinkedHashMapTail(LinkedHashMap<K, V> map) {
        Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
        Map.Entry<K, V> tail = null;
        while (iterator.hasNext()) {
            tail = iterator.next();
        }
        return tail;
    }

    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    public static TreeMap<String, Object> sortMapByKey(final Map<String, Object> map, final int order) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        final TreeMap<String, Object> sortMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String str1, String str2) {
                return str1.compareTo(str2);
            }
        });
        sortMap.putAll(map);
        return sortMap;
    }

    /**
     * 获取不相同的集合
     * 以第一个map为参数为基数比较
     */
    public static Map<String, Object> getDiffrentMap(Map<String, Object> newMap, Map<String, Object> oldMap) {
        Map<String, Object> diffrent = new HashMap<>();
        if (newMap == null || oldMap == null) return diffrent;
        for (String key : newMap.keySet()) {
            if (!equals(newMap.get(key), oldMap.get(key))) {
                diffrent.put(key, newMap.get(key));
            }
        }
        return diffrent;
    }

    /**
     * @param o1
     * @param o2
     * @return
     */
    public static <T extends Comparable> int comparesTo(T o1, T o2) {
        if (o1 == null && o2 == null) return 0;
        else if (o1 == null || o2 == null) return -1;
        return o1.compareTo(o2);
    }


    public static boolean equals(Object o1, Object o2) {
        if (o1 == null && o2 == null) return true;
        else if (o1 == null || o2 == null) return false;
        return o1.toString().compareTo(o2.toString()) == 0;
    }

    public static boolean isDiffrentBean(Object newBean, Object oldBean) {
        Field[] fields = newBean.getClass().getDeclaredFields();
        for (Field newField : fields) {
            newField.setAccessible(true);
            try {
                Field oldField = oldBean.getClass().getDeclaredField(newField.getName());
                oldField.setAccessible(true);
                Object newObj = newField.get(newBean);
                Object old = oldField.get(oldBean);
                //list不进行比对,直接为不同的
                if (newObj instanceof List) {
                    return false;
                } else {
                    if (!equals(newObj, old)) return false;
                }

            } catch (IllegalAccessException | NoSuchFieldException e) {
                // e.printStackTrace();
            }
        }
        return true;
    }

    public static Map<String, Object> getDiffrentBean(Object newBean, Object oldBean) {
        Map<String, Object> diffrent = new HashMap<>();
        if (newBean == null || oldBean == null) return diffrent;

        Field[] fields = newBean.getClass().getDeclaredFields();
        for (Field newField : fields) {
            newField.setAccessible(true);
            try {
                Field oldField = oldBean.getClass().getDeclaredField(newField.getName());
                oldField.setAccessible(true);
                Object newObj = newField.get(newBean);
                Object old = oldField.get(oldBean);
                //list不进行比对,直接为不同的
                if (newObj instanceof List) {
                    diffrent.put(newField.getName(), newObj);
                } else {
                    if (!equals(newObj, old)) {
                        diffrent.put(newField.getName(), newObj);
                    }
                }


            } catch (IllegalAccessException | NoSuchFieldException e) {
                // e.printStackTrace();
            }
        }

        return diffrent;
    }

    /**
     * 获取集合中的位置 根据OnlyField 的字段
     */
    public static <T> int getPositionFromData(List<T> listData, T bean) {
        List<String> list = AnnotationUtils.getOnlyFieldNames(bean);
        if (!list.isEmpty()) {
            for (int i = 0; i < listData.size(); i++) {
                if (listData.get(i) == null) continue;
                boolean isEqually = true;
                for (String s : list) {
                    Object oldValue = JavaMethod.getFieldValue(bean, s);
                    Object newValue = JavaMethod.getFieldValue(listData.get(i), s);
                    if (!equals(newValue, oldValue)) isEqually = false;
                }
                if (isEqually) return i;
            }
        }
        return -1;
    }

    public static <T> boolean beanEquals_OnlyField(T bean1, T bean2) {
        if (bean1 == null || bean2 == null) return false;
        //获取类中的 OnlyField字段
        List<String> list = AnnotationUtils.getOnlyFieldNames(bean1);
        for (String s : list) {
            if (!compareFeild(bean1, bean2, s)) return false;
        }
        return true;
    }


    //比较两个实体类的字段值是否相同
    public static <T> boolean compareFeild(T bean1, T bean2, String feild) {
        Object oldValue = JavaMethod.getFieldValue(bean1, feild);
        Object newValue = JavaMethod.getFieldValue(bean2, feild);
        return equals(newValue, oldValue);
    }


    public static <T> boolean beanEquals(T bean1, T bean2) {
        if (bean1 == null || bean2 == null) return false;
        return getDiffrentBean(bean1, bean2).size() == 0;
    }

    /**
     * 除去重复的
     */
    public static List<String> getDiffrentList(List<String> list1, List<String> list2) {
        Map<String, Integer> map = new HashMap<>(list1.size() + list2.size());
        List<String> diff = new ArrayList<>();
        List<String> maxList = list1;
        List<String> minList = list2;
        if (list2.size() > list1.size()) {
            maxList = list2;
            minList = list1;
        }

        for (String string : maxList) {
            map.put(string, 1);
        }

        for (String string : minList) {
            Integer cc = map.get(string);
            if (cc != null) {
                map.put(string, ++cc);
                continue;
            }
            map.put(string, 1);
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                diff.add(entry.getKey());
            }
        }
        return diff;
    }

    public static <T> List<T> clearDiffList(List<T> list) {
        if (list == null || list.size() == 1) return list;
        int count = list.size();
        for (int i = count - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (beanEquals_OnlyField(list.get(i), list.get(j))) {
                    list.remove(i);
                    break;
                }
            }

        }
        return list;
    }

    //转换为保六2位小数
    public static float floatchange2(String n) {
        BigDecimal bd = new BigDecimal(n);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    //转换为保六2位小数
    public static float floatchange2(float n) {
        BigDecimal bd = new BigDecimal(n);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    //转换为保六2位小数
    public static double doubleChange2(String n) {
        BigDecimal bd = new BigDecimal(n);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    //转换为保六2位小数
    public static double doubleChange2(double n) {
        BigDecimal bd = new BigDecimal(n);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    public static String intParse(int number) {
        String[] ss = {"", "十", "百", "千"};

        char[] ch = String.valueOf(number).toCharArray();
        if (ch.length > 4) return "";

        int count = ch.length - 1;
        String str = "";
        String s1 = TextUtils.join("", ss);
        for (int i = 0; i <= count; i++) {
            int no = Character.getNumericValue(ch[i]);
            String sse = intSimpleParse(no);//汉字
            String uunit = ss[count - i];//单位

            if (no == 0 && !TextUtils.isEmpty(uunit)) {
                if (s1.contains(uunit)) {
                    uunit = "";
                }
            }

            str += sse + uunit;

        }

        while (str.contains("零零")) {
            str = str.replaceAll("零零", "零");
        }
        if (str.endsWith("零") && str.length() > 1) {
            str = str.substring(0, str.length() - 1);
        }
        if (str.startsWith("一十")) {
            str = str.substring(1);
        }
        return str;
    }


    public static String intSimpleParse(int i) {
        String ss = null;
        switch (i) {
            case 0:
                ss = "零";
                break;
            case 1:
                ss = "一";
                break;
            case 2:
                ss = "二";
                break;
            case 3:
                ss = "三";
                break;
            case 4:
                ss = "四";
                break;
            case 5:
                ss = "五";
                break;
            case 6:
                ss = "六";
                break;
            case 7:
                ss = "七";
                break;
            case 8:
                ss = "八";
                break;
            case 9:
                ss = "九";
                break;

        }
        return ss;
    }

    //二分法排序 升序
    public static <T extends Comparable> List<T> sortAsc(List<T> list) {
        if (list.isEmpty()) return list;
        int index = 0;
        List<T> newList = new ArrayList<>();
        newList.add(list.get(0));

        for (int i = 1; i < list.size(); i++) {
            index = binarySearchPositionAsc(newList, list.get(i));
            newList.add(index, list.get(i));
        }
        return newList;
    }

    //二分法查找插入位置 升序

    /**
     * @param list
     * @param value
     * @return
     */
    private static <T extends Comparable> int binarySearchPositionAsc(List<T> list, T value) {
        int start = 0;
        int end = list.size() - 1;
        int mid;
        while (start <= end) {
            mid = (start + end) >>> 1;

            int comp = comparesTo(list.get(mid), value);

            if (comp < 0) {
                start = mid + 1;
            } else if (comp > 0) {
                end = mid - 1;
            } else {
                return mid;  // value found
            }
        }
        return start;
    }

    //二分法查找插入位置 降序
    private static int binarySearchPositionDesc(List<Integer> list, int value) {
        int start = 0;
        int end = list.size() - 1;
        int mid;
        while (start <= end) {
            mid = (start + end) >>> 1;
            int midVal = list.get(mid);
            if (midVal < value) {
                start = mid - 1;
            } else if (midVal > value) {
                end = mid + 1;
            } else {
                return mid;  // value found
            }
        }
        return start;
    }

    //二分法查找插入位置 升序
    private static <T> int binarySearchPositionAsc(List<T> list, T value, String fieldName) {
        int start = 0;
        int end = list.size() - 1;
        int mid;
        while (start <= end) {
            mid = (start + end) >>> 1;
            int comp = comparesTo(getFieldValue(list.get(mid), fieldName), getFieldValue(value, fieldName));
            if (comp < 0) {
                start = mid + 1;
            } else if (comp > 0) {
                end = mid - 1;
            } else {
                return mid;  // value found
            }
        }
        return start;
    }

    /**
     * @param list      需要排序的数组
     * @param fieldName 排序对比的字段
     */
    public static <T> List<T> sortAsc(List<T> list, String fieldName) {
        if (list.isEmpty()) return list;
        int index;
        List<T> newList = new ArrayList<>();
        newList.add(list.get(0));
        for (int i = 1; i < list.size(); i++) {
            index = binarySearchPositionAsc(newList, list.get(i), fieldName);
            newList.add(index, list.get(i));
        }
        return newList;
    }


    //二分法排序 降序
    public static List<Integer> sortDesc(List<Integer> list) {
        if (list.isEmpty()) return list;
        int index = 0;
        List<Integer> newList = new ArrayList<>();
        newList.add(list.get(0));

        for (int i = 1; i < list.size(); i++) {
            index = binarySearchPositionDesc(newList, list.get(i));
            newList.add(index, list.get(i));
        }
        return newList;
    }


    public static float getMaxValue(List<Float> list) {
        float f = 0f;
        for (Float aFloat : list) {
            if (f < aFloat) f = aFloat;
        }
        return f;
    }


    public static boolean isNumberString(String value) {
        try {
            new BigDecimal(value);
        } catch (NumberFormatException e) {
            Log.i("ss", e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isStringText(String value) {
        Pattern p = Pattern.compile("[a-zA-Z]");
        Matcher m = p.matcher(value);
        return m.matches();
    }

    public static boolean isCHString(String value) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(value);
        return m.matches();
    }


    //获取泛型类型 例如List<String>

    /**
     * @param obj
     * @param index 例如 List<String> , List<List<String> <>中的参数可以为多个   第一个参数index为0
     * @return
     */
    public static Class<?> getGenericClass(Object obj, int index) {
        Type genType = obj.getClass().getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            throw new RuntimeException("Index outof bounds");
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class<?>) params[index];
    }

    public static Class<?>[] getGenericClass(Object obj) {
        Type genType = obj.getClass().getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return new Class[]{Object.class};
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Class<?>[] cls = new Class[params.length];

        for (int i = 0; i < params.length; i++) {
            cls[i] = (Class<?>) params[i];
        }
        return cls;
    }


}
