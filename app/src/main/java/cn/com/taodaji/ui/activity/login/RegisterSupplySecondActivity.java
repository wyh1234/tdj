package cn.com.taodaji.ui.activity.login;

import android.Manifest;
import android.app.Activity;
import android.app.UiAutomation;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.base.activity.ActivityManage;
import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;
import com.base.utils.SerializableMap;
import com.base.utils.SystemUtils;
import com.base.utils.UIDataProcessing;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.LoginMethod;
import cn.com.taodaji.model.entity.FindByIsActive;
import cn.com.taodaji.model.entity.MarketLocal;
import cn.com.taodaji.model.entity.Register;
import cn.com.taodaji.model.entity.RegisterSaleTypeBean;
import cn.com.taodaji.model.event.MarketOrTypeEvent;
import cn.com.taodaji.model.event.SelectShopOrPositionEvent;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.ui.activity.employeeManagement.PopupBottomActivity;
import cn.com.taodaji.ui.activity.shopManagement.ShopInformationActivity;
import tools.activity.DataBaseActivity;
import tools.activity.SimpleToolbarActivity;
import tools.extend.PhoneUtils;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class RegisterSupplySecondActivity extends DataBaseActivity implements View.OnClickListener {
    private String register_flag,type_name;
    private EditText shop_name;
    private ImageView radio_agreement;
    private Button register_OK;
    private TextView place;
    private EditText address_detail;
    private EditText market_NO;
    private EditText et_realName;
    private TextView register_market_localhost, register_sale_type;
    private ImageView back,service;
    private double lat,lon;
    private int market_id=-1,type_id=-1;//选中的市场id,类型id
    private List<MarketOrTypeEvent> market_localhost = new ArrayList<>();
    private ArrayList<String> marketList = new ArrayList<>();

    private List<MarketOrTypeEvent> sale_type = new ArrayList<>();
    private ArrayList<String> typeList = new ArrayList<>();

    private int site = -1;

    private FindByIsActive findByIsActive;//启用的城市信息

    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.activity_register_supply_second);
    }

    @Override
    protected void initData() {
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initView() {
        register_flag = getIntent().getStringExtra(FLAG);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        } else {
            //Toast.makeText(this, "您已经申请了权限!", Toast.LENGTH_SHORT).show();
        }

        ViewUtils.findViewById(this, R.id.agreement_content).setOnClickListener(this);
        shop_name = ViewUtils.findViewById(this, R.id.shop_name_hint);
        radio_agreement = ViewUtils.findViewById(this, R.id.radio_agreement);
        radio_agreement.setOnClickListener(this);
        radio_agreement.setSelected(true);
        register_OK = ViewUtils.findViewById(this, R.id.register_OK);
        register_OK.setOnClickListener(this);
        place = ViewUtils.findViewById(this, R.id.place);
        place.setOnClickListener(this);

        back = ViewUtils.findViewById(this,R.id.img_back);
        back.setOnClickListener(this);
        service = ViewUtils.findViewById(this,R.id.ic_customer_service);
        service.setOnClickListener(this);

        register_market_localhost = ViewUtils.findViewById(this,R.id.register_market_localhost);
        register_market_localhost.setOnClickListener(this);
        register_sale_type = ViewUtils.findViewById(this,R.id.register_sale_type);
        register_sale_type.setOnClickListener(this);

        address_detail = ViewUtils.findViewById(this, R.id.address_detail);
        et_realName =  ViewUtils.findViewById(this, R.id.et_realName);
        if (register_flag.equals(PURCHASER))
            address_detail.setImeOptions(EditorInfo.IME_ACTION_DONE);
        else address_detail.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        address_detail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    register_OK.callOnClick();
                    return true;
                }
                return false;
            }
        });
        market_NO = ViewUtils.findViewById(this, R.id.market_NO);
        market_NO.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    register_OK.callOnClick();
                    return true;
                }
                return false;
            }
        });

    }

    private void marker_data() {
        if (register_flag.equals(PURCHASER)) return;
        market_localhost.clear();
        marketList.clear();
        if (site == -1) return;
        //从网络获取本地市场
        Map<String, Object> market_map = new HashMap();
        market_map.put("type", "1");//1所有，2绑定，3未绑定的
        getRequestPresenter().getMarket_local(market_map, site, new RequestCallback<MarketLocal>() {
            @Override
            public void onSuc(MarketLocal body) {
                for (MarketLocal.DataBean dataBean : body.getData()) {
                    MarketOrTypeEvent item = new MarketOrTypeEvent();
                    item.setId(dataBean.getEntityId());
                    item.setName(dataBean.getName());
                    market_localhost.add(item);
                    marketList.add(dataBean.getName());
                }
            }

            @Override
            public void onFailed(int marketLocal, String msg) {

            }
        });

    }

    private void type_data() {
        if (register_flag.equals(PURCHASER)) return;
        if (site == -1) return;
        sale_type.clear();
        typeList.clear();
        //从网络获取商品类别
        Map<String, String> type_map = new HashMap();
        type_map.put("site", site + "");

        addRequest(ModelRequest.getInstance().saleType(type_map), new RequestCallback<RegisterSaleTypeBean>() {

            @Override
            protected void onSuc(RegisterSaleTypeBean body) {

                if (body.getData() != null && body.getData().getList() != null) {
                    for (RegisterSaleTypeBean.DataBean.ListBean dataBean : body.getData().getList()) {
                        MarketOrTypeEvent item = new MarketOrTypeEvent();
                        item.setId(dataBean.getCategoryId());
                        item.setName(dataBean.getCategoryName());
                        typeList.add(dataBean.getCategoryName());
                        sale_type.add(item);
                    }
                }
            }

            @Override
            public void onFailed(int errCode, String msg) {

            }
        });

    }



    private Map<String, Object> values_map;


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.register_OK:
                /*      注册前验证*/
                if (et_realName.getText().toString().length() < 1) {
                    UIUtils.showToastSafesShort("真实姓名不可为空");
                    return;
                }

                if (register_flag.equals(SUPPLIER)) {
                    if (TextUtils.isEmpty(shop_name.getText().toString().trim())) {
                        UIUtils.showToastSafesShort("店铺名称不可为空");
                        return;
                    }
                    if (site==-1) {
                        UIUtils.showToastSafesShort("请选择店铺位置");
                        return;
                    }
                    if (type_id==-1) {
                        UIUtils.showToastSafesShort("请选择售卖商品类别");
                        return;
                    }
                    if (market_id==-1) {
                        UIUtils.showToastSafesShort("请选择当地市场");
                        return;
                    }
                    if (TextUtils.isEmpty(market_NO.getText().toString())) {
                        UIUtils.showToastSafesShort("市场编号不可为空");
                        return;
                    }

                } else {
                    if (TextUtils.isEmpty(address_detail.getText().toString())) {
                        UIUtils.showToastSafesShort("收货地址不可为空");
                        address_detail.requestFocus();
                        return;
                    }
                }


                /* 获取页面的值*/
                values_map = new HashMap<>();

                Bundle bundle = getIntent().getExtras();
                SerializableMap serializableMap = (SerializableMap) bundle.get("tepmap");
                Map<String, Object> map1 = serializableMap.getMap();
                values_map.putAll(map1);
                values_map.put("realname",et_realName.getText().toString().trim());
                values_map.put("storeName",shop_name.getText().toString().trim());
                values_map.put("storeAddress",place.getText().toString().trim()+address_detail.getText().toString().trim());
                values_map.put("storeNumber",market_NO.getText().toString().trim());
                values_map.put("marketId",market_id);
                values_map.put("categoryId", type_id);
                values_map.put("categoryName", type_name);
                values_map.put("lon", lon);
                values_map.put("lat", lat);


                if (site == -1) {
                    UIUtils.showToastSafesShort("请选择城市");
                    return;
                }
                loading("正在注册...");
                getRequestPresenter().supplier_registerData(values_map, site, new RequestCallback<Register>(this) {
                    @Override
                    public void onSuc(Register body) {
                        Map<String, Object> map_login = new HashMap<>();
                        map_login.put("account", values_map.get("phoneNumber"));
                        map_login.put("loginType", PASSWORD_LOGIN);
                        map_login.put("password", values_map.get("password").toString());
                        UIUtils.showToastSafesShort("注册成功");
                        Activity loginActivity = ActivityManage.getActivity(LoginActivity.class);
                        if (loginActivity != null) loginActivity.finish();
                        LoginMethod.getInstance(RegisterSupplySecondActivity.this).login(map_login, register_flag);
                    }

                    @Override
                    public void onFailed(int register, String msg) {
                        loadingDimss();
                        UIUtils.showToastSafesShort(msg);
                    }
                });
                break;
            case R.id.ic_customer_service:
                /** 拨打电话*/
                SystemUtils.callPhone(this, PhoneUtils.getPhoneService());
                break;
            case R.id.img_back:
                //关闭按扭后跳转到主页
                hideSoftInput(view.getWindowToken());//隐藏键盘
                finish();
                break;
            case R.id.place:
                Intent intent1 = new Intent(this, ShopAddressActivity.class);
                intent1.putExtra("isSupply",true);
                startActivityForResult(intent1,100);
                break;
            case R.id.register_market_localhost:
                if (site==-1){
                    UIUtils.showToastSafesShort("请先选择店铺位置");
                    return;
                }
                Intent intent = new Intent(RegisterSupplySecondActivity.this, SelectMarketOrTypeActivity.class);
                intent.putExtra("title", "所属市场");
                intent.putStringArrayListExtra("itemList",marketList);
                intent.putExtra("flag", true);
                startActivity(intent);
                break;
            case R.id.register_sale_type:
                if (site==-1){
                    UIUtils.showToastSafesShort("请先选择店铺位置");
                    return;
                }
                Intent intent2 = new Intent(RegisterSupplySecondActivity.this, SelectMarketOrTypeActivity.class);
                intent2.putExtra("title", "出售类别");
                intent2.putStringArrayListExtra("itemList", typeList);
                intent2.putExtra("flag", false);
                startActivity(intent2);
                break;
            case R.id.agreement_content:
                startActivity(new Intent(this, AgreementActivity.class));
                break;
            case R.id.radio_agreement:
                radio_agreement.setSelected(!radio_agreement.isSelected());
                register_OK.setEnabled(radio_agreement.isSelected());
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MarketOrTypeEvent event) {
        if (event.isFlag()){
            market_id = market_localhost.get(event.getPosition()).getId();
            register_market_localhost.setText(market_localhost.get(event.getPosition()).getName());
            register_market_localhost.setTextColor(getResources().getColor(R.color.black));
        }else {
            type_id = sale_type.get(event.getPosition()).getId();
            type_name = sale_type.get(event.getPosition()).getName();
            register_sale_type.setText(type_name);
            register_sale_type.setTextColor(getResources().getColor(R.color.black));
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            //当地的市场刷新
            if (data != null) {
                String result = data.getExtras().getString("result","");//得到新Activity 关闭后返回的数据
                place.setText(result);
                place.setTextColor(getResources().getColor(R.color.black));
                site = data.getExtras().getInt("id",PublicCache.site);
                lon = data.getExtras().getDouble("longitude");
                lat = data.getExtras().getDouble("latitude");
                marker_data();
                type_data();
            }
        }
    }
}
