package com.base.retrofit;

import android.util.ArrayMap;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CustomParamsInterceptor implements Interceptor {
    private Map<String, String> headers;
    private Map<String, String> params;

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public CustomParamsInterceptor(Map<String, String> headers, Map<String, String> params) {
        this.headers = headers;
        this.params = params;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        ArrayMap paramsMap = new ArrayMap();
        Request oldRequest = chain.request();

        //添加请求头
        Request.Builder heard = oldRequest.newBuilder();
        if (headers != null && headers.size() > 0) {
            Set<String> keys = headers.keySet();
            for (String headerKey : keys) {
                heard.addHeader(headerKey, headers.get(headerKey)).build();
                paramsMap.put(headerKey, headers.get(headerKey));
            }
        }

        Request newRequest = null;
        if (oldRequest.method().equals("POST") && oldRequest.body() instanceof FormBody) {

            FormBody body = (FormBody) oldRequest.body();
            FormBody.Builder body_build = new FormBody.Builder();

            Map<String, String> params_new = new HashMap<>();

            for (int i = 0; i < body.size(); i++) {
                params_new.put(body.name(i), body.value(i));
            }

            if (params != null && params.size() > 0) {
                for (String s : params.keySet()) {
                    params_new.put(s, params.get(s));

                }
            }
            for (Map.Entry<String, String> stringStringEntry : params_new.entrySet()) {
                body_build.addEncoded(stringStringEntry.getKey(), stringStringEntry.getValue());
                paramsMap.put(stringStringEntry.getKey(),stringStringEntry.getValue());

            }
            Gson gson = new Gson();


            newRequest = heard.post(RequestBody.create(JSON, gson.toJson(paramsMap))).build();
//            newRequest = heard.method(oldRequest.method(), body_build.build()).build();

        } else {
            HttpUrl.Builder builder = oldRequest.url().newBuilder();
            if (params != null && params.size() > 0) {
                for (String s : params.keySet()) {
                    builder.setEncodedQueryParameter(s, params.get(s));
                }
            }
            newRequest = heard.method(oldRequest.method(), oldRequest.body()).url(builder.build()).build();
        }


        return chain.proceed(newRequest);
    }
}
