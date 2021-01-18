package cn.com.taodaji.ui.activity.shopManagement;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.base.retrofit.RequestCallback;
import com.base.utils.DialogUtils;
import com.base.utils.SystemUtils;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.application.MyApplication;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.ChangeHotelBean;
import cn.com.taodaji.model.entity.HotelList;
import cn.com.taodaji.model.entity.PurchaseBean;
import cn.com.taodaji.model.entity.ShopAddressItem;
import cn.com.taodaji.model.event.Login_in;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.model.sqlBean.LoginPurchaseBean;
import cn.com.taodaji.model.sqlBean.LoginSupplierBean;
import cn.com.taodaji.ui.activity.employeeManagement.EmployeesManagementActivity;
import cn.com.taodaji.viewModel.adapter.StoresManagementAdapter;
import tools.activity.SimpleToolbarActivity;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class StoresManagementActivity extends SimpleToolbarActivity implements View.OnClickListener {
    private View mainView;
    private Button createStore;
    private RelativeLayout relativeLayout;
    private TextView currentShopTitle,currentShopAddress,currentExpired;
    private RecyclerView recyclerView;
    private StoresManagementAdapter adapter;
    private List<ShopAddressItem> itemList = new ArrayList<>();
    private String[] tips = {"您是否确定把","","切换为当前门店呢？"};
    private int[] index = {2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


   @Override
    public void titleSetting(Toolbar toolbar){
       setStatusBarColor();
       setToolBarColor();
       simple_title.setText("收货地址管理");
       right_icon_text.setText("员工管理");
       right_icon.setVisibility(View.VISIBLE);
       right_icon.setImageResource(R.mipmap.employees_management);
       right_onclick_line.setOnClickListener(this);
   }

   @Override
   protected void onRestart(){
        super.onRestart();
        initList();
   }
    @Override
    public void initMainView(){
        mainView = getLayoutView(R.layout.activity_stores_management);
        body_other.addView(mainView);

        currentShopTitle = ViewUtils.findViewById(mainView,R.id.tv_current_shop_title);
        currentShopAddress = ViewUtils.findViewById(mainView,R.id.tv_current_shop_address);
        currentExpired = ViewUtils.findViewById(mainView,R.id.tv_expired_address);
        relativeLayout = ViewUtils.findViewById(mainView,R.id.rl_current_shop);
        relativeLayout.setOnClickListener(this);
        createStore = ViewUtils.findViewById(mainView,R.id.btn_create_new_store);
        createStore.setOnClickListener(this);

        recyclerView = ViewUtils.findViewById(mainView,R.id.rv_other_stores);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StoresManagementAdapter(itemList,this);
        recyclerView.setAdapter(adapter);
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                tips[1] = "“"+itemList.get(position).getTitle()+"”";
                DialogUtils.getInstance(StoresManagementActivity.this).getSimpleColorDialog("",tips,index).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loading("请稍候");
                        Map<String,Object> map = new HashMap<>();
                        map.put("loginUserId",PublicCache.loginPurchase.getLoginUserId());
                        map.put("uniqueId",SystemUtils.getDeviceId(getApplicationContext()));
                        map.put("account",itemList.get(position).getAccount());
                        if (PublicCache.deviceToken_umeng == null){
                            PublicCache.deviceToken_umeng = "abc";
                        }
                        map.put("pushToken",PublicCache.deviceToken_umeng);
                        map.put("sourceType","android");
                        RequestPresenter.getInstance().getChangeShop(map, new RequestCallback<PurchaseBean>() {
                            @Override
                            protected void onSuc(PurchaseBean body) {
                                currentShopTitle.setText(itemList.get(position).getTitle());
                                currentShopAddress.setText(itemList.get(position).getSnippet());
                                switch (itemList.get(position).getAuthStatus())
                                {
                                    case -1:
                                        currentExpired.setVisibility(View.VISIBLE);
                                        currentExpired.setText("审核中");
                                        break;
                                    case 0:
                                        currentExpired.setVisibility(View.VISIBLE);
                                        currentExpired.setText("审核中");
                                        break;
                                    case 1:
                                        currentExpired.setVisibility(View.GONE);
                                        break;
                                    case 2:
                                        currentExpired.setVisibility(View.VISIBLE);
                                        currentExpired.setText("审核失败");
                                        break;
                                    default:
                                        break;
                                }

                                PublicCache.site = body.getData().getSite();
                                PublicCache.site_name = body.getData().getSiteName();
                                PublicCache.site_login = PublicCache.site;
                                PublicCache.site_name_login = PublicCache.site_name;
                                PublicCache.userNameId = body.getData().getEntityId();

                                LoginPurchaseBean.deleteAll(LoginPurchaseBean.class);
                                LoginSupplierBean.deleteAll(LoginSupplierBean.class);
                                PublicCache.loginPurchase = body.getData();
                                PublicCache.login_mode = PURCHASER;
                                body.getData().save();
                                EventBus.getDefault().post(new Login_in());
                                initList();
                            }

                            @Override
                            public void onFailed(int errCode, String msg) {
                                loadingDimss();
                                Toast.makeText(StoresManagementActivity.this,msg,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                },R.color.red_dark).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });
        loading("请稍候");
        initList();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        } else {
            //Toast.makeText(this, "您已经申请了权限!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.right_onclick_line:
                Intent intent = new Intent(this, EmployeesManagementActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_current_shop:
                Intent intent1 = new Intent(this,ShopInformationActivity.class) ;
                startActivity(intent1);
                break;
            case R.id.btn_create_new_store:
                if (PublicCache.loginPurchase.getEmpRole()==0||PublicCache.loginPurchase.getEmpRole()==3){
                Intent intent2 = new Intent(this,CreateStoreActivity.class);
                startActivity(intent2);}
                else {
                    UIUtils.showToastSafesShort("您没有此操作权限！");
                }
                break;
                default:break;
        }
    }

    public void initList() {
        if (PublicCache.loginPurchase != null) {
            itemList.clear();
            RequestPresenter.getInstance().getHotelList(PublicCache.loginPurchase.getLoginUserId(), new RequestCallback<HotelList>(this) {
                @Override
                protected void onSuc(HotelList body) {
                    for (HotelList.DataBean.ListBean bean : body.getData().getList()) {
                        if (bean.getCustomerId() != PublicCache.loginPurchase.getEntityId()){
                            ShopAddressItem item = new ShopAddressItem();
                            item.setTitle(bean.getEnterpriseCode());
                            item.setSnippet(bean.getEnterpriseMsg());
                            item.setAuthStatus(bean.getAuthStatus());
                            item.setAccount(bean.getAccount());
                            itemList.add(item);
                        }else {
                            currentShopTitle.setText(bean.getEnterpriseCode());
                            currentShopAddress.setText(bean.getEnterpriseMsg());
                            switch (bean.getAuthStatus())
                            {
                                case -1:
                                    currentExpired.setVisibility(View.VISIBLE);
                                    currentExpired.setText("审核中");
                                    break;
                                case 0:
                                    currentExpired.setVisibility(View.VISIBLE);
                                    currentExpired.setText("审核中");
                                    break;
                                case 1:
                                    currentExpired.setVisibility(View.GONE);
                                    break;
                                case 2:
                                    currentExpired.setVisibility(View.VISIBLE);
                                    currentExpired.setText("审核失败");
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                    loadingDimss();
                }

                @Override
                public void onFailed(int errCode, String msg) {
                    UIUtils.showToastSafesShort(msg);
                    loadingDimss();
                }
            });
        }
    }
}
