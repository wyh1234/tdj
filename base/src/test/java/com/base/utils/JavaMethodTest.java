package com.base.utils;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by yangkuo on 2018-07-25.
 */
public class JavaMethodTest {

    @Test
    public void transMap2Json() {
    }

    @Test
    public void transBean2Json() {
    }

    @Test
    public void transJson2Map() {
    }

    @Test
    public void transBean2Map() {
    }

    @Test
    public void transMap2Bean() {
    }

    @Test
    public void getJsonBean() {
    }

    @Test
    public void getContainsNamesValue() {
    }

    @Test
    public void copyValue() {
    }

    @Test
    public void getValueFromBean() {
    }

    @Test
    public void getMethodValue() {
    }

    @Test
    public void getMethod() {
    }

    @Test
    public void getFieldValue() {
        ADInfo adInfo = new ADInfo();
        adInfo.setImageName("测试");
        String ss = JavaMethod.getFieldValue(adInfo, "imageName");

        assertEquals(ss, "测试");

        boolean bb = JavaMethod.getFieldValue(adInfo, "selected");

        assertEquals(bb, false);


    }

    @Test
    public void getField() {
    }

    @Test
    public void setFieldValue() {
    }

    @Test
    public void transformClass() {

        Object s1 = JavaMethod.transformClass(BigDecimal.class, "1");
        assertEquals(s1, BigDecimal.ONE);

        Object s2 = JavaMethod.transformClass(String.class, 1);
        assertEquals(s2, "1");

        Object s3 = JavaMethod.transformClass(Date.class, "2015年07月11日 10:42:58:976");
        assertSame(s3.getClass(), Date.class);

        Object s4 = JavaMethod.transformClass(Integer.class, "1");
        assertSame(s4, 1);
        Object s14 = JavaMethod.transformClass(int.class, "1");
        assertSame(s14, 1);


        Object s5 = JavaMethod.transformClass(Long.class, "1");
        assertSame(s5, 1L);
        Object s15 = JavaMethod.transformClass(long.class, "1");
        assertSame(s15, 1L);

        Object s6 = JavaMethod.transformClass(double.class, "1.0");
        assertEquals(s6, 1.0d);
        Object s16 = JavaMethod.transformClass(Double.class, "1.0");
        assertEquals(s16, 1.0d);

        Object s7 = JavaMethod.transformClass(float.class, "1.0");
        assertEquals(s7, 1.0f);
        Object s17 = JavaMethod.transformClass(Float.class, "1.0");
        assertEquals(s17, 1.0f);

        boolean s8 = JavaMethod.transformClass(boolean.class, "false");
        assertEquals(s8, false);
        boolean s18 = JavaMethod.transformClass(Boolean.class, "true");
        assertEquals(s18, true);
    }

    @Test
    public void chagedMap() {
    }

    @Test
    public void getLinkedHashMapHead() {
    }

    @Test
    public void getLinkedHashMapTail() {
    }

    @Test
    public void sortMapByKey() {
    }

    @Test
    public void getDiffrentMap() {
    }

    @Test
    public void comparesTo() {

        int xx = JavaMethod.comparesTo("23", "3");
        D3.getrad();
    }

    @Test
    public void compareTo() {
        boolean bb = JavaMethod.equals(1, 1);
        assertEquals(bb, true);

        boolean aa = JavaMethod.equals("1", 1);
        assertEquals(aa, true);

        boolean ab = JavaMethod.equals("a", "1");
        assertEquals(ab, false);

        boolean abool = JavaMethod.equals(true, false);
        assertEquals(abool, false);

        boolean afloat = JavaMethod.equals("1.53", 1.53f);
        assertEquals(afloat, true);


    }

    @Test
    public void getDiffrentBean() {
    }

    @Test
    public void getPositionFromData() {
    }

    @Test
    public void beanEquals_OnlyField() {
    }

    @Test
    public void compareFeild() {
    }

    @Test
    public void beanEquals() {
    }

    @Test
    public void getDiffrentList() {
    }

    @Test
    public void clearDiffList() {
    }

    @Test
    public void floatchange2() {
    }

    @Test
    public void floatchange21() {
    }

    @Test
    public void doubleChange2() {
    }

    @Test
    public void doubleChange21() {
    }

    @Test
    public void intParse() {
    }

    @Test
    public void intSimpleParse() {
    }

    @Test
    public void sortAsc() {
    }

    @Test
    public void sortAsc1() {
    }

    @Test
    public void sortDesc() {
    }

    @Test
    public void getMaxValue() {
    }

    @Test
    public void isNumberString() {
    }

    @Test
    public void isStringText() {
    }

    @Test
    public void isCHString() {
    }
}