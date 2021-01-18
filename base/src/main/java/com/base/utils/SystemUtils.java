package com.base.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.base.activity.ActivityManage;
import com.base.activity.BaseActivity;
import com.base.common.Config;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import static com.base.application.App.getApplication;

public class SystemUtils {
    // 获取当前版本的版本号
    public static String getVersionName() {
        try {
            PackageManager packageManager = UIUtils.getContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(UIUtils.getContext().getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    // 获取当前应用的版本号
    public static int getVersionCode() {
        try {
            PackageManager packageManager = UIUtils.getContext().getApplicationContext().getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(UIUtils.getContext().getApplicationContext().getPackageName(), 0);
            return packInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    // 获取手机系统的版本号
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    // 获取手机型号
    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }

    // 获取手机sdk版本
    public static int getPhoneSDKVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    // 获取手机厂商
    public static String getPhoneSeller() {
        return android.os.Build.MANUFACTURER;
    }

    //获取androidid
    @SuppressLint("HardwareIds")
    public static String getAndroidId() {
        return Settings.Secure.getString(UIUtils.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    // IMEI码
    @SuppressLint("HardwareIds")
    public static String getDeviceId(Context context) {
        TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (mTelephony == null || ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            UIUtils.showToastSafesShort("没有IMEI权限");
            return getAndroidId();
        } else{
            String imei;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                imei = mTelephony.getImei();
            }
            else {
                imei = mTelephony.getDeviceId();
            }
            Log.e("getAndroidId",getAndroidId());//89c7f32f7824f466
            return UIUtils.isNullOrZeroLenght(imei)? getAndroidId():imei;
        }
    }


    /**
     * 检测所有的权限是否都已授权
     *
     * @param permissions
     * @return
     */
    public static boolean permissionsChecked(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        }

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    // 读取本机号码功能
    public static String getPhoneNO(Context context) {
        TelephonyManager phoneMgr = (TelephonyManager) UIUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (phoneMgr == null || ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "";
        } else {
            @SuppressLint("HardwareIds") String phoneNO = phoneMgr.getLine1Number();
            if (phoneNO == null) phoneNO = "";
            if (phoneNO.startsWith("+86")) {
                phoneNO = phoneNO.replace("+86", "");
            }
            return phoneNO;
        }
    }

    public static void callPhone(Activity activity, String phoneNo) {
        if (phoneNo == null||phoneNo.equals("")) return;
        /** 拨打电话*/
        Intent intent = new Intent(Intent.ACTION_DIAL);//需要客户自己拨打
        //intent = new Intent(Intent.ACTION_CALL);//帮客户拨通
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (!phoneNo.startsWith("tel:")) {
            phoneNo = "tel:" + phoneNo;
        }
//        phoneNo = phoneNo.replaceAll("-", "");
        Uri data = Uri.parse(phoneNo);
        intent.setData(data);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
            } else activity.startActivity(intent);

        } else activity.startActivity(intent);

    }

    /**
     * 发送短信(掉起发短信页面)
     *
     * @param tel     电话号码
     * @param content 短息内容
     */
    public static void sendMessage(Activity activity, String tel, String content) {

        if (PhoneNumberUtils.isGlobalPhoneNumber(tel)) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + tel));
            intent.putExtra("sms_body", content);
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    activity.requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
                } else activity.startActivity(intent);

            } else activity.startActivity(intent);
        }


    }

    public static void callQQ(String qq) {
        String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq;
        BaseActivity baseActivity = ActivityManage.getTopActivity();
        if (baseActivity == null) return;
        baseActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        // 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        // 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断qq是否可用     *      * @param context     * @return
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    //获取当前屏幕是否开启旋转  /0为关闭 1为开启

    public static int getScreenState() {
        return Settings.System.getInt(UIUtils.getContext().getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0);
    }

    //设置当前屏幕是否开启旋转  /0为关闭 1为开启
    public static void setScreenState(int state) {
        Settings.System.putInt(UIUtils.getContext().getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, state);
    }

    //设置当前屏幕竖屏
    public static void setScreenVertical(Activity activity) {
        //  activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //保持竖屏
        if (activity.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    //设置当前屏幕横屏
    public static void setScreenHorizontal(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    //设置当前屏幕默认跟随系统
    public static void setScreenDefault(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    /**
     * 判断手机设备是否安装指定包名的apk应用程序
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isSpecialApplInstalled(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void install(Context context, File file) {
        Intent var2 = new Intent();
        var2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        var2.setAction(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri uriForFile = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileprovider", file);
            var2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            var2.setDataAndType(uriForFile, context.getContentResolver().getType(uriForFile));
        } else {
            var2.setDataAndType(Uri.fromFile(file), getMIMEType(file));
        }
        try {
            context.startActivity(var2);
        } catch (Exception var5) {
            var5.printStackTrace();
            UIUtils.showToastSafesShort("没有找到打开此类文件的程序");
        }

    }

    public static void clearApk(String apkName) {
        //检查本地是否有该版本的安装包
        String absPath = Config.APKSaveDir + "/" + apkName;

        final File file = new File(absPath);
        if (IOUtils.fileIsExists(file) && SystemUtils.getVersionName().compareTo(SystemUtils.getApkVersionName(absPath, UIUtils.getContext())) >= 0) {
            file.delete();
        }
    }

    private static String getMIMEType(File var0) {
        String var1 = "";
        String var2 = var0.getName();
        String var3 = var2.substring(var2.lastIndexOf(".") + 1, var2.length()).toLowerCase();
        var1 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(var3);
        return var1;
    }

    /**
     * 获取apk包的信息：版本号，名称，图标等
     *
     * @param absPath apk包的绝对路径
     * @param context
     */
    public static String getApkVersionName(String absPath, Context context) {

        PackageManager pm = context.getPackageManager();
        PackageInfo pkgInfo = pm.getPackageArchiveInfo(absPath, PackageManager.GET_ACTIVITIES);

        if (pkgInfo != null) {
            ApplicationInfo appInfo = pkgInfo.applicationInfo;
            /* 必须加这两句，不然下面icon获取是default icon而不是应用包的icon */
            appInfo.sourceDir = absPath;
            appInfo.publicSourceDir = absPath;
            String appName = pm.getApplicationLabel(appInfo).toString();// 得到应用名
            String packageName = appInfo.packageName; // 得到包名
            String version = pkgInfo.versionName; // 得到版本信息

            /* icon1和icon2其实是一样的 */
            //     Drawable icon1 = pm.getApplicationIcon(appInfo);// 得到图标信息
            //    Drawable icon2 = appInfo.loadIcon(pm);
            //    String pkgInfoStr = String.format("PackageName:%s, Vesion: %s, AppName: %s", packageName, version, appName);
            //  Log.i(TAG, String.format("PkgInfo: %s", pkgInfoStr));
            return version;
        }
        return "1.0";
    }

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param context
     * @param className 服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public static boolean isServiceExisted(Context context, String className) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(Integer.MAX_VALUE);

        if (!(serviceList.size() > 0)) {
            return false;
        }

        for (int i = 0; i < serviceList.size(); i++) {
            ActivityManager.RunningServiceInfo serviceInfo = serviceList.get(i);
            ComponentName serviceName = serviceInfo.service;

            if (serviceName.getClassName().equals(className)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断某个进程是否正在运行的方法
     *
     * @param context
     * @param proessName 进程的名字
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public static boolean isProessRunning(Context context, String proessName) {

        boolean isRunning = false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningAppProcessInfo> lists = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : lists) {
            if (info.processName.equals(proessName)) {
                isRunning = true;
            }
        }

        return isRunning;
    }

    public static void startAppLocationSetting(BaseActivity context, int... requestCode) {
        Intent locationIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        if (requestCode.length > 0) context.startActivityForResult(locationIntent, requestCode[0]);
        else context.startActivity(locationIntent);
    }

    public static void startAppSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }

    public static void startManageApplications(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.setClassName("com.android.settings", "com.android.settings.ManageApplications");
        context.startActivity(intent);
    }


    public static void startManageApplicationInfo(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    public static void startManageJurisdiction(BaseActivity context, int... location_jurisdiction_requestCode) {

        String model = android.os.Build.MODEL; // 手机型号
        String release = android.os.Build.VERSION.RELEASE; // android系统版本号
        String brand = Build.BRAND;//手机厂商
        if (TextUtils.equals(brand.toLowerCase(), "redmi") || TextUtils.equals(brand.toLowerCase(), "xiaomi")) {
            gotoMiuiPermission(context, location_jurisdiction_requestCode);//小米
        } else if (TextUtils.equals(brand.toLowerCase(), "meizu")) {
            gotoMeizuPermission(context, location_jurisdiction_requestCode);
        } else if (TextUtils.equals(brand.toLowerCase(), "huawei") || TextUtils.equals(brand.toLowerCase(), "honor")) {
            gotoHuaweiPermission(context, location_jurisdiction_requestCode);
        } else {
            if (location_jurisdiction_requestCode.length > 0)
                context.startActivityForResult(getAppDetailSettingIntent(context), location_jurisdiction_requestCode[0]);
            else context.startActivity(getAppDetailSettingIntent(context));
        }

    }


    /**
     * 跳转到miui的权限管理页面
     */
    private static void gotoMiuiPermission(BaseActivity context, int... location_jurisdiction_requestCode) {
        try { // MIUI 8
            Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            localIntent.putExtra("extra_pkgname", context.getPackageName());

            if (location_jurisdiction_requestCode.length > 0)
                context.startActivityForResult(localIntent, location_jurisdiction_requestCode[0]);
            else context.startActivity(localIntent);

        } catch (Exception e) {
            try { // MIUI 5/6/7
                Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                localIntent.putExtra("extra_pkgname", context.getPackageName());

                if (location_jurisdiction_requestCode.length > 0)
                    context.startActivityForResult(localIntent, location_jurisdiction_requestCode[0]);
                else context.startActivity(localIntent);

            } catch (Exception e1) {
                // 否则跳转到应用详情
                if (location_jurisdiction_requestCode.length > 0)
                    context.startActivityForResult(getAppDetailSettingIntent(context), location_jurisdiction_requestCode[0]);
                else context.startActivity(getAppDetailSettingIntent(context));
            }
        }
    }

    /**
     * 跳转到魅族的权限管理系统
     */
    private static void gotoMeizuPermission(BaseActivity context, int... location_jurisdiction_requestCode) {
        try {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra("packageName", context.getPackageName());

            if (location_jurisdiction_requestCode.length > 0)
                context.startActivityForResult(intent, location_jurisdiction_requestCode[0]);
            else context.startActivity(intent);

        } catch (Exception e) {
            // 否则跳转到应用详情
            if (location_jurisdiction_requestCode.length > 0)
                context.startActivityForResult(getAppDetailSettingIntent(context), location_jurisdiction_requestCode[0]);
            else context.startActivity(getAppDetailSettingIntent(context));
        }
    }

    /**
     * 华为的权限管理页面
     */
    private static void gotoHuaweiPermission(BaseActivity context, int... location_jurisdiction_requestCode) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
            //华为权限管理
            intent.setComponent(comp);

            if (location_jurisdiction_requestCode.length > 0)
                context.startActivityForResult(intent, location_jurisdiction_requestCode[0]);
            else context.startActivity(intent);
        } catch (Exception e) {
            // 否则跳转到应用详情
            if (location_jurisdiction_requestCode.length > 0)
                context.startActivityForResult(getAppDetailSettingIntent(context), location_jurisdiction_requestCode[0]);
            else context.startActivity(getAppDetailSettingIntent(context));
        }
    }

    /**
     * 获取应用详情页面intent（如果找不到要跳转的界面，也可以先把用户引导到系统设置页面）     *     * @return
     */
    private static Intent getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        return localIntent;
    }

}
