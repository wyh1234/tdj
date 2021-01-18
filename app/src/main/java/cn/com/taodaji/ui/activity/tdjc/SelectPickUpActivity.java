package cn.com.taodaji.ui.activity.tdjc;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.lljjcoder.style.citylist.Toast.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.LoginMethod;
import cn.com.taodaji.model.entity.LocationBean;
import cn.com.taodaji.model.entity.UpdateCommunityRef;
import cn.com.taodaji.model.entity.XiaoQuAddressItem;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.ui.activity.integral.tools.ShowLoadingDialog;
import cn.com.taodaji.ui.activity.login.CityActivity;
import cn.com.taodaji.viewModel.adapter.XiaoQuAddressAdapter;
import tools.activity.SimpleToolbarActivity;

public class SelectPickUpActivity extends SimpleToolbarActivity implements SelectPickUpPoupWindow.SelectPickUpPoupWindowListener{
    View mainView;
    private int id;
    private String name;
    private TextView tv_search,cityName,tv_type,tv_shop_address,tv_shop_title,tv_jl,tv_current_address;
    private EditText addressSearch;
    private ImageView clearKeyword;
    private RecyclerView addressRecyclerView;
    private List<XiaoQuAddressItem.DataBean.DataListBean.ItemsBean> itemList = new ArrayList<>();
    private LocationBean locationBean;
    private XiaoQuAddressAdapter addressAdapter;
    private String currentCommunityName;
    private int mpos;
    private String communityId;


    private SelectPickUpPoupWindow selectPickUpPoupWindow;

    public LocationBean getLocationBean() {
        return locationBean;
    }

    public void setLocationBean(LocationBean locationBean) {
        this.locationBean = locationBean;
    }

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("选择提货点");
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
    @Subscribe
    public void eventCode(LocationBean locationBean) {
        LogUtils.e(locationBean);
        LogUtils.e(locationBean.getaMapLocation().getAddress());
        setLocationBean(locationBean);
        getData(locationBean,"");

        tv_type.setText("当前提货点");
        tv_shop_address.setVisibility(View.VISIBLE);
        tv_shop_title.setVisibility(View.VISIBLE);
        tv_jl.setVisibility(View.VISIBLE);
        tv_current_address.setVisibility(View.GONE);
        tv_shop_address.setText(getIntent().getStringExtra("currentCommunityaddress"));
        tv_shop_title.setText(getIntent().getStringExtra("currentCommunityName"));
            tv_jl.setText("距离"+new BigDecimal(getIntent().getStringExtra("distance")).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()+"公里");

//        if (PublicCache.loginPurchase!=null&&PublicCache.loginPurchase.getIsC()==1){
//            tv_type.setText("当前提货点");
//            tv_shop_address.setVisibility(View.VISIBLE);
//            tv_shop_title.setVisibility(View.VISIBLE);
//            tv_jl.setVisibility(View.GONE);
//            tv_current_address.setVisibility(View.GONE);
//            tv_shop_address.setText(getIntent().getStringExtra("currentCommunityaddress"));
//            tv_shop_title.setText(getIntent().getStringExtra("currentCommunityName"));
//        }else {
//            getData(locationBean,"");
//            tv_type.setText("当前位置");
//            tv_shop_address.setVisibility(View.GONE);
//            tv_shop_title.setVisibility(View.GONE);
//            tv_jl.setVisibility(View.GONE);
//            tv_current_address.setVisibility(View.VISIBLE);
//            tv_current_address.setText(locationBean.getAddress());
//        }

    }
    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.select_pick_up_activity);
        body_other.addView(mainView);
        id = PublicCache.site;
        name = PublicCache.site_name;


        cityName = ViewUtils.findViewById(mainView,R.id.tv_current_city);
        cityName.setText(name);

        tv_search = ViewUtils.findViewById(mainView,R.id.tv_search);
        addressSearch = ViewUtils.findViewById(mainView,R.id.et_shop_address);
        clearKeyword = ViewUtils.findViewById(mainView,R.id.iv_clear_keyword);
        addressRecyclerView = ViewUtils.findViewById(mainView, R.id.rv_shop_address_list);
        tv_type = ViewUtils.findViewById(mainView, R.id.tv_type);
        tv_shop_address = ViewUtils.findViewById(mainView, R.id.tv_shop_address);
        tv_shop_title = ViewUtils.findViewById(mainView, R.id.tv_shop_title);
        tv_jl = ViewUtils.findViewById(mainView, R.id.tv_jl);
        tv_current_address = ViewUtils.findViewById(mainView, R.id.tv_current_address);


        addressRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        addressRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        addressAdapter = new XiaoQuAddressAdapter(itemList, this);
        addressRecyclerView.setAdapter(addressAdapter);

        addressRecyclerView.setItemViewCacheSize(10);
        addressAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                commitAddress(position);
            }
        });
        // 设置搜索文本监听
        addressSearch.clearFocus();

        addressSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().equals("")){
                    clearKeyword.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //搜索
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ListUtils.isNullOrZeroLenght(addressSearch.getText().toString().trim())){
                    ToastUtils.showShortToast(SelectPickUpActivity.this,"请输入关键字");

                }else {
                    Intent intent=new Intent(SelectPickUpActivity.this,SeachPickUpActivity.class);
                    intent.putExtra("keyWord",addressSearch.getText().toString().trim());
                    intent.putExtra("id",id);
                    intent.putExtra("name",name);
                    startActivityForResult(intent, 4);

                }
            }
        });

        //清空搜索内容
        clearKeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addressSearch.setText("");
                clearKeyword.setVisibility(View.INVISIBLE);
            }
        });

        //切换城市事件
        cityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PublicCache.loginPurchase==null){
                    Intent intent1 = new Intent(SelectPickUpActivity.this, CityActivity.class);
                    intent1.putExtra("data", PublicCache.findByIsActive);
                    startActivityForResult(intent1, 3);
                }
            }
        });

        currentCommunityName=getIntent().getStringExtra("currentCommunityName");
    }


    public void commitAddress(int pos) {
        mpos=pos;
        PublicCache.site = id;
        PublicCache.site_name = name;
        if (PublicCache.loginPurchase == null) {
//            LoginMethod.getInstance(getBaseActivity()).toLoginActivity();
//            UIUtils.showToastSafesShort("请登录后在选择");

            EventBus.getDefault().post(itemList.get(pos));
            finish();
             }else {

            if (selectPickUpPoupWindow != null) {
                if (selectPickUpPoupWindow.isShowing()) {
                    return;
                }

            }
            selectPickUpPoupWindow = new SelectPickUpPoupWindow(  currentCommunityName,itemList.get(pos).getCommunityName(), SelectPickUpActivity.this);
            selectPickUpPoupWindow.setSelectPickUpPoupWindowListener(this);
            selectPickUpPoupWindow.setPopupWindowFullScreen(true);//铺满
            selectPickUpPoupWindow.setDismissWhenTouchOutside(false);
            selectPickUpPoupWindow.setInterceptTouchEvent(false);
            selectPickUpPoupWindow.setBackPressEnable(false);
            selectPickUpPoupWindow.showPopupWindow();
            communityId=itemList.get(pos).getId()+"";
        }
    }

    //选择城市后回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK){
            if (requestCode==3){
                id= data.getExtras().getInt("id");
                name=data.getExtras().getString("name");
                cityName.setText(name);

                LogUtils.i(id);
                getData(getLocationBean(),addressSearch.getText().toString().trim());
            }else {
                LocationBean locationBean=new LocationBean();
                locationBean.setLongitude(data.getExtras().getDouble("longitude"));
                locationBean.setLatitude(data.getExtras().getDouble("latitude"));
                addressSearch.setText(data.getExtras().getString("name"));
                tv_type.setText("当前选择位置");

                tv_shop_address.setVisibility(View.VISIBLE);
                tv_shop_title.setVisibility(View.VISIBLE);
                tv_jl.setVisibility(View.VISIBLE);
                tv_current_address.setVisibility(View.GONE);

                tv_shop_address.setText(data.getExtras().getString("result"));
                tv_shop_title.setText(data.getExtras().getString("title"));
                tv_jl.setText("距离"+data.getExtras().getString("distance")+"公里");
                getData(locationBean,addressSearch.getText().toString().trim());
            }




        }
    }
    public void getData(LocationBean locationBean,String communityName){
        if (!ListUtils.isEmpty(itemList)){
            itemList.clear();
            addressAdapter.notifyDataSetChanged();
        }
        ShowLoadingDialog.showLoadingDialog(this);
        Map<String,Object> map=new HashMap<>();
        map.put("page",0);
        map.put("ps",20);
        map.put("pn",1);
        map.put("lon",locationBean.getLongitude());
        map.put("lat",locationBean.getLatitude());
        map.put("communityName",communityName);

        LogUtils.e(map);
        getRequestPresenter().customerCommunity_find(id,map, new RequestCallback<XiaoQuAddressItem>() {
            @Override
            protected void onSuc(XiaoQuAddressItem body) {
                ShowLoadingDialog.close();
                if (!ListUtils.isEmpty(body.getData().getDataList().getItems())){
                    itemList.addAll(body.getData().getDataList().getItems());
                    addressAdapter.setIndex(1);
                    addressAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onFailed(int errCode, String msg) {
                ShowLoadingDialog.close();
            }
        });
    }

    @Override
    public void ok() {

            Map<String,Object> map=new HashMap<>();
            map.put("customerId",PublicCache.loginPurchase.getEntityId());
            map.put("communityId",communityId);
            LogUtils.e(map);
            getRequestPresenter().updateCommunityRef(map, new RequestCallback<UpdateCommunityRef>() {
                @Override
                protected void onSuc(UpdateCommunityRef body) {

                    LogUtils.e(body);
                    ShowLoadingDialog.close();
                        EventBus.getDefault().post(itemList.get(mpos));
                        selectPickUpPoupWindow.dismiss();
                        finish();



                }

                @Override
                public void onFailed(int errCode, String msg) {
                    if (errCode==1){
                        ToastUtils.showShortToast(SelectPickUpActivity.this,msg);
                        selectPickUpPoupWindow.dismiss();
                    }
                    ShowLoadingDialog.close();
                }
            });
        }


    @Override
    public void cancle() {
        selectPickUpPoupWindow.dismiss();

    }
}
