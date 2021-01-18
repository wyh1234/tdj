package cn.com.taodaji.viewModel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.model.entity.PickupPackageItem;

public class PickupPackageAdapter extends RecyclerView.Adapter<PickupPackageAdapter.ViewHolder> {
    private List<PickupPackageItem> mItemList;
    private Context mContext;
    private int selected = -1;

    public PickupPackageAdapter(List<PickupPackageItem> mItemList, Context mContext) {
        this.mItemList = mItemList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pickup_package,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setSelection(int position){
        this.selected = position;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        PickupPackageItem item = mItemList.get(position);
        if (selected==position){
            holder.bg.setBackgroundColor(mContext.getResources().getColor(R.color.gold_bg));
            holder.itemSellPrice.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.itemSellPrice.setBackgroundColor(mContext.getResources().getColor(R.color.gold));
            holder.divider.setBackgroundColor(mContext.getResources().getColor(R.color.gold_bg));
            holder.package_bg.setBackground(mContext.getResources().getDrawable(R.drawable.z_round_rect_gold));
        }else {
            holder.bg.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            holder.itemSellPrice.setTextColor(mContext.getResources().getColor(R.color.red_f0));
            holder.itemSellPrice.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            holder.divider.setBackgroundColor(mContext.getResources().getColor(R.color.gray_6a));
            holder.package_bg.setBackground(mContext.getResources().getDrawable(R.drawable.z_round_rect_gray_6a));
        }
        if (item.getDiscountAmount()>1000){
            holder.itemSellPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
        }
        if (item.getRechargeAmount()>1000){
            holder.itemPackagePrice.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
        }
        holder.itemSellPrice.setText("售价："+item.getDiscountAmount()+"元");
        holder.itemPackagePrice.setText(item.getRechargeAmount()+"");
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemPackagePrice,itemSellPrice;
        LinearLayout bg,package_bg;
        View divider;
        ImageView badge;

        public ViewHolder(View view) {
            super(view);
            bg = view.findViewById(R.id.ll_bg);
            itemPackagePrice = view.findViewById(R.id.tv_package_price);
            itemSellPrice = view.findViewById(R.id.tv_sell_price);
            badge = view.findViewById(R.id.iv_badge);
            package_bg = view.findViewById(R.id.ll_package_bg);
            divider = view.findViewById(R.id.divider);
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
        return mItemList.size();
    }
}
