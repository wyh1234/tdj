package cn.com.taodaji.model.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.math.BigDecimal;

import cn.com.taodaji.viewModel.adapter.PickupOrderDetailAdapter;

public class PickUpDetail extends AbstractExpandableItem<PickUpGoods> implements MultiItemEntity{

    private String name;
    private String shortName;
    private BigDecimal total;
    private int orderCount;
    private BigDecimal pickupFee;
    private BigDecimal receiveFee;
    private String url;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public BigDecimal getPickupFee() {
        return pickupFee;
    }

    public void setPickupFee(BigDecimal pickupFee) {
        this.pickupFee = pickupFee;
    }

    public BigDecimal getReceiveFee() {
        return receiveFee;
    }

    public void setReceiveFee(BigDecimal receiveFee) {
        this.receiveFee = receiveFee;
    }

    @Override
    public int getItemType() {
        return PickupOrderDetailAdapter.TYPE_LEVEL_1;
    }

    @Override
    public int getLevel() {
        return 1;
    }
}
