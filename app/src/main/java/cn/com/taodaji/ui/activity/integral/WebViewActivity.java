package cn.com.taodaji.ui.activity.integral;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.activity.ActivityManage;
import com.base.activity.BaseActivity;
import com.base.application.App;
import com.base.retrofit.cookie.PersistentCookieStore;
import com.base.utils.UIUtils;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.taodaji.R;
import cn.com.taodaji.model.LoginMethod;
import cn.com.taodaji.model.entity.IntegralOrder;
import cn.com.taodaji.ui.activity.advertisement.CreateTfAdvManageActivity;
import cn.com.taodaji.ui.activity.integral.tools.CurrentItem;
import cn.com.taodaji.ui.activity.integral.tools.SimpleWebView;
import cn.com.taodaji.ui.activity.login.LoginActivity;
import cn.com.taodaji.ui.activity.login.LoginPurchaserActivity;
import okhttp3.Cookie;
import tools.activity.DataBaseActivity;
import tools.share.ShareUtils;
import tools.statusbar.Eyes;

public class WebViewActivity extends DataBaseActivity  {
    @BindView(R.id.tv_refresh)
    TextView tv_refresh;
    @BindView(R.id.wv_program)
    SimpleWebView wv_program;
    @BindView(R.id.intergral_toolbar)
    Toolbar intergral_toolbar;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @OnClick({R.id.btn_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                if (wv_program.canGoBack())
                    wv_program.goBack();
                else
                    finish();
                break;
        }
        }

    /**
     * 给WebView同步Cookie
     *
     * @param context 上下文
     * @param url     可以使用[domain][host]
     */
    private void syncCookie(Context context, String url) {
        CookieSyncManager.createInstance(context);

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();// 移除旧的[可以省略]
        List<Cookie> cookies = new PersistentCookieStore(context).getCookies();// 获取Cookie[可以是其他的方式获取]
        for (int i = 0; i < cookies.size(); i++) {
            Cookie cookie = cookies.get(i);
            String value = cookie.name() + "=" + cookie.value();
            cookieManager.setCookie(url, value);
        }
        CookieSyncManager.getInstance().sync();// To get instant sync instead of waiting for the timer to trigger, the host can call this.
    }

    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.webview_layout);
    }
    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        if (getIntent().getStringExtra("title")!=null){
            intergral_toolbar.setVisibility(View.VISIBLE);
            btn_back.setVisibility(View.VISIBLE);
            tv_title.setText(getIntent().getStringExtra("title"));
            tv_title.setTextColor(getResources().getColor(R.color.gray_66));
            btn_back.setImageResource(R.mipmap.left_arrow_gary);
            Eyes.setStatusBarColor(this, UIUtils.getColor(this, R.color.white));
            Eyes.setLightStatusBar(this,true);//设置状态栏字体颜色
            intergral_toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        }else {
            intergral_toolbar.setVisibility(View.GONE);
            Eyes.translucentStatusBar(this, true);
        }
        wv_program.addJavascriptInterface(new AndroidtoJs(), "android");//AndroidtoJS类对象映射到js的test对象

        initDetailsH5();
    }

    /**
     * 初始化webView
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void initDetailsH5() {

        if (getIntent().getStringExtra("url")!=null){
            LogUtils.i(getIntent().getStringExtra("url"));
            syncCookie(App.getApplication(),getIntent().getStringExtra("url"));
            wv_program.loadUrl(getIntent().getStringExtra("url"));
        }
        wv_program.setWebViewClient(new SimpleWebView.SimpleWebViewClient() {
/*            @Override
            public void onPageFinished(com.tencent.smtt.sdk.WebView webView, String url) {
                super.onPageFinished(webView, url);
                myProgressBar.setVisibility(View.GONE);
//                toolbarTitle.setText(webView.getTitle());//获取WebView 的标题，设置到toolbar中去
            }*/

            @Override
            public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView webView, String url) {
                if (url.contains("Activity/")) {

                } else if (url.contains("Share")) {

                } else {
                    webView.loadUrl(url);
                }
                return true;
            }

        });
/*        wv_program.setWebChromeClient(new SimpleWebView.SimpleWebChromeClient() {
            @Override
            public void onProgressChanged(com.tencent.smtt.sdk.WebView webView, int newProgress) {
                if (newProgress == 100) {
                    myProgressBar.setVisibility(View.GONE);
                } else {
                    if (View.GONE == myProgressBar.getVisibility()) {
                        myProgressBar.setVisibility(View.VISIBLE);
                    }
                    myProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(webView, newProgress);
            }

        });*/
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (wv_program.canGoBack())
                wv_program.goBack();
            else
                finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public class AndroidtoJs extends Object {
        @JavascriptInterface
        public void goback()
        {
            finish();

        }
        @JavascriptInterface
        public void goBack()
        {
            finish();

        }
        @JavascriptInterface
        public void goShopCart()
        {
            LogUtils.i(1);
            ActivityManage.setTopActivity(IntegralShopMainActivity.class);
            EventBus.getDefault().post(new CurrentItem());
            finish();

        }

        @JavascriptInterface
        public void toufang()
        {
            Intent intent=new Intent(WebViewActivity.this, CreateTfAdvManageActivity.class);
            intent.putExtra("DataBean",getIntent().getSerializableExtra("DataBean"));
            intent.putExtra("stage_type",getIntent().getStringExtra("stage_type"));
            intent.putExtra("entity_id",getIntent().getIntExtra("entity_id",0));
            intent.putExtra("name",getIntent().getStringExtra("name"));
            intent.putExtra("advertisementPackageId",getIntent().getIntExtra("advertisementPackageId",0));
            intent.putExtra("advPackagePice",getIntent().getStringExtra("advPackagePice"));
            intent.putExtra("limit_days",getIntent().getIntExtra("limit_days",0));
            startActivity(intent);

        }
        @JavascriptInterface
        public void showSuccess(String msg)
        {
            LogUtils.i(2);
            UIUtils.showToastSafe(msg);

//            ActivityManage.setTopActivity(IntegralShopMainActivity.class);
//            EventBus.getDefault().post(new CurrentItem());
//            finish();


        }
        @JavascriptInterface
        public void addErr(String msg)
        {
            if ("901".equals(msg)){
                if (!ActivityManage.isActivityExist(LoginActivity.class) && !ActivityManage.isActivityExist(LoginPurchaserActivity.class)) {
                    BaseActivity baseActivity = ActivityManage.getTopActivity();
                    if (baseActivity == null) return;
                    LoginMethod.getInstance(baseActivity).toLoginActivity();
                }
            }else {
                LogUtils.i(3);
                UIUtils.showToastSafe(msg);
            }
        }
        @JavascriptInterface
        public void paymentOrder(String orderId,String totalFee)
        {
            IntegralOrder.IntegralOrderData integralOrderData=new IntegralOrder.IntegralOrderData();
            integralOrderData.setOrderId(orderId);
            integralOrderData.setTotalFee(totalFee);
            Intent intent1=new Intent(WebViewActivity.this,IntegralShopPayActivity.class);
            intent1.putExtra("IntegralOrder",integralOrderData);
            startActivity(intent1);
            finish();

        }

        @JavascriptInterface
        public void goBackViewShowMessage(String msg)
        {

            finish();

        }
        @JavascriptInterface
        public void shareAction(String imgUrl,String link,String title,String desc){
            LogUtils.e(".........");
            new ShareUtils(WebViewActivity.this).shareWeb(imgUrl, link, title, desc);


        }


    }



}
