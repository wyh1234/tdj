package com.base.retrofit;

import retrofit2.Call;

/**
 * Created by yangkuo on 2018-10-17.
 * 请求状态
 */
public class RequestState {

    /**
     * <100 内部错误  applicationErr;
     * >=100:异常 onFailure
     * >=1000，网络错误  onNetWorkErr
     */
    public int errCode = 0;
    /**
     * 报错信息
     */
    public String errStr = "";
    /**
     * 用于区分请求
     * ps:  页面3个请求，可以分为0, 1，2
     */
    public int typeCode = 0;
    /**
     * 请求类别
     * activity加载时的为 0
     * 点击事件时的提交为 1
     */
    public int type_from = 0;

    /**
     * 点击的位置
     * 只有{@link #type_from ==1时有效}
     */
    public int position;
    /**
     * //-1 请求失败，0正在请求，1请求成功，2请求取消
     */
    public int state = 0;
    public Call call;  //请求
    public String url;//请求访问的路径
    public Object data = null;//返回的数据
    public long startTime; //请求开始时间
    public long endTime = 0L; //请求结束时间

    /**
     * @param call ==null 时表示不需要取消请求
     */
    public RequestState(Call call) {
        this(call, 0);
    }

    public RequestState(Call call, int typeCode) {
        this(call, typeCode, -1);
    }

    public RequestState(Call call, int typeCode, int position) {
        this.call = call;
        if (call != null) url = call.request().url().toString();
        startTime = System.currentTimeMillis();
        if (position > -1) type_from = 1;
        this.position = position;
        this.typeCode = typeCode;
    }

}
