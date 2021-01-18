package com.base.entity;

import java.util.List;

/**
 * Created by yangkuo on 2018-09-14.
 */

public class PageBean<T> {

    /**
     * length : 10
     * pageNo : 1
     * totalRow : 3
     * from : 0
     * to : 10
     * recordList : [{"entityId":6,"siteId":2,"createTime":"2018-09-06 15:43:29","updateTime":"2018-09-06 15:43:29","type":1,"capitalChangeAmount":2.4,"capitalChangeReason":"供应商资金冻结","orderId":224747,"transactionNo":"20180906154323","orderCommissionAmount":0,"extOrderId":"6443372769232031744","orderFreezeStatus":"FREEZE","orderStatus":"wait_seller_confirm_goods","withdrawalTotalAmount":null,"withdrawalFeeAmount":null,"withdrawalActuralAmount":null,"withdrawalStatus":null,"afterSalesCustomerId":null,"afterSalesCustomerImg":null,"afterSalesTotalAmount":null,"afterSalesCommissionAmount":null,"afterSalesOrderItemId":null,"afterSalesQrCodeId":null,"afterSalesProductId":null,"productImg":null,"productName":null,"productNickName":null,"productDesc":null,"productPrice":null,"productUnit":null,"productAvgUnit":null,"productAvgPrice":null,"productDiscountAvgPrice":null,"specificationId":null,"level2Value":null,"level2Unit":null,"level3Value":null,"level3Unit":null,"levelType":null,"supplierUserId":44,"storeId":23,"storeName":"姐弟生姜大蒜批发","mobile":"18986399038","supplierFreezeAmount":8681.02,"supplierWithdrawalAmount":91,"supplierTotalAmount":8772.02,"remarks":null,"status":0,"tokenId":"d328596a-fd9d-4968-8134-dd5b7d1cd37f","year":"2018","month":"09","flag":"2018-09"},{"entityId":16,"siteId":2,"createTime":"2018-09-06 15:45:41","updateTime":"2018-09-06 15:45:41","type":1,"capitalChangeAmount":4.7,"capitalChangeReason":"供应商资金冻结","orderId":224757,"transactionNo":"20180906154525","orderCommissionAmount":0,"extOrderId":"6443373306316853248","orderFreezeStatus":"FREEZE","orderStatus":"wait_seller_confirm_goods","withdrawalTotalAmount":null,"withdrawalFeeAmount":null,"withdrawalActuralAmount":null,"withdrawalStatus":null,"afterSalesCustomerId":null,"afterSalesCustomerImg":null,"afterSalesTotalAmount":null,"afterSalesCommissionAmount":null,"afterSalesOrderItemId":null,"afterSalesQrCodeId":null,"afterSalesProductId":null,"productImg":null,"productName":null,"productNickName":null,"productDesc":null,"productPrice":null,"productUnit":null,"productAvgUnit":null,"productAvgPrice":null,"productDiscountAvgPrice":null,"specificationId":null,"level2Value":null,"level2Unit":null,"level3Value":null,"level3Unit":null,"levelType":null,"supplierUserId":44,"storeId":23,"storeName":"姐弟生姜大蒜批发","mobile":"18986399038","supplierFreezeAmount":8685.72,"supplierWithdrawalAmount":91,"supplierTotalAmount":8776.72,"remarks":null,"status":0,"tokenId":"f0305976-d107-41be-a7d9-81d3b07cec42","year":"2018","month":"09","flag":"2018-09"},{"entityId":17,"siteId":2,"createTime":"2018-09-06 17:53:09","updateTime":"2018-09-06 17:53:09","type":1,"capitalChangeAmount":4.8,"capitalChangeReason":"供应商资金解冻","orderId":223849,"transactionNo":"20180719104634","orderCommissionAmount":0,"extOrderId":"6425541155965898752","orderFreezeStatus":"UNFREEZE","orderStatus":"trade_finished","withdrawalTotalAmount":null,"withdrawalFeeAmount":null,"withdrawalActuralAmount":null,"withdrawalStatus":null,"afterSalesCustomerId":null,"afterSalesCustomerImg":null,"afterSalesTotalAmount":null,"afterSalesCommissionAmount":null,"afterSalesOrderItemId":null,"afterSalesQrCodeId":null,"afterSalesProductId":null,"productImg":null,"productName":null,"productNickName":null,"productDesc":null,"productPrice":null,"productUnit":null,"productAvgUnit":null,"productAvgPrice":null,"productDiscountAvgPrice":null,"specificationId":null,"level2Value":null,"level2Unit":null,"level3Value":null,"level3Unit":null,"levelType":null,"supplierUserId":44,"storeId":23,"storeName":"姐弟生姜大蒜批发","mobile":"","supplierFreezeAmount":8680.92,"supplierWithdrawalAmount":95.8,"supplierTotalAmount":8776.72,"remarks":null,"status":0,"tokenId":"804c5999-f3b2-438e-a5be-047dc98589d0","year":"2018","month":"09","flag":"2018-09"}]
     * needCount : true
     * totalPage : 1
     */

    private int length;
    private int pageNo;
    private int totalRow;
    private int from;
    private int to;
    private boolean needCount;
    private int totalPage;
    private List<T> recordList;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public boolean isNeedCount() {
        return needCount;
    }

    public void setNeedCount(boolean needCount) {
        this.needCount = needCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<T> recordList) {
        this.recordList = recordList;
    }
}

