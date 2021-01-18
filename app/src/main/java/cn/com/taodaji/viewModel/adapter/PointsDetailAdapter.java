package cn.com.taodaji.viewModel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.model.entity.GetPointsItem;
import cn.com.taodaji.model.entity.IntegralItem;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;

public class PointsDetailAdapter extends RecyclerView.Adapter<PointsDetailAdapter.ViewHolder> {
    private List<IntegralItem.DataBean> mItemList;
    private Context mContext;

    public PointsDetailAdapter(List<IntegralItem.DataBean> mItemList, Context mContext) {
        this.mItemList = mItemList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_points_detail,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        if (!ListUtils.isNullOrZeroLenght(mItemList.get(position).getExtOrderId())){
            holder.tv_item_num.setText(mItemList.get(position).getExtOrderId()+"");
        }

        if (mItemList.get(position).getType()==0){
            holder.itemIcon.setBackgroundResource(R.mipmap.recharge);
        }else if (mItemList.get(position).getType()==1){
            holder.itemIcon.setBackgroundResource(R.mipmap.share);
        }else if (mItemList.get(position).getType()==2){
            holder.itemIcon.setBackgroundResource(R.mipmap.check);
        }else if (mItemList.get(position).getType()==3){
            holder.itemIcon.setBackgroundResource(R.mipmap.shopping);

        }else if (mItemList.get(position).getType()==4){
            holder.itemIcon.setBackgroundResource(R.mipmap.tuijiandiya);
        }else if (mItemList.get(position).getType()==5){
            holder.itemIcon.setBackgroundResource(R.mipmap.shengjizhengsong);
        }else if (mItemList.get(position).getType()==6){
        holder.itemIcon.setBackgroundResource(R.mipmap.goumaikoujian);
        }else if (mItemList.get(position).getType()==7){
            holder.itemIcon.setBackgroundResource(R.mipmap.dingdanback);
        } else if (mItemList.get(position).getType()==8){
            holder.itemIcon.setBackgroundResource(R.mipmap.quxiaodingdan);
        }else if (mItemList.get(position).getType()==9){
            holder.itemIcon.setBackgroundResource(R.mipmap.pingjia_bg);
        }else if (mItemList.get(position).getType()==10){
            holder.itemIcon.setBackgroundResource(R.mipmap.shopping);
        }else if (mItemList.get(position).getType()==12){
            holder.itemIcon.setBackgroundResource(R.mipmap.duiba);
        }else if (mItemList.get(position).getType()==13){
            holder.itemIcon.setBackgroundResource(R.mipmap.duiba);
        }else {
            holder.itemIcon.setBackgroundResource(R.mipmap.complete);

        }

        holder.itemTitle.setText(mItemList.get(position).getTypeName());
        holder.itemDate.setText(mItemList.get(position).getCreateTime());
        if (Integer.parseInt(mItemList.get(position).getIntegralStr())>0){
            holder.tv_points_amount.setTextColor(mContext.getResources().getColor(R.color.red_f0));
        }else {
            holder.tv_points_amount.setTextColor(mContext.getResources().getColor(R.color.gray_69));
        }
        holder.tv_points_amount.setText(mItemList.get(position).getIntegralStr()+"");
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemIcon;
        TextView itemTitle,itemDate,tv_points_amount,tv_item_num;

        public ViewHolder(View view) {
            super(view);
            itemIcon = view.findViewById(R.id.iv_item_view);
            itemTitle = view.findViewById(R.id.tv_item_title);
            itemDate = view.findViewById(R.id.tv_item_date);
            tv_item_num = view.findViewById(R.id.tv_item_num);
            tv_points_amount = view.findViewById(R.id.tv_points_amount);
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

