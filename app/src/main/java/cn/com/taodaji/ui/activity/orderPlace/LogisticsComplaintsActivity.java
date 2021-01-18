package cn.com.taodaji.ui.activity.orderPlace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.glide.nineImageView.ImagesActivity;
import com.base.listener.UploadPicturesDoneListener;
import com.base.retrofit.RequestCallback;
import com.base.utils.DensityUtil;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.AddCategory;
import cn.com.taodaji.model.entity.ComplaintDetail;
import cn.com.taodaji.model.entity.OrderDetail;
import cn.com.taodaji.model.entity.ProblemItem;
import cn.com.taodaji.model.entity.ProblemList;
import cn.com.taodaji.model.event.ComplaintEvent;
import cn.com.taodaji.model.event.SelectShopOrPositionEvent;
import cn.com.taodaji.model.presenter.RequestPresenter2;
import cn.com.taodaji.ui.activity.employeeManagement.PopupBottomActivity;
import cn.com.taodaji.viewModel.adapter.ComplaintPictureAdapter;
import cn.com.taodaji.viewModel.adapter.SimpleAfterSalesImageAdapter;
import tools.activity.TakePhotosActivity;
import tools.fragment.AddedPicturesFragment;

public class LogisticsComplaintsActivity extends TakePhotosActivity implements UploadPicturesDoneListener,View.OnClickListener {

    private int driverId,type=0;
    private String extOrderId;
    private OrderDetail orderDetail;
    private TextView driverName,orderNo,problemType,contact,contactPhone,pictureUpload,tips;
    private EditText problemInfo;
    private Button commit;
    private ArrayList<ProblemItem> problemList = new ArrayList<>();
    private AddedPicturesFragment addedPicturesFragment;
    private int flag=0;
    private RecyclerView recyclerView;
    private SimpleAfterSalesImageAdapter afterSalesImageAdapter;
    private List<ProblemItem> itemList = new ArrayList<>();
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("物流投诉");
        flag = getIntent().getIntExtra("type",0);
        if (flag==1){
            simple_title.setText("投诉中心");
        }
    }

    @Override
    protected void initMainView() {
        View mainView = getLayoutView(R.layout.activity_logistics_complaints);
        body_scroll.addView(mainView);

        orderDetail = (OrderDetail) getIntent().getSerializableExtra("orderDetail");
        driverId = orderDetail.getItems().get(0).getDriverId();
        extOrderId = orderDetail.getExtOrderId();

        driverName = mainView.findViewById(R.id.tv_driver_name);
        driverName.setText(orderDetail.getItems().get(0).getDriverName());
        recyclerView = mainView.findViewById(R.id.rv_complaints_picture);
        orderNo = mainView.findViewById(R.id.tv_order_number);
        orderNo.setText(orderDetail.getOrderNo());
        problemType = mainView.findViewById(R.id.tv_select_problem);
        problemType.setOnClickListener(this);
        problemInfo = mainView.findViewById(R.id.et_problem_info);
        contact = mainView.findViewById(R.id.tv_contact);
        contactPhone = mainView.findViewById(R.id.tv_contact_phone);
        commit = mainView.findViewById(R.id.btn_commit);
        commit.setOnClickListener(this);
        linearLayout = mainView.findViewById(R.id.ll_cnm);
        tips = mainView.findViewById(R.id.tv_tips1);
        pictureUpload = mainView.findViewById(R.id.tv_picture_upload);

        addedPicturesFragment = (AddedPicturesFragment) getSupportFragmentManager().findFragmentById(R.id.addedPicturesFragment);
        //addedPicturesFragment.setBackgroundColor(R.color.white);
        addedPicturesFragment.setMaxSelect(3);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        afterSalesImageAdapter = new SimpleAfterSalesImageAdapter(){
            @Override
            public View onCreateHolderView(ViewGroup parent, int viewType) {
                View view = ViewUtils.getFragmentView(parent, R.layout.t_imageview);
                int w = parent.getMeasuredWidth();
                int h = (w - DensityUtil.dp2px(60)) / 3;
                UIUtils.setViewSize(view, h, h);
                return view;
            }
        };
        recyclerView.setAdapter(afterSalesImageAdapter);

        if (flag==1){
            initComplaintsDetail();
            linearLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            pictureUpload.setText("上传的凭证照片");
            tips.setText("(*点击可查看大图)");
        }else {
            linearLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            initProblemList();
            pictureUpload.setText("上传凭证");
            tips.setText("(*选填项/不超过3张)");
        }

    }

    public void initProblemList(){
        problemList.clear();
        getRequestPresenter().getLogisticsProblem(1,PublicCache.loginPurchase.getLoginUserId(), new RequestCallback<ProblemList>() {
            @Override
            protected void onSuc(ProblemList body) {
                if (body.getErr()==0){
                    if (body.getData().getList().size()!=0){
                        for (ProblemList.DataBean.ListBean bean:body.getData().getList()){
                            ProblemItem item = new ProblemItem();
                            item.setText(bean.getProblem());
                            item.setId(bean.getEntityId());
                            problemList.add(item);
                        }
                    }
                    contact.setText(body.getData().getEvaName());
                    contactPhone.setText(body.getData().getEvaTel());
                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafe(msg);
            }
        });
    }

    public void commitComplaints(){
        RequestPresenter2.getInstance().addComplaint(driverId, extOrderId, type, problemInfo.getEditableText().toString(), addedPicturesFragment.getImageStr(), PublicCache.loginPurchase.getLoginUserId(), PublicCache.loginPurchase.getEntityId(), new RequestCallback<AddCategory>() {
            @Override
            protected void onSuc(AddCategory body) {
                UIUtils.showToastSafe(body.getMsg());
                finish();
            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafe(msg);
            }
        });
    }

    public void initComplaintsDetail(){
        RequestPresenter2.getInstance().findComplaintDetail(driverId, extOrderId, new RequestCallback<ComplaintDetail>() {
            @Override
            protected void onSuc(ComplaintDetail body) {
                if (body.getErr()==0){
                    problemType.setText(body.getData().getItems().getProblem());
                    problemType.setClickable(false);
                    problemInfo.setText(body.getData().getItems().getContent());
                    problemInfo.setFocusable(false);
                    commit.setVisibility(View.GONE);
                    contact.setText(body.getData().getItems().getEvaName());
                    contactPhone.setText(body.getData().getItems().getEvaTel());
                    afterSalesImageAdapter.setData(body.getData().getItems().getImg());
                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafe(msg);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_commit:
                if (type==0){
                    UIUtils.showToastSafe("请选择问题类型");
                    break;
                }
                if (!addedPicturesFragment.isUploadDone()) {
                    addedPicturesFragment.setCallBack(true);
                    UIUtils.showToastSafe("图片上传中，请稍侯");
                } else {
                    commitComplaints();
                }
                break;
            case R.id.tv_select_problem:
                Intent intent = new Intent(LogisticsComplaintsActivity.this, ComplainTypeActivity.class);
                intent.putExtra("title","问题类型");
                intent.putExtra("itemList",problemList);
                startActivity(intent);
                break;
                default:break;
        }
    }

    @Override
    public void uploadPicturesIsDone(Object obj) {
        if (isDestroyed()) return;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ComplaintEvent event) {
            problemType.setText(event.getText());
            type = event.getId();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
