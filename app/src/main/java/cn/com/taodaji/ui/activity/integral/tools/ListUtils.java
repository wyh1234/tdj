package cn.com.taodaji.ui.activity.integral.tools;

import com.apkfuns.logutils.LogUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListUtils {
    private static DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public static boolean isEmpty(List list) {
        if (list == null) {
            return true;
        }
        return list.size() == 0;
    }
    /**
     * <手机号码判断>
     *
     * @param tel
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isTel(String tel) {
        String str = "^[0-9]{11}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(tel);
        return m.matches();
    }
    public static long get_time_difference(Long starttime,String end) {
        long from = 0;
        long to = 0;
        long m1 = 0;
        try {
            from = starttime;
            to = df1.parse(end).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        m1 = (to - from);
        LogUtils.i(m1);
        if (m1<=0){
            return 0;

        }else {
            return Math.abs(m1);
        }


    }
    public static long get_time_difference(String starttime,String end) {
        long from = 0;
        long to = 0;
        long m1 = 0;
        try {
            from = df1.parse(starttime).getTime();
            to = df1.parse(end).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        m1 = (to - from);
        LogUtils.i(m1);
        if (m1<=0){
            return 0;

        }else {
            return m1;
        }


    }

    /**
     * 判断字符串是否为null或者0长度，字符串在判断长度时，先去除前后的空格,空或者0长度返回true,否则返回false
     *
     * @param str 被判断的字符串
     * @return boolean
     */
    public static boolean isNullOrZeroLenght(String str) {
        return (null == str || "".equals(str.trim())) ? true : false;
    }
}
