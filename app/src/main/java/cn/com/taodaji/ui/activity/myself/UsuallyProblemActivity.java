package cn.com.taodaji.ui.activity.myself;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.base.utils.ViewUtils;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.ShopEmployeeItem;
import cn.com.taodaji.model.entity.ShopEmployeeList;
import cn.com.taodaji.viewModel.adapter.EmployeesManagementAdapter;
import cn.com.taodaji.viewModel.adapter.UsuallyProblemAdapter;
import tools.activity.SimpleToolbarActivity;

public class UsuallyProblemActivity  extends SimpleToolbarActivity {

    private View mainView;
    private WebView webView;
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("常见问题");
    }

    protected void initMainView() {
        mainView = ViewUtils.getLayoutViewMatch(this, R.layout.activity_usually_problem);
        body_other.addView(mainView);

        webView = mainView.findViewById(R.id.wv_problem);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.loadUrl("https://www.taodaji.com.cn/h5/rw/help.html");
    }

}
