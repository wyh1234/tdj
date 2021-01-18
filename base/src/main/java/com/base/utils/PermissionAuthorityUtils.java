package com.base.utils;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.SparseArrayCompat;

import com.base.activity.BaseActivity;
import com.base.listener.OnPermissionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangkuo on 2018-09-10.
 */
public class PermissionAuthorityUtils {


    //请求权限回调集合
    private SparseArrayCompat<OnPermissionListener> permissionListenerIntegerHashMap = new SparseArrayCompat<>();


    private BaseActivity context;

    public PermissionAuthorityUtils(BaseActivity context) {
        this.context = context;
    }


    /**
     * 添加请求权限监听
     */
    public void addPermissionListen(int requestCode, OnPermissionListener onPermissionListener) {
        permissionListenerIntegerHashMap.put(requestCode, onPermissionListener);
    }

    /**
     * 请求权限
     *
     * @param permissions 请求的权限
     * @param requestCode 请求权限的请求码
     */
    public void permissionRequest(int requestCode, String... permissions) {
        OnPermissionListener onPermissionListener;

        if (permissionsChecked(permissions)) {
            if ((onPermissionListener = permissionListenerIntegerHashMap.get(requestCode)) != null) {
                onPermissionListener.permissionSuccess(requestCode);
            }
        } else {
            List<String> needPermissions = getDeniedPermissions(permissions);
            ActivityCompat.requestPermissions(context, needPermissions.toArray(new String[needPermissions.size()]), requestCode);

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                context.requestPermissions(needPermissions.toArray(new String[needPermissions.size()]), REQUEST_CODE_PERMISSION);
//            } else
//                ActivityCompat.requestPermissions(context, needPermissions.toArray(new String[needPermissions.size()]), REQUEST_CODE_PERMISSION);

        }
    }

    /**
     * 检测所有的权限是否都已授权
     *
     * @param permissions
     * @return
     */
    public boolean permissionsChecked(String... permissions) {
        for (String permission : permissions) {
            int i = context.checkCallingOrSelfPermission(permission);
            if (i == PackageManager.PERMISSION_GRANTED) {

            }
        }

        return SystemUtils.permissionsChecked(context, permissions);
//        return false;
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     */
    private List<String> getDeniedPermissions(String... permissions) {
        List<String> needRequestPermissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED || ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                needRequestPermissionList.add(permission);
            }
        }
        return needRequestPermissionList;
    }


    /**
     * 系统请求权限回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        OnPermissionListener onPermissionListener;
        if ((onPermissionListener = permissionListenerIntegerHashMap.get(requestCode)) != null) {
            if (verifyPermissions(grantResults)) {
                onPermissionListener.permissionSuccess(requestCode);
            } else {
                onPermissionListener.permissionFail(requestCode);
//                showTipsDialog();
            }
        }
    }

    /**
     * 确认所有的权限是否都已授权
     *
     * @param grantResults
     * @return
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 显示提示对话框
     */
    private void showTipsDialog() {
        ViewUtils.showDialog(context, "提示信息", "应用缺少必要权限，该功能暂时无法使用。如若需要，请单击【设置】按钮前往应用列表进行权限授权。").setNegativeButton("取消", (dialog, which) -> dialog.dismiss()).setPositiveButton("设置", (dialog, which) -> startAppSettings()).show();


//        new AlertDialog.Builder(context, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).setTitle("提示信息").setMessage("应用缺少必要权限，该功能暂时无法使用。如若需要，请单击【设置】按钮前往设置中心进行权限授权。").setNegativeButton("取消", (dialog, which) -> dialog.dismiss()).setPositiveButton("设置", (dialog, which) -> startAppSettings()).show();
    }

    /**
     * 启动当前应用设置页面
     */
    public void startAppSettings() {
        SystemUtils.startManageJurisdiction(context);
    }


}
