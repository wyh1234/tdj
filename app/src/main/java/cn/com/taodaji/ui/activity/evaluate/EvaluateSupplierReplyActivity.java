package cn.com.taodaji.ui.activity.evaluate;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.EvaluationList;
import cn.com.taodaji.model.entity.EvaluationUpdate;

import com.base.viewModel.BaseViewHolder;

import cn.com.taodaji.model.event.EvaluateReplyEvent;
import com.base.retrofit.ResultInfoCallback;
import cn.com.taodaji.viewModel.vm.EvaluateManageVM;
import tools.activity.SimpleToolbarActivity;

import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

public class EvaluateSupplierReplyActivity extends SimpleToolbarActivity {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        setTitle("回复评价");
    }

    private View mainView;
    private EvaluationList.DataBean.ItemsBean bean;
    private EditText supplier_reply;

    @Override
    protected void initMainView() {
        mainView = ViewUtils.getLayoutViewMatch(this, R.layout.item_evaluate_supplier_reply);
        body_other.addView(mainView);

        supplier_reply = ViewUtils.findViewById(mainView, R.id.supplier_reply);
        Button button = ViewUtils.findViewById(mainView, R.id.post);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ss = supplier_reply.getText().toString();
                if (ss.length() == 0) {
                    UIUtils.showToastSafesShort("请输入回复内容");
                    return;
                }
                if (bean == null) return;
                loading();
                getRequestPresenter().evaluation_update(bean.getEntityId(), ss, new ResultInfoCallback<EvaluationUpdate>() {
                    @Override
                    public void onFailed(int evaluationUpdateResultInfo, String msg) {
                        loadingDimss();
                    }

                    @Override
                    public void onSuccess(EvaluationUpdate body) {
                        //发送评价回复成功事件
                        if (bean != null)
                            EventBus.getDefault().post(new EvaluateReplyEvent(bean.getEntityId()));
                        UIUtils.showToastSafesShort("回复成功");
                        loadingDimss();
                        finish();
                    }
                });
            }
        });
    }

    @Override
    protected void initData() {
        bean = (EvaluationList.DataBean.ItemsBean) getIntent().getSerializableExtra("data");
        if (bean == null) return;
        BaseViewHolder viewHolder = new BaseViewHolder(mainView, new EvaluateManageVM(), null);
        viewHolder.setValues(bean);

//        View textView21 = ViewUtils.findViewById(mainView, R.id.textView_21);
//        View textView22 = ViewUtils.findViewById(mainView, R.id.textView_22);


        ImageView evaluate_logo = ViewUtils.findViewById(mainView, R.id.evaluate_logo);
        evaluate_logo.setSelected(true);
        evaluate_logo.setImageResource(PublicCache.getEvaluateInformation().getKeyOfId(bean.getCreditLevel()));
        TextView evaluate_name = ViewUtils.findViewById(mainView, R.id.evaluate_name);
        evaluate_name.setText(PublicCache.getEvaluateInformation().getValueOfId(bean.getCreditLevel()));



//        if (TextUtils.isEmpty(bean.getNickName())) {
//            if (textView21 != null) textView21.setVisibility(View.GONE);
//            if (textView22 != null) textView22.setVisibility(View.GONE);
//        } else {
//            if (textView21 != null) textView21.setVisibility(View.VISIBLE);
//            if (textView22 != null) textView22.setVisibility(View.VISIBLE);
//        }
    }
}
