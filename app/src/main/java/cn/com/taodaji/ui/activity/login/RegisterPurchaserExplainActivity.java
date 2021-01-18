package cn.com.taodaji.ui.activity.login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.base.utils.ViewUtils;

import cn.com.taodaji.R;
import tools.activity.SimpleToolbarActivity;

public class RegisterPurchaserExplainActivity extends SimpleToolbarActivity {
    private View mainView;
  private TextView text_send_money;


    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("门店注册说明");
    }

    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.activity_register_purchaser_explain);
        body_scroll.addView(mainView);

        text_send_money=ViewUtils.findViewById(mainView,R.id.text_send_money);

        text_send_money.setText("不满100元收取20元配送费");
    }
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, RegisterPurchaserExplainActivity.class);
//        intent.putExtra("year", year);
//        intent.putExtra("month", month);
        context.startActivity(intent);
    }
}
