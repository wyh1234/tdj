package cn.com.taodaji.ui.activity.myself;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import com.base.retrofit.ResultInfoCallback;
import tools.activity.SimpleToolbarActivity;

import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

/**
 * Created by Administrator on 2017-09-02.
 */

public class QQNumberAddActivity extends SimpleToolbarActivity implements View.OnClickListener {
    EditText qqNumber;
    TextView ok;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor(R.color.blue_2898eb);
        setToolBarColor(R.color.blue_2898eb);
        setTitle("店铺QQ");
    }

    @Override
    protected void initMainView() {
        View view = ViewUtils.getLayoutViewMatch(this, R.layout.activity_myself_qq_number);
        body_other.addView(view);
        qqNumber = ViewUtils.findViewById(view, R.id.qq_number);
        ok = ViewUtils.findViewById(view, R.id.ok);
        ok.setOnClickListener(this);
    }

    public void onClick(View view) {
        final String qq = qqNumber.getText().toString().trim();
        if (qq.length() == 0) {
            UIUtils.showToastSafesShort("请输入QQ号码");
            return;
        }
        if (PublicCache.loginSupplier == null) return;
        loading();
        getRequestPresenter().qq_update(PublicCache.loginSupplier.getEntityId(), qq, new ResultInfoCallback() {

            @Override
            public void onFailed(int o, String msg) {
                UIUtils.showToastSafesShort(msg);
                loadingDimss();
            }

            @Override
            public void onSuccess(Object body) {
                if (PublicCache.loginSupplier != null) {
                    PublicCache.loginSupplier.setQqNumber(qq);
                    PublicCache.loginSupplier.update();
                }
                loadingDimss();
                UIUtils.showToastSafesShort("上传成功");
                finish();
            }
        });


    }
}
