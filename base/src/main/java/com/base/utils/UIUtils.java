package com.base.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.base.application.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.List;

import static java.lang.reflect.Array.getInt;





/*     TextUtils中常用的方法
       Log.e("tag",TextUtils.isEmpty(a)+"");// 字符串是否为null或“”
       Log.e("tag",TextUtils.isDigitsOnly(c)+"");// 字符串是否全是数字
       Log.e("tag",TextUtils.isGraphic(d)+"");// 字符串是否含有可打印的字符
       Log.e("tag",TextUtils.concat(a,b)+"");// 拼接多个字符串
       Log.e("tag",TextUtils.getTrimmedLength(b)+"");// 去掉字符串前后两端空格(相当于trim())之后的长度
       Log.e("tag",TextUtils.getReverse(b,0,b.length())+"");// 在字符串中，start和end之间字符的逆序
       Log.e("tag",TextUtils.join("-",list));// 在数组中每个元素之间使用“-”来连接
       String[]arr=TextUtils.split(e,"-");// 以"-"来分组
       Log.e("tag",TextUtils.htmlEncode(f)+"");// 使用html编码字符串*/


public class UIUtils {

    /**
     * 判断字符串是否为null或者0长度，字符串在判断长度时，先去除前后的空格,空或者0长度返回true,否则返回false
     *
     * @param str 被判断的字符串
     * @return boolean
     */
    public static boolean isNullOrZeroLenght(String str) {
        return (null == str || "".equals(str.trim())||"null".equals(str.trim())) ? true : false;
    }
    /**
     * dip转换px
     */
    public static int dip2px(float dip) {

        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }


    public static int getScreenWidthPixels() {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int getScreenHeightPixels() {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }


    public static void setViewSize(View view, int x, int y) {
        ViewGroup.LayoutParams laParams = view.getLayoutParams();
        if (laParams != null) {
            laParams.width = x;
            laParams.height = y;
        } else {
            laParams = new ViewGroup.LayoutParams(x, y);
        }
        view.setLayoutParams(laParams);
    }

    public static void setViewWidth(View view, int width) {
        ViewGroup.LayoutParams laParams = view.getLayoutParams();
        if (laParams != null) {
            laParams.width = width;
            view.setLayoutParams(laParams);
        }
    }

    public static void setViewHeight(View view, int height) {
        ViewGroup.LayoutParams laParams = view.getLayoutParams();
        if (laParams != null) {
            laParams.height = height;
            view.setLayoutParams(laParams);
        }
    }

    /*
     * 切换软键盘的状态，如当前为收起变为弹出,若当前为弹出变为收起
     */
    public static void toggleInput(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /*
     * 强制隐藏输入法键盘
     */
    public static void hideInput(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    /**
     * 检测网络是否可用
     *
     * @
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    /**
     * 获取当前网络类型
     *
     * @ 0：没有网络   1：WIFI网络   2：WAP网络    3：NET网络
     */

    public static final int NETTYPE_WIFI = 0x01;
    public static final int NETTYPE_CMWAP = 0x02;
    public static final int NETTYPE_CMNET = 0x03;

    public static int getNetworkType(Context context) {
        int netType = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if (!TextUtils.isEmpty(extraInfo)) {
                if (extraInfo.toLowerCase().equals("cmnet")) {
                    netType = NETTYPE_CMNET;
                } else {
                    netType = NETTYPE_CMWAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NETTYPE_WIFI;
        }
        return netType;
    }


    //获取文件名  不带后缀
    public static String getFileName(String pathandname) {
        int start = pathandname.lastIndexOf("/");
        int end = pathandname.lastIndexOf(".");
        if (start != -1 && end != -1) {
            return pathandname.substring(start + 1, end);
        } else {
            return null;
        }

    }

    //获取文件名带后缀
    public static String getFileNames(String pathandname) {
        int start = pathandname.lastIndexOf("/");
        if (start != -1) {
            return pathandname.substring(start + 1);
        } else {
            return null;
        }

    }

    //获取文件后缀名
    public static String getFileNameExtends(String pathandname) {

        int start = pathandname.lastIndexOf(".");
        int end = pathandname.length();
        if (start != -1 && end != -1) {
            return pathandname.substring(start + 1, end);
        } else {
            return null;
        }

    }

    public static SharedPreferences getSharedPreferences(String filename) {
        return getContext().getSharedPreferences(filename, Context.MODE_PRIVATE);
    }


    /**
     * 获取上下
     *
     * @
     */
    public static Context getContext() {
        return App.getApplication();
    }

    public static String getPackageName() {
        return getContext().getPackageName();
    }

    /**
     * 获取主线程
     *
     * @
     */
    public static Thread getMainThread() {
        return App.getMainThread();
    }

    /**
     * 获取主线程id
     *
     * @
     */
    public static long getMainThreadId() {
        return App.getMainThreadId();
    }

    /**
     * 获取到主线程的looper
     *
     * @
     */
    public static Looper getMainThreadLooper() {
        return App.getMainThreadLooper();
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getHandler() {
        return App.getMainThreadHandler();
    }

    /**
     * 延时在主线程执行runnable
     */
    public static boolean postDelayed(Runnable runnable, long delayMillis) {
        return getHandler().postDelayed(runnable, delayMillis);
    }

    /**
     * 在主线程执行runnable
     */
    public static boolean post(Runnable runnable) {
        return getHandler().post(runnable);
    }

    /**
     * 从主线程looper里面移除runnable
     */
    public static void removeCallbacks(Runnable runnable) {
        getHandler().removeCallbacks(runnable);
    }


    /**
     * 获取图片
     */
    public static Bitmap getAssetsImage(String filename) {
        InputStream ins = null;
        Bitmap bitmap = null;
        try {
            ins = getContext().getAssets().open(filename);
            bitmap = BitmapFactory.decodeStream(ins);
        } catch (IOException e) {
            e.printStackTrace();
            return bitmap;
        } finally {
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    /**
     * @param filename /image/app_private.html
     * @return
     */
    public static String getAssetsUrl(String filename) {
        return "file:///android_asset/" + filename;
    }

    /**
     * 从assets中读取txt
     */
    public static String getAssetsText(String filename) {
        String text;
        try {
            InputStream is = getContext().getAssets().open(filename);
            text = readTextFromInputStream(is);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            text = "";
        }
        return text;
    }

    /**
     * 从raw中读取txt
     */
    public static String getRawText(int resId) {
        String text;
        try {
            InputStream is = getResources().openRawResource(resId);
            text = readTextFromInputStream(is);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            text = "";
        }
        return text;
    }

    /**
     * 从输入流中按行读取txt
     */
    private static String readTextFromInputStream(InputStream is) {
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer buffer = new StringBuffer("");
        String str;
        try {
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
                buffer.append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                reader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return buffer.toString();
    }


    /**
     * 获取资源
     */

    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 获取文字
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 获取文字数组
     */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 获取dimen
     */
    public static int getDimens(@DimenRes int resId) {
        return getResources().getDimensionPixelSize(resId);
    }


    /**
     * 获取颜色
     */

    public static int getColor(@ColorRes int resId) {
        return ContextCompat.getColor(getContext(), resId);
    }

    public static int getColor(Context context, @ColorRes int resId) {
        return ContextCompat.getColor(context, resId);
    }

    /**
     * 获取颜色选择
     */

    public static ColorStateList getColorStateList(int resId) {
        return ContextCompat.getColorStateList(getContext(), resId);
    }

    // 判断当前的线程是不是在主线程
    public static boolean isRunInMainThread() {
        return android.os.Process.myTid() == getMainThreadId();
    }

    public static void runInMainThread(Runnable runnable) {
        // 在主线程运行
        if (isRunInMainThread()) {
            runnable.run();
        } else {
            post(runnable);
        }
    }

    /**
     * 对toast的简易封装确线程安全，可以在非UI线程调用
     */
    public static void showToastSafe(final String str) {
        if (isRunInMainThread()) {

            showToast(str);
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    showToast(str);
                }
            });
        }
    }


    public static void showToastSafesShort(final String str) {
        if (isRunInMainThread()) {
            showToastshort(str);
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    showToastshort(str);
                }
            });
        }
    }

    private static void showToastshort(String str) {
        // Toast toast = Toast.makeText(getContext(), str, Toast.LENGTH_SHORT);
//        IToast toast = ToastCompat.makeText(getContext(), str, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.CENTER, 0, 30);
//        toast.show();
        // android.R.style.Animation_Toast;
        // SnackBarUtils.showTopSnackbar(str);

        Toast toast = Toast.makeText(getContext(), str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private static void showToast(String str) {
        //  Toast toast = Toast.makeText(getContext(), str, Toast.LENGTH_LONG);
//        IToast toast = ToastCompat.makeText(getContext(), str, Toast.LENGTH_LONG);
//        toast.setGravity(Gravity.CENTER, 0, 30);
//        toast.show();
        //  SnackBarUtils.showTopSnackbar(str);
        Toast toast = Toast.makeText(getContext(), str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }


    // 地球平均半径
    private static final double EARTH_RADIUS = 6378137;

    // 把经纬度转为度（°）
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 根据两点间经纬度坐标（double值），计算两点间距离，单位：千米
     * @author ershuai
     * @param lng1
     * @param lat1
     * @param lng2
     * @param lat2
     * @return
     */
    public static double getDistance(Double lng1, Double lat1, Double lng2, Double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = s / 1000;
        DecimalFormat df = new DecimalFormat("#.00");
        s = Double.parseDouble(df.format(s));
        return s;
    }

}

