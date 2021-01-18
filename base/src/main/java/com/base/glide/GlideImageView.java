package com.base.glide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

import com.base.R;
import com.base.annotation.ShapeType;
import com.base.common.PublicStaticValue;
import com.base.glide.progress.CircleProgressView;
import com.base.glide.progress.OnGlideImageViewListener;
import com.base.glide.progress.OnProgressListener;
import com.base.glide.transformation.GlideCircleTransformation;
import com.base.glide.transformation.GlideRoundRectTransform;
import com.base.utils.BitmapUtil;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class GlideImageView extends ShapeImageView {

    private GlideImageLoader mImageLoader;

    public GlideImageView(Context context) {
        this(context, null);
    }

    public GlideImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GlideImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        mImageLoader = GlideImageLoader.create(this);
    }

    public GlideImageLoader getImageLoader() {
        if (mImageLoader == null) {
            mImageLoader = GlideImageLoader.create(this);
        }
        return mImageLoader;
    }

    public String getImageUrl() {
        return getImageLoader().getImageUrl();
    }

    public void loadImage(Object value, boolean... placeholders) {
        loadImage(value, null, placeholders);
    }

    public void loadImage(Object value, CircleProgressView progressView, boolean... placeholders) {
        if (value == null) value = "";

        if (PublicStaticValue.def_round_drawable == null)
            PublicStaticValue.def_round_drawable = BitmapUtil.toRoundDrawable(R.drawable.head_portrait);

        //默认有占位图
        boolean placeholder = true;
        if (placeholders.length > 0) placeholder = placeholders[0];

        //加载参数
        RequestOptions requestOptions;

        //获取加载图片类型
        int shapeType = getShapeType();
        int radius = getRadius();

        switch (shapeType) {
            case ShapeType.CIRCLE:
                requestOptions = new RequestOptions().transform(new GlideCircleTransformation());
                if (placeholder)
                    requestOptions.placeholder(PublicStaticValue.def_round_drawable).error(PublicStaticValue.def_round_drawable);
                break;
            case ShapeType.ROUNDRECT:
                requestOptions = new RequestOptions().transform(new GlideRoundRectTransform(radius));
                if (placeholder)
                    requestOptions.placeholder(R.drawable.head_portrait).error(R.drawable.head_portrait);
                break;
            default:
                requestOptions = new RequestOptions();
                if (placeholder)
                    requestOptions.placeholder(R.drawable.head_portrait).error(R.drawable.head_portrait);
                break;
        }

        //加载图片
        getImageLoader().load(value, progressView, requestOptions.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.DATA));
    }


   //默认缺省图
    public void loadImage(Object value, int resource) {
        loadImage(value, null, resource);
    }

    public void loadImage(Object value, CircleProgressView progressView, int resource) {
        if (value == null) value = "";

        if (PublicStaticValue.def_round_drawable == null)
            PublicStaticValue.def_round_drawable = BitmapUtil.toRoundDrawable(R.drawable.head_portrait);

        //默认有占位图
//        boolean placeholder = true;
//        if (placeholders.length > 0) placeholder = placeholders[0];

        //加载参数
        RequestOptions requestOptions;

        //获取加载图片类型
        int shapeType = getShapeType();
        int radius = getRadius();

        switch (shapeType) {
            case ShapeType.CIRCLE:
                requestOptions = new RequestOptions().transform(new GlideCircleTransformation());
              //  if (placeholder)
                    requestOptions.placeholder(PublicStaticValue.def_round_drawable).error(PublicStaticValue.def_round_drawable);
                break;
            case ShapeType.ROUNDRECT:
                requestOptions = new RequestOptions().transform(new GlideRoundRectTransform(radius));
              //  if (placeholder)
                    requestOptions.placeholder(resource).error(resource);
                break;
            default:
                requestOptions = new RequestOptions();
               // if (placeholder)
                    requestOptions.placeholder(resource).error(resource);
                break;
        }

        //加载图片
        getImageLoader().load(value, progressView, requestOptions.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.DATA));
    }



    public GlideImageView listener(OnGlideImageViewListener listener) {
        getImageLoader().setOnGlideImageViewListener(getImageUrl(), listener);
        return this;
    }

    public GlideImageView listener(OnProgressListener listener) {
        getImageLoader().setOnProgressListener(getImageUrl(), listener);
        return this;
    }
}
