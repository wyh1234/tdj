package cn.com.taodaji.ui.activity.homepage;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.greenrobot.eventbus.EventBus;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.ui.activity.market.GoodsDetailActivity;
import cn.com.taodaji.ui.activity.market.ShopDetailInformationActivity;
import cn.com.taodaji.ui.activity.wallet.RechargeMoneyActivity;
import tools.activity.SimpleToolbarActivity;
import tools.share.ShareUtils;

import com.apkfuns.logutils.LogUtils;
import com.base.utils.DialogUtils;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.math.BigDecimal;

public class SpecialActivitiesActivity extends SimpleToolbarActivity implements UMShareListener {
    private DialogUtils dialogUtils = null;
    private int tempUser;
    private ProgressBar progressBar1;
    private ImageView iv_back;
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor(R.color.translucent);
        setToolBarColor(R.color.white);
        if (getIntent().getStringExtra("name")!=null){
            simple_title.setText(getIntent().getStringExtra("name"));
        }else {
            simple_title.setText("活动专题");
        }

        simple_title.setTextColor(UIUtils.getColor(R.color.black_65));

        toolbar.setNavigationIcon(R.mipmap.left_arrow_gary);
        if (getIntent().getStringExtra("duiba")!=null){
            simple_title.setText("积分商城");

        }

    }

    @Override
    protected void onDestroy() {
        webview.clearFormData();
        webview.destroy();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private String url = null;
    private WebView webview;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initMainView() {
        url = getIntent().getStringExtra("url");

        /**
         * 采购商的身份
         * role: 0 表示主账号、1表示子账号厨师  2表示子账号财务 3表示子账号管理员
         */
        if (PublicCache.loginPurchase != null) {
            if (PublicCache.loginPurchase.getFlag()==1) {
                tempUser=PublicCache.loginPurchase.getEntityId();
            }else{
                tempUser=PublicCache.loginPurchase.getSubUserId();
            }

        }

        View view = getLayoutView(R.layout.t_webview);
        body_other.addView(view);

        webview = view.findViewById(R.id.webView1);
        progressBar1 =view.findViewById(R.id.progressBar1);
        progressBar1.setVisibility(View.VISIBLE);
        iv_back =view.findViewById(R.id.iv_back);
        if (getIntent().getStringExtra("duiba")!=null){
            iv_back.setVisibility(View.VISIBLE);
        }
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (webview.canGoBack())
                    webview.goBack();
                else
                    finish();
            }
        });
        WebSettings webSettings = webview.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);


        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true); //处理js方法调用出错情况
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//设置脚本是否允许自动打开弹窗，默认false，不允许
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//支持内容重新布局
        if (url != null) {
            webview.loadUrl(url);
        }
        //添加与js的交互接口,起的名称与js代码中的接口名称要一致
        webview.addJavascriptInterface(this, "homePage");
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                LogUtils.e(url);
                LogUtils.e(url.contains("platformapi/startApp"));
                if (url.contains("platformapi/startApp")||url.contains("dispatch_message/")) {
                    try {
                        Uri uri = Uri.parse(url);
                        Intent intent;
                        intent = Intent.parseUri(url,
                                Intent.URI_INTENT_SCHEME);
                        intent.addCategory("android.intent.category.BROWSABLE");
                        intent.setComponent(null);
                        // intent.setSelector(null);
                        startActivity(intent);

                    } catch (Exception e) {

                    }
                } else {
                    view.loadUrl(url);
                }
                return true;
            }
        });

        //设置Web视图
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = ViewUtils.showDialog(getBaseActivity(), "提示", message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress > progressBar1.getProgress()) {
//                    progressBar1.setVisibility(View.VISIBLE);
                    progressBar1.setProgress(newProgress);
                }
                if (newProgress == 100) {
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar1.setVisibility(View.GONE);
                        }
                    }, 500);

                }
            }

        });
    }


    @JavascriptInterface
    public void jump2Page(String... mess) {
        if (mess.length > 0) {
            if (mess.length == 2) {
                if (mess[0].equals("商品详情")) {
                    GoodsDetailActivity.startActivity(this, Integer.valueOf(mess[1]));
                } else if (mess[0].equals("店铺详情")) {
                    ShopDetailInformationActivity.startActivity(this, Integer.valueOf(mess[1]));
                }
            }


        } else finish();
    }

    @JavascriptInterface
    public void shareWeb(String shareUrl, String title, String description) {
        if (!TextUtils.isEmpty(url) && url.contains("toRechargeActivity")) {
            new ShareUtils(this).setUmShareListener(this).shareWeb(R.mipmap.share_recharge,shareUrl, title, description);
        }else if (!TextUtils.isEmpty(url) && url.contains("doaward")) {
            new ShareUtils(this).setUmShareListener(this).shareWeb(R.mipmap.share_doaward,shareUrl, title, description);
        }else{
            new ShareUtils(this).setUmShareListener(this).shareWeb(shareUrl, title, description);
        }

    }

    @JavascriptInterface
    public void rechargeWeb(BigDecimal money) {
        if (PublicCache.loginPurchase != null) {
            RechargeMoneyActivity.startActivity(this, money);
        } else if (PublicCache.loginSupplier != null) {
            if (dialogUtils == null) {
                dialogUtils=DialogUtils.getInstance(getBaseActivity()).getSingleDialog(R.layout.dialog_activities_message,UIUtils.getString(R.string.dialog_recharge_tips)).setPositiveButton("",null);
            }
            dialogUtils.show();
        } else {
            UIUtils.showToastSafe("请先登陆");
        }
    }
    @JavascriptInterface
    public void shareAction(String imgUrl,String link,String title,String desc){
        LogUtils.e(".........");
        new ShareUtils(this).shareWeb(imgUrl, link, title, desc);


    }


    @Override
    public void onStart(SHARE_MEDIA share_media) {

    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        if (webview == null) {
            return;
        }
        //Android调用JS方法有参数
//        webview.loadUrl("javascript:javacalljs()");
//        webview.loadUrl("javascript:sum(6,6)");
        webview.loadUrl("javascript:shareCallback("+tempUser+")");
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {


        if (webview == null) {
            return;
        }
        //Android调用JS方法有参数
//        webview.loadUrl("javascript:javacalljs()");


        webview.loadUrl("javascript:shareCallback("+tempUser+")");
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

}
