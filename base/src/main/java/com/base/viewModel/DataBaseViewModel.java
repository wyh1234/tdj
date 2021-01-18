package com.base.viewModel;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.base.R;
import com.base.retrofit.HttpRequestManage;
import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;

/**
 * Created by yangkuo on 2018/11/13.
 */
public class DataBaseViewModel {

    private DataLoadingListener dataLoadingListener;

    private View base_reload;
    private LinearLayout ll_base_loading;
    private long time = System.currentTimeMillis();//初始化时间

    private View base_loading;

    public DataBaseViewModel(DataLoadingListener dataLoadingListener) {
        this.dataLoadingListener = dataLoadingListener;
        ll_base_loading = dataLoadingListener.ll_base_loading();
    }


    public void onStartLoading() {
        closeBase_loading();
        closeBase_reload();
        ll_base_loading.addView(getBase_loading());
    }

    /**
     * Activity重新加载数据
     */
    public void onDataReload() {
        closeBase_loading();
        closeBase_reload();
        ll_base_loading.addView(getBase_reload());
    }

    /**
     * Activity成功
     */
    public void onDataLoadingSucc() {
        long date = System.currentTimeMillis() - time;
        if (date < 1500) {
            ll_base_loading.postDelayed(this::onDataLoadingSucc, 1500 - date);
        } else {
            closeBase_loading();
            closeBase_reload();

            //所有请求都成功后，清除之前的请求记录，后面activity可能再次刷新，如果有一个请失败则走重新加载的路线
            HttpRequestManage.clear(dataLoadingListener.hashCode());
        }
    }

    /**
     * 所有请求返回时回调
     */
    private void requestAllReturn(List<RequestCallback> list) {
        if (isLoading()) {
            for (RequestCallback requestCallback : list) {
                if (requestCallback != null) {
                    //加载失败，重新加载
                    if (requestCallback.testData.state == -1 || requestCallback.testData.state == 2) {
                        onDataReload();
                        return;
                    }
                }
            }
            //加载成功
            onDataLoadingSucc();
        }
    }

    /**
     * 重新加载失败的请求
     */
    public void reloadingRequest() {
        List<RequestCallback> list = HttpRequestManage.getRequestList(dataLoadingListener.hashCode());
        for (RequestCallback requestCallback : list) {
            if (requestCallback.testData.state == -1 || requestCallback.testData.state == 2) {
                Call call = requestCallback.testData.call.clone();
                if (call != null) {
                    requestCallback.setCall(call);
                    requestCallback.testData.state = 0;
                    call.enqueue(requestCallback);
                }

            }
        }
    }


    /**
     * 取消还未返回的请求
     * {@link RequestCallback#setCall(Call)} 调用了这个方法才可以取消
     */
    public void requestCancel() {
        List<RequestCallback> list = HttpRequestManage.getRequestList(dataLoadingListener.hashCode());
        for (RequestCallback requestCallback : list) {
            if (requestCallback.testData.state == 0 && requestCallback.testData.call != null) {
                requestCallback.testData.state = 2;
                requestCallback.testData.call.cancel();
            }
        }
    }


    /**
     * 请求返回时回调该方法
     */
    public final void requestReturnUniform() {
        List<RequestCallback> list = HttpRequestManage.getRequestList(dataLoadingListener.hashCode());
        if (list.size() == 1) {
            requestAllReturn(list);
        } else {
            boolean back = true;
            for (RequestCallback requestCallback : list) {
                if (requestCallback.testData.state == 0) {
                    back = false;
                    break;
                }
            }
            if (back) requestAllReturn(list);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isLoading()) {
                dataLoadingListener.getRootView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isLoading()) {
                            UIUtils.showToastSafesShort("正在取消网络请求");
                        }
                    }
                }, 500);
                requestCancel();
                return true;
            }
        }
        return false;
    }

    public void onDestroy() {
        if (dataLoadingListener != null) HttpRequestManage.clear(dataLoadingListener.hashCode());
    }

    public View getBase_reload() {
        if (base_reload == null) {
            base_reload = ViewUtils.getLayoutViewMatch(dataLoadingListener.getBaseActivity(), R.layout.activity_base_reload);//重新加载
            base_reload.setOnClickListener(v -> {
            });
            base_reload.findViewById(R.id.tv_reloading).setOnClickListener(v -> {
                onStartLoading();
                reloadingRequest();
            });
        }
        return base_reload;
    }

    public void closeBase_reload() {
        ViewUtils.removeSelfFromParent(base_reload);
        base_reload = null;
    }

    public View getBase_loading() {
        if (base_loading == null) {
            base_loading = ViewUtils.getLayoutViewMatch(dataLoadingListener.getBaseActivity(), R.layout.activity_base_loading);//正在加载
            base_loading.setOnClickListener(v -> {
            });
        }
        ImageView iv_gif_loading = base_loading.findViewById(R.id.iv_gif_loading);
        Glide.with(iv_gif_loading).load(R.mipmap.gif_loading).into(iv_gif_loading);
        return base_loading;
    }

    public void closeBase_loading() {
        ViewUtils.removeSelfFromParent(base_loading);
        base_loading = null;
    }

    /**
     * 是否正在加载数据
     *
     * @return
     */
    public boolean isLoading() {
//        if (base_loading != null) {
//            return base_loading.getVisibility() == View.VISIBLE;
//        }
//        return false;
//        return base_loading.getParent() != null;
        return base_loading != null;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    public void initData() {

    }
}
