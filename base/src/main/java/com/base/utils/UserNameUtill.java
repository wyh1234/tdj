package com.base.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserNameUtill {


    /**
     * 验证手机格式
     */
    public static boolean isPhoneNO(String phoneNO) {
       /* String telRegex = "^1[3|4|5|7|8][0-9]{9}$";
        if (TextUtils.isEmpty(phoneNO)) return false;
        else return phoneNO.matches(telRegex);*/
        if (phoneNO == null) return false;
        if (phoneNO.length() == 11) {
            return true;
        }
        return false;
    }

    /**
     * 验证邮箱输入是否合法
     */
    public static boolean isEmail(String strEmail) {
        // String strPattern ="^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        String strPattern = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }
}
