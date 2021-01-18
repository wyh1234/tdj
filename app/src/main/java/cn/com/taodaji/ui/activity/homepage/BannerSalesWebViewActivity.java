package cn.com.taodaji.ui.activity.homepage;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;


import com.base.activity.BaseActivity;
import com.base.utils.UIUtils;

import java.math.BigDecimal;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.ui.activity.wallet.RechargeMoneyActivity;
import tools.share.ShareUtils;


public class BannerSalesWebViewActivity  extends BaseActivity implements View.OnClickListener{
   private WebView webview;
    private String url;
    private ImageView img_back;

    @Override
    protected View getContentLayout() {
        return  getLayoutView(R.layout.activity_banner_sales_web_view);
    }

    @Override
    protected void initView() {

        webview=findViewById(R.id.webView1);
        url = getIntent().getStringExtra("url");

        img_back=findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        initWebView();
    }



    private  void initWebView(){
        WebSettings webSettings = webview.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDatabaseEnabled(true);//允许加载视频
        webSettings.setDomStorageEnabled(true); //处理js方法调用出错情况
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//设置脚本是否允许自动打开弹窗，默认false，不允许
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//支持内容重新布局


        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setBuiltInZoomControls(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setUseWideViewPort(true); // 关键点
        webSettings.setPluginState(WebSettings.PluginState.ON);
//        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容
//        webview.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }


        // sebs.setTextZoom(140);//字体大小默认是100
        //加载需要显示的网页
//        webview.loadUrl("http://admin.taodaji.com.cn/platform/toApplyJoinCity.html?applySource=1");
        webview.loadUrl(url);

        //Android调用JS方法有参数
//        webview.loadUrl("javascript:javacalljs()");
//        webview.loadUrl("javascript:sum(6,6)");

//        对于JS调用Android代码的方法有3种：

//        通过WebView的addJavascriptInterface（）进行对象映射
//        通过 WebViewClient 的shouldOverrideUrlLoading ()方法回调拦截 url
//        通过 WebChromeClient 的onJsAlert()、onJsConfirm()、onJsPrompt（）方法回调拦截JS对话框alert()、confirm()、prompt（） 消息
        //添加与js的交互接口,起的名称与js代码中的接口名称要一致
        webview.addJavascriptInterface(this, "rechargeWebViewController");
    }
    @JavascriptInterface
    public void shareWeb(String url, String title, String description) {
        new ShareUtils(this).shareWeb(url, title, description);
    }
    @JavascriptInterface
    public void rechargeWeb(BigDecimal money) {
        if (PublicCache.loginPurchase!=null) {
            RechargeMoneyActivity.startActivity(this,money);
        }else  if (PublicCache.loginSupplier!=null) {
            UIUtils.showToastSafe("采购商专用");
        }else {
            UIUtils.showToastSafe("请先登陆");
        }
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.img_back :
                finish();
            break;
            default:
            break;
        }
    }
    public static void startActivity(Context context, String url) {
        Intent intent = new Intent(context, BannerSalesWebViewActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

}
