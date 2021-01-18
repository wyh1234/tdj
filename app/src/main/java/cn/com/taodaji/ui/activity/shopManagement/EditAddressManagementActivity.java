package cn.com.taodaji.ui.activity.shopManagement;

import android.Manifest;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.CommunityAddress;
import cn.com.taodaji.model.entity.CommunityAddressUpdate;
import cn.com.taodaji.model.entity.UpdateCommunityRef;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.ui.activity.integral.tools.ShowLoadingDialog;
import cn.com.taodaji.ui.activity.login.ShopAddressActivity;
import cn.com.taodaji.ui.activity.login.XiaoQuAddressActivity;
import io.reactivex.functions.Consumer;
import tools.activity.SimpleToolbarActivity;
import tools.location.LocationUtils;

public class EditAddressManagementActivity extends SimpleToolbarActivity implements View.OnClickListener{
    private View mainView;
    private TextView ed_phone,ed_gr_no,text_shop_gr_address,text_shop_gr_address_one,ed_gr_no_one;
    private EditText ed_name;
    private CommunityAddress.DataBean dataBean;
    private LinearLayout line_gr_address,line_gr_address_one,ll_gr_no_one,ll_gr_no;
    private Button _ok;
    private RxPermissions rxPermissions;

    private  int communityId;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id._OK:
//                if (ListUtils.isNullOrZeroLenght(ed_phone.getText().toString())){
//                    UIUtils.showToastSafe("请填写收货人手机号码");
//                    return;
//                }
//                if (!ListUtils.isTel(ed_phone.getText().toString())){
//                    UIUtils.showToastSafe("您填写的手机号码格式不正确");
//                    return;
//                }
//                if (dataBean!=null){
                    if (ListUtils.isNullOrZeroLenght(ed_name.getText().toString())){
                        UIUtils.showToastSafe("请填写收货人姓名");
                        return;
                    }
                    if (TextUtils.isEmpty(ed_gr_no.getText().toString().trim())){
                        UIUtils.showToastSafe("请填写门牌号");
                        return;
                    }
//                }

//                if (dataBean!=null){
//                    getData();
//                }else {
                    updateDeliverAddress();
//                }


                break;
            case R.id.line_gr_address:
//                if (dataBean!=null){
                    location();
//                }else {
//                    Intent intent1 = new Intent(this, ShopAddressActivity.class);
//                    intent1.putExtra("title","选择小区位置");
//                    intent1.putExtra("isSupply",true);
//                    startActivityForResult(intent1,2);
//                }

                break;

        }

    }

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("收货地址编辑");
    }

    @Override
    protected void initMainView() {
        rxPermissions = new RxPermissions(this);
        mainView = getLayoutView(R.layout.activity_edit_adress_information);
        body_other.addView(mainView);
        ed_phone= ViewUtils.findViewById(mainView,R.id.ed_phone);
        ed_name= ViewUtils.findViewById(mainView,R.id.ed_name);
        ed_gr_no= ViewUtils.findViewById(mainView,R.id.ed_gr_no);
        text_shop_gr_address= ViewUtils.findViewById(mainView,R.id.text_shop_gr_address);
        _ok= ViewUtils.findViewById(mainView,R.id._OK);
        line_gr_address= ViewUtils.findViewById(mainView,R.id.line_gr_address);
        line_gr_address_one= ViewUtils.findViewById(mainView,R.id.line_gr_address_one);

        ll_gr_no_one= ViewUtils.findViewById(mainView,R.id.ll_gr_no_one);
        ll_gr_no= ViewUtils.findViewById(mainView,R.id.ll_gr_no);
        text_shop_gr_address_one= ViewUtils.findViewById(mainView,R.id.text_shop_gr_address_one);
        ed_gr_no_one= ViewUtils.findViewById(mainView,R.id.ed_gr_no_one);

        line_gr_address.setOnClickListener(this);
        _ok.setOnClickListener(this);
            ed_phone.setText(getIntent().getStringExtra("customerTel"));
            ed_name.setText(getIntent().getStringExtra("customerName"));
        text_shop_gr_address.setText(getIntent().getStringExtra("address"));
        ed_gr_no.setText(getIntent().getStringExtra("streetNumber"));

        dataBean= (CommunityAddress.DataBean) getIntent().getSerializableExtra("body");
        if (dataBean!=null){
//            ed_phone.setText(dataBean.getTelephone());
//            ed_name.setText(dataBean.getMiddlename());
//            ed_gr_no.setText(dataBean.getStreetNumber());
//            ed_gr_no_one.setText(dataBean.getStreetNumber());
//            text_shop_gr_address.setText(dataBean.getAddress());
//            text_shop_gr_address_one.setText(dataBean.getAddress());

            communityId=dataBean.getCommunityId();

//            if (PublicCache.loginPurchase.getAuthStatus()==1){
//                line_gr_address.setVisibility(View.GONE);
//                line_gr_address_one.setVisibility(View.VISIBLE);
//                ll_gr_no_one.setVisibility(View.VISIBLE);
//                ll_gr_no.setVisibility(View.GONE);
//            }else {
                line_gr_address.setVisibility(View.VISIBLE);
                line_gr_address_one.setVisibility(View.GONE);
                ll_gr_no_one.setVisibility(View.GONE);
                ll_gr_no.setVisibility(View.VISIBLE);
//            }
        }
//        else {
//            line_gr_address.setVisibility(View.VISIBLE);
//            line_gr_address_one.setVisibility(View.GONE);
//
//            ed_gr_no.setText(getIntent().getStringExtra("streetNumber"));
//            text_shop_gr_address.setText(getIntent().getStringExtra("address"));
//        }
        }


    public void getData(){

        ShowLoadingDialog.showLoadingDialog(this);
        Map<String,Object> map=new HashMap<>();
        map.put("customerId",dataBean.getEntityId());
        map.put("communityId",communityId);
        map.put("streetNumber",ed_gr_no.getText().toString().trim());
        map.put("middlename",ed_name.getText().toString().trim());
        map.put("telephone",ed_phone.getText().toString().trim());
        LogUtils.e(map);
        getRequestPresenter().customerCommunity_update(map, new RequestCallback<CommunityAddressUpdate>() {
            @Override
            protected void onSuc(CommunityAddressUpdate body) {
                ShowLoadingDialog.close();
                if (body.getData().getMsg().equals("success")){
                    Intent intent = new Intent();
                    //设置返回数据
                    EditAddressManagementActivity.this.setResult(RESULT_OK, intent);
                    //关闭Activity
                    EditAddressManagementActivity.this.finish();
                }


            }

            @Override
            public void onFailed(int errCode, String msg) {
                ShowLoadingDialog.close();
            }
        });



    }

    public void updateDeliverAddress(){
        ShowLoadingDialog.showLoadingDialog(this);
        Map<String,Object> map=new HashMap<>();
        map.put("customerId",PublicCache.loginPurchase.getEntityId());
        map.put("streetNumber",ed_gr_no.getText().toString().trim());
        map.put("address",text_shop_gr_address.getText().toString().trim());
        map.put("receName",ed_name.getText().toString().trim());
        map.put("recePhone",ed_phone.getText().toString().trim());
        map.put("addressId",getIntent().getIntExtra("addressId",0));
        LogUtils.i(map);

        getRequestPresenter().updateDeliverAddress(map, new RequestCallback<UpdateCommunityRef>() {
            @Override
            protected void onSuc(UpdateCommunityRef body) {
                ShowLoadingDialog.close();
                EventBus.getDefault().post(new CommunityAddress());
//                Intent intent = new Intent();
//                //把返回数据存入Intent
////                intent.putExtra("streetNumber", ed_gr_no.getText().toString().trim());
////                intent.putExtra("address",ed_gr_no.getText().toString().trim());
//
//                //设置返回数据
//             setResult(RESULT_OK, intent);
                //关闭Activity
               finish();


            }

            @Override
            public void onFailed(int errCode, String msg) {
                ShowLoadingDialog.close();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3) {
            if (data != null&&data.getExtras().get("result")!=null) {
                String result = data.getExtras().getString("result", "");//得到新Activity 关闭后返回的数据
                text_shop_gr_address.setText("("+(data.getExtras().getString("communityName",""))+")"+result);

                communityId = data.getExtras().getInt("communityId", 0);
            }
        }else {
            if (data != null){
                String result = data.getExtras().getString("result", "");//得到新Activity 关闭后返回的数据
                text_shop_gr_address.setText("("+(data.getExtras().getString("title",""))+")"+result);
            }


        }

    }
    public void  location(){
        rxPermissions.request( Manifest.permission.ACCESS_COARSE_LOCATION).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean b) throws Exception {
                if (b){
//                    LocationUtils.getInstance().startLocalService("");
                    Intent intent2 = new Intent(EditAddressManagementActivity.this, ShopAddressActivity.class);
                    intent2.putExtra("EditAddress","EditAddress");
                    intent2.putExtra("title","title");
                    intent2.putExtra("isSupply",true);
                    intent2.putExtra("deliverScope",getIntent().getIntExtra("deliverScope",0));
                    startActivityForResult(intent2,2);
                }

            }
        });
    }
}
