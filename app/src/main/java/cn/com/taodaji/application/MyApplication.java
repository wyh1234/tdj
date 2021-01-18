package cn.com.taodaji.application;


import android.annotation.TargetApi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.util.Log;

import com.apkfuns.logutils.LogUtils;
import com.base.application.App;
import com.base.utils.UIUtils;
import com.orm.SugarContext;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.message.IUmengCallback;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import org.greenrobot.eventbus.EventBus;
import cn.com.taodaji.common.Constants;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.event.UmengTokenEvent;


public class MyApplication extends App implements Constants {
    private SharedPreferences sp;


    public MyApplication() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化sugar
        SugarContext.init(this);
        UMConfigure.init(UIUtils.getContext() ,UMConfigure.DEVICE_TYPE_PHONE,"6190a0e186168b2ca3478f821120efce");
        // 选用LEGACY_AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.LEGACY_MANUAL);
        // 支持在子进程中统计自定义事件
        UMConfigure.setProcessEvent(true);
        UMConfigure.setLogEnabled(true);
        LogUtils.i("UMConfigure"+getTestDeviceInfo(UIUtils.getContext())[0]+"UMConfigure"+getTestDeviceInfo(UIUtils.getContext())[1]);


//        Config.DEBUG = true;
        UMShareAPI.get(this);
        PlatformConfig.setWeixin("wxf1c361fc73d52a29", "58faa8bcfd0184cd54e661a65b07debd");
        PlatformConfig.setQQZone("1105981855", "L8VEYdKQt0YfHP49");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");


        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.d("myToken", deviceToken);
                PublicCache.deviceToken_umeng = deviceToken;
                EventBus.getDefault().post(new UmengTokenEvent(deviceToken));
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.d("myToken_err", s1);
            }
        });

        //sdk开启通知声音
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
        mPushAgent.setNotificationClickHandler(new UmengClickHandler());

        //默认显示通知栏数量
        mPushAgent.setDisplayNotificationNumber(1);

        intiMessage();
    }

    public void intiMessage() {
        sp = getSharedPreferences("is_open_news", 0);

        // boolean order_message = sp.getBoolean("order_message", true);
        boolean message = sp.getBoolean("message", true);

        PushAgent mPushAgent = PushAgent.getInstance(this);


        if (message) {
            mPushAgent.enable(new IUmengCallback() {
                @Override
                public void onSuccess() {
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean("order_message", false);
                    //提交edit
                    edit.commit();
                }

                @Override
                public void onFailure(String s, String s1) {

                }
            });
        } else {
            //点击开关取消推送   注销回调：IUmengCallback；，则需要调用以下代码（请在Activity内调用）：
            mPushAgent.disable(new IUmengCallback() {
                @Override
                public void onSuccess() {
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean("order_message", false);
                    //提交edit
                    edit.commit();
                }

                @Override
                public void onFailure(String s, String s1) {

                }
            });
        }


    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }

    @TargetApi(9)
    protected void setStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
    }

    public  String[] getTestDeviceInfo(Context context){
        String[] deviceInfo = new String[2];
        try {
            if(context != null){
                deviceInfo[0] = DeviceConfig.getDeviceIdForGeneral(context);
                deviceInfo[1] = DeviceConfig.getMac(context);
            }
        } catch (Exception e){
        }
        return deviceInfo;
    }
}
