package com.base.utils;

import android.support.annotation.ColorRes;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.base.R;
import com.base.activity.ActivityManage;
import com.base.top_snackbar.BaseTransientTopBar;
import com.base.top_snackbar.TopSnackBar;

/**
 * Created by Administrator on 2018-02-06.
 */

public class SnackBarUtils {
    /**
     * 设置Snackbar上的字体颜色
     *
     * @param snackbar
     * @param color
     */
    public static void setSnackbarMessageTextColor(Snackbar snackbar, @ColorRes int color) {
        View view = snackbar.getView();
        view.setBackgroundColor(UIUtils.getColor(R.color.translucent));
        ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(UIUtils.getColor(color));
    }

    public static void showTopSnackbar(View view, String str) {
        TopSnackBar snackbar = TopSnackBar.make(view, str, BaseTransientTopBar.LENGTH_SHORT);
        snackbar.setSnackbarMessageTextColor(R.color.white);
        snackbar.show();
    }

    public static TopSnackBar showTopSnackbarAction(View view, String str) {
        TopSnackBar snackbar = TopSnackBar.make(view, str, BaseTransientTopBar.LENGTH_SHORT);
        snackbar.setSnackbarMessageTextColor(R.color.white);
        snackbar.show();
        return snackbar;
    }

}
