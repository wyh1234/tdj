package com.base.retrofit;


import android.support.annotation.NonNull;

import com.base.activity.BaseActivity;
import com.base.entity.ResultInfo;
import com.base.viewModel.DataLoadingListener;

import retrofit2.Call;

public abstract class ResultInfoCallback<T> extends RequestCallback<ResultInfo<T>> {

    public ResultInfoCallback() {

    }

    public ResultInfoCallback(@NonNull DataLoadingListener dataLoadingListener) {
        super(dataLoadingListener);
    }

    @Override
    protected final void onSuc(ResultInfo<T> body) {
        onSuccess(body.getData());
    }

    public abstract void onSuccess(T body);

}
