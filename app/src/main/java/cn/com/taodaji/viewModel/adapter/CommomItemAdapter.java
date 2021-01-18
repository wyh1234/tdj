package cn.com.taodaji.viewModel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.utils.ADInfo;
import com.base.utils.JavaMethod;

import java.util.List;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;

public class CommomItemAdapter extends RecyclerView.Adapter<CommomItemAdapter.ViewHolder> {
    private List<ADInfo> mItemList;
    private Context mContext;

    public CommomItemAdapter(List<ADInfo> mItemList, Context mContext) {
        this.mItemList = mItemList;
        this.mContext = mContext;
    }

    public CommomItemAdapter() {
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_commom_icon, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ADInfo item = mItemList.get(position);
        holder.itemTitle.setText(item.getImageName());
        if (item.getGoodsCount()==0){
            holder.itemNum.setVisibility(View.INVISIBLE);
        }else {
            holder.itemNum.setText(item.getGoodsCount()+"");
        }
        holder.itemIcon.setImageResource(item.getImageResource());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle, itemNum;
        ImageView itemIcon;
        public ViewHolder(View view) {
            super(view);
            itemTitle = view.findViewById(R.id.image_name);
            itemNum = view.findViewById(R.id.count_image);
            itemIcon = view.findViewById(R.id.image);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null)
                        itemClickListener.onItemClick(view, getLayoutPosition());
                }
            });
        }
    }

    public void update(int position, Map<String, Object> map) {
        if (map.size() > 0) {
            JavaMethod.transMap2Bean(mItemList.get(position), map);
            notifyItemChanged(position, map);
        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public List<ADInfo> getList() {
        return mItemList;
    }

    public void setList(List<ADInfo> mItemList) {
        this.mItemList = mItemList;
    }
}