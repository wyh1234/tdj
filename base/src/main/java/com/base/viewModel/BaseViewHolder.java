package com.base.viewModel;

import android.graphics.Paint;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.glide.GlideImageView;
import com.base.utils.UIUtils;
import com.base.viewModel.BaseVM;
import com.base.viewModel.adapter.BaseRecyclerViewAdapter;
import com.base.viewModel.adapter.OnItemClickListener;

import java.util.List;
import java.util.Map;

public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener {

    private BaseVM baseVM;
    private BaseRecyclerViewAdapter adapter;

    private SparseArrayCompat<View> viewList = new SparseArrayCompat<>();
    /**
     * 调用 {@link #setValues(Object, String...)} (Object)}方法后才会初始化
     */
    private Object bean;//缓存的值


    //activity或fragment点击事件
    private OnItemClickListener itemClickListener;

    /**
     * 普通的activity或fragment使用
     *
     * @param itemClickListener ==null时不调用
     */
    public BaseViewHolder(View itemView, @Nullable BaseVM baseVM, @Nullable OnItemClickListener itemClickListener) {
        super(itemView);
        this.baseVM = baseVM;
        this.itemClickListener = itemClickListener;
        onClickInit();
    }


    //适配器使用
    public BaseViewHolder(View itemView, int viewType, @NonNull BaseRecyclerViewAdapter recyclerViewAdapter) {
        super(itemView);
        adapter = recyclerViewAdapter;
        this.itemClickListener = recyclerViewAdapter.getItemClickListener();
        baseVM = adapter.getVM(viewType);
        onClickInit();
    }


    public Object getListBean() {
        if (bean == null) {
            if (adapter != null) {
                int pos = getAdapterPosition();
                if (pos > -1) bean = adapter.getListBean(pos);
            }
        }
        return bean;
    }

    public BaseVM getBaseVM() {
        return baseVM;
    }

    public List<Integer> getOnClickResIdList() {
        return baseVM.getViewIdOnClick();
    }

    private void onClickInit() {
        if (baseVM == null) return;
        //初始化点击事件
        List<Integer> onclick = baseVM.getViewIdOnClick();
        if (onclick.size() > 0) {
            for (int i = 0; i < onclick.size(); i++) {
                View view = findViewById(onclick.get(i));
                if (view != null) {
                    view.setOnClickListener(this);
                    view.setOnLongClickListener(this);
                    view.setOnTouchListener(this);
                }

            }
        }
    }

    @Override
    public void onClick(View v) {
        onClickEvent(v, 0, getAdapterPosition());
    }


    /**
     * itemClickListener最先执行
     * adapter  次之
     * baseVM   最后
     *
     * @param onclickType 值2，1，0，值越大越先执行
     */
    public boolean onClickEvent(View v, int onclickType, int position) {

        //adapter!=null 说明是在activity或fragment中
        boolean ret = false;
        if (adapter == null) {
            if (itemClickListener == null || !(ret = itemClickListener.onItemClick(v, onclickType, position, getListBean()))) {
                //activity或fragment中不需要对点击事件处理，或未处理，交有baseVM处理
                if (baseVM != null)
                    ret = baseVM.onItemClick(v, onclickType, position, getListBean());
            }

        } else {
            //adapter!=null 说明是在recyclerView中
            if (itemClickListener == null || !(ret = itemClickListener.onItemClick(v, onclickType, position, adapter.getListBean(position)))) {
                if (!(ret = adapter.onItemClick(v, onclickType, position, adapter.getListBean(position)))) {
                    //adapter返回false表示未处理，则交由baseVM处理
                    //baseVM一般对需要复用的点击事件处理
                    if (baseVM != null)
                        ret = baseVM.onItemClick(v, onclickType, position, adapter.getListBean(position));
                }
            }
        }
        return ret;
    }


    @Override
    public boolean onLongClick(View v) {
        return onClickEvent(v, 1, getAdapterPosition());
    }

    /**
     * 设置需要选中，折叠的字段
     */
    public String getSelectFieldName() {
        if (adapter != null) return adapter.foldName;
        return null;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            return onClickEvent(v, 2, getAdapterPosition());
        }
        return false;
    }

    public <T> void setValues(Map<String, Object> map, T bean) {
        if (baseVM == null) return;
        baseVM.setValues(this, map, bean);
    }

    public <T> void setValues(@NonNull T bean, String... pamstr) {
        if (baseVM == null) return;
        this.bean = bean;
        if (pamstr.length == 0) baseVM.setValues(this, bean);
        else baseVM.setValue(this, bean, pamstr);
    }


    public void setValues(String pamstr, Object value) {
        if (baseVM == null) return;
        baseVM.setValues(this, pamstr, value);
    }


    public <T extends View> T findViewById(@IdRes int resId) {
        if (viewList.get(resId, null) == null) {
            View view = itemView.findViewById(resId);
            if (view != null) viewList.put(resId, view);
        }
        return (T) viewList.get(resId, null);
    }

    public Map<String, Object> getViewValues() {
        return baseVM.getViewValues(this);
    }

    /**
     * 设置TextView文本
     */
    public void setText(@IdRes int viewId, Object text) {
        TextView tv = findViewById(viewId);
        if (tv != null && baseVM != null) {
            baseVM.setValue(tv, text);
        }
    }

    public void setImageRes(@IdRes int viewId, Object obj) {
        ImageView imageView = findViewById(viewId);
        if (imageView != null && baseVM != null) {
            baseVM.setValues(imageView, obj);
        }
    }

    public void setBg(@IdRes int viewId, Object obj) {
        LinearLayout linearLayout = findViewById(viewId);
        if (linearLayout != null && baseVM != null) {
            baseVM.setValues(linearLayout, obj);
        }
    }
    public String getText(@IdRes int viewId) {
        TextView tv = findViewById(viewId);
        if (tv != null) return tv.getText().toString();
        return "";
    }

    public void setTextColor(@IdRes int viewId, @ColorRes int colorId) {
        TextView tv = findViewById(viewId);
        if (tv != null) tv.setTextColor(UIUtils.getColor(colorId));
    }


    /**
     * 设置View的Visibility
     */
    public void setVisibility(@IdRes int viewId, int visibility) {
        View view = findViewById(viewId);
        if (view != null) {
            view.setVisibility(visibility);
        }
    }

    public void setLinearLayoutGravity(@IdRes int viewId, int gravity) {
        LinearLayout view = findViewById(viewId);
        if (view != null) {
            view.setGravity(gravity);
        }
    }

    /**
     * 设置View的isSelected
     */
    public void setViewSelected(@IdRes int viewId, boolean isSelected) {
        View view = findViewById(viewId);
        if (view != null) {
            view.setSelected(isSelected);
        }
    }

    /**
     * 设置View的isChecked
     */
    public void setViewChecked(@IdRes int viewId, boolean isChecked) {
        View view = findViewById(viewId);
        if (view != null && view instanceof Checkable) {
            Checkable checkable = (Checkable) view;
            checkable.setChecked(isChecked);
        }
    }

    public void setTextViewDeleteLine(int viewId) {
        TextView textView = findViewById(viewId);
        if (textView != null)
            textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
    }

    public void setTextViewUnderLine(int viewId) {
        TextView textView = findViewById(viewId);
        if (textView != null)
            textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
    }

}
