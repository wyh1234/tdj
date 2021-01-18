package cn.com.taodaji.viewModel.adapter;

import android.content.Intent;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.utils.DateUtils;
import com.base.utils.JavaMethod;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.WaitNoticeResultBean;
import cn.com.taodaji.model.event.OrderDetailEvent;
import cn.com.taodaji.model.event.WaitEvent;
import cn.com.taodaji.ui.activity.orderPlace.OrderDetailActivity;
import cn.com.taodaji.ui.pay.PurchaserOrderConfirmaActivity;

public class WaitListAdapter extends SingleRecyclerViewAdapter {
    //用于退出 Activity,避免 Countdown，造成资源浪费。
    private SparseArray<CountDownTimer> countDownCounters = new SparseArray<>();
    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new BaseVM() {
            @Override
            protected void dataBinding() {

                putRelation("affairTitle", R.id.text_wait_status);
                putRelation("customerName",R.id.text_wait_shop);
                putRelation("originatorName",R.id.text_start_person);
                putRelation("originatorRole",R.id.text_start_role);
                putRelation("capitalAmount",R.id.text_money);
                putRelation("createOrderTime",R.id.text_order_time);
               // putRelation("createOrderTime",R.id.text_wait_time);
            }

            @Override
            public void setValue(@NonNull TextView textView, @NonNull Object value) {
                if (textView.getId() == R.id.text_start_role) {
                    int originatorRole = JavaMethod.transformClass(value, int.class);
                    String role=PublicCache.getRoleType().getValueOfKey(originatorRole);
                    super.setValue(textView, role);
                } else if (textView.getId() == R.id.text_money) {
                    BigDecimal money=JavaMethod.transformClass(value, BigDecimal.class);
                    super.setValue(textView, money.stripTrailingZeros().toPlainString()+"元");
                }  else super.setValue(textView, value);
            }

            @Override
            protected void addOnclick() {
                putViewOnClick(R.id.text_go_pay);
                putViewOnClick(R.id.tv_view_oder_detail);
            }
        });
    }
    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return ViewUtils.getFragmentView(parent, R.layout.item_wait_list);
    }

    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object item) {
        if (onclickType == 0) {
            if (PublicCache.loginPurchase==null)return true;
            if (PublicCache.loginPurchase.getEmpRole()==1||PublicCache.loginPurchase.getEmpRole()==5) {
                UIUtils.showToastSafesShort(PublicCache.getRoleType().getValueOfKey(PublicCache.loginPurchase.getEmpRole())+"没有该操作权限");
                return true;
            }
            switch (view.getId()){
                case R.id.text_go_pay:
                    WaitNoticeResultBean.DataBean.ItemsBean bean=(WaitNoticeResultBean.DataBean.ItemsBean)item;
                    Intent intent2 = new Intent(view.getContext(), PurchaserOrderConfirmaActivity.class);
                    intent2.putExtra("CreateTime",DateUtils.dateStringToLong(bean.getCreateOrderTime(), "yyyy-MM-dd HH:mm:ss"));
                    intent2.putExtra("OrderIds", bean.getOrderIds());
                    intent2.putExtra("OutTradeNo", bean.getOutTradeNo());
                    intent2.putExtra("Order_no", bean.getExtOrderId());
                    intent2.putExtra("TotalPrice", bean.getCapitalAmount()+"");
                    intent2.putExtra("position",position);
                    view.getContext().startActivity(intent2);
                    break;
                case R.id.tv_view_oder_detail:
                    WaitNoticeResultBean.DataBean.ItemsBean beans=(WaitNoticeResultBean.DataBean.ItemsBean)item;
                    if (TextUtils.isEmpty(beans.getExtOrderId())) break;
                    OrderDetailActivity.startActivity(view.getContext(), new OrderDetailEvent(0, beans.getExtOrderId(),0));
                    break;
                    default:
                        break;
            }
            return true;
        }
        return super.onItemClick(view, onclickType, position, item);
    }

    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        super.onBindViewHolderCustomer(holder, position);

        TextView timeText=holder.findViewById(R.id.text_wait_time);

        WaitNoticeResultBean.DataBean.ItemsBean bean=(WaitNoticeResultBean.DataBean.ItemsBean)getListBean(position);

        TextView viewOrder = holder.findViewById(R.id.tv_view_oder_detail);
        viewOrder.setText(Html.fromHtml("<u>查看订单详情></u>"));
        //BigDecimal limited_hour;   //支付的倒计时
       // limited_hour = new BigDecimal(PublicCache.initializtionData.getCount_down_time());

        int minute = PublicCache.initializtionData.getCount_down_time();
        long next_hour = DateUtils.getMinute(DateUtils.dateStringToLong(bean.getCreateOrderTime()), minute);
        long time_end = next_hour - System.currentTimeMillis();


        TextView text_go_pay=holder.findViewById(R.id.text_go_pay);
       // timeText.setTimes(time_end,position,text_go_pay);


        CountDownTimer countDownTimer = countDownCounters.get(timeText.hashCode());
        if (countDownTimer != null) {
            //将复用的倒计时清除
            countDownTimer.cancel();
        }


        if (time_end > 0) {
            countDownTimer = new CountDownTimer(time_end, 1000) {
                public void onTick(long millisUntilFinished) {
                    String mm= DateUtils.parseTime("mm", millisUntilFinished);
                    String ss= DateUtils.parseTime("ss", millisUntilFinished);
                    timeText.setTextColor(UIUtils.getColor(R.color.black_65));
                    timeText.setText("付款倒计时: " + mm+":"+ss);
                    text_go_pay.setVisibility(View.VISIBLE);

                }
                public void onFinish() {
                    timeText.setText("订单已超时关闭");
                    timeText.setTextColor(UIUtils.getColor(R.color.red_dark));
                    text_go_pay.setVisibility(View.GONE);
                    EventBus.getDefault().post(new WaitEvent(position));

                }
            }.start();
            //将此 countDownTimer 放入list.
            countDownCounters.put(timeText.hashCode(), countDownTimer);
        } else {
            timeText.setText("订单已超时关闭");
            timeText.setTextColor(UIUtils.getColor(R.color.red_dark));
            text_go_pay.setVisibility(View.GONE);
            EventBus.getDefault().post(new WaitEvent(position));
        }


    }

    /**
     * 清空当前 CountTimeDown 资源
     */
    public void cancelAllTimers() {
        if (countDownCounters == null) {
            return;
        }
        for (int i = 0, length = countDownCounters.size(); i < length; i++) {
            CountDownTimer cdt = countDownCounters.get(countDownCounters.keyAt(i));
            if (cdt != null) {
                cdt.cancel();
            }
        }
    }

}
