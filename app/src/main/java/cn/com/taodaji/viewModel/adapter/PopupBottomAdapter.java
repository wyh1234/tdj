package cn.com.taodaji.viewModel.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.model.entity.SearchShop;

/**
 * Created by zhaowenlong on 2019/3/7.
 */
public class PopupBottomAdapter extends RecyclerView.Adapter<PopupBottomAdapter.ViewHolder> {
    private String[] mStrings;
    private Context mContext;
    private List<String> stringList = new ArrayList<>();

    public PopupBottomAdapter(String[] mStrings, Context mContext) {
        this.mStrings = mStrings;
        this.mContext = mContext;
    }

    public PopupBottomAdapter(Context mContext, List<String> stringList) {
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
        if (mStrings==null||mStrings.length==0){
            holder.itemTitle.setText(stringList.get(position));
        }else {
            if (mStrings[position].indexOf("(")>=0&&mStrings[position].indexOf(")")>0){
                SpannableString spannableString = new SpannableString(mStrings[position]);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF7D01")), mStrings[position].indexOf("("), mStrings[position].indexOf(")")+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.itemTitle.setText(spannableString);
            }else {
                holder.itemTitle.setText(mStrings[position]);
            }
        }

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
        if (mStrings==null||mStrings.length==0){
            return stringList.size();
        }
        else {
            return mStrings.length;
        }
    }
}
