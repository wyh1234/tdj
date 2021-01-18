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

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.PaymentList;

public class PaymentListAdapter extends RecyclerView.Adapter<PaymentListAdapter.PaymentListViewHolder>{

    private Context context;
    private List<PaymentList.DataBean.ListBean> listBeans;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public PaymentListAdapter(Context context, List<PaymentList.DataBean.ListBean> listBeans) {
        this.context = context;
        this.listBeans = listBeans;

    }

    @NonNull
    @Override
    public PaymentListAdapter.PaymentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.pay_ment_list_item, parent, false);
        PaymentListViewHolder paymentListViewHolder = new PaymentListViewHolder(root);
        return paymentListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentListAdapter.PaymentListViewHolder holder, int position) {
        holder.tv_orderNo.setText("订单号："+listBeans.get(position).getOrder_no());
        if (listBeans.get(position).getType()==4){
            holder.tv_money.setText(""+listBeans.get(position).getMoney().toString());

        }else {
            holder.tv_money.setText("-"+listBeans.get(position).getMoney().toString());

        }
        holder.tv_content.setText(listBeans.get(position).getContent());
        holder.tv_pay_way.setText(listBeans.get(position).getPayment_name());
        ImageLoaderUtils.loadImage(holder.iv,listBeans.get(position).getPic_url());
    }

    @Override
    public int getItemCount() {
        return listBeans.size();
    }
    class PaymentListViewHolder extends RecyclerView.ViewHolder{
        TextView tv_orderNo,tv_money,tv_content,tv_pay_way;
        ImageView iv;


        public PaymentListViewHolder(View itemView) {
            super(itemView);
            tv_orderNo=itemView.findViewById(R.id.tv_orderNo);
            tv_money=itemView.findViewById(R.id.tv_money);
            tv_content=itemView.findViewById(R.id.tv_content);
            tv_pay_way=itemView.findViewById(R.id.tv_pay_way);
            iv=itemView.findViewById(R.id.iv);
        }
    }
}
