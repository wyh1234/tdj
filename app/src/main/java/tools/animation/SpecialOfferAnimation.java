package tools.animation;

import android.content.Context;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import cn.com.taodaji.R;


public class SpecialOfferAnimation {

    private AnimationSet animationSet;
//    private Context mContext;
    private ImageView mImageView;
    private Animation animation1, animation2;
    private TranslateAnimation animation5;

    public SpecialOfferAnimation(Context context, ImageView imageView) {
//        mContext = context;
        mImageView = imageView;

        //第一个"抢"字位移动画   从左进入

        animation1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f
                , Animation.RELATIVE_TO_SELF, 0f);
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mImageView.setImageResource(R.mipmap.ani_tj_1);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mImageView.startAnimation(animation5);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation1.setDuration(300);
        animation1.setStartOffset(0);
        animation1.setFillAfter(true);
        animation1.setInterpolator(context, android.R.anim.accelerate_interpolator);

        //第一个"抢"字位移动画   从右出去
        animation5 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0f
                , Animation.RELATIVE_TO_SELF, 0f);
        animation5.setDuration(500);
        animation5.setStartOffset(1500);
        animation5.setInterpolator(context, android.R.anim.decelerate_interpolator);
        animation5.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mImageView.startAnimation(animation2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //第二个“特价”位移动画

        animation2 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f
                , Animation.RELATIVE_TO_SELF, 0f);
        animation2.setDuration(800);
        animation2.setFillAfter(true);
        //延迟500让第一个动画播放完成
        animation2.setStartOffset(0);
        animation2.setInterpolator(context, android.R.anim.overshoot_interpolator);
        animation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mImageView.setImageResource(R.mipmap.ani_tj_2);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mImageView.startAnimation(animationSet);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        //第三个“特价”放大动画    放大和淡出同时播放
        Animation animation3 = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation3.setDuration(500);
        animation3.setStartOffset(1000);
        animation3.setInterpolator(context, android.R.anim.linear_interpolator);

        //第四个“特价”淡出动画
        Animation animation4 = new AlphaAnimation(1.0f, 0f);
        animation4.setDuration(500);
        animation4.setStartOffset(1000);
        animation4.setInterpolator(context, android.R.anim.linear_interpolator);


        animationSet = new AnimationSet(true);

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mImageView.startAnimation(animation1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animationSet.addAnimation(animation3);
        animationSet.addAnimation(animation4);

    }

    public void startAnimation() {
        mImageView.startAnimation(animation1);
    }

}
