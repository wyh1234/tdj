package cn.com.taodaji.ui.activity.linkPage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.base.common.PublicStaticValue;
import com.base.glide.GlideImageView;
import com.base.utils.ViewUtils;

import java.util.ArrayList;

import cn.com.taodaji.R;
import cn.com.taodaji.ui.activity.homepage.ManageActivity;
import tools.activity.DataBaseActivity;

public class GuideActivity extends DataBaseActivity implements ViewPager.OnPageChangeListener {

    /**
     * 装分页显示的view的数组
     */
    private ArrayList<View> pageViews;
    // 引导图片资源
    final int[] pics = {R.mipmap.start1,R.mipmap.start2,R.mipmap.start3};
    private GlideImageView[] indicators;
    private LinearLayout indicatorLayout; // 指示器
    private ViewPager viewPager;
    private int index = 0;

    @Override
    protected void initView() {

        // 将要分页显示的View装入数组中
//        LayoutInflater inflater = getLayoutInflater();
        pageViews = new ArrayList<>();
        PublicStaticValue.isPatch = true;//第一次进入，需要完全关闭
        for (int pic : pics) {
            GlideImageView iv = ViewUtils.getImageView(this);

            // 定义一个布局并设置参数
            LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            iv.setLayoutParams(mParams);
            // 防止图片不能填满屏幕
            iv.setScaleType(ScaleType.CENTER_CROP);
            // 加载图片资源
            iv.setImageResource(pic);
            pageViews.add(iv);
        }

        // 从指定的XML文件加载视图
//        ViewGroup viewPics = (ViewGroup) inflater.inflate(com.base.R.layout.view_cycle_viewpager_contet, null);
        // 指示器
        indicatorLayout = findViewById(R.id.viewpager_indicator);
        // 实例化viewpager
        viewPager = findViewById(R.id.viewPager);
        // 显示滑动图片的视图
//        setContentView(viewPics);
        // 设置viewpager的适配器和监听事件
        viewPager.setAdapter(new GuidePageAdapter());
        viewPager.addOnPageChangeListener(this);
        getIndicators();
        setIndicator(0);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected View getContentLayout() {
        return getLayoutView(com.base.R.layout.view_cycle_viewpager_contet);
    }

    private Button.OnClickListener Button_OnClickListener = v -> onclick();

    private void onclick() {
        // 设置已经引导
        setGuided();
        // 跳转
        Intent mIntent = new Intent();
        mIntent.setClass(this, ManageActivity.class);
        startActivity(mIntent);
        finish();
    }

    private static final String SHAREDPREFERENCES_NAME = "my_pref";
    private static final String KEY_GUIDE_ACTIVITY = "guide_activity";

    private void setGuided() {
        SharedPreferences settings = getSharedPreferences(SHAREDPREFERENCES_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_GUIDE_ACTIVITY, "false");
        editor.apply();
    }

    private void setIndicator(int selectedPosition) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setImageResource(R.mipmap.icon_point);
        }
        if (indicators.length > selectedPosition)
            indicators[selectedPosition].setImageResource(R.mipmap.icon_point_pre);
    }


    private void getIndicators() {
        indicators = new GlideImageView[pics.length];
        indicatorLayout.removeAllViews();
        int margin = getResources().getDimensionPixelOffset(R.dimen.indicator_margin);
        int size = getResources().getDimensionPixelOffset(R.dimen.indicator_size);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(size, size);
        lp.setMarginStart(margin);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = ViewUtils.getImageView(this);
            indicators[i].setLayoutParams(lp);
            indicatorLayout.addView(indicators[i]);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //  position :当前页面，及你点击滑动的页面
        //  positionOffset:当前页面偏移的百分比
        //   positionOffsetPixels:当前页面偏移的像素位置
        //  表示在前一个页面滑动到后一个页面的时候，在前一个页面滑动前调用的方法。
    }

    @Override
    public void onPageSelected(int position) {
        // index = position;
        setIndicator(position);
        //if (pageViews.size() - 1 == position) onclick();
        //position是表示你当前选中的页面，这事件是在你页面跳转完毕的时候调用的。
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state ==1的时候表示正在滑动，state==2的时候表示滑动完毕了，state==0的时候表示什么都没做，就是停在那。
    }

    private class GuidePageAdapter extends PagerAdapter {

        // 销毁position位置的界面
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //  super.destroyItem(container, position, object);
            container.removeView(pageViews.get(position));
        }

        // 获取当前窗体界面数
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return pageViews.size();
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(pageViews.get(position));
            Log.i("ViewPager", "" + position);
            if (position == pageViews.size() - 1) {
                pageViews.get(position).setOnClickListener(Button_OnClickListener);
            }
            return pageViews.get(position);
        }

        // 判断是否由对象生成界面
        @Override
        public boolean isViewFromObject(View v, Object arg1) {
            // TODO Auto-generated method stub
            return v == arg1;
        }

        @Override
        public int getItemPosition(Object object) {
            // TODO Auto-generated method stub
            return super.getItemPosition(object);
        }

    }

}