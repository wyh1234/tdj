package cn.com.taodaji.ui.activity.evaluate;


import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.base.glide.GlideImageView;
import com.base.glide.ImageLoaderUtils;
import com.base.utils.BitmapUtil;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.BaseIntegral;
import cn.com.taodaji.model.entity.EvaluateIntegral;
import cn.com.taodaji.model.entity.OrderList;

import com.base.entity.ResultInfo;

import cn.com.taodaji.model.event.OrderListSucEvent;

import com.base.retrofit.RequestCallback;

import tools.activity.SimpleToolbarActivity;

import com.base.utils.DensityUtil;
import com.base.utils.UIUtils;

public class EvaluateSupplierActivity extends SimpleToolbarActivity implements View.OnClickListener {
    GlideImageView customerLogo;
    TextView customerName;
    TextView payCount;
    RatingBar ratingBar;
    TextView ratingBarValue;
    Button post;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        setTitle("评价");
    }

    private OrderList.ItemsBean bean;

    @Override
    protected void initMainView() {
        View mainView = getLayoutView(R.layout.item_evaluate_supplier);
        body_other.addView(mainView);
        customerLogo = ViewUtils.findViewById(mainView, R.id.customerLogo);
        customerName = ViewUtils.findViewById(mainView, R.id.customerName);
        payCount = ViewUtils.findViewById(mainView, R.id.pay_count);
        ratingBar = ViewUtils.findViewById(mainView, R.id.ratingBar);
        ratingBarValue = ViewUtils.findViewById(mainView, R.id.ratingBar_value);
        post = ViewUtils.findViewById(mainView, R.id.post);
        post.setOnClickListener(this);
        DensityUtil.setViewHight(ratingBar, BitmapUtil.getDrawable(R.drawable.ratingbar_star).getMinimumHeight());
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratingBarValue.setText(v + "分");
            }
        });
        bean = (OrderList.ItemsBean) getIntent().getSerializableExtra("data");
        if (bean != null) {
            ImageLoaderUtils.loadImage(customerLogo,  bean.getCustomerLogo());
            customerName.setText(bean.getCustomerName());
            payCount.setText("第" + bean.getBuyNumber() + "次购买");
        }

    }

    @Override
    protected void initData() {

    }


    public void onClick(View view) {
        float f = ratingBar.getRating();
        if (PublicCache.loginSupplier == null || bean == null) return;
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("customerId", PublicCache.loginSupplier.getEntityId());
        parameterMap.put("orderNo", bean.getOrderNo());
        parameterMap.put("type", 2);

        parameterMap.put("logisticsLevel", -1);
        parameterMap.put("serviceLevel", -1);
        parameterMap.put("evaluationInfos", "");

        parameterMap.put("storeId", PublicCache.loginSupplier.getStore());
        parameterMap.put("orderId", bean.getOrderId());
        parameterMap.put("creditScore", (double) f);
        loading();
        getRequestPresenter().toEvaluation(parameterMap, new RequestCallback<EvaluateIntegral>() {
            @Override
            public void onSuc(EvaluateIntegral body) {
                //通知订单列表，操作成功
                if (bean != null)
                    EventBus.getDefault().post(new OrderListSucEvent(bean.getOutTradeNo()));
                UIUtils.showToastSafesShort(body.getData());
                loadingDimss();
                finish();
            }

            @Override
            public void onFailed(int resultInfo, String msg) {
                UIUtils.showToastSafesShort(msg);
                loadingDimss();
            }
        });
    }
}
