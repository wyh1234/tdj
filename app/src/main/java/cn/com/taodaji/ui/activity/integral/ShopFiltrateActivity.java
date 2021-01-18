package cn.com.taodaji.ui.activity.integral;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.base.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.FiltrateService;
import cn.com.taodaji.ui.activity.integral.adapter.FiltrateCategoryAdapter;
import cn.com.taodaji.ui.activity.integral.adapter.FiltrateServiceAdapter;
import tools.activity.DataBaseActivity;
import tools.animation.ScrollLinearLayoutManager;
import tools.statusbar.Eyes;

public class ShopFiltrateActivity extends DataBaseActivity {
    @BindView(R.id.intergral_toolbar)
    Toolbar intergral_toolbar;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.filtrate_service_list)
    RecyclerView filtrate_service_list;

    @BindView(R.id.filtrate_category_list)
    RecyclerView filtrate_category_list;
    @BindView(R.id.filtrate_pice_list)
    RecyclerView filtrate_pice_list;
    private FiltrateServiceAdapter filtrateServiceAdapter;
    private FiltrateCategoryAdapter filtrateCategoryAdapter;
    private List<FiltrateService> list=new ArrayList<>();
    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.shop_filtrate_layout);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initView() {
        super.initView();
        Eyes.setStatusBarColor(this, UIUtils.getColor(this, R.color.sx_bg));
        ButterKnife.bind(this);
        intergral_toolbar.setBackgroundColor(getResources().getColor(R.color.sx_bg));
        tv_title.setText("商品筛选");
        tv_title.setTextColor(getResources().getColor(R.color.gray_66));
        btn_back.setVisibility(View.VISIBLE);
        btn_back.setImageResource(R.mipmap.left_arrow_gary);
        list.add(new FiltrateService());
        list.add(new FiltrateService());
        list.add(new FiltrateService());
        list.add(new FiltrateService());
        ScrollLinearLayoutManager layoutManager=   new ScrollLinearLayoutManager(this, 3);
        filtrate_service_list.setLayoutManager(layoutManager);
        filtrateServiceAdapter=new FiltrateServiceAdapter(this,list);
        filtrate_service_list.setAdapter(filtrateServiceAdapter);

        ScrollLinearLayoutManager layoutManager1=   new ScrollLinearLayoutManager(this, 3);
        filtrate_pice_list.setLayoutManager(layoutManager1);
        filtrateServiceAdapter=new FiltrateServiceAdapter(this,list);
        filtrate_pice_list.setAdapter(filtrateServiceAdapter);

        ScrollLinearLayoutManager layoutManager2=   new ScrollLinearLayoutManager(this, 4);
        filtrate_category_list.setLayoutManager(layoutManager2);
        filtrateCategoryAdapter=new FiltrateCategoryAdapter(this,list);
        filtrate_category_list.setAdapter(filtrateCategoryAdapter);
    }
}
