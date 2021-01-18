package com.base.viewModel;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.base.activity.BaseActivity;
import com.base.activity.BaseFragment;
import com.base.entity.ResultInfo;
import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;
import com.base.viewModel.adapter.OnItemClickListener;

import java.util.Map;

import retrofit2.Call;


/**
 * Created by yangkuo on 2018-10-16.
 */
public abstract class BaseViewModel extends DataBaseViewModel implements OnItemClickListener {


    /**
     * //ui和data的对应关系
     *
     * @return 如果不需要自动赋值，直接返回null
     */
    public abstract BaseVM getVM();


    // ------------------------------------------------------------- 以上是需要重写的方法----------------------------------------------------------------------------------------------

    private Intent mIntent;

    private View mainView;
    public BaseViewHolder viewHolder;//用于操作ui


    public BaseViewModel(BaseActivity activity) {
        super(activity);
        mIntent = activity.getIntent();
        mainView = activity.getBaseContentView();
    }

    public BaseViewModel(BaseFragment fragment) {
        super(fragment);
        mIntent = fragment.getBaseActivity().getIntent();
        mainView = fragment.getRootView();
    }


    @Override
    public void initData() {
        super.initData();
        viewHolder = new BaseViewHolder(mainView, getVM(), this);
    }

    public void addRequest(Call call, RequestCallback callback) {
        callback.setCall(call).enqueue(callback);
    }

    public void addRequest(Call call, ResultInfoCallback callback) {
        callback.setCall(call).enqueue(callback);
    }


    public BaseViewHolder getViewHolder() {
        return viewHolder;
    }


    public <T> void setData(@NonNull T obj) {
        viewHolder.setValues(obj);
    }

    public <T> void setData(Map<String, Object> map, T bean) {
        viewHolder.setValues(map, bean);
    }


    public void setData(@NonNull String pamstr, Object value) {
        viewHolder.setValues(pamstr, value);
    }

    public Intent getIntent() {
        return mIntent;
    }

    public View getRootView() {
        return mainView;
    }
}
