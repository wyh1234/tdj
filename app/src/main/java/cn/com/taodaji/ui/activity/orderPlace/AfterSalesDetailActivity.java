package cn.com.taodaji.ui.activity.orderPlace;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;
import com.base.utils.CustomerData;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.adapter.splite_line.DividerGridItemDecoration;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.DeleteSalesAppByEntityId;
import cn.com.taodaji.model.entity.RefundDetail;
import cn.com.taodaji.model.event.AfterSalesCancleEvent;
import cn.com.taodaji.viewModel.adapter.SimpleAfterSalesDetailAdapter;
import cn.com.taodaji.viewModel.adapter.SimpleAfterSalesImageAdapter;
import tools.activity.SimpleToolbarActivity;

/**
 * 退款退货详情页面
 **/
public class AfterSalesDetailActivity extends SimpleToolbarActivity implements View.OnClickListener {

    TextView textView;
    TextView refundNo;
    TextView textView1;
    TextView refundTime;
    TextView refundCancel;
    RecyclerView recyclerView;
    RecyclerView imageRecyler;
    TextView refundCount, tv_problem_type, tv_problem_remark;
    LinearLayout lv_problem_type;
    TextView refundMoney;
    TextView description;
    TextView textView11, tv_cause, tv_explain;
    TextView textView12;
    TextView unit;
    TextView help;
    TextView mTv_bh_text;
    TextView mTv_bh,problem_info;
    LinearLayout mLinearLayoutGone;
    private List<String> imageUrlList = new ArrayList<>();
    private String mTextOrderNo = "";       //订单编号
    private String mTextQrCodeId = "";      //商品编号

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setToolBarColor();
        setStatusBarColor();
    }

    private static final int send_msg_code = 0x101;
    private SimpleAfterSalesDetailAdapter afterSalesDetailAdapter;
    private SimpleAfterSalesImageAdapter afterSalesImageAdapter;
    private RefundDetail refundDetail;
    private int isUserCoupon = 0;

    @Override
    protected void initMainView() {
        View mainView = getLayoutView(R.layout.activity_refund_detail);
        body_scroll.addView(mainView);
        problem_info= ViewUtils.findViewById(mainView, R.id.problem_info);
        textView = ViewUtils.findViewById(mainView, R.id.textView);
        refundNo = ViewUtils.findViewById(mainView, R.id.refund_no);
        textView1 = ViewUtils.findViewById(mainView, R.id.textView_1);
        refundTime = ViewUtils.findViewById(mainView, R.id.refund_time);
        refundCancel = ViewUtils.findViewById(mainView, R.id.refund_cancel);
        refundCancel.setOnClickListener(this);

        tv_problem_type = ViewUtils.findViewById(mainView, R.id.tv_problem_type);
        lv_problem_type = ViewUtils.findViewById(mainView, R.id.lv_problem_type);
        tv_problem_remark = ViewUtils.findViewById(mainView, R.id.tv_problem_remark);

        recyclerView = ViewUtils.findViewById(mainView, R.id.recyclerView);
        imageRecyler = ViewUtils.findViewById(mainView, R.id.image_recyler);
        refundCount = ViewUtils.findViewById(mainView, R.id.refund_count);
        refundMoney = ViewUtils.findViewById(mainView, R.id.refund_money);
        description = ViewUtils.findViewById(mainView, R.id.description);
        textView11 = ViewUtils.findViewById(mainView, R.id.textView_11);
        textView12 = ViewUtils.findViewById(mainView, R.id.textView_12);

        tv_cause = mainView.findViewById(R.id.tv_cause);
        tv_explain = mainView.findViewById(R.id.tv_explain);

        unit = ViewUtils.findViewById(mainView, R.id.unit);
        help = ViewUtils.findViewById(mainView, R.id.help);
        mTv_bh_text = ViewUtils.findViewById(mainView, R.id.textview_text_bh);
        mTv_bh = ViewUtils.findViewById(mainView, R.id.textview_bh);
        mLinearLayoutGone = ViewUtils.findViewById(mainView, R.id.linearLayout_gone);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(1, R.color.gray_db));
        recyclerView.addItemDecoration(new LeftDecoration());
        afterSalesDetailAdapter = new SimpleAfterSalesDetailAdapter();
        recyclerView.setAdapter(afterSalesDetailAdapter);

        imageRecyler.setLayoutManager(new GridLayoutManager(this, 3));
        imageRecyler.addItemDecoration(new DividerGridItemDecoration(UIUtils.dip2px(10), R.color.white));
        afterSalesImageAdapter = new SimpleAfterSalesImageAdapter();
        imageRecyler.setAdapter(afterSalesImageAdapter);
    }

    @Override
    protected void initData() {

        int id = getIntent().getIntExtra("data", -1);
        int itemId = getIntent().getIntExtra("itemId", -1);

        if (id != -1) {
            onStartLoading();
            getRequestPresenter().after_details(id, new ResultInfoCallback<RefundDetail>(this) {
                @Override
                public void onSuccess(RefundDetail body) {
                    afterDatas(body);
                }

                @Override
                public void onFailed(int refundDetailResultInfo, String msg) {
//                loadingDimss();
                    UIUtils.showToastSafe(msg);
                }
            });
        } else if (itemId != -1) {
            onStartLoading();
            getRequestPresenter().after_details_order(itemId, new ResultInfoCallback<RefundDetail>(this) {
                @Override
                public void onSuccess(RefundDetail body) {
                    afterDatas(body);
                }

                @Override
                public void onFailed(int refundDetailResultInfo, String msg) {
//                loadingDimss();
                    UIUtils.showToastSafe(msg);
                }
            });
        }


    }

    private void afterDatas(RefundDetail body) {
        isUserCoupon = body.getIsUseCoupon();
        mTextQrCodeId = body.getQr_code_id();
        CustomerData<Integer, String, String> problem = PublicCache.getAfterSaleProblem();
        if (problem != null) {
            String pro = problem.getValueOfKey(body.getProblem_type());
            if (!TextUtils.isEmpty(pro)) {
                tv_problem_type.setText(pro);
            }
        } else UIUtils.showToastSafesShort("问题类型出错 type=" + body.getApply_type());

        if (TextUtils.isEmpty(body.getRemark())) {
            lv_problem_type.setVisibility(View.GONE);
        } else {
            lv_problem_type.setVisibility(View.VISIBLE);
            tv_problem_remark.setText(body.getRemark());
        }
        refundDetail = body;
        handler.sendEmptyMessage(send_msg_code);
//                loadingDimss();
        if (body.getProblem_info()==1){
            problem_info.setText("售后责任归属：物流责任");
        }else if (body.getProblem_info()==2){
            problem_info.setText("售后责任归属：客户责任");
        }else if (body.getProblem_info()==3){
            problem_info.setText("售后责任归属：销售商责任");
        }else if (body.getProblem_info()==4){
            problem_info.setText("售后责任归属：无效售后");
        }else {
            problem_info.setText("售后责任归属：其他");
        }
    }

    public void onEvent() {

        if (refundDetail == null) return;

        String[] image = null;
        image = refundDetail.getCertificate_photos().split(",");
        for (int i = 0; i < image.length; i++) {
            if (image[i] != null && image[i].contains(".")) {
                imageUrlList.add(image[i]);
            }
        }
        mTextOrderNo = refundDetail.getOrder_no();   //订单编号
        initTextData();
        //1退款,2换货
        if (refundDetail.getApply_type() == 1) {
            textView11.setText("申请退款：");
            textView1.setText("申请退款时间：");
            textView12.setText("退款金额：");
            simple_title.setText("退款详情");
            tv_cause.setText("退款原因：");
            tv_explain.setText("退款说明：");
        } else if (refundDetail.getApply_type() == 2) {
            textView11.setText("申请换货：");
            textView1.setText("申请换货时间：");
            textView12.setText("换货金额：");
            simple_title.setText("换货详情");
            tv_cause.setText("换货原因：");
            tv_explain.setText("换货说明：");
        } else if (refundDetail.getApply_type() == 3) {
            textView11.setText("申请退款：");
            textView1.setText("申请售后时间：");
            textView12.setText("退款金额：");
            simple_title.setText("退款退货详情");
            tv_cause.setText("退款原因：");
            tv_explain.setText("退款说明：");
        } else if (refundDetail.getApply_type() == 4) {
            textView11.setText("申请补货：");
            textView1.setText("申请补货时间：");
            textView12.setText("补货金额：");
            simple_title.setText("补货详情");
            tv_cause.setText("补货原因：");
            tv_explain.setText("补货说明：");
        }


        refundNo.setText(refundDetail.getQr_code_id());
        refundTime.setText(refundDetail.getCreate_time());
        description.setText(refundDetail.getProblem_description());
        refundCount.setText(String.valueOf(refundDetail.getAmount()));
        refundMoney.setText(String.valueOf(refundDetail.getTotal_price()));
        unit.setText(refundDetail.getAvg_unit());
        afterSalesDetailAdapter.setList(refundDetail.getItems());
        afterSalesDetailAdapter.setSelected(0);


        String imageStr = "";
        for (int i = 0; i < imageUrlList.size(); i++) {
            if (i != imageUrlList.size() - 1) {
                imageStr += imageUrlList.get(i) + ",";
            } else {
                imageStr += imageUrlList.get(i);
            }
        }
        afterSalesImageAdapter.setData(imageStr);

        if (PublicCache.loginSupplier != null) refundCancel.setVisibility(View.GONE);
        else if (PublicCache.loginPurchase != null && refundDetail.getStatus() > 3)
            refundCancel.setVisibility(View.GONE);
        else refundCancel.setVisibility(View.VISIBLE);

    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            if (what == send_msg_code) {
                onEvent();
                if (isUserCoupon == 1) {
                    help.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    private void initTextData() {
        if (PublicCache.loginSupplier != null) {
            textView.setText("商品编号：");
            refundNo.setText(mTextQrCodeId);
        } else {
            textView.setText("订单编号：");
            refundNo.setText(mTextOrderNo);
        }
    }


    public static void startActivity(Context context, int id, int itemId) {
        Intent intent = new Intent(context, AfterSalesDetailActivity.class);
        intent.putExtra("data", id);
        intent.putExtra("itemId", itemId);
        context.startActivity(intent);
    }


    public void onClick(View view) {
        if (PublicCache.loginPurchase==null)return;
        if (PublicCache.loginPurchase.getEmpRole()==2||PublicCache.loginPurchase.getEmpRole()==5) {
            UIUtils.showToastSafesShort(PublicCache.getRoleType().getValueOfKey(PublicCache.loginPurchase.getEmpRole())+"没有该操作权限");
            return ;
        }
        if (refundDetail != null) {
            loading();
            getRequestPresenter().deleteSalesAppByEntityId(refundDetail.getEntity_id(),refundDetail.getOrder_id(),refundDetail.getOrder_item_id(), new RequestCallback<DeleteSalesAppByEntityId>() {
                @Override
                public void onSuc(DeleteSalesAppByEntityId body) {
                    loadingDimss();
                    UIUtils.showToastSafesShort("撤销成功");
                    EventBus.getDefault().post(new AfterSalesCancleEvent(refundDetail.getOrder_item_id()));
                    finish();
                }

                @Override
                public void onFailed(int deleteSalesAppByEntityId, String msg) {
                    loadingDimss();
                    UIUtils.showToastSafesShort(msg);
                }
            });
        }

    }
}
