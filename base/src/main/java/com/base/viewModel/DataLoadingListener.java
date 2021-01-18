package com.base.viewModel;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.base.activity.BaseActivity;

/**
 * Created by yangkuo on 2018/11/13.
 */
public interface DataLoadingListener {

    /**
     * 单个请求返回时回调该方法
     */
    void requestReturnUniform();

    View getRootView();

    LinearLayout ll_base_loading();

    BaseActivity getBaseActivity();
}
