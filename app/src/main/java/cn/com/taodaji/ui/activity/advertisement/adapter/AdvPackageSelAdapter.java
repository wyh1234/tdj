package cn.com.taodaji.ui.activity.advertisement.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.AdvPackage;
import cn.com.taodaji.model.entity.MarketingManage;

public class AdvPackageSelAdapter extends RecyclerView.Adapter<AdvPackageSelAdapter.AdvPackageSelViewHolder>{
    private List<MarketingManage.DataBean.PackageListBean> advPackageList;
    private Context context;
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public AdvPackageSelAdapter(Context context, List<MarketingManage.DataBean.PackageListBean> advPackageList){
        this.advPackageList=advPackageList;
        this.context=context;

    }
    @NonNull
    @Override
    public AdvPackageSelAdapter.AdvPackageSelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.adv_package_sel_item, parent, false);
        AdvPackageSelViewHolder advPackageSelViewHolder = new AdvPackageSelViewHolder(root);
        return advPackageSelViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdvPackageSelAdapter.AdvPackageSelViewHolder holder, int position) {
        holder.tv_adv_title.setText(advPackageList.get(position).getStage()+"期"+advPackageList.get(position).getDays()+"天");
        if (advPackageList.get(position).getGift_days()>0){
            holder.tv_adv_desc.setVisibility(View.VISIBLE);
            holder.tv_adv_desc.setText("加送"+advPackageList.get(position).getGift_days()+"天（共"+(advPackageList.get(position).getGift_days()+
                    advPackageList.get(position).getDays())+"天)");
        }else {
            holder.tv_adv_desc.setVisibility(View.GONE);
        }
        holder.tv_pice.setText("￥"+advPackageList.get(position).getPrice());
        if (listener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(view,position);

                }
            });

        }
        holder.iv_sel.setSelected(advPackageList.get(position).isFlag());


    }

    @Override
    public int getItemCount() {
        return advPackageList.size();
    }
    class AdvPackageSelViewHolder extends RecyclerView.ViewHolder{
        TextView tv_adv_title,tv_adv_desc,tv_pice;
        ImageView iv_sel;

        public AdvPackageSelViewHolder(View itemView) {
            super(itemView);
            tv_adv_title=itemView.findViewById(R.id.tv_adv_title);
            tv_adv_desc=itemView.findViewById(R.id.tv_adv_desc);
            tv_pice=itemView.findViewById(R.id.tv_pice);
            iv_sel=itemView.findViewById(R.id.iv_sel);

        }
    }
}
