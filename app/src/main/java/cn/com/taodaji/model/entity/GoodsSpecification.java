package cn.com.taodaji.model.entity;

import com.base.annotation.OnlyField;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2018-02-26.
 */

public class GoodsSpecification implements Serializable {

    /**
     * level2Value : -1
     * levelType : 1
     * stock : 999999
     * price : 10.9
     * avgPrice : 10.9
     * salesNumber : 68
     * level2Unit :
     * specId : 733
     * avgUnit : 壶
     * level3Unit :
     * level1Unit : 壶
     * level3Value : -1
     * productId : 949
     */

    private BigDecimal level2Value = BigDecimal.ZERO;
    private int levelType;
    private int stock;
    private BigDecimal price = BigDecimal.ZERO;
    private BigDecimal avgPrice = BigDecimal.ZERO;
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private int salesNumber;
    private String level2Unit;
    @OnlyField
    private int specId;
    private String avgUnit;
    private String level3Unit;
    private String level1Unit;
    private BigDecimal level3Value = BigDecimal.ZERO;
    private int productId;
    private String text;//文字  扩展
    private int siteId;

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public BigDecimal getLevel2Value() {
        return level2Value;
    }

    public void setLevel2Value(BigDecimal level2Value) {
        this.level2Value = level2Value;
    }

    public int getLevelType() {
        return levelType;
    }

    public void setLevelType(int levelType) {
        this.levelType = levelType;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }

    public int getSalesNumber() {
        return salesNumber;
    }

    public void setSalesNumber(int salesNumber) {
        this.salesNumber = salesNumber;
    }

    public String getLevel2Unit() {
        return level2Unit;
    }

    public void setLevel2Unit(String level2Unit) {
        this.level2Unit = level2Unit;
    }

    public int getSpecId() {
        return specId;
    }

    public void setSpecId(int specId) {
        this.specId = specId;
    }

    public String getAvgUnit() {
        return avgUnit;
    }

    public void setAvgUnit(String avgUnit) {
        this.avgUnit = avgUnit;
    }

    public String getLevel3Unit() {
        return level3Unit;
    }

    public void setLevel3Unit(String level3Unit) {
        this.level3Unit = level3Unit;
    }

    public String getLevel1Unit() {
        return level1Unit;
    }

    public void setLevel1Unit(String level1Unit) {
        this.level1Unit = level1Unit;
    }

    public BigDecimal getLevel3Value() {
        return level3Value;
    }

    public void setLevel3Value(BigDecimal level3Value) {
        this.level3Value = level3Value;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
