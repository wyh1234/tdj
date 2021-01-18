package com.base.utils;


import java.io.Serializable;
import java.util.Map;

/**
 * 序列化map供Bundle传递map使用
 */
public class SerializableMap implements Serializable {

    private Map<String, Object> map;

    public SerializableMap(Map<String, Object> map) {
        this.map = map;
    }


    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
       // return this;
    }
}
