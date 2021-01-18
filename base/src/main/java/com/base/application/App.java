package com.base.application;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;

import com.base.common.Config;
import com.base.utils.IOUtils;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

/**
 * Created by Administrator on 2017-12-30.
 */

public class App extends TinkerApplication {
    // 获取到主线程的上下文
    private static Application mContext = null;
    // 获取到主线程的handler
    private static Handler mMainThreadHandler = null;
    // 获取到主线程的looper
    private static Looper mMainThreadLooper = null;
    // 获取到主线程
    private static Thread mMainThead = null;
    // 获取到主线程的id
    private static int mMainTheadId;


    public App() {
        //bugly
        super(ShareConstants.TINKER_ENABLE_ALL, "com.base.application.SampleApplicationLike", "com.tencent.tinker.loader.TinkerLoader", false);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //创建更新文件目录和图片存放目录
        IOUtils.makeRootDirectory(Config.APKSaveDir);
        IOUtils.makeRootDirectory(Config.imageSaveDir);
        IOUtils.makeRootDirectory(Config.glideImageSaveDir);

        //线程池工具类初始化
        mContext = this;
        mMainThreadHandler = new Handler();
        mMainThreadLooper = getMainLooper();
        mMainThead = Thread.currentThread();
        // android.os.Process.myUid()获取到用户id
        // android.os.Process.myPid();//获取到进程id
        // android.os.Process.myTid()获取到调用线程的id
        mMainTheadId = Process.myTid();// 主线程id


        /** CookieManager初始化*/
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookieManager);

        //打开错误日志，保存到sd卡
        //  MyCrashHandler myCrashHandler = MyCrashHandler.getInstance();
        //  myCrashHandler.init(this);
        //内存泄漏检测工具导入
//        LeakCanary.install(this);


    }

    public static Application getApplication() {
        return mContext;
    }

    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    public static Looper getMainThreadLooper() {
        return mMainThreadLooper;
    }

    public static Thread getMainThread() {
        return mMainThead;
    }

    public static int getMainThreadId() {
        return mMainTheadId;
    }

}
