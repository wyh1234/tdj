package tools.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.com.taodaji.R;

import com.base.widget.FloatingDragView;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;


public abstract class CollapseToolbarActivity extends MenuToolbarActivity implements AppBarLayout.OnOffsetChangedListener {

    public LinearLayout title_collapse_view;
    public CollapsingToolbarLayout collapsingToolbarLayout;
    public AppBarLayout appBarLayout;
    public LinearLayout collapse_content;
    public LinearLayout collapse_bottom;
    public Toolbar toolbar;
    public FloatingDragView floating;
    public CoordinatorLayout coordinatorLayout;

    public TextView detail_title;

    @Override
    protected Toolbar initToolbar() {
        coordinatorLayout = ViewUtils.findViewById(this, R.id.coordinatorLayout);
        collapsingToolbarLayout = ViewUtils.findViewById(this, R.id.collapseToolbarLayout);

        appBarLayout = ViewUtils.findViewById(this, R.id.appbarLayout);
        appBarLayout.addOnOffsetChangedListener(this);
        title_collapse_view = ViewUtils.findViewById(this, R.id.title_collapse_view);
        collapse_content = ViewUtils.findViewById(this, R.id.collapse_content);
        collapse_bottom = ViewUtils.findViewById(this, R.id.collapse_bottom);
        floating = ViewUtils.findViewById(this, R.id.floating);
        collapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER_HORIZONTAL);
        toolbar = ViewUtils.findViewById(this, R.id.toolbar);
        toolbar.setContentInsetEndWithActions(UIUtils.dip2px(66));
        detail_title = findViewById(R.id.detail_title);
        setImmersiveStatusBarToolbar(toolbar);
        // setImmersiveNavigationBar();
        titleSetting(toolbar);
        return toolbar;
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        float offset = Math.abs(verticalOffset);
        float abl = appBarLayout.getMeasuredHeight();
        float tb = toolbar.getMeasuredHeight();
//        float sb = StatusBarUtil.getStatusBarHeight();
        float am = abl - tb;
        float aph = (offset / am) * 255;
        toolbar.getBackground().setAlpha((int) aph);
    }

    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.toolbar_collapse);
    }
}
