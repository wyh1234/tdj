package cn.com.taodaji.ui.activity.integral.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.glide.ImageLoaderUtils;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.IntegralShop;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.ui.activity.integral.tools.OrderstateCountDownTimer;


public class IntegralShopAdapter extends RecyclerView.Adapter<IntegralShopAdapter.IntegralShopViewHolder> {
    private Context context;
    private OnItemClickListener listener;
    private SparseArray<OrderstateCountDownTimer> countDownMap;
    List<IntegralShop.DataBean.ObjBean> data;
    public IntegralShopAdapter(Context context, List<IntegralShop.DataBean.ObjBean> data) {
        this.context=context;
        this.data=data;
        countDownMap = new SparseArray<>();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    @NonNull
    @Override
    public IntegralShopAdapter.IntegralShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.intergral_shop_list_item, parent, false);
        IntegralShopAdapter.IntegralShopViewHolder integralShopViewHolder = new IntegralShopAdapter.IntegralShopViewHolder(root);
        return integralShopViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IntegralShopAdapter.IntegralShopViewHolder holder, int position) {
        holder.shop_name.setText(data.get(position).getName());
        ImageLoaderUtils.loadImage(holder.iv,data.get(position).getMasterPic());
        //1.现金，2.积分，3，积分加现金
        if (data.get(position).getSellType()==1){
            holder.intergral_num_tv.setText(data.get(position).getPreferentialPrice()+"元");

        }else if (data.get(position).getSellType()==2){
            holder.intergral_num_tv.setText(data.get(position).getPreferentialIntegral()+"积分");
        }else {
            holder.intergral_num_tv.setText(data.get(position).getPreferentialPrice()+"元+"+data.get(position).getPreferentialIntegral()+"积分");
        }
        holder.tv_yj.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
        if (data.get(position).getMoneyPrice().doubleValue()>0&&data.get(position).getPreferentialPrice().doubleValue()>0){
            if (data.get(position).getMoneyPrice()==data.get(position).getPreferentialPrice()){
                holder.tv_yj.setVisibility(View.GONE);
            }else {
                holder.tv_yj.setVisibility(View.VISIBLE);
                    holder.tv_yj.setText("原价:"+data.get(position).getMoneyPrice()+"元");
            }
        }

        holder.tv_kc.setText("库存"+data.get(position).getRepertory());
        holder.tv_buy.setText("马上抢");
        holder.tv_buy.setBackgroundResource(R.mipmap.mashangqiang);
        if (data.get(position).getRepertory()==0){
            holder.tv_buy.setText("售罄");
            holder.tv_buy.setBackgroundResource(R.mipmap.shouqing_bg);
        }
        //将前一个缓存清除
        if (holder.countDownTimer != null) {
            holder.countDownTimer.cancel();
        }

        if ( !ListUtils.isNullOrZeroLenght(data.get(position).getEndTime())) {

            if (ListUtils.get_time_difference(System.currentTimeMillis(),data.get(position).getEndTime())!=0){
                holder.countDownTimer = new OrderstateCountDownTimer(ListUtils.get_time_difference(System.currentTimeMillis(),data.get(position).getEndTime()), 1000,
                        holder.tv_hous,holder.tv_minute,holder.tv_second, holder.tv_days) {
                    @Override
                    public void onFinish() {
                        holder.tv_hous.setText("00");
                        holder.tv_minute.setText("00");
                        holder.tv_second.setText("00");
                        holder.tv_days.setText("00");
                       holder.tv_buy.setText("售罄");
                        holder.tv_buy.setBackgroundResource(R.mipmap.shouqing_bg);
                    }
                };
                holder.countDownTimer.start();
                countDownMap.put(holder.rl_time.hashCode(),  holder.countDownTimer);

            }else {
               holder.tv_buy.setText("售罄");
                holder.tv_buy.setBackgroundResource(R.mipmap.shouqing_bg);
            }


        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null)
                if ((ListUtils.get_time_difference(System.currentTimeMillis(), data.get(position).getEndTime()))==0){
                    return;
                }
                if (data.get(position).getRepertory()==0){
                    return;
                }
                        listener.onItemClick(holder.itemView, holder.getLayoutPosition());

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class IntegralShopViewHolder extends RecyclerView.ViewHolder{
        TextView shop_name;
        TextView intergral_num_tv,tv_yj;
        TextView tv_kc;
        TextView tv_buy;
        TextView tv_hous,tv_minute,tv_second,tv_days;
//        RelativeLayout rl_buy;
        RelativeLayout rl_time;
        ImageView iv;
        public OrderstateCountDownTimer countDownTimer;
        public IntegralShopViewHolder(View itemView) {
            super(itemView);
            shop_name=itemView.findViewById(R.id.shop_name);
            intergral_num_tv=itemView.findViewById(R.id.intergral_num_tv);
            tv_kc=itemView.findViewById(R.id.tv_kc);
            tv_buy=itemView.findViewById(R.id.tv_buy);
            tv_hous=itemView.findViewById(R.id.tv_hous);
            tv_minute=itemView.findViewById(R.id.tv_minute);
            tv_second=itemView.findViewById(R.id.tv_second);
//            rl_buy=itemView.findViewById(R.id.rl_buy);
            rl_time=itemView.findViewById(R.id.rl_time);
            tv_days=itemView.findViewById(R.id.tv_days);
            iv=itemView.findViewById(R.id.iv);
            tv_yj=itemView.findViewById(R.id.tv_yj);

        }
    }
}
