package cn.com.taodaji.viewModel.adapter;

import android.content.Context;
import android.support.design.widget.CheckableImageButton;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigDecimal;

import cn.com.taodaji.R;
import cn.com.taodaji.model.sqlBean.CartGoodsBean;
import cn.com.taodaji.viewModel.vm.goods.CartGoodsVM;

import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.BaseViewHolder;
import com.base.utils.ViewUtils;
import com.zyinux.specialstring.relase.SpecialStringBuilder;

import tools.extend.SimpleStringUtils;
import tools.shopping_anim.ShoppingButtonNew;

import static cn.com.taodaji.common.Constants.goodsNameLength;


public class SimpleCartAdapter extends SingleRecyclerViewAdapter {

    private AlertDialog alertDialog;
    private Context context;

    public SimpleCartAdapter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new CartGoodsVM());
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {

        return ViewUtils.getFragmentView(parent, R.layout.item_cart);
    }

    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        CartGoodsBean bean = (CartGoodsBean) getListBean(position);
        if (bean == null) return;
//        String name = bean.getProductName();
//        int nameL = name == null ? 0 : name.length();
//        String ss = bean.getNickName();
//        if (ss == null) ss = "";
//        int niceName = ss.length();
//
//        if (nameL >= goodsNameLength) ss = "";
//        else if (nameL + niceName > goodsNameLength) {
//            ss = ss.substring(0, goodsNameLength - nameL) + "..";
//        }
//        TextView textView = holder.findViewById(R.id.goods_nickName);
//        if (textView != null) textView.setText(ss);
        //别名的()
//        View textView21 = holder.findViewById(R.id.textView_21);
//        View textView22 = holder.findViewById(R.id.textView_22);
//        if (TextUtils.isEmpty(ss)) {
//            if (textView21 != null) textView21.setVisibility(View.GONE);
//            if (textView22 != null) textView22.setVisibility(View.GONE);
//        } else {
//            if (textView21 != null) textView21.setVisibility(View.VISIBLE);
//            if (textView22 != null) textView22.setVisibility(View.VISIBLE);
//        }

        TextView goods_name_txt = holder.findViewById(R.id.goods_name);
        //(String name,String nickName,int productType,int productCriteria,int isP)
        SpecialStringBuilder sb=SimpleStringUtils.getTitleName(bean.getProductName(),bean.getNickName(),bean.getProductType(),bean.getProductCriteria(),bean.getIsP());
        goods_name_txt.setText(sb.getCharSequence());
        holder.setVisibility(R.id.textView_21, View.GONE);
        holder.setVisibility(R.id.goods_nickName, View.GONE);
        holder.setVisibility(R.id.textView_22, View.GONE);


        holder.setText(R.id.tv_textView1,"订购");
        //购买数量
        TextView goods_sum_price = holder.findViewById(R.id.goods_sum_price);
        goods_sum_price.setText(String.valueOf(bean.getProductPrice().multiply(new BigDecimal(bean.getProductQty()))));
        ShoppingButtonNew bt_shopping = holder.findViewById(R.id.bt_shopping);
        bt_shopping.setGoodsCount(bean.getProductQty());
        bt_shopping.setCartGoodsBean(bean);
        bt_shopping.setShowDeleteAlertDialog(true);
        //库存预警
        TextView stock_help = holder.findViewById(R.id.stock_help);
        if (bean.getStock() < bean.getProductQty()) {
            stock_help.setVisibility(View.VISIBLE);
            stock_help.setText("最大可购数量" + bean.getStock());
        } else {
            stock_help.setText("");
            stock_help.setVisibility(View.GONE);
        }

        //失效
        CheckableImageButton imageView = holder.findViewById(R.id.item_select);

        View invild_text = holder.findViewById(R.id.invalid_text);
        View line_total_price = holder.findViewById(R.id.line_total_price);

        if (imageView == null) return;
        if (bean.getStatus() != 1 || bean.getStoreStatus() != 0) {
            imageView.setImageResource(R.mipmap.icon_invalid);
            imageView.setBackgroundResource(0);
            line_total_price.setVisibility(View.GONE);
            invild_text.setVisibility(View.VISIBLE);
            stock_help.setVisibility(View.GONE);
        } else {
            imageView.setImageResource(0);
            imageView.setBackgroundResource(R.drawable.s_select_round);
            line_total_price.setVisibility(View.VISIBLE);
            invild_text.setVisibility(View.GONE);
        }
        //
        //特价活动，限购数量
        //促销
        /*if (bean.getTypeXg() == 1) {
            holder.setVisibility(R.id.special_offer, View.VISIBLE);
            if (bean.getCountXg() > 100) {
                holder.setVisibility(R.id.line_xq, View.GONE);
            } else {
                holder.setVisibility(R.id.line_xq, View.VISIBLE);
            }

        } else {
            holder.setVisibility(R.id.line_xq, View.GONE);
            holder.setVisibility(R.id.special_offer, View.GONE);
        }
        //精品
        View img_hot_sale = holder.findViewById(R.id.img_hot_sale);
        if (bean.getTypeXg() == 3) {
            img_hot_sale.setVisibility(View.VISIBLE);
        } else {
            img_hot_sale.setVisibility(View.GONE);
        }
        holder.setVisibility(R.id.jinpin, View.VISIBLE);

        //商品标准“1”：通货商品 “2”：精品商品 '
        if (bean.getProductCriteria()==2 ) {
            holder.setImageRes(R.id.jinpin,R.mipmap.icon_jin_red);
        }else{
            holder.setImageRes(R.id.jinpin,R.mipmap.icon_tong_blue);
        }*/

        //多规格
        if (bean.getLevelType() == 1) {
            holder.setVisibility(R.id.level3Value, View.GONE);
            holder.setVisibility(R.id.specification_split, View.GONE);
            holder.setVisibility(R.id.level3Unit, View.GONE);

            holder.setVisibility(R.id.level2Value, View.GONE);
            holder.setVisibility(R.id.level2Unit, View.GONE);

            holder.setVisibility(R.id.textView1, View.GONE);
            holder.setVisibility(R.id.textView2, View.GONE);
        } else if (bean.getLevelType() == 2) {
            holder.setVisibility(R.id.level2Value, View.VISIBLE);
            holder.setVisibility(R.id.level2Unit, View.VISIBLE);

            holder.setVisibility(R.id.textView1, View.VISIBLE);
            holder.setVisibility(R.id.textView2, View.VISIBLE);

            holder.setVisibility(R.id.level3Value, View.GONE);
            holder.setVisibility(R.id.specification_split, View.GONE);
            holder.setVisibility(R.id.level3Unit, View.GONE);

        } else {


            holder.setVisibility(R.id.level3Value, View.VISIBLE);
            holder.setVisibility(R.id.specification_split, View.VISIBLE);
            holder.setVisibility(R.id.level3Unit, View.VISIBLE);

            holder.setVisibility(R.id.level2Value, View.VISIBLE);
            holder.setVisibility(R.id.level2Unit, View.VISIBLE);

            holder.setVisibility(R.id.textView1, View.VISIBLE);
            holder.setVisibility(R.id.textView2, View.VISIBLE);
        }
    }
}
