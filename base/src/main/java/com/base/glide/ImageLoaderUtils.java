package com.base.glide;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.base.R;
import com.base.common.Config;
import com.base.glide.progress.CircleProgressView;
import com.base.utils.FileSaveUtil;
import com.base.utils.UIUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

/**
 * Created by yangkuo on 2018-06-16.
 * <p>
 * 图片加载类，所有的图片
 */
public class ImageLoaderUtils {

    /**
     * @param imageView    需要加载图片的View
     * @param value        图片资源
     * @param placeholders 是否显示占位图 默认显示
     */
    public static void loadImage(@NonNull ImageView imageView, Object value, boolean... placeholders) {
        if (value == null) value = "";
        if (value.getClass() == Integer.class || value.getClass() == int.class) {
            imageView.setImageResource((int) value);
        }
        //默认有占位图
        boolean placeholder = true;
        if (placeholders.length > 0) placeholder = placeholders[0];
        //加载参数
        RequestOptions requestOptions = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.DATA);
        if (placeholder)
            requestOptions.placeholder(R.drawable.head_portrait).error(R.drawable.head_portrait);

        GlideImageLoader.create(imageView).requestBuilder(value, requestOptions).into(imageView);

    }

    /**
     * @param imageView    需要加载图片的View
     * @param value       gif 图片资源  循环播放动画
     */
    public static void loadImage(@NonNull ImageView imageView, int value) {
      //  Glide.with(UIUtils.getContext()).load(value).asGif().into(imageView);

        Glide.with(UIUtils.getContext()).load(value).listener(new RequestListener() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                if (resource instanceof GifDrawable) {
                    //加载一次
                    ((GifDrawable)resource).setLoopCount(GifDrawable.LOOP_FOREVER);
                }
                return false;
            }
        }).into(imageView);
    }


    /**
     * @param imageView    需要加载图片的View
     * @param value        图片资源
     * @param placeholders 是否显示占位图 默认显示
     */
    public static void loadImage(@NonNull GlideImageView imageView, Object value, boolean... placeholders) {
        loadImage(imageView, null, value, placeholders);
    }


    public static void loadImage(@NonNull GlideImageView imageView, CircleProgressView progressView, Object value, boolean... placeholders) {
        imageView.loadImage(value, progressView, placeholders);
    }


    public static void saveImage(Object value) {
        Glide.with(UIUtils.getContext()).asBitmap().load(value).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, Transition<? super Bitmap> transition) {
                FileSaveUtil.saveFile(UIUtils.getContext(), Config.imageSaveDir, +System.currentTimeMillis() + ".jpg", resource);
            }
        });
    }

}
