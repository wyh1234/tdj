package cn.com.taodaji.ui.activity.myself;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.glide.GlideImageView;
import com.base.glide.ImageLoaderUtils;
import com.base.utils.JavaMethod;
import com.base.utils.UIDataProcessing;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.ImageUploadOk;
import cn.com.taodaji.ui.activity.login.PhoneReBindFirstActivity;
import cn.com.taodaji.ui.activity.ocr.RealNameAuthenticationActivity;
import cn.com.taodaji.ui.activity.ocr.RealNameAuthenticationShowActivity;
import cn.com.taodaji.ui.activity.shopManagement.SaleCategoryManagementActivity;
import tools.activity.CollapseToolbarActivity;

public class SypplyNameCardActivity extends CollapseToolbarActivity implements View.OnClickListener {
    private boolean hasGotToken = false;
    private AlertDialog.Builder alertDialog;

    private View mainView;
    private TextView shop_name, tv_supply_sale_type,tv_shop_evaluate;
    private ImageView img_go_sale_type;

    //private EditText shop_name_hint;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isScreenVertical=false;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor(R.color.transparent);
        setToolBarColor(R.color.blue_2898eb);
        if (detail_title != null) {
            detail_title.setText("我的名片");
            detail_title.setPadding(0, 0, UIUtils.dip2px(33), 0);
        }
//        toolbar.setContentInsetEndWithActions(UIUtils.dip2px(16));
//        toolbar.setContentInsetStartWithNavigation(UIUtils.dip2px(66));
//        toolbar.setContentInsetsRelative(0,0);
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initMainView() {


        alertDialog = new AlertDialog.Builder(this);
        mainView = getLayoutView(R.layout.activity_myself_supply_name_card);
        collapse_content.addView(mainView);

        View view = ViewUtils.getLayoutView(this, R.layout.activity_myself_supply_name_card_top);
        title_collapse_view.addView(view);
        myself_headportrait = view.findViewById(R.id.myself_headportrait);
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
        auditing_message_close = view.findViewById(R.id.auditing_message_close);
        auditing_message_close.setOnClickListener(this);
        shop_name = view.findViewById(R.id.shop_name);
        tv_shop_evaluate = view.findViewById(R.id.tv_shop_evaluate);
        tv_supply_sale_type = view.findViewById(R.id.tv_supply_sale_type);
        img_go_sale_type = view.findViewById(R.id.img_go_sale_type);
        img_go_sale_type.setOnClickListener(this);


        // shop_name_hint = ViewUtils.findViewById(mainView, R.id.shop_name_hint);

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


        myself_name_card_station_address_linearlayout = ViewUtils.findViewById(mainView, R.id.myself_name_card_station_address_linearlayout);

        bindChange = ViewUtils.findViewById(mainView, R.id.bindChange);
        certification = ViewUtils.findViewById(mainView, R.id.certification);
        licence = ViewUtils.findViewById(mainView, R.id.licence);
        foodQuality = ViewUtils.findViewById(mainView, R.id.foodQuality);


        myself_name_card_station_address_linearlayout.setOnClickListener(this);
        bindChange.setOnClickListener(this);
        certification.setOnClickListener(this);
        licence.setOnClickListener(this);
        foodQuality.setOnClickListener(this);



       /* if (PublicCache.loginPurchase != null) {
            imageParamsKey = "imageUrl";
            if (PublicCache.loginPurchase != null && PublicCache.loginPurchase.getImageUrl() != null) {
                imageUrl = PublicCache.loginPurchase.getImageUrl();
            }
            mLinearLayout.setVisibility(View.GONE);

            LinearLayout bindChange = ViewUtils.findViewById(mainView, R.id.bindChange);
            if (PublicCache.loginPurchase.getRole() != 0) {
                bindChange.setVisibility(View.GONE);
            } else {
                bindChange.setVisibility(View.VISIBLE);
            }


        } else*/
        if (PublicCache.loginSupplier != null) {
            imageParamsKey = "storePics";
            imageUrl = PublicCache.loginSupplier.getStorePics();
            //mLinearLayout.setVisibility(View.VISIBLE);
        } else return;

        ImageLoaderUtils.loadImage(myself_headportrait, imageUrl);


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
            ImageLoaderUtils.loadImage(myself_headportrait, imageUrl);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        initDatas();
    }


    private void initDatas() {
        Map<String, Object> map = null;
        if (PublicCache.loginSupplier != null) {
            if (TextUtils.isEmpty(PublicCache.loginSupplier.getLicencePics())) {
                licence_text.setText("证件上传");
            } else licence_text.setText("查看");


            if (TextUtils.isEmpty(PublicCache.loginSupplier.getFoodQualityPics())) {
                foodQuality_text.setText("证件上传");
            } else foodQuality_text.setText("查看");

            shop_name.setText(PublicCache.loginSupplier.getStoreName());
            tv_shop_evaluate.setVisibility(View.VISIBLE);
            tv_shop_evaluate.setText(getIntent().getStringExtra("storeScore")+"分");
           tv_supply_sale_type.setText(PublicCache.loginSupplier.getStoreCategory());

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

            case R.id.img_go_sale_type:
                break;
            case R.id.myself_name_card_station_address_linearlayout:
                Intent intent4 = new Intent(getBaseActivity(), ShippingAddressActivity.class);
                startActivity(intent4);
                break;
            case R.id.bindChange:
                //手机换绑
                Intent intent5 = new Intent(getBaseActivity(), PhoneReBindFirstActivity.class);
                startActivity(intent5);
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
                ViewUtils.findViewById(title_collapse_view, R.id.auditing_message_group).setVisibility(View.GONE);
                break;
        }
    }
}
