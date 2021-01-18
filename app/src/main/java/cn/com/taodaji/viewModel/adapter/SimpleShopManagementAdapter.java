package cn.com.taodaji.viewModel.adapter;


import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.com.taodaji.R;
import cn.com.taodaji.application.MyApplication;
import cn.com.taodaji.model.entity.GoodsInformation;
import cn.com.taodaji.model.entity.GoodsSpecification;
import cn.com.taodaji.model.entity.NameValue;
import cn.com.taodaji.model.entity.ProblemItem;
import cn.com.taodaji.viewModel.ProblemAdapter;
import cn.com.taodaji.viewModel.vm.goods.GoodsShopListVM;

import com.alibaba.fastjson.JSON;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.BaseViewHolder;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static cn.com.taodaji.common.Constants.goodsNameLength;


public class SimpleShopManagementAdapter extends SingleRecyclerViewAdapter {
    private int status;

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new GoodsShopListVM());
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        View view = ViewUtils.getFragmentView(parent, R.layout.item_shop_management_fragment);
        TextView shelves_up = view.findViewById(R.id.shelves_up);
        LinearLayout verifyInfo = view.findViewById(R.id.verifyInfo);
        TextView goods_delete = view.findViewById(R.id.goods_delete);
        TextView goods_edit = view.findViewById(R.id.goods_edit);
        TextView inventory_edit = view.findViewById(R.id.tv_inventory_edit);
        TextView price = view.findViewById(R.id.tv_price_edit);

        if (status == 1) {
            shelves_up.setText("下架");
            goods_delete.setVisibility(View.GONE);
            verifyInfo.setVisibility(View.GONE);
            inventory_edit.setVisibility(View.VISIBLE);
            price.setVisibility(View.VISIBLE);
        } else if (status == 2) {
            shelves_up.setText("上架");
            inventory_edit.setVisibility(View.VISIBLE);
            price.setVisibility(View.GONE);
        } else {
            shelves_up.setVisibility(View.GONE);
            goods_edit.setText("编辑重新提交");
            verifyInfo.setVisibility(View.VISIBLE);
            inventory_edit.setVisibility(View.GONE);
            price.setVisibility(View.GONE);
        }
        return view;
    }


    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        // super.onBindViewHolder(holder, position);

        GoodsInformation bean = (GoodsInformation) getListBean(position);
//        int nameL = bean.getName().length();
//        String ss = bean.getNickName();
//        int niceName = ss.length();
//
//        if (nameL >= goodsNameLength) ss = "";
//        else if (nameL + niceName > goodsNameLength) {
//            ss = ss.substring(0, goodsNameLength - nameL) + "..";
//        }
//        TextView goods_nickName = holder.findViewById(R.id.goods_nickName);
//        goods_nickName.setText(ss);

//        View textView21 = holder.findViewById(R.id.textView_21);
//        View textView22 = holder.findViewById(R.id.textView_22);
//        if (TextUtils.isEmpty(ss)) {
//            if (textView21 != null) textView21.setVisibility(View.GONE);
//            if (textView22 != null) textView22.setVisibility(View.GONE);
//        } else {
//            if (textView21 != null) textView21.setVisibility(View.VISIBLE);
//            if (textView22 != null) textView22.setVisibility(View.VISIBLE);
//        }
        if (status ==2){
            if (bean.getStatus()==100){
                holder.setText(R.id.verifyInfo_text, bean.getEndTime());
                holder.setVisibility(R.id.goods_delete, View.GONE);
                holder.setVisibility(R.id.view_reason, View.VISIBLE);
                holder.setVisibility(R.id.stop_goods,View.VISIBLE);
                holder.setVisibility(R.id.tv_inventory_edit,View.GONE);
                holder.setVisibility(R.id.goods_edit,View.GONE);
                holder.setVisibility(R.id.shelves_up,View.GONE);
                holder.setVisibility(R.id.verifyInfo_text,View.VISIBLE);
                holder.setText(R.id.verifyInfo_text, "恢复时间："+bean.getEndTime());
            }else {
                holder.setVisibility(R.id.goods_delete, View.VISIBLE);
                holder.setVisibility(R.id.view_reason, View.GONE);
                holder.setVisibility(R.id.stop_goods,View.GONE);
                holder.setVisibility(R.id.tv_inventory_edit,View.VISIBLE);
                holder.setVisibility(R.id.goods_edit,View.VISIBLE);
                holder.setVisibility(R.id.shelves_up,View.VISIBLE);
                holder.setVisibility(R.id.verifyInfo_text,View.GONE);
            }
        }


        if (status == 3) {
            if (bean.getAuthStatus() == 1) {
                holder.setText(R.id.verifyInfo_text, "正在审核中，请耐心等待。");
                holder.setVisibility(R.id.goods_delete, View.GONE);
//                holder.setText(R.id.goods_edit, "编辑商品");
            } else {
                holder.setText(R.id.verifyInfo_text, "审核失败原因：" + bean.getVerifyInfo());
//                holder.setText(R.id.goods_edit, "编辑重新提交");
                holder.setVisibility(R.id.goods_delete, View.VISIBLE);
            }
        }


        RecyclerView recyclerView = holder.findViewById(R.id.rv_goods_property);
        List<ProblemItem> itemList = new ArrayList<>();
        ProblemAdapter adapter = new ProblemAdapter(itemList, MyApplication.getApplication());
        List<NameValue> parseArray = JSON.parseArray(bean.getProductProperty(), NameValue.class);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        recyclerView.setAdapter(adapter);

        if (parseArray!=null) {
            for (NameValue value : parseArray) {
                ProblemItem item = new ProblemItem();
                item.setText(value.getPValue());
                itemList.add(item);
            }
            adapter.notifyDataSetChanged();
        }


      /*  //是否是精品蔬菜
        if (bean.getProductType() == 3) holder.setVisibility(R.id.img_hot_sale, View.VISIBLE);
        else holder.setVisibility(R.id.img_hot_sale, View.GONE);

        holder.setVisibility(R.id.jinpin, View.VISIBLE);

        //商品标准“1”：通货商品 “2”：精品商品 '
        if (bean.getProductCriteria()==2 ) {
            holder.setImageRes(R.id.jinpin,R.mipmap.icon_jin_red);
        }else{
            holder.setImageRes(R.id.jinpin,R.mipmap.icon_tong_blue);
        }


        //是否为促销商品
        if (bean.getProductType() == 1) {
            holder.setVisibility(R.id.special_offer, View.VISIBLE);
        } else {
            holder.setVisibility(R.id.special_offer, View.GONE);
        }
        //是否整件批
        if (bean.getIsP() == 1) {
            holder.setVisibility(R.id.tv_isP, View.VISIBLE);
        } else {
            holder.setVisibility(R.id.tv_isP, View.GONE);

        }
*/

        BigDecimal maxPrice = bean.getMaxPrice();
        if (bean.getSpecs() == null) return;
        //如果只有一个规格
        if (maxPrice.compareTo(new BigDecimal(-1)) == 0 && bean.getSpecs().size() > 0) {
            GoodsSpecification gsf = bean.getSpecs().get(0);
            if (gsf == null) return;
            if (gsf.getLevelType() == 3) {
                holder.setVisibility(R.id.goods_spec_1, View.VISIBLE);
                holder.setVisibility(R.id.specification_split, View.VISIBLE);
                holder.setText(R.id.goods_price, String.valueOf(gsf.getPrice()));
                holder.setText(R.id.goods_unit, String.valueOf(gsf.getLevel1Unit()));
                holder.setText(R.id.level2Value, String.valueOf(gsf.getLevel2Value()));
                holder.setText(R.id.level2Unit, String.valueOf(gsf.getLevel2Unit()));
                holder.setText(R.id.level3Value, String.valueOf(gsf.getLevel3Value()));
                holder.setText(R.id.level3Unit, String.valueOf(gsf.getLevel3Unit()));
                if (gsf.getAvgUnit().equals(gsf.getLevel3Unit())){
                    holder.setText(R.id.tv_inventory_number,"余量："+gsf.getStock()+gsf.getLevel1Unit());
                    holder.setText(R.id.tv_allowance_number,"共余："+gsf.getStock()*gsf.getLevel3Value().intValue()*gsf.getLevel2Value().floatValue()+gsf.getLevel3Unit());
                }else {
                    holder.setText(R.id.tv_inventory_number,"余量："+gsf.getStock()+gsf.getLevel1Unit());
                    holder.setText(R.id.tv_allowance_number,"共余："+gsf.getStock()*gsf.getLevel2Value().floatValue()+gsf.getAvgUnit());
                }
                holder.setVisibility(R.id.level3Value, View.VISIBLE);
                holder.setVisibility(R.id.level3Unit, View.VISIBLE);
                holder.setVisibility(R.id.goods_spec_more, View.GONE);
            } else if (gsf.getLevelType() == 2) {
                holder.setVisibility(R.id.goods_spec_1, View.VISIBLE);
                holder.setText(R.id.goods_price, String.valueOf(gsf.getPrice()));
                holder.setText(R.id.goods_unit, String.valueOf(gsf.getLevel1Unit()));
                holder.setText(R.id.level2Value, String.valueOf(gsf.getLevel2Value()));
                holder.setText(R.id.level2Unit, String.valueOf(gsf.getLevel2Unit()));
                if (gsf.getAvgUnit().equals(gsf.getLevel2Unit())){
                    holder.setText(R.id.tv_inventory_number,"余量："+gsf.getStock()+gsf.getLevel1Unit());
                    holder.setText(R.id.tv_allowance_number,"共余："+gsf.getStock()*gsf.getLevel2Value().floatValue()+gsf.getLevel2Unit());
                }else {
                    holder.setText(R.id.tv_inventory_number,"余量："+gsf.getStock()+gsf.getLevel1Unit());
                    holder.setText(R.id.tv_allowance_number,"共余："+gsf.getStock()+gsf.getAvgUnit());
                }
                holder.setVisibility(R.id.level3Value, View.GONE);
                holder.setVisibility(R.id.level3Unit, View.GONE);
                holder.setVisibility(R.id.specification_split, View.GONE);
                holder.setVisibility(R.id.goods_spec_more, View.GONE);
            } else {
                holder.setVisibility(R.id.goods_spec_more, View.GONE);
                holder.setVisibility(R.id.goods_spec_1, View.GONE);
                holder.setText(R.id.tv_inventory_number,"余量："+gsf.getStock()+gsf.getLevel1Unit());
            }

            bean.setStock(gsf.getStock());
        } else {
            holder.setVisibility(R.id.goods_spec_more, View.VISIBLE);
            holder.setVisibility(R.id.goods_spec_1, View.GONE);
            holder.setText(R.id.goods_max_price, String.valueOf(bean.getMaxPrice()));
            int stk = 10000;
            if (bean.getSpecs() == null) return;
            for (GoodsSpecification gs : bean.getSpecs()) {
                if (stk > gs.getStock()) {
                    stk = gs.getStock();
                }
            }
            bean.setStock(stk);
        }

        TextView stock = holder.findViewById(R.id.stock);
        TextView textView_4 = holder.findViewById(R.id.textView_4);
        if (bean.getStock() < 100) {
            stock.setTextColor(UIUtils.getColor(R.color.red_dark));
            textView_4.setTextColor(UIUtils.getColor(R.color.red_dark));
        } else {
            stock.setTextColor(UIUtils.getColor(R.color.gray_69));
            textView_4.setTextColor(UIUtils.getColor(R.color.gray_69));
        }

        if (bean.getStock() < 100 && status == 1) {
            holder.setVisibility(R.id.stock, View.VISIBLE);
        } else holder.setVisibility(R.id.stock, View.GONE);


        if (TextUtils.isEmpty(bean.getPackageName())) {
            holder.setText(R.id.tv_cash_pledge_price, "");
        } else holder.setText(R.id.tv_cash_pledge_price, "(另收押金" + bean.getForegift() + "元)");
    }

}
