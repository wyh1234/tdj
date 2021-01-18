package cn.com.taodaji.ui.activity.scanner;

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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.ScannerFeeList;


public class ScannerFeeListAdapter  extends RecyclerView.Adapter<ScannerFeeListAdapter.ScannerFeeListViewHolder>{
    private Context context;
    private List<ScannerFeeList.DataBean.ItemsBean>  listBeans;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ScannerFeeListAdapter(Context context, List<ScannerFeeList.DataBean.ItemsBean> listBeans) {
        this.context = context;
        this.listBeans = listBeans;

    }
    @NonNull
    @Override
    public ScannerFeeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.scanner_fee_item, parent, false);
        ScannerFeeListViewHolder scannerFeeListViewHolder = new ScannerFeeListViewHolder(root);
        return scannerFeeListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScannerFeeListViewHolder holder, int position) {
        if (listBeans.get(position).getPrice().compareTo(BigDecimal.ZERO)>0){
            holder.tv_orderNo.setText("-"+listBeans.get(position).getPrice()+"元");
        }else {
            holder.tv_orderNo.setText(listBeans.get(position).getPrice()+"元");
        }

//            holder.tv_money.setText("-"+listBeans.get(position).getPrice()+"元");
        if (listBeans.get(position).getType()==4){
          holder.tv_content.setText("扫码入库严重超时");
        }else if (listBeans.get(position).getType()==1){
            holder.tv_content.setText("扫码入库正常免收费");
        }else if (listBeans.get(position).getType()==2){
            holder.tv_content.setText("扫码入库人工正常收费");

        }else if (listBeans.get(position).getType()==3){
            holder.tv_content.setText("扫码入库延时");
        }else if (listBeans.get(position).getType()==5){
            holder.tv_content.setText("扫码入库严重超时额外扣费");
        }else if (listBeans.get(position).getType()==6){
            holder.tv_content.setText("扫码入库免收费");
        }else if (listBeans.get(position).getType()==7){
            holder.tv_content.setText("扫码入库取消收费");
        }
        holder.tv_date.setText(getTimes(new Date(listBeans.get(position).getCreateTime()))+"");
//        ImageLoaderUtils.loadImage(holder.iv,listBeans.get(position).getPic_url());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view,position);
            }
        });
    }
    public static String getTimes(Date date) {//
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
    @Override
    public int getItemCount() {
        return listBeans.size();
    }

    class ScannerFeeListViewHolder extends RecyclerView.ViewHolder {
        TextView tv_orderNo,tv_content,tv_date;
        ImageView iv;
        public ScannerFeeListViewHolder(View itemView) {
            super(itemView);

            tv_orderNo=itemView.findViewById(R.id.tv_orderNo);
//            tv_money=itemView.findViewById(R.id.tv_money);
            tv_content=itemView.findViewById(R.id.tv_content);
            tv_date=itemView.findViewById(R.id.tv_date);
            iv=itemView.findViewById(R.id.iv);
        }
    }
}
