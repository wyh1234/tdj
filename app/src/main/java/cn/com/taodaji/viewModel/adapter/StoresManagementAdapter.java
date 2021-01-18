package cn.com.taodaji.viewModel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.model.entity.ShopAddressItem;

/**
 * Created by zhaowenlong on 2019/3/4.
 */
public class StoresManagementAdapter extends RecyclerView.Adapter<StoresManagementAdapter.ViewHolder> {
    private List<ShopAddressItem> mItemList;
    private Context mContext;

    public StoresManagementAdapter(List<ShopAddressItem> mItemList, Context mContext) {
        this.mItemList = mItemList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_other_stores,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        ShopAddressItem item = mItemList.get(position);
        holder.itemTitle.setText(item.getTitle());
        holder.itemData.setText(item.getSnippet());
        holder.itemIcon.setVisibility(View.VISIBLE);
        holder.itemTitle.setTextColor(mContext.getResources().getColor(R.color.black));
        holder.itemData.setTextColor(mContext.getResources().getColor(R.color.gray));
        switch (item.getAuthStatus()) {
            case -1:
                holder.itemExpired.setVisibility(View.VISIBLE);
                holder.itemExpired.setText("审核中");
                break;
            case 0:
                holder.itemExpired.setVisibility(View.VISIBLE);
                holder.itemExpired.setText("审核中");
                break;
            case 1:
                holder.itemExpired.setVisibility(View.GONE);
                break;
            case 2:
                holder.itemExpired.setVisibility(View.VISIBLE);
                holder.itemExpired.setText("审核失败");
                break;
                default:break;
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle,itemData,itemExpired;
        ImageView itemIcon;

        public ViewHolder(View view) {
            super(view);
            itemTitle = view.findViewById(R.id.tv_item_shop_title);
            itemData = view.findViewById(R.id.tv_item_shop_address);
            itemExpired = view.findViewById(R.id.tv_expired_address);
            itemIcon = view.findViewById(R.id.iv_select_current_shop);
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
