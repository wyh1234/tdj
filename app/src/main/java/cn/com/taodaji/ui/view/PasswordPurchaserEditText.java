package cn.com.taodaji.ui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.base.listener.DrawableLeftListener;
import com.base.listener.DrawableRightListener;
import com.base.utils.BitmapUtil;
import com.base.utils.UIUtils;
import com.base.widget.my_edittext.MyEditText;

import cn.com.taodaji.R;

public class PasswordPurchaserEditText extends MyEditText implements DrawableLeftListener {
    public PasswordPurchaserEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init_view();
    }

    public PasswordPurchaserEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init_view();
    }

    public PasswordPurchaserEditText(Context context) {
        super(context);
        init_view();
    }

    public PasswordPurchaserEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init_view();
    }

    private void init_view() {
        if (!isInEditMode()) {
            setCompoundDrawablePadding(UIUtils.dip2px(20));
            Drawable drawable = BitmapUtil.getDrawable(R.mipmap.suo_bg);
            // 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            setCompoundDrawables(drawable, null,null , null);
            setDrawableLeftListener(this);
            setCustomSelectionActionModeCallback(new ActionMode.Callback() {
                public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                    return false;
                }

                public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                    return false;
                }

                public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                    return false;
                }

                public void onDestroyActionMode(ActionMode actionMode) {
                }
            });
            setLongClickable(false);
            setTextIsSelectable(false);

        }
        setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    private boolean password_show = false;

    public void passwordSetShow(boolean show) {
        if (password_show != show) {
            password_show = show;
            // 刷新 drawable state
            refreshDrawableState();
        }
    }

    // 代表选中状态的集合
    private static final int[] PASSWORD_SHOW_STATE_SET = new int[]{
            R.attr.state_password_show
    };

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        if (password_show) {
            // 如果选中，将父类的结果和选中状态合并之后返回
            return mergeDrawableStates(super.onCreateDrawableState(extraSpace + 1), PASSWORD_SHOW_STATE_SET);
        }
        return super.onCreateDrawableState(extraSpace);
    }




    @Override
    public void onDrawableLeftClick(View view) {
        passwordSetShow(!password_show);

        //设置密码是否可见
        if (password_show) setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        else setTransformationMethod(PasswordTransformationMethod.getInstance());

    }
}