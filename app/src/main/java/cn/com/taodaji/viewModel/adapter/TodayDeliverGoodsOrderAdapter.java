package cn.com.taodaji.viewModel.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.glide.GlideImageView;
import com.base.glide.ImageLoaderUtils;
import com.bumptech.glide.Glide;

import java.math.BigDecimal;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.model.entity.TodayDeliverGoods;
import cn.com.taodaji.model.entity.TodayDeliverGoodsOrderBean;

import static cn.com.taodaji.common.Constants.goodsNameLength;

/**
 * Created by Administrator on 2017-10-17.
 */

public class TodayDeliverGoodsOrderAdapter extends RecyclerView.Adapter<TodayDeliverGoodsOrderAdapter.ViewHolder> {
    private List<TodayDeliverGoods> mItemList;
    private Context mContext;

    public TodayDeliverGoodsOrderAdapter(List<TodayDeliverGoods> mItemList, Context mContext) {
        this.mItemList = mItemList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_today_deliver_goods,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        TodayDeliverGoods item = mItemList.get(position);
        holder.itemTitle.setText(item.getProductName()+"("+item.getNickName()+")");
        Glide.with(mContext).load(item.getProductImg()).into(holder.itemIcon);
        if (item.getPrintStatus()==1){
            holder.itemPrint.setImageResource(R.mipmap.printed3x);
        }else {
            holder.itemPrint.setImageResource(R.mipmap.unprinted3x);
        }
        switch (item.getLevelType()) {
            case 1:
                holder.itemPrice.setText(item.getPrice()+"元/"+item.getUnit()+"x"+item.getQty());
                holder.itemUnit.setText(item.getUnit());
                break;
            case 2:
                holder.itemPrice.setText(item.getPrice()+"元/"+item.getUnit()+"("+item.getLevel2Value()+item.getLevel2Unit()+")"+"x"+item.getQty());
                if (item.getAvgUnit().equals(item.getLevel2Unit())) {
                    holder.itemUnit.setText(item.getUnit()+"/共"+item.getLevel2Value().multiply(new BigDecimal(item.getQty()))+item.getLevel2Unit());
                }else {
                    holder.itemUnit.setText(item.getUnit()+"/共"+item.getQty()+item.getLevel2Unit());
                }
                break;
            case 3:
                holder.itemPrice.setText(item.getPrice()+"元/"+item.getUnit()+"("+item.getLevel2Value()+item.getLevel2Unit()+"x"+item.getLevel3Value()+item.getLevel3Unit()+")"+"x"+item.getQty());
                if (item.getAvgUnit().equals(item.getLevel3Unit())){
                    holder.itemUnit.setText(item.getUnit()+"/共"+item.getLevel3Value().multiply(item.getLevel2Value().multiply(new BigDecimal(item.getQty())))+item.getLevel3Unit());
                }else {
                    holder.itemUnit.setText(item.getUnit()+"/共"+item.getLevel2Value().multiply(new BigDecimal(item.getQty()))+item.getLevel2Unit());
                }
                break;
                default:break;
        }
        holder.itemNumber.setText(item.getQty()+"");
        holder.itemOrder.setText("商品单号："+item.getQrCodeId());
        if (item.getIsC()==1){
            holder.itemStore.setText(item.getCustomerName()+"("+item.getCustomerLineCode()+")");
        }else {
            holder.itemStore.setText(item.getShippingHotel()+"("+item.getCustomerLineCode()+")");
        }

        holder.itemTotalPrice.setText(item.getTotalPrice()+"");
        if (item.isSelected()){
            holder.select.setChecked(true);
        }else {
            holder.select.setChecked(false);
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemIcon,itemPrint;
        TextView itemTitle,itemStore,itemPrice,itemUnit,itemNumber,itemTotalPrice,itemOrder;
        CheckBox select;

        public ViewHolder(View view) {
            super(view);
            select = view.findViewById(R.id.cb_select_print);
            itemTitle = view.findViewById(R.id.tv_product_name);
            itemStore = view.findViewById(R.id.tv_store_name);
            itemPrice =view.findViewById(R.id.tv_product_price);
            itemNumber = view.findViewById(R.id.item_today_deliver_goods_number);
            itemTotalPrice = view.findViewById(R.id.cart_price);
            itemUnit = view.findViewById(R.id.unit);
            itemOrder = view.findViewById(R.id.tv_product_NO);
            itemIcon = view.findViewById(R.id.iv_product_icon);
            itemPrint = view.findViewById(R.id.iv_print_status);
            select.setOnClickListener(new View.OnClickListener() {
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
        return mItemList.size();
    }
}