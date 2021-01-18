package cn.com.taodaji.ui.activity.penalty.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.glide.ImageLoaderUtils;
import com.base.glide.nineImageView.ImageAttr;
import com.base.glide.nineImageView.ImagesActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.PunishData;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.ui.activity.penalty.OrderCountDownTimer;
import cn.com.taodaji.ui.activity.purchaseBill.PhotoViewActivity;


public class PunishListAdapter extends RecyclerView.Adapter<PunishListAdapter.PunishListViewHolder>{
    private Context context;
    private OnItemClickListener listener;
    private List<PunishData.DataBean.ListBean> data;
    private SparseArray<OrderCountDownTimer> countDownMap;


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public PunishListAdapter(Context context, List<PunishData.DataBean.ListBean> data) {
        this.context=context;
        this.data=data;
        countDownMap = new SparseArray<>();
    }

    @NonNull
    @Override
    public PunishListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.punish_list_item, parent, false);
        PunishListViewHolder punishListViewHolder = new PunishListViewHolder(root);
        return punishListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PunishListViewHolder holder, int position) {
        holder.tv_date.setText("时间:"+data.get(position).getCreate_time());
        holder.tv_type.setText("缴费订单:"+data.get(position).getOrder_no());
        holder.tv_detail.setText("缴费事由:"+data.get(position).getItem());
        holder.tv_money.setText("￥"+data.get(position).getMoney());
        holder.tv_appeal_cause.setText("附加说明:"+data.get(position).getPunish());

        if (data.get(position).getStatus()==1){
            if (data.get(position).getUt_status()==2){
                holder.tv_status.setText("已逾期");
                holder.tv_status.setTextColor(context.getResources().getColor(R.color.red_f0));
                holder.tv_time.setVisibility(View.GONE);
                holder.tv_remark.setText("已逾期，店铺暂停提现");
            }else {
                holder.tv_status.setText("待处理");
                holder.tv_status.setTextColor(context.getResources().getColor(R.color.blue_2898eb));
                holder.rl_bottom.setVisibility(View.VISIBLE);
                //将前一个缓存清除
                if (holder.countDownTimer != null) {
                    holder.countDownTimer.cancel();
                }
                if ( !ListUtils.isNullOrZeroLenght(data.get(position).getClose_time())) {
                    holder.tv_time.setVisibility(View.VISIBLE);
                    if (ListUtils.get_time_difference(System.currentTimeMillis(),data.get(position).getClose_time())!=0){
                        holder.countDownTimer = new OrderCountDownTimer(ListUtils.get_time_difference(System.currentTimeMillis(),data.get(position).getClose_time()), 1000,
                                holder.tv_time) {
                            @Override
                            public void onFinish() {
                                holder.tv_time.setVisibility(View.GONE);
                                holder.tv_remark.setText("已逾期，店铺暂停提现");
                                holder.tv_status.setText("已逾期");
                                holder.tv_status.setTextColor(context.getResources().getColor(R.color.red_f0));

                            }
                        };
                        holder.countDownTimer.start();
                        countDownMap.put(holder.tv_time.hashCode(),  holder.countDownTimer);

                    }else {
                        holder.tv_time.setVisibility(View.GONE);
                        holder.tv_remark.setText("已逾期，店铺暂停提现");
                        holder.tv_status.setText("已逾期");
                        holder.tv_status.setTextColor(context.getResources().getColor(R.color.red_f0));
                    }


                }

            }




        }else if (data.get(position).getStatus()==4){
            holder.tv_status.setText("已取消");
            holder.tv_status.setTextColor(context.getResources().getColor(R.color.gray_69));
            holder.rl_bottom.setVisibility(View.GONE);

        }else if (data.get(position).getStatus()==3){
            holder.tv_status.setText("已缴纳");
            holder.tv_status.setTextColor(context.getResources().getColor(R.color.gray_69));
            holder.rl_bottom.setVisibility(View.GONE);
        }


        holder.tv_go_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick( view, position);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick( view, position);
            }
        });
        holder.tv_iamge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.get(position).getVoucher().size()>0){
                    Intent intent = new Intent(context, PhotoViewActivity.class);
                    intent.putExtra("showphoto", 0);
                    intent.putStringArrayListExtra("showphotolist", (ArrayList<String>) data.get(position).getVoucher());
                    context.startActivity(intent);
                }


            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class PunishListViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout rl_bottom;
        TextView tv_go_pay,tv_status,tv_time,tv_date,tv_type,tv_detail,tv_money,tv_appeal_cause,tv_remark,tv_iamge;
        OrderCountDownTimer countDownTimer;
        public PunishListViewHolder(View itemView) {
            super(itemView);
            tv_go_pay=itemView.findViewById(R.id.tv_go_pay);
            tv_status=itemView.findViewById(R.id.tv_status);
            rl_bottom=itemView.findViewById(R.id.rl_bottom);
            tv_time=itemView.findViewById(R.id.tv_time);
            tv_date=itemView.findViewById(R.id.tv_date);
            tv_type=itemView.findViewById(R.id.tv_type);
            tv_detail=itemView.findViewById(R.id.tv_detail);
            tv_money=itemView.findViewById(R.id.tv_money);
            tv_appeal_cause=itemView.findViewById(R.id.tv_appeal_cause);
            tv_remark=itemView.findViewById(R.id.tv_remark);
            tv_iamge=itemView.findViewById(R.id.tv_iamge);
        }
    }
}
