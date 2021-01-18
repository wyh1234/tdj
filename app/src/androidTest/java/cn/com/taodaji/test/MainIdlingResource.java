package cn.com.taodaji.test;

import android.support.annotation.NonNull;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.util.Log;

import com.base.activity.BaseActivity;
import com.base.retrofit.HttpRequestManage;
import com.base.retrofit.RequestCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by yangkuo on 2018-07-26.
 */
public class MainIdlingResource implements IdlingResource {

    private BaseActivity mActivity;
    private ResourceCallback resourceCallback;

    public List<RequestCallback> list;

    public RequestCallback lastCallback;//最后一个的返回

    public MainIdlingResource(BaseActivity baseActivity) {
        mActivity = baseActivity;
        list = HttpRequestManage.getRequestList(baseActivity.hashCode());
        if (list.size() > 0) lastCallback = list.get(list.size() - 1);
        start();
    }


    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {

        if (resourceCallback != null) {
            list = HttpRequestManage.getRequestList(mActivity.hashCode());
            if (list.size() == 0) return false;
            else if (list.size() == 1) {
                lastCallback = list.get(0);
                if (lastCallback.testData.state == 0) return false;
                else return setEnd();
            } else {
                boolean isAllback = true;
                for (RequestCallback requestCallback : list) {
                    if (requestCallback.testData.state == 0) {
                        isAllback = false;
                        lastCallback = requestCallback;
                        break;
                    }
                }

                if (isAllback) return setEnd();
                else return false;
            }

        } else return false;

/*        boolean isIdle;
        if (resourceCallback != null) {
            //如果list.size() == 0 还没有开始检查
            if (list.size() == 0) return true;
                //只有一个请求时
            else if (list.size() == 1) {
                lastCallback = list.get(0);
                if (lastCallback.testData.state == 0) isIdle = false;
                else isIdle = true;
            }
            //有多个请求时
            else {
                boolean isAllback = true;
                for (RequestCallback requestCallback : list) {
                    if (requestCallback.testData.state == 0) {
                        isAllback = false;
                        lastCallback = requestCallback;
                        break;
                    }
                }
                isIdle = isAllback;
                //第一次检查多个请求时，如果全部
                if (isAllback && lastCallback == null) {
                    lastCallback = list.get(list.size() - 1);
                }
            }

            if (isIdle) resourceCallback.onTransitionToIdle();

            return isIdle;
        } else return true;*/

    }

    private boolean setEnd() {
        resourceCallback.onTransitionToIdle();
        if (lastCallback != null && lastCallback.testData.state != 0) {
            IdlingRegistry.getInstance().unregister(this);
        }
        return true;
    }

    public void start() {
        IdlingRegistry.getInstance().register(this);
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        this.resourceCallback = resourceCallback;
    }


}
