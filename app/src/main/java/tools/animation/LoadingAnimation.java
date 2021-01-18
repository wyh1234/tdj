package tools.animation;

import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

/**
 * Created by yangkuo on 2018/11/17.
 */
public class LoadingAnimation {

    private long time = 300;//动画执行时长


    private ImageView iv_arm_left, iv_arm_right, iv_foot_left, iv_foot_right;

    private Animation animation_arm_left, animation_foot_left, animation_arm_right;

    private AnimationSet animationSet_foot_right;

    public LoadingAnimation(ImageView iv_arm_left, ImageView iv_arm_right, ImageView iv_foot_left, ImageView iv_foot_right) {

        this.iv_arm_left = iv_arm_left;
        this.iv_arm_right = iv_arm_right;
        this.iv_foot_left = iv_foot_left;
        this.iv_foot_right = iv_foot_right;


        //左胳膊从下面往上面摆动
        animation_arm_left = new RotateAnimation(-45, -15, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation_arm_left.setDuration(time);


        //左脚从上面往下面摆动
        animation_foot_left = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 10, Animation.RELATIVE_TO_SELF, 100, Animation.RELATIVE_TO_SELF, 10, Animation.RELATIVE_TO_SELF, 100);
        animation_foot_left.setDuration(time);


        //右胳膊从上面往下面摆动
        animation_arm_right = new RotateAnimation(10, -30, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation_arm_right.setDuration(time);


        //右脚从下面往上面摆动,位移和放大同时
        animationSet_foot_right = new AnimationSet(true);
        Animation animation_foot_right_t = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 10, Animation.RELATIVE_TO_SELF, 100, Animation.RELATIVE_TO_SELF, 10, Animation.RELATIVE_TO_SELF, 100);
        animation_foot_right_t.setDuration(time);
        Animation animation_foot_right_s = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation_foot_right_s.setDuration(time);
        animationSet_foot_right.addAnimation(animation_foot_right_t);
        animationSet_foot_right.addAnimation(animation_foot_right_s);

    }

    public void startAnimation() {
        iv_arm_left.startAnimation(animation_arm_left);
        iv_arm_right.startAnimation(animation_arm_right);
        iv_foot_right.startAnimation(animationSet_foot_right);
        iv_foot_left.startAnimation(animation_foot_left);
    }

    public void endAnimation() {
        iv_arm_left.clearAnimation();
        iv_arm_right.clearAnimation();
        iv_foot_right.clearAnimation();
        iv_foot_left.clearAnimation();
    }
}
