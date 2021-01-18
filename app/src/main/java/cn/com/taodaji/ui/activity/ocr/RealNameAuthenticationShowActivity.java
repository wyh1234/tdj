package cn.com.taodaji.ui.activity.ocr;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import tools.activity.SimpleToolbarActivity;

import com.base.glide.GlideImageView;
import com.base.glide.ImageLoaderUtils;
import com.base.utils.ViewUtils;


/**
 * Created by Administrator on 2017-12-26.
 */

public class RealNameAuthenticationShowActivity extends SimpleToolbarActivity {
    private View mainView;
    private TextView mTvName, mTvIdNumber;
    private GlideImageView mImgFront, mImgBack;
    private String name, idNumber;
    private String imageFrontPath, imageBackPath;
    private LinearLayout ll_heard;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("实名认证");
    }

    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.activity_real_name_authentication_show);
        body_scroll.addView(mainView);

        ll_heard = ViewUtils.findViewById(mainView, R.id.ll_heard);
        setViewBackColor(ll_heard);
        mTvName = ViewUtils.findViewById(mainView, R.id.real_name_tv);
        mTvIdNumber = ViewUtils.findViewById(mainView, R.id.real_id_number_tv);
        mImgFront = ViewUtils.findViewById(mainView, R.id.id_card_fornt_image);
        mImgBack = ViewUtils.findViewById(mainView, R.id.id_card_back_image);
        String frontImage, backImage;


        if (PublicCache.loginPurchase != null) {
            mTvName.setText(PublicCache.loginPurchase.getRealname());
            mTvIdNumber.setText(disposeString(PublicCache.loginPurchase.getIdentificationCard()));
            frontImage = PublicCache.loginPurchase.getFrontageIdcardImageUrl();
            backImage = PublicCache.loginPurchase.getBackIdcardImageUrl();
        } else if (PublicCache.loginSupplier != null) {
            mTvName.setText(PublicCache.loginSupplier.getRealname());
            mTvIdNumber.setText(disposeString(PublicCache.loginSupplier.getIdcardNumber()));
            frontImage = PublicCache.loginSupplier.getFrontageIdcardImageUrl();
            backImage = PublicCache.loginSupplier.getBackIdcardImageUrl();
        } else return;

        ImageLoaderUtils.loadImage(mImgFront,  frontImage);
        ImageLoaderUtils.loadImage(mImgBack,  backImage);
    }

    public String disposeString(String cardNO) {
        if (TextUtils.isEmpty(cardNO) || cardNO.length() < 18) return cardNO;
        else {
            return cardNO.substring(0, 15) + "***" + cardNO.substring(17);
        }
    }


}
