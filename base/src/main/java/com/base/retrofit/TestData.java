package com.base.retrofit;

import retrofit2.Call;

/**
 * Created by yangkuo on 2018-07-28.
 */
public class TestData<T> {


    //-1 请求失败，0正在请求，1请求成功，2请求取消
    public int state = 0;

    //报错信息
    public String errStr = "";

    public Call call;

    // <100 内部错误  applicationErr;
    //  >=100:异常 onFailure
    // >=1000，网络错误  onNetWorkErr
    public int errCode = 0;
    public String url;//请求访问的路径
    public T data = null;//返回的数据
    //请求开始时间
    public long startTime = System.currentTimeMillis();

    //请求结束时间
    public long endTime = 0L;
}
