package cn.com.taodaji.viewModel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.FlagshipStroeInfo;

public class FlagshipStroeAdapter  extends RecyclerView.Adapter<FlagshipStroeAdapter.FlagshipStroeViewHolder>{
    private Context context;
    private List<FlagshipStroeInfo> list;

    public FlagshipStroeAdapter(Context context, List<FlagshipStroeInfo> list){
        this.context=context;
        this.list=list;

    }
    @NonNull
    @Override
    public FlagshipStroeAdapter.FlagshipStroeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.flagship_stroe_item, parent, false);
        FlagshipStroeAdapter.FlagshipStroeViewHolder flagshipStroeViewHolder=new  FlagshipStroeAdapter.FlagshipStroeViewHolder(root);
        return flagshipStroeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FlagshipStroeAdapter.FlagshipStroeViewHolder holder, int position) {
        holder.tv.setText(list.get(position).getName());

        holder.tv1.setText(list.get(position).getCont());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class FlagshipStroeViewHolder extends RecyclerView.ViewHolder{
        TextView tv,tv1;

        public FlagshipStroeViewHolder(View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.tv);
            tv1=itemView.findViewById(R.id.tv1);
        }
    }
}
