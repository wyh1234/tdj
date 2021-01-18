package cn.com.taodaji.ui.activity.myself;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.FeeTips;
import cn.com.taodaji.model.entity.PayManage;
import cn.com.taodaji.ui.activity.advertisement.MarketingManageMentActivity;
import cn.com.taodaji.ui.activity.advertisement.adapter.PayManageAdapter;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.ui.activity.integral.tools.ShowLoadingDialog;
import cn.com.taodaji.ui.activity.packingCash.PackingCashCurrentActivity;
import cn.com.taodaji.ui.activity.penalty.PunishListActivity;
import cn.com.taodaji.ui.activity.scanner.ScannerFeeActivity;
import tools.activity.DataBaseActivity;
import tools.animation.ScrollLinearLayoutManager;
import tools.statusbar.Eyes;

import static com.base.utils.UIUtils.getContext;

public class PayManageActivity extends DataBaseActivity implements PayManageAdapter.OnItemClickListener {
    @BindView(R.id.intergral_toolbar)
    Toolbar intergral_toolbar;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.tv_right)
    TextView tv_right;
    @BindView(R.id.all_reccyclerView)
    RecyclerView all_reccyclerView;
    private PayManageAdapter payManageAdapter;
    private List<PayManage.DataBean> mData=new ArrayList<>();

    @OnClick({R.id.btn_back,R.id.tv_right})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.tv_right:
                Intent intent=new Intent(this,PaymentListActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.pay_manage_layout);
    }

    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        Eyes.setStatusBarColor(this, UIUtils.getColor(this, R.color.blue_2898eb));
        tv_title.setTextColor(getResources().getColor(R.color.white));
        btn_back.setVisibility(View.VISIBLE);
        btn_back.setImageResource(R.mipmap.left_arrow_white);
        intergral_toolbar.setBackgroundColor(getResources().getColor(R.color.blue_2898eb
        ));
        tv_title.setText("缴费中心");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("缴费清单");
        Drawable drawable = getResources().getDrawable(R.mipmap.jaiofeiqingdan);// 找到资源图片
        // 这一步必须要做，否则不会显示。
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置图片宽高
        tv_right.setCompoundDrawables(drawable, null, null, null);// 设置到控件中

        tv_right.setCompoundDrawablePadding(10);
        ScrollLinearLayoutManager layout = new ScrollLinearLayoutManager(this, 2);
        all_reccyclerView.setLayoutManager(layout);
        payManageAdapter=new PayManageAdapter(mData,this);
        payManageAdapter.setOnItemClickListener(this);
        all_reccyclerView.setAdapter(payManageAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        ShowLoadingDialog.showLoadingDialog(this);
        getRequestPresenter().payManage(new HashMap<>(), new RequestCallback<PayManage>() {
            @Override
            protected void onSuc(PayManage body) {
                ShowLoadingDialog.close();
                if (!ListUtils.isEmpty(body.getData())){
                    for (int i=0;i<body.getData().size();i++){
                        PayManage.DataBean  dataBean1=new PayManage.DataBean();
                        dataBean1.setType(1);
                        dataBean1.setType_url(body.getData().get(i).getType_url());
                        mData.add(dataBean1);
                        for (int j=0;j<body.getData().get(i).getList().size();j++){
                            PayManage.DataBean  dataBean=new PayManage.DataBean();
                            dataBean.setType(2);
                            dataBean.setItem_content(body.getData().get(i).getList().get(j).getItem_content());
                            dataBean.setItem_name(body.getData().get(i).getList().get(j).getItem_name());
                            dataBean.setItem_url(body.getData().get(i).getList().get(j).getItem_url());
                            dataBean.setItem_type(body.getData().get(i).getList().get(j).getItem_type());
                            mData.add(dataBean);
                        }

                    }
                    LogUtils.e(mData);
                    payManageAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailed(int errCode, String msg) {
                ShowLoadingDialog.close();

            }
        });
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Intent intent=null;
        if (mData.get(position).getItem_type().equals("FINE")){
             intent=new Intent(this, PunishListActivity.class);
        }else if (mData.get(position).getItem_type().equals("ADVERTISEMENT")){
             intent=new Intent(this, MarketingManageMentActivity.class);
        }else if (mData.get(position).getItem_type().equals("ANNAL")){
         FeeTips.DataBean.InfoBean  infoBean= (FeeTips.DataBean.InfoBean) getIntent().getSerializableExtra("feeTips");
            if (infoBean.getServStatus() == 2) {//服务中
                intent = new Intent(this, MyEquitiesActivity.class);
            } else if (infoBean.getServStatus() == 3) {

            } else {
                intent = new Intent(this, MySelfSupYearMoney.class);
                intent.putExtra("storeId", PublicCache.loginSupplier.getStore() + "");
            }
            if (infoBean.getIsSelected() == 1) {
                intent = new Intent(this, MySelfSupYearMoney.class);
                intent.putExtra("storeId", PublicCache.loginSupplier.getStore() + "");
            } else if (infoBean.getIsSelected() == 0) {
                intent = new Intent(this, MySelfSupYearMoney.class);
                intent.putExtra("IsSelected", "0");
                intent.putExtra("type", infoBean.getStoreType() == 2 ? "ZM" : "QJ");
                intent.putExtra("storeId", PublicCache.loginSupplier.getStore() + "");
            }
        }else if(mData.get(position).getItem_type().equals("PACK")){
            intent = new Intent(getContext(), PackingCashCurrentActivity.class);
        }else if(mData.get(position).getItem_type().equals("RECEIV")){
            intent = new Intent(getBaseActivity(), PickupServiceActivity.class);
        }else if (mData.get(position).getItem_type().equals("SWEEPCODE")){
            intent = new Intent(getBaseActivity(), ScannerFeeActivity.class);
        }
        if (intent!=null){
            startActivity(intent);
        }


    }
}
