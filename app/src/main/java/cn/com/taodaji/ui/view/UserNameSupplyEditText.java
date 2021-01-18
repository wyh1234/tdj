package cn.com.taodaji.ui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.base.listener.DrawableLeftListener;
import com.base.listener.DrawableRightListener;
import com.base.utils.UIUtils;
import com.base.widget.my_edittext.MyEditText;

import cn.com.taodaji.InterfaceUtils.UserNameChangeListener;
import cn.com.taodaji.R;

public class UserNameSupplyEditText extends MyEditText implements DrawableRightListener, TextWatcher, DrawableLeftListener {
    public UserNameChangeListener userNameChangeListener;

    public UserNameSupplyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init_view();
    }

    public UserNameSupplyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init_view();
    }

    public UserNameSupplyEditText(Context context) {
        super(context);
        init_view();
    }

    public UserNameSupplyEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init_view();
    }

    public void removeDrawableRightListener() {
        removeDrawableRightListener(this);
    }

    private void init_view() {


        setCompoundDrawablePadding(UIUtils.dip2px(20));
        Drawable phone = ContextCompat.getDrawable(getContext(), R.mipmap.shouji_bg);
        if (phone != null) {
            phone.setBounds(0, 0, phone.getMinimumWidth(), phone.getMinimumHeight());
            setCompoundDrawables(phone, null, null, null);
        }
        setDrawableRightListener(this);
        setDrawableLeftListener(this);
        addTextChangedListener(this);
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


    @Override
    public void onDrawableRightClick(View view) {
        setText("");
        if (userNameChangeListener != null) {
            userNameChangeListener.onUserNameCloseListener();
        }

        //强制弹出软键盘
    /*    if (getContext() instanceof BaseActivity) {
            ((BaseActivity) getContext()).showInputMethod(this);
        }*/
    }


    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    public void setUserNameChangeListener(UserNameChangeListener userNameChangeListener) {
        this.userNameChangeListener = userNameChangeListener;
    }

    @Override
    public void afterTextChanged(Editable editable) {
//        Drawable phone = ContextCompat.getDrawable(getContext(), R.mipmap.icon_orange_phone);
//        if (phone != null) {
//            phone.setBounds(0, 0, phone.getMinimumWidth(), phone.getMinimumHeight());
//            setCompoundDrawables(phone, null,null , null);
//        }
        Drawable phone = ContextCompat.getDrawable(getContext(), R.mipmap.shouji_bg);
        phone.setBounds(0, 0, phone.getMinimumWidth(), phone.getMinimumHeight());
//        setCompoundDrawablePadding(UIUtils.dip2px(10));
        if (editable.toString().length() == 0) {
            setCompoundDrawables(phone, null, null, null);
        } else if (editable.toString().length() > 0) {
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.mipmap.phone_clear);
            // 这一步必须要做,否则不会显示.
            if (drawable != null) {
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                setCompoundDrawables(phone, null, drawable, null);
            }
            if (userNameChangeListener != null) {
                userNameChangeListener.onUserNameChangeListener(editable.toString());
            }


        }

    }

    @Override
    public void onDrawableLeftClick(View view) {

    }
}
