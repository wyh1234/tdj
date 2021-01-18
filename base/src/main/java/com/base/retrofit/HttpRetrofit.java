package com.base.retrofit;


import android.util.Log;

import com.base.retrofit.cookie.CookieManger;
import com.base.retrofit.jackSon.JacksonConverterFactoryCustomer;
import com.base.retrofit.jackSon.JacksonUtils;
import com.base.utils.SystemUtils;
import com.base.utils.UIUtils;
import com.ihsanbal.logging.LoggingInterceptor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;


public class HttpRetrofit {

    // 创建请求服务
    public static <T> T getRetrofitApiService(Class<T> cls, String ROOT_URL) {
        return getRetrofit(ROOT_URL).create(cls);
    }
    // 创建请求服务
    public static <T> T getRetrofitApiServices(Class<T> cls, String ROOT_URL,String url) {
        return getRetrofitJson(ROOT_URL,url).create(cls);
    }


    //创建适配器
    private static Retrofit getRetrofit(String ROOT_URL) {
        //添加请求头
        Map<String, String> map = new HashMap<>();
        map.put("user-agent", "Android");
        map.put("Content-Type", "application/json");
        //添加公共的请求参数
        Map<String, String> params = new HashMap<>();
        params.put("versionType", "Android");
        params.put("versionCode", String.valueOf(SystemUtils.getVersionCode()));
        params.put("versionName", SystemUtils.getVersionName());
        params.put("uniqueId", SystemUtils.getAndroidId());
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
//                .addNetworkInterceptor(new HttpLoggingInterceptor()
//                        .setLevel(HttpLoggingInterceptor.Level.HEADERS))
                .cookieJar(new CookieManger(UIUtils.getContext()))//添加cookies管理器
                .addInterceptor(new HttpInterceptor(map, params))//添加拦截器
                .connectTimeout(60, TimeUnit.SECONDS)//
                .readTimeout(60, TimeUnit.SECONDS);

//        if (BuildConfig.DEBUG) {
//打印网络请求日志
            LoggingInterceptor httpLoggingInterceptor = new LoggingInterceptor.Builder().loggable(true)
//                    .setLevel(Level.BASIC)
//                    .log(Platform.INFO)
                    .request("reader")
//                    .response("reader")
                    .build();
            okHttpClient.addInterceptor(httpLoggingInterceptor);
 //       }

        return new Retrofit.Builder().baseUrl(ROOT_URL)
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                    .setDateFormat(DateUtils.getSimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
//                .addConverterFactory(JacksonConverterFactory.create(JacksonUtils.getObjectMapper()))
                .addConverterFactory(JacksonConverterFactoryCustomer.create(JacksonUtils.getObjectMapper())).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(okHttpClient.build()).build();
    }


    //创建适配器
    private static Retrofit getRetrofitJson(String ROOT_URL,String url) {
        //添加请求头
        List<Cookie> cookies =new CookieManger(UIUtils.getContext()).loadForRequest(HttpUrl.parse(url));
        Map<String, String> map = new HashMap<>();
        map.put("user-agent", "Android");
        map.put("Content-Type", "application/json");
        if (cookies!=null&&cookies.size()>0){
            for (Cookie cookie:cookies){
                if ("_n_i".equals(cookie.name())){
                    map.put("sign",cookie.value());
                }
            }

        }
        //添加公共的请求参数
        Map<String, String> params = new HashMap<>();
        params.put("versionType", "Android");
        params.put("versionCode", String.valueOf(SystemUtils.getVersionCode()));
        params.put("versionName", SystemUtils.getVersionName());
        params.put("uniqueId", SystemUtils.getAndroidId());
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
//                .addNetworkInterceptor(new HttpLoggingInterceptor()
//                        .setLevel(HttpLoggingInterceptor.Level.HEADERS))
//                .cookieJar(new CookieManger(UIUtils.getContext()))//添加cookies管理器
                .addInterceptor(new CustomParamsInterceptor(map, params))//添加拦截器
                .connectTimeout(60, TimeUnit.SECONDS)//
                .readTimeout(60, TimeUnit.SECONDS);

//        if (BuildConfig.DEBUG) {
//打印网络请求日志
        LoggingInterceptor httpLoggingInterceptor = new LoggingInterceptor.Builder().loggable(true)
//                    .setLevel(Level.BASIC)
//                    .log(Platform.INFO)
                .request("reader")
//                    .response("reader")
                .build();
        okHttpClient.addInterceptor(httpLoggingInterceptor);
        //       }

        return new Retrofit.Builder().baseUrl(ROOT_URL)
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                    .setDateFormat(DateUtils.getSimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
//                .addConverterFactory(JacksonConverterFactory.create(JacksonUtils.getObjectMapper()))
                .addConverterFactory(JacksonConverterFactoryCustomer.create(JacksonUtils.getObjectMapper())).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(okHttpClient.build()).build();
    }


}
