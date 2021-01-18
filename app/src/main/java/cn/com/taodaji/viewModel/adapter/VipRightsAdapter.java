package cn.com.taodaji.viewModel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.glide.ImageLoaderUtils;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.model.entity.UserPrivilegeinfo;
import cn.com.taodaji.model.entity.VipRights;

public class VipRightsAdapter extends RecyclerView.Adapter<VipRightsAdapter.ViewHolder> {
    private List<UserPrivilegeinfo.DataBean.PrivilegesBean> mItemList;
    private Context mContext;

    public VipRightsAdapter(List<UserPrivilegeinfo.DataBean.PrivilegesBean> mItemList, Context mContext) {
        this.mItemList = mItemList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vip_rights,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        ImageLoaderUtils.loadImage( holder.itemIcon,mItemList.get(position).getMenuPic());
        holder.itemTitle.setText(mItemList.get(position).getTitle());
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemIcon;
        TextView itemTitle;

        public ViewHolder(View view) {
            super(view);
            itemIcon = view.findViewById(R.id.iv_item_icon);
            itemTitle = view.findViewById(R.id.tv_item_title);
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

