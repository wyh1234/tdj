package cn.com.taodaji.model.presenter;

import com.base.retrofit.HttpRetrofit;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.AddCategory;
import cn.com.taodaji.model.entity.AddressUpdate;
import cn.com.taodaji.model.entity.ComplaintDetail;
import cn.com.taodaji.model.entity.DegreeOfSatisfaction;
import cn.com.taodaji.model.entity.EvaluationDetail;
import cn.com.taodaji.model.entity.HaltSaleProduct;
import cn.com.taodaji.model.entity.ModifyInventory;
import cn.com.taodaji.model.entity.NewsAndCount;
import cn.com.taodaji.model.entity.PickupFeeList;
import cn.com.taodaji.model.entity.PickupOrderDetail;
import cn.com.taodaji.model.entity.PickupPackage;
import cn.com.taodaji.model.entity.PunishmentMessage;
import cn.com.taodaji.model.entity.PunishmentReaded;
import cn.com.taodaji.model.entity.ReceiveFee;
import cn.com.taodaji.model.entity.RefreshPickupFee;
import cn.com.taodaji.model.entity.SupplyMoneyListBean;
import cn.com.taodaji.model.entity.SupplyMonthlyBillBean;

import com.base.retrofit.RequestCallback;

/**
 * Created by yangkuo on 2018-06-23.
 */
public class RequestPresenter2 {

    private RequestService requestService2;
    private static RequestPresenter2 requestPresenter2 = null;
    public static RequestPresenter2 getInstance() {
        if (requestPresenter2 == null) {
            requestPresenter2 = new RequestPresenter2();
        }
        return requestPresenter2;
    }

    public RequestPresenter2() {
        requestService2 = HttpRetrofit.getRetrofitApiService(RequestService.class, PublicCache.getROOT_URL().get(1));
    }

    //供应商新版（有余额）交易明细列表
    public void getSupplyMoneyListNew(int storeId, int pn, int ps, RequestCallback<SupplyMoneyListBean> callback) {
        requestService2.getSupplyMoneyListNew(PublicCache.site_login, storeId, pn, ps).enqueue(callback);
    }

    //供应商新版（有余额）交易明细筛选列表
    public void getSupplyMoneyListFilter(Map<String, Object> params, RequestCallback<SupplyMoneyListBean> callback) {
        requestService2.getSupplyMoneyListFilter(PublicCache.site_login, params).enqueue(callback);
    }

    //根据storeID查询出供应商月账单相关信息
    public void findPageMonthlyBill(int storeId, int year, int month, RequestCallback<SupplyMonthlyBillBean> callback) {
        requestService2.findPageMonthlyBill(storeId, year, month).enqueue(callback);
    }

    //查询某用户下是否有新公告，以及未读公告的数量
    public void getNewAndCount(int userId,RequestCallback<NewsAndCount> callback){
        requestService2.getNewAndCount(PublicCache.site_login,1,userId).enqueue(callback);
    }

    //查询某用户下处罚公告的的数量
    public void getPunishmentList(int userId,int receiverType,int pn, int ps, RequestCallback<PunishmentMessage> callback){
        requestService2.getPunishmentList(PublicCache.site_login,userId,receiverType,pn,ps).enqueue(callback);
    }

    //更改处罚公告状态到已读
    public void updatePunishmentStatus(int userId, int entityId, RequestCallback<PunishmentReaded> callback){
        requestService2.getPunishmentReaded(PublicCache.site_login,0,entityId,userId).enqueue(callback);
    }

    public void addEvaluation(String orderId,int customerId,int userId,int driverId,int evaType,String evaContent,int anonymous,int customerAddrId,String problemIdStr,RequestCallback<AddCategory> callback){
        requestService2.addEvaluation(orderId,customerId,userId,driverId,evaType,evaContent,anonymous,PublicCache.site_login,customerAddrId,problemIdStr).enqueue(callback);
    }

    public void findShippingEvaluationDetail(int driverId,String extOrderId,RequestCallback<EvaluationDetail> callback){
        requestService2.findShippingEvaluationDetail(PublicCache.site_login,driverId,extOrderId).enqueue(callback);
    }

    public void findComplaintDetail(int driverId, String extOrderId, RequestCallback<ComplaintDetail> callback){
        requestService2.findComplaintDetail(PublicCache.site_login,driverId,extOrderId).enqueue(callback);
    }
    public void getDegreeOfSatisfaction(int driverId,String extOrderId,RequestCallback<DegreeOfSatisfaction> callback){
        requestService2.getDegreeOfSatisfaction(PublicCache.site_login,driverId,extOrderId).enqueue(callback);
    }
    public void addComplaint(int driverId,String extOrderId,int type,String content,String img,int loginUserId,int customerId,RequestCallback<AddCategory> callback){
        requestService2.addComplaint(PublicCache.site_login,driverId,extOrderId,type,content,img,loginUserId,customerId).enqueue(callback);
    }

    //开通自动续费服务
    public void setAutomaticRenewal(int storeId, int flag, RequestCallback<AddressUpdate> callback){
        requestService2.setAutomaticRenewal(storeId,flag).enqueue(callback);
    }

    //设置自动续费的金额
    public void setAutomaticRenewalFee(int storeId,int flag, RequestCallback<AddressUpdate> callback){
        requestService2.setAutomaticRenewalFee(storeId,flag).enqueue(callback);
    }

    //获取接货包列表
    public void getPickupPackageList(RequestCallback<PickupPackage> callback){
        requestService2.getPickupPackageList(PublicCache.site).enqueue(callback);
    }

    //获取接货费明细列表
    public void getPickupFeeList(int storeId, int pn, int ps, RequestCallback<PickupFeeList> callback){
        requestService2.getPickupFeeList(storeId,pn,ps).enqueue(callback);
    }

    //接货费明细
    public void findReceiveStationFeeDetail(int storeId, Double fee , String date,int type,int id,int pn,int ps,RequestCallback<PickupOrderDetail> callback){
        requestService2.findReceiveStationFeeDetail(storeId,fee,date,type,id,pn,ps).enqueue(callback);
    }

    //根据商品ID查询出相关规格
    public void getSpecificationIdByEntityId(int productId,RequestCallback<ModifyInventory> callback){
        requestService2.getSpecificationIdByEntityId(PublicCache.site_login,productId).enqueue(callback);
    }

    //查询接货费余额
    public void refreshPickupFee(int storeId, RequestCallback<RefreshPickupFee> callback){
        requestService2.refreshPickupFee(storeId,PublicCache.site_login).enqueue(callback);
    }

}
