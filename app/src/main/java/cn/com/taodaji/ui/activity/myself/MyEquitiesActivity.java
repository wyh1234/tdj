package cn.com.taodaji.ui.activity.myself;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.glide.GlideImageView;
import com.base.glide.ImageLoaderUtils;
import com.base.retrofit.RequestCallback;
import com.base.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.Privilegeinfo;
import cn.com.taodaji.model.entity.PrivilegesInfo;
import cn.com.taodaji.model.entity.SupplierAnnalFeeInfo;
import cn.com.taodaji.viewModel.adapter.MySelfSupYearAdapter;
import cn.com.taodaji.viewModel.adapter.MySelfSupYearPayWayAdapter;
import tools.activity.SimpleToolbarActivity;

public class MyEquitiesActivity extends SimpleToolbarActivity implements View.OnClickListener,MySelfSupYearAdapter.OnItemClickListener {
    private View mainView;
    private RecyclerView recyclerView;
    private ImageView iv_equities,my_nameCard;
    private MySelfSupYearAdapter mySelfSupYearAdapter;
    private List<Privilegeinfo> list=new ArrayList();
    private TextView shop_name,shop_time;
    private GlideImageView myself_headportrait;
    private String type;
    private RelativeLayout rl_tq;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor(R.color.statusBarColor);
        setToolBarColor(R.color.statusBarColor);
        simple_title.setText("我的权益");
    }

    @Override
    protected void initMainView() {
        mainView = ViewUtils.getLayoutViewMatch(this, R.layout.my_equities_layout);
        body_other.addView(mainView);
        recyclerView= ViewUtils.findViewById(mainView, R.id.recyclerView);
        rl_tq= ViewUtils.findViewById(mainView, R.id.rl_tq);
        rl_tq.setOnClickListener(this);
        iv_equities= ViewUtils.findViewById(mainView, R.id.iv_equities);
        my_nameCard= ViewUtils.findViewById(mainView, R.id.my_nameCard);
        shop_time= ViewUtils.findViewById(mainView, R.id.shop_time);
        shop_name=ViewUtils.findViewById(mainView, R.id.shop_name);
        myself_headportrait=ViewUtils.findViewById(mainView, R.id.myself_headportrait);
        iv_equities.setOnClickListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));



        shop_name.setText(PublicCache.loginSupplier.getStoreName());
        ImageLoaderUtils.loadImage(myself_headportrait, PublicCache.loginSupplier.getStorePics());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_equities:
                if (getIntent().getStringExtra("IsSelected")!=null){
                    Intent intent_rl_syy=new Intent(this,MySelfSupYearMoney.class);
                        intent_rl_syy.putExtra("storeId",PublicCache.loginSupplier.getStore()+"");
                    startActivity(intent_rl_syy);
                }else {
                    Intent intent=new Intent(this,YearMoneyRenewActivity.class);
                    intent.putExtra("type",getType());
                    startActivity(intent);
                }

                break;
            case R.id.rl_tq:
                Intent intent1=new Intent(this,FlagshipStroeActivity.class);
                if (getIntent().getStringExtra("IsSelected")!=null){
                    intent1.putExtra("IsSelected","IsSelected");
                }else {
                    intent1.putExtra("type",getType());
                }
                if (getType().equals("ZM")){

                    intent1.putExtra("qy","qy");
                    intent1.putExtra("XF","XF");
                    startActivity(intent1);
                }else {

                    intent1.putExtra("XF","XF");
                    startActivity(intent1);
                }
                break;
        }
    }
    @Override
    protected void initData() {

        getSupplierAnnalFeePrivileges(PublicCache.loginSupplier.getStore()+"");



    }

    private void getSupplierAnnalFeePrivileges(String storeId) {
        loading();
        getRequestPresenter().getSupplierAnnalFeePrivileges(PublicCache.site_login,storeId, new RequestCallback<PrivilegesInfo>() {
            @Override
            public void onSuc(PrivilegesInfo body) {
                loadingDimss();
                if (body.getData()!=null){
                    if (body.getData().getSotreType()==2){
                        setType("ZM");
                        list.add(new Privilegeinfo("专卖店标识",R.mipmap.dianpu_bg));
                        list.add(new Privilegeinfo("培训服务",R.mipmap.peixun_bg));
                        my_nameCard.setImageResource(R.mipmap.zhuamaidian_one);
                    }else {
                        setType("QJ");
                        list.add(new Privilegeinfo("VIP尊贵身份",R.mipmap.vip_bg));
                        list.add(new Privilegeinfo("一对一培训",R.mipmap.peixun_bg));
                        list.add(new Privilegeinfo("橱窗数多10个",R.mipmap.guangao_bg));
                        list.add(new Privilegeinfo("优先租赁仓库",R.mipmap.zulin_bg));
                        list.add(new Privilegeinfo("专业分析服务",R.mipmap.ywfx_bg));
                        list.add(new Privilegeinfo("赠送一周启动页",R.mipmap.zqzw_bg));
                        my_nameCard.setImageResource(R.mipmap.qijianduan);
                    }
                    shop_time.setText("服务时间："+body.getData().getStartTime()+"到"+body.getData().getEndTime());
                    mySelfSupYearAdapter=new MySelfSupYearAdapter(MyEquitiesActivity.this,list);
                    mySelfSupYearAdapter.setOnItemClickListener(MyEquitiesActivity.this);
                    recyclerView.setAdapter(mySelfSupYearAdapter);
                }

            }

            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
                loadingDimss();
            }
        });

    }

    @Override
    public void OnItemClick1(View view, int position) {
        Intent intent1=new Intent(this,FlagshipStroeActivity.class);
        if (getIntent().getStringExtra("IsSelected")!=null){
            intent1.putExtra("IsSelected","IsSelected");
        }else {
            intent1.putExtra("type",getType());
        }
        if (getType().equals("ZM")){

            intent1.putExtra("qy","qy");
            intent1.putExtra("XF","XF");
            startActivity(intent1);
        }else {

            intent1.putExtra("XF","XF");
            startActivity(intent1);
        }
    }
}
