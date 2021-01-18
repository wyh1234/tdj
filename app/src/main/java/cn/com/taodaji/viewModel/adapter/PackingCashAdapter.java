package cn.com.taodaji.viewModel.adapter;


import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;

import cn.com.taodaji.R;

import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.sqlBean.CartGoodsBean;
import cn.com.taodaji.viewModel.vm.PackingCashCarGoodsBeanVM;

public class PackingCashAdapter extends SingleRecyclerViewAdapter {
    private int type;

    public void setType(int type) {
        this.type = type;
    }
    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return ViewUtils.getFragmentView(parent, R.layout.item_packing_cash_list);
    }

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new PackingCashCarGoodsBeanVM());
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
       // String[] title = {"处理中押金", "未退押金","已支付押金", "已退押金"}; 1 2 3 4


        //orderStatusNum;//订单数字状态  0 不可退押，1-可点击退押
        switch(type){
            case 1 :
                holder.setText(R.id.tv_top_data_type,"申请时间：");
                holder.setVisibility(R.id.tv_go_back_money,View.VISIBLE);
                holder.setText(R.id.tv_go_back_money,"进度查询");
                holder.setVisibility(R.id.tv_cash_speed,View.VISIBLE);
            break;
            case 2 :{
                holder.setText(R.id.tv_top_data_type,"下单时间：");
                holder.setVisibility(R.id.line_wait_day,View.VISIBLE);


              if (PublicCache.loginPurchase != null){
                    holder.setText(R.id.tv_go_back_money,"退押金");
                    holder.setVisibility(R.id.tv_go_back_money,View.VISIBLE);
                }else{
                    holder.setVisibility(R.id.tv_go_back_money,View.GONE);
                }
                break;}
            case 3 :
                holder.setText(R.id.tv_top_data_type,"下单时间：");
                break;
            case 4 :
                holder.setText(R.id.tv_top_data_type,"下单时间：");
                holder.setVisibility(R.id.tv_go_back_money,View.VISIBLE);
                holder.setText(R.id.tv_go_back_money,"进度查询");
                holder.setVisibility(R.id.line_wait_day,View.VISIBLE);
                break;
        }

    }


    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        super.onBindViewHolderCustomer(holder, position);

        CartGoodsBean bean = (CartGoodsBean) getListBean(position);
         if (bean == null) return;
        if (type==1) {
            holder.setText(R.id.tv_data,bean.getApplyTime());
        }else {
            holder.setText(R.id.tv_data,bean.getPayTime());
        }

       // holder.setText(R.id.tv_supply_name,bean.getStoreName());
        if (type==2||type==4) {
            holder.setText(R.id.tv_day,bean.getDayDiff());
        }
        TextView btn_go=holder.findViewById(R.id.tv_go_back_money);
        if (bean.getCurrentStatus()==0) {//当前押金列表请求状态  0-未退，1-处理中，2-已退，3-已支付
            if (bean.getOrderStatusNum()==1) {//订单数字状态  0 不可退押，1-可点击退押
                btn_go.setTextColor(UIUtils.getColor(R.color.white));//
                btn_go.setBackgroundResource(R.drawable.r_round_rect_solid_orange_ff7d01);
            }else {
                btn_go.setTextColor(UIUtils.getColor(R.color.gray_6a));
                btn_go.setBackgroundResource(R.drawable.r_round_rect_solid_white);
            }
        }

        holder.setVisibility(R.id.tv_cash_speed,View.GONE);
        int applyStatus =bean.getApplyStatus();
        if (bean.getCurrentStatus()==1) {//当前押金列表请求状态  0-未退，1-处理中，2-已退，3-已支付
            holder.setVisibility(R.id.tv_cash_speed,View.VISIBLE);
            String str="";
            switch(applyStatus){
                case 1 :
                    str="申请退押";
                    break;
                case 2 :
                    str="取货中";
                    break;
                case 3 :
                    str="等待认领";
                    break;
                case 4 :
                    str="已退";
                    break;
                case 5 :
                    str="拒绝退";
                    break;
                case 6 :
                    str="撤销";
                    break;
            }
            holder.setText(R.id.tv_cash_speed,str);
        }
    }
}
