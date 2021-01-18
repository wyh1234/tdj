package cn.com.taodaji.viewModel.vm;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.utils.JavaMethod;
import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;
import com.zyinux.specialstring.relase.SpecialStringBuilder;

import java.math.BigDecimal;

import cn.com.taodaji.R;
import cn.com.taodaji.common.Constants;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.GoodsInformation;
import cn.com.taodaji.model.entity.OrderDetail;
import cn.com.taodaji.model.sqlBean.CartGoodsBean;
import tools.extend.SimpleStringUtils;

import static cn.com.taodaji.common.Constants.goodsNameLength;

public class OrderPlaceGoodsDetailVM extends BaseVM {
    @Override
    protected void addOnclick() {
        if (PublicCache.loginPurchase != null) {
            putViewOnClick(R.id.item_view);
            putViewOnClick(R.id.tv_go_back_money);}
        else putViewOnClick(R.id.order_print_goods);

        putViewOnClick(R.id.after_sales);
    }

    @Override
    protected void dataBinding() {
        putRelation(R.id.goods_image, "image");
//        putRelation(R.id.goods_name, "name");
        putRelation(R.id.goods_unit, "unit");
        putRelation(R.id.unit, "unit");
        putRelation(R.id.goods_price, "price");
        putRelation(R.id.cart_price, "totalPrice");
        putRelation(R.id.order_no, "qrCodeId");
        putRelation(R.id.level2Value, "level2Value");
        putRelation(R.id.level2Unit, "level2Unit");
        putRelation(R.id.level3Value, "level3Value");
        putRelation(R.id.level3Unit, "level3Unit");


        putRelation(R.id.tv_isP, "isP");
        putRelation("amount", R.id.goods_count_x);

        putRelation("avgUnit", R.id.goods_unit2);
        putRelation_more("levelType");
        putRelation_more("name", "nickName","isP","productType","productCriteria");
       // putRelation_more("productCriteria", "productType");


        putRelation("packageName", R.id.tv_cash_pledge_name);
        putRelation("packageStatus", R.id.tv_textView1); //押金状态
        putRelation("foregift", R.id.tv_cash_pledge_price);//押金单价
        putRelation("packageNum", R.id.tv_cash_pledge_count);//包装数量
        putRelation("packageFee", R.id.tv_cash_pledge_sum);//订购或未退押金金额
        putRelation("isForegift", R.id.ll_cash_pledge); // 是否收取押金 0-不收，1-收

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
          /*  case R.id.tv_isP:
                if (!"".equals(value.toString())) {
                    int isp = JavaMethod.transformClass(int.class, value);
                    if (isp == 0) view.setVisibility(View.GONE);
                    else view.setVisibility(View.VISIBLE);
                }
                break;*/
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

        } else super.setValue(textView, value);
    }

    @Override
    public <T> void setValue(BaseViewHolder holder, String pamstr, @NonNull T bean) {
        super.setValue(holder, pamstr, bean);

        if (holder.getListBean() instanceof OrderDetail.ItemsBean) {
            OrderDetail.ItemsBean goodsBean = (OrderDetail.ItemsBean) holder.getListBean();
            if (pamstr.equals("levelType")) {
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
                        holder.setText(R.id.goods_count, goodsBean.getAmount());
                    } else if (type == 2) {
                        holder.setText(R.id.goods_count, goodsBean.getLevel2Value().multiply(goodsBean.getAmount()));
                    } else if (type == 3) {
                        holder.setText(R.id.goods_count, goodsBean.getLevel3Value().multiply(goodsBean.getLevel2Value().multiply(goodsBean.getAmount())));
                    }
                } else {
                    if (type == 2) {
                        holder.setText(R.id.goods_count, goodsBean.getAmount());
                    } else if (type == 3) {
                        holder.setText(R.id.goods_count, goodsBean.getLevel2Value().multiply(goodsBean.getAmount()));
                    }
                }
            } else if (pamstr.equals("name")) {
                TextView goods_name_txt = holder.findViewById(R.id.goods_name);
                //(String name,String nickName,int productType,int productCriteria,int isP)
                SpecialStringBuilder sb=SimpleStringUtils.getTitleName(goodsBean.getName(),goodsBean.getNickName(),goodsBean.getProductType(),goodsBean.getProductCriteria(),goodsBean.getIsP());
                goods_name_txt.setText(sb.getCharSequence());

                holder.setVisibility(R.id.textView_21, View.GONE);
                holder.setVisibility(R.id.goods_nickName, View.GONE);
                holder.setVisibility(R.id.textView_22, View.GONE);

              /*  String goods_name = goodsBean.getName();
                if (!TextUtils.isEmpty(goodsBean.getNickName())) {
                    goods_name = goodsBean.getName() + "(" + goodsBean.getNickName() + ")";
                }
                holder.setText(R.id.goods_name, goods_name);

                holder.setVisibility(R.id.textView_21, View.GONE);
                holder.setVisibility(R.id.goods_nickName, View.GONE);
                holder.setVisibility(R.id.textView_22, View.GONE);*/

            }
        }
    }


    @Override
    public void setValues(BaseViewHolder holder, String pamstr, Object value) {
        if (pamstr.equals("amount")) {
            holder.setText(R.id.goods_count_x, "X" + value.toString());
        } else {
            super.setValues(holder, pamstr, value);
        }
    }


}
