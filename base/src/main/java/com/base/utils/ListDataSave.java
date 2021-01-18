package com.base.utils;

import android.content.Context;
import android.content.SharedPreferences;

//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;

import com.base.retrofit.jackSon.JacksonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-10-19.
 */

public class ListDataSave {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public ListDataSave(Context mContext, String preferenceName) {
        preferences = mContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /**
     * 保存List
     *
     * @param tag
     * @param datalist
     */
    public <T> void setDataList(String tag, List<T> datalist) {
        if (null == datalist || datalist.size() <= 0) return;

//        Gson gson = new Gson();
        //转换成json数据，再保存
//        String strJson = gson.toJson(datalist);
        String strJson = null;
        try {
            strJson = JacksonUtils.getObjectMapper().writeValueAsString(datalist);
            editor.clear();
            editor.putString(tag, strJson);
            editor.commit();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取List
     *
     * @param tag
     * @return
     */
    public <T> List<T> getDataList(String tag) {
        List<T> datalist = new ArrayList<>();
        String strJson = preferences.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
//        Gson gson = new Gson();
//        datalist = gson.fromJson(strJson, new TypeToken<List<T>>() {}.getType());

        try {
            datalist = JacksonUtils.getObjectMapper().readValue(strJson, JavaMethod.getCollectionType(List.class, JavaMethod.getGenericClass(datalist, 0)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return datalist;
    }

    public void clearData() {
        editor.clear().commit();
    }
}
