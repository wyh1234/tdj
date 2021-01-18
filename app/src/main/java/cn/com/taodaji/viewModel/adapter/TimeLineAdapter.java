package cn.com.taodaji.viewModel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.taodaji.R;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.HorizontalVh> {

    private Context context;


    //时间节点数
    private int nodeNum = 0;

    //当前到达节点
    private int currentNode = 1;


    public TimeLineAdapter(Context context, int nodeNum) {
        this.context = context;
        this.nodeNum = nodeNum;
    }

    @Override
    public HorizontalVh onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.time_line, null, false);
        HorizontalVh vh = new HorizontalVh(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(HorizontalVh holder, int position) {
        if (position < currentNode) {
            //当前节点之前的全部设为已经经过
            holder.point.setImageResource(R.mipmap.notification_unread);
            holder.lineLeft.setBackgroundResource(R.color.red_f0);
            holder.lineRight.setBackgroundResource(R.color.red_f0);
            holder.showDate.setTextColor(context.getResources().getColor(R.color.red_f0));
        }
        // 去掉左右两头的分支
        if (position == 0) {
            holder.lineLeft.setVisibility(View.INVISIBLE);
        }else {
            holder.lineLeft.setVisibility(View.VISIBLE);
        }
        if (position == nodeNum - 1) {
            holder.lineRight.setVisibility(View.INVISIBLE);
        }else {
            holder.lineRight.setVisibility(View.VISIBLE);
        }
        holder.showDate.setText("第"+ ++position +"天");
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
        private TextView showDate;


        public HorizontalVh(View itemView) {
            super(itemView);
            this.point = (ImageView) itemView.findViewById(R.id.point);
            this.lineLeft = itemView.findViewById(R.id.line_left);
            this.lineRight = itemView.findViewById(R.id.line_right);
            this.showDate = itemView.findViewById(R.id.show_time);
        }
    }
}
