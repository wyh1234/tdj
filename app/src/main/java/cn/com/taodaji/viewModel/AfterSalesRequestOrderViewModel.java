package cn.com.taodaji.viewModel;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.base.activity.BaseActivity;
import com.base.utils.DateUtils;
import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.OrderDetail;
import cn.com.taodaji.model.entity.ScanQRCode;
import cn.com.taodaji.model.event.AfterSalesCancleEvent;
import cn.com.taodaji.model.event.AfterSalesCreateEvent;
import cn.com.taodaji.model.sqlBean.CartGoodsBean;
import cn.com.taodaji.ui.activity.orderPlace.AfterSalesCreateActivity;
import cn.com.taodaji.ui.activity.orderPlace.AfterSalesDetailActivity;
import cn.com.taodaji.viewModel.adapter.AfterSalesRequestOrderAdapter;

/**
 * Created by yangkuo on 2018/10/25.
 */
public class AfterSalesRequestOrderViewModel extends BaseViewModel {

    public AfterSalesRequestOrderViewModel(BaseActivity activity) {
        super(activity);
    }

    @Override
    public BaseVM getVM() {
        return new BaseVM() {
            @Override
            protected void dataBinding() {
                putRelation("statusLabel", R.id.tv_mess);
                putRelation("stationName", R.id.tv_station);
                putRelation("extOrderId", R.id.tv_orderNo);
                putRelation("itemCount", R.id.tv_goods_count);
            }
        };
    }

    private AfterSalesRequestOrderAdapter afterSalesRequestOrderAdapter;
    private ScanQRCode.DataBean bean;

    @Override
    public void initData() {
        super.initData();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        bean = (ScanQRCode.DataBean) getIntent().getSerializableExtra("data");
        if (bean != null) {
            long now_12 = DateUtils.dateStringToLong(DateUtils.dateLongToString(0, "yyyy-MM-dd") + " 12:00:00");
            long timer_12 = now_12 - System.currentTimeMillis();//距当天中午12点的时间还剩下多少秒

            if (timer_12 > 0)
                getRootView().postDelayed(() -> afterSalesRequestOrderAdapter.notifyDataSetChanged(), timer_12 + 1000);

            setData(bean);

            RecyclerView recyclerView = getRootView().findViewById(R.id.rcy_goods_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(getRootView().getContext()));
            afterSalesRequestOrderAdapter = new AfterSalesRequestOrderAdapter();
            afterSalesRequestOrderAdapter.setItemClickListener(this);
            recyclerView.setAdapter(afterSalesRequestOrderAdapter);
            afterSalesRequestOrderAdapter.setExpectDeliveredDate(bean.getExpectDeliveredDate());
            afterSalesRequestOrderAdapter.setList(bean.getItems());
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    //接收售后成功事件
    @Subscribe
    public void onEvent(AfterSalesCreateEvent event) {
        int orderItemId = event.getOrderItemId();
        for (int i = afterSalesRequestOrderAdapter.getFirstPosition(); i <= afterSalesRequestOrderAdapter.getLastPosition(); i++) {
            if (afterSalesRequestOrderAdapter.getListBean(i) instanceof OrderDetail.ItemsBean) {
                OrderDetail.ItemsBean bean = (OrderDetail.ItemsBean) afterSalesRequestOrderAdapter.getListBean(i);
                if (bean.getItemId() == orderItemId) {
                    afterSalesRequestOrderAdapter.update(i, "status", 6);
                    break;
                }
            }
        }
    }
    //接收售后取消事件
    @Subscribe
    public void onEvent(AfterSalesCancleEvent event) {
        int orderItemId = event.getOrderItemId();
        for (int i = 0; i < afterSalesRequestOrderAdapter.getItemCount(); i++) {
            if (afterSalesRequestOrderAdapter.getListBean(i) instanceof OrderDetail.ItemsBean) {
                OrderDetail.ItemsBean bean = (OrderDetail.ItemsBean) afterSalesRequestOrderAdapter.getListBean(i);
                if (bean.getItemId() == orderItemId) {
                    afterSalesRequestOrderAdapter.update(i, "status", 4);
                    break;
                }
            }
        }
    }
    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object abean) {
        if (onclickType == 0) {
            switch (view.getId()) {
                case R.id.after_sales:
                    OrderDetail.ItemsBean itemsBean = (OrderDetail.ItemsBean) abean;
                    if (itemsBean == null) return true;
                    if (itemsBean.getStatus() != 6 && itemsBean.getStatus() != 9) {
                        itemsBean.setIsUseCoupon(this.bean.getCouponAmount());
                        AfterSalesCreateActivity.startActivity(view.getContext(), itemsBean);
                    } else if (itemsBean.getStatus() == 6 || itemsBean.getStatus() == 9) {
                        AfterSalesDetailActivity.startActivity(view.getContext(), -1, itemsBean.getItemId());
                    }
                    break;
            }
        }
        return false;
    }
}
