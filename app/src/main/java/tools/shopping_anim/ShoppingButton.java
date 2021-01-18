package tools.shopping_anim;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.utils.BitmapUtil;
import com.base.utils.DensityUtil;

import cn.com.taodaji.R;

public abstract class ShoppingButton extends LinearLayout implements View.OnClickListener, ShopCartInterface {

    public ImageView iv_add;
    public ImageView iv_subtract;
    public TextView tv_count;
    private View mShoppingCart;//购物车
    private ViewGroup mMainLayout;//toolbar下面的主窗体
    public int maxCount;

    public void setmMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }



    public ShoppingButton(Context context) {
        super(context);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        initView(context);
    }

    public ShoppingButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ShoppingButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    public void setmShoppingCart(View mShoppingCart) {
        this.mShoppingCart = mShoppingCart;
    }

    public void setmMainLayout(ViewGroup mMainLayout) {
        this.mMainLayout = mMainLayout;
    }

    private void initView(final Context context) {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        //减少
        iv_subtract = new ImageView(context);
        iv_subtract.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        Drawable drawable = BitmapUtil.getDrawable(R.mipmap.ic_shopping_subtract);
        Drawable drawable = BitmapUtil.getDrawable(R.mipmap.jianhao_bg);
        int w = drawable.getMinimumWidth()+20;
        int h = drawable.getMinimumHeight();
        iv_subtract.setImageDrawable(drawable);
        iv_subtract.setVisibility(GONE);
        addView(iv_subtract);
        iv_subtract.setOnClickListener(v -> {
            if (countChanged(false)) showTotalPrice(getGoodsCount() - 1);
        });

        //数量
        tv_count = new TextView(context);

        LayoutParams layoutParams = new LayoutParams(w, h);
        layoutParams.leftMargin = 8;
        layoutParams.rightMargin = 8;
        tv_count.setLayoutParams(layoutParams);
        tv_count.setGravity(Gravity.CENTER);
        tv_count.setText("0");
        tv_count.setVisibility(GONE);
        tv_count.setTextSize(9.0f);
        tv_count.setBackgroundResource(R.drawable.z_round_rect_gray_df_fffdfe);
//        tv_count.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
//        Drawable drawable = BitmapUtil.getDrawable(R.drawable.line_ff7d01);
//        tv_count.setPadding(0, 0, 0, DensityUtil.dp2px(6));
//        drawable.setBounds(DensityUtil.dp2px(1), 0, DensityUtil.dp2px(28), 5);
//        tv_count.setCompoundDrawables(null, null, null, drawable);

        addView(tv_count);
        tv_count.setOnClickListener(this);
        //添加
        iv_add = new ImageView(context);
        iv_add.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        iv_add.setImageResource(R.mipmap.ic_shopping_add);
        iv_add.setImageResource(R.mipmap.jiahao_bg);
        addView(iv_add);
        iv_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = getGoodsCount() + 1;
                if (countChanged(true)) {
                    showTotalPrice(count);
                    if (mShoppingCart == null || mMainLayout == null) return;

                    int[] addLocation = new int[2];
                    int[] cartLocation = new int[2];
                    int[] recycleLocation = new int[2];
                    view.getLocationInWindow(addLocation);
                    mShoppingCart.getLocationInWindow(cartLocation);
                    mMainLayout.getLocationInWindow(recycleLocation);

                    PointF startP = new PointF();
                    PointF endP = new PointF();
                    PointF controlP = new PointF();

                    startP.x = addLocation[0];
                    startP.y = addLocation[1] - recycleLocation[1];
                    endP.x = cartLocation[0];
                    endP.y = cartLocation[1] - recycleLocation[1];
                    controlP.x = endP.x;
                    controlP.y = startP.y;

                    final ShoppingAddImageView rxFakeAddImageView = new ShoppingAddImageView(context);
                    mMainLayout.addView(rxFakeAddImageView);
//                    rxFakeAddImageView.setImageResource(R.mipmap.ic_shopping_add);
                    rxFakeAddImageView.setImageResource(R.mipmap.jiahao_bg);
                    rxFakeAddImageView.getLayoutParams().width = DensityUtil.dp2px(30);
                    rxFakeAddImageView.getLayoutParams().height = DensityUtil.dp2px(30);
                    rxFakeAddImageView.setVisibility(View.VISIBLE);
                    ObjectAnimator addAnimator = ObjectAnimator.ofObject(rxFakeAddImageView, "mPointF", new PointFTypeEvaluator(controlP), startP, endP);
                    addAnimator.setInterpolator(new AccelerateInterpolator());
                    addAnimator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                            rxFakeAddImageView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            rxFakeAddImageView.setVisibility(View.GONE);
                            mMainLayout.removeView(rxFakeAddImageView);

                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
                    ObjectAnimator scaleAnimatorX = new ObjectAnimator().ofFloat(mShoppingCart, "scaleX", 0.6f, 1.0f);
                    ObjectAnimator scaleAnimatorY = new ObjectAnimator().ofFloat(mShoppingCart, "scaleY", 0.6f, 1.0f);
                    scaleAnimatorX.setInterpolator(new AccelerateInterpolator());
                    scaleAnimatorY.setInterpolator(new AccelerateInterpolator());
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.play(scaleAnimatorX).with(scaleAnimatorY).after(addAnimator);
                    animatorSet.setDuration(800);
                    animatorSet.start();

                }


            }
        });
    }

    public int getGoodsCount() {
        if (tv_count == null) {
            return 0;
        } else {
            String ss = tv_count.getText().toString();
            return Integer.valueOf(ss);
        }
    }

    public void setGoodsCount(int count) {
        showTotalPrice(count);
        if (tv_count != null) {
            tv_count.setText(String.valueOf(count));
        }
    }

    /**
     * @param count
     */
    private void showTotalPrice(int count) {
        if (iv_subtract != null && tv_count != null) {
            if (count > 0) {
                tv_count.setVisibility(VISIBLE);
                iv_subtract.setVisibility(VISIBLE);
            } else {
                tv_count.setVisibility(GONE);
                iv_subtract.setVisibility(GONE);
            }
        }
    }
}
