package cn.com.taodaji.ui.activity.advertisement.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.glide.ImageLoaderUtils;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.PayManage;

public class PayManageAdapter  extends RecyclerView.Adapter{
    public static final int TYPE_ONE = 0;
    public static final int TYPE_TWO = 1;
    private List<PayManage.DataBean> mData;

    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClickListener(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public PayManageAdapter(List<PayManage.DataBean> data, Context context) {
        this.mData = data;
        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_TWO) {
            view = View.inflate(context, R.layout.pay_manage_one_item, null);
            return new OneItemHolder(view);

        } else {
            view = View.inflate(context, R.layout.pay_manage_two_item, null);
            return new TwoItemHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OneItemHolder) {
            ImageLoaderUtils.loadImage(((OneItemHolder) holder).imageView,mData.get(position).getType_url());

        } else {
            ImageLoaderUtils.loadImage(((TwoItemHolder) holder).iv,mData.get(position).getItem_url());
            ((TwoItemHolder) holder).tv_title.setText(mData.get(position).getItem_name());
            ((TwoItemHolder) holder).tv_desc.setText(mData.get(position).getItem_content());
            if (mData.get(position).getItem_name().equals("平台年费")){
                ((TwoItemHolder) holder).tv_desc.setTextColor(context.getResources().getColor(R.color.pay_manage));
            }else {
                ((TwoItemHolder) holder).tv_desc.setTextColor(context.getResources().getColor(R.color.pay_manage_text));
            }
            ((TwoItemHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClickListener(view,position);
                }
            });
        }

    }
    @Override
    public void onAttachedToRecyclerView(@io.reactivex.annotations.NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    switch (type) {
                        case TYPE_ONE:
                            return 1;
                        case TYPE_TWO:
                            return 2;
                        default:
                            return 2;
                    }
                }
            });
        }


    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    //根据条件返回条目的类型
    @Override
    public int getItemViewType(int position) {
        PayManage.DataBean  dataBean= mData.get(position);
        if (dataBean.getType() == 2) {
            return TYPE_ONE;
        } else {
            return TYPE_TWO;
        }
    }


    /**
     * 创建三种ViewHolder
     */
    class OneItemHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public OneItemHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }

    class TwoItemHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv_title,tv_desc;

        public TwoItemHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_desc = itemView.findViewById(R.id.tv_desc);
        }
    }
}
