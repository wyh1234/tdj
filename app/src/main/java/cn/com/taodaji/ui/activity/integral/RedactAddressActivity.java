package cn.com.taodaji.ui.activity.integral;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.AddressInfo;
import cn.com.taodaji.model.entity.BaseIntegral;
import cn.com.taodaji.model.entity.IntegralShopCart;
import cn.com.taodaji.model.entity.RedactAddress;
import cn.com.taodaji.ui.activity.integral.adapter.IntegralShopCartAdapter;
import cn.com.taodaji.ui.activity.integral.adapter.RedactAddressAdapter;
import cn.com.taodaji.ui.activity.integral.tools.ActivityManager;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.ui.activity.integral.tools.ShowLoadingDialog;
import tools.activity.DataBaseActivity;
import tools.animation.ScrollLinearLayoutManager;
import tools.statusbar.Eyes;

public class RedactAddressActivity extends DataBaseActivity implements RedactAddressAdapter.OnItemClickListener {
    @BindView(R.id.address_list)
    RecyclerView address_list;
    @BindView(R.id.intergral_toolbar)
    Toolbar intergral_toolbar;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.tv_right)
    TextView tv_right;
    private List<RedactAddress.DataBean> list=new ArrayList<>();
    private RedactAddressAdapter redactAddressAdapter;
    private int position=-1;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @OnClick({R.id.tv_right,R.id.btn_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_right:
                Intent intent=new Intent(this,AddAddressActivity.class);
                startActivity(intent);

                break;
            case R.id.btn_back:
                LogUtils.i(list);
                if (ListUtils.isEmpty(list)){
                    EventBus.getDefault().post(new AddressInfo());
                }else if (list.size()==1){
                    AddressInfo addressInfo= new AddressInfo();
                    AddressInfo.DataBean dataBean= new AddressInfo.DataBean();
                    dataBean.setRecevingMobile(list.get(0).getRecevingMobile());
                    dataBean.setRecevingPersion(list.get(0).getRecevingPersion());
                    dataBean.setIsDefault(list.get(0).getIsDefault());
                    dataBean.setDetailAddress(list.get(0).getDetailAddress());
                    addressInfo.setData(dataBean);
                    EventBus.getDefault().post(addressInfo);
                }
                finish();

                break;
        }
    }
    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.redact_address_layout);
    }
    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        ActivityManager.getActivityManager().addActivity(this);
        Eyes.setStatusBarColor(this, UIUtils.getColor(this, R.color.white));
        intergral_toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        tv_title.setText("收货地址");
        tv_title.setTextColor(getResources().getColor(R.color.gray_66));
        btn_back.setVisibility(View.VISIBLE);
        btn_back.setImageResource(R.mipmap.left_arrow_gary);
        tv_right.setVisibility(View.GONE);
        tv_right.setTextColor(getResources().getColor(R.color.gray_66));

        LinearLayoutManager layout = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        address_list.setLayoutManager(layout);
        redactAddressAdapter=new RedactAddressAdapter(this,list);
        redactAddressAdapter.setOnItemClickListener(this);
//        redactAddressAdapter.setOnItemClickListener(this);
        address_list.setAdapter(redactAddressAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        getAddress();

    }

    public void getAddress(){
        ShowLoadingDialog.showLoadingDialog(this);
        Map<String,Object> map=new HashMap<>();
        map.put("userId",  PublicCache.loginPurchase.getEmpRole()==0?PublicCache.loginPurchase.getEntityId():PublicCache.loginPurchase.getLoginUserId());
        getIntegralRequestPresenter().getAddress(map, new RequestCallback<RedactAddress>(this) {
            @Override
            protected void onSuc(RedactAddress body) {
                ShowLoadingDialog.close();
                list.addAll(body.getData());
                redactAddressAdapter.notifyDataSetChanged();

            }
            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
                ShowLoadingDialog.close();
                UIUtils.showToastSafe(msg);

            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        setPosition(position);
        Intent intent=new Intent(this,AddAddressActivity.class);
        intent.putExtra("bj","bj");
        intent.putExtra("DataBean",list.get(position));
        startActivity(intent);
    }

    @Override
    public void onItemSelClick(View view, int position) {
        for (int i=0;i<list.size();i++){
            list.get(i).setaBoolean(false);
        }
        list.get(position).setaBoolean(true);
        redactAddressAdapter.notifyItemChanged(position);
        AddressInfo addressInfo= new AddressInfo();
        AddressInfo.DataBean dataBean= new AddressInfo.DataBean();
        dataBean.setRecevingMobile(list.get(position).getRecevingMobile());
        dataBean.setRecevingPersion(list.get(position).getRecevingPersion());
        dataBean.setIsDefault(list.get(position).getIsDefault());
        dataBean.setDetailAddress(list.get(position).getDetailAddress());
        addressInfo.setData(dataBean);
        EventBus.getDefault().post(addressInfo);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void delete_address(BaseIntegral event) {
        if (getPosition()!=-1)
        list.remove(getPosition());
        redactAddressAdapter.notifyDataSetChanged();

        setPosition(-1);


    }
}
