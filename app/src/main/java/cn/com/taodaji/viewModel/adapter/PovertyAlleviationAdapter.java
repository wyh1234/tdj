package cn.com.taodaji.viewModel.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.utils.ADInfo;
import com.base.utils.UIUtils;
import com.bumptech.glide.Glide;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;

public class PovertyAlleviationAdapter extends RecyclerView.Adapter<PovertyAlleviationAdapter.ViewHolder> {
    private List<ADInfo> mItemList;
    private Context mContext;

    public PovertyAlleviationAdapter(List<ADInfo> mItemList, Context mContext) {
        this.mItemList = mItemList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_poverty_alleviation,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        ADInfo item = mItemList.get(position);
        holder.title.setText(item.getImageName());
        holder.content.setText(item.getImageContent());
        Glide.with(mContext).load(item.getImageUrl()).into(holder.image);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title,content;

        public ViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.iv_ad_image);
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

