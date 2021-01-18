package cn.com.taodaji.ui.activity.purchaseBill;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.utils.ViewUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.liyi.viewer.data.ViewData;
import com.liyi.viewer.factory.ImageLoader;
import com.liyi.viewer.listener.OnImageChangedListener;
import com.liyi.viewer.listener.OnWatchStatusListener;
import com.liyi.viewer.widget.ImageViewer;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import tools.activity.SimpleToolbarActivity;

public class PhotoViewActivity extends SimpleToolbarActivity {
    private List<Object> mImageList;
    private List<String> list;
    private RequestOptions mOptions;
    private List<ViewData> mViewDatas;
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("缴费图片");
    }

    @Override
    protected void initMainView() {
        View mainView = ViewUtils.getLayoutView(this, R.layout.activity_photoview);
        body_other.addView(mainView);
        ImageViewer imagePreivew = ViewUtils.findViewById(mainView, R.id.imagePreivew);
        TextView title = ViewUtils.findViewById(mainView, R.id.title);
        mOptions = new RequestOptions()
                .error(R.mipmap.tdj_logo);
        final Intent intent = getIntent();
        if (intent.getStringArrayListExtra("showphotolist") != null) {
            list = intent.getStringArrayListExtra("showphotolist");
            mImageList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                mImageList.add(list.get(i));
            }
        }
        // 图片浏览的起始位置
        mViewDatas = new ArrayList<>();
        for (int i = 0, len = intent.getStringArrayListExtra("showphotolist").size(); i < len; i++) {
            ViewData viewData = new ViewData();
            mViewDatas.add(viewData);
        }
        title.setText(intent.getIntExtra("showphoto", 0) + 1 + "/" + mImageList.size());

        imagePreivew.setStartPosition(intent.getIntExtra("showphoto", 0));
        // 图片的数据源
        imagePreivew.setImageData(mImageList);
        // 外部 View 的位置以及尺寸等信息
        imagePreivew.setViewData(mViewDatas);
        // 自定义图片的加载方式
        imagePreivew.showIndex(false);
        imagePreivew.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(final int position, Object src, final ImageView view) {
                Glide.with(PhotoViewActivity.this)
                        .load(src)
                        .apply(mOptions)
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onLoadStarted(@Nullable Drawable placeholder) {
                                super.onLoadStarted(placeholder);
                                view.setImageDrawable(placeholder);
                            }

                            @Override
                            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                super.onLoadFailed(errorDrawable);
                                view.setImageDrawable(errorDrawable);
                            }

                            @Override
                            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                                view.setImageDrawable(resource);
                                mImageList.set(position, resource);
                                mViewDatas.get(position).setImageWidth(resource.getIntrinsicWidth());
                                mViewDatas.get(position).setImageHeight(resource.getIntrinsicHeight());

                            }
                        });
            }
        });
        // 开启图片浏览
        imagePreivew.watch();
        imagePreivew.setOnImageChangedListener(new OnImageChangedListener() {
            @Override
            public void onImageSelected(int position, ImageView view) {
                title.setText(position + 1 + "/" + mImageList.size());
            }
        });
        imagePreivew.setOnWatchStatusListener(new OnWatchStatusListener() {
            @Override
            public void onWatchStart(int state, int position, ImageView view) {


            }

            @Override
            public void onWatchDragging(ImageView view) {
            }

            @Override
            public void onWatchReset(int state, ImageView view) {
            }

            @Override
            public void onWatchEnd(int state) {
                LogUtils.i(state);
                if (state == 4) {
                    finish();
                }

            }
        });
    }
}
