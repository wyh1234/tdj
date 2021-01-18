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

public class VerifyCategoryAdapter extends RecyclerView.Adapter<VerifyCategoryAdapter.ViewHolder> {
    private List<ProblemItem> mItemList;
    private Context mContext;

    public VerifyCategoryAdapter(List<ProblemItem> mItemList, Context mContext) {
        this.mItemList = mItemList;
        this.mContext = mContext;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_verify_category, parent, false);
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
        holder.itemTitle.setText(item.getText()+"  >  ");
        holder.itemContent.setText(item.getNickname());
        holder.itemDate.setText("申请时间："+item.getNum());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle,itemContent,itemDate;

        public ViewHolder(View view) {
            super(view);
            itemTitle = view.findViewById(R.id.tv_item_title);
            itemContent = view.findViewById(R.id.tv_item_content);
            itemDate = view.findViewById(R.id.tv_apply_date);
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
