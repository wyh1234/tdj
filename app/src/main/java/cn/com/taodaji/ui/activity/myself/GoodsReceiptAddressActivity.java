package cn.com.taodaji.ui.activity.myself;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;
import com.base.swipetoloadlayout.listener.OnGetDataListener;
import com.base.utils.SerializableMap;
import com.base.utils.SystemUtils;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.LoadMoreUtil;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.AddressDelete;
import cn.com.taodaji.model.entity.GoodsReceiptAddress;
import cn.com.taodaji.model.entity.GoodsReceiptAddressBean;
import cn.com.taodaji.model.event.UpdateAddressEvent;
import cn.com.taodaji.viewModel.vm.ReceiptAddressVM;
import tools.activity.SimpleToolbarActivity;


public class GoodsReceiptAddressActivity extends SimpleToolbarActivity implements View.OnClickListener, OnGetDataListener {
    public LoadMoreUtil loadMoreUtils;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor(R.color.white);
        toolbar.setNavigationIcon(R.mipmap.left_arrow_gary);//设置导航栏图标
        simple_title.setText("收货地址");
        simple_title.setTextColor(UIUtils.getColor(R.color.gray_66));
        title_split_line.setVisibility(View.VISIBLE);
/*
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 1) {
                    loadingDimss();
                    //提交订单OrderPlaceActivity 中默认地址更新
                    EventBus.getDefault().post(new UpdateAddressEvent());
                    finish();
                } else finish();
            }
        });*/
    }

    private View mainView;
    private SingleRecyclerViewAdapter myRecyclerViewAdapter;
    private int update_position = -1;
    private int type;
    private int clickPos;
    private boolean flg;


    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.activity_myself_goods_receipt_address);
        body_other.addView(mainView);
        body_other.setBackgroundColor(UIUtils.getColor(R.color.gray_f2));
        final LinearLayout address_detail = ViewUtils.findViewById(mainView, R.id.address_detail);
        RecyclerView recyclerView = getLayoutView(R.layout.t_recyclerview);
        address_detail.addView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //GoodsReceiptAddressBean
        myRecyclerViewAdapter = new SingleRecyclerViewAdapter() {
            @Override
            public void initBaseVM() {
                putBaseVM(0, new ReceiptAddressVM());
            }

            @Override
            public View onCreateHolderView(ViewGroup parent, int viewType) {
                return ViewUtils.getFragmentView(parent, R.layout.item_myself_goods_receipt_address);
            }

            @Override
            public void onBindViewHolderBind(BaseViewHolder holder, int position) {
                super.onBindViewHolderBind(holder, position);
                if (type == 1) {
                    holder.setText(R.id.address_default, "设为收货地址");
                }
            }

            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object itemBean) {
                if (onclickType == 0) {
                    GoodsReceiptAddressBean bean = (GoodsReceiptAddressBean) itemBean;
                    clickPos = position;
                    switch (view.getId()) {
                        case R.id.address_detail:
                            if (type != 1) return true;
                            EventBus.getDefault().post(new UpdateAddressEvent(bean));
                            finish();
                            return true;
/*                        case R.id.address_default:
                            if (bean.getIsDefault() == 1) return true;
                            final Map<String, Object> map = new HashMap<>();
                            map.put("isDefault", 1);
                            map.put("addressId", bean.getAddressId());
                            loading();
                            getRequestPresenter().updateAddress(map, new RequestCallback<AddressUpdate>() {
                                @Override
                                public void onFailed(AddressUpdate addressUpdate, String msg) {
                                    loadingDimss();
                                }

                                @Override
                                public void onSuc(AddressUpdate body) {
                                    loadingDimss();
                                    initData();
                                    if (type == 1) {
                                        //提交订单OrderPlaceActivity 中默认地址更新
                                        EventBus.getDefault().post(new UpdateAddressEvent(bean));
                                        finish();
                                    }

                                }
                            });
                            return true;*/

                        case R.id.address_update:
                            update_position = position;
                            Intent intent = new Intent(getBaseActivity(), GoodsReceiptAddressAddActivity.class);
                            intent.putExtra("data", bean);
                            intent.putExtra("flg1", flg);
                            startActivityForResult(intent, 200);
                            return true;
                        case R.id.address_delete:
                            if (!flg) {
                                loading();
                                getRequestPresenter().deleteAddress(bean.getAddressId(), new RequestCallback<AddressDelete>() {
                                    @Override
                                    public void onFailed(int addressDelete, String msg) {
                                        loadingDimss();
                                    }

                                    @Override
                                    public void onSuc(AddressDelete body) {
                                        loadingDimss();
                                        myRecyclerViewAdapter.remove(clickPos);
                                    }
                                });
                            }
                            return true;
                    }
                }

                return super.onItemClick(view, onclickType, position, itemBean);
            }
        };
        recyclerView.setAdapter(myRecyclerViewAdapter);
        loadMoreUtils = new LoadMoreUtil(this, recyclerView);

    }

    @Override
    protected void initData() {
        type = getIntent().getIntExtra("type", 0);
        flg = getIntent().getBooleanExtra("flg", false);
        getData(1);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.address_add:
                Intent intent = new Intent(this, GoodsReceiptAddressAddActivity.class);
                startActivityForResult(intent, 100);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                //添加
                initData();
            } else if (requestCode == 200) {
                Bundle bundle = data.getExtras();
                SerializableMap sermap = (SerializableMap) bundle.get("data");
                Map<String, Object> map = null;
                if (sermap != null) {
                    map = sermap.getMap();
                }
                //修改
                myRecyclerViewAdapter.update(update_position, map);
            }
        }
    }


    @Override
    public void getData(int pn) {
        if (pn == 1) loading();
        getRequestPresenter().getAddressList(pn, 10, PublicCache.loginPurchase.getEntityId(),PublicCache.loginPurchase.getLoginUserId(),new ResultInfoCallback<GoodsReceiptAddress>() {
            @Override
            public void onSuccess(GoodsReceiptAddress body) {
                loadingDimss();
                if (body.getTotal() == 0) return;
                loadMoreUtils.setHasLoadMore(true);
                loadMoreUtils.setData(body.getItems(), body.getPn(), body.getPs());
            }

            @Override
            public void onFailed(int goodsReceiptAddressResultInfo, String msg) {
                loadingDimss();
            }
        });
    }
}
