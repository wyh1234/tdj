package cn.com.taodaji.viewModel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.model.entity.ProblemItem;


public class ProblemListAdapter extends RecyclerView.Adapter<ProblemListAdapter.ViewHolder> {
    private List<ProblemItem> mItemList;
    private Context mContext;

    public ProblemListAdapter(List<ProblemItem> mItemList, Context mContext) {
        this.mItemList = mItemList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_problem_list,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        ProblemItem item = mItemList.get(position);
        holder.itemTitle.setText(item.getText());
        if (item.isChecked()){
            holder.itemTitle.setBackgroundResource(R.drawable.bg_selected_problem);
            holder.itemTitle.setTextColor(mContext.getResources().getColor(R.color.orange_yellow_ff7e01));
        }else {
            holder.itemTitle.setBackgroundResource(R.drawable.bg_unselect_problem);
            holder.itemTitle.setTextColor(mContext.getResources().getColor(R.color.gray));
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle;

        public ViewHolder(View view) {
            super(view);
            itemTitle = view.findViewById(R.id.rb_item_title);
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
