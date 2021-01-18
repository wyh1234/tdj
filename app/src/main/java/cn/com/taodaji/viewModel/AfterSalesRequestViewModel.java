package cn.com.taodaji.viewModel;

import android.view.View;
import android.widget.TextView;

import com.base.activity.BaseActivity;
import com.base.utils.DateUtils;
import com.base.utils.UIUtils;
import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.com.taodaji.R;
import cn.com.taodaji.common.Constants;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.OrderDetail;
import cn.com.taodaji.model.entity.ScanQRCode;
import cn.com.taodaji.model.event.AfterSalesCancleEvent;
import cn.com.taodaji.model.event.AfterSalesCreateEvent;
import cn.com.taodaji.model.event.PackingCashReturnSweepCodeEvent;
import cn.com.taodaji.model.sqlBean.CartGoodsBean;
import cn.com.taodaji.ui.activity.orderPlace.AfterSalesCreateActivity;
import cn.com.taodaji.ui.activity.orderPlace.AfterSalesDetailActivity;
import cn.com.taodaji.ui.activity.packingCash.PackingCashReturnActivity;
import cn.com.taodaji.viewModel.vm.OrderPlaceGoodsDetailVM;

/**
 * Created by yangkuo on 2018/10/24.
 */
public class AfterSalesRequestViewModel extends BaseViewModel {

    public AfterSalesRequestViewModel(BaseActivity activity) {
        super(activity);
    }

    @Override
    public BaseVM getVM() {
        return new OrderPlaceGoodsDetailVM();
    }


    private ScanQRCode.DataBean bean;
    private long expectDeliveredDate;
    private OrderDetail.ItemsBean itemsBean;

    public long getExpectDeliveredDate() {
        return expectDeliveredDate;
    }

    public void setExpectDeliveredDate(long expectDeliveredDate) {
        this.expectDeliveredDate = expectDeliveredDate;
    }

    @Override
    public void initData() {
        super.initData();
        bean = (ScanQRCode.DataBean) getIntent().getSerializableExtra("data");
        expectDeliveredDate = bean.getExpectDeliveredDate();

        if (bean.getItems().size() == 0) return;
        itemsBean = bean.getItems().get(0);
        setData(itemsBean);
        init();
        //当天中午12点
        long now_12 = DateUtils.dateStringToLong(DateUtils.dateLongToString(0, "yyyy-MM-dd") + " 12:00:00");
        long timer_12 = now_12 - System.currentTimeMillis();//距当天中午12点的时间还剩下多少秒

        if (timer_12 > 0) UIUtils.getHandler().postDelayed(this::init, timer_12 + 1000);


        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void init() {

        getViewHolder().findViewById(R.id.after_sales).setVisibility(View.VISIBLE);


        if (itemsBean == null) return;
        if (itemsBean.getStatus() == 6 || itemsBean.getStatus() == 9) {
            getViewHolder().setText(R.id.after_sales, Constants.Order_itemStatus.get(itemsBean.getStatus()));
        } else {
            //送达时期的中午12点
            long expectDeliveredDate_s12 = DateUtils.dateStringToLong(DateUtils.dateLongToString(getExpectDeliveredDate(), "yyyy-MM-dd") + " 12:00:00");
            if (System.currentTimeMillis() < expectDeliveredDate_s12) {
                getViewHolder().setText(R.id.after_sales, "申请售后");
            } else {
                getViewHolder().setText(R.id.after_sales, "已关闭");
                getViewHolder().findViewById(R.id.after_sales).setEnabled(false);
                getViewHolder().setTextColor(R.id.after_sales, R.color.red_dark);
                getViewHolder().setVisibility(R.id.ll_phone, View.VISIBLE);
            }
        }
           // 是否收取押金 0-不收，1-收 isForegift
        if (PublicCache.loginPurchase != null){
            if (itemsBean.getPackageStatus() != 1){
                getViewHolder().setText(R.id.tv_go_back_money,"退押金");
                getViewHolder().setVisibility(R.id.tv_go_back_money,View.VISIBLE);
                getViewHolder().setVisibility(R.id.tv_back_day,View.GONE);
            }else{
                getViewHolder().setVisibility(R.id.tv_go_back_money,View.GONE);
                getViewHolder().setVisibility(R.id.tv_back_day,View.VISIBLE);
            }
        }else{
            getViewHolder().setVisibility(R.id.tv_go_back_money,View.GONE);
            getViewHolder().setVisibility(R.id.tv_back_day,View.GONE);
        }
    }

    //接收售后成功事件
    @Subscribe
    public void onEvent(AfterSalesCreateEvent event) {
        if (itemsBean != null) itemsBean.setStatus(6);
        init();
    }
    //接收售后取消事件
    @Subscribe
    public void onEvent(AfterSalesCancleEvent event) {
        if (itemsBean != null) itemsBean.setStatus(4);
        init();
    }

    //接收退押金事件
    @Subscribe
    public void onEvent(PackingCashReturnSweepCodeEvent event) {
        if (itemsBean != null) itemsBean.setPackageStatus(1);
        init();
    }
    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object bean) {
        if (onclickType == 0) {
            switch (view.getId()) {
                case R.id.after_sales:
                    if (itemsBean == null) return true;
                    if (itemsBean.getStatus() != 6 && itemsBean.getStatus() != 9) {
                        itemsBean.setIsUseCoupon(this.bean.getCouponAmount());
                        AfterSalesCreateActivity.startActivity(view.getContext(), itemsBean);
                    } else if (itemsBean.getStatus() == 6 || itemsBean.getStatus() == 9) {
                        AfterSalesDetailActivity.startActivity(view.getContext(), -1, itemsBean.getItemId());
                    }
                    break;

                case R.id.tv_go_back_money:
                    TextView textView = (TextView) view;
                    String state = textView.getText().toString();
                    if (state.equals("退押金")){
                        PackingCashReturnActivity.startActivity(view.getContext(), itemsBean);
                    }
                    break;
            }
        }
        return false;
    }
}
