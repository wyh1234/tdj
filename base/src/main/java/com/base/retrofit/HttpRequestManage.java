package com.base.retrofit;

import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangkuo on 2018-07-26.
 */
public class HttpRequestManage {


    private static SparseArrayCompat<List<RequestCallback>> sparseArrayCompat = new SparseArrayCompat<>();

    /**
     * @param callback
     *
     * @param hashCode
     */
    public static void put(@NonNull RequestCallback callback, int hashCode) {
        List<RequestCallback> list = sparseArrayCompat.get(hashCode, null);
        if (list == null) {
            list = new ArrayList<>();
            sparseArrayCompat.put(hashCode, list);
        }
        list.add(callback);
    }

    //获取activity 的所有请求，以便销毁时取消
    public static List<RequestCallback> getRequestList(int hashCode) {
        return sparseArrayCompat.get(hashCode, new ArrayList<>());
    }


    public static void clear(int hashCode) {
        List<RequestCallback> list = sparseArrayCompat.get(hashCode, null);
        if (list != null) list.clear();
    }


}
