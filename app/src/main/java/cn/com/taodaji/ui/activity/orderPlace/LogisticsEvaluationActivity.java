package cn.com.taodaji.ui.activity.orderPlace;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.base.glide.GlideImageView;
import com.base.retrofit.RequestCallback;
import com.base.utils.BitmapUtil;
import com.base.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Unbinder;
import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.AddCategory;
import cn.com.taodaji.model.entity.EvaluationDetail;
import cn.com.taodaji.model.entity.OrderDetail;
import cn.com.taodaji.model.entity.ProblemItem;
import cn.com.taodaji.model.entity.ProblemList;
import cn.com.taodaji.model.presenter.RequestPresenter2;
import cn.com.taodaji.viewModel.adapter.ProblemListAdapter;
import tools.activity.SimpleToolbarActivity;

public class LogisticsEvaluationActivity extends SimpleToolbarActivity implements View.OnClickListener {

    private GlideImageView driverIcon;
    private TextView driverName,deliveryDate,tips,evaluationReason;
    private TextView satisfied,unsatisfied,detailContent;
    private OrderDetail orderDetail;
    private LinearLayout llsatisfied,llunsatisfied,llAddEvaluation,llEvaluationDetail;
    private RecyclerView recyclerView;
    private ProblemListAdapter adapter;
    private CheckBox unname;
    private List<ProblemItem> itemList = new ArrayList<>();
    private int evaluationType = 2,anonymousType=0;
    private EditText evaluationContent;
    private StringBuffer problemType= new StringBuffer();
    private Button commit;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("物流评价");
        if (getIntent().getIntExtra("type",0)==1){
            simple_title.setText("评价详情");
        }
    }

    @Override
    protected void initMainView() {
        View mainView = getLayoutView(R.layout.activity_logistics_evaluation);
        body_scroll.addView(mainView);

        orderDetail = (OrderDetail) getIntent().getSerializableExtra("orderDetail");
        driverIcon = mainView.findViewById(R.id.giv_driver_icon);
        driverName = mainView.findViewById(R.id.tv_driver_name);
        deliveryDate = mainView.findViewById(R.id.tv_delivery_date);
        driverName.setText(orderDetail.getItems().get(0).getDriverName()+"（淘大集配送员）");
        deliveryDate.setText(orderDetail.getExpectDeliveredDate()+"  "+orderDetail.getExpectDeliveredEarliestTime()+"--"+orderDetail.getExpectDeliveredLatestTime()+"左右送达");
        satisfied = mainView.findViewById(R.id.tv_satisfied);
        unsatisfied = mainView.findViewById(R.id.tv_unsatisfied);
        llsatisfied = mainView.findViewById(R.id.ll_satisfied);
        llunsatisfied = mainView.findViewById(R.id.ll_unsatisfied);
        llAddEvaluation = mainView.findViewById(R.id.ll_add_evaluation);
        llEvaluationDetail = mainView.findViewById(R.id.ll_evaluation_detail);
        evaluationContent = mainView.findViewById(R.id.et_evaluation_content);
        detailContent = mainView.findViewById(R.id.tv_detail_content);
        tips = mainView.findViewById(R.id.tv_tips);
        evaluationReason = mainView.findViewById(R.id.tv_evaluation_reason);
        commit = mainView.findViewById(R.id.btn_commit_evaluation);
        commit.setOnClickListener(this);
        unname = mainView.findViewById(R.id.cart_checked);
        unname.setOnClickListener(this);
        llsatisfied.setOnClickListener(this);
        llunsatisfied.setOnClickListener(this);
        recyclerView = mainView.findViewById(R.id.rv_problem_list);
        driverIcon.setImageBitmap(BitmapUtil.toRoundBitmap(R.mipmap.driver_icon));

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new ProblemListAdapter(itemList,this);
        recyclerView.setAdapter(adapter);


        if (getIntent().getIntExtra("type",0)==1){
            initDetailList();
        }else {
            //1-物流投诉，0-评价不满意，2-评价满意
            initProblemList(2);

            llsatisfied.setBackgroundResource(R.drawable.bg_selected_evaluation);
            llunsatisfied.setBackgroundResource(R.drawable.bg_unselect_evaluation);
            satisfied.setTextColor(getResources().getColor(R.color.orange_yellow_ff7e00));
            unsatisfied.setTextColor(getResources().getColor(R.color.gray_6a));
            Drawable drawable= getResources().getDrawable(R.mipmap.satisfied_selected);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            satisfied.setCompoundDrawables(drawable,null,null,null);
            Drawable drawable1= getResources().getDrawable(R.mipmap.unsatisfied);
            drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
            unsatisfied.setCompoundDrawables(drawable1,null,null,null);

            adapter.setItemClickListener(new ItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    if (itemList.get(position).isChecked()){
                        itemList.get(position).setChecked(false);
                    }else {
                        itemList.get(position).setChecked(true);
                    }
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_satisfied:
                llsatisfied.setBackgroundResource(R.drawable.bg_selected_evaluation);
                llunsatisfied.setBackgroundResource(R.drawable.bg_unselect_evaluation);
                satisfied.setTextColor(getResources().getColor(R.color.orange_yellow_ff7e00));
                unsatisfied.setTextColor(getResources().getColor(R.color.gray_6a));
                Drawable drawable= getResources().getDrawable(R.mipmap.satisfied_selected);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                satisfied.setCompoundDrawables(drawable,null,null,null);
                Drawable drawable1= getResources().getDrawable(R.mipmap.unsatisfied);
                drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
                unsatisfied.setCompoundDrawables(drawable1,null,null,null);
                initProblemList(2);
                evaluationType = 2;
                break;
            case R.id.ll_unsatisfied:
                llunsatisfied.setBackgroundResource(R.drawable.bg_selected_evaluation);
                llsatisfied.setBackgroundResource(R.drawable.bg_unselect_evaluation);
                unsatisfied.setTextColor(getResources().getColor(R.color.orange_yellow_ff7e00));
                satisfied.setTextColor(getResources().getColor(R.color.gray_6a));
                Drawable drawable2= getResources().getDrawable(R.mipmap.satisfied);
                drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                satisfied.setCompoundDrawables(drawable2,null,null,null);
                Drawable drawable3= getResources().getDrawable(R.mipmap.unsatisfied_selected);
                drawable3.setBounds(0, 0, drawable3.getMinimumWidth(), drawable3.getMinimumHeight());
                unsatisfied.setCompoundDrawables(drawable3,null,null,null);
                initProblemList(0);
                evaluationType = 0;
                break;
            case R.id.btn_commit_evaluation:
                int i = 0;
                if (itemList.size()!=0){
                    for (ProblemItem item : itemList){
                        if (item.isChecked()){
                            String temp = item.getId()+",";
                            problemType.append(temp);
                            i++;
                        }
                    }
                    if (i==0){
                        UIUtils.showToastSafe("请选择问题类型");
                        return;
                    }
                }
                if (unname.isChecked()){
                    anonymousType = 1;
                }else {
                    anonymousType = 0;
                }
                loading("请稍侯");
                getRequestPresenter().addEvaluation(orderDetail.getOrderNo(), orderDetail.getCustomerId(), PublicCache.loginPurchase.getLoginUserId(), orderDetail.getItems().get(0).getDriverId(), evaluationType, evaluationContent.getText().toString().trim(), anonymousType, orderDetail.getCustomerAddrId(), problemType.toString(), new RequestCallback<AddCategory>() {
                    @Override
                    protected void onSuc(AddCategory body) {
                        UIUtils.showToastSafe(body.getMsg());
                        loadingDimss();
                        finish();
                    }

                    @Override
                    public void onFailed(int errCode, String msg) {
                        UIUtils.showToastSafe(msg);
                        loadingDimss();
                    }
                });
                break;
                default:break;
        }
    }


    public void initProblemList(int type){
        itemList.clear();
        getRequestPresenter().getLogisticsProblem(type,PublicCache.loginPurchase.getLoginUserId(), new RequestCallback<ProblemList>() {
            @Override
            protected void onSuc(ProblemList body) {
                if (body.getData().getList().size()!=0){
                    for (ProblemList.DataBean.ListBean bean : body.getData().getList() ){
                        itemList.add(new ProblemItem(bean.getEntityId(),bean.getProblem(),false));
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void initDetailList(){
        itemList.clear();
        RequestPresenter2.getInstance().findShippingEvaluationDetail(orderDetail.getItems().get(0).getDriverId(), orderDetail.getExtOrderId(), new RequestCallback<EvaluationDetail>() {
            @Override
            protected void onSuc(EvaluationDetail body) {
                if (body.getData().getItems().getEvaType()==2){
                    llsatisfied.setBackgroundResource(R.drawable.bg_selected_evaluation);
                    llunsatisfied.setVisibility(View.GONE);
                    satisfied.setTextColor(getResources().getColor(R.color.orange_yellow_ff7e00));
                    unsatisfied.setTextColor(getResources().getColor(R.color.gray_6a));
                    Drawable drawable= getResources().getDrawable(R.mipmap.satisfied_selected);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    satisfied.setCompoundDrawables(drawable,null,null,null);
                    Drawable drawable1= getResources().getDrawable(R.mipmap.unsatisfied);
                    drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
                    unsatisfied.setCompoundDrawables(drawable1,null,null,null);
                }else {
                    llunsatisfied.setBackgroundResource(R.drawable.bg_selected_evaluation);
                    llsatisfied.setVisibility(View.GONE);
                    unsatisfied.setTextColor(getResources().getColor(R.color.orange_yellow_ff7e00));
                    satisfied.setTextColor(getResources().getColor(R.color.gray_6a));
                    Drawable drawable2= getResources().getDrawable(R.mipmap.satisfied);
                    drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                    satisfied.setCompoundDrawables(drawable2,null,null,null);
                    Drawable drawable3= getResources().getDrawable(R.mipmap.unsatisfied_selected);
                    drawable3.setBounds(0, 0, drawable3.getMinimumWidth(), drawable3.getMinimumHeight());
                    unsatisfied.setCompoundDrawables(drawable3,null,null,null);
                }

                llAddEvaluation.setVisibility(View.GONE);
                llEvaluationDetail.setVisibility(View.VISIBLE);
                detailContent.setText(body.getData().getItems().getEvaContent());
                tips.setVisibility(View.GONE);
                evaluationReason.setText("评价原因");

                if (body.getData().getItems().getTagList().size()!=0){
                    for (EvaluationDetail.DataBean.ItemsBean.TagListBean bean : body.getData().getItems().getTagList()){
                        itemList.add(new ProblemItem(bean.getEntityId(),bean.getProblem(),true));
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafe(msg);
            }
        });
    }

}