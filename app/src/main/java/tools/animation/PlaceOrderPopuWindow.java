package tools.animation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.ClassifyPopuWindowInfo;
import cn.com.taodaji.model.sqlBean.CartGoodsBean;
import cn.com.taodaji.viewModel.adapter.ClassifyPopuWindowAdapter;
import razerdp.basepopup.BasePopupWindow;

import static android.support.v7.widget.RecyclerView.*;

public class PlaceOrderPopuWindow extends BasePopupWindow implements View.OnClickListener {
    private View popupView;
    private RecyclerView list_reamk;
    private TextView tv_total,tv_cnnal,tv_btn;
    private PlaceOrderPopuWindowListener listener;
    public void setPlaceOrderPopuWindowListener(PlaceOrderPopuWindowListener listener) {
        this.listener = listener;
    }
    public interface PlaceOrderPopuWindowListener {
        void button_1(View v);

    }
    public PlaceOrderPopuWindow(Context context,List<CartGoodsBean> list_remak_bean,View v) {
        super(context);
        list_reamk = popupView.findViewById(R.id.list_reamk);
        tv_total= popupView.findViewById(R.id.tv_total);
        tv_cnnal= popupView.findViewById(R.id.tv_cnnal);
        tv_btn= popupView.findViewById(R.id.tv_btn);
        tv_cnnal.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        tv_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.button_1(v);
            }
        });
        tv_total.setText("共备注商品"+list_remak_bean.size()+"个");
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);//预租房
        list_reamk.setLayoutManager(linearLayoutManager1);

        list_reamk.setAdapter(new PlaceOrderPopoWindowAdapter(context,list_remak_bean));
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected Animation initShowAnimation() {
        return null;
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    public View onCreatePopupView() {
        popupView = createPopupById(R.layout.place_order_popu_item);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }

    class PlaceOrderPopoWindowAdapter extends RecyclerView.Adapter<PlaceOrderPopoWindowAdapter.PlaceOrderPopoWindowViewHolder>{
        private Context context;
        private List<CartGoodsBean> list_remak_bean;
        public PlaceOrderPopoWindowAdapter(Context context, List<CartGoodsBean> list_remak_bean){
            this.context=context;
            this.list_remak_bean=list_remak_bean;
        }

        @NonNull
        @Override
        public PlaceOrderPopoWindowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View root = LayoutInflater.from(context).inflate(R.layout.place_order_item, parent, false);
            PlaceOrderPopoWindowViewHolder placeOrderPopoWindowViewHolder = new PlaceOrderPopoWindowViewHolder(root);
            return placeOrderPopoWindowViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull PlaceOrderPopoWindowViewHolder holder, int position) {
            holder.tv_name.setText("名称："+list_remak_bean.get(position).getProductName()+"("+list_remak_bean.get(position).getNickName()+")");
            holder.tv_remak.setText("备注："+list_remak_bean.get(position).getRemark());
            holder.tv_pice.setText("价格："+list_remak_bean.get(position).getProductPrice()+"元/"+list_remak_bean.get(position).getAvgUnit()+"X"+
                    list_remak_bean.get(position).getProductQty());
        }

        @Override
        public int getItemCount() {
            return list_remak_bean.size();
        }

       class PlaceOrderPopoWindowViewHolder extends ViewHolder{
            TextView tv_name;
           TextView tv_pice,tv_remak;

           public PlaceOrderPopoWindowViewHolder(View itemView) {
               super(itemView);
               tv_name=itemView.findViewById(R.id.tv_name);
               tv_remak=itemView.findViewById(R.id.tv_remak);
               tv_pice=itemView.findViewById(R.id.tv_pice);

           }
       }
    }




  }
