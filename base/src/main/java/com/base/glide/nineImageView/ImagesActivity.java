package com.base.glide.nineImageView;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.base.R;
import com.base.viewModel.adapter.BaseRecyclerViewAdapter;
import com.base.viewModel.adapter.GroupRecyclerAdapter;
import com.base.viewModel.BaseViewHolder;
import com.base.utils.ColorUtil;
import com.base.utils.DensityUtil;
import com.base.utils.JavaMethod;
import com.base.utils.StatusBarUtil;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ImagesActivity extends Activity implements ViewTreeObserver.OnPreDrawListener {

    public static final String IMAGE_ATTR = "image_attr";
    public static final String CUR_POSITION = "cur_position";
    public static final int ANIM_DURATION = 300; // ms

    private RelativeLayout rootView;
    private ViewPager viewPager;
    private TextView tvTip;
    private ImagesAdapter mAdapter;
    private List<ImageAttr> imageAttrs;

    private int curPosition;//翻页位置
    private int screenWidth;
    private int screenHeight;
    private float scaleX;
    private float scaleY;
    private float translationX;
    private float translationY;


    //单个的打开方式
    public static void startActivity(View imageView, Object url) {
        if (url == null) url = "";
        ImageAttr attr = new ImageAttr();
        if (imageView != null) {
            attr.url = url.toString();
            attr.width = imageView.getWidth();
            attr.height = imageView.getHeight();
            int[] points = new int[2];
            imageView.getLocationInWindow(points);
            attr.left = points[0];
            attr.top = points[1];
            startActivity(imageView.getContext(), Collections.singletonList(attr), 0);
        }
    }

    /**
     * RecyclerView的打开方式
     *
     * @param position           被点击的位置
     * @param imageViewId
     * @param imageFieldName
     * @param itemType_start_end
     */
    public static void startActivity(RecyclerView recyclerView, int position, @IdRes int imageViewId, String imageFieldName, int... itemType_start_end) {
        int index = -1;//点击的图片在images中所点的位置
        BaseRecyclerViewAdapter adapter = (BaseRecyclerViewAdapter) recyclerView.getAdapter();

        int startPosition = adapter.getFirstPosition();//从startPosition 开始的图片进行显示
        int endPosition = adapter.getLastPosition();//到endPosition 图片结束显示
        int itemType = BaseRecyclerViewAdapter.TYPE_CHILD;

        switch (itemType_start_end.length) {
            case 1:
                itemType = itemType_start_end[0];
                break;
            case 2:
                itemType = itemType_start_end[0];
                startPosition = itemType_start_end[1];
                break;
            case 3:
                itemType = itemType_start_end[0];
                startPosition = itemType_start_end[1];
                endPosition = itemType_start_end[2];
                break;
        }

        List<ImageAttr> images = new ArrayList<>();//需要显示图片的集合

        if (position >= startPosition && position <= endPosition) {

            //初始化图片的集合
            //首先需要获取第一个和最后一个显示的viewHolder的position
            View view = recyclerView.getChildAt(0);
            //显示在第一个的position
            int one = recyclerView.getChildAdapterPosition(view);
            //遍历寻找显示在第一个的itemType 位置 one
            for (int i = one; i < adapter.getItemCount(); i++) {
                if (adapter.getItemViewType(i) == itemType) {
                    one = i;
                    break;
                }
            }

            //显示在最后一个的position 这个需要判断无法直接获得
            int last = 0;
            for (int i = startPosition; i <= endPosition; i++) {

                if (adapter.getItemViewType(i) == itemType) {

                    if (i == position) index = images.size();

                    BaseViewHolder viewHolder = adapter.getViewHolder(i);
                    //在第一位的之前的位置信息丢失,都显示one的位置信息
                    if (i < one) {
                        viewHolder = adapter.getViewHolder(one);
                    }
                    //超出屏幕之外，显示last的位置信息
                    else if (viewHolder == null) {
                        viewHolder = adapter.getViewHolder(last);
                    } else {
                        last = i;
                    }
                    ImageView imageView = viewHolder.findViewById(imageViewId);
                    ImageAttr attr = getImageAttr(imageView, adapter.getListBean(i), imageFieldName);
                    if (attr.url == null) attr.url = "";
                    images.add(attr);
                }
            }

        }
        startActivity(recyclerView.getContext(), images, index);
    }


    /**
     * 普通的RecyclerView打开方式
     *
     * @param imageViewId    图片对应的id
     * @param imageFieldName 图片地址对应的url
     * @param position       被点击的位置
     */
    public static void startActivity(RecyclerView recyclerView, @IdRes int imageViewId, String imageFieldName, int position) {

        int startPosition;//从startPosition 开始的图片进行显示
        int endPosition;//到endPosition 图片结束显示
        BaseRecyclerViewAdapter adapter = (BaseRecyclerViewAdapter) recyclerView.getAdapter();

        startPosition = adapter.getFirstPosition();
        endPosition = adapter.getLastPosition();

        //如果已分组
        if (adapter instanceof GroupRecyclerAdapter) {
            GroupRecyclerAdapter adapterGroup = (GroupRecyclerAdapter) adapter;
            int groupPosition = adapterGroup.getGroupPosition(position);

            startPosition = groupPosition + 1;
            endPosition = adapterGroup.getGroupLastPosition(position);
        }
        if (endPosition < startPosition) return;
        startActivity(recyclerView, position, imageViewId, imageFieldName, BaseRecyclerViewAdapter.TYPE_CHILD, startPosition, endPosition);
    }

    //九宫格图片打开方式
    public static void startActivity(NineImageView nineImageView, List<ImageAttr> images, int index) {
        for (int i = 0; i < images.size(); i++) {
            ImageAttr attr = images.get(i);
            View imageView = nineImageView.getChildAt(i);
            if (i >= nineImageView.getMaxSize()) {
                imageView = nineImageView.getChildAt(nineImageView.getMaxSize() - 1);
            }
            attr.width = imageView.getWidth();
            attr.height = imageView.getHeight();
            int[] points = new int[2];
            imageView.getLocationInWindow(points);
            attr.left = points[0];
            attr.top = points[1];
        }
        startActivity(nineImageView.getContext(), images, index);
    }

    /**
     * @param imageView
     * @param bean
     * @param imageFieldName
     * @return
     */
    private static ImageAttr getImageAttr(View imageView, Object bean, String imageFieldName) {
        Object obj = JavaMethod.getFieldValue(bean, imageFieldName);
        if (obj == null) obj = "";
        ImageAttr attr = new ImageAttr();
        if (imageView != null) {
            attr.url = obj.toString();
            attr.width = imageView.getWidth();
            attr.height = imageView.getHeight();
            int[] points = new int[2];
            imageView.getLocationInWindow(points);
            attr.left = points[0];
            attr.top = points[1];
        }
        return attr;
    }

    private static void startActivity(Context context, List<ImageAttr> images, int index) {
        if (context instanceof Activity){
            Intent intent = new Intent(context, ImagesActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ImagesActivity.IMAGE_ATTR, (Serializable) images);
            bundle.putInt(ImagesActivity.CUR_POSITION, index);
            intent.putExtras(bundle);
            context.startActivity(intent);
            ((Activity) context).overridePendingTransition(0, 0);
        }

    }
    public static void startActivitys(Context context, List<ImageAttr> images, int index) {
        if (context instanceof Activity){
            Intent intent = new Intent(context, ImagesActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ImagesActivity.IMAGE_ATTR, (Serializable) images);
            bundle.putInt(ImagesActivity.CUR_POSITION, index);
            intent.putExtras(bundle);
            context.startActivity(intent);
            ((Activity) context).overridePendingTransition(0, 0);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        StatusBarUtil.setStatusBarTranslucent(this, false);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tvTip = (TextView) findViewById(R.id.tv_tip);
        rootView = (RelativeLayout) findViewById(R.id.rootView);
        screenWidth = DensityUtil.getScreenWidthPixels(this);
        screenHeight = DensityUtil.getScreenHeightPixels(this);

        Intent intent = getIntent();
        imageAttrs = (List<ImageAttr>) intent.getSerializableExtra(IMAGE_ATTR);
        curPosition = intent.getIntExtra(CUR_POSITION, 0);
        tvTip.setText(String.format(getString(R.string.image_index), (curPosition + 1), imageAttrs.size()));

        mAdapter = new ImagesAdapter(this, imageAttrs);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(curPosition);
        viewPager.getViewTreeObserver().addOnPreDrawListener(this);

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                curPosition = position;
                tvTip.setText(String.format(getString(R.string.image_index), (curPosition + 1), imageAttrs.size()));
            }
        });
    }

    @Override
    public void onBackPressed() {
        finishWithAnim();
    }

    private void initImageAttr(ImageAttr attr) {
        int originalWidth = attr.width;
        int originalHeight = attr.height;
        int originalCenterX = attr.left + originalWidth / 2;
        int originalCenterY = attr.top + originalHeight / 2;

        float widthRatio = screenWidth * 1.0f / originalWidth;
        float heightRatio = screenHeight * 1.0f / originalHeight;
        float ratio = widthRatio > heightRatio ? heightRatio : widthRatio;
        int finalWidth = (int) (originalWidth * ratio);
        int finalHeight = (int) (originalHeight * ratio);

        scaleX = originalWidth * 1.0f / finalWidth;
        scaleY = originalHeight * 1.0f / finalHeight;
        translationX = originalCenterX - screenWidth / 2;
        translationY = originalCenterY - screenHeight / 2;

        Log.d("--->", "(left, top): (" + attr.left + ", " + attr.top + ")");
        Log.d("--->", "originalWidth: " + originalWidth + " originalHeight: " + originalHeight);
        Log.d("--->", "finalWidth: " + finalWidth + " finalHeight: " + finalHeight);
        Log.d("--->", "scaleX: " + scaleX + " scaleY: " + scaleY);
        Log.d("--->", "translationX: " + translationX + " translationY: " + translationY);
        Log.d("--->", "" + attr.toString());
        Log.d("--->", "----------------------------------------------------------------");
    }

    @Override
    public boolean onPreDraw() {
        rootView.getViewTreeObserver().removeOnPreDrawListener(this);
        PhotoView photoView = mAdapter.getPhotoView(curPosition);
        ImageAttr attr = imageAttrs.get(curPosition);
        initImageAttr(attr);

        setBackgroundColor(0f, 1f, null);
        translateXAnim(photoView, translationX, 0);
        translateYAnim(photoView, translationY, 0);
        scaleXAnim(photoView, scaleX, 1);
        scaleYAnim(photoView, scaleY, 1);
        return true;
    }

    public void finishWithAnim() {
        PhotoView photoView = mAdapter.getPhotoView(curPosition);
        photoView.setScale(1f);
        ImageAttr attr = imageAttrs.get(curPosition);
        initImageAttr(attr);

        translateXAnim(photoView, 0, translationX);
        translateYAnim(photoView, 0, translationY);
        scaleXAnim(photoView, 1, scaleX);
        scaleYAnim(photoView, 1, scaleY);
        setBackgroundColor(1f, 0f, new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
                overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    private void translateXAnim(final PhotoView photoView, float from, float to) {
        ValueAnimator anim = ValueAnimator.ofFloat(from, to);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                photoView.setX((Float) valueAnimator.getAnimatedValue());
            }
        });
        anim.setDuration(ANIM_DURATION);
        anim.start();
    }

    private void translateYAnim(final PhotoView photoView, float from, float to) {
        ValueAnimator anim = ValueAnimator.ofFloat(from, to);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                photoView.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        anim.setDuration(ANIM_DURATION);
        anim.start();
    }

    private void scaleXAnim(final PhotoView photoView, float from, float to) {
        ValueAnimator anim = ValueAnimator.ofFloat(from, to);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                photoView.setScaleX((Float) valueAnimator.getAnimatedValue());
            }
        });
        anim.setDuration(ANIM_DURATION);
        anim.start();
    }

    private void scaleYAnim(final PhotoView photoView, float from, float to) {
        ValueAnimator anim = ValueAnimator.ofFloat(from, to);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                photoView.setScaleY((Float) valueAnimator.getAnimatedValue());
            }
        });
        anim.setDuration(ANIM_DURATION);
        anim.start();
    }

    private void setBackgroundColor(float from, float to, Animator.AnimatorListener listener) {
        ValueAnimator anim = ValueAnimator.ofFloat(from, to);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rootView.setBackgroundColor(ColorUtil.evaluate((Float) valueAnimator.getAnimatedValue(), Color.TRANSPARENT, Color.BLACK));
            }
        });
        anim.setDuration(ANIM_DURATION);
        if (listener != null) {
            anim.addListener(listener);
        }
        anim.start();
    }
}
