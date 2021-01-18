package cn.com.taodaji.model.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.math.BigDecimal;

import cn.com.taodaji.viewModel.adapter.PickupOrderDetailAdapter;

public class PickUpGoods implements MultiItemEntity {

    private String shopName;
    private String orderNo;
    private String priceUnit;
    private int goodsCount;
    private BigDecimal pickupFee;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public BigDecimal getPickupFee() {
        return pickupFee;
    }

    public void setPickupFee(BigDecimal pickupFee) {
        this.pickupFee = pickupFee;
    }

    @Override
    public int getItemType() {
        return PickupOrderDetailAdapter.TYPE_PERSON;
    }
}
