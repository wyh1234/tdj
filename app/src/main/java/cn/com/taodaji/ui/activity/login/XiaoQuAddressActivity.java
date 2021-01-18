package cn.com.taodaji.ui.activity.login;

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
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.LocationBean;
import cn.com.taodaji.model.entity.XiaoQuAddressItem;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.ui.activity.integral.tools.ShowLoadingDialog;
import cn.com.taodaji.viewModel.adapter.XiaoQuAddressAdapter;
import tools.activity.SimpleToolbarActivity;

public class XiaoQuAddressActivity extends SimpleToolbarActivity {
    private View mainView;
    private int id;
    private String name,cityCode;
    private TextView cityName,cancelSearch;
    private EditText addressSearch;
    private ImageView clearKeyword;
    private RecyclerView addressRecyclerView;
    private List<XiaoQuAddressItem.DataBean.DataListBean.ItemsBean> itemList = new ArrayList<>();
    private LocationBean locationBean;

    private XiaoQuAddressAdapter addressAdapter;

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
       simple_title.setText("搜索小区");
    }

    @Override
    protected void initData() {
        super.initData();
        registerEventBus(this);
    }

    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.xiaoqu_address_layout);
        body_other.addView(mainView);

        id = PublicCache.site;
        name = PublicCache.site_name;
        cityCode = PublicCache.site_name;


        cityName = ViewUtils.findViewById(mainView,R.id.tv_current_city);
        cityName.setText(name);

        cancelSearch = ViewUtils.findViewById(mainView,R.id.tv_cancel_search);
        addressSearch = ViewUtils.findViewById(mainView,R.id.et_shop_address);
        clearKeyword = ViewUtils.findViewById(mainView,R.id.iv_clear_keyword);
        addressRecyclerView = ViewUtils.findViewById(mainView, R.id.rv_shop_address_list);

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
        addressSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    cancelSearch.setVisibility(View.VISIBLE);
                }else {
                    cancelSearch.setVisibility(View.GONE);
                }
            }
        });

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
                if (editable.toString().trim().equals("")){
                    getData(getLocationBean(),"");
                }else {//
                    getData(getLocationBean(),editable.toString().trim());
//                    doSearchQuery(editable.toString().trim(),cityCode,"",null);
                }
                //小区搜索，默认根据经纬度查询

            }
        });
        //取消搜索
        cancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addressSearch.clearFocus();
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
                if (getIntent().getBooleanExtra("isRegister",false)){
                    UIUtils.showToastSafe("修改地址时无法修改当前城市");
                }else {
                    Intent intent1 = new Intent(XiaoQuAddressActivity.this, CityActivity.class);
                    intent1.putExtra("data", PublicCache.findByIsActive);
                    startActivityForResult(intent1, 3);
                }
            }
        });
    }

    //选择城市后回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK){
            id= data.getExtras().getInt("id");
            name=data.getExtras().getString("name");
            cityName.setText(name);
            cityCode = data.getStringExtra("cityCode");
            getData(getLocationBean(),addressSearch.getText().toString().trim());

        }
    }


    @Subscribe
    public void eventCode(LocationBean locationBean) {
        LogUtils.e(locationBean);//根据酒店的经纬度计算距离
        setLocationBean(locationBean);
        getData(locationBean,"");


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
                    addressAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onFailed(int errCode, String msg) {
                ShowLoadingDialog.close();
            }
        });
    }

    public void commitAddress(int pos){
        //数据是使用Intent返回
        Intent intent = new Intent();
        //把返回数据存入Intent
        intent.putExtra("result", itemList.get(pos).getAddress());
        intent.putExtra("longitude",getLocationBean().getLongitude());
        intent.putExtra("latitude",getLocationBean().getLatitude());
        intent.putExtra("id",id);
        intent.putExtra("name",name);
        intent.putExtra("communityId",itemList.get(pos).getId());
        intent.putExtra("communityName",itemList.get(pos).getCommunityName());
        intent.putExtra("communityAbbreviation",itemList.get(pos).getCommunityShortName());

        //设置返回数据
        this.setResult(RESULT_OK, intent);
        //关闭Activity
        this.finish();
    }
}
