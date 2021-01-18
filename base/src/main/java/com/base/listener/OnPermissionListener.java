package com.base.listener;


/**
 * Created by yangkuo on 2018-06-02.
 */

public interface OnPermissionListener {
    /**
     * 获取权限成功
     *
     * @param requestCode
     */
    void permissionSuccess(int requestCode);

    /**
     * 权限获取失败
     *
     * @param requestCode
     */
    void permissionFail(int requestCode);
}
