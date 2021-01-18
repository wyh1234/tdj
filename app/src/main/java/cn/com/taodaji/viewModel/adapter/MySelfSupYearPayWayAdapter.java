package cn.com.taodaji.viewModel.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.SupplierAnnalFeeInfo;
import cn.com.taodaji.model.entity.YearPayWayInfo;

public class MySelfSupYearPayWayAdapter extends RecyclerView.Adapter<MySelfSupYearPayWayAdapter.MySelfSupYearPayWayViewHolder> {
    private Context context;
    List<SupplierAnnalFeeInfo.DataBean.ListBean> listBeans;
    private boolean b;
    private String type;
    private boolean disFlag;

    public boolean isDisFlag() {
        return disFlag;
    }

    public void setDisFlag(boolean disFlag) {
        this.disFlag = disFlag;
    }

    /*  public MySelfSupYearPayWayAdapter(Context context, List<YearPayWayInfo> yearPayWayInfoList ,boolean b){
            this.context=context;
            this.yearPayWayInfoList=yearPayWayInfoList;
            this.b=b;

        }*/
    public MySelfSupYearPayWayAdapter(Context context,List<SupplierAnnalFeeInfo.DataBean.ListBean> listBeans ,boolean b){
        this.context=context;
        this.listBeans=listBeans;
        this.b=b;

    }


    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void OnItemClick(View view, int position);
    }

    @NonNull
    @Override
    public MySelfSupYearPayWayAdapter.MySelfSupYearPayWayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.myselfsup_year_pay_way_item, parent, false);
    /*    root.setPadding(44, 0, 44, 0);
      int w = UIUtils.getScreenWidthPixels();
            LinearLayout linearLayout= root.findViewById(R.id.ll);
        int image_w = (w - 120) / 2;

        UIUtils.setViewSize(linearLayout, image_w, image_w);*/
        MySelfSupYearPayWayAdapter.MySelfSupYearPayWayViewHolder mySelfSupYearPayWayViewHolder=new MySelfSupYearPayWayAdapter.MySelfSupYearPayWayViewHolder(root);
        return mySelfSupYearPayWayViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MySelfSupYearPayWayAdapter.MySelfSupYearPayWayViewHolder holder, int position) {
        if (listBeans.get(position).isB()){
            holder.ll.setBackgroundResource(R.drawable.my_btn_bgs_one_item);
            holder.tv_num.setTextColor(context.getResources().getColor(R.color.white));
            holder.tv_dw.setTextColor(context.getResources().getColor(R.color.white));
            holder.tv_xj.setTextColor(context.getResources().getColor(R.color.white));
            holder.tv_x_money.setTextColor(context.getResources().getColor(R.color.white));
            holder.tv_yj.setTextColor(context.getResources().getColor(R.color.white));
            holder.tv_y_money.setTextColor(context.getResources().getColor(R.color.white));
        }else {
            holder.ll.setBackgroundResource(R.drawable.my_btn_bgs_item);
            holder.tv_num.setTextColor(context.getResources().getColor(R.color.statusBarColor));
            holder.tv_dw.setTextColor(context.getResources().getColor(R.color.statusBarColor));
            holder.tv_xj.setTextColor(context.getResources().getColor(R.color.statusBarColor));
            holder.tv_x_money.setTextColor(context.getResources().getColor(R.color.statusBarColor));
            holder.tv_yj.setTextColor(context.getResources().getColor(R.color.statusBarColor));
            holder.tv_y_money.setTextColor(context.getResources().getColor(R.color.statusBarColor));
        }
        if (getType().equals("ZM")){
            if (isDisFlag()){
                holder.tv_y_money.setText(String.format("%.2f",listBeans.get(position).getSpecDisAmount())+"元");

            }else {
                holder.tv_y_money.setText(String.format("%.2f",listBeans.get(position).getSpecAmount())+"元");
            }
        }else {
            if (isDisFlag()){
                holder.tv_y_money.setText(String.format("%.2f",listBeans.get(position).getVipDisAmount())+"元");

            }else {
                holder.tv_y_money.setText(String.format("%.2f",listBeans.get(position).getVipAmount())+"元");
            }
        }
        holder.tv_yj.setVisibility(View.GONE);

/*        if (b){
            holder.rl3.setVisibility(View.VISIBLE);
//            holder.tv_y_money.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
//            holder.tv_yj.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线

            if (getType().equals("ZM")){
                holder.tv_x_money.setText(listBeans.get(position).getSpecDisAmount()+"元");
            }else {
                holder.tv_x_money.setText(listBeans.get(position).getVipDisAmount()+"元");
            }

            holder.tv_xj.setVisibility(View.GONE);
        }else {
            if (getType().equals("ZM")){
                holder.tv_y_money.setText(listBeans.get(position).getSpecAmount()+"元");
                holder.tv_x_money.setText("￥"+listBeans.get(position).getSpecDisAmount()+"元");
            }else {
                holder.tv_y_money.setText(listBeans.get(position).getVipAmount()+"元");
                holder.tv_x_money.setText("￥"+listBeans.get(position).getVipDisAmount()+"元");
            }

            holder.rl3.setVisibility(View.GONE);
            holder.tv_xj.setVisibility(View.GONE);
        }*/
        holder.tv_num.setText(listBeans.get(position).getOpenCycle()+"");



        if (listener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int la = holder.getLayoutPosition();
                    listener.OnItemClick(holder.itemView, la);

                }
            });
        }

    }

    public void setType(String type){
        this.type=type;

    }

    public String getType() {
        return type;
    }

    @Override
    public int getItemCount() {
        return listBeans.size();
    }

    public class MySelfSupYearPayWayViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll,rl3;
        private TextView tv_num;
        private TextView tv_dw;
        private TextView tv_xj;
        private TextView tv_x_money;
        private TextView tv_yj;
        private TextView tv_y_money;
        public MySelfSupYearPayWayViewHolder(View itemView) {
            super(itemView);
            ll=itemView.findViewById(R.id.ll);
            tv_num=itemView.findViewById(R.id.tv_num);
            tv_dw=itemView.findViewById(R.id.tv_dw);
            tv_xj=itemView.findViewById(R.id.tv_xj);
            tv_x_money=itemView.findViewById(R.id.tv_x_money);
            tv_yj=itemView.findViewById(R.id.tv_yj);
            tv_y_money=itemView.findViewById(R.id.tv_y_money);

            rl3=itemView.findViewById(R.id.rl3);
        }
    }
}