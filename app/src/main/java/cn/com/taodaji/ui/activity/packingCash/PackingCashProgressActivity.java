package cn.com.taodaji.ui.activity.packingCash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.entity.ResultInfo;
import com.base.retrofit.RequestCallback;
import com.base.utils.DensityUtil;
import com.base.utils.SystemUtils;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.splite_line.DividerGridItemDecoration;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.DeleteSalesAppByEntityId;
import cn.com.taodaji.model.entity.OrderDetail;
import cn.com.taodaji.model.entity.PackingCashProgressBean;
import cn.com.taodaji.model.entity.PackingReturnMaxBean;
import cn.com.taodaji.model.entity.RefundDetail;
import cn.com.taodaji.model.event.AfterSalesCancleEvent;
import cn.com.taodaji.model.event.PackingCashCancleEvent;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.model.sqlBean.CartGoodsBean;
import cn.com.taodaji.ui.activity.orderPlace.AfterSalesDetailActivity;
import cn.com.taodaji.ui.activity.orderPlace.LeftDecoration;
import cn.com.taodaji.viewModel.adapter.SimpleAfterSalesDetailAdapter;
import cn.com.taodaji.viewModel.adapter.SimpleAfterSalesImageAdapter;
import cn.com.taodaji.viewModel.adapter.SimplePackingCashProgressAdapter;
import cn.com.taodaji.viewModel.vm.OrderPlaceGoodsDetailVM;
import cn.com.taodaji.viewModel.vm.OrderPlaceGoodsVM;
import cn.com.taodaji.viewModel.vm.PackingCashCarGoodsBeanVM;
import tools.activity.SimpleToolbarActivity;
import tools.extend.PhoneUtils;

public class PackingCashProgressActivity extends SimpleToolbarActivity {
    private SimplePackingCashProgressAdapter simplePackingCashProgressAdapter;
    private SimpleAfterSalesImageAdapter afterSalesImageAdapter;

    RecyclerView recyclerView;
    RecyclerView imageRecyler;

    private CartGoodsBean cartGoodsBean;


    private TextView refund_cancel,refund_time,refund_no,tv_supply_name;//

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("退押金进度查询");
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }

    @Override
    protected void initMainView() {

        right_icon.setVisibility(View.VISIBLE);
        right_icon.setImageResource(R.mipmap.ic_customer_service_white);
        right_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUtils.callPhone(PackingCashProgressActivity.this, PhoneUtils.getPhoneService());
            }
        });
        View mainView = getLayoutView(R.layout.activity_packing_cash_progress);
        body_scroll.addView(mainView);

        refund_cancel = ViewUtils.findViewById(mainView, R.id.refund_cancel);
        refund_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PublicCache.loginPurchase==null)return ;
                if (PublicCache.loginPurchase.getEmpRole()==2||PublicCache.loginPurchase.getEmpRole()==5) {
                    UIUtils.showToastSafesShort(PublicCache.getRoleType().getValueOfKey(PublicCache.loginPurchase.getEmpRole())+"没有该操作权限");
                    return ;
                }
                cancelPackingCash();
            }
        });

        recyclerView = ViewUtils.findViewById(mainView, R.id.recyclerView);
        imageRecyler = ViewUtils.findViewById(mainView, R.id.image_recyler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(1, R.color.gray_db));
        recyclerView.addItemDecoration(new LeftDecoration());
        simplePackingCashProgressAdapter = new SimplePackingCashProgressAdapter();
        simplePackingCashProgressAdapter.setSelectFieldName("check");
        recyclerView.setAdapter(simplePackingCashProgressAdapter);

        imageRecyler.setLayoutManager(new GridLayoutManager(this, 3));
        imageRecyler.addItemDecoration(new DividerGridItemDecoration(UIUtils.dip2px(20), R.color.white));
        //imageRecyler.setBackgroundColor(getResources().getColor(R.color.white));
        afterSalesImageAdapter = new SimpleAfterSalesImageAdapter(){

            @Override
            public View onCreateHolderView(ViewGroup parent, int viewType) {
                View view = ViewUtils.getFragmentView(parent, R.layout.t_imageview);
                int w = parent.getMeasuredWidth();
                int h = (w - DensityUtil.dp2px(60)) / 3;
                UIUtils.setViewSize(view, h, h);
                return view;
            }

        };
        imageRecyler.setAdapter(afterSalesImageAdapter);

        tv_supply_name=mainView.findViewById(R.id.tv_supply_name);
        refund_time=mainView.findViewById(R.id.refund_time);
        refund_no=mainView.findViewById(R.id.refund_no);

        View goods_information = ViewUtils.findViewById(mainView, R.id.pack_cash_create_layout);

        if (cartGoodsBean != null) {
            refund_no.setText(cartGoodsBean.getQrCodeId());
            refund_time.setText(cartGoodsBean.getPayTime());
            //列表
            BaseViewHolder holder = new BaseViewHolder(goods_information, new PackingCashCarGoodsBeanVM(), null);
            holder.setValues(cartGoodsBean);
            if (PublicCache.loginPurchase != null){
                tv_supply_name.setText("供应商："+cartGoodsBean.getStoreName());
            }else{
                tv_supply_name.setText("采购商："+cartGoodsBean.getCustomerName());
            }
            afterSalesImageAdapter.setData(cartGoodsBean.getPackageImg());

        }


    }

    @Override
    protected void initData() {
        getPackingProgressList();
    }
    private void getPackingProgressList(){
        loading();
        Map<String, Object> map = new HashMap<>();
        map.put("site", PublicCache.site_login);
        if (cartGoodsBean != null) {
            map.put("afterSaleId", cartGoodsBean.getAfterSaleId());
        }

        addRequest(ModelRequest.getInstance().getPackingCashProgress(map), new RequestCallback<PackingCashProgressBean>() {
            @Override
            protected void onSuc(PackingCashProgressBean body) {
                loadingDimss();
                if (body.getData()!= null) {
                    //退押状态  为1时可撤销
                    if (body.getData().getStatus() == 1) {
                        if (PublicCache.loginPurchase != null){
                            refund_cancel.setVisibility(View.VISIBLE);
                        }else {
                            refund_cancel.setVisibility(View.GONE);
                        }
                    }else {
                        refund_cancel.setVisibility(View.GONE);
                    }
                    if (body.getData().getList()!= null) {
                        simplePackingCashProgressAdapter.setList(body.getData().getList());
                        simplePackingCashProgressAdapter.setSelected(0);
                    }

                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
                loadingDimss();
                UIUtils.showToastSafe(msg);
            }

        });
    }

    private void cancelPackingCash() {
        if (cartGoodsBean != null) {
            loading();

            Map<String, Object> map = new HashMap<>();
            map.put("site", PublicCache.site_login);
            if (cartGoodsBean != null) {
                map.put("afterSaleId", cartGoodsBean.getAfterSaleId());
                map.put("customerName", cartGoodsBean.getCustomerName());
            }

            addRequest(ModelRequest.getInstance().cancelPackingCash(map), new RequestCallback<ResultInfo>() {
                @Override
                protected void onSuc(ResultInfo body) {
                    loadingDimss();
                    if (body.getErr() == 0) {
                        UIUtils.showToastSafesShort("撤销成功");
                        EventBus.getDefault().post(new PackingCashCancleEvent(cartGoodsBean.getAfterSaleId()));
                        finish();
                    }else{
                        UIUtils.showToastSafesShort(body.getMsg());
                    }


                }

                @Override
                public void onFailed(int errCode, String msg) {
                    loadingDimss();
                    UIUtils.showToastSafe(msg);
                }

            });
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    //列表入口
    @Subscribe(sticky = true)
    public void onEvent(CartGoodsBean event) {
        EventBus.getDefault().removeStickyEvent(event);
        cartGoodsBean = event;
    }


    public static void startActivity(Context context, CartGoodsBean unit) {
        if (unit == null) return;
        EventBus.getDefault().postSticky(unit);
        context.startActivity(new Intent(context, PackingCashProgressActivity.class));
    }

    public static void startActivity(Context context, OrderDetail.ItemsBean unit) {
        if (unit == null) return;
        EventBus.getDefault().postSticky(unit);
        context.startActivity(new Intent(context, PackingCashProgressActivity.class));
    }
}
