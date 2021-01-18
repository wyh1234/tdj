package cn.com.taodaji.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import cn.com.taodaji.R;
import cn.com.taodaji.common.Constants;
import cn.com.taodaji.ui.activity.homepage.ManageActivity;
import cn.com.taodaji.common.PublicCache;
import tools.activity.DataBaseActivity;

import com.base.activity.ActivityManage;
import com.base.activity.BaseActivity;
import com.base.utils.ViewUtils;

public class ChooseLoginActivity extends DataBaseActivity implements View.OnClickListener, Constants {

    private LinearLayout login_close;

    @Override
    protected void initView() {
        super.initView();
        setStatusBarForeColorColor(false);
        findViewById(R.id.purchaser_login).setOnClickListener(this);
        findViewById(R.id.supplier_login).setOnClickListener(this);
        login_close = ViewUtils.findViewById(this, R.id.login_close);
        login_close.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }


    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.activity_login_choose);
    }


    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.login_close:
                if (ActivityManage.isActivityExist(ManageActivity.class)) {
                    finish();
                    return;
                }

                //关闭按扭后跳转到主页
                intent = new Intent(this, ManageActivity.class);
                break;
            case R.id.purchaser_login:
                //采购商登录
                intent = new Intent(this, LoginPurchaserActivity.class);
                intent.putExtra(FLAG, PURCHASER);
                PublicCache.login_mode = PURCHASER;
                break;
            case R.id.supplier_login:
                //供应商登录
                intent = new Intent(this, LoginActivity.class);
                intent.putExtra(FLAG, SUPPLIER);
                PublicCache.login_mode = SUPPLIER;
                break;

        }
        if (intent != null) {
            startActivity(intent);
            this.finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            login_close.callOnClick();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
