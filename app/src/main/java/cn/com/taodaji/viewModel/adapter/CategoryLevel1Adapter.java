package cn.com.taodaji.viewModel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.model.entity.GoodsLevel1;

public class CategoryLevel1Adapter extends RecyclerView.Adapter<CategoryLevel1Adapter.ViewHolder> {
    private List<GoodsLevel1> mItemList;
    private Context mContext;
    private int selected = 0;

    public CategoryLevel1Adapter(List<GoodsLevel1> mItemList, Context mContext) {
        this.mItemList = mItemList;
        this.mContext = mContext;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_level1,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        GoodsLevel1 item = mItemList.get(position);
        if (selected==position) {
            holder.bg.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            holder.title.setTextColor(mContext.getResources().getColor(R.color.blue_2898eb));
            holder.icon.setVisibility(View.VISIBLE);
        } else {
            holder.bg.setBackgroundColor(mContext.getResources().getColor(R.color.gray_6e));
            holder.title.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.icon.setVisibility(View.INVISIBLE);
        }
        holder.title.setText(item.getName());
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;
        LinearLayout bg;
        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tv_item_title);
            icon = view.findViewById(R.id.iv_item_icon);
            bg = view.findViewById(R.id.ll_bg);
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

