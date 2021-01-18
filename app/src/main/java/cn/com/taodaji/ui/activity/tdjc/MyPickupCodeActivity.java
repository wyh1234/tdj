package cn.com.taodaji.ui.activity.tdjc;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.base.retrofit.RequestCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.DeliveryCode;
import cn.com.taodaji.model.entity.MyPickupCode;
import cn.com.taodaji.model.entity.XiaoQuAddressItem;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.ui.activity.integral.tools.ShowLoadingDialog;
import cn.com.taodaji.viewModel.adapter.XiaoQuAddressAdapter;
import tools.activity.SimpleToolbarActivity;

public class MyPickupCodeActivity extends SimpleToolbarActivity  {
    View mainView;
    RecyclerView pick_up_list;
    RelativeLayout rl_null;
    MyPickupCodeAdapter myPickupCodeAdapter;
    private List<DeliveryCode.DataBean.ListBean> itemList = new ArrayList<>();
    MyPickupCodePoupWindow myPickupCodePoupWindow;
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("我的提货码");
    }

    @Override
    protected void initData() {
        super.initData();
        getDeliveryCode();

    }

    public void  getDeliveryCode(){
        ShowLoadingDialog.showLoadingDialog(this);
        Map<String, Object> params=new HashMap<>();
        params.put("customerId", PublicCache.loginPurchase.getEntityId());
        getRequestPresenter().findCustomerCommunityDeliveryCode(params, new RequestCallback<DeliveryCode>() {
            @Override
            protected void onSuc(DeliveryCode body) {
                ShowLoadingDialog.close();
                if (ListUtils.isEmpty(body.getData().getList())){
                    rl_null.setVisibility(View.VISIBLE);
                }else {
                    rl_null.setVisibility(View.GONE);
                    itemList.addAll(body.getData().getList());
                    myPickupCodeAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailed(int errCode, String msg) {
                ShowLoadingDialog.close();
                rl_null.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.my_pick_up_code_activity);
        body_other.addView(mainView);
        pick_up_list=mainView.findViewById(R.id.pick_up_list);
        rl_null=mainView.findViewById(R.id.rl_null);

        pick_up_list.setLayoutManager(new LinearLayoutManager(this));
        pick_up_list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        myPickupCodeAdapter = new MyPickupCodeAdapter(this, itemList);
        pick_up_list.setAdapter(myPickupCodeAdapter);

        myPickupCodeAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (v.getId()==R.id.rl_code){
                    if (myPickupCodePoupWindow!=null){
                        if (myPickupCodePoupWindow.isShowing()){
                            return;
                        }

                    }
                    myPickupCodePoupWindow = new MyPickupCodePoupWindow(MyPickupCodeActivity.this,itemList.get(position));
                    myPickupCodePoupWindow.setPopupWindowFullScreen(true);//铺满
                    myPickupCodePoupWindow.setDismissWhenTouchOutside(false);
                    myPickupCodePoupWindow.setInterceptTouchEvent(false);
                    myPickupCodePoupWindow.setBackPressEnable(false);//返回键禁止取消
                    myPickupCodePoupWindow.showPopupWindow();
                }else {
                    Intent intent=new Intent(MyPickupCodeActivity.this,MyPickupAddressActivity.class);//传提货点经纬度，地址
                    intent.putExtra("lat",itemList.get(position).getLat()+"");
                    intent.putExtra("lon",itemList.get(position).getLon()+"");
                    intent.putExtra("communityShortName",itemList.get(position).getCommunityName());
                    intent.putExtra("address",itemList.get(position).getAddress());
                    startActivity(intent);
                }


            }
        });
    }
}
