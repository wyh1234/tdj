package com.base.viewModel;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.glide.GlideImageView;
import com.base.glide.ImageLoaderUtils;
import com.base.utils.JavaMethod;

import java.math.BigDecimal;

/**
 * Created by yangkuo on 2019/2/1.
 */
public class BaseUtils {
    public static void setValues(@NonNull View view, Object value) {
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
        }
    }


    public static void setValue(@NonNull Checkable checkable, Object value, Object tag) {
        if (value instanceof Boolean) {
            checkable.setChecked((boolean) value);
        } else {
            checkable.setChecked(JavaMethod.equals(value, tag));
        }
    }

    public static void setValue(@NonNull TextView textView, @NonNull Object value) {
        if (value instanceof BigDecimal) {
            BigDecimal mat = ((BigDecimal) value).setScale(2, BigDecimal.ROUND_HALF_UP);
            textView.setText(mat.stripTrailingZeros().toPlainString());
        } else {
            textView.setText(value.toString());
        }
    }


    public static void setValue(@NonNull GlideImageView imageView, @NonNull Object value) {
        imageView.loadImage(value);
    }

    public static void setValue(@NonNull ImageView imageView, @NonNull Object value) {
        ImageLoaderUtils.loadImage(imageView, value);
    }
}
