package cn.com.taodaji.viewModel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.glide.GlideImageView;
import com.base.glide.ImageLoaderUtils;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.model.entity.CouponsChooseCouponList;
import cn.com.taodaji.model.entity.NewCouponsChooseCouponList;

public class NewCashCouponAdapter extends RecyclerView.Adapter<NewCashCouponAdapter.ViewHolder> {
    private List<NewCouponsChooseCouponList.DataBean.ItemBean> listData;
    private Context mContext;
    private int stase;


    public void setStase(int stase) {
        this.stase = stase;
    }

    public NewCashCouponAdapter(List<NewCouponsChooseCouponList.DataBean.ItemBean> listData, Context mContext) {
        this.listData = listData;
        this.mContext = mContext;
    }

    @Override
    public NewCashCouponAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cash_coupon_usable,parent,false);
        NewCashCouponAdapter.ViewHolder holder = new NewCashCouponAdapter.ViewHolder(view);
        return holder;
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(NewCashCouponAdapter.ViewHolder holder, int position){
        holder. purchaser.setVisibility(View.VISIBLE);
        if (stase==1){
            holder.textView10.setText("店券");
        }else {
            holder.textView10.setText("淘券");
        }
        if (listData.get(position).getPurchaseAmount().toString().equals("0")){
            holder.tv_cash_coupon_use_condition.setText("无金额限制");
        }else {
            holder.tv_cash_coupon_use_condition.setText("满" + listData.get(position).getPurchaseAmount() + "可用");
        }
        holder.tv_cash_coupon_money.setText(listData.get(position).getAmount().toString());
        holder.tv_cash_coupon_use_range.setText(listData.get(position).getCouponDesc());
        String value;
        value = listData.get(position).getStartTime().replace("-", ".");
        if (value.length() > 10) {
            value = listData.get(position).getStartTime().substring(0, 10);
        }
        holder.iv_cash_coupon_new_select.setSelected(listData.get(position).getSelected());
        ImageLoaderUtils.loadImage( holder.imageview,listData.get(position).getLogoImageUrl());
        holder.order_num.setText(listData.get(position).getStoreName());
        holder.validity_stat.setText(listData.get(position).getStartTime().replace("-", ".").length()>10
        ?listData.get(position).getStartTime().replace("-", ".").substring(0, 10):listData.get(position).getStartTime().replace("-", "."));
        holder.validity_end.setText(listData.get(position).getEndTime().replace("-", ".").length()>10
                ?listData.get(position).getEndTime().replace("-", ".").substring(0, 10):listData.get(position).getEndTime().replace("-", "."));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_cash_coupon_money,tv_cash_coupon_use_condition,textView10,tv_cash_coupon_use_range,validity_stat,validity_end,order_num;
        GlideImageView iv_cash_coupon_new_select,imageview;
        LinearLayout purchaser;

        public ViewHolder(View view) {
            super(view);
            tv_cash_coupon_money=view.findViewById(R.id.tv_cash_coupon_money);
            tv_cash_coupon_use_condition=view.findViewById(R.id.tv_cash_coupon_use_condition);
            textView10=view.findViewById(R.id.textView10);
            tv_cash_coupon_use_range=view.findViewById(R.id.tv_cash_coupon_use_range);
            validity_stat=view.findViewById(R.id.validity_stat);
            validity_end=view.findViewById(R.id.validity_end);
            iv_cash_coupon_new_select=view.findViewById(R.id.iv_cash_coupon_new_select);
            purchaser=view.findViewById(R.id.purchaser);
            order_num=view.findViewById(R.id.order_num);
            imageview=view.findViewById(R.id.imageview);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null)
                        itemClickListener.onItemClick(view, getLayoutPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount(){
        return listData.size();
    }
}
