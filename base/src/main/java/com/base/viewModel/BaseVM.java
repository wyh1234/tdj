package com.base.viewModel;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.glide.GlideImageView;
import com.base.glide.ImageLoaderUtils;
import com.base.utils.JavaMethod;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseVM {


    //控件id和需要赋值的字段，进行关联,一个字段关联多个id
    public Map<String, List<Integer>> relationMap = new HashMap<>();

    //多个字段联合控制多个控件，我们只保存这个字段组
    public Map<String, String[]> relationMaps = new HashMap<>();

    public int last_select_position = 0;//上次选中的position,isRadio=true时有用


    //需要处理点击事件的控件
    public List<Integer> onclickList = new ArrayList<>();

    public BaseVM() {
        dataBinding();
        addOnclick();
    }

    protected abstract void dataBinding();

    protected void addOnclick() {
    }


    /**
     * 合并另外一个VM
     * 只合并键值对，
     */
//    public void addVM(BaseVM baseVM) {
//        relationMap.putAll(baseVM.relationMap);
//        onclickList.addAll(baseVM.onclickList);
//    }

    /**
     * 一对一
     *
     * @param resId
     * @param fieldName
     */
    @Deprecated
    public void putRelation(@IdRes int resId, String fieldName) {
        if (TextUtils.isEmpty(fieldName)) return;

        List<Integer> list = relationMap.get(fieldName);
        if (list == null) {
            list = new ArrayList<>();
            relationMap.put(fieldName, list);
        }
        list.add(resId);
    }

    /**
     * 一对多
     *
     * @param fieldName
     * @param resId
     */
    public void putRelation(String fieldName, @IdRes Integer... resId) {
        if (TextUtils.isEmpty(fieldName)) return;
        if (resId.length > 0) {
            relationMap.put(fieldName, Arrays.asList(resId));
        }
    }


    /**
     * 多对多
     *
     * @param fieldName
     */
    public void putRelation_more(String... fieldName) {
        if (fieldName.length > 0) {
            relationMaps.put(fieldName[0], fieldName);
        }
    }


    /**
     * 获取列表
     *
     * @return
     */
    public Map<String, List<Integer>> getRelationList() {
        return relationMap;
    }


    /**
     * 获取对应的字段列表
     *
     * @param fieldName
     * @return
     */
    public List<Integer> getViewIdOfString(String fieldName) {
        List<Integer> list = relationMap.get(fieldName);
        return list == null ? new ArrayList<>() : list;
    }


    public void putViewOnClick(@IdRes int resId) {
        onclickList.add(resId);
    }

    public List<Integer> getViewIdOnClick() {
        return onclickList;
    }

    /*
     *---------------------------------------------------------------------------以下多对多的赋值  -------------------------------------------------------------------------------------
     */

    /**
     * {@link com.base.viewModel.adapter.BaseRecyclerViewAdapter#onBindViewHolder(BaseViewHolder, int)}
     *
     * @param viewHolder
     * @param bean
     * @param <T>
     */
    public <T> void setValues(BaseViewHolder viewHolder, @NonNull T bean) {
        Map<String, Object> map = JavaMethod.transBean2Map(bean);

        for (String s : relationMap.keySet()) {
            setValues(viewHolder, s, map.get(s));
        }

        for (String s : relationMaps.keySet()) {
            setValue(viewHolder, bean, relationMaps.get(s));
        }

    }

    /**
     * 多对多赋值处理
     *
     * @param viewHolder
     * @param bean
     * @param pamstr
     * @param <T>
     */
    public final <T> void setValue(BaseViewHolder viewHolder, @NonNull T bean, String... pamstr) {
        if (pamstr.length == 0) {
            setValues(viewHolder, bean);
        } else {
            setValue(viewHolder, pamstr[0], bean);
        }
    }


    /**
     * 从局部刷新的地方来
     * {@link com.base.viewModel.adapter.BaseRecyclerViewAdapter#onBindViewHolder(BaseViewHolder, int, List)}
     *
     * @param viewHolder
     * @param map
     * @param bean
     * @param <T>
     */
    public <T> void setValues(BaseViewHolder viewHolder, Map<String, Object> map, T bean) {
        if (map.size() > 0) {
            //多对多的需要单独处理
            for (String s : relationMaps.keySet()) {
                String[] sst = relationMaps.get(s);
                if (sst != null) {
                    //检查map中是否含有多对多字段,如果有则从map中移除
                    boolean isExist = false;
                    for (String s1 : sst) {
                        if (map.get(s1) != null) {
                            isExist = true;
                            map.remove(s1);
                        }
                    }
                    //如果存在多对多则调用赋值
                    if (isExist) setValue(viewHolder, bean, sst);
                }
            }


            for (String ss : map.keySet()) {
                setValues(viewHolder, ss, map.get(ss));
            }
        }
    }

    /**
     * 多对多的赋值,这个方法需要重写
     *
     * @param viewHolder
     * @param pamstr     处在第一位的字段
     * @param bean       实体类
     */
    public <T> void setValue(BaseViewHolder viewHolder, String pamstr, @NonNull T bean) {

    }

    /*
     *---------------------------------------------------------------------------以上多对多的赋值  -------------------------------------------------------------------------------------
     */

    /**
     * 根据pamstr赋值
     *
     * @param viewHolder
     * @param pamstr
     * @param value
     */
    public void setValues(BaseViewHolder viewHolder, String pamstr, Object value) {
        if (pamstr.equals(viewHolder.getSelectFieldName())) {
            if (value != null) {
                boolean b = (boolean) value;
                if (b) last_select_position = viewHolder.getAdapterPosition();
            }
        }

        if (relationMap.size() > 0) {
            List<Integer> lis = relationMap.get(pamstr);
            if (lis != null && lis.size() > 0) {
                for (Integer li : lis) {
                    if (viewHolder.findViewById(li) != null)
                        setValues(viewHolder.findViewById(li), value);
                }
            }
        }
    }

    public void setValues(@NonNull View view, Object value) {
//        if (view == null) return;
        if (view instanceof Checkable) {
            setValue((Checkable) view, value, view.getTag());
        } else if (value instanceof Boolean) {
            view.setSelected((boolean) value);
        } else if (view instanceof TextView) {
            if (value == null) value = "";
            setValue((TextView) view, value);
        } else if (view instanceof GlideImageView) {
            if (value == null) value = "";
            setValue((GlideImageView) view, value);
        } else if (view instanceof ImageView) {
            if (value == null) value = "";
            setValue((ImageView) view, value);
        }else if (view instanceof LinearLayout) {
            setValue((LinearLayout) view, value);
        }
    }
    public void setValue(@NonNull LinearLayout linearLayout, @NonNull Object value) {
        linearLayout.setBackgroundResource((int)value);
    }
    public void setValue(@NonNull Checkable checkable, Object value, Object tag) {
        if (value instanceof Boolean) {
            checkable.setChecked((boolean) value);
        } else {
            checkable.setChecked(JavaMethod.equals(value, tag));
        }
    }

    public void setValue(@NonNull TextView textView, @NonNull Object value) {
        if (value instanceof BigDecimal) {
            BigDecimal mat = ((BigDecimal) value).setScale(2, BigDecimal.ROUND_HALF_UP);
            textView.setText(mat.stripTrailingZeros().toPlainString());
        } else {
            textView.setText(value.toString());
        }
    }


    public void setValue(@NonNull GlideImageView imageView, @NonNull Object value) {
        imageView.loadImage(value);
    }

    public void setValue(@NonNull ImageView imageView, @NonNull Object value) {
        ImageLoaderUtils.loadImage(imageView, value);
    }


    /**
     * 获取一对一列表的值,一对多则只读取第一个
     *
     * @param viewHolder
     * @return
     */
    public Map<String, Object> getViewValues(BaseViewHolder viewHolder) {

        Map<String, Object> map = new HashMap<>();

        for (String s : relationMap.keySet()) {
            List<Integer> list = relationMap.get(s);
            if (list != null && list.size() > 0) {
                int resId = list.get(0);
                if (viewHolder.findViewById(resId) != null && viewHolder.findViewById(resId) instanceof TextView) {
                    TextView textView = viewHolder.findViewById(resId);
                    if (textView.getVisibility() == View.VISIBLE)
                        map.put(s, textView.getText().toString().trim());
                }
            }
        }
        return map;
    }

    //onclickType 0 点击事件，1 长按事件 ， 2，触摸事件 按下去就触发
    public boolean onItemClick(View view, int onclickType, int position, Object bean) {
        return false;
    }


}
