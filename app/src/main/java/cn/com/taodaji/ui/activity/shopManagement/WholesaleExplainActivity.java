package cn.com.taodaji.ui.activity.shopManagement;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.base.utils.UIUtils;

import cn.com.taodaji.R;
import tools.activity.SimpleToolbarActivity;

public class WholesaleExplainActivity extends SimpleToolbarActivity {
    private View mainView;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("整件规格");
    }

    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.t_webview);
        body_other.addView(mainView);

        WebView webview = findViewById(R.id.webView1);
        WebSettings webSettings = webview.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true); //处理js方法调用出错情况
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//支持内容重新布局

        webview.loadUrl(UIUtils.getAssetsUrl("wholeUnitShow/whole_unit_show.html"));


    }

}
