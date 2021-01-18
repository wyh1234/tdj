package com.base.utils;


import android.support.v4.util.SparseArrayCompat;

public class CustomerData<K, V, D> {

    //code为第二个key，具有唯一性
    private SparseArrayCompat<K> code = new SparseArrayCompat<>();
    private SparseArrayCompat<V> values = new SparseArrayCompat<>();
    private SparseArrayCompat<D> description = new SparseArrayCompat<>();

    public void put(int id, K key, V value, D descr) {
        code.put(id, key);
        values.put(id, value);
        description.put(id, descr);
    }

    public K getKeyOfId(int id) {
        return code.get(id);
    }

    public K getKeyAtIndex(int index) {
        return code.valueAt(index);
    }

    public K getKeyOfValue(V value) {
        int index = indexOfValue(value);
        if (index == -1) return null;
        return code.valueAt(index);
    }

    public V getValueOfId(int id) {
        return values.get(id);
    }

    public V getValueAtIndex(int index) {
        return values.valueAt(index);
    }

    public V getValueOfKey(K key) {
        int index = indexOfKey(key);
        if (index == -1) return null;
        return values.valueAt(index);
    }

    public D getDesc(int id) {
        return description.get(id);
    }

    public D getDescAtIndex(int index) {
        return description.valueAt(index);
    }

    public D getDesc(K key) {
        int index = indexOfKey(key);
        if (index == -1) return null;
        return description.valueAt(index);
    }

    public int idAt(int index) {
        return code.keyAt(index);
    }

    public int idOfKey(K key) {
        int index = indexOfKey(key);
        if (index != -1) return code.keyAt(index);
        return -1;
    }

    public int idOfValue(V value) {
        int index = indexOfValue(value);
        if (index != -1) return code.keyAt(index);
        return -1;
    }

    public int indexOfValue(V value) {
        if (value instanceof String) {
            for (int i = 0; i < values.size(); i++) {
                if (value.equals(values.valueAt(i))) return i;
            }
        } else return values.indexOfValue(value);

        return -1;
    }

    public int indexOfKey(K key) {
        if (key instanceof String) {
            for (int i = 0; i < code.size(); i++) {
                if (key.equals(code.valueAt(i))) return i;
            }
        } else return code.indexOfValue(key);

        return -1;
    }


    public K keyOfValue(V value) {
        int index = indexOfValue(value);
        if (index == -1) return null;
        return code.valueAt(index);
    }

    public int size() {
        return code.size();
    }

    public void clear() {
        code.clear();
        values.clear();
        description.clear();
    }

    public void remove(int id) {
        code.remove(id);
        values.remove(id);
        description.remove(id);
    }

    public void removeAt(int index) {
        code.removeAt(index);
        values.removeAt(index);
        description.removeAt(index);
    }
}
