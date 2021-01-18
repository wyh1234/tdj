package cn.com.taodaji.test;


import com.base.activity.BaseActivity;
import com.base.retrofit.HttpRequestManage;
import com.base.retrofit.RequestCallback;

import org.hamcrest.Matchers;

import java.util.List;

import cn.com.taodaji.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by yangkuo on 2018-08-03.
 */
public class TestUtil {

    public List<RequestCallback> list;
    public RequestCallback lastCallback;//最后一个的返回

    public boolean isFinishing = false;//baseActivity 是否已经关闭？

    public void waitForRequestBack(BaseActivity baseActivity) {
        boolean back;
        lastCallback = null;
        isFinishing = false;
        do {
            list = HttpRequestManage.getRequestList(baseActivity.hashCode());
            if (list.size() == 0) {
                //activity关闭时，请求被清除
                if (baseActivity.isFinishing()) {
                    isFinishing = true;
                    back = true;
                    lastCallback = new RequestCallback() {
                        @Override
                        protected void onSuc(Object body) {

                        }
                    };
                    lastCallback.testData.errCode = 0;
                    lastCallback.testData.state = 1;
                } else back = false;
            } else if (list.size() == 1) {
                lastCallback = list.get(0);
                if (lastCallback.testData.state == 0) back = false;
                else back = true;
            } else {
                back = true;
                for (RequestCallback requestCallback : list) {
                    if (requestCallback.testData.state == 0) {
                        back = false;
                        lastCallback = requestCallback;
                        break;
                    }
                }
                if (back && lastCallback == null) lastCallback = list.get(list.size() - 1);
            }
            //主线程停止0.1秒
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!back);
    }


    public void waitForDialog(String str) {
        boolean sleep = true;
        do {
            try {
                onView(withText(str)).check(matches(isDisplayed()));
            } catch (Exception e) {
                sleep = false;
            }
        } while (sleep);
    }


    public void waitForProgressDialog() {
        boolean sleep = true;
        do {
            try {
                onView(Matchers.allOf(withId(R.id.image_popup_window), withId(R.id.progressBar2))).check(matches(isDisplayed()));
            } catch (Exception e) {
                sleep = false;
            }
        } while (sleep);
    }

}
