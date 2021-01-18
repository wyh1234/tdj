package tools.activity;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.taodaji.R;

import com.base.utils.ViewUtils;

/**
 * 继承该类的子类，可以设置SimpleToolbar的相关内容
 */
public abstract class SimpleToolbarActivity extends ToolbarBaseActivity {

    public TextView simple_title;
    public TextView search_text;
    public LinearLayout title_right;
    public TextView right_textView;
    public ImageView right_icon;
    public TextView right_textView_new,right_icon_text;

    public LinearLayout line_group;
    public LinearLayout line_top_tips;
    public LinearLayout right_onclick_line;
    public RelativeLayout rl_fl,rl_more;

    /**
     * 初始化工具栏
     */
    @Override
    protected Toolbar initToolbar() {
        View view = ViewUtils.getLayoutView(this, R.layout.toolbar_customer_simple_title);
        if (title != null) title.addView(view);
        Toolbar toolbar = ViewUtils.findViewById(view, R.id.toolbar);
        simple_title = ViewUtils.findViewById(view, R.id.simple_title);
        title_right = ViewUtils.findViewById(view, R.id.title_right);
        search_text = ViewUtils.findViewById(view, R.id.search_text);
        right_textView = ViewUtils.findViewById(view, R.id.right_text);
        right_icon = ViewUtils.findViewById(view, R.id.right_icon);
        right_icon_text = ViewUtils.findViewById(view, R.id.right_icon_text);
        right_textView_new = ViewUtils.findViewById(view, R.id.right_text_new);

//        line_evaluate_tips = ViewUtils.findViewById(view, R.id.line_evaluate_tips);
        line_group = ViewUtils.findViewById(view, R.id.line_group);
        line_top_tips = ViewUtils.findViewById(view, R.id.line_top_tips);

        right_onclick_line = ViewUtils.findViewById(view, R.id.right_onclick_line);
//        text_evaluate_or_reply_tips = ViewUtils.findViewById(view, R.id.text_evaluate_or_reply_tips);
//        txt_go_evaluate_or_reply = ViewUtils.findViewById(view, R.id.txt_go_evaluate_or_reply);
        rl_fl=ViewUtils.findViewById(view, R.id.rl_fl);
        rl_more=ViewUtils.findViewById(view, R.id.rl_more);


        return toolbar;
    }

    public void setTitle(String str) {
        simple_title.setText(str);
    }

    public void setRightIcon(Drawable icon) {
        right_icon.setBackground(icon);
    }

    public void setRightIcon(@DrawableRes int icon) {
        right_icon.setBackgroundResource(icon);
    }

}
