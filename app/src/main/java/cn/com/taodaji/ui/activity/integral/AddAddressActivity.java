package cn.com.taodaji.ui.activity.integral;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.activity.ActivityManage;
import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.AddressInfo;
import cn.com.taodaji.model.entity.BaseIntegral;
import cn.com.taodaji.model.entity.CartQuantity;
import cn.com.taodaji.model.entity.IntegralShopCart;
import cn.com.taodaji.model.entity.RedactAddress;
import cn.com.taodaji.ui.activity.integral.adapter.IntegralShopCartAdapter;
import cn.com.taodaji.ui.activity.integral.popuwindow.BuyIntegralPopupWindow;
import cn.com.taodaji.ui.activity.integral.popuwindow.DelPopuWindow;

import cn.com.taodaji.ui.activity.integral.tools.ActivityManager;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.ui.activity.integral.tools.ShowLoadingDialog;
import tools.activity.DataBaseActivity;
import tools.animation.ScrollLinearLayoutManager;
import tools.statusbar.Eyes;

public class AddAddressActivity  extends DataBaseActivity implements DelPopuWindow.DelPopuWindowListener {
    @BindView(R.id.intergral_toolbar)
    Toolbar intergral_toolbar;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.tv_right)
    TextView tv_right;

    @BindView(R.id.tv_more)
    TextView tv_more;

    @BindView(R.id.tv_gs)
    TextView tv_gs;
    @BindView(R.id.tv_home)
    TextView tv_home;
    @BindView(R.id.ed_name)
    EditText ed_name;
    @BindView(R.id.ed_address)
    EditText ed_address;
    @BindView(R.id.ed_phone)
    EditText ed_phone;
    @BindView(R.id.btn)
    Button btn;
    private int isDefault;
    private  RedactAddress.DataBean d;

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    private DelPopuWindow delPopuWindow;
    @OnClick({R.id.tv_more,R.id.tv_gs,R.id.tv_home,R.id.btn_back,R.id.btn,R.id.tv_right})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_right:
                if (delPopuWindow!=null){
                    if (delPopuWindow.isShowing()){
                        return;
                    }

                }
                delPopuWindow = new DelPopuWindow(this);
                delPopuWindow.setDismissWhenTouchOutside(false);
                delPopuWindow.setDelPopuWindowListener(this);
                delPopuWindow.setInterceptTouchEvent(false);
                delPopuWindow.setPopupWindowFullScreen(true);//铺满
                delPopuWindow.showPopupWindow();
                break;
            case R.id.tv_more:
                setIsDefault(1);
                tv_more.setSelected(true);
                tv_home.setSelected(false);
                tv_gs.setSelected(false);
                break;
            case R.id.tv_home:
                setIsDefault(2);
                tv_more.setSelected(false);
                tv_home.setSelected(true);
                tv_gs.setSelected(false);
                break;
            case R.id.tv_gs:
                tv_more.setSelected(false);
                tv_home.setSelected(false);
                tv_gs.setSelected(true);
                setIsDefault(3);
                break;
            case R.id.btn_back:
                finish();
                break;

            case R.id.btn:
                if (ListUtils.isNullOrZeroLenght(ed_name.getText().toString())){
                    UIUtils.showToastSafe("请填写收货人");
                    return;
                }
                if (ListUtils.isNullOrZeroLenght(ed_phone.getText().toString())){
                    UIUtils.showToastSafe("请输入收货人手机号码");
                    return;
                }
                if (!ListUtils.isTel(ed_phone.getText().toString())){
                    UIUtils.showToastSafe("您输入的手机号码格式不正确");
                    return;
                }
                if (ListUtils.isNullOrZeroLenght(ed_address.getText().toString())){
                    UIUtils.showToastSafe("请填写收货人地址");
                    return;
                }

                if (getIntent().getStringExtra("bj")!=null){
                    shipAddress_update();
                }else {
                    shipAddress_add();
                }

                break;

        }
    }

    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.add_address_layout);
    }

    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        Eyes.setStatusBarColor(this, UIUtils.getColor(this, R.color.white));
        Eyes.setLightStatusBar(this,true);//设置状态栏字体颜色
        intergral_toolbar.setBackgroundColor(getResources().getColor(R.color.white));


        tv_title.setTextColor(getResources().getColor(R.color.gray_66));
        btn_back.setVisibility(View.VISIBLE);
        btn_back.setImageResource(R.mipmap.left_arrow_gary);
        tv_right.setTextColor(getResources().getColor(R.color.gray_66));
        tv_right.setVisibility(View.VISIBLE);

        tv_more.setSelected(false);
        if (getIntent().getStringExtra("bj")!=null){
            tv_title.setText("编辑地址");
            tv_right.setText("删除");
            tv_right.setVisibility(View.VISIBLE);
          d = (RedactAddress.DataBean) getIntent().getSerializableExtra("DataBean");
            LogUtils.i(d);
            ed_name.setText(d.getRecevingPersion());
            ed_phone.setText(d.getRecevingMobile());
            ed_address.setText(d.getDetailAddress());
         if(d.getIsDefault()==2){
             setIsDefault(2);
             tv_home.setSelected(true);
             tv_more.setSelected(false);
             tv_gs.setSelected(false);
            }else if (d.getIsDefault()==3){
             setIsDefault(3);
             tv_gs.setSelected(true);
             tv_home.setSelected(false);
             tv_more.setSelected(false);
            }else if(d.getIsDefault()==1){
             setIsDefault(1);
             tv_gs.setSelected(false);
             tv_home.setSelected(false);
             tv_more.setSelected(true);

         }
        }else {
            tv_title.setText("添加地址");
            tv_right.setVisibility(View.GONE);
        }

    }

    @Override
    protected void initData() {
        super.initData();
    }

    public void shipAddress_delete(){
        ShowLoadingDialog.showLoadingDialog(this);
        Map<String,Object> map=new HashMap<>();
        map.put("id",d.getId());
        getIntegralRequestPresenter().shipAddress_delete(map, new RequestCallback<BaseIntegral>(this) {
            @Override
            protected void onSuc(BaseIntegral body) {
                ShowLoadingDialog.close();
                EventBus.getDefault().post(new BaseIntegral());
                finish();

            }
            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
                ShowLoadingDialog.close();
                UIUtils.showToastSafe(msg);

            }
        });
    }

    public void shipAddress_add(){
        ShowLoadingDialog.showLoadingDialog(this);
        Map<String,Object> map=new HashMap<>();
        map.put("userId",PublicCache.loginPurchase.getEmpRole()==0?PublicCache.loginPurchase.getEntityId():PublicCache.loginPurchase.getLoginUserId());
        map.put("detailAddress",ed_address.getText().toString());
        map.put("recevingPersion",ed_name.getText().toString());
        map.put("recevingMobile",ed_phone.getText().toString());
        map.put("status","1");
        map.put("isDefault",getIsDefault());
        map.put("city","");
        map.put("gid","");
        map.put("lon","");
        map.put("lat","");
        getIntegralRequestPresenter().shipAddress_add(map, new RequestCallback<AddressInfo>(this) {
            @Override
            protected void onSuc(AddressInfo body) {
                ShowLoadingDialog.close();
                EventBus.getDefault().post(body);
                ActivityManager.getActivityManager().removeAllActivity();
                finish();

            }
            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
                ShowLoadingDialog.close();
                UIUtils.showToastSafe(msg);

            }
        });
    }
    public void shipAddress_update(){
        ShowLoadingDialog.showLoadingDialog(this);
        Map<String,Object> map=new HashMap<>();
        map.put("id",d.getId());
        map.put("userId", PublicCache.loginPurchase.getEmpRole()==0?PublicCache.loginPurchase.getEntityId():PublicCache.loginPurchase.getLoginUserId());
        map.put("detailAddress",ed_address.getText().toString());
        map.put("recevingPersion",ed_name.getText().toString());
        map.put("recevingMobile",ed_phone.getText().toString());
        map.put("status","1");
        map.put("isDefault",getIsDefault());
        map.put("city","");
        map.put("gid","");
        map.put("lon","");
        map.put("lat","");
        getIntegralRequestPresenter().shipAddress_update(map, new RequestCallback<AddressInfo>(this) {
            @Override
            protected void onSuc(AddressInfo body) {
                ShowLoadingDialog.close();
                EventBus.getDefault().post(body);
                ActivityManager.getActivityManager().removeAllActivity();
                finish();

            }
            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
                ShowLoadingDialog.close();
                UIUtils.showToastSafe(msg);

            }
        });
    }

    @Override
    public void delete() {
        shipAddress_delete();
    }
}
