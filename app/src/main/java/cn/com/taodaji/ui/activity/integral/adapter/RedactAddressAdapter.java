package cn.com.taodaji.ui.activity.integral.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.AddressInfo;
import cn.com.taodaji.model.entity.IntegralShopCart;
import cn.com.taodaji.model.entity.RedactAddress;

public class RedactAddressAdapter extends BaseRecyclerViewAdapter<RedactAddress.DataBean>  {
    private OnItemClickListener listener;
    public  RedactAddressAdapter(Context context, List<RedactAddress.DataBean> data) {
        super(context, data, R.layout.address_list_item);
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemSelClick(View view, int position);

    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    @Override
    protected void onBindData(RecyclerViewHolder holder, RedactAddress.DataBean bean, int position) {
        ((TextView)holder.getView(R.id.tv_name)).setText(bean.getRecevingPersion());
        ((TextView)holder.getView(R.id.tv_phone)).setText(bean.getRecevingMobile());
        ((TextView)holder.getView(R.id.tv_address)).setText(bean.getDetailAddress());
        ((ImageView)holder.getView(R.id.iv)).setSelected(bean.isaBoolean());
        if (bean.getIsDefault()==1){
            ((TextView)holder.getView(R.id.tv_status)).setVisibility(View.VISIBLE);
            ((TextView)holder.getView(R.id.tv_status)).setText("默认");
        }else if (bean.getIsDefault()==2){
            ((TextView)holder.getView(R.id.tv_status)).setVisibility(View.VISIBLE);
            ((TextView)holder.getView(R.id.tv_status)).setText("家");
        }else if (bean.getIsDefault()==3){
            ((TextView)holder.getView(R.id.tv_status)).setVisibility(View.VISIBLE);
            ((TextView)holder.getView(R.id.tv_status)).setText("公司");
        }else {
            ((TextView)holder.getView(R.id.tv_status)).setVisibility(View.GONE);
        }
        ((ImageView)holder.getView(R.id.iv_bj)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.onItemClick(holder.itemView, holder.getLayoutPosition());
                }
            }
        });
        ((ImageView)holder.getView(R.id.iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.onItemSelClick(holder.itemView, holder.getLayoutPosition());
                }
            }
        });



    }
}
