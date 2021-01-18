package cn.com.taodaji.ui.activity.evaluate;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.base.viewModel.adapter.splite_line.DividerItemDecoration;
import com.base.entity.ResultInfo;
import com.base.listener.UploadPicturesDoneListener;
import com.base.takephoto.model.TResult;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.adapter.SimpleEvaluatePurchaseAdapter;
import cn.com.taodaji.model.entity.EvaluationList;
import cn.com.taodaji.model.event.EvaluateListSucEvent;

import com.base.retrofit.RequestCallback;

import cn.com.taodaji.model.sqlBean.CartGoodsBean;
import tools.activity.TakePhotosActivity;


public class EvaluateUpdatePurchaseActivity extends TakePhotosActivity implements UploadPicturesDoneListener, View.OnClickListener {

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor(R.color.orange_yellow_ff7d01);
        setToolBarColor(R.color.orange_yellow_ff7d01);
        simple_title.setText("更改评价");
    }

    private SimpleEvaluatePurchaseAdapter evaluatePurchaseAdapter;

    private EvaluationList.DataBean.ItemsBean evaluationBean;
    //ratingBar1送货速度    ratingBar2服务态度
    private RatingBar ratingBar1, ratingBar2;
    private Map<String, Object> parameterMap = new HashMap<>();

    private ImageView hideNameImg;
    private LinearLayout lineHideName;
//    private  View footView;

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
        lineHideName = ViewUtils.findViewById(mainView, R.id.line_hide_name);
        lineHideName.setVisibility(View.GONE);

//        footView = ViewUtils.getFragmentView(recyclerView, R.layout.item_evaluate_purchase_foot);
//        ratingBar1 = ViewUtils.findViewById(footView, R.id.ratingBar);
//        ratingBar2 = ViewUtils.findViewById(footView, R.id.ratingBar2);
//        DensityUtil.setViewHight(ratingBar1, UIUtils.getDrawable(R.drawable.ratingbar_star).getMinimumHeight());
//        DensityUtil.setViewHight(ratingBar2, UIUtils.getDrawable(R.drawable.ratingbar_star).getMinimumHeight());


    }

    public void getParameters() {
        loading();
        if (evaluatePurchaseAdapter.checkedImagesUpDone()) {


            if (evaluationBean != null) {
                uploadPicturesIsDone(evaluatePurchaseAdapter.getViewValues());
            }


        } else evaluatePurchaseAdapter.setCallBack(true);
    }

    //图片上传完毕后的回调
    @Override
    public void uploadPicturesIsDone(Object obj) {
        if (evaluatePurchaseAdapter == null) return;

        if (evaluationBean != null) {
            modify(obj);
        }

    }

    private void modify(Object obj) {
        if (obj != null) {
            Map<String, Object> map = (Map<String, Object>) obj;
            getRequestPresenter().buyer_evaluation_update(map, new RequestCallback<ResultInfo>() {
                @Override
                public void onSuc(ResultInfo body) {
                    Object object = map.get("creditLevel");
                    evaluationBean.setCreditLevel(object == null ? 0 : Integer.valueOf(object.toString()));

                    Object object1 = map.get("evalImg");
                    evaluationBean.setCreditImgs(object1 == null ? "" : object1.toString());

                    Object object2 = map.get("evaluationContent");
                    evaluationBean.setCreditContent(object2 == null ? "" : object2.toString());

                    evaluationBean.setModifyCount(evaluationBean.getModifyCount() + 1);
                    //通知评论列表，操作成功
                    EventBus.getDefault().post(new EvaluateListSucEvent(evaluationBean));

                    UIUtils.showToastSafesShort("评价完成");
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


        if (evaluatePurchaseAdapter != null && evaluationBean != null) {
            List<CartGoodsBean> extraField = new ArrayList<>();
            CartGoodsBean cartGoodsBean = new CartGoodsBean();
            cartGoodsBean.setEntityId(evaluationBean.getEntityId());
            cartGoodsBean.setProductImage(evaluationBean.getProductImg());
            cartGoodsBean.setCreditImgs(evaluationBean.getCreditImgs());
            cartGoodsBean.setCreditLevel(evaluationBean.getCreditLevel());
            cartGoodsBean.setStoreId(evaluationBean.getStoreId());
            cartGoodsBean.setItemId(evaluationBean.getOrderItemId());
            cartGoodsBean.setOrderId(evaluationBean.getOrderId());
            cartGoodsBean.setProductId(evaluationBean.getProductId());
            cartGoodsBean.setCreditContent(evaluationBean.getCreditContent());
            extraField.add(cartGoodsBean);
            evaluatePurchaseAdapter.notifyDataSetChanged(extraField);
            evaluatePurchaseAdapter.setTopPosition(0);
        }

    }


    @Subscribe(sticky = true)
    public void onEvent(EvaluationList.DataBean.ItemsBean event) {

        evaluationBean = event;


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.release:
                getParameters();
                break;

            case R.id.img_hide_name://是否匿名提交
                hideNameImg.setSelected(!hideNameImg.isSelected());
                break;


        }
    }
}
