package cn.com.taodaji.viewModel.vm;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.utils.JavaMethod;
import com.base.utils.UIUtils;
import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.zyinux.specialstring.relase.SpecialStringBuilder;

import java.math.BigDecimal;

import cn.com.taodaji.R;
import cn.com.taodaji.common.Constants;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.sqlBean.CartGoodsBean;
import cn.com.taodaji.viewModel.adapter.SubmitOrderPlaceAdapter;
import tools.extend.SimpleStringUtils;


public class OrderPlaceGoodsVM extends BaseVM {
    private SingleRecyclerViewAdapter adapter;



    public OrderPlaceGoodsVM(SingleRecyclerViewAdapter adapter){
        this.adapter=adapter;

    }
    public OrderPlaceGoodsVM(){

    }

    //添加订单精品图标
    protected void dataBinding() {
        putRelation(R.id.goods_image, "productImage");
//        putRelation(R.id.goods_name, "productName");
        putRelation(R.id.level2Value, "level2Value");
        putRelation(R.id.level2Unit, "level2Unit");
        putRelation(R.id.level3Value, "level3Value");
        putRelation(R.id.level3Unit, "level3Unit");
        putRelation(R.id.tv_isP, "isP");

        putRelation("productUnit", R.id.goods_unit, R.id.unit);
        putRelation("avgUnit", R.id.goods_unit2);

        putRelation_more("productQty", "priceSum", "productPrice");
        putRelation_more("levelType");
//        putRelation_more("nickName");
        putRelation_more("productName", "nickName", "isP", "productType", "productCriteria");
//        putRelation_more("productCriteria", "productType");


        putRelation("packageStatus", R.id.tv_textView1); //押金状态

        putRelation("packageNum", R.id.tv_cash_pledge_count);//包装数量
        putRelation("packageFee", R.id.tv_cash_pledge_sum);//订购或未退押金金额

        putRelation("packageName", R.id.tv_cash_pledge_name);
        putRelation("foregift", R.id.tv_cash_pledge_price);//押金单价
        putRelation("isForegift", R.id.ll_cash_pledge); // 是否收取押金 0-不收，1-收
//        putRelation("remark", R.id.nz_rl); // 是否收取押金 0-不收，1-收
    }


    @Override
    protected void addOnclick() {
        putViewOnClick(R.id.after_sales);
        putViewOnClick(R.id.item_view);
    }


    @Override
    public <T> void setValues(BaseViewHolder viewHolder, @NonNull T bean) {
        super.setValues(viewHolder, bean);
        if (PublicCache.loginPurchase != null) {
            viewHolder.setImageRes(R.id.iv_cash_pledge, R.mipmap.ic_cash_pledge_pur);
        } else viewHolder.setImageRes(R.id.iv_cash_pledge, R.mipmap.ic_cash_pledge_sup);
    }

    @Override
    public void setValues(@NonNull View view, Object value) {
        switch (view.getId()) {
            case R.id.ll_cash_pledge:
                int ss = JavaMethod.transformClass(value, int.class);
                // 是否收取押金 0-不收，1-收
                if (ss == 0) view.setVisibility(View.GONE);
                else view.setVisibility(View.VISIBLE);
                break;

            case R.id.tv_isP:
                if (!"".equals(value.toString())) {
                    /*int isp = JavaMethod.transformClass(int.class, value);
                    if (isp == 0) view.setVisibility(View.GONE);
                    else view.setVisibility(View.VISIBLE);*/
                    view.setVisibility(View.GONE);
                }
                break;
            default:
                super.setValues(view, value);
        }
    }

    @Override
    public void setValue(@NonNull TextView textView, @NonNull Object value) {
        if (textView.getId() == R.id.tv_textView1) {
            int packageStatus = JavaMethod.transformClass(value, int.class);
            //押金状态  -1 订购，0 - 未退, 1-已退完
            switch (packageStatus) {
                case -1:
                    super.setValue(textView, "订购");
                    break;
                case 0:
                    super.setValue(textView, "未退");
                    break;
                default:
                    super.setValue(textView, "已退完");
                    break;
            }

        } else
            super.setValue(textView, value);
    }

    @Override
    public void setValue(BaseViewHolder holder, String pamstr, @NonNull Object bean) {
        super.setValue(holder, pamstr, bean);
        TextView tv_btn= holder.findViewById(R.id.tv_btn);
        if( adapter instanceof SubmitOrderPlaceAdapter){

            tv_btn.setVisibility(View.VISIBLE);
        }else {
            tv_btn.setVisibility(View.GONE);
        }


        if (bean instanceof CartGoodsBean) {
            CartGoodsBean goodsBean = (CartGoodsBean) bean;
            RelativeLayout nz_rl = holder.findViewById(R.id.nz_rl);
            TextView tv_remark = holder.findViewById(R.id.tv_remark);
            if (UIUtils.isNullOrZeroLenght(goodsBean.getRemark())){
                nz_rl.setVisibility(View.GONE);
                tv_btn.setText("添加备注");
            }else {
                nz_rl.setVisibility(View.VISIBLE);
                tv_btn.setText("修改备注");
                tv_remark.setText(goodsBean.getRemark());
            }

            switch (pamstr) {
                case "levelType":
                    int type = goodsBean.getLevelType();
                    //多规格
                    if (type == 1) {
                        holder.setVisibility(R.id.level3Value, View.GONE);
                        holder.setVisibility(R.id.specification_split, View.GONE);
                        holder.setVisibility(R.id.level3Unit, View.GONE);

                        holder.setVisibility(R.id.level2Value, View.GONE);
                        holder.setVisibility(R.id.level2Unit, View.GONE);

                        holder.setVisibility(R.id.textView1, View.GONE);
                        holder.setVisibility(R.id.textView2, View.GONE);
                    } else if (type == 2) {
                        holder.setVisibility(R.id.level3Value, View.GONE);
                        holder.setVisibility(R.id.specification_split, View.GONE);
                        holder.setVisibility(R.id.level3Unit, View.GONE);

                        //holder.setVisibility(R.id.tv_btn, View.VISIBLE);

                        holder.setVisibility(R.id.level2Value, View.VISIBLE);
                        holder.setVisibility(R.id.level2Unit, View.VISIBLE);

                        holder.setVisibility(R.id.textView1, View.VISIBLE);
                        holder.setVisibility(R.id.textView2, View.VISIBLE);
                    } else {
                        holder.setVisibility(R.id.level3Value, View.VISIBLE);
                        holder.setVisibility(R.id.specification_split, View.VISIBLE);
                        holder.setVisibility(R.id.level3Unit, View.VISIBLE);

                        holder.setVisibility(R.id.level2Value, View.VISIBLE);
                        holder.setVisibility(R.id.level2Unit, View.VISIBLE);

                        holder.setVisibility(R.id.textView1, View.VISIBLE);
                        holder.setVisibility(R.id.textView2, View.VISIBLE);
                    }


                    //如果均价单位是最小单
                    if (Constants.specification_unit_base.contains(goodsBean.getAvgUnit())) {
                        if (type == 1) {
                            holder.setText(R.id.goods_count, goodsBean.getProductQty());
                        } else if (type == 2) {
                            holder.setText(R.id.goods_count, goodsBean.getLevel2Value().multiply(BigDecimal.valueOf(goodsBean.getProductQty())));
                        } else if (type == 3) {
                            holder.setText(R.id.goods_count, goodsBean.getLevel3Value().multiply(goodsBean.getLevel2Value().multiply(BigDecimal.valueOf(goodsBean.getProductQty()))));
                        }
                    }
                    //均价单位是二级单位
                    else {
                        if (type == 2) {
                            holder.setText(R.id.goods_count, goodsBean.getProductQty());
                        } else if (type == 3) {
                            holder.setText(R.id.goods_count, goodsBean.getLevel2Value().multiply(BigDecimal.valueOf(goodsBean.getProductQty())));
                        }
                    }
                    break;
                case "productName":

                    TextView goods_name_txt = holder.findViewById(R.id.goods_name);
                    //(String name,String nickName,int productType,int productCriteria,int isP)
                    SpecialStringBuilder sb = SimpleStringUtils.getTitleName(goodsBean.getProductName(), goodsBean.getNickName(), goodsBean.getProductType(), goodsBean.getProductCriteria(), goodsBean.getIsP());
                    goods_name_txt.setText(sb.getCharSequence());

                    holder.setVisibility(R.id.textView_21, View.GONE);
                    holder.setVisibility(R.id.goods_nickName, View.GONE);
                    holder.setVisibility(R.id.textView_22, View.GONE);

                    break;
                case "productQty":
                    BigDecimal sum = goodsBean.getProductPrice().multiply(BigDecimal.valueOf(goodsBean.getProductQty()));
                    goodsBean.setPriceSum(sum);
                    holder.setText(R.id.goods_count_x, "X" + goodsBean.getProductQty());
                    holder.setText(R.id.cart_price, goodsBean.getPriceSum());
                    holder.setText(R.id.goods_price, goodsBean.getProductPrice());
                    break;
                case "productCriteria":

                    //促销
                    if (goodsBean.getProductType() == 1) {
                        holder.setVisibility(R.id.special_offer, View.VISIBLE);
                    } else {
                        holder.setVisibility(R.id.special_offer, View.GONE);
                    }
                    holder.setVisibility(R.id.img_hot_sale, View.GONE);
                    holder.setVisibility(R.id.jinpin, View.GONE);

                    if (goodsBean.getProductType() == 1 || goodsBean.getProductType() == 3) {
                        holder.setVisibility(R.id.img_ad, View.VISIBLE);
                    } else {
                        holder.setVisibility(R.id.img_ad, View.GONE);
                    }
                 /*   //促销
                    if (goodsBean.getProductType() == 1) {
                        holder.setVisibility(R.id.special_offer, View.VISIBLE);
                    } else {
                        holder.setVisibility(R.id.special_offer, View.GONE);
                    }
                    //是否是热销
                    View img_hot_sale = holder.findViewById(R.id.img_hot_sale);
                    if (goodsBean.getProductType() == 3) {
                        img_hot_sale.setVisibility(View.VISIBLE);
                    } else {
                        img_hot_sale.setVisibility(View.GONE);
                    }
                    holder.setVisibility(R.id.jinpin, View.VISIBLE);

                    //商品标准“1”：通货商品 “2”：精品商品 '
                    if (goodsBean.getProductCriteria() == 2) {
                        holder.setImageRes(R.id.jinpin, R.mipmap.icon_jin_red);
                    } else if (goodsBean.getProductCriteria() == 1) {
                        holder.setImageRes(R.id.jinpin, R.mipmap.icon_tong_blue);
                    } else {
                        holder.setVisibility(R.id.jinpin, View.GONE);
                    }*/
                    break;
            }

        }

    }


}
