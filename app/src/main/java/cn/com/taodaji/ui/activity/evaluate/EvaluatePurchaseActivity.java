package cn.com.taodaji.ui.activity.evaluate;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.base.viewModel.adapter.splite_line.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.BaseIntegral;
import cn.com.taodaji.model.entity.EvaluateIntegral;
import cn.com.taodaji.viewModel.adapter.SimpleEvaluatePurchaseAdapter;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.OrderList;

import com.base.entity.ResultInfo;

import cn.com.taodaji.model.event.EvaluateListSucEvent;
import cn.com.taodaji.model.event.OrderListSucEvent;
import com.base.retrofit.RequestCallback;
import tools.activity.TakePhotosActivity;

import com.base.listener.UploadPicturesDoneListener;
import com.base.utils.BitmapUtil;
import com.base.utils.DensityUtil;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.takephoto.model.TResult;


public class EvaluatePurchaseActivity extends TakePhotosActivity implements UploadPicturesDoneListener, View.OnClickListener {

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor(R.color.orange_yellow_ff7d01);
        setToolBarColor(R.color.orange_yellow_ff7d01);
        simple_title.setText("评价");
    }

    private SimpleEvaluatePurchaseAdapter evaluatePurchaseAdapter;
    private OrderList.ItemsBean bean;
    //ratingBar1送货速度    ratingBar2服务态度
    private RatingBar ratingBar1, ratingBar2;
    private Map<String, Object> parameterMap = new HashMap<>();

    private ImageView hideNameImg;

    @Override
    protected void initMainView() {
        View mainView = getLayoutView(R.layout.activity_evaluate_puchase);
        body_other.addView(mainView);

        RecyclerView recyclerView = ViewUtils.findViewById(mainView, R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(UIUtils.dip2px(10), R.color.gray_f2));
        evaluatePurchaseAdapter = new SimpleEvaluatePurchaseAdapter();
        evaluatePurchaseAdapter.setTakePhoto(getTakePhoto());
        evaluatePurchaseAdapter.setMaxSelect(3);
        evaluatePurchaseAdapter.setUploadPicturesDoneListener(this);
        recyclerView.setAdapter(evaluatePurchaseAdapter);

        ViewUtils.findViewById(mainView, R.id.release).setOnClickListener(this);
        hideNameImg = ViewUtils.findViewById(mainView, R.id.img_hide_name);
        hideNameImg.setOnClickListener(this);

        View footView = ViewUtils.getFragmentView(recyclerView, R.layout.item_evaluate_purchase_foot);
        ratingBar1 = ViewUtils.findViewById(footView, R.id.ratingBar);
        ratingBar2 = ViewUtils.findViewById(footView, R.id.ratingBar2);
        DensityUtil.setViewHight(ratingBar1, BitmapUtil.getDrawable(R.drawable.ratingbar_star).getMinimumHeight());
        DensityUtil.setViewHight(ratingBar2, BitmapUtil.getDrawable(R.drawable.ratingbar_star).getMinimumHeight());
        evaluatePurchaseAdapter.addFooterView(footView);
    }

    public void getParameters() {
        loading();
        if (evaluatePurchaseAdapter.checkedImagesUpDone()) {
            uploadPicturesIsDone(evaluatePurchaseAdapter.geteValuationInfos());
        } else evaluatePurchaseAdapter.setCallBack(true);
    }

    //图片上传完毕后的回调
    @Override
    public void uploadPicturesIsDone(Object obj) {
        if (evaluatePurchaseAdapter == null) return;
        //添加评价，评价内容，图片
        // String ss = evaluatePurchaseAdapter.geteValuationInfos();
        String ss = obj.toString();
        parameterMap.put("evaluationInfos", ss);
        getRequestPresenter().toEvaluation(parameterMap, new RequestCallback<EvaluateIntegral>() {
            @Override
            public void onSuc(EvaluateIntegral body) {
                //通知订单列表，操作成功
                EventBus.getDefault().post(new OrderListSucEvent(bean.getOutTradeNo()));
                //通知评论列表，操作成功
                EventBus.getDefault().post(new EvaluateListSucEvent(true));
                UIUtils.showToastSafesShort(body.getData());
           /*     if (body.getData()==0){

                    UIUtils.showToastSafesShort("评价完成");
                }else {
                    UIUtils.showToastSafesShort("赠送您"+body.getData()+"积分，可去积分商城兑换");
                }*/

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

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        //相册的图片分发
        if (evaluatePurchaseAdapter != null) {
            evaluatePurchaseAdapter.takeSuccess(result);
        }
    }

    @Override
    protected void initData() {
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);

        if (evaluatePurchaseAdapter != null && bean != null) {
            evaluatePurchaseAdapter.notifyDataSetChanged(bean.getExtraField());
            evaluatePurchaseAdapter.setTopPosition(0);
        }

    }

    @Subscribe(sticky = true)
    public void onEvent(OrderList.ItemsBean event) {
        bean = event;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.release:
                float rat1 = ratingBar1.getRating();
                float rat2 = ratingBar2.getRating();
                if (rat1 == 0f) {
                    UIUtils.showToastSafesShort("请对送货速度进行评价");
                    return;
                }
                if (rat2 == 0f) {
                    UIUtils.showToastSafesShort("请对送服务态度进行评价");
                    return;
                }
                if (PublicCache.loginPurchase == null || bean == null) return;
                parameterMap.clear();
                parameterMap.put("customerId", PublicCache.loginPurchase.getEntityId());
                parameterMap.put("orderNo", bean.getOrderNo());
                parameterMap.put("type", 1);

                parameterMap.put("logisticsLevel", rat1);
                parameterMap.put("serviceLevel", rat2);

                parameterMap.put("storeId", -1);
                parameterMap.put("orderId", -1);
                parameterMap.put("creditScore", -1);
                if (hideNameImg.isSelected()) {
                    parameterMap.put("isVirtual", 0);//isVirtual 0匿名1正常
                } else {
                    parameterMap.put("isVirtual", 1);//isVirtual 0匿名1正常
                }
                getParameters();
                break;

            case R.id.img_hide_name://是否匿名提交
                hideNameImg.setSelected(!hideNameImg.isSelected());

                break;


        }
    }
}
