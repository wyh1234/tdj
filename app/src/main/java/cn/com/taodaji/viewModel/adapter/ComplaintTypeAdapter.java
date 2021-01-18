package cn.com.taodaji.viewModel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.model.entity.ProblemItem;

public class ComplaintTypeAdapter extends RecyclerView.Adapter<ComplaintTypeAdapter.ViewHolder> {
    private Context mContext;
    private List<ProblemItem> stringList = new ArrayList<>();

    public ComplaintTypeAdapter(Context mContext, List<ProblemItem> stringList) {
        this.mContext = mContext;
        this.stringList = stringList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_popup_bottom,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.itemTitle.setText(stringList.get(position).getText());
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle;

        public ViewHolder(View view) {
            super(view);
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
            return stringList.size();
    }
}

