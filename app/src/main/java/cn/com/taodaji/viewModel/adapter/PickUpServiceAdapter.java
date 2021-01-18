package cn.com.taodaji.viewModel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.utils.UIUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.model.entity.PickupServiceItem;

public class PickUpServiceAdapter extends RecyclerView.Adapter<PickUpServiceAdapter.ViewHolder> {
    private List<PickupServiceItem> mItemList;
    private Context mContext;

    public PickUpServiceAdapter(List<PickupServiceItem> mItemList, Context mContext) {
        this.mItemList = mItemList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pickup_service,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        PickupServiceItem item = mItemList.get(position);
        switch (item.getStatus()){
            case 0://已关闭
                holder.switchBtn.setVisibility(View.VISIBLE);
                holder.switchBtn.setBackgroundResource(R.drawable.r_round_rect_solid_gray_66);
                holder.switchBtn.setTextColor(mContext.getResources().getColor(R.color.white));
                holder.switchBtn.setText("已关闭");
                break;
            case 1://已开通
                holder.switchBtn.setVisibility(View.VISIBLE);
                holder.switchBtn.setBackgroundResource(R.drawable.round_rect_soild_green);
                holder.switchBtn.setTextColor(mContext.getResources().getColor(R.color.white));
                holder.switchBtn.setText("已开通");
                break;
            case 2:
            case 3:
                holder.switchBtn.setVisibility(View.VISIBLE);
                holder.switchBtn.setBackgroundResource(R.drawable.r_round_rect_orage_ff7d01);
                holder.switchBtn.setTextColor(mContext.getResources().getColor(R.color.orange_yellow_ff7e01));
                holder.switchBtn.setText("等待开通");
                break;
            case 4:
                holder.switchBtn.setVisibility(View.VISIBLE);
                holder.switchBtn.setBackgroundResource(R.drawable.round_rect_soild_red);
                holder.switchBtn.setTextColor(mContext.getResources().getColor(R.color.white));
                holder.switchBtn.setText("申请开通被驳回");
                break;
            case 5:
            case 6:
                holder.switchBtn.setVisibility(View.VISIBLE);
                holder.switchBtn.setBackgroundResource(R.drawable.z_round_rect_red_bg);
                holder.switchBtn.setTextColor(mContext.getResources().getColor(R.color.red_f0));
                holder.switchBtn.setText("等待关闭");
                break;
            case 7:
                holder.switchBtn.setVisibility(View.VISIBLE);
                holder.switchBtn.setBackgroundResource(R.drawable.round_rect_soild_red);
                holder.switchBtn.setTextColor(mContext.getResources().getColor(R.color.white));
                holder.switchBtn.setText("申请关闭被驳回");
                break;
            case 8:
            case 9:
                holder.switchBtn.setVisibility(View.VISIBLE);
                holder.switchBtn.setBackgroundResource(R.drawable.r_round_rect_orage_ff7d01);
                holder.switchBtn.setTextColor(mContext.getResources().getColor(R.color.orange_yellow_ff7e01));
                holder.switchBtn.setText("申请开通");
                break;
                default:break;
        }
        holder.itemTitle.setText(item.getTitle());
        holder.itemNum.setText(position+1+"");
        holder.itemGoods.setText(item.getGoods());
        holder.itemTime.setText(item.getTime());
        holder.itemPhone.setText(item.getPhone());
        holder.itemAddress.setText(item.getAddress());
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle,itemNum,itemGoods,itemTime,itemPhone,itemAddress,itemCall;
        Button switchBtn;
        ImageView badge,navi;


        public ViewHolder(View view) {
            super(view);
            itemTitle = view.findViewById(R.id.tv_item_title);
            itemNum = view.findViewById(R.id.tv_item_num);
            itemGoods = view.findViewById(R.id.tv_item_goods);
            itemTime = view.findViewById(R.id.tv_item_time);
            itemPhone = view.findViewById(R.id.tv_item_phone);
            itemAddress = view.findViewById(R.id.tv_address);
            itemCall = view.findViewById(R.id.tv_item_call);
            switchBtn = view.findViewById(R.id.btn_turn_on_service);
            badge = view.findViewById(R.id.tv_item_badge);
            navi = view.findViewById(R.id.iv_navi_icon);
            itemCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null)
                        itemClickListener.onItemClick(view, getLayoutPosition());
                }
            });
            navi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null)
                        itemClickListener.onItemClick(view, getLayoutPosition());
                }
            });
            switchBtn.setOnClickListener(new View.OnClickListener() {
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