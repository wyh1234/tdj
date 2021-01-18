package com.base.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.base.R;
import com.base.glide.GlideImageView;


public class ViewUtils {
    /* 把自身从父View中移 */
    public static void removeSelfFromParent(View view) {
        // 先找到父类，再过父类移除子类
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) parent;
                group.removeView(view);
            }
        }
    }

    /**
     * @param view
     * @param defaultTopMargin -1则表示调上一个控件的大小，-2表示前两个，>=0表示默认的defaultTopMargin
     */
    public static void setViewBottom(View view, int defaultTopMargin) {
        if (view != null) {

            Point point = DensityUtil.getScreenMetrics(view.getContext());
            Rect rect = DensityUtil.getViewRectOnScreen(view);
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            if (view.getParent() != null) {
                //距离底部的距离
                int spe = point.y - lp.bottomMargin - rect.bottom;

                //调整指定控件的大小，来达到调整控件位置的目的
                if (defaultTopMargin < 0) {
                    //获取当前控件的位index;
                    int index = ((ViewGroup) view.getParent()).indexOfChild(view);
                    //检查需要调整的控是否存在
                    int index_previous = index + defaultTopMargin;
                    //如果需要调整的控存在,index_previous就是它的index
                    if (index_previous >= 0) {
                        View previousView = ((ViewGroup) view.getParent()).getChildAt(index_previous);
                        if (previousView != null) {
                            //获取需要调整控件的大小
                            ViewGroup.LayoutParams gpParams = previousView.getLayoutParams();
                            //如果控件没有在屏幕的底部
                            if (spe > 0) {
                                gpParams.height += spe;
                                previousView.setLayoutParams(gpParams);
                            }
                            //如果控件超出屏幕的底部，检查是否需要复位
                            else if (spe < 0) {
                                int minimumHeight = previousView.getMinimumHeight();
                                if (gpParams.height > minimumHeight) {
                                    gpParams.height = minimumHeight;
                                    previousView.setLayoutParams(gpParams);
                                }
                            }
                        }
                    }
                }
                //调整控件的topMargin来达到调整控件位置的目的
                else {
                    //如果控件没有在屏幕的底部,将控件设置在底部
                    if (spe > 0) {
                        lp.topMargin += spe;
                        view.setLayoutParams(lp);
                    }
                    //如果控件超出屏幕的底部，检查是否需要复位
                    else if (spe < 0) {
                        if (lp.topMargin > defaultTopMargin) {
                            lp.topMargin = defaultTopMargin;
                            view.setLayoutParams(lp);
                        }
                    }
                }
            }

        }
    }

    public static GlideImageView getImageView(Context context) {
        if (context == null) return null;
        return (GlideImageView) LayoutInflater.from(context).inflate(R.layout.t_imageview, null);
    }


    private static View get_root_view(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }


    /* FindViewById的泛型封装，减少强转代码 */
    public static <T extends View> T findViewById(View layout, @IdRes int id) {
        return (T) layout.findViewById(id);
    }

    /* FindViewById的泛型封装，减少强转代码 */
    public static <T extends View> T findViewWithTag(View layout, Object tag) {
        return (T) layout.findViewWithTag(tag);
    }

    public static <T extends View> T getFragmentView(ViewGroup parent, @LayoutRes int layoutResid) {
        return (T) LayoutInflater.from(parent.getContext()).inflate(layoutResid, parent, false);
    }

    /**
     * 获取布局
     *
     * @ resId
     * @
     */

    public static <T extends View> T getLayoutView(Context context, @LayoutRes int res_layid) {
        return (T) LayoutInflater.from(context).inflate(res_layid, null);
    }

    public static View getLayoutViewMatch(Context context, @LayoutRes int layoutResId) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View view = getLayoutView(context, layoutResId);
        view.setLayoutParams(lp);
        return view;
    }

    /* FindViewById的泛型封装，减少强转代码 */
    @SuppressWarnings("unchecked")
    public static <T extends View> T findViewById(Activity activity, int id) {
        return (T) activity.findViewById(id);
    }

    /* 获取根视图 */
    public static View getRootView(Activity act) {
        return act.getWindow().getDecorView().findViewById(android.R.id.content);
    }

    /**
     * 对TextView的简易封装确线程安全，可以在非UI线程调用
     */
    public static void setTextView_Text(final TextView textView, final String text) {
        if (UIUtils.isRunInMainThread()) {
            textView.setText(text);
        } else {
            UIUtils.post(new Runnable() {
                @Override
                public void run() {
                    textView.setText(text);
                }
            });
        }
    }

    //ScrollView嵌套ListView时，会无法正确的计算ListView的大小
    public static void fixListViewHeight(ListView listView) {
        // 如果没有设置数据适配器，则ListView没有子项，返回。
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        if (listAdapter == null) {
            return;
        }
        for (int index = 0, len = listAdapter.getCount(); index < len; index++) {
            View listViewItem = listAdapter.getView(index, null, listView);
            // 计算子项View 的宽高
            listViewItem.measure(0, 0);
            // 计算所有子项的高度和
            totalHeight += listViewItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // listView.getDividerHeight()获取子项间分隔符的高度
        // params.height设置ListView完全显示需要的高度
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static AlertDialog.Builder showDialog(Context mContext, String title, String message) {
        // 构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(message);
        return builder;
    }


}
