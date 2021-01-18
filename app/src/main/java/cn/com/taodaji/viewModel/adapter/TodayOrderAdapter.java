package cn.com.taodaji.viewModel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.utils.UIUtils;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.model.entity.ProblemItem;

public class TodayOrderAdapter extends RecyclerView.Adapter<TodayOrderAdapter.ViewHolder> {
    private List<ProblemItem> mItemList;
    private Context mContext;
    private int selected = 0;

    public TodayOrderAdapter(List<ProblemItem> mItemList, Context mContext) {
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_today_order, parent, false);
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
        LogUtils.e(item.getText());
        if (!UIUtils.isNullOrZeroLenght(item.getNum())){
                holder.itemNum.setText("("+item.getNum()+")");

        }else {
            holder.itemNum.setText("");
        }
        if (item.getNickname()!=null){
            holder.itemNickname.setVisibility(View.VISIBLE);
            holder.itemNickname.setText(item.getNickname());
        }else {
            holder.itemNickname.setVisibility(View.GONE);
        }
        if (selected==position) {
            holder.relativeLayout.setBackgroundResource(R.drawable.bg_selected_problem);
            holder.itemTitle.setTextColor(mContext.getResources().getColor(R.color.orange_yellow_ff7e01));
            holder.itemNickname.setTextColor(mContext.getResources().getColor(R.color.orange_yellow_ff7e01));
        } else {
            holder.relativeLayout.setBackgroundResource(R.drawable.bg_unselect_problem);
            holder.itemTitle.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.itemNickname.setTextColor(mContext.getResources().getColor(R.color.gray));
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle,itemNickname,itemNum;
        RelativeLayout relativeLayout;

        public ViewHolder(View view) {
            super(view);
            itemNum = view.findViewById(R.id.tv_item_num);
            itemTitle = view.findViewById(R.id.tv_item_title);
            relativeLayout = view.findViewById(R.id.rl_item);
            itemNickname = view.findViewById(R.id.tv_item_nickname);
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
