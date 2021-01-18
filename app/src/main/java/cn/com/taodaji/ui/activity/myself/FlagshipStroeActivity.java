package cn.com.taodaji.ui.activity.myself;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.base.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.FlagshipStroeInfo;
import cn.com.taodaji.model.entity.Privilegeinfo;
import cn.com.taodaji.viewModel.adapter.FlagshipStroeAdapter;
import tools.activity.SimpleToolbarActivity;
import tools.animation.RecyclerViewDivider;

public class FlagshipStroeActivity extends SimpleToolbarActivity implements View.OnClickListener {
    private View mainView;
    private RecyclerView recyclerView;
    private FlagshipStroeAdapter flagshipStroeAdapter;
    private ImageView iv;
    private Button btn;
    private List<FlagshipStroeInfo> list=new ArrayList();
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor(R.color.statusBarColor);
        setToolBarColor(R.color.statusBarColor);
        if (getIntent().getStringExtra("qy")!=null){
            simple_title.setText("专卖店服务");
        }else {
            simple_title.setText("旗舰店服务");
        }

    }

    @Override
    protected void initMainView() {
        mainView = ViewUtils.getLayoutViewMatch(this, R.layout.flagship_stroe_layout);
        body_other.addView(mainView);
        btn= ViewUtils.findViewById(mainView, R.id.btn);
        btn.setOnClickListener(this);
        recyclerView= ViewUtils.findViewById(mainView, R.id.recyclerView);
        iv= ViewUtils.findViewById(mainView, R.id.iv);

        if (getIntent().getStringExtra("qy")==null){
            iv.setImageResource(R.mipmap.huangguan_bg);
            list.add(new FlagshipStroeInfo("VIP尊贵身份","享受店铺vip标志，尽显尊贵身份"));
            list.add(new FlagshipStroeInfo("一对一培训","享受APP操作培训；分拣培训；售后培训"));
            list.add(new FlagshipStroeInfo("橱窗数多10个","多同品类销售商10个SKU橱窗位"));
            list.add(new FlagshipStroeInfo("优先租赁仓库","尊享优先仓库；尊享优惠政策"));
            list.add(new FlagshipStroeInfo("专业分析服务","尊享定期业务数据分析培训"));
            list.add(new FlagshipStroeInfo("每月赠送一周启动页广告","每月赠送一周启动页广告"));
            list.add(new FlagshipStroeInfo("每月免费赠送5个商品置顶一周","每月免费赠送5个商品置顶一周"));
            list.add(new FlagshipStroeInfo("可同时经营两个品类","可同时经营两个品类"));


        }else {
            iv.setImageResource(R.mipmap.jiaopei_bg);
            list.add(new FlagshipStroeInfo("专享专卖店标识","独有专享店标识"));
            list.add(new FlagshipStroeInfo("专享培训服务","享受APP操作培训；分拣培训；售后培训"));
        }
        LinearLayoutManager layout = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false);
        recyclerView.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL, 2, this.getResources().getColor(R.color.gray_f5)));

        recyclerView.setLayoutManager(layout);
        flagshipStroeAdapter=new FlagshipStroeAdapter(this,list);
        recyclerView.setAdapter(flagshipStroeAdapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn:
                if (getIntent().getStringExtra("XF")!=null){
                    if (getIntent().getStringExtra("IsSelected")!=null){
                        Intent intent_rl_syy=new Intent(this,MySelfSupYearMoney.class);
                        intent_rl_syy.putExtra("storeId", PublicCache.loginSupplier.getStore()+"");
                        startActivity(intent_rl_syy);
                    }else {
                        Intent intent=new Intent(this,YearMoneyRenewActivity.class);
                        intent.putExtra("type",getIntent().getStringExtra("type"));
                        startActivity(intent);
                    }

                }else {
                    finish();
                }

            break;
        }

    }
}
