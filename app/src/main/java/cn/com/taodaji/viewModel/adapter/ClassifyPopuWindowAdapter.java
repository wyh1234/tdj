package cn.com.taodaji.viewModel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.ClassifyPopuWindowInfo;

public class ClassifyPopuWindowAdapter extends RecyclerView.Adapter {

    //定义三种常量  表示三种条目类型
    public static final int TYPE_ONE = 0;
    public static final int TYPE_TWO = 1;
    private  List<ClassifyPopuWindowInfo> data;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemOneClickListener(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ClassifyPopuWindowAdapter(List<ClassifyPopuWindowInfo> list_second, Context context) {
        this.data = list_second;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        if (viewType == TYPE_ONE) {
            view = View.inflate(context, R.layout.one_item, null);
            return new OneItemHolder(view);

        } else {
            view = View.inflate(context, R.layout.two_item, null);
            return new TwoItemHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof OneItemHolder) {
            ((OneItemHolder) viewHolder).tv_name_one.setText(data.get(i).getCategoryName());

            if (i==0){
                ((OneItemHolder) viewHolder).rl_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemOneClickListener(viewHolder.itemView, viewHolder.getLayoutPosition());

                    }
                });
            }


        } else {
            ((TwoItemHolder) viewHolder).tv_name_two.setText(data.get(i).getCategoryName());

            ((TwoItemHolder) viewHolder).tv_name_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemOneClickListener(viewHolder.itemView, viewHolder.getLayoutPosition());

                }
            });

        }

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
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
                            return 2;
                        case TYPE_TWO:
                            return 1;
                        default:
                            return 3;
                    }
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    //根据条件返回条目的类型
    @Override
    public int getItemViewType(int position) {
        ClassifyPopuWindowInfo classifyPopuWindowInfo = data.get(position);
        if (classifyPopuWindowInfo.getType() == 1) {
            return TYPE_ONE;
        } else {
            return TYPE_TWO;
        }
    }


    /**
     * 创建三种ViewHolder
     */
    class OneItemHolder extends RecyclerView.ViewHolder {
        TextView tv_name_one;
        RelativeLayout rl_one;
        public OneItemHolder(View itemView) {
            super(itemView);
            tv_name_one = itemView.findViewById(R.id.tv_name_one);
            rl_one=itemView.findViewById(R.id.rl_one);
        }
    }

    class TwoItemHolder extends RecyclerView.ViewHolder {
        TextView tv_name_two;

        public TwoItemHolder(View itemView) {
            super(itemView);
            tv_name_two = itemView.findViewById(R.id.tv_name_two);
        }
    }
}
