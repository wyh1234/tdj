package tools.activity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.base.activity.StatusBarBaseActivity;
import com.base.entity.ResultInfo;
import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;
import com.base.utils.JavaMethod;
import com.base.utils.UIUtils;
import com.umeng.analytics.MobclickAgent;

import cn.com.taodaji.common.Constants;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.DictFindAll;
import cn.com.taodaji.model.entity.FindByIsActive;
import cn.com.taodaji.model.sqlBean.LoginPurchaseBean;
import cn.com.taodaji.model.sqlBean.LoginSupplierBean;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.ui.activity.integral.requsest.IntegralShopRequestPresenter;
import retrofit2.Call;

/**
 * Created by yangkuo on 2018-08-15.
 */
public abstract class DataBaseActivity extends StatusBarBaseActivity implements Constants {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //内存回收时，或进程关闭时,恢复静态数据
        if (savedInstanceState != null) {
            recoverStaticData(savedInstanceState);
        }
        super.onCreate(savedInstanceState);
    }

//fragment  有时侯刷新不对
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        recoverStaticData(savedInstanceState);
//    }

    //保存静态数据
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(this.getClass().getSimpleName(), "保存数据 " + outState.toString());

        outState.putInt("site", PublicCache.site);
        outState.putString("site_name", PublicCache.site_name);
        outState.putInt("site_login", PublicCache.site_login);
        //outState.putInt("isP_DEFAULT", PublicCache.isP_DEFAULT);

        outState.putString("site_name_login", PublicCache.site_name_login);
        outState.putString("location_default", PublicCache.location_default);
        outState.putDouble("longtitude", PublicCache.longtitude);
        outState.putDouble("latitude", PublicCache.latitude);
        outState.putInt("userNameId", PublicCache.userNameId);

        if (PublicCache.findByIsActive != null)
            outState.putString("findByIsActive", JavaMethod.transBean2Json(PublicCache.findByIsActive));

        outState.putInt("notifycationCount", PublicCache.notifycationCount);
        outState.putString("deviceToken_umeng", PublicCache.deviceToken_umeng);

        if (PublicCache.loginPurchase != null)
            outState.putString("loginPurchase", JavaMethod.transBean2Json(PublicCache.loginPurchase));

        if (PublicCache.loginSupplier != null)
            outState.putString("loginSupplier", JavaMethod.transBean2Json(PublicCache.loginSupplier));

        outState.putInt("isInvoice", PublicCache.isInvoice);
        outState.putString("login_mode", PublicCache.login_mode);
        outState.putString("WX_APP_ID", PublicCache.WX_APP_ID);

        if (PublicCache.initializtionData != null)
            outState.putString("initializtionData", JavaMethod.transBean2Json(PublicCache.initializtionData));

    }

    //恢复保存的静态数据
    public void recoverStaticData(@NonNull Bundle savedInstanceState) {
        Log.d(this.getClass().getSimpleName(), "恢复数据 " + savedInstanceState.toString());


        PublicCache.site = savedInstanceState.getInt("site", 2);
        PublicCache.site_name = savedInstanceState.getString("site_name");
        PublicCache.site_login = savedInstanceState.getInt("site_login");
        //PublicCache.isP_DEFAULT = savedInstanceState.getInt("isP_DEFAULT", 0);

        PublicCache.site_name_login = savedInstanceState.getString("site_name_login");
        PublicCache.location_default = savedInstanceState.getString("location_default");
        PublicCache.longtitude = savedInstanceState.getDouble("longtitude", 0d);
        PublicCache.latitude = savedInstanceState.getDouble("latitude", 0d);
        PublicCache.userNameId = savedInstanceState.getInt("userNameId");


        PublicCache.findByIsActive = JavaMethod.getJsonBean(savedInstanceState.getString("findByIsActive", null), FindByIsActive.class);
        PublicCache.notifycationCount = savedInstanceState.getInt("notifycationCount");
        PublicCache.deviceToken_umeng = savedInstanceState.getString("deviceToken_umeng");
        PublicCache.loginPurchase = JavaMethod.getJsonBean(savedInstanceState.getString("loginPurchase", null), LoginPurchaseBean.class);
        PublicCache.loginSupplier = JavaMethod.getJsonBean(savedInstanceState.getString("loginSupplier", null), LoginSupplierBean.class);
        PublicCache.isInvoice = savedInstanceState.getInt("isInvoice", 0);
        PublicCache.login_mode = savedInstanceState.getString("login_mode");
        PublicCache.WX_APP_ID = savedInstanceState.getString("WX_APP_ID");
        PublicCache.initializtionData = JavaMethod.getJsonBean(savedInstanceState.getString("initializtionData", null), DictFindAll.DataBean.class);


    }


    //获取初始化的数据
    public void initializtionData() {
        if (PublicCache.initializtionData != null) return;
        getRequestPresenter().dictFindAll(new RequestCallback<DictFindAll>() {
            @Override
            public void onSuc(DictFindAll body) {
                PublicCache.initializtionData = body.getData();
            }

            @Override
            public void onFailed(int dictFindAll, String msg) {
                UIUtils.showToastSafesShort(msg);
            }
        });
    }

    //网络请求
    public RequestPresenter getRequestPresenter() {
        return RequestPresenter.getInstance();
    }

    public IntegralShopRequestPresenter getIntegralRequestPresenter() {
        return IntegralShopRequestPresenter.getInstance();
    }
    public <T> void addRequest(Call<T> call, RequestCallback<T> callback) {
        callback.setCall(call).enqueue(callback);
    }

    public <T> void addRequest(Call<ResultInfo<T>> call, ResultInfoCallback<T> callback) {
        callback.setCall(call).enqueue(callback);
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
