package com.base.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.base.utils.JavaMethod;
import com.base.utils.UIUtils;

/**
 * Description:管理所有的栈中的Activity
 */
public class ActivityManage {

    /**
     * 存放activity的列表
     */
    public static LinkedHashMap<Class<?>, BaseActivity> activities = new LinkedHashMap<>();


    public static void setData(LinkedHashMap<Class<?>, BaseActivity> activities_new) {
        if (activities != null) activities = activities_new;
    }

    public static LinkedHashMap<Class<?>, BaseActivity> getData() {
        return activities;
    }

    /**
     * 添加Activity
     *
     * @param activity
     */
    public static void addActivity(BaseActivity activity, Class<?> clz) {
        activities.put(clz, activity);
    }

    public static boolean isClossAll() {
        return activities.size() == 0;
    }


    /**
     * 判断一个Activity 是否存在
     *
     * @param clz
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static <T extends BaseActivity> boolean isActivityExist(Class<T> clz) {
        boolean res;
        BaseActivity activity = getActivity(clz);
        if (activity == null) {
            res = false;
        } else {
            if (activity.isFinishing() || activity.isDestroyed()) {
                res = false;
            } else {
                res = true;
            }
        }

        return res;
    }

    /**
     * 获得栈顶activity实例
     *
     * @return
     */

    public static <T extends BaseActivity> T getTopActivity() {
        if (activities == null) return null;
        Map.Entry<Class<?>, BaseActivity> entry = JavaMethod.getLinkedHashMapTail(activities);
        return (T) entry.getValue();
    }

    public static <T extends BaseActivity> void setTopActivity(Class<T> clazz) {
        if (activities != null && activities.size() > 0) {
            Set<Entry<Class<?>, BaseActivity>> sets = activities.entrySet();
            boolean isclose = false;
            for (Entry<Class<?>, BaseActivity> s : sets) {
                if (isclose) {
                    if (!s.getValue().isFinishing()) {
                        s.getValue().finish();
                    }
                }
                if (s.getKey().getName().equals(clazz.getName())) {
                    isclose = true;
                }
            }
            if (!isclose) {
                Map.Entry<Class<?>, BaseActivity> entry = JavaMethod.getLinkedHashMapHead(activities);
                if (entry.getValue() != null)
                    entry.getValue().startActivity(new Intent(UIUtils.getContext(), clazz));
            }
        }
    }

    /**
     * 获得指定activity实例
     *
     * @param clazz Activity 的类对象
     * @return
     */
    public static <T extends BaseActivity> T getActivity(Class<T> clazz) {
        return (T) activities.get(clazz);
    }

    /**
     * 移除activity,代替finish
     *
     * @param activity
     */
    public static void removeActivity(BaseActivity activity) {
        if (activities.containsValue(activity)) {
            activities.remove(activity.getClass());
        }
    }

    /**
     * 移除所有的Activity
     */
    public static void removeAllActivity() {
        if (activities != null && activities.size() > 0) {
            Set<Entry<Class<?>, BaseActivity>> sets = activities.entrySet();
            for (Entry<Class<?>, BaseActivity> s : sets) {
                if (!s.getValue().isFinishing()) {
                    s.getValue().finish();
                }
            }
        }

        if (activities != null) {
            activities.clear();
        }
    }

    /**
     * 移除自己以外所有的Activity
     */
    public static void removeAllActivity(BaseActivity activity) {
        if (activities != null && activities.size() > 0) {
            Set<Entry<Class<?>, BaseActivity>> sets = activities.entrySet();
            for (Entry<Class<?>, BaseActivity> s : sets) {
                if (s.getValue() != activity) {
                    if (!s.getValue().isFinishing()) {
                        s.getValue().finish();
                    }
                }

            }
        }

        if (activities != null) {
            activities.clear();
            activities.put(activity.getClass(), activity);
        }
    }
}
