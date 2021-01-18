package cn.com.taodaji.viewModel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.model.entity.ShopAddressItem;
import cn.com.taodaji.model.entity.XiaoQuAddressItem;

public class XiaoQuAddressAdapter extends RecyclerView.Adapter<XiaoQuAddressAdapter.ViewHolder> {
    private List<XiaoQuAddressItem.DataBean.DataListBean.ItemsBean> mItemList;
    private Context mContext;
    private int index;

    public XiaoQuAddressAdapter(List<XiaoQuAddressItem.DataBean.DataListBean.ItemsBean> mItemList, Context mContext) {
        this.mItemList = mItemList;
        this.mContext = mContext;
    }
    public void setIndex(int index){
        this.index=index;
    }

    @Override
    public XiaoQuAddressAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_xiaoqu_address,parent,false);
        XiaoQuAddressAdapter.ViewHolder holder = new XiaoQuAddressAdapter.ViewHolder(view);
        return holder;
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(XiaoQuAddressAdapter.ViewHolder holder, int position){
        XiaoQuAddressItem.DataBean.DataListBean.ItemsBean item = mItemList.get(position);
        holder.itemTitle.setText(item.getCommunityName());
        holder.itemData.setText(item.getCity()+item.getArea()+item.getAddress());
            holder.tv_jl.setText("距离"+item.getDistance().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()+"公里");
        if (index==1){
            holder.tv_ta.setVisibility(View.VISIBLE);
        }else {
            holder.tv_ta.setVisibility(View.GONE);
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle,itemData,tv_jl,tv_ta;

        public ViewHolder(View view) {
            super(view);
            tv_jl = view.findViewById(R.id.tv_jl);
            itemTitle = view.findViewById(R.id.tv_shop_title);
            itemData = view.findViewById(R.id.tv_shop_address);
            tv_ta=view.findViewById(R.id.tv_ta);
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
