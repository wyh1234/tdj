package cn.com.taodaji.viewModel.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.base.utils.ViewUtils;
import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.PickupFeeList;
import cn.com.taodaji.ui.activity.myself.FeeDetailActivity;

public class PickupDetailAdapter extends SingleRecyclerViewAdapter {

    private Context mContext;

    public PickupDetailAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new BaseVM() {
            @Override
            protected void dataBinding() {
                //putRelation("enterpriseCode", R.id.text_shop_name);
                //putRelation("nickName",R.id.text_person_name);
                //putRelation("account",R.id.text_shop_phone);
            }

            @Override
            protected void addOnclick() {

                //  putViewOnClick(R.id.item_view);
            }
        });
    }
    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return ViewUtils.getFragmentView(parent, R.layout.item_pickup_detail2);
    }

    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        super.onBindViewHolderCustomer(holder, position);
        PickupFeeList.DataBean.PageBeanBean.RecordListBean bean = (PickupFeeList.DataBean.PageBeanBean.RecordListBean)getListBean(position);
        // img_green_point\
        switch (bean.getType()){
            case 2:
                holder.setImageRes(R.id.iv_item_icon,R.mipmap.turn_in3x);
                holder.setText(R.id.tv_item_price,"+"+bean.getPayMoney());
                holder.setText(R.id.tv_item_info,"从供货款余额续费");
                break;
            case 3:
                holder.setImageRes(R.id.iv_item_icon,R.mipmap.turn_out3x);
                holder.setText(R.id.tv_item_price,"-"+bean.getPayMoney());
                holder.setText(R.id.tv_item_info,"转出至供货款余额");
                break;
            case 4:
                holder.setImageRes(R.id.iv_item_icon, R.mipmap.pickup3x);
                holder.setText(R.id.tv_item_price, "-" + bean.getPayMoney());
                if (bean.getStatus() == 1) {
                    holder.setText(R.id.tv_item_info, bean.getReceiveStationNameAbbr() + "（接：" + bean.getStationNameAbbr() + "仓商品" + bean.getOrderTotalFee() + "元）");
                }
                break;
            case 5:
                holder.setImageRes(R.id.iv_item_icon, R.mipmap.saoma_mx_bg);
                holder.setText(R.id.tv_item_price, "-" + bean.getPayMoney());
                    holder.setText(R.id.tv_item_info, "扫码费定时扣款");
                break;
            case 1:
                holder.setImageRes(R.id.iv_item_icon,R.mipmap.buy_package3x);
                holder.setText(R.id.tv_item_price,"+"+bean.getBuyMoney());
                switch (bean.getRechargeType()){
                    case 1:
                        holder.setText(R.id.tv_item_info,"充值淘钱包（支付宝）");
                        break;
                    case 2:
                        holder.setText(R.id.tv_item_info,"充值淘钱包（微信）");
                        break;
                    case 3:
                        holder.setText(R.id.tv_item_info,"充值淘钱包（余额）");
                        break;
                        default:break;
                }
                break;
            default:break;
        }
        holder.setText(R.id.tv_item_balance,"余额"+bean.getStoreReceiveMoney()+"元");
        holder.setText(R.id.tv_item_date,bean.getPayTime());
        RelativeLayout relativeLayout = holder.findViewById(R.id.rl_item);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, FeeDetailActivity.class);
                intent.putExtra("feePercent",bean.getFeePercent()+"");
                intent.putExtra("receiveType",bean.getReceiveType());
                intent.putExtra("payMoney",bean.getPayMoney()+"");
                intent.putExtra("expectDeliveredDate",bean.getExpectDeliveredDate());
                intent.putExtra("receiveStationId",bean.getReceiveStationId());
                intent.putExtra("receiveTime",bean.getReceiveTime());
                intent.putExtra("orderTotalFee",bean.getOrderTotalFee()+"");
                intent.putExtra("address",bean.getReceiveStationName()+"("+bean.getReceiveStationNameAbbr()+")接至："+bean.getStationName()+"("+bean.getStationNameAbbr()+")");
                if (bean.getType()==4){
                mContext.startActivity(intent);}
            }
        });
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }
}

