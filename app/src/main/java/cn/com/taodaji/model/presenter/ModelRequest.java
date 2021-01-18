package cn.com.taodaji.model.presenter;

import com.base.retrofit.HttpRetrofit;

import cn.com.taodaji.common.PublicCache;

/**
 * Created by yangkuo on 2018-10-15.
 */
public class ModelRequest {

    private static ModelService requestService1;//公共的请求

    private static ModelService requestService2;//公共的请求

    private ModelRequest() {

    }

    public static ModelService getInstance(int... type) {
        if (requestService1 == null) {
            requestService1 = HttpRetrofit.getRetrofitApiService(ModelService.class, PublicCache.getROOT_URL().get(0));
        }
        if (requestService2 == null) {
            requestService2 = HttpRetrofit.getRetrofitApiService(ModelService.class, PublicCache.getROOT_URL().get(1));
        }

        if (type.length == 0 || type[0] == 0) return requestService1;
        else return requestService2;
    }


}
