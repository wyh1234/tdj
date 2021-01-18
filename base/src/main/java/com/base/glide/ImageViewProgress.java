package com.base.glide;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.base.R;
import com.base.glide.progress.CircleProgressView;
import com.base.glide.progress.OnGlideImageViewListener;
import com.base.utils.DensityUtil;
import com.base.utils.UIUtils;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestOptions;


/**
 * Created by yangkuo on 2018-06-13.
 */
public class ImageViewProgress extends FrameLayout {


    public ImageViewProgress(Context context) {
        super(context);
        init(context);
    }

    public ImageViewProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ImageViewProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private GlideImageView glideImageView;
    private CircleProgressView circleProgressView;

    private void init(Context context) {
        //图片
        glideImageView = new GlideImageView(context);
        glideImageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            glideImageView.setTransitionName(UIUtils.getString(R.string.transitional_image));
        }

        //进度条
        circleProgressView = new CircleProgressView(context);
        circleProgressView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        circleProgressView.setProgressStyle(CircleProgressView.ProgressStyle.FILL_IN_ARC);//进度条类型
        circleProgressView.setOuterColor(UIUtils.getColor(R.color.transparent90_white));//外圈的颜色
        circleProgressView.setOuterSize(DensityUtil.dp2px(1));//外圈的宽度
        circleProgressView.setInnerPadding(DensityUtil.dp2px(2));//内外圈的间隔
        circleProgressView.setNormalBarColor(UIUtils.getColor(R.color.transparent));//未完成进度颜色
        circleProgressView.setReachBarColor(UIUtils.getColor(R.color.transparent90_white));//已完成进度颜色
        circleProgressView.setProgress(0);
        circleProgressView.setMax(100);
        circleProgressView.setVisibility(GONE);
        addView(glideImageView);
        addView(circleProgressView);
    }

    public void loadImage(Object obj) {
        glideImageView.loadImage(obj, circleProgressView, false);

        if (obj != null && obj.getClass() == String.class) {
            glideImageView.listener(new OnGlideImageViewListener() {
                @Override
                public void onProgress(int percent, boolean isDone, GlideException exception) {
                    circleProgressView.setProgress(percent);
                    circleProgressView.setVisibility(isDone ? View.GONE : View.VISIBLE);
                }
            });
        }
    }


    public GlideImageView getGlideImageView() {
        return glideImageView;
    }

    public CircleProgressView getCircleProgressView() {
        return circleProgressView;
    }

}
