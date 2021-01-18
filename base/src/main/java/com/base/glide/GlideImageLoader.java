package com.base.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.base.glide.progress.CircleProgressView;
import com.base.glide.progress.OnGlideImageViewListener;
import com.base.glide.progress.OnProgressListener;
import com.base.glide.progress.ProgressManager;
import com.base.glide.transformation.GlideCircleTransformation;

import com.base.utils.UIUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.lang.ref.WeakReference;

public class GlideImageLoader {

    private WeakReference<ImageView> mImageView;
    private Object mImageUrlObj;
    private long mTotalBytes = 0;
    private long mLastBytesRead = 0;
    private boolean mLastStatus = false;
    private Handler mMainThreadHandler;

    private OnProgressListener internalProgressListener;
    private OnGlideImageViewListener onGlideImageViewListener;
    private OnProgressListener onProgressListener;

    public static GlideImageLoader create(ImageView imageView) {
        return new GlideImageLoader(imageView);
    }

    private GlideImageLoader(ImageView imageView) {
        mImageView = new WeakReference<>(imageView);
        mMainThreadHandler = new Handler(Looper.getMainLooper());
    }

    public ImageView getImageView() {
        if (mImageView != null) {
            return mImageView.get();
        }
        return null;
    }

    public Context getContext() {
/*        if (getImageView() != null) {
            return getImageView().getContext();
        }*/
        return UIUtils.getContext();
//        return null;
    }

    public String getImageUrl() {
        if (mImageUrlObj == null) return "";
        if (!(mImageUrlObj instanceof String)) return "";
        return (String) mImageUrlObj;
    }

    public void load(Object obj, RequestOptions... requestOptions) {
        load(obj, null, requestOptions);
    }


    public void load(Object obj, CircleProgressView progressView, RequestOptions... requestOptions) {
        if (getContext() == null || getImageView() == null) return;
        if (obj == null) obj = "";
        if (obj.getClass() == Integer.class || obj.getClass() == int.class) {
            getImageView().setImageResource((int) obj);
        } else {
            RequestOptions options = requestOptions.length > 0 ? requestOptions[0] : new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.DATA);
            requestBuilder(obj, progressView, options).into(getImageView());
        }
    }

    public RequestBuilder<Drawable> requestBuilder(Object obj, CircleProgressView progressView, RequestOptions options) {
        if (progressView != null)
            setOnGlideImageViewListener(obj.toString(), (percent, isDone, exception) -> {
                if (percent > progressView.getProgress()) progressView.setProgress(percent);
                progressView.setVisibility(isDone ? View.GONE : View.VISIBLE);
            });
        return requestBuilder(obj, options);
    }

    public RequestBuilder<Drawable> requestBuilder(Object obj, RequestOptions options) {
        if (obj == null) obj = "";
        return Glide.with(getContext()).load(obj).thumbnail(0.4f).apply(options).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                mainThreadCallback(mLastBytesRead, mTotalBytes, true, e);
                ProgressManager.removeProgressListener(internalProgressListener);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                if (getImageView() != null) {
                    ViewGroup.LayoutParams layoutParams = getImageView().getLayoutParams();
                    //当高为WRAP_CONTENT 则根据宽按图片比例自动调整imageView的高
                    if (layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT) {

                        if (layoutParams.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
                            layoutParams.width = resource.getIntrinsicWidth();
                            layoutParams.height = resource.getIntrinsicHeight();
                            getImageView().setLayoutParams(layoutParams);
                        } else {
                            float width = resource.getIntrinsicWidth();
                            float height = resource.getIntrinsicHeight();
                            float scale = height / width;
                            int w = getImageView().getMeasuredWidth();
                            int h = (int) (w * scale);
                            layoutParams.width = w;
                            layoutParams.height = h;
                            getImageView().setLayoutParams(layoutParams);
                        }
                    }
                    //当宽为WRAP_CONTENT 则根据高按图片比例自动调整imageView的宽
                    else if (layoutParams.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
                        float width = resource.getIntrinsicWidth();
                        float height = resource.getIntrinsicHeight();
                        float scale = width / height;
                        int h = getImageView().getMeasuredHeight();
                        int w = (int) (h * scale);
                        layoutParams.height = h;
                        layoutParams.width = w;
                        getImageView().setLayoutParams(layoutParams);

                    }
                }


                mainThreadCallback(mLastBytesRead, mTotalBytes, true, null);
                ProgressManager.removeProgressListener(internalProgressListener);
                return false;
            }
        });
    }

    public RequestOptions requestOptions(int placeholderResId) {
        return requestOptions(placeholderResId, placeholderResId);
    }

    public RequestOptions requestOptions(int placeholderResId, int errorResId) {
        return new RequestOptions().placeholder(placeholderResId).error(errorResId);
    }

    public RequestOptions circleRequestOptions(int placeholderResId) {
        return circleRequestOptions(placeholderResId, placeholderResId);
    }

    public RequestOptions circleRequestOptions(int placeholderResId, int errorResId) {
        return requestOptions(placeholderResId, errorResId).transform(new GlideCircleTransformation());
    }


    private void addProgressListener() {
        if (getImageUrl() == null) return;
        final String url = getImageUrl();
        if (!url.startsWith("http")) return;

        internalProgressListener = new OnProgressListener() {
            @Override
            public void onProgress(String imageUrl, long bytesRead, long totalBytes, boolean isDone, GlideException exception) {
                if (totalBytes == 0) return;
                if (!url.equals(imageUrl)) return;
                if (mLastBytesRead == bytesRead && mLastStatus == isDone) return;

                mLastBytesRead = bytesRead;
                mTotalBytes = totalBytes;
                mLastStatus = isDone;
                mainThreadCallback(bytesRead, totalBytes, isDone, exception);

                if (isDone) {
                    ProgressManager.removeProgressListener(this);
                }
            }
        };
        ProgressManager.addProgressListener(internalProgressListener);
    }

    private void mainThreadCallback(final long bytesRead, final long totalBytes, final boolean isDone, final GlideException exception) {
        mMainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                final int percent = (int) ((bytesRead * 1.0f / totalBytes) * 100.0f);
                if (onProgressListener != null) {
                    onProgressListener.onProgress((String) mImageUrlObj, bytesRead, totalBytes, isDone, exception);
                }

                if (onGlideImageViewListener != null) {
                    onGlideImageViewListener.onProgress(percent, isDone, exception);
                }
            }
        });
    }

    //不要和glide
    public void setOnGlideImageViewListener(String imageUrl, OnGlideImageViewListener onGlideImageViewListener) {
        this.mImageUrlObj = imageUrl;
        this.onGlideImageViewListener = onGlideImageViewListener;
        addProgressListener();
    }

    public void setOnProgressListener(String imageUrl, OnProgressListener onProgressListener) {
        this.mImageUrlObj = imageUrl;
        this.onProgressListener = onProgressListener;
        addProgressListener();
    }
}
