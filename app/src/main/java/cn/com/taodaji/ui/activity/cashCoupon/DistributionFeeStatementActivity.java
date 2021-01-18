package cn.com.taodaji.ui.activity.cashCoupon;

import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.DistributionFeeStatementBean;

import com.base.retrofit.ResultInfoCallback;
import tools.activity.SimpleToolbarActivity;
import com.base.utils.ViewUtils;

/**
 * Created by Administrator on 2017-09-28.
 * 配送费activity
 */

public class DistributionFeeStatementActivity extends SimpleToolbarActivity {
    private TextView mTextViewStatement;
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor(R.color.orange_yellow_ff7d01);
        setToolBarColor(R.color.orange_yellow_ff7d01);
        setTitle("配送费说明");
    }

    @Override
    protected void initMainView() {
     View view=ViewUtils.getLayoutView(this,R.layout.activity_distribution_fee_statement);
     body_other.addView(view);
        mTextViewStatement = view.findViewById(R.id.tv_text);
        getData();
    }

    public void getData() {

        getRequestPresenter().distribution_fee_statement(new ResultInfoCallback<List<DistributionFeeStatementBean>>() {
            @Override
            public void onSuccess(List<DistributionFeeStatementBean> body) {
                String text = "";
                if(body.size() > 0 ){
                    for (int i = 0; i <body.size() ; i++) {
                        text += body.get(i).getDescription() + "\n";
                    }
                     mTextViewStatement.setText(text);
                }
            }

            @Override
            public void onFailed(int listResultInfo, String msg) {

            }
        });

    }
}
