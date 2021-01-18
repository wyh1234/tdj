package cn.com.taodaji.ui.activity.orderPlace;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.base.utils.SystemUtils;
import com.base.viewModel.BaseViewModel;

import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.AfterSalesRequestViewModel;
import tools.activity.SimpleToolbarActivity;
import tools.extend.PhoneUtils;

/**
 * Created by yangkuo on 2018/10/24.
 */
public class AfterSalesRequestActivity extends SimpleToolbarActivity {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        setTitle("申请售后");
    }

    @Override
    protected void initMainView() {
        View mainView = getLayoutView(R.layout.activity_after_sales_request);
        TextView phoneTxt=mainView.findViewById(R.id.call_phone);
        phoneTxt.setText(PhoneUtils.getPhoneAfter());
        body_other.addView(mainView);
        findViewById(R.id.call_phone).setOnClickListener(v -> SystemUtils.callPhone(getBaseActivity(), PhoneUtils.getPhoneAfter()));
    }

    @Override
    public BaseViewModel setViewModel() {
        return new AfterSalesRequestViewModel(this);
    }
}
