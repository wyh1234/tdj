package cn.com.taodaji.ui.fragment;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.apkfuns.logutils.LogUtils;
import com.base.retrofit.RequestCallback;
import com.base.utils.ClickCheckedUtil;
import com.base.utils.DensityUtil;
import com.base.utils.JavaMethod;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.model.entity.AddCategory;
import cn.com.taodaji.model.entity.OrderDetail;
import cn.com.taodaji.model.entity.TodayDeliverGoods;
import cn.com.taodaji.model.entity.TodayDeliverGoodsOrderBean;
import cn.com.taodaji.model.entity.TodayDeliverScreenEvent;
import cn.com.taodaji.model.event.PrintComplete;
import cn.com.taodaji.model.event.PrintStatus;
import cn.com.taodaji.model.event.TodayOrderTimeEvent;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.viewModel.adapter.TodayDeliverGoodsOrderAdapter;
import tools.fragment.LoadMoreRecyclerViewFragment;
import tools.gprint.PrintUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodayDeliverGoodsOrderFragment extends LoadMoreRecyclerViewFragment {
    private int printStatus,storeId,rwId,stationId;
    private static int printerStatus;
    private PrintUtils printUtils;
    private RelativeLayout loading;
    private TodayDeliverGoodsOrderAdapter adapter;
    private TextView orderCount, orderAmount, printAll;
    private  String truckTime="",productNickName;
    private  int productId=-1,areaId=-1;
    private List<TodayDeliverGoods> goodsList = new ArrayList<>();
    private CheckBox selectAll;
    private Context mContext;
    private View view_bottom;
    private String expectDeliveredDate;
    private int isC=-1;



    public void setLoading(RelativeLayout loading) {
        this.loading = loading;
    }

    public void setPrintStatus(int printStatus) {
        this.printStatus = printStatus;
    }

    public void setRwId(int rwId) {
        this.rwId = rwId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public void setPrintUtils(PrintUtils printUtils) {
        this.printUtils = printUtils;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void initData() {
        super.initData();
        recycler_targetView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new TodayDeliverGoodsOrderAdapter(goodsList,mContext);
        recycler_targetView.setAdapter(adapter);
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (goodsList.get(position).isSelected()){
                    goodsList.get(position).setSelected(false);
                }else {
                    goodsList.get(position).setSelected(true);
                }
                totalOrderAndPrice();
            }
        });

        view_bottom = ViewUtils.getLayoutView(getContext(), R.layout.part_today_deliver_goods_order_view);

        orderCount =ViewUtils.findViewById(view_bottom,R.id.tv_total_order) ;
        orderAmount = ViewUtils.findViewById(view_bottom,R.id.tv_total_price) ;
        printAll = ViewUtils.findViewById(view_bottom, R.id.tv_print_all);
        selectAll = ViewUtils.findViewById(view_bottom,R.id.cb_select_all);
        selectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    selectAllStatus(true);
                }else {
                    selectAllStatus(false);
                }
            }
        });

        printAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ClickCheckedUtil.onClickChecked(1000)) {
                    List<OrderDetail.ItemsBean> list = new ArrayList<>();
                    for (TodayDeliverGoods goods : goodsList){
                        if (goods.isSelected()){
                            OrderDetail.ItemsBean temp=getTodayData(goods);
                            if (temp != null) {
                                list.add(temp);
                            }
                        }
                    }
                    if (printerStatus==1){
                        if (list.size()==0){
                            UIUtils.showToastSafe("未找到需要打印的商品");
                        }else {
                            loading.setVisibility(View.VISIBLE);
                            printUtils.printList(list);
                        }
                    }else{
                        UIUtils.showToastSafe("打印机未成功连接");
                    }
                }
            }
        });

        bottom_view.addView(view_bottom);
        swipeToLoadLayout.setPadding(0,0,0,DensityUtil.dp2px(42));
        swipeToLoadLayout.setRefreshing(true);
    }


    public void selectAllStatus(boolean flag){
        for (int i=0;i<goodsList.size();i++){
            goodsList.get(i).setSelected(flag);
        }
        adapter.notifyDataSetChanged();
        totalOrderAndPrice();
    }

    public void totalOrderAndPrice(){
        BigDecimal totalPrice = new BigDecimal(0);
        int totalOrder = 0;
        for (int i=0;i<goodsList.size();i++){
            if (goodsList.get(i).isSelected()){
                totalPrice = totalPrice.add(goodsList.get(i).getTotalPrice());
                totalOrder++;
            }
        }
        orderCount.setText("共"+totalOrder+"个订单，");
        orderAmount.setText("总合计："+totalPrice+"元");
    }

    public void getHttpData() {
        goodsList.clear();
        RequestPresenter.getInstance().today_deliver_goods_order(truckTime, storeId, stationId, rwId, areaId, productId, printStatus,productNickName,isC,
                ListUtils.isNullOrZeroLenght(expectDeliveredDate)?"1":expectDeliveredDate
                , new RequestCallback<TodayDeliverGoodsOrderBean>() {
            @Override
            protected void onSuc(TodayDeliverGoodsOrderBean body) {
                if (!body.getData().getExpectDeliveredDate().equals("")){
                    EventBus.getDefault().post(new TodayOrderTimeEvent(body.getData().getExpectDeliveredDate(),true));
                }
                if (body.getData().getOrderItems().size()!=0){
                    for (TodayDeliverGoodsOrderBean.DataBean.OrderItemsBean bean : body.getData().getOrderItems()){
                        TodayDeliverGoods item = new TodayDeliverGoods();
                        JavaMethod.copyValue(item,bean);
                        goodsList.add(item);
                    }
                }
                adapter.notifyDataSetChanged();
                stop();
                loading.setVisibility(View.GONE);
            }

            @Override
            public void onFailed(int errCode, String msg) {
                stop();
                loading.setVisibility(View.GONE);
                UIUtils.showToastSafe(msg);
            }
        });
    }


    public void updatePrintStatus(String itemIds){
        RequestPresenter.getInstance().updatePrintStatus(itemIds, new RequestCallback<AddCategory>() {
            @Override
            protected void onSuc(AddCategory body) {
                swipeToLoadLayout.setRefreshing(true);
            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafe(msg);
            }
        });
    }


    private OrderDetail.ItemsBean getTodayData(TodayDeliverGoods detailBean) {

        if (detailBean == null) {
            return null;
        }
        OrderDetail.ItemsBean itemsBean = new OrderDetail.ItemsBean();
        JavaMethod.copyValue(itemsBean, detailBean);

        itemsBean.setOrderId(detailBean.getItemId());
        itemsBean.setAmount(new BigDecimal(detailBean.getQty()));
        itemsBean.setName(detailBean.getProductName());
        itemsBean.setProductCriteria(Integer.parseInt(detailBean.getProductCriteria()));
        itemsBean.setStationShortName(detailBean.getStationShortName());
        itemsBean.setDriverNo(detailBean.getDriverNo());
        itemsBean.setReceiveWarehouseName(detailBean.getReceiveWarehouseName());
        itemsBean.setReceiveWarehouseShortName(detailBean.getReceiveWarehouseShortName());
        itemsBean.setIsC(detailBean.getIsC());
        itemsBean.setCustomerName(detailBean.getCustomerName());
        itemsBean.setCustomerMobile(detailBean.getCustomerMobile());
        OrderDetail.ReceiveAddrBean receiveAddrBean = new OrderDetail.ReceiveAddrBean();
        receiveAddrBean.setAddress(detailBean.getShippingAddress());
        receiveAddrBean.setHotelName(detailBean.getShippingHotel());
        receiveAddrBean.setName(detailBean.getShippingName());
        receiveAddrBean.setTelephone(detailBean.getShippingTel());
        itemsBean.setReceiveAddr(receiveAddrBean);
        LogUtils.e(itemsBean.getCustomerMobile());
        return itemsBean;
    }

    @Override
    public void getData(int pn) {
        if (selectAll!=null){
            selectAll.setChecked(false);
            orderCount.setText("共0个订单，");
            orderAmount.setText("总合计：0元");
        }
        getHttpData();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(TodayDeliverGoodsOrderFragment.this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter==null){
            adapter = new TodayDeliverGoodsOrderAdapter(goodsList,mContext);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(TodayDeliverGoodsOrderFragment.this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(TodayDeliverScreenEvent event) {
        areaId = event.getAreaId();
        truckTime = event.getTime();
        productId = event.getProductId();
        productNickName = event.getProductNickName();
        expectDeliveredDate=event.getPs_time();
        isC=event.getIsc();
        swipeToLoadLayout.setRefreshing(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PrintStatus event){
        printerStatus = event.getStatus();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PrintComplete event) {
        updatePrintStatus(event.getItemIds());
    }

}
