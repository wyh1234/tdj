package com.base.utils;



import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by yangkuo on 2018-08-23.
 */
public class DateUtilsTest {

    @Test
    public void dateStringToLong() {
        long date = DateUtils.dateStringToLong("1986-11-14 15:30:22:666", "yyyy-MM-dd HH:mm:ss:SSS");

        long ll1 = DateUtils.dateStringToLong("1986年11月14日           15:30:22:666");

        assertEquals(ll1, date);

    }
}