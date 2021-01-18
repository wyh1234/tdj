package cn.com.taodaji.ui.activity.integral.requsest;


import com.base.retrofit.HttpRetrofit;
import com.base.retrofit.RequestCallback;

import java.util.Map;

import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.AddressInfo;
import cn.com.taodaji.model.entity.AgainOrder;
import cn.com.taodaji.model.entity.BaseIntegral;
import cn.com.taodaji.model.entity.BuyIntegral;
import cn.com.taodaji.model.entity.CartQuantity;
import cn.com.taodaji.model.entity.CartQuantitys;
import cn.com.taodaji.model.entity.DuiBaRegisterUrl;
import cn.com.taodaji.model.entity.IntegralAliPay;
import cn.com.taodaji.model.entity.IntegralItem;
import cn.com.taodaji.model.entity.IntegralOrder;
import cn.com.taodaji.model.entity.IntegralShop;
import cn.com.taodaji.model.entity.IntegralShopCart;
import cn.com.taodaji.model.entity.IntegralShopMy;
import cn.com.taodaji.model.entity.IntegralWXPay;
import cn.com.taodaji.model.entity.Privilegeinfo;
import cn.com.taodaji.model.entity.QueryIntergral;
import cn.com.taodaji.model.entity.RedactAddress;
import cn.com.taodaji.model.entity.UserPrivilegeinfo;
import cn.com.taodaji.model.presenter.RequestService;

public class IntegralShopRequestPresenter {

    private RequestService requestService;//公共的请求
    private static IntegralShopRequestPresenter requestPresenter = null;
    public static IntegralShopRequestPresenter getInstance() {
        if (requestPresenter == null) {
            requestPresenter = new IntegralShopRequestPresenter();
        }
        return requestPresenter;
    }

    private IntegralShopRequestPresenter() {
        super();
        requestService = HttpRetrofit.getRetrofitApiServices(RequestService.class, PublicCache.getROOT_URL().get(2),PublicCache.getROOT_URL().get(0));
    }

    public void commodity_queryList( Map<String,Object> map, RequestCallback<IntegralShop> callback ) {
        requestService.commodity_queryList(PublicCache.site,map).enqueue(callback);
    }
    public void cart_queryList( Map<String,Object> map, RequestCallback<IntegralShopCart> callback ) {
        requestService.cart_queryList(map).enqueue(callback);
    }
    public void update_quantity( Map<String,Object> map, RequestCallback<CartQuantitys> callback ) {
        requestService.update_quantity(map).enqueue(callback);
    }
    public void delete_cart( Map<String,Object> map, RequestCallback<CartQuantitys> callback ) {
        requestService.delete_cart(map).enqueue(callback);
    }
    public void verifyUserIntegral( Map<String,Object> map, RequestCallback<CartQuantity> callback ) {
        requestService.verifyUserIntegral(map).enqueue(callback);
    }
    public void getDefaultAddress( Map<String,Object> map, RequestCallback<AddressInfo> callback ) {
        requestService.getDefaultAddress(map).enqueue(callback);
    }
    public void getAddress( Map<String,Object> map, RequestCallback<RedactAddress> callback ) {
        requestService.getAddress(map).enqueue(callback);
    }
    public void shipAddress_delete( Map<String,Object> map, RequestCallback<BaseIntegral> callback ) {
        requestService.shipAddress_delete(map).enqueue(callback);
    }
    public void shipAddress_update( Map<String,Object> map, RequestCallback<AddressInfo> callback ) {
        requestService.shipAddress_update(map).enqueue(callback);
    }
    public void shipAddress_add( Map<String,Object> map, RequestCallback<AddressInfo> callback ) {
        requestService.shipAddress_add(map).enqueue(callback);
    }
    public void order_create( Map<String,Object> map, RequestCallback<IntegralOrder> callback ) {
        requestService.order_create(map).enqueue(callback);
    }
    public void order_ali_pay( Map<String,Object> map, RequestCallback<IntegralAliPay> callback ) {
        requestService.order_ali_pay(map).enqueue(callback);
    }
    public void order_wx_pay(Map<String,Object> map, RequestCallback<IntegralWXPay> callback ) {
        requestService.order_wx_pay(map).enqueue(callback);
    }
    public void integral_pay(Map<String,Object> map, RequestCallback<IntegralOrder> callback ) {
        requestService.integral_pay(map).enqueue(callback);
    }
    public void orderQuery(Map<String,Object> map, RequestCallback<IntegralOrder> callback ) {
        requestService.orderQuery(map).enqueue(callback);
    }
    public void aliorderQuery(Map<String,Object> map, RequestCallback<IntegralOrder> callback ) {
        requestService.aliorderQuery(map).enqueue(callback);
    }

    public void getUserAndApproach(Map<String,Object> map, RequestCallback<IntegralShopMy> callback ) {
        requestService.getUserAndApproach(map).enqueue(callback);
    }
    public void getUserAndPrivilege(Map<String,Object> map, RequestCallback<UserPrivilegeinfo> callback ) {
        requestService.getUserAndPrivilege(map).enqueue(callback);
    }
    public void integral_signIn(Map<String,Object> map, RequestCallback<CartQuantity> callback ) {
        requestService.integral_signIn(map).enqueue(callback);
    }
    public void integral_item(Map<String,Object> map, RequestCallback<IntegralItem> callback ) {
        requestService.integral_item(map).enqueue(callback);
    }
    public void integral_queryInviteList(Map<String,Object> map, RequestCallback<IntegralItem> callback ) {
        requestService.integral_queryInviteList(map).enqueue(callback);
    }
    public void checkUser(Map<String,Object> map, RequestCallback<BaseIntegral> callback ) {
        requestService.checkUser(map).enqueue(callback);
    }
    public void query_intergral_list(Map<String,Object> map, RequestCallback<QueryIntergral> callback ) {
        requestService.query_intergral_list(map).enqueue(callback);
    }
    public void buyIntegral(Map<String,Object> map, RequestCallback<IntegralOrder> callback ) {
        requestService.buyIntegral(map).enqueue(callback);
    }
    public void order_again(Map<String,Object> map, RequestCallback<AgainOrder> callback ) {
        requestService.order_again(map).enqueue(callback);
    }
    public void getDuiBaRegisterUrl(Map<String,Object> map, RequestCallback<DuiBaRegisterUrl> callback ) {
        requestService.getDuiBaRegisterUrl(map).enqueue(callback);
    }
    public void getPartnerUser(Map<String,Object> map, RequestCallback<BuyIntegral> callback ) {
        requestService.getPartnerUser(map).enqueue(callback);
    }
    public void getShareDuibaRegisterUrl( Map<String,Object> map ,RequestCallback<DuiBaRegisterUrl> callback) {
        requestService.getShareDuibaRegisterUrl(map).enqueue(callback);
    }
}
