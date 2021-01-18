package cn.com.taodaji.ui.activity.myself;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.base.utils.ADInfo;
import com.base.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.com.taodaji.R;
import tools.activity.SimpleToolbarActivity;

public class HelpCenterServiceDetailSupplierActivity extends SimpleToolbarActivity {
    private View mainView;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("服务条款");
    }

    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.t_webview);
        body_scroll.addView(mainView);
        EventBus.getDefault().register(this);
    }

    public static void startActivity(Context context, ADInfo abs) {
        EventBus.getDefault().postSticky(abs);
        Intent intent = new Intent(context, HelpCenterServiceDetailSupplierActivity.class);
        context.startActivity(intent);
    }

    @Subscribe(sticky = true)
    public void onEvent(ADInfo event) {
        EventBus.getDefault().removeStickyEvent(event);

        WebView webview = findViewById(R.id.webView1);
        WebSettings sebs = webview.getSettings();
        // sebs.setJavaScriptEnabled(true);
        String[] url = {"helpCenter/app_private.html", "helpCenter/app_service.html", "helpCenter/app_spend.html"};

        sebs.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//支持内容重新布局
        // sebs.setTextZoom(140);//字体大小默认是100
//        webview.loadUrl("file:///android_asset/"+url[event.getEntity_id()]);

        webview.loadUrl(UIUtils.getAssetsUrl(url[event.getEntity_id()]));

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
