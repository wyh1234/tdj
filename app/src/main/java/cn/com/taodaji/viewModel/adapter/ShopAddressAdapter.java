package cn.com.taodaji.viewModel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.utils.UIUtils;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.ShopAddressItem;

/**
 * Created by zhaowenlong on 2019/3/2.
 */
public class ShopAddressAdapter extends RecyclerView.Adapter<ShopAddressAdapter.ViewHolder> {
    private List<ShopAddressItem> mItemList;
    private Context mContext;
    private int index;
    public void setIndex(int index){
        this.index=index;
    }
    public ShopAddressAdapter(List<ShopAddressItem> mItemList, Context mContext) {
        this.mItemList = mItemList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shop_address,parent,false);
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
        holder.tv_jl.setText("距离"+UIUtils.getDistance(item.getPoint().getLongitude(),item.getPoint().getLatitude(), PublicCache.longtitude,PublicCache.latitude)+"公里");

//        if (item.getDistance()<1){
//            holder.tv_jl.setText("距离小于"+1+"m");
//        }else {
//            holder.tv_jl.setText("距离"+item.getDistance()+"m");
//        }
        if (index!=1){
            if (position==0){
                item.setCurrent(true);
            }
        }

        if (item.isCurrent()) {
            holder.itemIcon.setBackground(mContext.getResources().getDrawable(R.drawable.shop_location_selected));
            holder.itemTitle.setTextColor(mContext.getResources().getColor(R.color.orange_yellow_ff7e01));
            holder.tv_jl.setTextColor(mContext.getResources().getColor(R.color.orange_yellow_ff7e01));
            item.setCurrent(false);
        } else {
            holder.itemIcon.setBackground(mContext.getResources().getDrawable(R.drawable.shop_location_unselect));
            holder.itemTitle.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.tv_jl.setTextColor(mContext.getResources().getColor(R.color.black));
            item.setCurrent(false);
        }
        if (index==1){
            holder.tv_jl.setVisibility(View.VISIBLE);
        }else {
            holder.tv_jl.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null)
                    itemClickListener.onItemClick(view, position);
            }
        });
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemIcon;
        TextView itemTitle,itemData,tv_jl;

        public ViewHolder(View view) {
            super(view);
            itemIcon = view.findViewById(R.id.iv_item_location);
            itemTitle = view.findViewById(R.id.tv_shop_title);
            itemData = view.findViewById(R.id.tv_shop_address);
            tv_jl= view.findViewById(R.id.tv_jl);

        }
    }

    @Override
    public int getItemCount(){
        return mItemList.size();
    }
}
