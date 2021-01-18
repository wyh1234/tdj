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
import cn.com.taodaji.model.entity.GoodsLevel2;

public class VegetablesGoodsAdapter extends RecyclerView.Adapter<VegetablesGoodsAdapter.ViewHolder> {
    private List<GoodsLevel2> mItemList;
    private Context mContext;

    public VegetablesGoodsAdapter(List<GoodsLevel2> mItemList, Context mContext) {
        this.mItemList = mItemList;
        this.mContext = mContext;
    }

    public List<GoodsLevel2> getmItemList() {
        return mItemList;
    }

    public void setmItemList(List<GoodsLevel2> mItemList) {
        this.mItemList = mItemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_level2,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        GoodsLevel2 item = mItemList.get(position);
        holder.title.setText(item.getName());
        holder.cancle.setVisibility(View.VISIBLE);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,cancle;
        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tv_item_title);
            cancle = view.findViewById(R.id.tv_cancle);
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