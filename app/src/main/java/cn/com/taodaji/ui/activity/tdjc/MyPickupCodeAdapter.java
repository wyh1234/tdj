package cn.com.taodaji.ui.activity.tdjc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.model.entity.DeliveryCode;
import cn.com.taodaji.model.entity.MyPickupCode;

public class MyPickupCodeAdapter extends RecyclerView.Adapter<MyPickupCodeAdapter.MyPickupCodeViewHolder> {
    private List<DeliveryCode.DataBean.ListBean> mItemList;
    private Context mContext;
    public MyPickupCodeAdapter(Context context,List<DeliveryCode.DataBean.ListBean> ItemList){
        this.mContext=context;
        this.mItemList=ItemList;

    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    @NonNull
    @Override
    public MyPickupCodeAdapter.MyPickupCodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_my_pickup_code,parent,false);
        MyPickupCodeAdapter.MyPickupCodeViewHolder holder = new MyPickupCodeAdapter.MyPickupCodeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyPickupCodeAdapter.MyPickupCodeViewHolder holder, int position) {
        holder.rl_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null){
                    itemClickListener.onItemClick(view, holder.getLayoutPosition());
            }
            }
        });
        holder.tv_name.setText("自提点："+mItemList.get(position).getCommunityShortName());
        holder.tv_phone.setText("团长："+mItemList.get(position).getMasterPhone());
        holder.tv_time.setText("提货时间："+mItemList.get(position).getExpectDeliveredDate()+"("+mItemList.get(position).getExpectDeliveredEarliestTime()+"至"+
                mItemList.get(position).getExpectDeliveredLatestTime()+")" );
        holder.tv_address.setText("地址："+mItemList.get(position).getCity()+mItemList.get(position).getArea()+mItemList.get(position).getAddress());
        if (mItemList.get(position).getDeliveryType()==1){
            holder.tv_type.setText("自提");
        }else {
            holder.tv_type.setText("送货上门");
        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size();


    }

    public class MyPickupCodeViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rl_code;
        TextView tv_name,tv_type,tv_phone,tv_time,tv_address;

        public MyPickupCodeViewHolder(View itemView) {
            super(itemView);
            rl_code=itemView.findViewById(R.id.rl_code);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_phone=itemView.findViewById(R.id.tv_phone);
            tv_time=itemView.findViewById(R.id.tv_time);
            tv_address=itemView.findViewById(R.id.tv_address);
            tv_type=itemView.findViewById(R.id.tv_type);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null)
                        itemClickListener.onItemClick(view, getLayoutPosition());
                }
            });
        }
    }
}
