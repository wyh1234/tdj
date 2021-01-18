package cn.com.taodaji.model.presenter;

import android.support.test.runner.AndroidJUnit4;

import com.base.retrofit.RequestCallback;
import org.junit.Test;
import org.junit.runner.RunWith;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.SupplyMoneyListBean;
import cn.com.taodaji.ui.activity.login.LoginUtils;

/**
 * Created by yangkuo on 2018-09-17.
 */
@RunWith(AndroidJUnit4.class)
public class RequestPresenter2Test {

    @Test
    public void getSupplyMoneyListNew() {

        LoginUtils.loginSupplier();

        RequestPresenter.getInstance().getSupplyMoneyListNew(PublicCache.loginSupplier.getStore(), 1, 5, new RequestCallback<SupplyMoneyListBean>() {

            @Override
            protected void onSuc(SupplyMoneyListBean body) {
                if (body != null) {

                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
                super.onFailed(errCode, msg);
            }
        });

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSupplyMoneyListFilter() {
    }
}