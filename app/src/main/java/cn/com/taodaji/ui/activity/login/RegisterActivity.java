package cn.com.taodaji.ui.activity.login;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.FindByIsActive;
import cn.com.taodaji.model.entity.MarketLocal;
import cn.com.taodaji.model.entity.Register;
import cn.com.taodaji.model.entity.SmsCode;
import cn.com.taodaji.common.PublicCache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.base.retrofit.RequestCallback;

import com.base.activity.ActivityManage;

import com.base.retrofit.ResultInfoCallback;

import tools.activity.SimpleToolbarActivity;


import cn.com.taodaji.model.LoginMethod;
import tools.extend.PhoneUtils;

import com.base.utils.DialogUtils;
import com.base.utils.SystemUtils;
import com.base.utils.UIDataProcessing;
import com.base.utils.UIUtils;
import com.base.utils.UserNameUtill;
import com.base.utils.ViewUtils;

public class RegisterActivity extends SimpleToolbarActivity implements View.OnClickListener {
    private String register_flag;
    private View mainView;
    private TextView register_info;
    private TextView shop_name;
    private EditText shop_name_hint;
    private LinearLayout purchaser_hide;
    private ImageView radio_agreement;
    private Button register_OK;
    private EditText username_edit;
    private TextView get_verificationCode;
    private TextView place;
    private EditText password_edit1, et_realName;
    private EditText password_edit2;
    private EditText address_detail;
    private EditText market_NO;
    private Spinner register_market_localhost;

    private int market_id;//选中的市场id
    private List<Map<String, Object>> market_localhost = new ArrayList<>();
    private SimpleAdapter adapte_market;

    private int site = -1;

    private FindByIsActive findByIsActive;//启用的城市信息

    @Override
    public void titleSetting(Toolbar toolbar) {
        setToolBarColor(R.color.white);
        setStatusBarColor();
        toolbar.setNavigationIcon(R.mipmap.left_arrow_gary);//设置导航栏图标
        right_textView.setText("客服");
        right_textView.setTextSize(14);
        right_textView.setTextColor(UIUtils.getColor(R.color.gray_66));

        right_icon.setVisibility(View.VISIBLE);
        right_icon.setImageResource(R.mipmap.ic_customer_service);
        simple_title.setTextColor(UIUtils.getColor(R.color.gray_66));
        title_right.setOnClickListener(this);

    }

    @Override
    protected void initMainView() {
        register_flag = getIntent().getStringExtra(FLAG);
        setStatusBarColor();//设置通知栏颜色

        if (register_flag == null) return;
        switch (register_flag) {
            case PURCHASER:
                simple_title.setText("采购商注册");
                break;
            case SUPPLIER:
                simple_title.setText("销售商注册");
                break;
        }

        mainView = getLayoutView(R.layout.activity_login_register);
        body_scroll.addView(mainView);
        ViewUtils.findViewById(mainView, R.id.agreement_content).setOnClickListener(this);
        register_info = ViewUtils.findViewById(mainView, R.id.register_info);
        shop_name = ViewUtils.findViewById(mainView, R.id.shop_name);
        shop_name_hint = ViewUtils.findViewById(mainView, R.id.shop_name_hint);
        purchaser_hide = ViewUtils.findViewById(mainView, R.id.purchaser_hide);
        radio_agreement = ViewUtils.findViewById(mainView, R.id.radio_agreement);
        radio_agreement.setOnClickListener(this);
        radio_agreement.setSelected(true);
        register_OK = ViewUtils.findViewById(mainView, R.id.register_OK);
        register_OK.setOnClickListener(this);
        place = ViewUtils.findViewById(mainView, R.id.place);
        place.setOnClickListener(this);
        address_detail = ViewUtils.findViewById(mainView, R.id.address_detail);
        et_realName = mainView.findViewById(R.id.et_realName);
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
        market_NO = ViewUtils.findViewById(mainView, R.id.market_NO);
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


        register_market_localhost = ViewUtils.findViewById(mainView, R.id.register_market_localhost);
        adapte_market = new SimpleAdapter(this, market_localhost, android.R.layout.simple_spinner_item, new String[]{"name"}, new int[]{android.R.id.text1});
        adapte_market.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        register_market_localhost.setAdapter(adapte_market);


        password_edit1 = ViewUtils.findViewById(mainView, R.id.password_edit1);
        password_edit2 = ViewUtils.findViewById(mainView, R.id.password_edit2);

        get_verificationCode = ViewUtils.findViewById(mainView, R.id.get_verificationCode);
        get_verificationCode.setOnClickListener(this);

        username_edit = ViewUtils.findViewById(mainView, R.id.username_edit);
        username_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (!UserNameUtill.isPhoneNO(username_edit.getText().toString()))
                        username_edit.setError("手机号码格式不正确");
                }
            }
        });
        register_mode(register_flag);//切换注册用户类型
    }

    @Override
    protected void initData() {
        if (place != null) place.setText("请选择城市");
        site = -1;
        getCityName();
    }


    private void marker_data() {
        if (register_flag.equals(PURCHASER)) return;
        market_localhost.clear();
        if (site == -1) return;
        //从网络获取本地市场
        final Map<String, Object> market_map = new HashMap();
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
            }

            @Override
            public void onFailed(int marketLocal, String msg) {

            }
        });

    }


    private void register_mode(String register_flag) {
        switch (register_flag) {
            case PURCHASER:
                register_info.setText(UIUtils.getString(R.string.register_purchaser_info));
                register_info.setTextColor(UIUtils.getColor(R.color.orange_yellow_ff7d01));
                shop_name.setText(UIUtils.getString(R.string.organization_name));
                shop_name_hint.setHint(UIUtils.getString(R.string.organization_name_hint));
                purchaser_hide.setVisibility(View.GONE);
                radio_agreement.setBackgroundResource(R.drawable.s_select_round);
                register_OK.setBackgroundResource(R.drawable.r_round_rect_solid_orange_ff7d01);
                address_detail.setTag(UIUtils.getString(R.string.hotelAddress_tag));
                shop_name_hint.setTag(UIUtils.getString(R.string.hotelName_tag));
                break;
            case SUPPLIER:
                register_info.setText(UIUtils.getString(R.string.register_supplier_info));
                register_info.setTextColor(UIUtils.getColor(R.color.blue_light));
                shop_name.setText(UIUtils.getString(R.string.shop_name));
                shop_name_hint.setHint(UIUtils.getString(R.string.shop_name_hint));
                purchaser_hide.setVisibility(View.VISIBLE);
                radio_agreement.setBackgroundResource(R.drawable.s_supplier_register);
                register_OK.setBackgroundResource(R.drawable.r_round_rect_solid_blue_2898eb);
                address_detail.setTag(UIUtils.getString(R.string.storeAddress_tag));
                shop_name_hint.setTag(UIUtils.getString(R.string.storeName_tag));
                break;
        }
    }


    private Handler handler = UIUtils.getHandler();
    // 更新重新获取按扭
    int datetime = 60;
    Runnable runable = new Runnable() {
        @Override
        public void run() {
            // TODO 自动生成的方法存根
            if (datetime == 0) {
                get_verificationCode.setText("重新获取");
                get_verificationCode.setEnabled(true);
                datetime = 60;
                return;
            } else {
                get_verificationCode.setEnabled(false);
            }
            get_verificationCode.setText("已发送(" + datetime + "s)");
            get_verificationCode.setTextColor(UIUtils.getColor(R.color.gray_66));
            handler.postDelayed(runable, 1000);
            datetime--;
        }
    };
    private Map<String, Object> values_map;


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.register_OK:
                /*      注册前验证*/
                if (!UserNameUtill.isPhoneNO(username_edit.getText().toString())) {
                    UIUtils.showToastSafesShort("手机号码格式错误");
                    return;
                }
                if (password_edit1.getText().toString().compareTo(password_edit2.getText().toString()) != 0) {
                    UIUtils.showToastSafesShort("两次密码输入不一致");
                    return;
                }
                if (password_edit1.getText().toString().length() < 6) {
                    UIUtils.showToastSafesShort("密码最少需要6位");
                    return;
                }
                if (et_realName.getText().toString().length() < 1) {
                    UIUtils.showToastSafesShort("真实姓名不可为空");
                    return;
                }


                if (register_flag.equals(SUPPLIER)) {
                    if (TextUtils.isEmpty(shop_name_hint.getText().toString())) {
                        UIUtils.showToastSafesShort("店铺名称不可为空");
                        return;
                    }
                    if (TextUtils.isEmpty(address_detail.getText().toString())) {
                        UIUtils.showToastSafesShort("店铺地址不可为空");
                        return;
                    }
                    if (register_market_localhost.getSelectedItem() == null) {
                        UIUtils.showToastSafesShort("请选择当地市场");
                        return;
                    }
                    if (TextUtils.isEmpty(market_NO.getText().toString())) {
                        UIUtils.showToastSafesShort("市场编号不可为空");
                        return;
                    }


                } else {

                    if (TextUtils.isEmpty(shop_name_hint.getText().toString())) {
                        UIUtils.showToastSafesShort("酒店名称不可为空");
                        return;
                    }

                    if (TextUtils.isEmpty(address_detail.getText().toString())) {
                        UIUtils.showToastSafesShort("收货地址不可为空");
                        address_detail.requestFocus();
                        return;
                    }
                }
                if (site == -1) {
                    UIUtils.showToastSafesShort("请选择城市");
                    return;
                }

                /* 获取页面的值*/
                values_map = new HashMap<>();
                UIDataProcessing.getChildControlsValue(mainView, values_map);
                values_map.put("account", values_map.get("phoneNumber"));
                switch (register_flag) {
                    case PURCHASER:
                        loading("正在注册...");
                        getRequestPresenter().customer_registerData(values_map, site, new RequestCallback<Register>(this) {
                            @Override
                            public void onSuc(Register body) {
                                Map<String, Object> map_login = new HashMap<>();
                                map_login.put("account", values_map.get("phoneNumber"));
                                map_login.put("loginType", PASSWORD_LOGIN);
                                map_login.put("password", values_map.get("password").toString());
                                UIUtils.showToastSafesShort("注册成功");
                                Activity loginPurchaserActivity = ActivityManage.getActivity(LoginPurchaserActivity.class);
                                if (loginPurchaserActivity != null) loginPurchaserActivity.finish();
                                LoginMethod.getInstance(RegisterActivity.this).login(map_login, register_flag);
                            }

                            @Override
                            public void onFailed(int register, String msg) {
                                loadingDimss();
                                UIUtils.showToastSafesShort(msg);
                            }
                        });
                        break;
                    case SUPPLIER:
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
                                LoginMethod.getInstance(RegisterActivity.this).login(map_login, register_flag);
                            }

                            @Override
                            public void onFailed(int register, String msg) {
                                loadingDimss();
                                UIUtils.showToastSafesShort(msg);
                            }
                        });
                        break;
                }
                break;
            case R.id.get_verificationCode:
                if (!UserNameUtill.isPhoneNO(username_edit.getText().toString())) {
                    UIUtils.showToastSafesShort("手机号码格式不正确");
                    return;
                }
                String phoneNO = username_edit.getText().toString();
                Map<String, Object> map = new HashMap<>();
                map.put("telephone", phoneNO);
                map.put("smsType", 1);//1注册，2登陆，3忘记密码

                getRequestPresenter().smsCode(map, new RequestCallback<SmsCode>(this) {
                    @Override
                    public void onSuc(SmsCode body) {
                        handler.post(runable);
                        get_verificationCode.setText("已发送(" + datetime + "s)");
                        get_verificationCode.setTextColor(UIUtils.getColor(R.color.gray_66));
                        UIUtils.showToastSafesShort("短信发送成功");
                    }

                    @Override
                    public void onFailed(int smsCode, String msg) {
                        loadingDimss();
                        UIUtils.showToastSafesShort(msg);
                    }
                });

                break;
            case R.id.title_right:
                /** 拨打电话*/
                SystemUtils.callPhone(this, PhoneUtils.getPhoneService());
                break;
            case R.id.place:
                // Intent intent = new Intent(this, RegionSelectionActivity.class);
                //  intent.putExtra("data", placeSelectModel.getData());
                Intent intent = new Intent(this, CityActivity.class);
                intent.putExtra("data", findByIsActive);
                startActivityForResult(intent, 100);
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


    public void getCityName() {
        if (register_flag == null) return;
        int isActive = 3;
        switch (register_flag) {
            case PURCHASER:
                isActive = 3;//  3-正式运营
                break;
            case SUPPLIER:
                isActive = 1; //  1-启用
                break;
        }
        getRequestPresenter().findByIsActive(isActive, new ResultInfoCallback<FindByIsActive>() {
            @Override
            public void onSuccess(FindByIsActive body) {
                findByIsActive = body;
                if (PublicCache.isLocationSucc) {
                    boolean isRight=false;
                    for (FindByIsActive.ListBean listBean : body.getList()) {
                        if (listBean.getName().equals(PublicCache.location_default)) {
                            isRight=true;
                            place.setText(PublicCache.location_default);
                            site = listBean.getId();

                            break;
                        }
                    }

                    if (isRight) {
                        String  temp[]={ "系统检测到您当前的位置在"  ,  PublicCache.location_default  ,  "，是否选择"  , PublicCache.location_default, "为门店所在地？"};
                        int index[]={2,4};
                        DialogUtils.getInstance(getBaseActivity()).getSimpleColorDialog("",temp,index).setNegativeButton("确定",null, R.color.orange_yellow_ff7d01).setPositiveButton("手动选择", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getBaseActivity(), CityActivity.class);
                                intent.putExtra("data", findByIsActive);
                                startActivityForResult(intent, 100);
                            }
                        }).show();
                    }else {

                        String  temp[]={ "系统检测到您当前所在的位置"  ,  "暂未开放"  ,  "，请手动选择门店所在位置。"  };

                        int index[]={2};
                        DialogUtils.getInstance(getBaseActivity()).getSimpleColorDialog("",temp,index).setPositiveButton("手动选择", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getBaseActivity(), CityActivity.class);
                                intent.putExtra("data", findByIsActive);
                                startActivityForResult(intent, 100);
                            }
                        }, R.color.orange_yellow_ff7d01).show();

                    }
                }else{
                    String  temp[]={ "门店定位信息"  ,  "获取失败"  ,  "，请手动选择门店所在位置。"  };
                    int index[]={2};
                    DialogUtils.getInstance(getBaseActivity()).getSimpleColorDialog("",temp,index).setPositiveButton("手动选择", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getBaseActivity(), CityActivity.class);
                            intent.putExtra("data", findByIsActive);
                            startActivityForResult(intent, 100);
                        }
                    }, R.color.orange_yellow_ff7d01).show();
                }

                marker_data();
            }

            @Override
            public void onFailed(int findByIsActiveResultInfo, String msg) {
                UIUtils.showToastSafesShort(msg);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            //当地的市场刷新
            if (data != null) {
                place.setText(data.getStringExtra("name"));
                site = data.getIntExtra("id", 2);
                marker_data();
            }

        }
    }


}
