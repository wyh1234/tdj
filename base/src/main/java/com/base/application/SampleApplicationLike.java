package com.base.application;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.base.common.PublicStaticValue;
import com.base.utils.UIUtils;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.interfaces.BetaPatchListener;
import com.tencent.bugly.beta.upgrade.UpgradeStateListener;
import com.tencent.tinker.loader.app.DefaultApplicationLike;

/**
 * Created by yangkuo on 2018-04-11.
 */

public class SampleApplicationLike extends DefaultApplicationLike {

    public static final String TAG = "Tinker.ApplicationLike";

    public SampleApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);

    }


    @Override
    public void onCreate() {
        super.onCreate();

        //自动检查更新开关
        Beta.autoCheckUpgrade = false;

        // 设置升级检查周期为60s(默认检查周期为0s)，60s内SDK不重复向后台请求策略);
        Beta.upgradeCheckPeriod = 60 * 60 * 1000;
        //设置sd卡的Download为更新资源存储目录
//        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        //设置Wifi下自动下载
        Beta.autoDownloadOnWifi = true;

        // 设置是否开启热更新能力，默认为true
        Beta.enableHotfix = true;
        // 设置是否自动下载补丁
        Beta.canAutoDownloadPatch = true;
        // 设置是否提示用户重启
        Beta.canNotifyUserRestart = false;
        // 设置是否自动合成补丁
        Beta.canAutoPatch = true;
        //延迟1秒初始化
        Beta.initDelay = 1000L;

        // 补丁回调接口
        Beta.betaPatchListener = new BetaPatchListener() {
            @Override
            public void onPatchReceived(String patchFile) {
                Log.d(TAG, "补丁下载地址" + patchFile);
            }

            @Override
            public void onDownloadReceived(long savedLength, long totalLength) {
//                Log.d("beta", String.format(Locale.getDefault(), "%s %d%%", Beta.strNotificationDownloading, (int) (totalLength == 0 ? 0 : savedLength * 100 / totalLength)));
            }

            @Override
            public void onDownloadSuccess(String msg) {
                Log.d(TAG, "补丁下载成功" + msg);
//                Beta.applyDownloadedPatch();
            }

            @Override
            public void onDownloadFailure(String msg) {
                Log.d(TAG, "补丁下载失败");
            }

            @Override
            public void onApplySuccess(String msg) {
                Log.d(TAG, "补丁应用成功");
                PublicStaticValue.isPatch = true;
            }

            @Override
            public void onApplyFailure(String msg) {
                Log.d(TAG, "补丁应用失败");

            }

            @Override
            public void onPatchRollback() {
                Log.d(TAG, "补丁回滚");
            }
        };

        /* 设置更新状态回调接口 */
        Beta.upgradeStateListener = new UpgradeStateListener() {
            @Override
            public void onUpgradeSuccess(boolean isManual) {
                Log.d(TAG, "更新成功");
                if (isManual) UIUtils.showToastSafesShort("请更新最新版本");
            }

            /**
             * 更新失败
             * @param isManual  true:手动检查 false:自动检查
             */
            @Override
            public void onUpgradeFailed(boolean isManual) {
                Log.d(TAG, "升级失败");
            }

            /**
             * 正在更新
             * @param isManual
             */
            @Override
            public void onUpgrading(boolean isManual) {

                Log.d(TAG, "正在更新...");
            }

            @Override
            public void onDownloadCompleted(boolean b) {
                Log.d(TAG, "下载完成");
            }

            /**
             * 没有更新
             * @param isManual
             */
            @Override
            public void onUpgradeNoVersion(boolean isManual) {
                Log.d(TAG, "没有更新");
//                if (PublicStaticValue.isCheckUpdate = true) {
//                    PublicStaticValue.isCheckUpdate = false;
//                    UIUtils.showToastSafesShort("已是最新版本");
//                }
                if (!isManual) {
                    UIUtils.showToastSafesShort("已是最新版本");
                }
            }
        };

    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        // 安装tinker
        Beta.installTinker(this);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }

}
