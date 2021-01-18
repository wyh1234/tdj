package cn.com.taodaji.ui.activity.myself;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.ImageUploadOk;
import cn.com.taodaji.model.entity.MarketLocal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.base.retrofit.RequestCallback;
import tools.activity.SimpleToolbarActivity;

import com.base.utils.JavaMethod;
import com.base.utils.UIDataProcessing;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

public class DataModificationActivity extends SimpleToolbarActivity implements View.OnClickListener {


    private View mainView;
    private TextView shop_name;
    private TextView licence_text;
    private TextView foodQuality_text;
    private EditText shop_name_hint;
    private LinearLayout purchaser_hide;
    private LinearLayout foodQuality;
    private LinearLayout licence;
    private Button data_modification_OK;
    private TextView address_detail;
    private Spinner register_market_localhost;
    private List<Map<String, Object>> market_localhost = new ArrayList<>();
    private SimpleAdapter adapte_market;
    private Map<String, Object> map = null;

    private TextView place;

    private int site;
    private String cityName = "";

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("修改资料");

    }

    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.activity_myself_data_modification);
        body_scroll.addView(mainView);

        shop_name = ViewUtils.findViewById(mainView, R.id.shop_name);
        shop_name_hint = ViewUtils.findViewById(mainView, R.id.shop_name_hint);
        purchaser_hide = ViewUtils.findViewById(mainView, R.id.purchaser_hide);
        licence_text = ViewUtils.findViewById(mainView, R.id.licence_text);
        foodQuality_text = ViewUtils.findViewById(mainView, R.id.foodQuality_text);

        foodQuality = ViewUtils.findViewById(mainView, R.id.foodQuality);
        foodQuality.setOnClickListener(this);
        licence = ViewUtils.findViewById(mainView, R.id.licence);
        licence.setOnClickListener(this);
        data_modification_OK = ViewUtils.findViewById(mainView, R.id.data_modification_OK);
        data_modification_OK.setOnClickListener(this);

        address_detail = ViewUtils.findViewById(mainView, R.id.address_detail);
        place = ViewUtils.findViewById(mainView, R.id.place);
        place.setOnClickListener(this);
        register_market_localhost = ViewUtils.findViewById(mainView, R.id.register_market_localhost);
        adapte_market = new SimpleAdapter(this, market_localhost, android.R.layout.simple_spinner_item, new String[]{"name"}, new int[]{android.R.id.text1});
        adapte_market.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        register_market_localhost.setAdapter(adapte_market);
        register_market_localhost.setTag(R.id.tag_first, "id");

        login_mode(PublicCache.login_mode);//切换注册用户类型

    }


    @Override
    protected void onResume() {
        super.onResume();
        login_mode(PublicCache.login_mode);//切换注册用户类型
    }

    @Override
    protected void initData() {

        if (PublicCache.loginPurchase != null) {
            site = PublicCache.loginPurchase.getSite();
            cityName = PublicCache.loginPurchase.getSiteName();
            map = JavaMethod.transBean2Map(PublicCache.loginPurchase);
        } else if (PublicCache.loginSupplier != null) {
            site = PublicCache.loginSupplier.getSite();
            cityName = PublicCache.loginSupplier.getSiteName();
            map = JavaMethod.transBean2Map(PublicCache.loginSupplier);
        } else return;


        if (map != null) {
            UIDataProcessing.setChildControlsValue(mainView, map);
        }
        place.setText(cityName);
        //获取市场信息
        if (PublicCache.login_mode.equals(SUPPLIER)) {
            marker_data();
        }
    }


    private void marker_data() {
        if (PublicCache.login_mode.equals(PURCHASER)) return;
        market_localhost.clear();
        //从网络获取本地市场
        Map<String, Object> market_map = new HashMap();
        market_map.put("type", "1");//1所有，2绑定，3未绑定的
        getRequestPresenter().getMarket_local(market_map, site, new RequestCallback<MarketLocal>() {
            @Override
            public void onSuc(MarketLocal body) {
                for (MarketLocal.DataBean dataBean : body.getData()) {
                    Map<String, Object> map_market = new HashMap<>();
                    map_market.put("id", dataBean.getEntityId());
                    map_market.put("name", dataBean.getName());
                    market_localhost.add(map_market);
                }
                adapte_market.notifyDataSetChanged();
                int cot = market_localhost.size();
                for (int a = 0; a < cot; a++) {
                    Map map1 = market_localhost.get(a);
                    if (map1 != null && !map1.isEmpty() && PublicCache.loginSupplier.getMarketId() == Integer.valueOf(map1.get("id").toString())) {
                        register_market_localhost.setSelection(a, true);
                        break;
                    }
                }
            }

            @Override
            public void onFailed(int marketLocal, String msg) {
                UIUtils.showToastSafesShort("获取市场信息失败");
            }
        });
    }

    public void editTextOnclick(View view) {
        ((EditText) view).setCursorVisible(true);
        ((EditText) view).setTextColor(UIUtils.getColor(R.color.gray_66));
    }

    private void login_mode(String register_flag) {
        switch (register_flag) {
            case PURCHASER:
                if (PublicCache.loginPurchase == null) return;

                shop_name.setText(UIUtils.getString(R.string.organization_name));
                shop_name_hint.setHint(UIUtils.getString(R.string.organization_name_hint));
                purchaser_hide.setVisibility(View.GONE);

                data_modification_OK.setBackgroundResource(R.drawable.r_round_rect_solid_orange_ff7d01);
                address_detail.setTag(UIUtils.getString(R.string.hotelAddress_tag));
                shop_name_hint.setTag(UIUtils.getString(R.string.hotelName_tag));

                foodQuality.setVisibility(View.GONE);

                if (PublicCache.loginPurchase.getAuthStatus() != 2 && !TextUtils.isEmpty(PublicCache.loginPurchase.getBzlicenceUrl())) {
                    licence_text.setText("审核中");
                }

                break;
            case SUPPLIER:
                if (PublicCache.loginSupplier == null) return;

                shop_name.setText(UIUtils.getString(R.string.shop_name));
                shop_name_hint.setHint(UIUtils.getString(R.string.shop_name_hint));
                purchaser_hide.setVisibility(View.VISIBLE);

                data_modification_OK.setBackgroundResource(R.drawable.r_round_rect_solid_blue_2898eb);
                address_detail.setTag(UIUtils.getString(R.string.storeAddress_tag));
                shop_name_hint.setTag(UIUtils.getString(R.string.storeName_tag));

                foodQuality.setVisibility(View.VISIBLE);

                if (PublicCache.loginSupplier.getAuthStatus() != 2) {
                    if (!TextUtils.isEmpty(PublicCache.loginSupplier.getLicencePics())) {
                        licence_text.setText("审核中");
                    }
                    if (!TextUtils.isEmpty(PublicCache.loginSupplier.getFoodQualityPics())) {
                        foodQuality_text.setText("审核中");
                    }
                }


                break;
        }
    }

    private Map<String, Object> values_map;

    @Override
    public void onClick(View view) {

        Intent intent = null;
        switch (view.getId()) {
            case R.id.foodQuality:
                /** 食品资格证上传或查看*/
                //3为未认证
/*                if ("审核中".equals(foodQuality_text.getText().toString())) {
                    //查看页面
                    ImageViewPopupWindow imageViewPopupWindow = new ImageViewPopupWindow(this);
                    imageViewPopupWindow.setPopupWindow_imageView_src(PublicCache.loginSupplier.getLicencePics());
                    imageViewPopupWindow.showAtLocation(mainView, Gravity.CENTER, 0, 0);
                } else {
                    intent = new Intent(this, ImageUploadActivity.class);
                    intent.putExtra("title", "经营许可证上传");
                    intent.putExtra("imageUrl", PublicCache.loginSupplier.getFoodQualityPics());
                    intent.putExtra("imageDescription", FOOD_QUALITY_UPLOAD);
                    intent.putExtra("imageParamsKey", "foodQualityPics");
                    startActivityForResult(intent, 300);
                }*/
                intent = new Intent(this, ImageUploadActivity.class);
                intent.putExtra("title", "经营许可证上传");
                intent.putExtra("imageUrl", PublicCache.loginSupplier.getFoodQualityPics());
                intent.putExtra("imageDescription", FOOD_QUALITY_UPLOAD);
                intent.putExtra("imageParamsKey", "foodQualityPics");
                startActivityForResult(intent, 300);

                break;
            case R.id.licence:
                /** 营业执照上传或查看*/
                String imageParamsKey = "";
                String imageUrl = "";
                switch (PublicCache.login_mode) {
                    case PURCHASER:
                        imageParamsKey = "bzlicenceUrl";
                        imageUrl = PublicCache.loginPurchase.getBzlicenceUrl();
                        break;
                    case SUPPLIER:
                        imageParamsKey = "licencePics";
                        imageUrl = PublicCache.loginSupplier.getLicencePics();
                        break;
                }
                //3为未认证
   /*             if ("审核中".equals(licence_text.getText().toString())) {
                    //查看页面
                    ImageViewPopupWindow imageViewPopupWindow = new ImageViewPopupWindow(this);
                    imageViewPopupWindow.setPopupWindow_imageView_src(imageUrl);
                    imageViewPopupWindow.showAtLocation(mainView, Gravity.CENTER, 0, 0);
                } else {
                    intent = new Intent(this, ImageUploadActivity.class);
                    intent.putExtra("title", "营业执照上传");
                    intent.putExtra("imageUrl", imageUrl);
                    intent.putExtra("imageDescription", LICENCE_UPLOAD);
                    intent.putExtra("imageParamsKey", imageParamsKey);
                    startActivity(intent);
                }*/
                intent = new Intent(this, ImageUploadActivity.class);
                intent.putExtra("title", "营业执照上传");
                intent.putExtra("imageUrl", imageUrl);
                intent.putExtra("imageDescription", LICENCE_UPLOAD);
                intent.putExtra("imageParamsKey", imageParamsKey);
                startActivity(intent);
                break;

            case R.id.data_modification_OK:
                /** 获取页面的值*/
                values_map = new HashMap<>();
                UIDataProcessing.getChildControlsValue(mainView, values_map);

                switch (PublicCache.login_mode) {
                    case PURCHASER:
                        values_map.put("entityId", PublicCache.loginPurchase.getEntityId());
                        values_map.put("phoneNumber", PublicCache.loginPurchase.getPhoneNumber());
                        loading();
                        getRequestPresenter().customer_update("update", values_map, new RequestCallback<ImageUploadOk>() {
                            @Override
                            public void onSuc(ImageUploadOk body) {
                                loadingDimss();
                                finish();
                            }

                            @Override
                            public void onFailed(int imageUploadOk, String msg) {
                                loadingDimss();
                            }
                        });
                        break;
                    case SUPPLIER:
                        loading();
                        values_map.put("entityId", PublicCache.loginSupplier.getEntityId());
                        getRequestPresenter().supplier_update("update", values_map, new RequestCallback<ImageUploadOk>() {
                            @Override
                            public void onSuc(ImageUploadOk body) {
                                loadingDimss();
                                finish();
                            }

                            @Override
                            public void onFailed(int imageUploadOk, String msg) {
                                UIUtils.showToastSafesShort(msg);
                                loadingDimss();
                            }
                        });

                        break;
                }


                break;
            case R.id.place:

                break;
        }
    }


}
