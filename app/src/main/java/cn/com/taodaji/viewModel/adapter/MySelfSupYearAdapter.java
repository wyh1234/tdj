package cn.com.taodaji.viewModel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.Privilegeinfo;

public class MySelfSupYearAdapter  extends RecyclerView.Adapter<MySelfSupYearAdapter.MySelfSupYearViewHolder> {
    private Context context;
    private List<Privilegeinfo> list;

    public MySelfSupYearAdapter(Context context, List<Privilegeinfo> list){
        this.context=context;
        this.list=list;

    }

    public void setList(List<Privilegeinfo> list1){
        this.list=list1;

    }
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void OnItemClick1(View view, int position);
    }


    @NonNull
    @Override
    public MySelfSupYearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.myselfsup_year_item, parent, false);
        MySelfSupYearViewHolder mySelfSupYearViewHolder=new MySelfSupYearViewHolder(root);

        return mySelfSupYearViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MySelfSupYearViewHolder holder, int position) {
        holder.tv.setText(list.get(position).getName());
        holder.iv.setImageResource(list.get(position).getImage());
        if (listener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int la = holder.getLayoutPosition();
                    listener.OnItemClick1(holder.itemView, la);

                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MySelfSupYearViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView tv;


        public MySelfSupYearViewHolder(View itemView) {
            super(itemView);
            iv=itemView.findViewById(R.id.iv);
            tv=itemView.findViewById(R.id.tv);
        }
    }

}
