package cn.com.taodaji.viewModel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.model.entity.DeliveryWarehouseItem;

public class DeliveryWarehouseAdapter extends RecyclerView.Adapter<DeliveryWarehouseAdapter.ViewHolder> {
    private List<DeliveryWarehouseItem> mItemList;
    private Context mContext;

    public DeliveryWarehouseAdapter(List<DeliveryWarehouseItem> mItemList, Context mContext) {
        this.mItemList = mItemList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_delivery_warehouse,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        DeliveryWarehouseItem item = mItemList.get(position);
        holder.itemTitle.setText(item.getStationName()+" (简称："+item.getShortName()+"仓)");
        holder.itemTotal.setText("总接到订单商品："+item.getTotalCount()+"个");
        holder.itemMorning.setText("早班车待入库："+item.getTotalEarlyBusCount()+"个");
        holder.itemMorningTime.setText(item.getEarlyBus()+"前送到");
        holder.itemNormal.setText("普通车待入库："+item.getTotalNormalBusCount()+"个");
        holder.itemNormalTime.setText(item.getNormalBus()+"前送到");
        holder.itemAddress.setText(item.getAddress());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle,itemTotal,itemMorning,itemMorningTime,itemNormal,itemNormalTime,itemAddress;
        Button start;
        ImageView navi;

        public ViewHolder(View view) {
            super(view);
            itemTitle = view.findViewById(R.id.tv_house_name);
            itemTotal = view.findViewById(R.id.tv_total);
            itemMorning = view.findViewById(R.id.morning);
            itemMorningTime = view.findViewById(R.id.morning_time);
            itemNormal = view.findViewById(R.id.normal_car);
            itemNormalTime = view.findViewById(R.id.normal_time);
            itemAddress = view.findViewById(R.id.tv_address);
            start = view.findViewById(R.id.btn_start_distribution);
            navi = view.findViewById(R.id.iv_navi_icon);

            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null)
                        itemClickListener.onItemClick(view, getLayoutPosition());
                }
            });
            navi.setOnClickListener(new View.OnClickListener() {
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
