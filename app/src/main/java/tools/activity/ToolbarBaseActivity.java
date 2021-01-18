package tools.activity;


import android.content.Intent;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import cn.com.taodaji.R;
import cn.com.taodaji.common.Constants;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.ui.activity.homepage.ManageActivity;

import com.base.activity.ActivityManage;
import com.base.widget.FloatingDragView;
import com.base.utils.ColorUtil;
import com.base.utils.UIUtils;

/**
 * 继承该类的子类，可以设置ToolBar的相关内容
 * <p>
 * RecycleView
 * <p>
 * 1、滑动不流畅
 * <p>
 * 解决方法：嵌套滑动不激活。
 * <p>
 * mRecycleView.setNestedScrollingEnable(false);
 * <p>
 * 2、当NestedScrollView嵌套RecycleView布局由Fragment管理，Fragment切换时会自动滑动到ReycleView的顶部。
 * <p>
 * 解决方法：在NestedScrollView唯一子布局中加入 android:descendantFocusability=“blocksDescendants”
 * <p>
 * android:descendantFocusability 有三个属性
 * <p>
 * 优先于子控件获取焦点
 * <p>
 * “beforeDescendants"
 * <p>
 * 当子控件不需要焦点时，获取焦点
 * <p>
 * ”afterDescendants“
 * <p>
 * 覆盖所有子控件获取焦点
 * <p>
 * ”blocksDescendants“
 */
public abstract class ToolbarBaseActivity extends DataBaseActivity {

    public Toolbar toolbar;

    public LinearLayout body_other;
    public LinearLayout body_center;
    public LinearLayout body_bottom;
    public LinearLayout body_scroll;
    public NestedScrollView ns_scroll_body;
    public View title_split_line;

    public FloatingDragView floating;
    public CoordinatorLayout mainLayout;

    protected void initView() {
        super.initView();

        title_split_line = findViewById(R.id.title_split_line);
        body_other = findViewById(R.id.other_body);
        body_center = findViewById(R.id.center_body);
        body_bottom = findViewById(R.id.bottom_body);
        body_scroll = findViewById(R.id.scroll_body);
        ns_scroll_body = findViewById(R.id.ns_scroll_body);


        floating = findViewById(R.id.floating);
        mainLayout = findViewById(R.id.mainLayout);
        // body_scroll.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        toolbar = initToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbar.setNavigationIcon(R.mipmap.left_arrow_white);
            toolbar.setContentInsetStartWithNavigation(5);
            titleSetting(toolbar);
        }
        View shopping_floating_button = findViewById(R.id.shopping_floating_button);
        if (shopping_floating_button != null)
            shopping_floating_button.setOnClickListener(v -> MenuToolbarActivity.goToPage(3));
        initMainView();
    }


    /**
     * 返回当前Activity布局文件的id
     *
     * @return
     */
    protected View getContentLayout() {
        return getLayoutView(R.layout.activity_other_currency);
    }

    /**
     * 初始化工具栏
     */
    protected abstract Toolbar initToolbar();

    protected abstract void titleSetting(Toolbar toolbar);

    /**
     * 初始化主窗体
     */
    protected abstract void initMainView();


    public void setToolBarColor() {
        switch (PublicCache.login_mode) {
            case PURCHASER:
                setToolBarColor(R.color.orange_yellow_ff7d01);
                break;
            case SUPPLIER:
                setToolBarColor(R.color.blue_2898eb);
                break;
        }
    }

    //需要随登录方式不同而改变的在initView 中调用,不调用则显示系统默认 orage_yellow_ff7d01
    public void setStatusBarColor() {
        switch (PublicCache.login_mode) {
            case Constants.SUPPLIER:
                setStatusBarColor(R.color.blue_2898eb);
                break;
            default:
                setStatusBarColor(R.color.orange_yellow_ff7d01);
                break;
        }
    }


    public void setViewBackColor(View view) {
        switch (PublicCache.login_mode) {
            case Constants.PURCHASER:
                view.setBackgroundResource(R.drawable.r_round_rect_solid_orange_ff7d01);
                break;
            case Constants.SUPPLIER:
                view.setBackgroundResource(R.drawable.r_round_rect_solid_blue_2898eb);
                break;
        }
    }

    public void setToolBarColor(@ColorRes int resid) {
        if (toolbar != null) toolbar.setBackgroundColor(UIUtils.getColor(resid));
    }

    public void setToolBackgroundResource(@DrawableRes int resid) {
        if (toolbar != null) toolbar.setBackgroundResource(resid);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getCurrentFocus() != null)
                    hideSoftInput(getCurrentFocus().getWindowToken());//隐藏键盘
                if (!ActivityManage.isActivityExist(ManageActivity.class)) {
                    Intent intent = new Intent(this, ManageActivity.class);
                    startActivity(intent);
                } else supportFinishAfterTransition();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {
        if (!ActivityManage.isActivityExist(ManageActivity.class)) {
            Intent intent = new Intent(this, ManageActivity.class);
            startActivity(intent);
            finish();
        } else super.onBackPressed();
    }
}
