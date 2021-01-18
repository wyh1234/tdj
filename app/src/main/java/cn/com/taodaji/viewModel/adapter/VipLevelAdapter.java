package cn.com.taodaji.viewModel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.taodaji.R;

public class VipLevelAdapter extends RecyclerView.Adapter<VipLevelAdapter.HorizontalVh> {

    private Context context;

    //时间节点数
    private int nodeNum = 0;

    //当前到达节点
    private int currentNode = 0;


    public VipLevelAdapter(Context context, int nodeNum) {
        this.context = context;
        this.nodeNum = nodeNum;
    }

    @Override
    public VipLevelAdapter.HorizontalVh onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.vip_level, null, false);
        VipLevelAdapter.HorizontalVh vh = new VipLevelAdapter.HorizontalVh(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(VipLevelAdapter.HorizontalVh holder, int position) {
        if (position < currentNode) {
            //当前节点之前的全部设为已经经过
            holder.point.setBackgroundResource(R.color.red_f0);
            holder.lineLeft.setBackgroundResource(R.color.red_f0);
            holder.lineRight.setBackgroundResource(R.color.red_f0);
            holder.showDate.setTextColor(context.getResources().getColor(R.color.red_f0));
        }
        // 去掉左右两头的分支
        if (position == 0) {
            holder.lineLeft.setVisibility(View.GONE);
        }else {
            holder.lineLeft.setVisibility(View.VISIBLE);
        }
//        if (position == nodeNum - 1) {
//            holder.lineRight.setVisibility(View.INVISIBLE);
//        }
        switch (position){
            case 0:
                holder.showDate.setText("铜牌");
                break;
            case 1:
                holder.showDate.setText("银牌");
                break;
            case 2:
                holder.showDate.setText("金牌");
                break;
            case 3:
                holder.showDate.setText("钻石");
                break;
            case 4:
                holder.showDate.setText("至尊");
                break;
                default:break;
        }
        if (position==0){
            holder.name.setText(position+"");
        }else {
            holder.name.setText((position)*1000+"");
        }

    }

    @Override
    public int getItemCount() {
        return nodeNum;
    }

    /**
     * 设置当前节点
     * @param currentNode
     */
    public void setCurrentNode(int currentNode) {
        this.currentNode = currentNode;
        this.notifyDataSetChanged();
    }

    class HorizontalVh extends RecyclerView.ViewHolder {
        private ImageView point;

        private View lineLeft, lineRight;
        private TextView showDate,name;


        public HorizontalVh(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.tv_node_name);
            this.point = (ImageView) itemView.findViewById(R.id.point);
            this.lineLeft = itemView.findViewById(R.id.line_left);
            this.lineRight = itemView.findViewById(R.id.line_right);
            this.showDate = itemView.findViewById(R.id.show_time);
        }
    }
}

