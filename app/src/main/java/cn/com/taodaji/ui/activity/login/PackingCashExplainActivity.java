package cn.com.taodaji.ui.activity.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.base.utils.ViewUtils;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import tools.activity.SimpleToolbarActivity;

public class PackingCashExplainActivity extends SimpleToolbarActivity {
    private WebView webview;
    private ProgressBar progressBar1;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        setTitle("包装物押金说明");
    }

    @Override
    protected void initMainView() {
        View view = getLayoutView(R.layout.t_webview);
        body_other.addView(view);
        webview = ViewUtils.findViewById(this, R.id.webView1);
        progressBar1=ViewUtils.findViewById(this, R.id.progressBar1);
        progressBar1.setVisibility(View.VISIBLE);


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


        if (PublicCache.initializtionData == null) return;
        if (PublicCache.loginSupplier!=null&&!TextUtils.isEmpty(PublicCache.initializtionData.getS_deposit_explanation())) {
            webview.loadUrl(PublicCache.initializtionData.getS_deposit_explanation());
        }else  if (PublicCache.loginPurchase!=null&&!TextUtils.isEmpty(PublicCache.initializtionData.getC_deposit_explanation())) {
            webview.loadUrl(PublicCache.initializtionData.getC_deposit_explanation());
        }


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

        });

        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        webview.clearFormData();
        webview.destroy();
    }
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, PackingCashExplainActivity.class);
        context.startActivity(intent);
    }
}
