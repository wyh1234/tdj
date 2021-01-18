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
 * Created by zhaowenlong on 2019/3/6.
 */
public class ChooseAssociatedShopAdapter extends RecyclerView.Adapter<ChooseAssociatedShopAdapter.ViewHolder> {
    private List<ShopAddressItem> mItemList;
    private Context mContext;

    public ChooseAssociatedShopAdapter(List<ShopAddressItem> mItemList, Context mContext) {
        this.mItemList = mItemList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_choose_associated_shop,parent,false);
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
        if (item.isCurrent()) {
            holder.itemIcon.setBackground(mContext.getResources().getDrawable(R.mipmap.checked));
            item.setCurrent(false);
        } else {
            holder.itemIcon.setBackground(mContext.getResources().getDrawable(R.mipmap.uncheck));
            item.setCurrent(true);
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemIcon;
        TextView itemTitle;


        public ViewHolder(View view) {
            super(view);
            itemIcon = view.findViewById(R.id.iv_item_checked);
            itemTitle = view.findViewById(R.id.tv_item_title);
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
