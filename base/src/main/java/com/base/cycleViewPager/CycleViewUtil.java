package com.base.cycleViewPager;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.base.glide.GlideImageView;
import com.base.glide.ImageLoaderUtils;
import com.base.utils.ADInfo;
import com.base.utils.ViewUtils;

public class CycleViewUtil extends CycleView {
    private List<ImageView> views = new ArrayList<>();
    private Context mContext;
    private boolean isShowRight = false;

    public CycleViewUtil(Context context) {
        super(context);
        mContext = context;
    }

    public void setShowRight(boolean showRight) {
        isShowRight = showRight;
    }


    public void setDatas(List<ADInfo> infos) {
        initialize(infos);
    }

    public void loadMores(List<ADInfo> infos) {
        views.clear();
        getList().addAll(infos);
    }

    public void loadMore(List<String> list) {

    }

    public void setData(List<String> list) {
        List<ADInfo> lia = new ArrayList<>();
        for (String ss : list) {
            ADInfo adInfo = new ADInfo();
            adInfo.setImageObject(ss);
            lia.add(adInfo);
        }
        setDatas(lia);
    }

    public void setData(String str_lst) {
        setData(Arrays.asList(str_lst.split(",")));
    }


    private void initialize(List<ADInfo> infos) {
        views.clear();
        if (infos == null || infos.size() == 0) {
            mainView.setVisibility(View.GONE);
            return;
        } else {
            mainView.setVisibility(View.VISIBLE);
        }


        if (infos.size() > 1) {
            getview(infos);
        } else {
            GlideImageView imageview1 = ViewUtils.getImageView(mContext);
            if (imageview1 == null) return;
            setViewValues(imageview1, infos.get(0));
            views.add(imageview1);
        }

        if (isShowRight) right_icon.setVisibility(View.VISIBLE);
        else right_icon.setVisibility(View.GONE);

        // 设置循环，在调用setData方法前调用
        setCycle(infos.size() > 1);
        // 在加载数据前设置是否循环
        setData(views, infos);
        // 设置轮播
        setWheel(infos.size() > 1);
        // 设置轮播时间，默认5000ms
        setTime(3000);

        // 设置圆点指示图标组居中显示，默认靠右
        setIndicatorCenter();

    }


    public void getview(List<ADInfo> infos) {
        // 将最后一个ImageView添加进来
        if (mContext == null) return;
        GlideImageView imageview = ViewUtils.getImageView(mContext);
        setViewValues(imageview, infos.get(infos.size() - 1));
        views.add(imageview);

        for (int i = 0; i < infos.size(); i++) {
            GlideImageView image = ViewUtils.getImageView(mContext);
            setViewValues(image, infos.get(i));
            views.add(image);
        }

        // 将第一个ImageView添加进来
        GlideImageView imageview1 = ViewUtils.getImageView(mContext);
        setViewValues(imageview1, infos.get(0));
        views.add(imageview1);
    }

    private void setViewValues(GlideImageView imageView, ADInfo adInfo) {
        ImageLoaderUtils.loadImage(imageView,  adInfo.getImageObject());
/*        GlideImageLoader glideImageLoader = GlideImageLoader.create(imageView);
        RequestOptions requestOptions = glideImageLoader.requestOptions(R.drawable.head_portrait).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        glideImageLoader.load(adInfo.getImageObject(), requestOptions);*/
    }
}
