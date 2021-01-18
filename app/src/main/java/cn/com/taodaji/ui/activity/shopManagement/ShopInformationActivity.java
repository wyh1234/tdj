package cn.com.taodaji.ui.activity.shopManagement;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;
import com.base.utils.UserNameUtill;
import com.base.utils.ViewUtils;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.ShopDetailBean;
import cn.com.taodaji.model.entity.ShopTypeBean;
import cn.com.taodaji.model.entity.TimeHourBean;
import cn.com.taodaji.model.entity.UpdateAddressBean;
import cn.com.taodaji.model.entity.UpdateCustomerBean;
import cn.com.taodaji.model.entity.UpdateLeaderBean;
import cn.com.taodaji.model.event.SelectShopOrPositionEvent;
import cn.com.taodaji.model.event.ShopTypeSelectListEvent;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.ui.activity.employeeManagement.LicenseActivity;
import cn.com.taodaji.ui.activity.employeeManagement.PopupBottomActivity;
import cn.com.taodaji.ui.activity.login.RegisterPurchaserShopTypeActivity;
import cn.com.taodaji.ui.activity.login.ShopAddressActivity;
import retrofit2.http.Body;
import tools.activity.SimpleToolbarActivity;

public class ShopInformationActivity extends SimpleToolbarActivity implements View.OnClickListener {

    private View mainView;
    private TextView shopTitle,headName,shopAddress,shopType,arriveTime,currentConsignee,isUpload,isCertification,strNumber;
    private LinearLayout name,address,type,time,consignee,upload,certification,streetNumber;
    private ImageView hadCertification;
    private String[] consigneeArray;
    private List<String> acountBeanList = new ArrayList<>();
    private OptionsPickerView pvCustomOptions;
    private List<ShopDetailBean.DataBean.SubAcountBean> leadersList = new ArrayList<>();
    private List<ShopTypeBean> oldSelectList = new ArrayList<>();
    private int currentLeaderId,currentEntiyId,currentAddressId;
    private int imgCheckStatus,licenceCheckStatus; //营业执照状态 0-待审核，1-审核成功，2-审核失败
    private String licenceUrlRefuseInfo,currentName,currentPhone; //营业执照审核失败原因
    public String imageUrl=null,licenceUrl=null;
    private StringBuffer currentShopType = new StringBuffer();
    private ArrayList<TimeHourBean> timeList = new ArrayList<>();
    private String currentCityName;
    public double lat,lon;

    private int start_select=7;
    private int end_select=0;

    private ArrayList<ArrayList<TimeHourBean>> options2Items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void titleSetting(Toolbar toolbar){
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("门店信息");
    }

    @Override
    public void initMainView(){
        mainView = getLayoutView(R.layout.activity_shop_information);
        body_other.addView(mainView);


        loading("请稍等");
        name = ViewUtils.findViewById(mainView,R.id.ll_head_name);
        address = ViewUtils.findViewById(mainView,R.id.ll_shop_address);
        type = ViewUtils.findViewById(mainView,R.id.ll_shop_type);
        time = ViewUtils.findViewById(mainView,R.id.ll_arrive_time);
        consignee = ViewUtils.findViewById(mainView,R.id.ll_current_consignee);
        upload = ViewUtils.findViewById(mainView,R.id.ll_is_upload);
        certification = ViewUtils.findViewById(mainView,R.id.ll_is_certification);
        streetNumber = ViewUtils.findViewById(mainView,R.id.ll_shop_street_number);
        name.setOnClickListener(this);
        address.setOnClickListener(this);
        type.setOnClickListener(this);
        time.setOnClickListener(this);
        consignee.setOnClickListener(this);
        upload.setOnClickListener(this);
        certification.setOnClickListener(this);
        streetNumber.setOnClickListener(this);

        shopTitle = ViewUtils.findViewById(mainView,R.id.tv_shop_title);
        headName = ViewUtils.findViewById(mainView,R.id.tv_head_name);
        shopAddress = ViewUtils.findViewById(mainView,R.id.tv_shop_address);
        shopType = ViewUtils.findViewById(mainView,R.id.tv_shop_type);
        arriveTime = ViewUtils.findViewById(mainView,R.id.tv_arrive_time);
        currentConsignee = ViewUtils.findViewById(mainView,R.id.tv_current_consignee);
        isUpload = ViewUtils.findViewById(mainView,R.id.tv_is_upload);
        isCertification = ViewUtils.findViewById(mainView,R.id.tv_is_certification);
        strNumber = ViewUtils.findViewById(mainView,R.id.tv_shop_street_number);

        hadCertification = ViewUtils.findViewById(mainView,R.id.iv_had_certification);

        getOptionData();
        initCustomOptionPicker();
        initViewData();
    }

    @Override
    public void onClick(View view){
        if (PublicCache.loginPurchase==null)return;
        if (PublicCache.loginPurchase.getEmpRole()==1||PublicCache.loginPurchase.getEmpRole()==2||PublicCache.loginPurchase.getEmpRole()==5) {
            UIUtils.showToastSafesShort(PublicCache.getRoleType().getValueOfKey(PublicCache.loginPurchase.getEmpRole())+"没有该操作权限");
            return ;
        }
        switch (view.getId()){
            case R.id.ll_head_name:
                if (consigneeArray.length==1){
                    LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View contentView = inflater.inflate(com.base.R.layout.dialog_current_leader, null);
                    Button ok = contentView.findViewById(R.id.bt_ok);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setCancelable(false);
                    builder.setView(contentView);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.getWindow().setBackgroundDrawableResource(com.base.R.color.transparent);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }else {
                    Intent intent = new Intent(ShopInformationActivity.this, PopupBottomActivity.class);
                    intent.putExtra("title", "负责人");
                    intent.putExtra("itemList", consigneeArray);
                    intent.putExtra("flag", true);
                    intent.putExtra("isLeader",true);
                    startActivity(intent);
                }
                break;
            case R.id.ll_shop_address:
                Intent intent1 = new Intent(ShopInformationActivity.this, ShopAddressActivity.class);
                intent1.putExtra("cityName",currentCityName);
                intent1.putExtra("shopAddress",shopAddress.getText().toString().trim());
                intent1.putExtra("lat",lat);
                intent1.putExtra("lon",lon);
                intent1.putExtra("isRegister",true);
                startActivityForResult(intent1,1);
                break;
            case R.id.ll_shop_type:
                Intent intent2 = new Intent(ShopInformationActivity.this, RegisterPurchaserShopTypeActivity.class);
                intent2.putExtra("flag",1);
                intent2.putExtra("oldSelectedList",(Serializable)oldSelectList);
                startActivity(intent2);
                break;
            case R.id.ll_arrive_time:
                if ( pvCustomOptions != null) {
                    pvCustomOptions.show(); //弹出自定义条件选择器
                }
                break;
            case R.id.ll_current_consignee:
                LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View contentView = inflater.inflate(com.base.R.layout.dialog_current_receiver, null);
                EditText name = contentView.findViewById(R.id.et_receiver_name);
                EditText phone = contentView.findViewById(R.id.et_receiver_phone);
                Button cancel = contentView.findViewById(R.id.bt_cancel);
                Button ok = contentView.findViewById(R.id.bt_ok);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setView(contentView);
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawableResource(com.base.R.color.transparent);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (name.getText().toString().trim().equals("")){
                            UIUtils.showToastSafesShort("收货人名称不能为空");
                        }else if (!UserNameUtill.isPhoneNO(phone.getText().toString().trim())){
                            UIUtils.showToastSafesShort("收货人电话格式不正确");
                        } else {
                            HashMap<String,Object> map = new HashMap<>();
                            map.put("addressId",currentAddressId);
                            map.put("consignee",name.getText().toString().trim());
                            map.put("phoneNumber",phone.getText().toString().trim());
                            RequestPresenter.getInstance().updateConsigneeOrTime(map, new RequestCallback<UpdateAddressBean>() {
                                @Override
                                protected void onSuc(UpdateAddressBean body) {
                                    alertDialog.dismiss();
                                    UIUtils.showToastSafesShort("修改成功");
                                    currentConsignee.setText(name.getText().toString().trim()+"  "+phone.getText().toString().trim());
                                    initViewData();
                            }

                                @Override
                                public void onFailed(int errCode, String msg) {
                                    UIUtils.showToastSafesShort(msg);
                                }
                            });
                        }
                    }
                });
                name.setText(currentName);
                phone.setText(currentPhone);
                alertDialog.show();
                break;
            case R.id.ll_is_upload:
                Intent intent4 = new Intent(ShopInformationActivity.this,StoreImageActivity.class);
                intent4.putExtra("imgStatus",imgCheckStatus);
                intent4.putExtra("url",imageUrl);
                startActivity(intent4);
                break;
            case R.id.ll_is_certification:
                Intent intent3 = new Intent(ShopInformationActivity.this, LicenseActivity.class);
                intent3.putExtra("status", licenceCheckStatus);
                intent3.putExtra("info",licenceUrlRefuseInfo);
                intent3.putExtra("licenseUrl",licenceUrl);
                startActivity(intent3);
                break;
            case R.id.ll_shop_street_number:
                LayoutInflater inflater1 = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view1 = inflater1.inflate(R.layout.dialog_street_number, null);
                EditText number = view1.findViewById(R.id.et_street_number);
                Button cancel1 = view1.findViewById(R.id.bt_cancel);
                Button ok1 = view1.findViewById(R.id.bt_ok);
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setCancelable(false);
                builder1.setView(view1);
                AlertDialog alertDialog1 = builder1.create();
                alertDialog1.getWindow().setBackgroundDrawableResource(com.base.R.color.transparent);
                cancel1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog1.dismiss();
                    }
                });
                ok1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HashMap<String,Object> map = new HashMap<>();
                        map.put("addressId",currentAddressId);
                        map.put("streetNumber",number.getText().toString());
                        RequestPresenter.getInstance().updateConsigneeOrTime(map, new RequestCallback<UpdateAddressBean>() {
                            @Override
                            protected void onSuc(UpdateAddressBean body) {
                                alertDialog1.dismiss();
                                UIUtils.showToastSafesShort("修改成功");
                                strNumber.setText(number.getText().toString().trim());
                                initViewData();
                            }

                            @Override
                            public void onFailed(int errCode, String msg) {
                                UIUtils.showToastSafesShort(msg);
                            }
                        });

                    }
                });
                number.setText(strNumber.getText().toString().trim());
                alertDialog1.show();
                break;
                default: break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SelectShopOrPositionEvent event) {
        if (event.isFlag()){
            loading("请稍后");
            HashMap<String,Object> map = new HashMap<>();
            map.put("entityId",currentEntiyId);
            map.put("oldLeaderId",currentLeaderId);
            map.put("leaderId",leadersList.get(event.getPosition()).getCustomerEntityId());
            RequestPresenter.getInstance().updateLeader(map, new RequestCallback<UpdateLeaderBean>() {
                @Override
                protected void onSuc(UpdateLeaderBean body) {
                    if (body.getErr()==0){
                    loadingDimss();
                    StringBuffer stringBuffer = new StringBuffer(event.getCurrentSelected());
                    stringBuffer.replace(stringBuffer.indexOf("("), stringBuffer.indexOf(")")+1,"  ");
                    headName.setText(stringBuffer.toString());
                    }else {
                        UIUtils.showToastSafe(body.getMsg());
                    }
                }

                @Override
                public void onFailed(int errCode, String msg) {
                    loadingDimss();
                    UIUtils.showToastSafesShort(msg);
                }
            });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ShopTypeSelectListEvent event){
        if (event.getFlag()==1){
            loading("请稍候");
            StringBuffer sb = new StringBuffer();
            StringBuffer shopTypeId = new StringBuffer();
            for (ShopTypeBean bean : event.getResultList()){
                sb.append(bean.getName());
                sb.append("、");
                shopTypeId.append(bean.getHotelTypeId());
                shopTypeId.append(",");
            }
            HashMap<String,Object> map = new HashMap<>();
            map.put("entityId",PublicCache.loginPurchase.getEntityId());
            map.put("hotelTypeIds",shopTypeId.toString());
            RequestPresenter.getInstance().updateShopType(map, new RequestCallback<UpdateCustomerBean>() {
                @Override
                protected void onSuc(UpdateCustomerBean body) {
                    loadingDimss();
                    if(sb.length()==0){
                        shopType.setText(sb.toString());
                    }else {
                        sb.deleteCharAt(sb.length() - 1);
                        shopType.setText(sb.toString());
                    }
                }

                @Override
                public void onFailed(int errCode, String msg) {
                    loadingDimss();
                    UIUtils.showToastSafesShort(msg);
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        initViewData();
    }

    public void initViewData(){
        if (PublicCache.loginPurchase!=null){
            RequestPresenter.getInstance().getShopDetail(PublicCache.loginPurchase.getEntityId(), new RequestCallback<ShopDetailBean>() {
                @Override
                protected void onSuc(ShopDetailBean body) {
                    imgCheckStatus = body.getData().getImgCheckStatus();
                    licenceCheckStatus = body.getData().getLicenceUrlCheckStatus();
                    licenceUrlRefuseInfo = body.getData().getLicenceUrlRefuseInfo();
                    currentEntiyId=body.getData().getEntityId();
                    currentLeaderId =body.getData().getLeaderId();
                    shopTitle.setText(body.getData().getHotelName());
                    currentAddressId = body.getData().getShipAddr().getEntity_id();
                    headName.setText(body.getData().getLeader()+" "+body.getData().getLeaderPhone());
                    shopAddress.setText(body.getData().getShipAddr().getStreet());
                    strNumber.setText(body.getData().getShipAddr().getStreet_number());
                    currentCityName = body.getData().getSiteName();
                    oldSelectList.clear();
                    currentShopType.setLength(0);
                    for (ShopDetailBean.DataBean.HotelTypeListBean bean : body.getData().getHotelTypeList()){
                        currentShopType.append(bean.getName());
                        currentShopType.append("、");
                        ShopTypeBean shopTypeBean = new ShopTypeBean();
                        shopTypeBean.setHotelTypeId(bean.getHotelTypeId());
                        shopTypeBean.setLevel(bean.getLevel());
                        shopTypeBean.setParentId(bean.getParentId());
                        shopTypeBean.setName(bean.getName());
                        oldSelectList.add(shopTypeBean);
                    }
                    if(currentShopType.length()==0){
                        shopType.setText(currentShopType.toString());
                    }else {
                        currentShopType.deleteCharAt(currentShopType.length()-1);
                        shopType.setText(currentShopType);
                    }
                    arriveTime.setText(body.getData().getShipAddr().getDeliveredTime()+"—"+body.getData().getShipAddr().getDeliveredTimeEnd());
                    currentName = body.getData().getShipAddr().getMiddlename();
                    currentPhone = body.getData().getShipAddr().getTelephone();
                    currentConsignee.setText(currentName+"  "+currentPhone);
                    lat = body.getData().getShipAddr().getLat();
                    lon = body.getData().getShipAddr().getLon();
                    imageUrl = body.getData().getImageUrl().trim();
                    if (!imageUrl.equals("")){
                        switch (imgCheckStatus){
                            case 0:
                                isUpload.setText("正在审核");
                                isUpload.setTextColor(getResources().getColor(R.color.gray_66));
                                break;
                            case 1:
                                isUpload.setText("查看");
                                isUpload.setTextColor(getResources().getColor(R.color.gray_66));
                                break;
                            case 2:
                                isUpload.setText("审核失败");
                                isUpload.setTextColor(getResources().getColor(R.color.red_dark));
                                break;
                            default:break;
                        }
                    }
                    licenceUrl = body.getData().getBzlicenceUrl().trim();
                    if (!licenceUrl.equals("")){
                        switch (licenceCheckStatus){
                            case 0:
                                isCertification.setText("正在审核");
                                isCertification.setTextColor(getResources().getColor(R.color.gray_66));
                                break;
                            case 1:
                                isCertification.setText("");
                                hadCertification.setVisibility(View.VISIBLE);
                                break;
                            case 2:
                                isCertification.setText("审核失败");
                                isCertification.setTextColor(getResources().getColor(R.color.red_dark));
                                break;
                                default:break;
                        }
                    }
                    acountBeanList.clear();
                    leadersList.clear();
                    for (ShopDetailBean.DataBean.SubAcountBean item : body.getData().getSubAcount()){
                        StringBuffer sb = new StringBuffer();
                        sb.append(item.getWorkName());
                        switch (item.getRole()){
                            case 0://管理员
                                sb.append("(管理员)");
                                break;
                            case 1:
                                sb.append("(厨师)");
                                break;
                            case 2:
                                sb.append("(财务)");
                                break;
                            case 3:
                                sb.append("(管理员)");
                                break;
                            case 4:
                                sb.append("(店长)");
                                break;
                            case 5:
                                sb.append("(员工)");
                                break;
                                default:break;
                        }
                        sb.append(item.getPhone());
                        acountBeanList.add(sb.toString());
                        leadersList.add(item);
                    }
                    consigneeArray = new String[acountBeanList.size()];
                    acountBeanList.toArray(consigneeArray);
                    loadingDimss();
                }

                @Override
                public void onFailed(int errCode, String msg) {
                    loadingDimss();
                    UIUtils.showToastSafesShort(msg);
                }
            });

        }
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
                String tx = timeList.get(options1).getPickerViewText()+"——"+  options2Items.get(options1).get(option2).getPickerViewText();
                HashMap<String,Object> map = new HashMap<>();
                map.put("addressId",currentAddressId);
                map.put("deliveredTime",timeList.get(options1).getPickerViewText());
                map.put("deliveredTimeEnd",options2Items.get(options1).get(option2).getPickerViewText());
                RequestPresenter.getInstance().updateConsigneeOrTime(map, new RequestCallback<UpdateAddressBean>() {
                    @Override
                    protected void onSuc(UpdateAddressBean body) {
                        UIUtils.showToastSafesShort("修改成功");
                        arriveTime.setText(tx);
                    }

                    @Override
                    public void onFailed(int errCode, String msg) {
                        UIUtils.showToastSafesShort(msg);
                    }
                });
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
                }) .setSelectOptions(start_select, end_select).isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isDialog(true)
                .setOutSideCancelable(false)
                .build();

        // pvCustomOptions.setPicker(timeList,timeList);//添加数据
        pvCustomOptions.setPicker(timeList,options2Items);
        // pvCustomOptions.setSelectOptions(6,6);
        //  pvCustomOptions.setSelectOptions(3,4);
        arriveTime.setText(timeList.get(start_select).getPickerViewText()+"——"+  options2Items.get(start_select).get(end_select).getPickerViewText());

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

        for (int i = 0; i <timeList.size() ; i++) {
            if (testList.size()>0) {
                testList.remove(0);
            }
            ArrayList<TimeHourBean> indexList = new ArrayList<>();
            indexList.addAll(testList);
            options2Items.add(indexList);
        }
        /*--------数据源添加完毕---------*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            String result = data.getExtras().getString("result");//得到新Activity 关闭后返回的数据
            loading("请稍候");
            HashMap<String,Object> map = new HashMap<>();
            map.put("addressId",currentAddressId);
            map.put("street",result);
            map.put("lon",data.getExtras().getDouble("longitude"));
            map.put("lat",data.getExtras().getDouble("latitude"));
            map.put("fenceGid",data.getExtras().getString("fenceGid"));
            RequestPresenter.getInstance().updateShopAddress(map, new RequestCallback<UpdateAddressBean>() {
                @Override
                protected void onSuc(UpdateAddressBean body) {
                    if (body.getErr()==0) {
                        loadingDimss();
                        shopAddress.setText(result);
                    }else {
                        UIUtils.showToastSafesShort(body.getMsg());
                    }
                }

                @Override
                public void onFailed(int errCode, String msg) {
                    loadingDimss();
                    UIUtils.showToastSafesShort(msg);
                }
            });
        }
    }
}
