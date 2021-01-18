package com.base.utils;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DateUtils {


    //获取周几，或者星期几  比如：周一，星期二
    public static String getWeekDay(String date, String format, String back) {
        int in = getWeekDay(dateStringToLong(date, format));
        if (in == 1) return back + "日";
        else return back + JavaMethod.intSimpleParse(in - 1);
    }

    public static String getWeekDay(String date, String back) {
        int in = getWeekDay(dateStringToLong(date));
        if (in == 1) return back + "日";
        else return back + JavaMethod.intSimpleParse(in - 1);
    }

    //获取一个星期的第几天，星期天是第1天，为1
    public static int getWeekDay(long date) {
        if (date == 0) date = System.currentTimeMillis();
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(date);
        return ca.get(Calendar.DAY_OF_WEEK);
    }

    //获取当前时间是这个月的第几天
    public static int getToDay() {
       // long date = System.currentTimeMillis();
        Calendar ca = Calendar.getInstance();
      //  ca.setTimeInMillis(date);
        //ca.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return ca.get(Calendar.DATE);
    }

    //获取几月，从0开始
    public static int getMonth(String date, String format) {
        return getMonth(dateStringToLong(date, format));
    }

    //获取几月，从0开始
    public static int getMonth(String date) {
        return getMonth(dateStringToLong(date));
    }

    public static int getMonth(long date) {
        if (date == 0) date = System.currentTimeMillis();
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(date);
        return ca.get(Calendar.MONTH);
    }

    /**
     * 格式化Date为String
     */
    public static String parseDate(String format, Date... date) {
        Date time;
        if (date.length == 0) time = new Date();
        else time = date[0];
        return getSimpleDateFormat(format).format(time);
    }

    public static Date parseDates(String date, String... format) {
        SimpleDateFormat sf;
        if (format.length == 0) sf = concludeDateFormat(date);
        else sf = getSimpleDateFormat(format[0]);
        return new Date(dateStringToLong(date, sf));
    }

    public static String parseCalendar(String format, Calendar calendar) {
        if (calendar == null) calendar = Calendar.getInstance();
        return getSimpleDateFormat(format).format(calendar.getTime());
    }

    public static Calendar parseCalendar(String dateStr, String... format) {
        long date = dateStringToLong(dateStr, format.length > 0 ? format[0] : concludeDateFormatString(dateStr));
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(date);
        return ca;
    }


    /**
     * 格式化Date为String
     *
     * @param format 返回时间的格式
     * @param date   需要格式化的时间，如果不填写则为当前时间
     * @return 返回格式化的字符串
     */
    public static String parseTime(String format, long... date) {
        long time;
        if (date.length == 0) time = System.currentTimeMillis();
        else time = date[0];
        return getSimpleDateFormat(format).format(new Date(time));
    }

    public static String getDayNext(long time, String format) {
        return getDay(format, 1, time);
    }

    public static String getHourNext(long time, String format) {
        return getHour(time, format, 1);
    }


    public static String getHourNext(String time, String format) {
        return getHour(dateStringToLong(time, format), format, 1);
    }


    /**
     * 获取@minute 分钟后的时间
     *
     * @time 基准时间
     * 返回时间为长整型
     */
    public static long getMinute(long time, int minute) {
        if (time == 0) time = System.currentTimeMillis();
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(time);
        ca.add(Calendar.MINUTE, minute);
        return ca.getTimeInMillis();
    }

    /**
     * 获取@minute 分钟后的时间
     *
     * @time 基准时间
     * @format 返回时间的格式
     */
    public static String getMinute(long time, String format, int minute) {
        return getSimpleDateFormat(format).format(getMinute(time, minute));
    }

    public static long getHour(long time, int num) {
        if (time == 0) time = System.currentTimeMillis();
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(time);
        ca.add(Calendar.HOUR, num);
        return ca.getTimeInMillis();
    }

    public static String getHour(long time, String format, int num) {
        return getSimpleDateFormat(format).format(getHour(time, num));
    }

    public static String getMonth(String format, int num, long... date) {
        long time;
        if (date.length == 0) time = System.currentTimeMillis();
        else time = date[0];

        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(time);
        ca.add(Calendar.MONTH, num);
        return getSimpleDateFormat(format).format(ca.getTime());
    }


    public static String getDay(String format, int num, long... date) {
        long time;
        if (date.length == 0) time = System.currentTimeMillis();
        else time = date[0];

        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(time);
        ca.add(Calendar.DATE, num);
        return getSimpleDateFormat(format).format(ca.getTime());
    }

    //获取相差天数
    public static int getSubtractDay(String startDate, String endDate, String format) {
        long startT = dateStringToLong(startDate, format); // 定义开始时间
        long endT = dateStringToLong(endDate, format); // 定义结束时间
        return getSubtractDay(startT, endT);
    }

    public static int getSubtractDay(long startDate_long, long endDate_long) {
        long ss = (endDate_long - startDate_long) / (1000 * 60 * 60 * 24);
        return (int) ss;
    }

    public static long getSubtractTimeInMillis(long start, long end) {
        return end - start;
    }

    public static long getSubtractTimeInMillis(String start, String end, String format) {
        return getSubtractTimeInMillis(dateStringToLong(start, format), dateStringToLong(end, format));
    }

    //日期字符,转换成long
    public static long dateStringToLong(String dateString, String format) {
        return dateStringToLong(dateString, getSimpleDateFormat(format));
    }

    /**
     * dateString  可以是常用的日期类型
     *
     * @param dateString
     * @return
     */
    public static long dateStringToLong(String dateString) {
        return dateStringToLong(dateString, concludeDateFormat(dateString));
    }

    public static long dateStringToLong(String dateString, SimpleDateFormat format) {
        dateString = dateString.replaceAll("\\s+", " ");
        Date date = null; // 定义时间类型
        try {
            date = format.parse(dateString); // 将字符型转换成日期型
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date == null ? 0 : date.getTime(); // 返回毫秒数
    }


    public static String dateStringToString(String date, String format, String toFormat) {
        return dateLongToString(dateStringToLong(date, format), toFormat);
    }

    //long型日期，转换字符型日期
    public static String dateLongToString(long date_long, String format) {
        return dateLongToString(date_long, getSimpleDateFormat(format));
    }

    public static String dateLongToString(long date_long) {
        return dateLongToString(date_long, concludeDateFormat());
    }

    public static String dateLongToString(long date_long, SimpleDateFormat format) {
        return format.format(date_long == 0 ? new Date() : new Date(date_long));
    }

    //获取SimpleDateFormat
    public static SimpleDateFormat getSimpleDateFormat(String format) {
        return new SimpleDateFormat(format, Locale.UK);
    }

    /**
     * 日期中必须包含 有是年开头，否则为null
     *
     * @param dateString
     * @return
     */
    public static SimpleDateFormat concludeDateFormat(String... dateString) {
        return getSimpleDateFormat(concludeDateFormatString(dateString));
    }

    public static String concludeDateFormatString(String... dateString) {
        String format = "";
        String date = dateString.length == 0 ? "" : dateString[0];

        //多个空格合并
        date = date.replaceAll("\\s+", " ");

//        if (!"".equals(date)) {
        if (date != null && date.length() > 0) {
            if (date.contains("年")) {
                format = "yyyy年";
            }

            if (date.contains("月")) {
                format += "MM月";
                if (!format.contains("年")) return null;
            }

            if (date.contains("日")) {
                format += "dd日";
            } else if (format.length() > 0) {
                if (date.length() == 10) format += "dd";
                else if (date.length() > 10) format += "dd ";
            }

            String spec = "";
            //1970-01-01 15:30:00
            if (date.contains("-")) {
                spec = "-";
            }
            //1970/01/01 15:30:00
            else if (date.contains("/")) {
                spec = "/";
            }
            //1970.01.01 15:30:00
            else if (date.contains(".")) {
                spec = ".";
            }

            if (!"".equals(spec)) {
                int start = date.indexOf(spec);
                int end = date.lastIndexOf(spec);

                //只有一个分割符  1970-11  或11-14
                if (end - start == 0) {
                    //1970-11
                    if (start == 4) {
                        format = "yyyy" + spec + "MM";
                    }
                    //11-14
                    else if (start == 2) {
                        format = "MM" + spec + "dd";
                    }
                } else {
                    format = "yyyy" + spec + "MM" + spec + "dd";
                }
            }
            //format 和 spec同时为空 19901114
            else if ("".equals(format)) {
                format = "yyyyMMdd";
            }


            //时间判断
            if (date.contains(":")) {
                int start = date.indexOf(":");
                int end = date.lastIndexOf(":");
                int count = end - start;


                switch (count) {
                    case 0://13:58
                        format += "HH:mm";
                        break;
                    case 3://13:58:23
                        format += "HH:mm:ss";
                        break;
                    case 6://13:58:23:978
                        format += "HH:mm:ss:SSS";
                        break;
                }
            }
        } else format = "yyyy-MM-dd HH:mm:ss";

        return format;
    }

}
