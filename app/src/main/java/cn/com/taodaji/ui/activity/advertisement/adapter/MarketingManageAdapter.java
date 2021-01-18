package cn.com.taodaji.ui.activity.advertisement.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.glide.ImageLoaderUtils;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.MarketingManage;

public class MarketingManageAdapter extends RecyclerView.Adapter<MarketingManageAdapter.MarketingManageViewHolder> {
    private Context context;
    private List<MarketingManage.DataBean> data;
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public MarketingManageAdapter(Context context, List<MarketingManage.DataBean> data) {
        this.context=context;
        this.data=data;
    }
    @NonNull
    @Override
    public MarketingManageAdapter.MarketingManageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.marketing_manage_item, parent, false);
        MarketingManageViewHolder marketingManageViewHolder = new MarketingManageViewHolder(root);
        return marketingManageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MarketingManageAdapter.MarketingManageViewHolder holder, int position) {
        ImageLoaderUtils.loadImage(holder.iv,data.get(position).getBg_url());
        holder.tv_title.setText(data.get(position).getName());
        holder.tv_content.setText(data.get(position).getRemark());
        holder.tv_desc.setText("什么是"+data.get(position).getName()+"？");
        if (data.get(position).getStage_type()==1){
            holder.tv_money.setText("￥"+data.get(position).getPackageList().get(0).getPrice()+"元/期");
            holder.tv_ulit.setVisibility(View.VISIBLE);
            holder.tv_ulit.setText(data.get(position).getPackageList().get(0).getStage()+"期"+data.get(position).getPackageList().get(0).getDays()+"天");
        }else {
            holder.tv_money.setText("￥"+data.get(position).getPackageList().get(0).getPrice()+"元/天");
            holder.tv_ulit.setVisibility(View.GONE);
        }

        if (listener!=null){
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(view,position);
                }
            });
            holder.tv_desc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(view,position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class MarketingManageViewHolder extends RecyclerView.ViewHolder{
        Button btn;
        TextView tv_title,tv_content,tv_money,tv_ulit,tv_desc;
        ImageView iv;

        public MarketingManageViewHolder(View itemView) {
            super(itemView);
            btn=itemView.findViewById(R.id.btn);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_content=itemView.findViewById(R.id.tv_content);
            tv_money=itemView.findViewById(R.id.tv_money);
            tv_ulit=itemView.findViewById(R.id.tv_ulit);
            iv=itemView.findViewById(R.id.iv);
            tv_desc=itemView.findViewById(R.id.tv_desc);
        }
    }
}
