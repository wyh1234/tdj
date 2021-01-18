package cn.com.taodaji.ui.activity.myself;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.apkfuns.logutils.LogUtils;
import com.base.glide.GlideImageView;
import com.base.glide.ImageLoaderUtils;
import com.base.retrofit.RequestCallback;
import com.base.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.Privilegeinfo;
import cn.com.taodaji.model.entity.SupplierAnnalFeeInfo;
import cn.com.taodaji.ui.activity.homepage.AttractInvestmentActivity;
import cn.com.taodaji.ui.activity.homepage.SpecialActivitiesActivity;
import cn.com.taodaji.ui.pay.YearMoneyPayActivity;
import cn.com.taodaji.viewModel.adapter.MySelfSupYearAdapter;
import cn.com.taodaji.viewModel.adapter.MySelfSupYearPayWayAdapter;
import tools.activity.SimpleToolbarActivity;

public class MySelfSupYearMoney extends SimpleToolbarActivity implements MySelfSupYearPayWayAdapter.OnItemClickListener,MySelfSupYearAdapter.OnItemClickListener,View.OnClickListener {
    private RecyclerView recyclerView,recyclerView1;
    private MySelfSupYearAdapter mySelfSupYearAdapter;
    private MySelfSupYearPayWayAdapter mySelfSupYearPayWayAdapter;
    private Button btn;
    private CheckBox radio;
    private TextView tv_xy;
    private LinearLayout ll_attention_shop,ll_good_enshrine;
    private List<Privilegeinfo> list=new ArrayList();
    private List<Privilegeinfo> list1=new ArrayList();
    private View mainView;
    private RelativeLayout rl_tq;
    private String type;
    private String openCycle;
    private String payCount;
    private TextView shop_name;
    private GlideImageView myself_headportrait;
    private LinearLayout ll_attention;
    public String getOpenCycle() {
        return openCycle;
    }

    public void setOpenCycle(String openCycle) {
        this.openCycle = openCycle;
    }

    public String getPayCount() {
        return payCount;
    }

    public void setPayCount(String payCount) {
        this.payCount = payCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private TextView tv_good_enshrine_count,tv_attention_shop_count;
    private SupplierAnnalFeeInfo body;

    public SupplierAnnalFeeInfo getBody() {
        return body;
    }

    public void setBody(SupplierAnnalFeeInfo body) {
        this.body = body;
    }

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor(R.color.statusBarColor);
        setToolBarColor(R.color.statusBarColor);
        simple_title.setText("平台年费");
    }

    @Override
    protected void initMainView() {
        mainView = ViewUtils.getLayoutViewMatch(this, R.layout.myselfsup_year_money_layout);
        body_other.addView(mainView);
        recyclerView= ViewUtils.findViewById(mainView, R.id.recyclerView);
        btn= ViewUtils.findViewById(mainView, R.id.btn);
        radio= ViewUtils.findViewById(mainView, R.id.radio);
        rl_tq= ViewUtils.findViewById(mainView, R.id.rl_tq);
        btn.setOnClickListener(this);
        rl_tq.setOnClickListener(this);
        shop_name=ViewUtils.findViewById(mainView, R.id.shop_name);
        myself_headportrait=ViewUtils.findViewById(mainView, R.id.myself_headportrait);
        ll_attention_shop= ViewUtils.findViewById(mainView, R.id.ll_attention_shop);
        ll_attention= ViewUtils.findViewById(mainView, R.id.ll_attention);
        ll_attention_shop.setOnClickListener(this);
        ll_good_enshrine= ViewUtils.findViewById(mainView, R.id.ll_good_enshrine);
        ll_good_enshrine.setOnClickListener(this);
        tv_attention_shop_count= ViewUtils.findViewById(mainView, R.id.tv_attention_shop_count);
        tv_good_enshrine_count= ViewUtils.findViewById(mainView, R.id.tv_good_enshrine_count);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView1= ViewUtils.findViewById(mainView, R.id.recyclerView1);
        tv_xy= ViewUtils.findViewById(mainView, R.id.tv_xy);
        tv_xy.setOnClickListener(this);
        recyclerView1.setLayoutManager(new GridLayoutManager(this, 2));

        list.add(new Privilegeinfo("VIP尊贵身份",R.mipmap.vip_bg));
        list.add(new Privilegeinfo("一对一培训",R.mipmap.peixun_bg));
        list.add(new Privilegeinfo("橱窗数多10个",R.mipmap.guangao_bg));
        list.add(new Privilegeinfo("优先租赁仓库",R.mipmap.zulin_bg));
        list.add(new Privilegeinfo("专业分析服务",R.mipmap.ywfx_bg));
        list.add(new Privilegeinfo("赠送一周启动页",R.mipmap.zqzw_bg));


        list1.add(new Privilegeinfo("专卖店标识",R.mipmap.dianpu_bg));
        list1.add(new Privilegeinfo("培训服务",R.mipmap.peixun_bg));
        mySelfSupYearAdapter=new MySelfSupYearAdapter(this,list1);
        recyclerView.setAdapter(mySelfSupYearAdapter);
        mySelfSupYearAdapter.setOnItemClickListener(this);
        tv_good_enshrine_count.setTextColor(getResources().getColor(R.color.year_money_colors));
        tv_attention_shop_count.setTextColor(getResources().getColor(R.color.gray_6c));
        if (getIntent().getStringExtra("type")!=null){
            setType(getIntent().getStringExtra("type"));
        }else {
            setType("ZM");
        }


        shop_name.setText(PublicCache.loginSupplier.getStoreName());
        ImageLoaderUtils.loadImage(myself_headportrait, PublicCache.loginSupplier.getStorePics());

    }
    @Override
    protected void initData() {
        if (getIntent().getStringExtra("IsSelected")!=null){
            ll_attention.setVisibility(View.GONE);

        }

        getSupplierAnnalFee(getIntent().getStringExtra("storeId"));
        LogUtils.i(getIntent().getStringExtra("storeId"));



    }

    private void getSupplierAnnalFee(String storeId) {

        loading();
        getRequestPresenter().getSupplierAnnalFee(PublicCache.site_login,storeId, new RequestCallback<SupplierAnnalFeeInfo>() {
            @Override
            public void onSuc(SupplierAnnalFeeInfo body) {
                loadingDimss();
                if (body.getData()!=null){
                    body.getData().getList().get(0).setB(true);
                    setBody(body);
                    mySelfSupYearPayWayAdapter=new MySelfSupYearPayWayAdapter(MySelfSupYearMoney.this,body.getData().getList(),true);
                    mySelfSupYearPayWayAdapter.setOnItemClickListener(MySelfSupYearMoney.this);
                    mySelfSupYearPayWayAdapter.setType(getType());
                    mySelfSupYearPayWayAdapter.setDisFlag(body.getData().isDisFlag());
                    recyclerView1.setAdapter(mySelfSupYearPayWayAdapter);
                    if (body.getData().isDisFlag()){
                        btn.setText( "立即支付"+String.format("%.2f",body.getData().getList().get(0).getSpecDisAmount()));
                        setPayCount(String.format("%.2f",body.getData().getList().get(0).getSpecDisAmount()));
                    }else {
                        btn.setText( "立即支付"+String.format("%.2f",body.getData().getList().get(0).getSpecAmount()));
                        setPayCount(String.format("%.2f",body.getData().getList().get(0).getSpecAmount()));
                    }

                    setOpenCycle(body.getData().getList().get(0).getOpenCycle());

                }

            }

            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
                loadingDimss();
            }
        });
    }



    @Override
    public void OnItemClick(View view, int position) {
        for (SupplierAnnalFeeInfo.DataBean.ListBean info:getBody().getData().getList()){
            info.setB(false);
        }
        getBody().getData().getList().get(position).setB(true);
        setOpenCycle( getBody().getData().getList().get(position).getOpenCycle());
        if (mySelfSupYearPayWayAdapter.getType().equals("ZM")){
            if (body.getData().isDisFlag()){
                setPayCount( String.format("%.2f",body.getData().getList().get(position).getSpecDisAmount()));
                btn.setText( "立即支付"+String.format("%.2f",body.getData().getList().get(position).getSpecDisAmount()));
            }else {
                setPayCount( String.format("%.2f",body.getData().getList().get(position).getSpecAmount()));
                btn.setText( "立即支付"+String.format("%.2f",body.getData().getList().get(position).getSpecAmount()));
            }

        }else {
            if (body.getData().isDisFlag()){
                setPayCount( String.format("%.2f",body.getData().getList().get(position).getVipDisAmount()));
                btn.setText( "立即支付"+String.format("%.2f",body.getData().getList().get(position).getVipDisAmount()));
            }else {
                setPayCount( String.format("%.2f",body.getData().getList().get(position).getVipAmount()));
                btn.setText( "立即支付"+String.format("%.2f",body.getData().getList().get(position).getVipAmount()));
            }
        }



        mySelfSupYearPayWayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_attention_shop:

                for (SupplierAnnalFeeInfo.DataBean.ListBean info:getBody().getData().getList()){
                    info.setB(false);
                }
                getBody().getData().getList().get(0).setB(true);

                mySelfSupYearAdapter.setList(list);

                setType("QJ");
                mySelfSupYearPayWayAdapter.setType(getType());
                mySelfSupYearPayWayAdapter.notifyDataSetChanged();

                mySelfSupYearAdapter.notifyDataSetChanged();

                tv_attention_shop_count.setTextColor(getResources().getColor(R.color.year_money_colors));
                tv_good_enshrine_count.setTextColor(getResources().getColor(R.color.gray_6c));
                if (body.getData().isDisFlag()){
                    btn.setText( "立即支付"+String.format("%.2f",body.getData().getList().get(0).getVipDisAmount()));
                    setPayCount(String.format("%.2f",body.getData().getList().get(0).getVipDisAmount()));
                }else {
                    btn.setText( "立即支付"+String.format("%.2f",body.getData().getList().get(0).getVipAmount()));
                    setPayCount(String.format("%.2f",body.getData().getList().get(0).getVipAmount()));
                }

                break;

            case R.id.ll_good_enshrine:
                for (SupplierAnnalFeeInfo.DataBean.ListBean info:getBody().getData().getList()){
                    info.setB(false);
                }
                getBody().getData().getList().get(0).setB(true);
                setType("ZM");
                mySelfSupYearPayWayAdapter.setType(getType());
                mySelfSupYearPayWayAdapter.notifyDataSetChanged();

                mySelfSupYearAdapter.setList(list1);
                mySelfSupYearAdapter.notifyDataSetChanged();
                tv_good_enshrine_count.setTextColor(getResources().getColor(R.color.year_money_colors));
                tv_attention_shop_count.setTextColor(getResources().getColor(R.color.gray_6c));
                if (body.getData().isDisFlag()){
                    btn.setText( "立即支付"+String.format("%.2f",body.getData().getList().get(0).getSpecDisAmount()));
                    setPayCount(String.format("%.2f",body.getData().getList().get(0).getSpecDisAmount()));
                }else {
                    btn.setText( "立即支付"+String.format("%.2f",body.getData().getList().get(0).getSpecAmount()));
                    setPayCount(String.format("%.2f",body.getData().getList().get(0).getSpecAmount()));
                }

                break;

            case R.id.btn:
                if (!radio.isChecked()){
                    Toast.makeText(this,"请勾选淘大集服务协议",Toast.LENGTH_LONG).show();
                    return;

                }
                Intent intent2=new Intent(this, YearMoneyPayActivity.class);
                intent2.putExtra("type",getType());
                intent2.putExtra("openCycle",getOpenCycle());
                intent2.putExtra("payCount",getPayCount());
                intent2.putExtra("storeId",getIntent().getStringExtra("storeId"));
                startActivity(intent2);
                break;
            case R.id.rl_tq:
                if (mySelfSupYearAdapter.getItemCount()==3){
                    Intent intent1=new Intent(this,FlagshipStroeActivity.class);
                    intent1.putExtra("qy","qy");
                    startActivity(intent1);
                }else {

                    Intent intent=new Intent(this,FlagshipStroeActivity.class);

                    startActivity(intent);
                }



                break;
            case  R.id.tv_xy:
                Intent intent1 = new Intent(this, SpecialActivitiesActivity.class);
                intent1.putExtra("url", "https://www.taodaji.com.cn/h5/help/s/app_service.html");
                intent1.putExtra("name", "服务条款");
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void OnItemClick1(View view, int position) {
        if (mySelfSupYearAdapter.getItemCount()==3){
            Intent intent1=new Intent(this,FlagshipStroeActivity.class);
            intent1.putExtra("qy","qy");
            startActivity(intent1);
        }else {

            Intent intent=new Intent(this,FlagshipStroeActivity.class);
            startActivity(intent);
        }

    }
}
