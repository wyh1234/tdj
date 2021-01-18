package cn.com.taodaji.viewModel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.model.entity.CommodityLabel;
import cn.com.taodaji.ui.fragment.MyselfShopManagement;

public class GoodsPropertiesAdapter extends RecyclerView.Adapter<GoodsPropertiesAdapter.ViewHolder> {
    private List<CommodityLabel.DataBean.ListBean> mItemList;
    private Context mContext;

    public GoodsPropertiesAdapter(List<CommodityLabel.DataBean.ListBean> mItemList, Context mContext) {
        this.mItemList = mItemList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_goods_properties,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        CommodityLabel.DataBean.ListBean item = mItemList.get(position);
        if (item.getRequired()==1){
            holder.title.setText("*"+item.getPropertyZhName());
        }else {
            holder.title.setText(item.getPropertyZhName());
        }
            holder.content.setText(item.getContent());

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,content;
        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tv_item_title);
            content = view.findViewById(R.id.tv_item_content);
            view.setOnClickListener(new View.OnClickListener() {
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

