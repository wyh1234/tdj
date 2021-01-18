package cn.com.taodaji.model.entity;


import com.base.annotation.OnlyField;

/**
 * Created by yangkuo on 2018-09-21.
 */
public class OrderListTop {

    private String lastName;
    private String customerLogo;
    private String statusCode;
    @OnlyField
    private String orderNo;
    private String communityShortName;
    private String  fullRegionAndShippingLineCode;

    public String getFullRegionAndShippingLineCode() {
        return fullRegionAndShippingLineCode;
    }

    public void setFullRegionAndShippingLineCode(String fullRegionAndShippingLineCode) {
        this.fullRegionAndShippingLineCode = fullRegionAndShippingLineCode;
    }

    public String getCommunityShortName() {
        return communityShortName;
    }

    public void setCommunityShortName(String communityShortName) {
        this.communityShortName = communityShortName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCustomerLogo() {
        return customerLogo;
    }

    public void setCustomerLogo(String customerLogo) {
        this.customerLogo = customerLogo;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
