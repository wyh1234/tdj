package com.base.glide.nineImageView;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.base.R;
import com.base.glide.GlideImageLoader;
import com.base.glide.ImageLoaderUtils;
import com.base.glide.progress.CircleProgressView;
import com.base.utils.ViewUtils;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.OnOutsidePhotoTapListener;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;


public class ImagesAdapter extends PagerAdapter implements OnPhotoTapListener, OnOutsidePhotoTapListener {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<ImageAttr> images;
    private SparseArray<PhotoView> photoViews = new SparseArray<>();

    public ImagesAdapter(Context context, @NonNull List<ImageAttr> images) {
        super();
        this.mContext = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    public PhotoView getPhotoView(int index) {
        return photoViews.get(index);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = (ViewGroup) mInflater.inflate(R.layout.item_photoview, container, false);
        CircleProgressView progressView = (CircleProgressView) view.findViewById(R.id.progressView);
        PhotoView photoView = (PhotoView) view.findViewById(R.id.photoView);
        photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        photoView.setOnPhotoTapListener(this);
        photoView.setOnOutsidePhotoTapListener(this);

        photoViews.put(position, photoView);

        ImageAttr attr = images.get(position);
        String url = TextUtils.isEmpty(attr.thumbnailUrl) ? attr.url : attr.thumbnailUrl;
        photoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = ViewUtils.showDialog(mContext, "提示", "保存图片");
                builder.setPositiveButton("保存", (dialog, which) -> {
                    ImageLoaderUtils.saveImage(url);
                    dialog.dismiss();
                });
                builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
                builder.create().show();
                return true;
            }
        });

        GlideImageLoader imageLoader = GlideImageLoader.create(photoView);
        RequestOptions requestOptions = imageLoader.requestOptions(R.color.black_63).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        imageLoader.requestBuilder(url, progressView, requestOptions).transition(DrawableTransitionOptions.withCrossFade()).into(photoView);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        photoViews.remove(position);
        container.removeView((View) object);
    }

    @Override
    public void onPhotoTap(ImageView view, float x, float y) {
        ((ImagesActivity) mContext).finishWithAnim();
    }

    @Override
    public void onOutsidePhotoTap(ImageView imageView) {
        ((ImagesActivity) mContext).finishWithAnim();
    }
}