package cn.com.taodaji.ui.activity.advertisement;

import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.taodaji.R;
import cn.com.taodaji.ui.activity.advertisement.adapter.MarketingManageMentAdapter;
import cn.com.taodaji.ui.activity.advertisement.adapter.TfAdvertisementManageAdapter;
import tools.activity.DataBaseActivity;
import tools.statusbar.Eyes;

public class TfAdvertisementManageActivity extends DataBaseActivity {
    @BindView(R.id.intergral_toolbar)
    Toolbar intergral_toolbar;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.tv_right)
    TextView tv_right;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    public List<String> titles = new ArrayList<>();
    public TfAdvertisementManageAdapter adatper;
    @OnClick({R.id.btn_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_back:
                finish();
                break;
        }
    }
    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.tfad_ad_manage_layout);
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
        tv_title.setText("投放计划");
        tv_right.setVisibility(View.GONE);
        tv_right.setText("推广管理");
        Drawable drawable = getResources().getDrawable(R.mipmap.tuiguanguanli);// 找到资源图片
        // 这一步必须要做，否则不会显示。
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置图片宽高
        tv_right.setCompoundDrawables(drawable, null, null, null);// 设置到控件中
        tv_right.setCompoundDrawablePadding(10);

        titles.add("审核");
        titles.add("排期中");
        titles.add("投放中");
        titles.add("已完成");
        titles.add("已暂停");

        viewPager.setOffscreenPageLimit(1);
        adatper = new TfAdvertisementManageAdapter(this.getSupportFragmentManager(), titles);
        viewPager.setAdapter(adatper);
        //将TabLayout和ViewPager关联起来。
        tabLayout.setupWithViewPager(viewPager);
        initTabView();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initTabView() {
        for (int i = 0; i < titles.size(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(getTabView(i));
            }
        }
    }
    private View getTabView(int position) {
        View v = ViewUtils.getFragmentView(tabLayout, R.layout.tablayout_punish_textview);
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        TextView titleText = v.findViewById(R.id.tab_text);
        titleText.setText(titles.get(position));
        return v;
    }
}
