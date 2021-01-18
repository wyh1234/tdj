package cn.com.taodaji.ui.activity.advertisement.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.glide.ImageLoaderUtils;

import java.math.BigDecimal;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.MarketingManage;
import cn.com.taodaji.model.entity.TfAdvertisement;

public class TfAdvertisementManageListAdapter extends RecyclerView.Adapter<TfAdvertisementManageListAdapter.TfAdvertisementManageViewHolder>  {
    private Context context;
    private List<TfAdvertisement.DataBean.ItemsBean> data;
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public TfAdvertisementManageListAdapter(Context context, List<TfAdvertisement.DataBean.ItemsBean> data) {
        this.context=context;
        this.data=data;
    }
    @NonNull
    @Override
    public TfAdvertisementManageListAdapter.TfAdvertisementManageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.tf_adv_manage_item, parent, false);
        TfAdvertisementManageViewHolder tfAdvertisementManageViewHolder = new TfAdvertisementManageViewHolder(root);
        return tfAdvertisementManageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TfAdvertisementManageListAdapter.TfAdvertisementManageViewHolder holder, int position) {
        if (listener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(view,position);
                }
            });
        }

        if (data.get(position).getStatus()==5){
            if (data.get(position).getStartTime().length()>0){
                holder.tv_time.setText("投放时间:"+data.get(position).getStartTime().substring(0,10));
            }

            holder.tv_state.setText("已暂停");
            holder.tv_state.setTextColor(context.getResources().getColor(R.color.tfstate));
        }else if (data.get(position).getStatus()==4){
            if (data.get(position).getStartTime().length()>0){
                holder.tv_time.setText("投放时间:"+data.get(position).getStartTime().substring(0,10));
            }
            holder.tv_state.setText("已完成");
            holder.tv_state.setTextColor(context.getResources().getColor(R.color.tfstate_one));
        }else if (data.get(position).getStatus()==3){
            holder.tv_time.setText("申请时间:"+data.get(position).getCreateTime().substring(0,10));
            holder.tv_state.setText("投放中");
            holder.tv_state.setTextColor(context.getResources().getColor(R.color.tfstate_two));
        }else if (data.get(position).getStatus()==2){
            holder.tv_time.setText("申请时间:"+data.get(position).getCreateTime().substring(0,10));
            holder.tv_state.setText("排期中");
            holder.tv_state.setTextColor(context.getResources().getColor(R.color.blue_2898eb));
        }else if (data.get(position).getStatus()==1){
            holder.tv_time.setText("申请时间:"+data.get(position).getCreateTime().substring(0,10));
            holder.tv_state.setText("待审核");
            holder.tv_state.setTextColor(context.getResources().getColor(R.color.tfstate_));
        }else if (data.get(position).getStatus()==6){
            holder.tv_time.setText("申请时间:"+data.get(position).getCreateTime().substring(0,10));
            holder.tv_state.setText("审核未通过:"+data.get(position).getRefuseRemark());
            holder.tv_state.setTextColor(context.getResources().getColor(R.color.tfstate));
        }
        ImageLoaderUtils.loadImage(holder.iv,data.get(position).getAdvertisementBgUrl());
        if (data.get(position).getStageType()==1){
            if (data.get(position).getGiftDays()>0){
                holder.tv_content.setText("投放套餐:"+data.get(position).getStage()+"期"+data.get(position).getPackageDays()+"天(加送"+data.get(position).getGiftDays()+"天)");
            }else {
                holder.tv_content.setText("投放套餐:"+data.get(position).getStage()+"期"+data.get(position).getPackageDays()+"天");
            }
            holder.tv_pice.setText("￥"+data.get(position).getPrice()+"元");
        }else {
            holder.tv_content.setText("投放套餐:"+data.get(position).getDays()+"天");
            holder.tv_pice.setText("￥"+(data.get(position).getPrice().multiply(new BigDecimal(data.get(position).getDays())))+"元");

        }


        holder.tv_title.setText("投放位置:"+data.get(position).getAdvertisementTypeName());

        holder.tv_id.setText("计划ID:"+data.get(position).getAdvertisementPlanCode());
        if (data.get(position).getCombination()==1){
            holder.tv_type.setText("广告类型：单品宣传");
        }else {
            holder.tv_type.setText("广告类型：套餐组合");
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class TfAdvertisementManageViewHolder extends RecyclerView.ViewHolder{
        TextView tv_time,tv_state,tv_title,tv_content,tv_id,tv_type,tv_pice;
        ImageView iv;

        public TfAdvertisementManageViewHolder(View itemView) {
            super(itemView);
            tv_time=itemView.findViewById(R.id.tv_time);
            tv_state=itemView.findViewById(R.id.tv_state);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_content=itemView.findViewById(R.id.tv_content);
            tv_id=itemView.findViewById(R.id.tv_id);
            tv_type=itemView.findViewById(R.id.tv_type);
            iv=itemView.findViewById(R.id.iv);
            tv_pice=itemView.findViewById(R.id.tv_pice);
        }
    }
}
