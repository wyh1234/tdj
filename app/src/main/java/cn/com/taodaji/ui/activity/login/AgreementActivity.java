package cn.com.taodaji.ui.activity.login;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import cn.com.taodaji.R;
import tools.activity.SimpleToolbarActivity;

public class AgreementActivity extends SimpleToolbarActivity {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("用户协议");
    }

    @Override
    protected void initMainView() {
        View view = getLayoutView(R.layout.t_webview);
        body_other.addView(view);

        WebView webview = (WebView) view.findViewById(R.id.webView1);
        WebSettings sebs = webview.getSettings();
        // sebs.setJavaScriptEnabled(true);

        sebs.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//支持内容重新布局
       // sebs.setTextZoom(140);//字体大小默认是100
        webview.loadUrl("file:///android_asset/app_agreement.html");
    }

    @Override
    protected void initData() {

    }
}
