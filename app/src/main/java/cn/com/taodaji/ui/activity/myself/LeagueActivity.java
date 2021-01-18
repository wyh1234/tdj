package cn.com.taodaji.ui.activity.myself;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import tools.activity.SimpleToolbarActivity;

/*
 * Created by yangkuo on 2018-05-08.
 *
 *一、，android访问js的方法
 *    webview.evaluateJavascript("javascript:CloseWebPage()", new ValueCallback<String>() {
 *      @Override
 *    public void onReceiveValue(String value) {
 *      //此处为 js 返回的结果
 *     Log.e("", value);
 *     }
 *   });
 *
 * 二、JS通过WebView调用 Android 代码
 *
 *    //通过WebView的addJavascriptInterface（）进行对象映射
 *   //添加与js的交互接口,起的名称与js代码中的接口名称要一致
 *    webview.addJavascriptInterface(this, "webViewController");
 *
 *        // 定义JS需要调用的方法
 *  // 被JS调用的方法必须加入@JavascriptInterface注解
 *   @JavascriptInterface
 *   public void goBackViewShowMessage(String msg) {
 *       UIUtils.showToastSafesShort(msg);
 *       finish();
 *  }
 *
 */

public class LeagueActivity extends SimpleToolbarActivity {

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webview.canGoBack()){
                    webview.goBack();
                }else {
                    finish();
                }
            }
        });
    }

    private WebView webview;
    private ProgressBar progressBar1;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initMainView() {
        View view = getLayoutView(R.layout.t_webview);
        body_other.addView(view);

        if (PublicCache.initializtionData == null) return;


        progressBar1 = view.findViewById(R.id.progressBar1);

        webview = (WebView) view.findViewById(R.id.webView1);
        webview.requestFocusFromTouch();


        WebSettings webSettings = webview.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);


        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true); //处理js方法调用出错情况
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//设置脚本是否允许自动打开弹窗，默认false，不允许
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//支持内容重新布局
        // sebs.setTextZoom(140);//字体大小默认是100
        //加载需要显示的网页   这个要写死
        //1采购商地址  2供应商地址
        int type=getIntent().getIntExtra("urlType",0);
        switch(type){
            case 1 :
                setTitle("帮助中心");
                webview.loadUrl(PublicCache.initializtionData.getCustomer_help_html());
            break;
            case 2 :
                setTitle("帮助中心");
                webview.loadUrl(PublicCache.initializtionData.getSupplier_help_html());
                break;
            case 3 :
                setTitle("申请团长");
                webview.loadUrl("https://m.51taodj.com/taodajiuat/community/joinMasterPage?flag=1");
                break;
            default:
                setTitle("城市加盟商申请");
                webview.loadUrl(PublicCache.initializtionData.getCity_join_url());
            break;
        }

//        webview.loadUrl(PublicCache.HTTP_URL + "/platform/toApplyJoinCity.html?applySource=1");

//        对于JS调用Android代码的方法有3种：

//        通过WebView的addJavascriptInterface（）进行对象映射
//        通过 WebViewClient 的shouldOverrideUrlLoading ()方法回调拦截 url
//        通过 WebChromeClient 的onJsAlert()、onJsConfirm()、onJsPrompt（）方法回调拦截JS对话框alert()、confirm()、prompt（） 消息

        //添加与js的交互接口,起的名称与js代码中的接口名称要一致
        webview.addJavascriptInterface(this, "webViewController");


        //设置Web视图
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress > 0) {
                    progressBar1.setVisibility(View.VISIBLE);
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
        });

        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    public void goBackViewShowMessage(String msg) {
        UIUtils.showToastSafesShort(msg);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webview != null) {
            webview.clearFormData();
            webview.destroy();
        }

    }

    public static void startActivity(Context context,int urlType) {
        Intent intent = new Intent(context, LeagueActivity.class);
        intent.putExtra("urlType",urlType);
        context.startActivity(intent);
    }

    public boolean onKeyDown(int keyCode , KeyEvent keyEvent){
        if(keyCode==keyEvent.KEYCODE_BACK){//监听返回键，如果可以后退就后退
            if(webview.canGoBack()){
                webview.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, keyEvent);

    }
}
