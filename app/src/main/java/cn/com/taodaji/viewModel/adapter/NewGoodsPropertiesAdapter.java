package cn.com.taodaji.viewModel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.model.entity.CommodityLabel;

public class NewGoodsPropertiesAdapter extends RecyclerView.Adapter<NewGoodsPropertiesAdapter.ViewHolder> {
    private List<CommodityLabel.DataBean.ListBean> mItemList;
    private Context mContext;

    public NewGoodsPropertiesAdapter(List<CommodityLabel.DataBean.ListBean> mItemList, Context mContext) {
        this.mItemList = mItemList;
        this.mContext = mContext;
    }

    @Override
    public NewGoodsPropertiesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_new_goods_properties,parent,false);
        NewGoodsPropertiesAdapter.ViewHolder holder = new NewGoodsPropertiesAdapter.ViewHolder(view);
        return holder;
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(NewGoodsPropertiesAdapter.ViewHolder holder, int position){
        CommodityLabel.DataBean.ListBean item = mItemList.get(position);
            holder.title.setText(item.getPropertyZhName());
        holder.content.setText(item.getContent());

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,content;
        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tv_item_title);
            content = view.findViewById(R.id.tv_item_content);
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

