package cn.com.taodaji.viewModel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.math.BigDecimal;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.model.entity.GoodsInformation;
import cn.com.taodaji.model.entity.GoodsSpecification;

public class SearchGoodsAdapter extends RecyclerView.Adapter<SearchGoodsAdapter.ViewHolder> {
    private List<GoodsInformation> mItemList;
    private Context mContext;
    private int status = 0;

    public SearchGoodsAdapter(List<GoodsInformation> mItemList, Context mContext) {
        this.mItemList = mItemList;
        this.mContext = mContext;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search_goods,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        GoodsInformation item = mItemList.get(position);
        if (status==1){
            holder.select.setVisibility(View.INVISIBLE);
        }
        holder.itemTitle.setText(item.getName());
        holder.itemContent.setText("("+item.getNickName()+")");
        Glide.with(mContext).load(item.getImage()).into(holder.itemIcon);
        if (item.getProductCriteria()==1){
            Glide.with(mContext).load(R.mipmap.icon_tong_blue).into(holder.itemP);
        }else {
            Glide.with(mContext).load(R.mipmap.icon_jin_red).into(holder.itemP);
        }
        GoodsSpecification specification = item.getSpecs().get(0);
        switch (specification.getLevelType()) {
            case 1:
                holder.itemPrice.setText(specification.getPrice()+"元/"+specification.getLevel1Unit());
                break;
            case 2:
                holder.itemPrice.setText(specification.getPrice()+"元/"+specification.getLevel1Unit()+"("+specification.getLevel2Value()+specification.getLevel2Unit()+")");
                break;
            case 3:
                holder.itemPrice.setText(specification.getPrice()+"元/"+specification.getLevel1Unit()+"("+specification.getLevel2Value()+specification.getLevel2Unit()+"x"+specification.getLevel3Value()+specification.getLevel3Unit()+")");
                break;
            default:break;
        }
        if (item.getIsF()==1){
            holder.select.setChecked(true);
        }else {
            holder.select.setChecked(false);
        }

    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemIcon,itemP;
        TextView itemTitle,itemContent,itemPrice;
        CheckBox select;

        public ViewHolder(View view) {
            super(view);
            select = view.findViewById(R.id.cb_select_print);
            itemTitle = view.findViewById(R.id.tv_product_name);
            itemContent = view.findViewById(R.id.tv_product_content);
            itemPrice =view.findViewById(R.id.tv_product_price);
            itemIcon = view.findViewById(R.id.iv_product_icon);
            itemP = view.findViewById(R.id.iv_p);
            select.setOnClickListener(new View.OnClickListener() {
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
