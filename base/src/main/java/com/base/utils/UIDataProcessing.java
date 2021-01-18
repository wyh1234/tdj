package com.base.utils;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import com.base.R;

import java.util.Map;

public class UIDataProcessing {

    private static Object value = null;//值
    private static Object tag_obj = null;//tag标签


    /**
     * 返回值字段或上传参数字段应在tag的最前，以下划线_隔开,Checkable的选中值应跟随其后
     * _onclick,或_nullable,_round等判断标签跟随其后
     * _format_"yyyy-MM-dd" (一般用于time转换) 或 _info_用户名不可为空(一般用于为空时描述)
     * ViewGroup 的点击事件_onclick或onclick,不会参于获取和赋值，可以不用考虑
     * <p/>
     * 递归调用，获取View中所有，设有Tag  的输入控件的值
     * 判断是否是组合框  tag采用，id,name 含有id就获取id，否则获取name
     * 判断是否实现Checkable接口的控件，tag =isActivie_1 ,isActivie的字段如果为1则选中
     * 可为空的输入框要加    _nullable,否则不能通过为空判断
     */
    public static boolean getChildControlsValue(View view, Map<String, Object> map) {
        //判断控件是否显示，隐藏的不做处理
        if (view.getVisibility() == View.VISIBLE) {
            tag_obj = view.getTag();

            // Spinner继承于ViewGroup所以要在ViewGroup前面判断
            if (view instanceof Spinner) {
                Spinner spinner = (Spinner) view;

                if (spinner.getSelectedItem()==null)return false;
                // 判断组合框  tag含有id就获取id，否则获取name，
                Map map1 = JavaMethod.transJson2Map(spinner.getSelectedItem().toString().trim());

                if (tag_obj != null) {
                    if (tag_obj.toString().toLowerCase().contains("id"))
                        map.put(tag_obj.toString(), map1 != null ? map1.get("id") : "");
                    else map.put(tag_obj.toString(), map1 != null ? map1.get("name") : "");
                }

            } else if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                int count;
                if ((count = viewGroup.getChildCount()) > 0)
                    for (int i = 0; i < count; i++)
                        getChildControlsValue(viewGroup.getChildAt(i), map);
            }
            //只处理设有tag标签的控件
            else if (tag_obj != null) {

                //解析出真正需要的字段
                String tag_s = tag_obj.toString();
                String tag;
                if (!tag_s.contains("_")) tag = tag_s;
                else tag = tag_s.substring(0, tag_s.indexOf("_"));

                //判断是否实现Checkable接口的控件
                if (view instanceof Checkable) {
                    if (((Checkable) view).isChecked())
                        map.put(tag, tag_s.substring(tag.length() + 1));
                }
                //判断是否TextView的子类
                else if (view instanceof TextView) {
                    TextView textView = (TextView) view;
                    String info;
                    String str = textView.getText().toString().trim();
                    //判断是否可为空
                    if (!tag.toLowerCase().contains("_nullable") && tag.contains("_info_")) {
                        tag_s = tag_s.substring(tag.indexOf("_info_") + 6);

                        if (tag_s.contains("_")) {
                            info = tag.substring(0, tag.indexOf("_"));
                        } else info = tag_s;

                        if (str.length() == 0) {
                            UIUtils.showToastSafesShort(info + "不可为空");
                            return false;
                        }
                    }

                    //判断是否密码输入框,是则加密处理
                    if (tag.toLowerCase().contains("password")) {

                        //判断是否需要验证两次密码是否一致
                        if ((value = map.get(tag)) != null && !MD5AndSHA.md5Encode(str).equals(value)) {
                            UIUtils.showToastSafesShort("两次密码输入不一致");
                            return false;
                        } else map.put(tag, MD5AndSHA.md5Encode(str));

                    } else map.put(tag, str);
                }
            }
        }


        return true;
    }

    /**
     * 递归调用，将传过来的值，根据tag分别赋予控件
     */
    public static void setChildControlsValue(View view, Map<String, Object> map) {
        if (map == null) return;
        if (view.getVisibility() == View.VISIBLE) {

            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                int count;
                if ((count = viewGroup.getChildCount()) > 0) for (int i = 0; i < count; i++)
                    setChildControlsValue(viewGroup.getChildAt(i), map);

            } else if ((tag_obj = view.getTag()) != null && !"".equals(tag_obj)) {

                //返回值字段或上传参数字段
                String tag_s = tag_obj.toString();
                String tag;
                if (!tag_s.contains("_")) tag = tag_s;
                else tag = tag_s.substring(0, tag_s.indexOf("_"));

                //获取tag的值,如果值不为空则赋值
                if ((value = map.get(tag)) != null && !"onclick".equals(tag_obj))
                    setViewData(view, tag_s, value);
            }
        }
    }

    /**
     * 递归调用，将传过来的值，根据tag分别赋予控件
     */
    public static <T> void setChildControlsValue(View view, T dataBean) {
        if (dataBean == null) return;

        /** 只处理选中状态的控件*/
        if (view.getVisibility() == View.VISIBLE) {

            /** 如果是容器则回调自己*/
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                int count = viewGroup.getChildCount();
                if (count > 0) {
                    for (int i = 0; i < count; i++) {
                        setChildControlsValue(viewGroup.getChildAt(i), dataBean);
                    }
                }
            } else if ((tag_obj = view.getTag()) != null && !"".equals(tag_obj) && !"onclick".equals(tag_obj)) {

                /**返回值字段或上传参数字段*/
                String tag_s = tag_obj.toString();
                String tag;

                /**ViewGroup 的点击事件onclick,不会参于获取和赋值，可以不用考虑,
                 * 返回值字段或上传参数字段，在tag标签的的最前面*/
                if (!tag_s.contains("_")) tag = tag_s;
                else tag = tag_s.substring(0, tag_s.indexOf("_"));


                /** 获取tag的值,并赋给控件*/
                if ((value = JavaMethod.getValueFromBean(dataBean, tag)) != null)
                    setViewData(view, tag_s, value);

            }

        }

    }


    private static void setViewData(View view, String tag, Object value) {
        //判断是否实现Checkable接口的控件
        if (view instanceof Checkable) {
            Checkable checkBox = (Checkable) view;
            if (tag.toLowerCase().contains("_" + value)) {
                checkBox.setChecked(true);
            } else checkBox.setChecked(false);
        }

        //判断是否ImageView的子类
        else if (view instanceof ImageView) {
            ImageView editText = (ImageView) view;
            Bitmap bitmap;
            //判断字段是否是字节数组
            if (tag.toLowerCase().contains("imagebyte") && null != (bitmap = BitmapUtil.byteToBitmap((byte[]) value))) {
                editText.setImageBitmap(bitmap);

                //判断是否是R.drawable.image
            } else if (tag.toLowerCase().contains("resource")) {
                editText.setImageResource((int) value);

                //判断是否切圆
            } else if (tag.toLowerCase().contains("_round")) {
                //  LoadHttpImageLoader.getInstance().displayFromHttpToRoundBitmap(value.toString(), editText);

            } else if ("".equals(value)) {
                editText.setImageResource(R.drawable.head_portrait);
            } //else
            //   LoadHttpImageLoader.getInstance().displayFromHttp(value.toString(), editText);
        }

        //判断是否TextView的子类
        else if (view instanceof TextView) {
            TextView editText = (TextView) view;

            //如果是时间类型的要转化
            if (tag.toLowerCase().contains("time") && tag.contains("_format_")) {
                tag = tag.substring(tag.indexOf("_format_") + 8);
                String format;

                if (tag.contains("_")) {
                    format = tag.substring(0, tag.indexOf("_"));
                } else format = tag;

                if (value instanceof Long) {
                    long time = (long) value;
                    editText.setText(DateUtils.dateLongToString(time, format));
                }


            } else editText.setText(value.toString());
        }
    }

}
