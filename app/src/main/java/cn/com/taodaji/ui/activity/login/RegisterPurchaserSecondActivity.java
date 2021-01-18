package cn.com.taodaji.ui.activity.login;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.activity.ActivityManage;
import com.base.common.Config;
import com.base.glide.ImageLoaderUtils;
import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;
import com.base.takephoto.model.TResult;
import com.base.utils.BitmapUtil;
import com.base.utils.DialogUtils;
import com.base.utils.IOUtils;
import com.base.utils.MD5AndSHA;
import com.base.utils.SerializableMap;
import com.base.utils.UIUtils;
import com.base.utils.UserNameUtill;
import com.base.utils.ViewUtils;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.InterfaceUtils.UserNameChangeListener;
import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.LoginMethod;
import cn.com.taodaji.model.entity.FindByIsActive;
import cn.com.taodaji.model.entity.ImageUpload;
import cn.com.taodaji.model.entity.LocationBean;
import cn.com.taodaji.model.entity.Register;
import cn.com.taodaji.model.entity.ShopTypeBean;
import cn.com.taodaji.model.entity.SmsCode;
import cn.com.taodaji.model.entity.TimeHourBean;
import cn.com.taodaji.model.entity.XiaoQuAddressItem;
import cn.com.taodaji.model.event.RegisterPurchaserSecondEvent;
import cn.com.taodaji.model.event.ShopTypeSelectListEvent;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.ui.activity.homepage.ManageActivity;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.ui.activity.integral.tools.ShowLoadingDialog;
import cn.com.taodaji.ui.activity.orderPlace.SubmitOrderActivity;
import cn.com.taodaji.ui.activity.tdjc.CitySelectPoupWindow;
import cn.com.taodaji.ui.activity.tdjc.SelectPickUpWayPoupWindow;
import cn.com.taodaji.ui.ppw.AgreementPoupWindow;
import cn.com.taodaji.ui.ppw.TakePhotoPopupWindow;
import cn.com.taodaji.ui.view.PasswordGrPurchaserEditText;
import cn.com.taodaji.ui.view.PasswordPurchaserEditText;
import cn.com.taodaji.ui.view.UserNameGrPurchaserEditText;
import cn.com.taodaji.ui.view.UserNamePurchaserEditText;
import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tools.activity.TakePhotosDataActivity;
import tools.extend.TakePhotoUtils;
import tools.location.LocationUtils;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class RegisterPurchaserSecondActivity extends TakePhotosDataActivity implements
        View.OnClickListener ,AgreementPoupWindow.AgreementPoupWindowListener, UserNameChangeListener, CitySelectPoupWindow.CitySelectPoupWindowListener {
    private ImageView radio_agreement, img_shop_picture;
    private Button register_OK;
    private LinearLayout  line_get_time,ll_gr,line_account,ll_qy,line_gr_address;//line_camera_wait,

    private String image_url = null;
    private boolean isCallback = false;
    private Bitmap bitmap;
    private int type;
    private LocationBean mlocationBean;
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private TextView text_shop_get_time, text_shop_type, text_shop_address,tv_gr_tab,tv_qy_tab,text_shop_gr_address,tv_tile;

    private OptionsPickerView pvCustomOptions=null;

    private ArrayList<TimeHourBean> timeList = new ArrayList<>();

    private ArrayList<ArrayList<TimeHourBean>> options2Items = new ArrayList<>();

    private List<ShopTypeBean> selectedList;

    private EditText edit_shop_name, edit_shop_invite,houseNumber,ed_gr_code,ed_gr_no;

    private Map<String, Object> values_map;

    private String start_time = "";
    private String end_time = "";
    private int start_select=7;
    private int end_select=0;
    private double lon;
    private double lat;

    private int id=PublicCache.site;
    private String name=PublicCache.site_name;
    private String fenceGid="";
    private boolean isFirst=true;
    private int communityId=0;
    private String communityName="";
    private String communityAbbreviation="";

    private TakePhotoPopupWindow takePhotoPopupWindow;
    private RxPermissions rxPermissions;
    private AgreementPoupWindow agreementPoupWindow;

    //个人用户
    private UserNameGrPurchaserEditText username_edit;
    private TextView get_verificationCode;
    private PasswordGrPurchaserEditText password_edit1;//, et_realName;
    private PasswordGrPurchaserEditText password_edit2;
    private EditText editText3,edit_real_name;
    private boolean phone_input = false;
    private Handler handler = UIUtils.getHandler();
    private CitySelectPoupWindow citySelectPoupWindow;
    // 更新重新获取按扭
    int datetime = 60;
    Runnable runable = new Runnable() {
        @Override
        public void run() {
            // TODO 自动生成的方法存根
            if (datetime == 0) {
                get_verificationCode.setText("重新获取");
                get_verificationCode.setEnabled(true);
                get_verificationCode.setTextColor(UIUtils.getColor(R.color.white));//
                get_verificationCode.setBackgroundResource(R.drawable.r_round_rect_solid_orange_ff7d01);
                datetime = 60;
                return;
            } else {
                get_verificationCode.setEnabled(false);
            }
            get_verificationCode.setText("已发送(" + datetime + "s)");
            // get_verificationCode.setTextColor(UIUtils.getColor(R.color.gray_66));
            handler.postDelayed(runable, 1000);
            datetime--;
        }
    };
    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.activity_register_purchaser_second);
    }

    @Override
    protected void initView() {
        super.initView();
        rxPermissions = new RxPermissions(this);
        radio_agreement = ViewUtils.findViewById(this, R.id.radio_agreement);
        radio_agreement.setOnClickListener(this);
//        radio_agreement.setSelected(true);

        register_OK = ViewUtils.findViewById(this, R.id.register_OK);
        register_OK.setOnClickListener(this);

       // line_camera_wait = ViewUtils.findViewById(this, R.id.line_camera_wait);

        text_shop_get_time = ViewUtils.findViewById(this, R.id.text_shop_get_time);
        text_shop_type = ViewUtils.findViewById(this, R.id.text_shop_type);
        text_shop_address = ViewUtils.findViewById(this, R.id.text_shop_address);

        houseNumber = ViewUtils.findViewById(this,R.id.edit_house_number);

        edit_shop_name = ViewUtils.findViewById(this, R.id.edit_shop_name);
        edit_shop_invite = ViewUtils.findViewById(this, R.id.edit_shop_invite);
        tv_tile= ViewUtils.findViewById(this, R.id.tv_tile);

        img_shop_picture = ViewUtils.findViewById(this, R.id.img_shop_picture);
        img_shop_picture.setOnClickListener(this);

        line_get_time = ViewUtils.findViewById(this, R.id.line_get_time);
        line_get_time.setOnClickListener(this);

        text_shop_gr_address=ViewUtils.findViewById(this, R.id.text_shop_gr_address);

        tv_gr_tab=ViewUtils.findViewById(this, R.id.tv_gr_tab);
        tv_gr_tab.setOnClickListener(this);

        tv_qy_tab=ViewUtils.findViewById(this, R.id.tv_qy_tab);
        tv_qy_tab.setOnClickListener(this);

        ll_gr=ViewUtils.findViewById(this, R.id.ll_gr);
        line_account=ViewUtils.findViewById(this, R.id.line_account);
        ll_qy=ViewUtils.findViewById(this, R.id.ll_qy);
        line_gr_address=ViewUtils.findViewById(this, R.id.line_gr_address);
        ed_gr_code=ViewUtils.findViewById(this, R.id.ed_gr_code);
        ed_gr_no=ViewUtils.findViewById(this, R.id.ed_gr_no);

        ViewUtils.findViewById(this, R.id.line_shop_type).setOnClickListener(this);
        ViewUtils.findViewById(this, R.id.line_explain).setOnClickListener(this);
        ViewUtils.findViewById(this, R.id.img_back).setOnClickListener(this);
        ViewUtils.findViewById(this, R.id.line_address).setOnClickListener(this);
        ViewUtils.findViewById(this, R.id.agreement_content).setOnClickListener(this);
        ViewUtils.findViewById(this, R.id.line_gr_address).setOnClickListener(this);


        getOptionData();
        initCustomOptionPicker();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        } else {
            //Toast.makeText(this, "您已经申请了权限!", Toast.LENGTH_SHORT).show();
        }

        setType(getIntent().getIntExtra("type",0));
        if (getType()==1){
            line_account.setVisibility(View.GONE);
            ll_qy.setVisibility(View.GONE);
            ll_gr.setVisibility(View.VISIBLE);
            tv_tile.setText("个人用户注册");
        }else {
            line_account.setVisibility(View.VISIBLE);
            ll_qy.setVisibility(View.VISIBLE);
            ll_gr.setVisibility(View.GONE);
            tv_tile.setText("企业用户注册");
        }

        //个人用户
        password_edit1 = ViewUtils.findViewById(this, R.id.password_edit1);
        password_edit2 = ViewUtils.findViewById(this, R.id.password_edit2);

        edit_real_name = ViewUtils.findViewById(this, R.id.edit_real_name);


        get_verificationCode = ViewUtils.findViewById(this, R.id.get_verificationCode);
        get_verificationCode.setOnClickListener(this);

        username_edit = ViewUtils.findViewById(this, R.id.username_edit);

        username_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    phone_input = UserNameUtill.isPhoneNO(username_edit.getText().toString());
                    if (!UserNameUtill.isPhoneNO(username_edit.getText().toString()))
                        username_edit.setError("手机号码格式不正确");
                }
            }
        });
        username_edit.setUserNameChangeListener(this);
        editText3 = ViewUtils.findViewById(this, R.id.editText3);
        get_verificationCode.setCompoundDrawablePadding(UIUtils.dip2px(10));

        editText3.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//密码框改成验证码，需要显示
        editText3.setCompoundDrawablePadding(UIUtils.dip2px(15));


        password_edit2.setTransformationMethod(PasswordTransformationMethod.getInstance());//密码框改成验证码，需要显示
        password_edit2.setCompoundDrawablePadding(UIUtils.dip2px(15));


    }

    private void setAgrementPoupwindow() {
        LogUtils.e("11111111111111111111111");
        if (agreementPoupWindow!=null){
            if (agreementPoupWindow.isShowing()){
                return;
            }

        }
        agreementPoupWindow = new AgreementPoupWindow(RegisterPurchaserSecondActivity.this);
        agreementPoupWindow.setAgreementPoupWindowListener(this);
        agreementPoupWindow.setPopupWindowFullScreen(true);//铺满
        agreementPoupWindow.showPopupWindow();
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }


    @Override
    public void takeSuccess(TResult result) {
        String path = result.getImage().getCompressPath();
        if (TextUtils.isEmpty(path)) return;
        String imageName = UIUtils.getFileNames(path);
        bitmap = BitmapUtil.getSmallBitmap(path);
        if (bitmap == null) return;
        image_url = null;

        ImageLoaderUtils.loadImage(img_shop_picture, path);
       // line_camera_wait.setVisibility(View.VISIBLE);
       // img_shop_picture.setClickable(false);

        getRequestPresenter().imageUpload(imageName, BitmapUtil.bitmapTobyte(bitmap, true), new Callback<ImageUpload>() {
            @Override
            public void onResponse(Call<ImageUpload> call, Response<ImageUpload> response) {
                if (response.body() != null) {
                    image_url = response.body().getData();
                    if (isCallback) returnResult();
                } else {
                    UIUtils.showToastSafesShort(response.message());
                }

            }

            @Override
            public void onFailure(Call<ImageUpload> call, Throwable t) {
                UIUtils.showToastSafesShort("图片上传失败，请检查网络");
                loadingDimss();
            }
        });
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    //上传图片信息
    private void returnResult() {
        //if (TextUtils.isEmpty(image_url)) return;
        if (getType()==1) {
            if (TextUtils.isEmpty(edit_real_name.getText().toString().trim())) {
                UIUtils.showToastSafesShort("请输真实姓名");
                return;
            }
            if (!UserNameUtill.isPhoneNO(username_edit.getText().toString())) {
                UIUtils.showToastSafesShort("手机号码格式错误");
                return;
            }
            if (TextUtils.isEmpty(editText3.getText().toString().trim())) {
                UIUtils.showToastSafesShort("请输入验证码");
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
        }

        /* 获取页面的值*/
        values_map = new HashMap<>();
        if (getType()==2){

            Bundle bundle = getIntent().getExtras();
            SerializableMap serializableMap = (SerializableMap) bundle.get("tepmap");
            Map<String, Object> map1 = serializableMap.getMap();
            values_map.putAll(map1);

        }else {
            values_map.put("realname", edit_real_name.getText().toString().trim());
            values_map.put("account", username_edit.getText().toString().trim());
            values_map.put("smsCode", editText3.getText().toString().trim());
            values_map.put("password", MD5AndSHA.md5Encode(password_edit2.getText().toString().trim()));
            values_map.put("phoneNumber",username_edit.getText().toString().trim());
        }




        if (getType()==2){
            values_map.put("hotelName", edit_shop_name.getText().toString());  //0-注册，1-门店创建
            values_map.put("hotelAddress", text_shop_address.getText().toString());  //0-注册，1-门店创建
        }else {
            if (TextUtils.isEmpty(text_shop_gr_address.getText().toString().trim())){
                ToastUtils.showShortToast(this,"请选择您所在的城市");
                return;
            }
            values_map.put("hotelAddress", text_shop_gr_address.getText().toString());  //0-注册，1-门店创建
        }

        values_map.put("resource", 0);  //0-注册，1-门店创建
        //values_map.put("operator", );// operator; //操作人,resource为1时 operator有值

        String hotelTypeId="";
        if (selectedList!= null) {
            for (int i = 0; i <selectedList.size() ; i++) {
                if (i == selectedList.size()-1) {
                    hotelTypeId=hotelTypeId+selectedList.get(i).getHotelTypeId();
                }else {
                    hotelTypeId=hotelTypeId+selectedList.get(i).getHotelTypeId()+",";
                }
            }
        }
        values_map.put("hotelTypeId",hotelTypeId);  //门店类型id
        if (image_url!=null) {
            values_map.put("imageUrl", image_url);  //门店形象照
        }


        if (getType()==2){
            values_map.put("streetNumber",houseNumber.getText().toString().trim());
            values_map.put("deliveredTime", start_time);  //门店到货开始时间
            values_map.put("deliveredTimeEnd", end_time);  //门店到货结束时间
            values_map.put("fenceGid", fenceGid);  //商圈id
        }else {
//            if (TextUtils.isEmpty(ed_gr_no.getText().toString().trim())){
//                ToastUtils.showShortToast(this,"请填写门牌号");
//                return;
//            }
//            values_map.put("streetNumber",ed_gr_no.getText().toString().trim());

                        if (TextUtils.isEmpty(text_shop_gr_address.getText().toString().trim())){
                ToastUtils.showShortToast(this,"请选择您的城市");
                return;
            }

        }

        if (getType()==2){
            if (!TextUtils.isEmpty(edit_shop_invite.getText().toString().trim())) {
                values_map.put("verifyCode", edit_shop_invite.getText().toString().trim());  //邀请码
            }
        }else {
            if (!TextUtils.isEmpty(ed_gr_code.getText().toString().trim())) {
                values_map.put("verifyCode", ed_gr_code.getText().toString().trim());  //邀请码
            }
        }


        values_map.put("parentId", -1);  //
        values_map.put("isG", -1);  //
        if (getType()==2){
            values_map.put("isC", 0);  //
        }else {
            values_map.put("isC", 1);  //
        }

        values_map.put("lon", lon+"");
        values_map.put("lat", lat+"");
        values_map.put("communityId", communityId);
        values_map.put("communityName",  communityName);
        values_map.put("communityAbbreviation",  communityAbbreviation);
        values_map.put("communityAddress",  text_shop_gr_address.getText().toString());
        if (getIntent().getStringExtra("vip")!=null){
            values_map.put("extraUserType",  "1");
        }
        LogUtils.i(values_map);

        loading("正在注册...");
        getRequestPresenter().customer_registerData(values_map, id, new RequestCallback<Register>(this) {
            @Override
            public void onSuc(Register body) {
                Map<String, Object> map_login = new HashMap<>();
                if (getType()==2){
                    map_login.put("account", values_map.get("account"));
                    map_login.put("password", values_map.get("password").toString()) ;

                }else {
                  map_login.put("account",  username_edit.getText().toString().trim());
                    map_login.put("password",MD5AndSHA.md5Encode(password_edit2.getText().toString().trim()));
                }
                map_login.put("loginType", PASSWORD_LOGIN);

                UIUtils.showToastSafesShort("注册成功");
                Activity loginPurchaserActivity = ActivityManage.getActivity(LoginPurchaserActivity.class);
                if (loginPurchaserActivity != null) loginPurchaserActivity.finish();
                LoginMethod.getInstance(RegisterPurchaserSecondActivity.this).login(map_login, PublicCache.login_mode);
            }

            @Override
            public void onFailed(int register, String msg) {
                loadingDimss();
                UIUtils.showToastSafesShort(msg);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.radio_agreement: {

//                radio_agreement.setSelected(!radio_agreement.isSelected());
//                register_OK.setEnabled(radio_agreement.isSelected());
                break;
            }
            case R.id.agreement_content:
                startActivity(new Intent(this, AgreementActivity.class));
                break;
            case R.id.line_shop_type: {
                RegisterPurchaserShopTypeActivity.startActivity(this,selectedList);
                break;
            }  case R.id.line_address: {
                isFirst=true;
                Intent intent1 = new Intent(this, ShopAddressActivity.class);
                startActivityForResult(intent1,2);
                break;
            }
            case R.id.line_gr_address:{
//                location();
//                if (citySelectPoupWindow!=null){
//                    if (citySelectPoupWindow.isShowing()){
//                        return;
//                    }
//
//                }
//                citySelectPoupWindow = new CitySelectPoupWindow(this);
//                citySelectPoupWindow.setCitySelectPoupWindowListener(this);
//                citySelectPoupWindow.setPopupWindowFullScreen(true);//铺满
//                citySelectPoupWindow.showPopupWindow();

                location();

                break;
            }
            case R.id.line_get_time: {

                if (pvCustomOptions != null) {
                    pvCustomOptions.setSelectOptions(start_select, end_select);
                    pvCustomOptions.show(); //弹出自定义条件选择器
                }
                break;
            }
            case R.id.img_shop_picture: {
                String fileName = System.currentTimeMillis() + ".jpg";
                Uri imageUri = Uri.fromFile(IOUtils.createFile(Config.imageSaveDir, fileName));
                if (takePhotoPopupWindow == null) {
                    takePhotoPopupWindow = new TakePhotoPopupWindow(v) {
                        @Override
                        public void goCamera() {
                            TakePhotoUtils.getInstance().setCrop(false).setImageUri(imageUri).openCamera(getTakePhoto());
                        }

                        @Override
                        public void goAlbum() {
                            TakePhotoUtils.getInstance().setCrop(false).setImageUri(imageUri).openPhotoAlbum(getTakePhoto(), 1, true);
                        }
                    };
                    takePhotoPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                } else {
                    if (!takePhotoPopupWindow.isShowing()) {
                        takePhotoPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0 );
                    }

                }
                break;
            }
            case R.id.register_OK: {//text_shop_get_time, text_shop_type, text_shop_address;  edit_shop_name
                if (getType()==2){
                    if (TextUtils.isEmpty(edit_shop_name.getText().toString().trim())) {
                        UIUtils.showToastSafesShort("门店名称不可为空");
                        return;
                    }
                    if (TextUtils.isEmpty(text_shop_address.getText().toString())) {
                        UIUtils.showToastSafesShort("门店地址不可为空");
                        return;
                    }
                    if (isFirst) {
                        makeSureCity();
                        return;
                    }
                    if (TextUtils.isEmpty(text_shop_get_time.getText().toString())) {
                        UIUtils.showToastSafesShort("请选择到货时间");
                        return;
                    }
                }


//                if (bitmap == null&&image_url == null) {
//                    UIUtils.showToastSafesShort("请选择一张图片");
//                    return;
//                }


//                if (image_url == null) {
//                    isCallback = true;
//                    loading("图片正在上传...");
//                } else {
                    returnResult();
//                }


                break;
            }
            case R.id.img_back: {
//                backDialog();
                finish();
                break;
            }
            case R.id.line_explain: {
                RegisterPurchaserExplainActivity.startActivity(this);
                break;


            }
            case R.id.tv_qy_tab:{
                tv_qy_tab.setBackground(getResources().getDrawable(R.drawable.rigister_tab_one_shap));
                tv_qy_tab.setTextColor(getResources().getColor(R.color.orange_yellow_ff7d01));
                tv_gr_tab.setBackground(getResources().getDrawable(R.drawable.rigister_tab_right_shap));
                tv_gr_tab.setTextColor(getResources().getColor(R.color.white));
                line_account.setVisibility(View.VISIBLE);
                ll_qy.setVisibility(View.VISIBLE);
                ll_gr.setVisibility(View.GONE);
                setType(2);
                break;
            }
            case R.id.tv_gr_tab:{
                tv_gr_tab.setBackground(getResources().getDrawable(R.drawable.rigister_tab_shap));
                tv_gr_tab.setTextColor(getResources().getColor(R.color.orange_yellow_ff7d01));
                tv_qy_tab.setBackground(getResources().getDrawable(R.drawable.rigister_tab_right_one_shap));
                tv_qy_tab.setTextColor(getResources().getColor(R.color.white));
                line_account.setVisibility(View.GONE);
                ll_qy.setVisibility(View.GONE);
                ll_gr.setVisibility(View.VISIBLE);
                setType(1);
                break;
            }
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
                        get_verificationCode.setTextColor(UIUtils.getColor(R.color.orange_yellow_ff7d01));
                        get_verificationCode.setBackgroundResource(R.drawable.r_round_rect_solid_gray_66);
                        UIUtils.showToastSafesShort("短信发送成功");
                    }

                    @Override
                    public void onFailed(int smsCode, String msg) {
                        loadingDimss();
                        UIUtils.showToastSafesShort(msg);
                    }
                });
        }
    }
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setAgrementPoupwindow();//弹出popupWindow
            }
        },20);

    }
    private void makeSureCity() {
        String temp[] = {"您当前注册的城市是", name, "，是否选择", name, "为门店所在地？"};
        int index[] = {2, 4};
        DialogUtils.getInstance(getBaseActivity()).getSimpleColorDialog("", temp, index).setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                isFirst=false;
            }
        }, R.color.orange_yellow_ff7d01).setPositiveButton("重新选择", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                isFirst=true;
                Intent intent1 = new Intent(getBaseActivity(), ShopAddressActivity.class);
                intent1.putExtra("isRegister", true);
                startActivityForResult(intent1, 2);
            }
        }).show();
    }


    private void initCustomOptionPicker() {//条件选择器初始化，自定义布局
        /**
         * @description
         *
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */


        pvCustomOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = timeList.get(options1).getPickerViewText() + "——" + options2Items.get(options1).get(option2).getPickerViewText();
                text_shop_get_time.setText(tx);

                start_select=options1;
                end_select=option2;

                start_time = timeList.get(options1).getPickerViewText();
                end_time = options2Items.get(options1).get(option2).getPickerViewText();
            }
        })
                .setDividerColor(UIUtils.getColor(R.color.transparent)).setLayoutRes(R.layout.part_register_purchaser_second_time_wheel, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView text_cancel = (TextView) v.findViewById(R.id.text_cancel);
                        TextView text_ok = (TextView) v.findViewById(R.id.text_ok);

                        ImageView ivCancel = (ImageView) v.findViewById(R.id.img_cancel);

                        text_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.dismiss();
                            }
                        });

                        text_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.returnData();
                                pvCustomOptions.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.dismiss();
                            }
                        });
                    }
                }).setSelectOptions(start_select, end_select).isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isDialog(true)
                .setOutSideCancelable(false)
                .build();

        // pvCustomOptions.setPicker(timeList,timeList);//添加数据
        pvCustomOptions.setPicker(timeList, options2Items);
        // pvCustomOptions.setSelectOptions(6,6);
        //  pvCustomOptions.setSelectOptions(3,4);
        text_shop_get_time.setText(timeList.get(start_select).getPickerViewText() + "——" + options2Items.get(start_select).get(end_select).getPickerViewText());

        start_time = timeList.get(start_select).getPickerViewText();
        end_time = options2Items.get(start_select).get(end_select).getPickerViewText();


        Dialog mDialog = pvCustomOptions.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvCustomOptions.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }

    }

    private void getOptionData() {

        /**
         * 注意：如果是添加JavaBean实体数据，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */

        ArrayList<TimeHourBean> testList = new ArrayList<>();
        for (int i = 5; i <= 12; i++) {
            if (i > 5) {
                testList.add(new TimeHourBean(i, i + ":00"));
            }
            if (i < 12) {

                if (i > 5) {
                    timeList.add(new TimeHourBean(i, i + ":00"));
                    testList.add(new TimeHourBean(i, i + ":30"));
                }

                if (i < 11) {
                    timeList.add(new TimeHourBean(i, i + ":30"));
                }
            }
        }

        for (int i = 0; i < timeList.size(); i++) {
            if (testList.size() > 0) {
                testList.remove(0);
            }
            ArrayList<TimeHourBean> indexList = new ArrayList<>();
            indexList.addAll(testList);
            options2Items.add(indexList);
        }


        /*--------数据源添加完毕---------*/
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }


    @Override
    protected void onStop() {
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true)
    public void onMessageEvent(ShopTypeSelectListEvent event) {
        EventBus.getDefault().removeStickyEvent(event);
        if (event .getFlag()!=0) {
            return;
        }
        selectedList = event.getResultList();
        String str = "";
        for (int i = 0; i < selectedList.size(); i++) {

            if (i == selectedList.size() - 1 ) {
                str = str + selectedList.get(i).getName() ;
            } else {
                str = str + selectedList.get(i).getName()+ "、";
            }
        }
        text_shop_type.setText(str);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (data != null&&data.getExtras().get("result")!=null) {
                String result = data.getExtras().getString("result","");//得到新Activity 关闭后返回的数据
                    text_shop_address.setText(result);
                 lon=data.getExtras().getDouble("longitude");
                 lat=data.getExtras().getDouble("latitude");

                 name = data.getExtras().getString("name",PublicCache.site_name);
                 id=data.getExtras().getInt("id",PublicCache.site);
                fenceGid = data.getExtras().getString("fenceGid","");
            }

        }else if (requestCode == 3){//个人
            if (data != null&&data.getExtras().get("result")!=null) {
                String result = data.getExtras().getString("result","");//得到新Activity 关闭后返回的数据

                lon=data.getExtras().getDouble("longitude");
                lat=data.getExtras().getDouble("latitude");
                name = data.getExtras().getString("name",PublicCache.site_name);
                id=data.getExtras().getInt("id",PublicCache.site);
                communityId=data.getExtras().getInt("communityId",0);
                communityName=data.getExtras().getString("communityName","");
                communityAbbreviation=data.getExtras().getString("communityAbbreviation","");
                text_shop_gr_address.setText("("+communityName+")"+result);

            }

        }else if (requestCode == 4){
            if ( data!=null){
                id= data.getExtras().getInt("id",PublicCache.site);
                name=data.getExtras().getString("name");
                text_shop_gr_address.setText(name);
                getData(mlocationBean);
            }

        }

    }
    @Subscribe(sticky = true)
    public void onMessageEvent(RegisterPurchaserSecondEvent event) {
        EventBus.getDefault().removeStickyEvent(event);
        if (event.getProcess()!=2){
            return;
        }
        reLoadView(event);

    }
    private void reLoadView(RegisterPurchaserSecondEvent event){
        Map<String, Object> temp_map=event.getValues_map();

        image_url=(String)temp_map.get("imageUrl");
        if (!TextUtils.isEmpty(image_url)) {
            ImageLoaderUtils.loadImage(img_shop_picture, image_url);
        }
       String  hotelName=(String)temp_map.get("hotelName");
        if (!TextUtils.isEmpty(hotelName)) {
            edit_shop_name.setText(hotelName);
        }
        String  hotelAddress=(String)temp_map.get("hotelAddress");
        if (!TextUtils.isEmpty(hotelAddress)) {
            if (getType()==2){
                text_shop_address.setText(hotelAddress);
            }else {
                text_shop_gr_address.setText(hotelAddress);
            }

        }
        String  deliveredTime=(String)temp_map.get("deliveredTime");
        if (!TextUtils.isEmpty(deliveredTime)) {
            start_time=deliveredTime;
        }
        String  deliveredTimeEnd=(String)temp_map.get("deliveredTimeEnd");
        if (!TextUtils.isEmpty(deliveredTimeEnd)) {
            end_time=deliveredTimeEnd;
        }
        if (!TextUtils.isEmpty(start_time)&&!TextUtils.isEmpty(end_time)) {
            text_shop_get_time.setText(start_time + "——" + end_time);
        }
          fenceGid=(String)temp_map.get("fenceGid");

        String  verifyCode=(String)temp_map.get("verifyCode");
        if (!TextUtils.isEmpty(verifyCode)) {
            if (getType()==2){
                edit_shop_invite.setText(verifyCode);
            }else {
                ed_gr_code.setText(verifyCode);
            }

        }

        start_select=(Integer) temp_map.get("start_select");
        end_select=(Integer) temp_map.get("end_select");

        id=(Integer)temp_map.get("id");
        name=(String)temp_map.get("name");

        try {
            String  lon1=(String)temp_map.get("lon");
            if (!TextUtils.isEmpty(lon1)) {
                lon=Double.parseDouble(lon1);
            }
            String  lat1=(String)temp_map.get("lat");
            if (!TextUtils.isEmpty(lat1)) {
                lat=Double.parseDouble(lat1);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        selectedList = event.getSelectedList();
        if (selectedList == null) {
            return;
        }
        String str = "";
        for (int i = 0; i < selectedList.size(); i++) {

            if (i == selectedList.size() - 1 ) {
                str = str + selectedList.get(i).getName() ;
            } else {
                str = str + selectedList.get(i).getName()+ "、";
            }
        }
        text_shop_type.setText(str);


    }
    @Override
    public void onBackPressed() {
        finish();
//        backDialog();

    }


    private void backDialog(){
        DialogUtils.getInstance(getBaseActivity()).getSimpleDialog("是否保存已有数据").setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }, R.color.gray_69).setPositiveButton("保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Map<String, Object> save_map = new HashMap<>();

                save_map.put("hotelName", edit_shop_name.getText().toString());  //0-注册，1-门店创建
                if (getType()==2){
                    save_map.put("hotelAddress", text_shop_address.getText().toString());  //0-注册，1-门店创建

                }else {
                    save_map.put("hotelAddress", text_shop_gr_address.getText().toString());  //0-注册，1-门店创建

                }

                save_map.put("imageUrl", image_url);  //门店形象照

                save_map.put("deliveredTime", start_time);  //门店到货开始时间
                save_map.put("deliveredTimeEnd", end_time);  //门店到货结束时间
                save_map.put("fenceGid", fenceGid);  //商圈id

                if (!TextUtils.isEmpty(edit_shop_invite.getText().toString().trim())) {
                    if (getType()==2){
                        save_map.put("verifyCode", edit_shop_invite.getText().toString().trim());  //邀请码

                    }else {
                        save_map.put("verifyCode", ed_gr_code.getText().toString().trim());  //邀请码

                    }
                }


                save_map.put("lon", lon+"");
                save_map.put("lat", lat+"");
                save_map.put("start_select", start_select);
                save_map.put("end_select", end_select);

                save_map.put("id", id);  //门店城市ID
                save_map.put("name", name);  //门店城市名称


                //初始化的数据
                RegisterPurchaserFirstActivity registerPurchaserFirstActivity = ActivityManage.getActivity(RegisterPurchaserFirstActivity.class);
                if (registerPurchaserFirstActivity != null) {
                    if (ActivityManage.isActivityExist(RegisterPurchaserFirstActivity.class)) {
                        registerPurchaserFirstActivity.setResult_map(save_map);
                        registerPurchaserFirstActivity.setSelectedList(selectedList);
                        registerPurchaserFirstActivity.setSave(true);
                    }
                }
                //EventBus.getDefault().postSticky(new RegisterPurchaserSecondEvent(save_map,selectedList,1));
                finish();
            }
        }, R.color.orange_yellow_ff7d01).show();
    }


    public void  location(){
        rxPermissions.request( Manifest.permission.ACCESS_COARSE_LOCATION).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean b) throws Exception {
                if (b){
                    LocationUtils.getInstance().startLocalService("");
                    isFirst=true;
//                    Intent intent2 = new Intent(RegisterPurchaserSecondActivity.this, XiaoQuAddressActivity.class);
//                    startActivityForResult(intent2,3);
                }

            }
        });
    }

    @Override
    public void ok() {
        radio_agreement.setSelected(!radio_agreement.isSelected());
        register_OK.setEnabled(radio_agreement.isSelected());
        agreementPoupWindow.dismiss();
    }

    @Override
    public void cancle() {


        ManageActivity manageActivity = ActivityManage.getActivity(ManageActivity.class);
        if (manageActivity != null && manageActivity.mViewPager != null)
            manageActivity.mViewPager.setCurrentItem(0, false);
        ActivityManage.setTopActivity(ManageActivity.class);

    }

    @Override
    public void onUserNameChangeListener(String s) {
        phone_input = UserNameUtill.isPhoneNO(username_edit.getText().toString());
        if (phone_input) {
            if (get_verificationCode.getCurrentTextColor() != UIUtils.getColor(R.color.white)) {
                get_verificationCode.setTextColor(UIUtils.getColor(R.color.white));//
                get_verificationCode.setBackgroundResource(R.drawable.r_round_rect_solid_orange_ff7d01);
            }
        }else{
            if (get_verificationCode.getCurrentTextColor() != UIUtils.getColor(R.color.orange_yellow_ff7d01)) {
                get_verificationCode.setTextColor(UIUtils.getColor(R.color.orange_yellow_ff7d01));
                get_verificationCode.setBackgroundResource(R.drawable.r_round_rect_solid_gray_66);
            }
        }
    }

    @Override
    public void onUserNameCloseListener() {
        get_verificationCode.setTextColor(UIUtils.getColor(R.color.gray_69));
        get_verificationCode.setBackgroundResource(R.drawable.r_round_rect_solid_gray_66);
    }

    @Override
    public void select_city() {

    }

    @Override
    public void select_city_one() {

    }

    @Subscribe
    public void eventCode(LocationBean locationBean) {
        LogUtils.e(locationBean);//根据酒店的经纬度计算距离
        mlocationBean=locationBean;
        Intent intent1 = new Intent(this, CityActivity.class);
        intent1.putExtra("data", PublicCache.findByIsActive);
        startActivityForResult(intent1, 4);

    }

    @Override
    protected void initData() {
        super.initData();
        registerEventBus(this);
    }

    public boolean isEventBusRegisted(Object subscribe) {
        return EventBus.getDefault().isRegistered(subscribe);
    }
    public void registerEventBus(Object subscribe) {
        if (!isEventBusRegisted(subscribe)) {
            EventBus.getDefault().register(subscribe);
        }
    }
    public void unregisterEventBus(Object subscribe) {
        if (isEventBusRegisted(subscribe)) {
            EventBus.getDefault().unregister(subscribe);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterEventBus(this);
    }
    public void getData(LocationBean locationBean){
        ShowLoadingDialog.showLoadingDialog(this);
        Map<String,Object> map=new HashMap<>();
        map.put("page",0);
        map.put("ps",20);
        map.put("pn",1);
        map.put("lon",locationBean.getLongitude());
        map.put("lat",locationBean.getLatitude());
        map.put("communityName","");

        LogUtils.e(map);
        getRequestPresenter().customerCommunity_find(id,map, new RequestCallback<XiaoQuAddressItem>() {
            @Override
            protected void onSuc(XiaoQuAddressItem body) {
                ShowLoadingDialog.close();
                communityId=body.getData().getDataList().getItems().get(0).getId();
                lon=Double.parseDouble(body.getData().getDataList().getItems().get(0).getLon());
                lat=Double.parseDouble(body.getData().getDataList().getItems().get(0).getLat());
                communityAbbreviation=body.getData().getDataList().getItems().get(0).getCommunityShortName();
                communityName=body.getData().getDataList().getItems().get(0).getCommunityName();
            }

            @Override
            public void onFailed(int errCode, String msg) {
                ShowLoadingDialog.close();
            }
        });
    }
}
