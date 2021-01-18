package cn.com.taodaji.ui.activity.penalty;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.PunishData;
import cn.com.taodaji.ui.activity.integral.WebViewActivity;
import cn.com.taodaji.ui.activity.penalty.adapter.PunishListFragmentAdapter;
import tools.activity.DataBaseActivity;
import tools.statusbar.Eyes;

public class PunishListActivity extends DataBaseActivity {
    public List<String> titles = new ArrayList<>();
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
/*    @BindView(R.id.tv_tab)
    public  TextView tv_tab;
    @BindView(R.id.tv_tab1)
    public  TextView tv_tab1;
    @BindView(R.id.tv_tab2)
    public  TextView tv_tab2*/;
    @BindView(R.id.tv_total)
    public  TextView tv_total;
    @BindView(R.id.tv_title)
      TextView tv_title;
    @BindView(R.id.btn_back)
    ImageView btn_back;
   /* @BindView(R.id.ll_item)
    public LinearLayout ll_item;*/

    @OnClick({R.id.btn_back,R.id.tv_right})
    public void  onClick(View view){
        switch (view.getId()){
    /*        case R.id.tv_tab:
                tv_tab.setSelected(true);
                tv_tab1.setSelected(false);
                tv_tab2.setSelected(false);
                EventBus.getDefault().post(new PunishData());
                break;
            case R.id.tv_tab1:
                tv_tab1.setSelected(true);
                tv_tab.setSelected(false);
                tv_tab2.setSelected(false);
                EventBus.getDefault().post(new PunishData());
                break;
            case R.id.tv_tab2:
                tv_tab.setSelected(false);
                tv_tab1.setSelected(false);

                tv_tab2.setSelected(true);
                EventBus.getDefault().post(new PunishData());
                break;*/
            case R.id.btn_back:

                finish();
                break;
            case R.id.tv_right:
                Intent intent=new Intent(this, WebViewActivity.class);
                intent.putExtra("url","https://www.taodaji.com.cn/h5/fine/punishWh.html");
                intent.putExtra("title","缴费管理");
                startActivity(intent);
                break;
        }
    }

    public PunishListFragmentAdapter adatper;
    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.punish_list_layout);
    }

    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
//        tv_tab.setSelected(true);
        tv_title.setText("缴费管理");
        Eyes.translucentStatusBar(this,true);
        titles.add("待处理");
        titles.add("已缴费");
        titles.add("已取消");
        initTabView();
        viewPager.setOffscreenPageLimit(2);
        adatper = new PunishListFragmentAdapter(this.getSupportFragmentManager(), titles);
        viewPager.setAdapter(adatper);
        //将TabLayout和ViewPager关联起来。
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0,false);
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
