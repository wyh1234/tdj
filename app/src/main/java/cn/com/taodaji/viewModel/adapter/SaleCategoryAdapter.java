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
import cn.com.taodaji.model.entity.ProblemItem;

public class SaleCategoryAdapter extends RecyclerView.Adapter<SaleCategoryAdapter.ViewHolder> {
    private List<ProblemItem> mItemList;
    private Context mContext;
    private int type = 0;

    public SaleCategoryAdapter(List<ProblemItem> mItemList, Context mContext) {
        this.mItemList = mItemList;
        this.mContext = mContext;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sale_category, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProblemItem item = mItemList.get(position);
        holder.itemTitle.setText(item.getText());
        switch (type){
            case 0:
                holder.itemTitle.setTextColor(mContext.getResources().getColor(R.color.orange_yellow_ff7e01));
                holder.itemTitle.setBackgroundResource(R.drawable.z_round_rect_orange_ff7d01);
                break;
            case 1:
                holder.itemTitle.setTextColor(mContext.getResources().getColor(R.color.blue_2898eb));
                holder.itemTitle.setBackgroundResource(R.drawable.z_round_rect_blue);
                break;
            case 2:
                holder.itemTitle.setTextColor(mContext.getResources().getColor(R.color.red_fa7c85));
                holder.itemTitle.setBackgroundResource(R.drawable.z_round_rect_red_bg);
                break;
                default:break;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle;

        public ViewHolder(View view) {
            super(view);
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
    public int getItemCount() {
        return mItemList.size();
    }
}