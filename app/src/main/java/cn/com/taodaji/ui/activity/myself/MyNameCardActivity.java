package cn.com.taodaji.ui.activity.myself;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.com.taodaji.ui.activity.ocr.RealNameAuthenticationActivity;
import cn.com.taodaji.ui.activity.ocr.RealNameAuthenticationShowActivity;
import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;

import java.util.Map;

import cn.com.taodaji.model.entity.ImageUploadOk;
import cn.com.taodaji.ui.activity.login.PhoneReBindFirstActivity;
import tools.activity.SimpleToolbarActivity;

import com.base.glide.GlideImageView;
import com.base.glide.ImageLoaderUtils;
import com.base.utils.JavaMethod;
import com.base.utils.UIDataProcessing;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

public class MyNameCardActivity extends SimpleToolbarActivity implements View.OnClickListener {
    private boolean hasGotToken = false;
    private AlertDialog.Builder alertDialog;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("我的名片");
    }

    private View mainView;
    private TextView shop_name;
    private EditText shop_name_hint;
    private TextView foodQuality_name;
    private TextView foodQuality_text;
    private TextView auditing_message;
    private TextView certification_text, licence_text, qq_number;
    private GlideImageView myself_headportrait;
    private LinearLayout mLinearLayout;
    String cardimage = null;
    String cardno = "0";//是否实名认证
    String imageUrl = "";
    private LinearLayout qq_number_group;
    String imageParamsKey = "";
    private String token;

    private LinearLayout myself_name_card_station_address_linearlayout, bindChange, certification, licence, foodQuality;
    private GlideImageView auditing_message_close;

    @SuppressLint("WrongConstant")
    @Override
    protected void initMainView() {
        alertDialog = new AlertDialog.Builder(this);
        mainView = getLayoutView(R.layout.activity_myself_name_card);
        body_scroll.addView(mainView);

        shop_name = ViewUtils.findViewById(mainView, R.id.shop_name);
        shop_name_hint = ViewUtils.findViewById(mainView, R.id.shop_name_hint);

        foodQuality_name = ViewUtils.findViewById(mainView, R.id.foodQuality_name);
        foodQuality_text = ViewUtils.findViewById(mainView, R.id.foodQuality_text);
        certification_text = ViewUtils.findViewById(mainView, R.id.certification_text);
        licence_text = ViewUtils.findViewById(mainView, R.id.licence_text);
        mLinearLayout = ViewUtils.findViewById(mainView, R.id.myself_name_card_station_address_linearlayout);

        qq_number_group = ViewUtils.findViewById(mainView, R.id.qq_number_group);
        qq_number = ViewUtils.findViewById(mainView, R.id.qq_number);
        qq_number_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseActivity(), QQNumberAddActivity.class);
                startActivity(intent);
            }
        });
        myself_headportrait = ViewUtils.findViewById(mainView, R.id.myself_headportrait);
        myself_headportrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** 头像设置*/

                Intent intent = new Intent(getBaseActivity(), ImageUploadActivity.class);
                intent.putExtra("title", "头像上传");
                intent.putExtra("isCut", true);//默认为false
                intent.putExtra("imageUrl", imageUrl);
                intent.putExtra("imageDescription", HEAD_PORTRAIT_UPLOAD);
                intent.putExtra("imageParamsKey", imageParamsKey);
                startActivity(intent);
            }
        });


        myself_name_card_station_address_linearlayout = ViewUtils.findViewById(mainView, R.id.myself_name_card_station_address_linearlayout);
        bindChange = ViewUtils.findViewById(mainView, R.id.bindChange);
        certification = ViewUtils.findViewById(mainView, R.id.certification);
        licence = ViewUtils.findViewById(mainView, R.id.licence);
        foodQuality = ViewUtils.findViewById(mainView, R.id.foodQuality);
        auditing_message_close = ViewUtils.findViewById(mainView, R.id.auditing_message_close);

        myself_name_card_station_address_linearlayout.setOnClickListener(this);
        bindChange.setOnClickListener(this);
        certification.setOnClickListener(this);
        licence.setOnClickListener(this);
        foodQuality.setOnClickListener(this);
        auditing_message_close.setOnClickListener(this);


        if (PublicCache.loginPurchase != null) {
            imageParamsKey = "imageUrl";
            if (PublicCache.loginPurchase != null && PublicCache.loginPurchase.getImageUrl() != null) {
                imageUrl = PublicCache.loginPurchase.getImageUrl();
            }
            mLinearLayout.setVisibility(View.GONE);

            LinearLayout bindChange = ViewUtils.findViewById(mainView, R.id.bindChange);
            if (PublicCache.loginPurchase.getFlag() != 1) {
                bindChange.setVisibility(View.GONE);
            } else {
                bindChange.setVisibility(View.VISIBLE);
            }


        } else if (PublicCache.loginSupplier != null) {
            imageParamsKey = "storePics";
            imageUrl = PublicCache.loginSupplier.getStorePics();
            mLinearLayout.setVisibility(View.VISIBLE);
        } else return;

        ImageLoaderUtils.loadImage(myself_headportrait,  imageUrl);


        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //头像上传完毕后返回
    @Subscribe
    public void onEvent(ImageUploadOk ok) {
        if (ok.getData() instanceof Boolean) return;
        if (ok.getMsg().equals(HEAD_PORTRAIT_UPLOAD)) {
            imageUrl = ok.getData().toString();
            ImageLoaderUtils.loadImage(myself_headportrait,  imageUrl);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        initDatas();
    }


    private void initDatas() {
        Map<String, Object> map = null;
        if (PublicCache.loginPurchase != null) {
            shop_name.setText("单位全称：");
            shop_name_hint.setTag(UIUtils.getString(R.string.hotelName_tag));
            ViewUtils.findViewById(mainView, R.id.purchaser_hide).setVisibility(View.GONE);
            foodQuality_name.setText("收货地址：");
            foodQuality_text.setText("管理地址");
            auditing_message = ViewUtils.findViewById(mainView, R.id.auditing_message);
            auditing_message.setText("请完善资料信息。");
            if (TextUtils.isEmpty(PublicCache.loginPurchase.getBzlicenceUrl())) {
                licence_text.setText("证件上传");
            } else licence_text.setText("查看");
            cardno = PublicCache.loginPurchase.getIsAuth();
            cardimage = PublicCache.loginPurchase.getIdentificationImage();

            qq_number_group.setVisibility(View.GONE);

            map = JavaMethod.transBean2Map(PublicCache.loginPurchase);
        } else if (PublicCache.loginSupplier != null) {
            if (TextUtils.isEmpty(PublicCache.loginSupplier.getLicencePics())) {
                licence_text.setText("证件上传");
            } else licence_text.setText("查看");


            if (TextUtils.isEmpty(PublicCache.loginSupplier.getFoodQualityPics())) {
                foodQuality_text.setText("证件上传");
            } else foodQuality_text.setText("查看");

            cardno = PublicCache.loginSupplier.getIsAuth();
            cardimage = PublicCache.loginSupplier.getIdcardPics();
            qq_number_group.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(PublicCache.loginSupplier.getQqNumber())) {
                PublicCache.loginSupplier.setQqNumber("添加");
            }
            map = JavaMethod.transBean2Map(PublicCache.loginSupplier);

        } else return;

        if (cardno.equals("0")) {
            certification_text.setText("证件上传");
        } else certification_text.setText("查看");
        UIDataProcessing.setChildControlsValue(mainView, map);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.myself_name_card_station_address_linearlayout:
                Intent intent4 = new Intent(getBaseActivity(), ShippingAddressActivity.class);
                startActivity(intent4);
                break;
            case R.id.bindChange:
                //手机换绑
                Intent intent1 = new Intent(getBaseActivity(), PhoneReBindFirstActivity.class);
                startActivity(intent1);
                break;
            case R.id.certification:
//                //实名认证
//                cardno = "0";
                if (cardno.equals("0")) {
                    startActivity(new Intent(this, RealNameAuthenticationActivity.class));
                } else {
                    startActivity(new Intent(this, RealNameAuthenticationShowActivity.class));
                }
                break;
            case R.id.licence:
                //营业执照
                String imageUrl = null;
                switch (PublicCache.login_mode) {
                    case PURCHASER:
                        imageUrl = PublicCache.loginPurchase.getBzlicenceUrl();
                        break;
                    case SUPPLIER:
                        imageUrl = PublicCache.loginSupplier.getLicencePics();
                        break;
                }
                intent = new Intent(this, ImageUploadActivity.class);
                intent.putExtra("title", "营业执照上传");
                intent.putExtra("imageUrl", imageUrl);
                intent.putExtra("imageDescription", LICENCE_UPLOAD);
                intent.putExtra("imageParamsKey", "licencePics");
                startActivity(intent);
                break;
            case R.id.foodQuality:
                //供应商是食品资格证，采购商是管理收货地址
                switch (PublicCache.login_mode) {
                    case PURCHASER:
                        //管理收货地址
                        Intent intent2 = new Intent(this, GoodsReceiptAddressActivity.class);
                        intent2.putExtra("flg", true);
                        startActivity(intent2);
                        break;
                    case SUPPLIER:
                        intent = new Intent(this, ImageUploadActivity.class);
                        intent.putExtra("title", "经营许可证上传");
                        intent.putExtra("imageUrl", PublicCache.loginSupplier.getFoodQualityPics());
                        intent.putExtra("imageDescription", FOOD_QUALITY_UPLOAD);
                        intent.putExtra("imageParamsKey", "foodQualityPics");
                        startActivityForResult(intent, 300);
                        break;
                }

                break;
            case R.id.auditing_message_close:
                ViewUtils.findViewById(mainView, R.id.auditing_message_group).setVisibility(View.GONE);
                break;
        }
    }
}
