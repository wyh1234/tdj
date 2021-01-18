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
import cn.com.taodaji.model.entity.YearPayWayInfo;
import cn.com.taodaji.ui.activity.homepage.SpecialActivitiesActivity;
import cn.com.taodaji.ui.pay.YearMoneyPayActivity;
import cn.com.taodaji.viewModel.adapter.MySelfSupYearAdapter;
import cn.com.taodaji.viewModel.adapter.MySelfSupYearPayWayAdapter;
import tools.activity.SimpleToolbarActivity;

public class YearMoneyRenewActivity extends SimpleToolbarActivity implements View.OnClickListener,MySelfSupYearPayWayAdapter.OnItemClickListener,MySelfSupYearAdapter.OnItemClickListener {
    private RecyclerView recyclerView,recyclerView1;
    private MySelfSupYearAdapter mySelfSupYearAdapter;
    private MySelfSupYearPayWayAdapter mySelfSupYearPayWayAdapter;
    private Button btn;
    private List<Privilegeinfo> list=new ArrayList();
    private View mainView;
    private ImageView my_nameCard;
    private SupplierAnnalFeeInfo body;
    private String type;
    private String openCycle;
    private String payCount;
    private CheckBox radio;
    private RelativeLayout rl_tq;
    private TextView shop_name;
    private GlideImageView myself_headportrait;
    private TextView tv_xy;
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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
        if (getIntent().getStringExtra("type").equals("ZM")){
            simple_title.setText("专卖店续费");
        }else {
            simple_title.setText("旗舰店续费");
        }

    }

    @Override
    protected void initMainView() {
        mainView = ViewUtils.getLayoutViewMatch(this, R.layout.year_money_renew_layout);
        body_other.addView(mainView);
        recyclerView= ViewUtils.findViewById(mainView, R.id.recyclerView);
        btn= ViewUtils.findViewById(mainView, R.id.btn);
        btn.setOnClickListener(this);
        rl_tq= ViewUtils.findViewById(mainView, R.id.rl_tq);
        rl_tq.setOnClickListener(this);
        radio= ViewUtils.findViewById(mainView, R.id.radio);
        shop_name=ViewUtils.findViewById(mainView, R.id.shop_name);
        myself_headportrait=ViewUtils.findViewById(mainView, R.id.myself_headportrait);
        my_nameCard= ViewUtils.findViewById(mainView, R.id.my_nameCard);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView1= ViewUtils.findViewById(mainView, R.id.recyclerView1);
        tv_xy= ViewUtils.findViewById(mainView, R.id.tv_xy);
        tv_xy.setOnClickListener(this);
        recyclerView1.setLayoutManager(new GridLayoutManager(this, 2));

        if (getIntent().getStringExtra("type").equals("ZM")){
            list.add(new Privilegeinfo("专卖店标识",R.mipmap.dianpu_bg));
            list.add(new Privilegeinfo("培训服务",R.mipmap.peixun_bg));
            my_nameCard.setImageResource(R.mipmap.zhuamaidian_one);


        }else {
            list.add(new Privilegeinfo("VIP尊贵身份",R.mipmap.vip_bg));
            list.add(new Privilegeinfo("一对一培训",R.mipmap.peixun_bg));
            list.add(new Privilegeinfo("橱窗数多10个",R.mipmap.guangao_bg));
            list.add(new Privilegeinfo("优先租赁仓库",R.mipmap.zulin_bg));
            list.add(new Privilegeinfo("专业分析服务",R.mipmap.ywfx_bg));
            list.add(new Privilegeinfo("赠送一周启动页",R.mipmap.zqzw_bg));
            my_nameCard.setImageResource(R.mipmap.qijianduan);
        }

        mySelfSupYearAdapter=new MySelfSupYearAdapter(this,list);
        recyclerView.setAdapter(mySelfSupYearAdapter);
        mySelfSupYearAdapter.setOnItemClickListener(this);
        setType(getIntent().getStringExtra("type"));

        shop_name.setText(PublicCache.loginSupplier.getStoreName());
        ImageLoaderUtils.loadImage(myself_headportrait, PublicCache.loginSupplier.getStorePics());
    }
    @Override
    protected void initData() {

        getSupplierAnnalFee(PublicCache.loginSupplier.getStore()+"");



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
                    mySelfSupYearPayWayAdapter=new MySelfSupYearPayWayAdapter(YearMoneyRenewActivity.this,body.getData().getList(),false);
                    mySelfSupYearPayWayAdapter.setOnItemClickListener(YearMoneyRenewActivity.this);
                        mySelfSupYearPayWayAdapter.setType(getType());
                    recyclerView1.setAdapter(mySelfSupYearPayWayAdapter);
                    if (getType().equals("ZM")){
                        if (body.getData().isDisFlag()){
                            setPayCount(String.format("%.2f",body.getData().getList().get(0).getSpecDisAmount()));
                            btn.setText( "立即支付"+String.format("%.2f",body.getData().getList().get(0).getSpecDisAmount()));
                        }else {
                            setPayCount(String.format("%.2f",body.getData().getList().get(0).getSpecAmount()));
                            btn.setText( "立即支付"+String.format("%.2f",body.getData().getList().get(0).getSpecAmount()));
                        }


                    }else {
                        if (body.getData().isDisFlag()){
                            setPayCount(String.format("%.2f",body.getData().getList().get(0).getVipDisAmount()));
                            btn.setText( "立即支付"+String.format("%.2f",body.getData().getList().get(0).getVipDisAmount()));
                        }else {
                            setPayCount(String.format("%.2f",body.getData().getList().get(0).getVipAmount()));
                            btn.setText( "立即支付"+String.format("%.2f",body.getData().getList().get(0).getVipAmount()));
                        }

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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn:
                if (!radio.isChecked()){
                    Toast.makeText(this,"请勾选淘大集服务协议",Toast.LENGTH_LONG).show();
                    return;

                }
                Intent intent2=new Intent(this, YearMoneyPayActivity.class);
                intent2.putExtra("type",getType());
                intent2.putExtra("openCycle",getOpenCycle());
                intent2.putExtra("payCount",getPayCount());
                intent2.putExtra("storeId",PublicCache.loginSupplier.getStore()+"");
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

    @Override
    public void OnItemClick(View view, int position) {
        for (SupplierAnnalFeeInfo.DataBean.ListBean info:getBody().getData().getList()){
            info.setB(false);
        }
        getBody().getData().getList().get(position).setB(true);
        if (getType().equals("ZM")){
            if (body.getData().isDisFlag()){
                setPayCount( String.format("%.2f",body.getData().getList().get(position).getSpecDisAmount()));
                btn.setText( "立即支付"+String.format("%.2f",body.getData().getList().get(position).getSpecDisAmount()));
            }else {
                setPayCount(String.format("%.2f",body.getData().getList().get(position).getSpecAmount()));
                btn.setText( "立即支付"+String.format("%.2f",body.getData().getList().get(position).getSpecAmount()));
            }

        }else {

            if (body.getData().isDisFlag()){
                setPayCount(String.format("%.2f",body.getData().getList().get(position).getVipDisAmount()));
                btn.setText( "立即支付"+String.format("%.2f",body.getData().getList().get(position).getVipDisAmount()));
            }else {
                setPayCount(String.format("%.2f",body.getData().getList().get(position).getVipAmount()));
                btn.setText( "立即支付"+String.format("%.2f",body.getData().getList().get(position).getVipAmount()));
            }
        }
        setOpenCycle( getBody().getData().getList().get(position).getOpenCycle());



        mySelfSupYearPayWayAdapter.notifyDataSetChanged();
    }
}
