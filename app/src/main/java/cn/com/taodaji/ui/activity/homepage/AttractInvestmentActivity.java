package cn.com.taodaji.ui.activity.homepage;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.base.utils.SystemUtils;
import com.base.utils.ViewUtils;

import cn.com.taodaji.R;
import tools.activity.SimpleToolbarActivity;

/**
 * Created by yangkuo on 2018-08-06.
 */
public class AttractInvestmentActivity extends SimpleToolbarActivity {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setTitle("招商入驻");
        setStatusBarColor();
        setToolBarColor();
    }

    @Override
    protected void initMainView() {
        View view = ViewUtils.getLayoutView(this, R.layout.activity_attract_investment);
        body_other.addView(view);
        String name = getIntent().getStringExtra("name");
        //String phone = getIntent().getStringExtra("phone");
        TextView tv_name = view.findViewById(R.id.tv_name);
        tv_name.setText(String.valueOf(name));
//        TextView tv_text2 = view.findViewById(R.id.tv_text2);
//        tv_text2.setText("联系电话：" + phone);
//        tv_text2.setOnClickListener(v -> SystemUtils.callPhone(this, phone));
    }
}
