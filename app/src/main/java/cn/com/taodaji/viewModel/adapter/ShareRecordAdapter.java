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
import cn.com.taodaji.model.entity.IntegralItem;
import cn.com.taodaji.model.entity.ShareRecord;

public class ShareRecordAdapter extends RecyclerView.Adapter<ShareRecordAdapter.ViewHolder> {
    private List<IntegralItem.DataBean> mItemList;
    private Context mContext;

    public ShareRecordAdapter(List<IntegralItem.DataBean> mItemList, Context mContext) {
        this.mItemList = mItemList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_share_record,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.itemTitle.setText(mItemList.get(position).getNickName());

        holder.itemData.setText(mItemList.get(position).getMobile().substring(0, 3) + "****" + mItemList.get(position).getMobile().substring(7, 11));
        holder.itemAction.setText(mItemList.get(position).getTypeName());
        holder.itemDate.setText(mItemList.get(position).getCreateTime().substring(0,10));
        holder.itemPoints.setText(mItemList.get(position).getIntegral()+"");
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle,itemData,itemAction,itemDate,itemPoints;

        public ViewHolder(View view) {
            super(view);
            itemTitle = view.findViewById(R.id.tv_name);
            itemData = view.findViewById(R.id.tv_phone_num);
            itemAction = view.findViewById(R.id.tv_item_action);
            itemDate = view.findViewById(R.id.tv_date);
            itemPoints = view.findViewById(R.id.tv_points);

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
