package cn.com.taodaji.viewModel.adapter;

import android.content.Intent;
import android.opengl.Visibility;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.utils.JavaMethod;
import com.base.utils.ViewUtils;
import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;

import java.math.BigDecimal;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.WaitNoticeResultBean;
import cn.com.taodaji.ui.activity.myself.NoticeAfterSaleDetailActivity;
import cn.com.taodaji.ui.activity.myself.NoticeGetSuccessDetailActivity;
import cn.com.taodaji.ui.activity.myself.NoticePayDetailActivity;

public class NoticeListAdapter extends SingleRecyclerViewAdapter {

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new BaseVM() {
            @Override
            protected void dataBinding() {

                putRelation("messageTitle", R.id.text_order_status);
                putRelation("capitalAmount",R.id.text_money);
                putRelation("originatorName",R.id.text_start_person);
                putRelation("originatorRole",R.id.text_start_role);

//                putRelation("paymentCustomerName",R.id.text_pay_person);
//                putRelation("paymentCustomerRole",R.id.text_pay_role);

//                putRelation("person_name",R.id.text_manage_time);
//                putRelation("phone",R.id.text_after_time);
                putRelation("customerName",R.id.text_wait_shop);
            }

            @Override
            public void setValue(@NonNull TextView textView, @NonNull Object value) {
                if (textView.getId() == R.id.text_start_role) {
                    int originatorRole = JavaMethod.transformClass(value, int.class);
                    String role= PublicCache.getRoleType().getValueOfKey(originatorRole);
                    super.setValue(textView, role);
                } else  if (textView.getId() == R.id.text_money) {
                    BigDecimal money= JavaMethod.transformClass(value, BigDecimal.class);
                    super.setValue(textView, money.stripTrailingZeros().toPlainString()+"元");
                }  else super.setValue(textView, value);
            }

            @Override
            protected void addOnclick() {

                putViewOnClick(R.id.text_go_detail);
            }
        });
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return ViewUtils.getFragmentView(parent, R.layout.item_notice_list);
    }

    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object bean) {
        if (onclickType == 0) {
            WaitNoticeResultBean.DataBean.ItemsBean itemsBean=(WaitNoticeResultBean.DataBean.ItemsBean)bean;
            //1.订单/付款成功
            //2.订单/付款超时订单关闭
            //3.提现/提现成功
            //4.售后申请/售后申请成功
            //5.售后完成
            switch (itemsBean.getMessageType()) {
                case 1: {
                    Intent intent = new Intent(view.getContext(), NoticePayDetailActivity.class);//NewsListActivity
                    intent.putExtra("data", itemsBean);
                    view.getContext().startActivity(intent);
                    break;
                }
                case 2: {
                    Intent intent = new Intent(view.getContext(), NoticePayDetailActivity.class);//NewsListActivity
                    intent.putExtra("data", itemsBean);
                    view.getContext().startActivity(intent);
                    break;
                }
                case 3: {
                    Intent intent = new Intent(view.getContext(), NoticeGetSuccessDetailActivity.class);//NewsListActivity
                    intent.putExtra("data", itemsBean);
                    view.getContext().startActivity(intent);
                    break;
                }
                case 4: {
                    Intent intent = new Intent(view.getContext(), NoticeAfterSaleDetailActivity.class);//NewsListActivity
                    intent.putExtra("data", itemsBean);
                    view.getContext().startActivity(intent);
                    break;
                }
                case 5: {
                    Intent intent = new Intent(view.getContext(), NoticeAfterSaleDetailActivity.class);//NewsListActivity
                    intent.putExtra("data", itemsBean);
                    view.getContext().startActivity(intent);
                    break;
                }
            }

            return true;
        }
            return super.onItemClick(view, onclickType, position, bean);
    }

    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        super.onBindViewHolderCustomer(holder, position);

        WaitNoticeResultBean.DataBean.ItemsBean bean=(WaitNoticeResultBean.DataBean.ItemsBean)getListBean(position);
        //1.订单/付款成功
        //2.订单/付款超时订单关闭
        //3.提现/提现成功
        //4.售后申请/售后申请成功
        //5.售后完成
        switch(bean.getMessageType()){
            case 1 :        //1.订单/付款成功
                holder.setImageRes(R.id.img_stutas,R.mipmap.icon_green_right);
                holder.setText(R.id.text_manage_time_title,"付款时间：");
                holder.setText(R.id.text_manage_time,bean.getPaymentSuccessTime());
                holder.setVisibility(R.id.ll_after_time, View.GONE);

                holder.setVisibility(R.id.ll_pay_person, View.VISIBLE);
                holder.setText(R.id.text_pay_person,bean.getPaymentCustomerName());

                int paymentCustomerRole =bean.getPaymentCustomerRole();
                String role= PublicCache.getRoleType().getValueOfKey(paymentCustomerRole);
                holder.setText(R.id.text_pay_role,role);
            break;
            case 2:  //2.订单/付款超时订单关闭
                holder.setImageRes(R.id.img_stutas,R.mipmap.icon_over_time);
                holder.setText(R.id.text_manage_time_title,"下单时间：");
                holder.setText(R.id.text_manage_time,bean.getCreateOrderTime());
                holder.setVisibility(R.id.ll_after_time, View.GONE);
                holder.setVisibility(R.id.ll_pay_person, View.GONE);
            break;
            case 3:   //3.提现/提现成功
                holder.setImageRes(R.id.img_stutas,R.mipmap.icon_get_sucess);
                holder.setText(R.id.text_manage_time_title,"处理时间：");
                holder.setText(R.id.text_manage_time,bean.getHandleWithdrawalTime());
                holder.setVisibility(R.id.ll_after_time, View.GONE);
                holder.setVisibility(R.id.ll_pay_person, View.GONE);
                break;
            case 4:  //4.售后申请/售后申请成功
                holder.setImageRes(R.id.img_stutas,R.mipmap.icon_after_apply_sucess);
                holder.setText(R.id.text_manage_time_title,"下单时间：");
                holder.setText(R.id.text_manage_time,bean.getCreateOrderTime());
                holder.setVisibility(R.id.ll_after_time, View.VISIBLE);
                holder.setText(R.id.text_after_time,bean.getAfterSalesApplicationCreateTime());
                holder.setVisibility(R.id.ll_pay_person, View.GONE);
                break;
            case 5:  //5.售后完成
                holder.setImageRes(R.id.img_stutas,R.mipmap.icon_after_finish);
                holder.setText(R.id.text_manage_time_title,"下单时间：");
                holder.setText(R.id.text_manage_time,bean.getCreateOrderTime());
                holder.setVisibility(R.id.ll_after_time, View.VISIBLE);
                holder.setText(R.id.text_after_time,bean.getAfterSalesApplicationFinishTime());
                holder.setVisibility(R.id.ll_pay_person, View.GONE);
                break;
        }


    }
}
