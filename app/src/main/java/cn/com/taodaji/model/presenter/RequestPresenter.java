package cn.com.taodaji.model.presenter;


import android.text.TextUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.common.Constants;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.*;
import cn.com.taodaji.model.event.EmpoleeStoreList;
import cn.com.taodaji.model.event.FenceGid;
import cn.com.taodaji.model.event.OrderDeleteEvent;
import cn.com.taodaji.model.event.OrderStatusEvent;

import com.base.entity.ResultInfo;

import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;

import retrofit2.Call;
import retrofit2.Callback;

import com.base.retrofit.HttpRetrofit;
import com.base.retrofit.UploadImageRetrofit;
import com.base.utils.SystemUtils;

public class RequestPresenter extends RequestPresenter2 {

    private RequestService requestService;//公共的请求
    private static RequestPresenter requestPresenter = null;
    public static RequestPresenter getInstance() {
        if (requestPresenter == null) {
            requestPresenter = new RequestPresenter();
        }
        return requestPresenter;
    }

    private RequestPresenter() {
        super();
        requestService = HttpRetrofit.getRetrofitApiService(RequestService.class, PublicCache.getROOT_URL().get(0));
    }

    /**
     * 数据字典
     * <p>
     * 1、 查询出所有的值
     * <p>
     * url	方法	说明
     * /dict/findAll	GET	查询出所有的值
     * 参数说明
     * <p>
     * 参数	说明
     * customer_withdrawal_fee	采购商提现费用比例
     * count_down_time	倒计时间
     * is_start_limited_time	是否启用限制下单
     * order_start_time	当天开始下单时间 6:00
     * order_end_time	当天截止下单时间24:00
     * customer_delivery_time	采购商送达时间
     * supplier_delivery_time	供应商送达时间
     */
    public void dictFindAll(RequestCallback<DictFindAll> callback) {
        requestService.dictFindAll(PublicCache.site).enqueue(callback);
    }


    /**
     * 1、查询出该区域所有的专题活动
     * <p/>
     * url	方法	说明
     * /specialActivities/findAll	GET	查询出该区域所有的专题活动
     */
    public void specialActivities_findAll(int flag, ResultInfoCallback<SpecialActivities> callback) {
        requestService.specialActivities_findAll(PublicCache.site, flag,0).enqueue(callback);
    }

    /**
     * 2 店铺推荐  2实力商家
     * <p/>
     * URL	方法	说明
     * /store/recommend/:type	GET	首页实力商家和优秀店铺推荐
     * 请求参数
     * <p/>
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * type	int	1		1优秀店铺，2实力商家
     */
    public void store_recommend(int type, int pn, int ps, ResultInfoCallback<StoreRecommend> callback) {
        requestService.store_recommend(type, PublicCache.site, pn, ps).enqueue(callback);
    }

    /**
     * 4 分类推荐商品
     * <p/>
     * URL	方法	说明
     * /product/findCommendProduct/:categoryId	GET	根据分类ID查询推荐商品
     * 请求参数
     * <p/>
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * categoryId	int	1		商品分类
     */
    public void findCommendProduct(int categoryId, int pn, int ps, ResultInfoCallback<FindCommendProduct> callback) {
        requestService.findCommendProduct(categoryId, PublicCache.site, pn, ps).enqueue(callback);
    }

    /**
     * 根据活动的ID询出特价商品列表
     * <p/>
     * url	方法	说明
     * /specialActivitiesProducts/findByActivitiesID/:specialActivitiesId	GET	根据活动的ID询出特价商品列表
     * 请求参数
     * <p/>
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * :specialActivitiesId	int	1	活动的ID
     * pn	1	分页pn
     * ps	1	分页ps
     */
    public void findByActivitiesID(int specialActivitiesId, Map<String, Object> map, RequestCallback<FindByActivitiesID> callback) {
        requestService.findByActivitiesID(specialActivitiesId, map, PublicCache.site).enqueue(callback);
    }

    public void findByActivitiesIDs(int specialActivitiesId, Map<String, Object> map, RequestCallback<FindByActivitiesID> callback) {
        requestService.findByActivitiesIDs(specialActivitiesId, map, PublicCache.site).enqueue(callback);
    }

    //9宫格数据请求
    public void commendForHomePage(int pn, int ps, ResultInfoCallback<HomepageGridDatas> callback) {
        requestService.commendForHomePage(PublicCache.site, pn, ps).enqueue(callback);
    }

    /**
     * 获取一级分类
     * <p>
     * URL	方法	说明
     * /product/commendCategory	GET	首页一级分类
     * 请求参数
     * <p>
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * site	int	1		站点ID
     */
    public void commendCategory(RequestCallback<HomePageFuncationButton> callback) {
        requestService.commendCategory(PublicCache.site).enqueue(callback);
    }

    /**
     * 检查是否需要更新
     */
    public void androidUpdate(RequestCallback<AndroidUpdate> callback) {
        requestService.androidUpdate().enqueue(callback);
    }

    //图片上传
    public void upload(String fileName, byte[] imageByte, Callback<ImageUpload> callback) {
        requestService.upload(UploadImageRetrofit.getMultipartBody_part(fileName, imageByte)).enqueue(callback);
    }

    //门店注册时门店图片上传
    public void imageUpload(String fileName, byte[] imageByte, Callback<ImageUpload> callback) {
        requestService.imageUpload(UploadImageRetrofit.getMultipartBody_part(fileName, imageByte)).enqueue(callback);
    }
    /**
     * 获取已启用或已运营的城市站点
     * <p>
     * url	方法	说明
     * /common/cityList/findByIsActive	GET	获取已启用或已运营的城市站点
     * 请求参数
     * <p>
     * 参数名	类型	必须(1是/0否)	说明
     * isActive	int	1	状态id
     */
    public void findByIsActive(int isActive, ResultInfoCallback<FindByIsActive> callback) {
//        requestService.findByIsActive(isActive).enqueue(callback);
        callback.setCall(requestService.findByIsActive(isActive)).enqueue(callback);
    }

    public void findByIsActive1(int isActive, ResultInfoCallback<Object> callback) {
        requestService.findByIsActive1(isActive).enqueue(callback);
    }

    public void forget_pwd(String customer, Map<String, Object> params, RequestCallback<ForgetPassword> callback) {
        requestService.forget_pwd(customer, params, PublicCache.site).enqueue(callback);
    }

    public void update_password(String path, Map<String, Object> params, RequestCallback<UpdatePassword> callback) {
        requestService.update_password(path, params, PublicCache.site).enqueue(callback);
    }


    /**
     * 查询所有的市场信息
     * <p/>
     * url	方法	说明
     * /market/findAll	GET	查询所有的市场信息
     */
    public void getMarket_ListAll(RequestCallback<MarketLocal> callback) {
        requestService.getMarket_ListAll(PublicCache.site).enqueue(callback);
    }

    /**
     * 某个市场下面所有的店铺信息
     * <p/>
     * url	方法	说明
     * /market/:marketId/stores	GET	某个市场下面所有的店铺信息
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * :marketId	num	1	市场entityId
     * pn	num	0	分页起始页，默认pn=1
     * ps	num	0	分页每页查询条数，默认ps=10
     */
    public void getMarket_shopInformation(int marketId, int pn, int ps, ResultInfoCallback<MarketShopList> callback) {
        requestService.getMarket_shopInformation(marketId, PublicCache.site, pn, ps).enqueue(callback);
    }

    /**
     * 根据店铺ID查询店铺详情
     * <p/>
     * url	方法	说明
     * /store/findOne/:store	GET	根据店铺ID查询店铺详情
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * :store	num	1	店铺ID
     */
    public void getShop_detail(int shopId, int userType, int personId, ResultInfoCallback<ShopDetail> callback) {
//        requestService.getShop_detail(shopId, PublicCache.site).enqueue(callback);
        callback.setCall(requestService.getShop_detail(shopId, userType, personId, PublicCache.site)).enqueue(callback);
    }


    /**
     * 根据店铺ID查询出该店铺的产品(上架产品)
     * <p/>
     * url	方法	说明
     * /store/:store/products	GET	根据店铺ID查询出该店铺的上架产品
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * :store	num	1	店铺ID
     * status	num	1	status=1表示查询上架产品,status=2表示下架产品
     */
    public void getShop_goods_detail(int shopId, int pn, int ps, ResultInfoCallback<ShopDetail_Goods> callback) {
        requestService.getShop_goods_detail(shopId, PublicCache.site, pn, ps).enqueue(callback);
    }
    public void   getnewShop_goods_detail(Map<String, Object> map,int shopId, int pn, int ps, ResultInfoCallback<ShopDetail_Goods> callback) {
        requestService.getnewShop_goods_detail(shopId,map, PublicCache.site, pn, ps).enqueue(callback);
    }
    /**
     * 根据商品ID查询出商品详情
     * <p/>
     * url	方法	说明
     * /product/findOne/:entityID	GET	根据商品ID查询出商品详情
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * :entityID	num	1	根据商品ID
     */
    public void showGoodsInformation(int entityId, int userType, int personId, ResultInfoCallback<GoodsInformation> callback) {
//        requestService.showGoodsInformation(entityId, PublicCache.site).enqueue(callback);
        callback.setCall(requestService.showGoodsInformation(entityId, userType, personId, PublicCache.site)).enqueue(callback);
    }

    /**
     * 获取商品提成及参考规格
     * url	方法	说明
     * /product/findProductCommission	POST	获取商品提成及参考规格
     * 请求参数
     * 参数名	类型	必须(1是/0否)	说明
     * commodityId	num	1	三级分类id
     * site	num	1	站点id
     * categoryId	num	1	二级分类id
     */
    public void findProductCommission(int categoryId, int commodityId, ResultInfoCallback<FindProductCommission> callback) {
        requestService.findProductCommission(categoryId, commodityId, PublicCache.site_login,PublicCache.loginSupplier.getStore()).enqueue(callback);
    }

    //意见反馈
    public void feedback_save(Map<String, Object> params, RequestCallback<FeedbackSave> callback) {
        requestService.feedback_save(params, PublicCache.site_login).enqueue(callback);
    }

    //新品需求
    public void fooddemand_save(Map<String, Object> params, RequestCallback<FooddemandSave> callback) {
        requestService.fooddemand_save(params, PublicCache.site_login).enqueue(callback);
    }

    //获取订单列表
    public void order_pageList(String status, int pn, int ps, ResultInfoCallback<OrderList> callback) {
        requestService.order_pageList(PublicCache.site_login, PublicCache.loginSupplier == null ? 0 : 1, status, pn, ps).enqueue(callback);
    }

    //订单状态修改
    public void modifyStatus(String status, String orderIds, String orderNo, RequestCallback<OrderStatusEvent> callback) {
        requestService.modifyStatus(PublicCache.site_login, PublicCache.loginSupplier == null ? 0 : 1, status, orderIds, orderNo).enqueue(callback);
    }

    //订单详情
    public void order_findOne(int orderId, String orderNO, ResultInfoCallback<OrderDetail> callback) {
        Map<String, Object> map = new HashMap<>();
        if (PublicCache.loginPurchase == null) {
            map.put("orderId", orderId);
        } else map.put("orderNO", orderNO);
//        requestService.order_findOne(map, PublicCache.site_login).enqueue(callback);

        callback.setCall(requestService.order_findOne(map, PublicCache.site_login)).enqueue(callback);
    }


    //购物车刷新接口
    public void product_findOneBase(String specIds, int customerId, RequestCallback<CartNet> callback) {
        requestService.product_findOneBase(specIds, customerId, PublicCache.site_login).enqueue(callback);
    }


    //订单删除
    public void order_delete(String orderIds, RequestCallback<OrderDeleteEvent> callback) {
        requestService.order_delete(orderIds, PublicCache.site_login).enqueue(callback);
    }

    //撤销订单到购物车
    public void order_deleteReally(String orderIds, RequestCallback<ResultInfo> callback) {
        requestService.order_deleteReally(orderIds, PublicCache.site_login).enqueue(callback);
    }


    //打印发货
    public void bindQrCode(int orderId, String orderItemIds, RequestCallback<DeliverGoods> callback) {
        requestService.bindQrCode(PublicCache.site_login, orderId, orderItemIds).enqueue(callback);
    }

    public void paymethod(ResultInfoCallback<OrderPayMethod> callback) {
        requestService.paymethod(PublicCache.site_login).enqueue(callback);
    }

    public void shipmethod(String addressId, ResultInfoCallback<OrderShipmethod> callback) {
        requestService.shipmethod(PublicCache.site_login, addressId).enqueue(callback);
    }

    public void getSearchhost(RequestCallback<Searchhost> callback) {
        requestService.getSearchhost(PublicCache.site).enqueue(callback);
    }

    public Call<SearchGoods_Resu> getSearchGoods(String goodsName) {
        return requestService.getSearchGoods(PublicCache.site, goodsName);
    }

    /**
     * 按名字搜索商品详情
     * <p>
     * url	方法	说明
     * /product/findListByName?name=大白菜sort=price&isAsc=0	GET	搜索商品详情
     * 请求参数
     * <p>
     * 参数名	类型	必须(1是/0否)	说明
     * name	str	1	商品名字
     * sort	str	1	排序字段createTime（发布时间），price（价格），qty（销量），popularity（综合人气）
     * isAsc	str	1	0升序，1降序
     * ps	str	1
     * pn	str	1
     */
    public void getSearchGood(int userType, String name, String sort, int isAsc, int isP, String productCriteria, int pn, int ps,int isCanteen, ResultInfoCallback<SearchGoods3> callback) {
        requestService.getSearchGood(userType, PublicCache.site, name, sort, isAsc, isP, productCriteria, pn, ps,isCanteen).enqueue(callback);
    }

    public void getSearchShop(String shopName, int userType, int pn, int ps, ResultInfoCallback<SearchShop> callback) {
        requestService.getSearchShop(shopName, userType, PublicCache.site, pn, ps).enqueue(callback);
    }

    /**
     * 搜索带关键字的店铺名字
     * <p>
     * url	方法	说明
     * /store/findName/:site/:name	GET	搜索带关键字的店铺名字（按时间排序前20）
     * 请求参数
     * <p>
     * 参数名	类型	必须(1是/0否)	说明
     * :site	num	1	商圈ID，默认2
     * :name	str	1	店铺名字
     */

    public Call<StoreFindName> getStoreFindName(String name) {
        return requestService.getStoreFindName(PublicCache.site, name);
    }

    /**
     * 搜索带关键字的商品名字
     * <p>
     * url	方法	说明
     * /product/search/findName/:name	GET	搜索带关键字的店铺名字（按拼音排序）
     * 请求参数
     * <p>
     * 参数名	类型	必须(1是/0否)	说明
     * :name	str	1	商品名字
     */

    public Call<ProductFindName> getProductFindName(String name,int isCanteen) {
        return requestService.getProductFindName(name, PublicCache.site,isCanteen);
    }

    //查询出全部商品信息
    public void findPageList(Map<String, Object> map, ResultInfoCallback<PickFoodGoodsList> callback) {
        requestService.findPageList(map, PublicCache.site).enqueue(callback);
    }

    /**
     * 经常买
     * <p/>
     * URL	方法	说明
     * /product/oftenBuy?catalogId=12&entityId=64&entityType=0	GET	首页经常买
     * 请求参数
     * <p/>
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * catalogId	int	1		一级分类id
     * entityId	int	1		登录用户id，未登录的用户传-1
     * entityType	int	1		用户类型，0酒店，1供应商，未登录用-1
     */
    public void oftenBuy(int catalogId, int entityId, int entityType,int isCanteen, int pn, int ps, ResultInfoCallback<PickFoodGoodsList> callback) {
        requestService.oftenBuy(PublicCache.site, catalogId, entityId, entityType,isCanteen, pn, ps).enqueue(callback);
    }
    public void oftenBuy(int catalogId, int entityId, int entityType, int pn, int ps, ResultInfoCallback<PickFoodGoodsList> callback) {
        requestService.oftenBuy(PublicCache.site, catalogId, entityId, entityType, pn, ps).enqueue(callback);
    }
    //商品管理搜索
    public void getShopManagementListSearch(int store, int status, int pn, int ps, String productName, ResultInfoCallback<HomepageGridData> callback) {
        requestService.getShopManagementListSearch(store, PublicCache.site_login, status, pn, ps, productName,"newStock",2).enqueue(callback);
    }

    //下架
    public void takeDown(int storeId, int productId, RequestCallback<TakeDown> callback) {
        requestService.takeDown(storeId, productId, PublicCache.site_login).enqueue(callback);
    }

    public void takeUp(int storeId, int productId, RequestCallback<TakeUp> callback) {
        requestService.takeUp(storeId, productId, PublicCache.site_login).enqueue(callback);
    }

    //编辑商品,重新编辑
    public void goodsUpdate(Map<String, Object> map, RequestCallback<GoodsUpdate> callback) {
        requestService.goodsUpdate(map, PublicCache.site_login).enqueue(callback);
    }

    public void goodsDelete(Map<String, Object> map, RequestCallback<GoodsDelete> callback) {
        requestService.goodsDelete(map, PublicCache.site_login).enqueue(callback);
    }

    // 查询商品分类(一级、二级)
    public void findCategoryList(int site,String refreshId, RequestCallback<GoodsCategoryList_Resu> callback) {
        callback.setCall(requestService.findCategoryList(site,refreshId)).enqueue(callback);
    }

    // 查询商品分类(一级、二级)
    public void newfindCategoryList(int site,int storeId ,RequestCallback<GoodsCategoryList_Resu> callback) {
        callback.setCall(requestService.newfindCategoryList(site,1,storeId)).enqueue(callback);
    }

    // 查询商品分类(三级分类)
    public void findCategoryList3(int categoryId, RequestCallback<GoodsCategoryListNext_Resu> callback) {
        requestService.findCategoryList3(categoryId, PublicCache.site_login).enqueue(callback);
    }

    //发布商品
    public void goodsCreate(Map<String, Object> map, ResultInfoCallback<GoodsInformation> callback) {
        requestService.goodsCreate(map, PublicCache.site_login).enqueue(callback);
    }

    //商品单位
    public void getUnitList(RequestCallback<GoodsUnit_Resu> callback) {
        requestService.getUnitList(PublicCache.site).enqueue(callback);
    }

    //自定义商品类别添加
    public void addCategory(Map<String, Object> map, RequestCallback<AddCategory> callback) {
        requestService.addCategory(map, PublicCache.site_login, PublicCache.site_name_login).enqueue(callback);
    }

    public void checkIfCategoryExist(String name, RequestCallback<CheckIfCategoryExist> callback) {
        requestService.checkIfCategoryExist(name, PublicCache.site).enqueue(callback);
    }


    //手机号换绑
    public void changeTelUrl(String path, Map<String, Object> params, RequestCallback<ChangeTelUrl> callback) {
        requestService.changeTelUrl(path, params, PublicCache.site_login).enqueue(callback);
    }

    public void smsCode(Map<String, Object> params, RequestCallback<SmsCode> callback) {
        if (PublicCache.login_mode.equals(Constants.PURCHASER)) params.put("userType", 0);
        else params.put("userType", 1);
        requestService.smsCode(params, PublicCache.site).enqueue(callback);
    }

    public void checkSmsCode(String phone, String smsCode, RequestCallback<CheckSmsCode> callback) {
        requestService.checkSmsCode(PublicCache.site, phone, smsCode).enqueue(callback);
    }


    public void bankAccount(Map<String, Object> map, RequestCallback<BankAccount> callback) {
        requestService.bankAccount(map, PublicCache.site_login).enqueue(callback);
    }

    public void bankUnbundling(int entityId, int storeId, RequestCallback<BankUnbundling> callback) {
        requestService.bankUnbundling(PublicCache.site_login, entityId, storeId).enqueue(callback);
    }


    public void afterSalesApplication(Map<String, Object> map, ResultInfoCallback<AfterSales> callback) {
        requestService.afterSalesApplication(map, PublicCache.site).enqueue(callback);
    }

    public void afterSaleHandler(int id, int status, RequestCallback<AfterSaleHandler> callback) {
        requestService.afterSaleHandler(PublicCache.site, id, status).enqueue(callback);
    }


    public void findPageByCSIdList(int type, int id, int pn, int ps, RequestCallback<PageByCSIdList> callback) {
        requestService.findPageByCSIdList(PublicCache.site, type, id, pn, ps).enqueue(callback);
    }

    public void deleteSalesAppByEntityId(int entityId,int orderId,int orderItemId, RequestCallback<DeleteSalesAppByEntityId> callback) {
        requestService.deleteSalesAppByEntityId(PublicCache.site, entityId,orderId,orderItemId).enqueue(callback);
    }


    /**
     * 采购商支付宝充值生成订单
     * <p/>
     * url	方法	说明
     * /recharge/create	POST	采购商支付宝充值
     * 请求参数
     * <p/>
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * :customerId	int	1	采购商ID
     * :customerName	int	1	采购商名称
     * :capital	int	1	充值资金
     * :paymentMethod	int	1	支付方式：“wechat_payment”或者 “alipay_payment”或者 “lian_payment”
     * :remarks	int	1	padding 默认“padding”
     */

    public void recharge_create(int customerId, int sub_userId, String customerName, String paymentMethod, String remarks, float capital, RequestCallback<RechargeCreate> callback) {
        requestService.recharge_create(PublicCache.site_login, customerId, sub_userId, customerName, paymentMethod, remarks, capital).enqueue(callback);
    }


    /**
     * 去评价
     * <p>
     * URL	方法	说明
     * /order/toEvaluation	POST	提交评价
     * 请求参数
     * <p>
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * customerId	int	1		用户ID
     * orderNo	String	1		订单编号
     * type	int	1		评价类型 1采购商提交评价，2供应商提交评价
     * logisticsLevel	double	1		物流评分，一星代表1分，半星为0.5分 , 供应商提交时值为-1
     * serviceLevel	double	1		服务评分 , 供应商提交时值为-1
     * evaluationInfos	json数组的字符串	1		评价信息：orderId订单id（必填）、orderItemId订单明细id（必填）、storeId店铺id（必填）、productId产品id（必填）、creditLevel评分等级1好评2中评3差评、evaluationContent评价内容、evaluationImg评价图片， 供应商提交时值为""
     * storeId	int	1		供应商id，采购商提交时值-1
     * orderId	int	1		订单id，采购商提交时值-1
     * creditScore	double	1		供应商评分，采购商提交时值-1
     */
    public void toEvaluation(Map<String, Object> map, RequestCallback<EvaluateIntegral> callback) {
        requestService.toEvaluation(map, PublicCache.site_login).enqueue(callback);
    }

    /**
     * 评价列表 - 供应商和采购商
     * <p>
     * URL	方法	说明
     * /order/evaluation/pageList	GET	评价列表
     * 请求参数
     * <p>
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * userType	int	1		用户类型0酒店1供应商
     * ps	int	1		分页
     * pn	int	1		页码
     * 返回值
     * <p>
     * isReply : 0未回复，1已回复， 2超时不能回复
     */

    public void evaluation_pageList(Map<String, Object> map, RequestCallback<EvaluationList> callback) {
        requestService.evaluation_pageList(map, PublicCache.site_login).enqueue(callback);
    }


    /**
     * 评价列表 - 产品
     * <p>
     * URL	方法	说明
     * /order/evaluation/pPageList	GET	评价列表
     * 请求参数
     * <p>
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * creditLevel	int	0		评价等级1好评2中评3差评 -1或空 全部
     * hasImg	int	0		是否有图1晒图 -1或空 全部
     * productId	int	0		商品id，-1或空全部
     * ps	int	1		分页
     * pn	int	1		页码
     */

    public void evaluation_pPageList(int creditLevel, int hasImg, int productId, int pn, int ps, RequestCallback<EvaluationList> callback) {
//        requestService.evaluation_pPageList(PublicCache.site_login, creditLevel, hasImg, productId, pn, ps).enqueue(callback);
        callback.setCall(requestService.evaluation_pPageList(PublicCache.site_login, creditLevel, hasImg, productId, pn, ps)).enqueue(callback);
    }

    /**
     * 评价统计
     * <p>
     * URL	方法	说明
     * /order/evaluation/statics	GET	评价统计
     * 请求参数
     * <p>
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * productId	int	1		商品id
     */

    public void evaluation_statics(int productId, RequestCallback<EvaluationStatics> callback) {
//        requestService.evaluation_statics(PublicCache.site_login, productId).enqueue(callback);
        callback.setCall(requestService.evaluation_statics(PublicCache.site_login, productId)).enqueue(callback);
    }

    /**
     * 评价回复
     * <p>
     * URL	方法	说明
     * /order/evaluation/update  	post	评价回复
     * 请求参数
     * <p>
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * entityId	int	1		评价id
     * replyContent	String	1		回复内容
     */
    public void evaluation_update(int entityId, String replyContent, ResultInfoCallback<EvaluationUpdate> callback) {
        requestService.evaluation_update(PublicCache.site_login, entityId, replyContent).enqueue(callback);
    }

    /**
     * 评价更改
     * <p>
     * URL	方法	说明
     * /order/evaluation/update  	post	评价更改
     * 请求参数
     * <p>
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * entityId	     int	1		评价id
     * productId     int	1   商品id
     * creditLevel   int	1   评价等级，1好评2中评3差评
     * evaluationContent	String	评价内容
     * evalImg              String  评价图片
     */
    public void buyer_evaluation_update(Map<String, Object> map, RequestCallback<ResultInfo> callback) {
        requestService.buyer_evaluation_update(PublicCache.site_login, map).enqueue(callback);
    }

    /**
     * 上传QQ号码
     * <p>
     * URL	方法	说明
     * /supplier/update put	评价回复
     * 请求参数
     * <p>
     * "entityId":32,
     * "qqNumber":"88988988"
     */
    public void qq_update(int entityId, String qqNumber, ResultInfoCallback callback) {
        requestService.qq_update(PublicCache.site_login, entityId, qqNumber).enqueue(callback);
    }

    public void coupons_statistics(int userId, RequestCallback<CouponsStatistics> callback) {
//        requestService.coupons_statistics(PublicCache.site_login, userId, 0).enqueue(callback);

        callback.setCall(requestService.coupons_statistics(PublicCache.site_login, userId, 0)).enqueue(callback);
    }

    public void coupons_findByUser(int userId, int status, int pn, int ps, RequestCallback<CouponsFindByUser> callback) {
        requestService.coupons_findByUser(PublicCache.site_login, userId, 0, status, pn, ps).enqueue(callback);
    }
    public void coupons_findVoucher(int userId,  int amountlsAsc,int createTimelsAsc,int pn, int ps, RequestCallback<CouponsFindByUser> callback) {
        requestService.coupons_findVoucher(PublicCache.site_login, userId, amountlsAsc,createTimelsAsc,0,0, pn, ps).enqueue(callback);
    }

    /**
     * 激活卡密券
     * <p>
     * url	方法	说明
     * /coupons/getCouponByCode	POST	领取卡密券
     * 参数实例
     * <p>
     * 说明：code代金券卡密
     * <p>
     * {
     * "userId":45,
     * "userType":0,
     * "code":"TDJKM001"
     * }
     */
    public void coupons_getCouponByCode(int userId, int isC,String code, ResultInfoCallback callback) {
        requestService.coupons_getCouponByCode(PublicCache.site_login, userId,isC, 0, code).enqueue(callback);
    }

    /**
     * 可领取代金券列表
     * <p>
     * url	方法	说明
     * /coupons/findReceiveList?userId=45&userType=0	GET	查询该用户可以领取的代金券
     */
    public void coupons_chooseCouponList(int userId,int isC, int pn, int ps, RequestCallback<CouponsFindreciveList> callback) {
        requestService.coupons_chooseCouponList(PublicCache.site_login, userId,isC, 0, pn, ps).enqueue(callback);
    }


    /**
     * 5、 领取代金券
     * <p>
     * url	方法	说明
     * /coupons/received	POST	领取代金券
     * 参数实例
     * <p>
     * 说明：
     * couponId代金券id,
     * account登录手机号，
     * accountName酒店名称，
     * startTime，endTime代金券的有效时间
     * <p>
     * {
     * "couponId":3,
     * "userId":45,
     * "userType":0,
     * "account":"13972212122",
     * "accountName":"大庆西路黄牛庄",
     * "startTime":"2017-09-01",
     * "endTime":"2017-10-01"
     * }
     */
    public void coupons_received(Map<String, Object> map, ResultInfoCallback callback) {
        requestService.coupons_received(map, PublicCache.site_login).enqueue(callback);
    }

    /**
     * 6、 去结算时选择代金券列表
     * <p>
     * url	方法	说明
     * /coupons/findReceiveList?userId=45&userType=0	GET	查询该用户可以领取的代金券
     * 参数说明
     * <p>
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * userId	int	1		用户ID
     * userType	int	1		用户类型
     * productInfo	json数组的字符串	1		商品信息productId， price，qty
     * 参数实例
     * <p>
     * {
     * "userId":45,
     * "userType":0,
     * "productInfo":"[{\"productId\":368,\"price\":10, \"qty\":25}]"
     * }
     */

    public void coupons_chooseCouponList(int userId, String productInfo, RequestCallback<CouponsChooseCouponList> callback) {
//        requestService.coupons_chooseCouponList(PublicCache.site_login, userId, 0, productInfo).enqueue(callback);
        callback.setCall(requestService.coupons_chooseCouponList(PublicCache.site_login, userId, 0, productInfo)).enqueue(callback);
    }

    public void coupons_chooseNewCouponList(int userId, String productInfo, RequestCallback<NewCouponsChooseCouponList> callback) {
//        requestService.coupons_chooseCouponList(PublicCache.site_login, userId, 0, productInfo).enqueue(callback);
        callback.setCall(requestService.coupons_chooseNewCouponList(PublicCache.site_login, userId, 0, productInfo)).enqueue(callback);
    }

    /**
     * 计算运费
     * <p>
     * url	方法	说明
     * /order/calculate/freight	POST	提交订单时计算运费
     * 参数说明
     * <p>
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * productInfo	json数组的字符串	1		商品信息productId， price，qty
     */
    //String productInfo 参数类型
    // FreightParticulars  实体类
    public void freight_particulars(String productInfo, RequestCallback<FreightParticulars> callback) {
//        requestService.freight_particulars(PublicCache.site_login, productInfo).enqueue(callback);
        callback.setCall(requestService.freight_particulars(PublicCache.site_login, productInfo)).enqueue(callback);
    }

    public void distribution_fee_statement(ResultInfoCallback<List<DistributionFeeStatementBean>> callback) {
        requestService.distribution_fee_statement(PublicCache.site_login).enqueue(callback);
    }

    public void freight_particulars_new(String orderNo, RequestCallback<FreightParticularsNew> callback) {
//        requestService.freight_particulars_new(PublicCache.site_login, orderNo).enqueue(callback);
        callback.setCall(requestService.freight_particulars_new(PublicCache.site_login, orderNo)).enqueue(callback);
    }

    public void after_details(int id, ResultInfoCallback<RefundDetail> callback) {
//        requestService.after_details(id, PublicCache.site_login).enqueue(callback);
        callback.setCall(requestService.after_details(id, PublicCache.site_login)).enqueue(callback);
    }
    public void after_details_salesNo(String afterSalesNo, ResultInfoCallback<RefundDetail> callback) {
//        requestService.after_details(id, PublicCache.site_login).enqueue(callback);
        callback.setCall(requestService.after_details_salesNo(afterSalesNo, PublicCache.site_login)).enqueue(callback);
    }

    public void after_details_order(int orderItemId, ResultInfoCallback<RefundDetail> callback) {
//        requestService.after_details_order(orderItemId, PublicCache.site_login).enqueue(callback);
        callback.setCall(requestService.after_details_order(orderItemId, PublicCache.site_login)).enqueue(callback);
    }

    public void goods_classify_search(String name, String storeId, RequestCallback<GoodsClassifySearchBean> callback) {
        requestService.goods_classify_search(PublicCache.site, name, storeId).enqueue(callback);
    }

    public void yi_jian_que_ren(int storeId, RequestCallback<YiJianQueRenBean> callback) {
        requestService.yi_jian_que_ren(storeId, PublicCache.site).enqueue(callback);
    }

    public void today_deliver_goods_order(String truckTime, int storeId,int stationId,int rwId,int regionId,int productId,int printStatus,String productNickName, int isc,String expectDeliveredDate,RequestCallback<TodayDeliverGoodsOrderBean> callback) {
        requestService.today_deliver_goods_order(storeId, PublicCache.site, truckTime,stationId,rwId,regionId,productId,printStatus,productNickName,isc,expectDeliveredDate).enqueue(callback);
    }

    public void supplier_reversion(Map<String, Object> map, RequestCallback<ResultInfo> callback) {
        requestService.supplier_reversion(map, PublicCache.site).enqueue(callback);
    }

    public void customer_reversion(Map<String, Object> map, RequestCallback<ResultInfo> callback) {
        requestService.customer_reversion(map, PublicCache.site).enqueue(callback);
    }


    /**
     * 消息推送
     * <p>
     * 1、 采购商绑定TOKEN
     * <p>
     * url	方法	说明
     * /pushMessage/customer/relation/token	POST	采购商绑定TOKEN
     * 参数说明
     * <p>
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * type	int	1		用户类型，0表示采购商 1表示供应商
     * customerId	int	1		酒店主账号的ID
     * telephone	str	1		采购商的手机号
     * customerFlag	int	1		1表示主账号、2表示子账号
     * customerSubuserId	int	1		子账号的ID
     * customerRole	int	1		子账号身份，0表示主管理员 1表示厨师 2表示财务 3表示子管理员
     * token	str	1		采购商绑定的token
     * phoneType	int	1		phone_type：1、ios 2、android
     */


    public void pushMessageCustomer(String token, RequestCallback<PushMessageCustomerToken> callback) {
        if (PublicCache.loginPurchase == null || TextUtils.isEmpty(token)) return;
        Map<String, Object> map = new HashMap<>();
        map.put("type", 0);
        map.put("customerId", PublicCache.loginPurchase.getEntityId());
        map.put("telephone", PublicCache.loginPurchase.getPhoneNumber());
        map.put("customerFlag", PublicCache.loginPurchase.getFlag());

        if (PublicCache.loginPurchase.getFlag() == 1)
            map.put("customerSubuserId", PublicCache.loginPurchase.getEntityId());
        else map.put("customerSubuserId", PublicCache.loginPurchase.getSubUserId());

        map.put("customerRole", PublicCache.loginPurchase.getEmpRole());
        map.put("token", token);
        map.put("phoneType", 2);
        requestService.pushMessageCustomer(map, PublicCache.site).enqueue(callback);
    }

    /**
     * 2、 供应商绑定TOKEN
     * <p>
     * url	方法	说明
     * /pushMessage/supplier/relation/token	POST	供应商绑定TOKEN
     * 参数说明
     * <p>
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * type	int	1		用户类型，0表示采购商 1表示供应商
     * supplierId	int	1		供应商的ID
     * telephone	str	1		供应商的手机号
     * token	str	1		供应商商绑定的token
     * phoneType	int	1		phone_type：1、ios 2、android
     */

    public void pushMessageSupplier(String token, RequestCallback<PushMessageCustomerToken> callback) {
        if (PublicCache.loginSupplier == null || TextUtils.isEmpty(token)) return;
        Map<String, Object> map = new HashMap<>();
        map.put("type", 1);
        map.put("supplierId", PublicCache.loginSupplier.getEntityId());
        map.put("telephone", PublicCache.loginSupplier.getPhoneNumber());
        map.put("token", token);
        map.put("phoneType", 2);
        map.put("uniqueId", SystemUtils.getAndroidId());
        requestService.pushMessageSupplier(map, PublicCache.site).enqueue(callback);
    }

    /**
     * 3、 退出登录删除用户绑定的TOKEN
     * <p>
     * url	方法	说明
     * /pushMessage/logout/delete/token	POST	退出登录删除用户绑定的TOKEN
     * 参数说明
     * <p>
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * userType	int	1		用户类型，0表示采购商 1表示供应商
     * phoneNumber	str	1		用户的手机号码
     */
    public void pushMessageLogout(ResultInfoCallback<Object> callback) {
        int userType;
        String phoneNumber;
        if (PublicCache.loginPurchase != null) {
            userType = 0;
            phoneNumber = PublicCache.loginPurchase.getPhoneNumber();
        } else if (PublicCache.loginSupplier != null) {
            userType = 1;
            phoneNumber = PublicCache.loginSupplier.getPhoneNumber();
        } else {
            callback.onSuccess(null);
            return;
        }
        requestService.pushMessageLogout(PublicCache.site_login, userType, phoneNumber).enqueue(callback);
    }

    /**
     * 获取通知
     * /pushMessageRecords/pageList	GET	查询出列表消息
     * 参数说明
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * entityId	int	1		采购商或者供应商的entityId
     * pn	    int	1		分页起始页
     * ps	    int	1		分页每页条数
     */
    public void getNews(int entityId, int pn, int ps, ResultInfoCallback<GetNews> callback) {
        requestService.getNews(PublicCache.site, entityId, pn, ps).enqueue(callback);
    }

    /**
     * 更新推送列表已经读过的消息
     * url	方法	说明  /pushMessageRecords/update  	POST	更新推送列表已经读过的消息
     * <p>
     * 参数名	类型	非空(1是/0否)       说明
     * entityId	int	1		记录的entityId
     * isRead	int	1		如果是1表示已读，0表示未读
     */
    public void readNews(int entityId, int isRead, ResultInfoCallback callback) {
        requestService.getReadNews(PublicCache.site, entityId, isRead).enqueue(callback);
    }


    /**
     * 添加/编辑商品图文详情
     * <p>
     * url	方法	说明
     * /product/saveDetail	POST	添加/编辑商品详情
     * 请求参数
     * <p>
     * 参数名	类型	必须(1是/0否)	说明
     * product_id	num	1	商品ID
     * details	String	1	商品图文详情
     * exp 参数
     * <p>
     * {
     * "product_id":1,
     * "details":"http://jgiohiuert.jpg#你好吗#呵呵呵呵呵呵#http://1234gfdg.jpeg"
     * }
     */
    public void product_saveDetail(int product_id, String details, ResultInfoCallback callback) {
        requestService.product_saveDetail(PublicCache.site, product_id, details).enqueue(callback);
    }

    /**
     * 根据商品ID查询出指定商品图文详情
     * <p>
     * url	方法	说明
     * /product/findProductDetail/:product_id	GET	根据商品ID查询出商品图文详情
     * 请求参数
     * <p>
     * 参数名	类型	必须(1是/0否)	说明
     * :product_id	num	1	根据商品ID
     */
    public void product_findProductDetail(int product_id, RequestCallback<FindProductDetail> callback) {
//        requestService.product_findProductDetail(product_id, PublicCache.site).enqueue(callback);
        callback.setCall(requestService.product_findProductDetail(product_id, PublicCache.site)).enqueue(callback);
    }

    /**
     * url	方法	说明
     * /product/unitList	GET	获取规格列表
     * <p>
     * 返回值
     * data：baseUnit 基础规格单位，level1Unit一级可选规格列表，listUnit全部规格列表
     */
    public void getUnitsList(RequestCallback<GetUnitList> callback) {
//        requestService.getUnitsList(PublicCache.site).enqueue(callback);
        callback.setCall(requestService.getUnitsList(PublicCache.site)).enqueue(callback);
    }


    /**
     * url	方法	说明
     * /productSpecification/save	POST	添加规格
     * <p>
     * 参数名	类型	必须(1是/0否)	说明
     * productId	num	1	商品id
     * level1Unit	String	1	一级规格
     * level2Unit	String	0	二级规格
     * level3Unit	String	0	三级规格
     * price	num	1	价格
     * level2Value	num	0	二级规格值
     * level3Value	num	0	三级规格值
     * levelType	num	1	规格级别1：只有一个规格组成，2：有两个规格组成，3：有三个规格组成
     * stock	num	1	库存
     * avgPrice	num	1	均价
     * avgUnit	string	1	均价规格
     */
    public void saveSpecification(Map<String, Object> map, ResultInfoCallback callback) {
        requestService.saveSpecification(map, PublicCache.site).enqueue(callback);
    }


    /**
     * url	方法	说明
     * /productSpecification/delete	POST	删除规格
     * <p>
     * 参数名	类型	必须(1是/0否)	说明
     * productId	num	1	商品id
     * entityId	num	1	规格id
     */
    public void delete(int productId, int entityId, ResultInfoCallback callback) {
        requestService.delete(PublicCache.site, productId, entityId).enqueue(callback);
    }

    /**
     * url	方法	说明
     * /productSpecification/update	POST	修改规格
     * <p>
     * 参数名	类型	必须(1是/0否)	说明
     * <p>
     * entityId	num	1	规格id
     * productId	num	1	商品id
     * price	num	1	价格
     * level2Value	num	0	二级规格值
     * level3Value	num	0	三级规格值
     * stock	num	1	库存
     * avgPrice	num	1	均价
     */
    public void update(Map<String, Object> map, ResultInfoCallback callback) {
        requestService.update(map, PublicCache.site).enqueue(callback);
    }


    /**
     * url	方法	说明
     * /specialMerchants/findAll	POST	获取所有招商入驻信息
     * site 站点
     */
    public void find_business(Map<String, Object> map, RequestCallback<FindBusiness> callback) {
        requestService.find_business(map, PublicCache.site).enqueue(callback);
    }

    /**
     * url	方法	说明
     * /customerInvoice/updateStatus	POST	开启/关闭发票
     * site 站点
     * entityId 发票抬头id
     * customerId 采购商id
     * isActive 是否开启：1-是,0-否
     */
    public void open_or_close_ticket(Map<String, Object> map, ResultInfoCallback callback) {
        requestService.open_or_close_ticket(map, PublicCache.site).enqueue(callback);
    }


    /**
     * 头像信息上传，食品资格证修改，营业执照修改,身份证上传
     */
    public void supplier_update(String update, Map<String, Object> params, RequestCallback<ImageUploadOk> callback) {
        requestService.supplier_update(update, params, PublicCache.site_login).enqueue(callback);
    }

    /**
     * 商品资质图片
     * <p/>
     * 1、根据productId查询所有
     * <p/>
     * url	方法	说明
     * /qualificationImage/getImageListByProductId/:productId	GET	根据productId查询所有
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * :productId	int	1	productId
     */
    public void getImageListByProductId(int productId, RequestCallback<ImageListByProductId> callback) {
        requestService.getImageListByProductId(productId).enqueue(callback);
    }

    /**
     * 根据imgeUrl删除商品资质图片记录
     * <p/>
     * url	方法	说明
     * /qualificationImage/deleteActualByImageURL	PUT	根据imgeUrl删除图片记录
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * imageUrl	Str	1	图片地址
     */
    public void deleteActualByImageURL(String imageUrl, RequestCallback<QualificationImage> callback) {
        requestService.deleteActualByImageURL(imageUrl, PublicCache.site_login).enqueue(callback);
    }

    /**
     * 上传资质图片（修改资质、上传图片）
     * <p/>
     * url	方法	说明
     * /image/android/qualification/save	POST	上传资质图片（修改资质、上传图片）
     * 参数名	类型	必须(1是/0否)	说明
     * productId	int	1	商品ID
     * image	String	1
     */
    public void qualification_upload(int productId, String image, RequestCallback<QualificationUpload> callback) {
        requestService.qualification_upload(productId, image, PublicCache.site_login).enqueue(callback);
    }

    /**
     * 酒店查询出收货地址列表
     * <p/>
     * url	方法	说明
     * /address/list/all	GET	酒店查询出收货地址列表
     */
    public void getAddressList(int pn, int ps,int customerId,int userId, ResultInfoCallback<GoodsReceiptAddress> callback) {
//        requestService.getAddressList(PublicCache.site_login, pn, ps).enqueue(callback);
        callback.setCall(requestService.getAddressList(PublicCache.site_login, pn, ps,customerId,userId)).enqueue(callback);
    }

    /**
     * 采购商添加收货地址
     * <p/>
     * url	方法	说明
     * /address/save	POST	供应商添加收货地址
     * 请求Header参数
     * <p/>
     * Content-Type: application/json
     * <p/>
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * consignee	String	1	用户名|
     * gender	int	1	性别 0：男 1：女
     * phoneNumber	String	1	电话号码
     * hotelName	String	1	酒店名称
     * provinceId	int	1	省编号
     * cityId	int	1	城市编号
     * street	String	1	街道
     */
    public void addAddress(Map<String, Object> map, RequestCallback<AddressSave> callback) {
        requestService.addAddress(map, PublicCache.site_login).enqueue(callback);
    }

    //删除收货地址
    public void deleteAddress(int addressId, RequestCallback<AddressDelete> callback) {
        requestService.deleteAddress(addressId, PublicCache.site_login).enqueue(callback);
    }

    /**
     * 更新收货地址
     * <p/>
     * url	方法	说明
     * /address/update	PUT	更新收货地址
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * addressId	int	1	地址ID
     * consignee	int	0	姓名
     * phoneNumber	String	0	电话
     * provinceId	int	0	省编号
     * cityId	int	0	城市编号
     * street	String	0	街道
     * hotelName	String	0	酒店名字
     * street	String	0	街道
     * gender	int	0	性别
     * isDefault	int	0	是否默认，1表示默认地址
     */
    public void updateAddress(Map<String, Object> map, RequestCallback<AddressUpdate> callback) {
        requestService.updateAddress(map, PublicCache.site_login).enqueue(callback);
    }

    /**
     * 酒店查询出默认收货地址列表
     * <p/>
     * url	方法	说明
     * /address/getDefaultOne	GET	酒店查询出收货地址列表
     */
    public void getDefaultOne(ResultInfoCallback<GoodsReceiptAddressBean> callback) {
        requestService.getDefaultOne(PublicCache.site_login).enqueue(callback);
    }

    /**
     * 头像信息上传，食品资格证修改，营业执照修改,身份证上传
     */
    public void customer_update(String update, Map<String, Object> params, RequestCallback<ImageUploadOk> callback) {
        requestService.customer_update(update, params, PublicCache.site_login).enqueue(callback);
    }

    //采购商登录
    public void loginData_purchase(Map<String, Object> params, RequestCallback<PurchaseBean> callback) {
        requestService.loginData_purchase(params).enqueue(callback);
    }

    //注册
    public void customer_registerData(Map<String, Object> params, int site, RequestCallback<Register> callback) {
        requestService.customer_registerData(params, site).enqueue(callback);
    }

    //结算生成订单
    public void toOrder(int loginUserId,int paymentCustomerId,int customerId, int addressId, String paymentCode, String shippingAddressInfo, String productInfo,
                        String couponItemInfo, int productCount, String freightItemInfo, ResultInfoCallback<OrderPlaceBack> callback) {
        requestService.toOrder(PublicCache.site_login,loginUserId,paymentCustomerId, "createdAt", customerId, addressId,
                paymentCode, shippingAddressInfo, productInfo, couponItemInfo, productCount, freightItemInfo).enqueue(callback);
    }
    //个人结算生成订单
    public void toPersonalOrder(int loginUserId,int paymentCustomerId,int customerId, int addressId, String paymentCode, String shippingAddressInfo, String productInfo,
                        String couponItemInfo, int productCount, String freightItemInfo,int timeEntityId,int deliveryType,int CommunityId,  ResultInfoCallback<OrderPlaceBack> callback) {
        requestService.toPersonalOrder(PublicCache.site_login,loginUserId,paymentCustomerId, "createdAt", customerId, addressId,
                paymentCode, shippingAddressInfo, productInfo, couponItemInfo, productCount, freightItemInfo,timeEntityId,deliveryType,CommunityId).enqueue(callback);
    }

    //* 采购商提现
    public void getCustomerCashWithdrwalApplication(Map<String, Object> map, ResultInfoCallback<CustomerCash> callback) {
        requestService.getCustomerCashWithdrwalApplication(map, PublicCache.site_login).enqueue(callback);
    }

    /**
     * 1、采购商绑定银行账号
     * <p/>
     * url	方法	说明
     * /customerFinance/bankAccount/save	POST	采购商绑定银行账号
     * 请求参数
     * <p/>
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * :provinceId	int	1	省ID
     * :cityId	int	1	城市ID
     * :customerId	int	1	采购商ID
     * :hotelName	int	1	酒店名称
     * :bankType	int	1	类型1、表示支付宝 2、表示银行卡
     * :isDefault	int	1	是否默认
     * :bankName	int	1	“支付宝”或者 “XXXX银行”
     * :accountNo	int	1	可以是 支付宝账、银
     * 行账号卡号
     * :ownerName	int	1	持卡姓名 或者 支付宝姓名
     * :bankAddress	int	1	银行地址
     */

    public void customerFinance_bankAccount(Map<String, Object> map, RequestCallback<ResultInfo> callback) {
        requestService.customerFinance_bankAccount(map, PublicCache.site_login).enqueue(callback);
    }

    /**
     * 2、根据CustomerId查询出该用户所有的银行账号信息
     * <p/>
     * url	方法	说明
     * /customerFinance/getAccountByCustomerId	GET	根据CustomerId查询出该用户所有的银行账号信息
     * 请求参数
     * <p/>
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * :customerId	int	1	采购商ID
     * :pn	int	1	pn分页
     * :ps	int	1	ps分页
     */
    public void getAccountByCustomerId(int customerId, RequestCallback<AccountByCustomerId> callback) {
        requestService.getAccountByCustomerId(PublicCache.site, customerId).enqueue(callback);
    }

    /**
     * 获取默认的银行卡信息
     * <p/>
     * url	方法	说明
     * /customerFinance/bankAccount/getDefaultAccount/:customerId	GET	获取默认的银行卡信息
     * 请求参数
     * <p/>
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * :customerId	int	1	采购商ID
     */

    public void customerFinance_getDefaultAccount(int customerId, RequestCallback<CustomerFinanceDefaultAccount> callback) {
        requestService.customerFinance_getDefaultAccount(customerId, PublicCache.site).enqueue(callback);
    }

    public void getWithdrawFeeRule( RequestCallback<WithdrawFeeRule> callback) {
        requestService.getWithdrawFeeRule(PublicCache.site).enqueue(callback);
    }

    /**
     * 采购商设置为默认的银行卡信息
     * <p/>
     * url	方法	说明
     * /customerFinance/bankAccount/setDefaultAccount	POST	采购商设置为默认的银行卡信息
     * 请求参数
     * <p/>
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * :entityId	int	1	银行卡ID
     * :customerId	int	1	采购商ID
     */
    public void customerFinance_setDefaultAccount(int entityId, int customerId, RequestCallback<ResultInfo> callback) {
        requestService.customerFinance_setDefaultAccount(PublicCache.site, entityId, customerId).enqueue(callback);
    }


    //采购商电子账单
    public void customerFinanceRecord(int customerId, int pn, int ps, RequestCallback<CustomerFinanceRecord> callback) {
        requestService.customerFinanceRecord(customerId, PublicCache.site, pn, ps).enqueue(callback);
    }

    //采购商余额明细
    public void customerFinanceRecord_balance(int customerId, int pn, int ps, RequestCallback<CustomerFinanceRecord> callback) {
        requestService.customerFinanceRecord_balance(customerId, PublicCache.site, pn, ps).enqueue(callback);
    }

    //电子账单筛选明细
    public void customerFinanceCondition(Map<String, Object> map, int pn, int ps, RequestCallback<CustomerFinanceCondition> callback) {
        requestService.customerFinanceCondition(map, PublicCache.site, pn, ps).enqueue(callback);
    }

    //余额筛选明细
    public void customerFinanceCondition_balance(Map<String, Object> map, int pn, int ps, RequestCallback<CustomerFinanceCondition> callback) {
        requestService.customerFinanceCondition_balance(map, PublicCache.site, pn, ps).enqueue(callback);
    }

    /**
     * 查询订单账单详情
     * <p>
     * url	方法	说明
     * /customerFinanceRecord/orderDetail/findOneById/:id	GET	根据ID查询订单账单详情
     * 请求参数
     * <p>
     * 参数名	类型	必须(1是/0否)	说明
     * :id	num	1	采购商的资金明细的entityId
     */
    public void customerFinanceRecordOrderDetail(int id, RequestCallback<CustomerFinanceRecordOrderDetail> callback) {
        callback.setCall(requestService.customerFinanceRecordOrderDetail(id, PublicCache.site)).enqueue(callback);
//        requestService.customerFinanceRecordOrderDetail(id, PublicCache.site).enqueue(callback);
    }


    /**
     * 查询订单条目详情
     * <p>
     * url	方法	说明
     * /customerFinanceRecord/orderItemDetail/findOneById/:id	GET	查询订单条目详情
     * 请求参数
     * <p>
     * 参数名	类型	必须(1是/0否)	说明
     * :id	num	1	采购商的资金明细的entityId
     * 返回值
     */
    public void customerFinanceRecordOrderItemDetail(int id, RequestCallback<CustomerFinanceRecordOrderItemDetail> callback) {
        callback.setCall(requestService.customerFinanceRecordOrderItemDetail(id, PublicCache.site)).enqueue(callback);
    }

    /**
     * 4、查询充值订单账单详情
     * <p>
     * url	方法	说明
     * /customerFinanceRecord/rechargeDetail/findOneById/:id	GET	根据ID查询充值订单账单详情
     * 请求参数
     * 参数名	类型	必须(1是/0否)	说明
     * :id	num	1	采购商的资金明细的entityId
     * 返回值
     */

    public void customerFinanceRecordRechargeDetail(int id, RequestCallback<CustomerFinanceRecordRechargeDetail> callback) {
        requestService.customerFinanceRecordRechargeDetail(id, PublicCache.site).enqueue(callback);
    }


    /**
     * 5、查询提现账单详情
     * <p>
     * url	方法	说明
     * /customerFinanceRecord/withdrawalDetail/findOneById/:id	GET	根据ID查询提现账单详情
     * 请求参数
     * <p>
     * 参数名	类型	必须(1是/0否)	说明
     * :id	num	1	采购商的资金明细的entityId
     */
    public void customerFinanceRecordWithdrawalDetail(int id, RequestCallback<CustomerFinanceRecordWithdrawalDetail> callback) {
        requestService.customerFinanceRecordWithdrawalDetail(id, PublicCache.site).enqueue(callback);
    }

    public void customerFinanceRecordRefundDetail(int id, RequestCallback<CustomerFinanceRecordRefundDetail> callback) {
        requestService.customerFinanceRecordRefundDetail(id, PublicCache.site).enqueue(callback);
    }

    /**
     * 6、查询最近五个月的账单详情
     * <p>
     * url	方法	说明
     * /customerFinanceRecord/findMonthBillDetail/:customerId	GET	查询最近五个月的账单详情
     * 请求参数
     * <p>
     * 参数名	类型	必须(1是/0否)	说明
     * :customerId	num	1	采购商的customerId
     * year	num	1	年
     * month	num	1	月
     */

    public void customerFinanceRecordFindMonthBillDetail(int customerId, int year, int month, RequestCallback<CustomerFinanceRecordFindMonthBillDetail> callback) {
        requestService.customerFinanceRecordFindMonthBillDetail(customerId, PublicCache.site, year, month).enqueue(callback);
    }


    public void checkCustomerPhoneExist(String telephone, RequestCallback<CheckPhoneExist> callback) {
        requestService.checkCustomerPhoneExist(PublicCache.site, telephone).enqueue(callback);
    }

    public void getSubUserList(String markCode, RequestCallback<SubAccount_Resu> callback) {
        requestService.getSubUserList(markCode, PublicCache.site_login).enqueue(callback);
    }

    //[使用验证码激活子账号]

    public void activation(int entityId, String verifyCode, ResultInfoCallback<Object> callback) {
        requestService.activation(PublicCache.site_login, entityId, verifyCode).enqueue(callback);
    }

    //拒绝激活
    public void subuser_refuse(int subUserId, ResultInfoCallback<Object> callback) {
        requestService.subuser_refuse(subUserId, PublicCache.site_login).enqueue(callback);
    }

    public void sendSmsToSubUser(int subUserId, ResultInfoCallback<Object> callback) {
        requestService.sendSmsToSubUser(PublicCache.site_login, subUserId).enqueue(callback);
    }


    public void subUserCreate(Map<String, Object> map, RequestCallback<SubUserCreate> callback) {
        requestService.subUserCreate(map, PublicCache.site_login).enqueue(callback);
    }

    public void subUserDelete(int customerId, int flag, String markCode, RequestCallback<SubUserDelete> callback) {
        requestService.subUserDelete(PublicCache.site_login, customerId, flag, markCode).enqueue(callback);
    }

    public void onOrOffSubUser(Map<String, Object> map, RequestCallback<OnOrOffSubUser> callback) {
        requestService.onOrOffSubUser(map, PublicCache.site_login).enqueue(callback);
    }


    /**
     * 余额支付的时候校验查询
     * <p>
     * url	方法	说明
     * /customerPaymentStatusRecord/balanceQuery/verify	POST	余额支付的时候校验
     * 请求参数
     * <p>
     * 参数名	类型	必须(1是/0否)	说明
     * customerId	num	1	采购商的customerId
     * customerTel	str	1	采购商的电话号码
     * hotelName	str	1	酒店名称
     * extOrderId	str	1	订单的extOrderId
     * outTradeNo	str	1	订单的outTradeNo
     * status	num	1	1、支付中 2、支付结束
     * isSupportBalancePayment	num	1	0、不支持余额支持 1、支持余额支持
     * totalPaymentAmount	num	1	支付的总金额
     * onlinePaymentAmount	num	1	支付宝或微信支付的总金额
     * balancePaymentAmount	num	1	余额支付的总金额
     * paymentMethod	num	1	1、支付宝 2、微信支付 3、余额支付 4、支付宝+余额 5、微信+余额
     */
    public void balanceQuery(Map<String, Object> map, RequestCallback<BalanceQuery> callback) {
        requestService.balanceQuery(map, PublicCache.site_login).enqueue(callback);
    }


    public void wxpay_repayId(String orderId, String actualPay, ResultInfoCallback<WXPay> callback) {
        requestService.wxpay_repayId(PublicCache.site_login, orderId, actualPay, 1).enqueue(callback);
    }

    public void wxpay_recharge(String payRecordId, float actualPay, ResultInfoCallback<WXPay> callback) {
        requestService.wxpay_recharge(PublicCache.site_login, payRecordId, actualPay, 1).enqueue(callback);
    }

    public void updateRecordsIsSupportBalance(String outTradeNo, int isSupportBalance, RequestCallback<ResultInfo> callback) {
        requestService.updateRecordsIsSupportBalance(PublicCache.site_login, outTradeNo, isSupportBalance).enqueue(callback);
    }

    public void balancePay(String outTradeNo, BigDecimal totalAmount, int customerId, RequestCallback<ResultInfo> callback) {
        requestService.balancePay(PublicCache.site_login, outTradeNo, totalAmount, customerId).enqueue(callback);
    }

    public void customer_refreshInfo(int entityId,int flag,int loginUserId, RequestCallback<MyselftUpdateP> callback) {
        requestService.customer_refreshInfo(PublicCache.site, flag, entityId,loginUserId, SystemUtils.getAndroidId()).enqueue(callback);
    }


    /**
     * 发票抬头信息
     * <p>
     * 1、 创建采购商发票抬头信息
     * <p>
     * url	方法	说明
     * /customerInvoice/create	POST	创建采购商发票抬头信息
     * 参数说明
     * <p>
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * customerId	int	1		采购商id
     * invoiceType	int	1		发票类型：1-专票
     * invoiceTitle	String	1		发票抬头
     * taxNumber	String	1		税号
     * bankName	String	1		开户银行
     * bankAccount	String	1		开户账号
     * telephone	String	1		企业注册电话
     * address	String	1		企业注册地址
     * isActive	int	1		是否开启：1-是，0-否
     */

    public void customerInvoice_create(Map<String, Object> map, ResultInfoCallback callback) {
        requestService.customerInvoice_create(map, PublicCache.site_login).enqueue(callback);
    }


    /**
     * 2、 修改采购商发票抬头信息
     * <p>
     * url	方法	说明
     * /customerInvoice/update	POST	修改采购商发票抬头信息
     * 参数说明
     * <p>
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * entityId	int	1		发票抬头id
     * customerId	int	0		采购商id
     * invoiceType	int	0		发票类型：1-专票
     * invoiceTitle	String	0		发票抬头
     * taxNumber	String	0		税号
     * bankName	String	0		开户银行
     * bankAccount	String	0		开户账号
     * telephone	String	0		企业注册电话
     * address	String	0		企业注册地址
     * isActive	int	0		是否开启：1-是，0-否
     */

    public void customerInvoice_update(Map<String, Object> map, ResultInfoCallback callback) {
        requestService.customerInvoice_update(map, PublicCache.site_login).enqueue(callback);
    }

    /**
     * 3、 开启/关闭发票
     * url	方法	说明
     * /customerInvoice/updateStatus	POST	开启/关闭发票
     * 参数说明
     * <p>
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * entityId	int	1		发票抬头id
     * customerId	int	1		采购商id
     * isActive	int	1		是否开启：1-是,0-否
     */
    public void customerInvoice_updateStatus(int entityId, int customerId, int isActive, ResultInfoCallback callback) {
        requestService.customerInvoice_updateStatus(entityId, customerId, isActive, PublicCache.site_login).enqueue(callback);
    }


    /**
     * 4、 查看采购商发票抬头信息
     * url	方法	说明
     * /customerInvoice/findOne	GET	开启/关闭发票
     * 参数说明
     * <p>
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * customerId	int	1		采购商id
     */

    public void customerInvoice_findOne(int customerId, RequestCallback<CustomerInvoice> callback) {
        requestService.customerInvoice_findOne(customerId, PublicCache.site_login).enqueue(callback);
    }

    //供应商登录
    public void loginData_supplier(Map<String, Object> params, RequestCallback<SupplierBean> callback) {
        requestService.loginData_supplier(params).enqueue(callback);
    }

    //注册
    public void supplier_registerData(Map<String, Object> params, int site, RequestCallback<Register> callback) {
        requestService.supplier_registerData(params, site).enqueue(callback);
    }

    //获取本地市场
    public void getMarket_local(Map<String, Object> params, int site, RequestCallback<MarketLocal> callback) {
        requestService.getMarket_local(params, site).enqueue(callback);
    }

    // 查询店铺的处罚记录
    public void getPunishScored(int storeId, RequestCallback<PunishScoreRecord> callback) {
        requestService.getPunishScored(storeId, PublicCache.site_login).enqueue(callback);
    }

    //共应商店铺状态设置
    public void offToday(Map<String, Object> params, RequestCallback<OffToday> callback) {
        requestService.offToday(params, PublicCache.site_login).enqueue(callback);
    }

    //获取订单的状态统计数量
    public void order_number(ResultInfoCallback<OrderStatus> callback) {
        requestService.order_number(PublicCache.site).enqueue(callback);
    }

    //查询供应商的银行账户
    public void getAccountByStoreId(int storeId, RequestCallback<AccountByStoreId_Resu> callback) {
        requestService.getAccountByStoreId(PublicCache.site_login, storeId).enqueue(callback);
    }


    //查询出供应商的货款(供应商登陆后也可以获得资金信息)
    public void getSupplyMoney(int entityId, ResultInfoCallback<SupplyMoney> callback) {
        requestService.getSupplyMoney(entityId, PublicCache.site_login).enqueue(callback);
    }

    //供应商申请提现
    public void getSupplierCashWithdrwalApplication(Map<String, Object> map, ResultInfoCallback<SupplierCash> callback) {
        requestService.getSupplierCashWithdrwalApplication(map, PublicCache.site_login).enqueue(callback);
    }


    //查询出供应商默认的银行卡
    public void getDefaultAccount(int storeId, ResultInfoCallback<DefaultAccount> callback) {
        requestService.getDefaultAccount(storeId, PublicCache.site_login).enqueue(callback);
    }


    //供应商设置默认银行卡
    public void setDefaultAccount(int entityId, int storeId, RequestCallback<DefaultAccountSet> callback) {
        requestService.setDefaultAccount(PublicCache.site_login, entityId, storeId).enqueue(callback);
    }

    //供应商交易明细列表
    public void logSupplierCapitalFlow(int pn, int ps, ResultInfoCallback<LogSupplierCapitalFlow> callback) {
        requestService.logSupplierCapitalFlow(PublicCache.site_login, pn, ps).enqueue(callback);
    }


    //供应商新版订单收款明细详情
    public void getSupplierOrderPayment(int orderid, String orderNO, RequestCallback<SupplyMoneyDetailBean> callback) {
        requestService.getSupplierOrderPayment(PublicCache.site_login, orderid, orderNO).enqueue(callback);
    }

    //供应商新版订单售后明细详情
    public void getSupplierOrderAfterSale(String afterSaleApplicationId, RequestCallback<SupplyMoneyDetailBean> callback) {
        requestService.getSupplierOrderAfterSale(PublicCache.site_login, afterSaleApplicationId).enqueue(callback);
    }

    //供应商新版订单提现明细详情
    public void getSupplierOrderGetCash(int withdrawalStatus, String cashWithdrawlEntityId, RequestCallback<SupplyMoneyDetailBean> callback) {
        requestService.getSupplierOrderGetCash(PublicCache.site_login, withdrawalStatus, cashWithdrawlEntityId).enqueue(callback);
    }

    //供应商新版查询出供应商订单条目相关信息
    public void getSupplierOrderFormItemDetailList(int storeId, int orderid, String orderNO, RequestCallback<SupplierOrderFormItemBean> callback) {
        requestService.getSupplierOrderFormItemDetailList(PublicCache.site_login, storeId, orderid, orderNO).enqueue(callback);
    }


    public void checkSupplierPhoneExist(String telephone, RequestCallback<CheckPhoneExist> callback) {
        requestService.checkSupplierPhoneExist(PublicCache.site, telephone).enqueue(callback);
    }

    public void supplier_refreshInfo(int entityId, RequestCallback<MyselftUpdateS> callback) {
        requestService.supplier_refreshInfo(PublicCache.site, entityId, SystemUtils.getAndroidId()).enqueue(callback);
    }

    //查询出全部押金列表
    public void getPackageForegiftList(Map<String, Object> map, RequestCallback<PackingCashBean> callback) {
        requestService.getPackageForegiftList(PublicCache.site_login,map ).enqueue(callback);
    }

    //获取门店列表
    public void  getHotelList(int customerId, RequestCallback<HotelList> callback){
        requestService.getHotelList(PublicCache.site_login,customerId).enqueue(callback);
    }

    //获取门店详情
    public void getShopDetail(int customerId, RequestCallback<ShopDetailBean> callback){
        requestService.getShopDetail(PublicCache.site_login,customerId).enqueue(callback);
    }

    //切换当前门店
    public void  getChangeShop(Map<String,Object> map,RequestCallback<PurchaseBean> callback){
        requestService.getChangeHotel(PublicCache.site_login,map).enqueue(callback);
    }

    //获取员工列表
    public void getEmployeesList(int loginUserId,RequestCallback<EmployeesListBean> callback){
        requestService.getEmployeesLsit(PublicCache.site_login,loginUserId).enqueue(callback);
    }

    //添加子员工
    public void  addSubUser(Map<String,Object> map, RequestCallback<AddSubuserBean> callback){
        requestService.addSubUser(PublicCache.site_login,map).enqueue(callback);
    }

    //修改员工
    public void updateCustomer(Map<String,Object> map,RequestCallback<UpdateCustomerBean> callback){
        requestService.updateCustomer(PublicCache.site_login,map).enqueue(callback);
    }

    //删除员工
    public void deleteCustomer(Map<String,Object> map,RequestCallback<DeleteCustomerBean> callback){
        requestService.deleteCustomer(PublicCache.site_login,map).enqueue(callback);
    }

    //更改负责人
    public void updateLeader(Map<String,Object> map,RequestCallback<UpdateLeaderBean> callback){
        requestService.updateLeader(PublicCache.site_login,map).enqueue(callback);
    }

    //获取当前范围内的酒店
    public void getSearchRange(RequestCallback<MapSearchRange> callback){
        requestService.getSearchRange(PublicCache.site_login).enqueue(callback);
    }

    //获取当前总经办内容
    public void getChainList(int customerId,String ownStores,RequestCallback<ChainShopList> callback){
        requestService.getChainList(PublicCache.site_login,customerId,ownStores).enqueue(callback);
    }

    //更新门店类型
    public void updateShopType(Map<String,Object> map,RequestCallback<UpdateCustomerBean> callback){
        requestService.updateShopType(PublicCache.site_login,map).enqueue(callback);
    }

    //更新门店的收货人或送货时间
    public void updateConsigneeOrTime(Map<String,Object> map,RequestCallback<UpdateAddressBean> callback){
        requestService.updateConsigneeOrTime(PublicCache.site_login,map).enqueue(callback);
    }

    //更新门店地址
    public void updateShopAddress(Map<String,Object> map,RequestCallback<UpdateAddressBean> callback){
        requestService.updateAddress(PublicCache.site_login,map).enqueue(callback);
    }

    //创建编辑总部
    public void createHeadquarters(Map<String,Object> map,RequestCallback<CreateHeadquartersBean> callback){
        requestService.createHeadquarters(PublicCache.site_login,map).enqueue(callback);
    }

    //获取员工所属门店列表
    public void getEmpStoreList(int id, RequestCallback<EmpoleeStoreList> callback){
        requestService.getEmpStoreList(PublicCache.site_login,id).enqueue(callback);
    }

    //判断是否在围栏里面
    public void  getFenceGid(String deviceId, String lat, String lon, RequestCallback<FenceGid> callback){
        requestService.getFenceGid(PublicCache.site_login,deviceId,lon,lat).enqueue(callback);
    }

    //获得编辑总经办相关联门店列表
    public void getEidtGmoList(int id,int gmoId,RequestCallback<GmoEditStore> callback){
        requestService.getEidtGmoList(PublicCache.site_login,id,gmoId).enqueue(callback);
    }

    //获取编辑员工信息
    public void getEidtEmployeeInfo(int id,int pid,RequestCallback<EidtEmployeeInfo> callback){
        requestService.getEidtEmployeeInfo(PublicCache.site_login,id,pid).enqueue(callback);
    }

    //获取配送仓列表
    public void getStationList(int storeId,RequestCallback<StationBean> callback){
        requestService.getStationList(storeId,PublicCache.site_login).enqueue(callback);
    }

    // 获取供应商年费标准
    public void getSupplierAnnalFee(int site,String storeId,RequestCallback<SupplierAnnalFeeInfo> callback ) {
        requestService.supplierAnnalFee(site,storeId).enqueue(callback);
    }

    public void getSupplierAnnalFeePay(int site,Map<String,Object> map,String storeId,RequestCallback<SupplierAnnalFeePayInfo> callback ) {
        requestService.getSupplierAnnalFeePay(site,map,storeId).enqueue(callback);
    }

    public void getSupplierAnnalFeePrivileges(int site,String storeId,RequestCallback<PrivilegesInfo> callback ) {
        requestService.getSupplierAnnalFeePrivileges(site,storeId).enqueue(callback);
    }

    public void getSupplierAnnalFeeTips(int site,String storeId,RequestCallback<FeeTips> callback ) {
        requestService.getSupplierAnnalFeeTips(site,storeId).enqueue(callback);
    }

    //获取配送司机位置
    public void getDriverLocation(String orderNo,String driverTel,int customerAddrId,RequestCallback<DriverLocation> callback){
        requestService.getDriverLocation(PublicCache.site_login,orderNo,driverTel,customerAddrId).enqueue(callback);
    }

    //获取物流投诉问题列表
    public void getLogisticsProblem(int type,int id,RequestCallback<ProblemList> callback){
        requestService.getLogisticsProblem(PublicCache.site_login,type,id).enqueue(callback);
    }

    //扶贫馆
    public void findPoverty(int flag,RequestCallback<Poverty> callback){
        requestService.findPoverty(PublicCache.site,flag).enqueue(callback);
    }

    //扶贫馆推荐
    public void findRecommend(int type, RequestCallback<PovertyAlleviationRecommend> callback){
        requestService.findRecommend(PublicCache.site,type).enqueue(callback);
    }

    //今日待入库订单-选择配送仓库（接货仓）
    public void getReceiveList(int storeId,RequestCallback<ReceiveList> callback){
        requestService.getReceiveList(storeId,PublicCache.site_login).enqueue(callback);
    }

    //推荐显示该商户对应的接货仓
    public void getReceiveWarehouseRecommendList(int storeId,RequestCallback<ReceiveWarehouseRecommendList> callback){
        requestService.getReceiveWarehouseRecommendList(PublicCache.site_login,storeId).enqueue(callback);
    }

    //开通接货仓服务
    public void openReceiveWarehouse(int storeId,int stationId,int receiveWarehouseId,int receiveType,int isStoreReceive,int open,RequestCallback<AddressUpdate> callback){
        requestService.openReceiveWarehouse(PublicCache.site_login,storeId,stationId,receiveWarehouseId,receiveType,isStoreReceive,open).enqueue(callback);
    }

    //关闭接货仓服务
    public void closeReceiveWarehouse(int storeId,int stationId,int receiveWarehouseId,int isOpen,int type,RequestCallback<AddressUpdate> callback){
        requestService.closeReceiveWarehouse(PublicCache.site_login,storeId,stationId,receiveWarehouseId,isOpen,type).enqueue(callback);
    }

    //供应商剩余接货费余额转出
    public void receiveFee(int storeId,RequestCallback<ReceiveFee> callback){
        requestService.receiveFee(storeId,PublicCache.site_login).enqueue(callback);
    }

    //购买接货包
    public void saveAndPay(int packageId,int store_id,int recharge_type,double pay_money,double buy_money,int is_automatic_renewal,double automatic_renewal_fee,String store_name,String store_mobile, RequestCallback<BuyPackageFee> callback){
        requestService.saveAndPay(packageId,store_id,recharge_type,pay_money,buy_money,PublicCache.site_login,is_automatic_renewal,automatic_renewal_fee,store_name,store_mobile).enqueue(callback);
    }

    //编辑商品 - 修改规格
    public void modifyInventory(int entity,int productId,BigDecimal price,BigDecimal lv2Value,BigDecimal lv3Value,int stock,BigDecimal avgPrice,int levelType,String lv1Unit,String lv2Unit,String lv3Unit,String avgUnit,RequestCallback<AddCategory> callback){
        requestService.modifyInventory(entity,productId,price,lv2Value,lv3Value,stock,avgPrice,levelType,lv1Unit,lv2Unit,lv3Unit,avgUnit).enqueue(callback);
    }
    //编辑商品 - 修改规格
    public void modifyInventory(int productId,BigDecimal price,int stock,RequestCallback<AddCategory> callback){
        requestService.modifyInventory(productId,price,stock).enqueue(callback);
    }

    //今日待入库，获取商品信息
    public void getProductNameList(int storeId,int stationId,int rwId,String truckTime,RequestCallback<TodayDeliveryProduct> callback){
        requestService.getProductNameList(storeId,stationId,PublicCache.site_login,rwId,truckTime).enqueue(callback);
    }

    //今日待入库，获取区域信息
    public void getProductAreaList(int storeId,int stationId,int rwId,String truckTime,RequestCallback<TodayDeliveryArea> callback){
        requestService.getProductAreaList(storeId,stationId,PublicCache.site_login,rwId,truckTime).enqueue(callback);
    }

    //今日待入库订单-修改订单打印状态
    public void updatePrintStatus(String itemIds,RequestCallback<AddCategory> callback){
        requestService.updatePrintStatus(itemIds).enqueue(callback);
    }

    //今日待入库订单-修改订单打印状态
    public void findFineOrderList(Map<String,Object> map,RequestCallback<PunishData> callback){
        requestService.findFineOrderList(PublicCache.site,map).enqueue(callback);
    }

    public void payManage(Map<String,Object> map,RequestCallback<PayManage> callback){
        requestService.payManage(PublicCache.site_login,map).enqueue(callback);
    }
    public void advfindList(Map<String,Object> map,RequestCallback<MarketingManage> callback){
        requestService.advfindList(PublicCache.site_login,map).enqueue(callback);
    }
    public void products(int storeId,String productName,int pn,int ps,RequestCallback<SelGoods> callback){
        requestService.products(storeId,PublicCache.site_login,productName,pn,ps).enqueue(callback);
    }
    public void addMyAdvertisement(Map<String,Object> map,RequestCallback<AvdOrder> callback){
        requestService.addMyAdvertisement(PublicCache.site_login,map).enqueue(callback);
    }
    public void myAdvertisementList(Map<String,Object> map,RequestCallback<TfAdvertisement> callback){
        requestService.myAdvertisementList(PublicCache.site_login,map).enqueue(callback);
    }
    public void adv_fee_pay(Map<String,Object> map,RequestCallback<SupplierAnnalFeePayInfo> callback){
        requestService.adv_fee_pay(PublicCache.site_login,map).enqueue(callback);
    }
    public void findPaymentList(Map<String,Object> map,RequestCallback<PaymentList> callback){
        requestService.findPaymentList(PublicCache.site_login,map).enqueue(callback);
    }
    public void advertisementOrFine(Map<String,Object> map,RequestCallback<AdvMoneyDetails> callback){
        requestService.advertisementOrFine(PublicCache.site_login,map).enqueue(callback);
    }
    public void addFinePopout(Map<String,Object> map,RequestCallback<AddFinePopout> callback){
        requestService.addFinePopout(PublicCache.site_login,map).enqueue(callback);
    }
    public void storeDiyFee(Map<String,Object> map,RequestCallback<StoreDiyFee> callback){
        requestService.storeDiyFee(PublicCache.site_login,map).enqueue(callback);
    }
    public void storeDiyFeeList(Map<String,Object> map,RequestCallback<ScannerFeeList> callback){
        requestService.storeDiyFeeList(PublicCache.site_login,map).enqueue(callback);
    }
    public void scannerFeeDrtail(Map<String,Object> map,RequestCallback<ScannerFeeListDetail> callback){
        requestService.scannerFeeDrtail(PublicCache.site_login,map).enqueue(callback);
    }

    //我的名片-可出售的商品分类
    public void getCurrentStoreCategoryList(int storeId,int status,RequestCallback<CurrentStoreCategory> callback){
        requestService.getCurrentStoreCategoryList(PublicCache.site_login,storeId,status).enqueue(callback);
    }

    //我的名片-可出售的商品分类-审核中主营分类
    public void getStoreCategoryCommodityList(int storeId,int status,RequestCallback<StoreCategoryCommodity> callback){
        requestService.getStoreCategoryCommodityList(PublicCache.site_login,storeId,status).enqueue(callback);
    }

    //我的名片-可出售的商品分类-删除主营类目
    public void deleteCommdityApply(int flag,int id,RequestCallback<DeleteCommodity> callback){
        requestService.deleteCommodityApply(flag,id).enqueue(callback);
    }

    //我的名片-可出售的商品分类-撤消申请
    public void cancelCommodityApply(int flag,int id, RequestCallback<AddCategory> callback){
        requestService.cancelCommodityApply(flag,id).enqueue(callback);
    }

    //供应商注册-分类搜索
    public void searchCategoryList(int id, String name, RequestCallback<GoodsCategorySelect> callback){
        requestService.searchCategoryList(id,name,PublicCache.site).enqueue(callback);
    }

    //供应商注册-获取三级分类可选数量限制
    public void getCommodityLimit(int id,int storeId,int flag,RequestCallback<CommodityLimit> callback){
        requestService.getCommodityLimit(id,PublicCache.site,storeId,flag).enqueue(callback);
    }

    //我的名片-可出售的商品分类-新增主营分类
    public void saveAddApply(int storeId,String username,String phone,String json,RequestCallback<AddCategory> callback){
        requestService.saveAddApply(PublicCache.site_login,storeId,username,phone,json).enqueue(callback);
    }

    //发布商品（商品属性及属性选择值）
    public void getCommodityLabel(int id,RequestCallback<CommodityLabel> callback){
        requestService.getCommodityLabel(PublicCache.site_login,id).enqueue(callback);
    }
    //发布商品（商品属性及属性选择值）
    public void getCommodityLabel(int id,int varietyEntityId,RequestCallback<CommodityLabel> callback){
        requestService.getCommodityLabel(PublicCache.site_login,id,varietyEntityId,1).enqueue(callback);
    }

    public void getCommodityAliasVariety(int id,RequestCallback<CommodityAliasVariety> callback){
        requestService.getCommodityAliasVariety(id,PublicCache.site_login).enqueue(callback);
    }
    public void getCommodityAliasVariety(int id,int isDrainageArea,RequestCallback<CommodityAliasVariety> callback){
        requestService.getCommodityAliasVariety(id,isDrainageArea,PublicCache.site_login).enqueue(callback);
    }

    public void getStandardList(int storeId,int commodityEntityId,int categoryId,int parentCategoryId,RequestCallback<StandardList> callback){
        requestService.getStandardList(PublicCache.site_login,storeId,commodityEntityId,categoryId,parentCategoryId).enqueue(callback);
    }

    //一键上传资质
    public void batchUpdCredentialImgs(int id,String imgs,String products,String qrCodes,RequestCallback<AddCategory> callback){
        requestService.batchUpdCredentialImgs(id,imgs,products,qrCodes).enqueue(callback);
    }

    //查找商品
    public void searchProducts(int id,String productName,int pn,int ps,RequestCallback<ResultInfo<ShopDetail_Goods>> callback){
        requestService.searchProducts(id,productName,pn,ps,99,11,1).enqueue(callback);
    }

    public void getCommodityShowStatus(int userId,RequestCallback<ShowStatus> callback){
        requestService.getCommodityShowStatus(userId).enqueue(callback);
    }

    public void setMainCategory(int userId,String username,String phone,int storeId,int saleType,String json,RequestCallback<AddCategory> callback){
        requestService.setMainCategory(userId,username,phone,storeId,PublicCache.site_login,10,"新鲜蔬菜",saleType,json).enqueue(callback);
    }


    public void validIsShow(int id,RequestCallback<ValidIsShow> callback){
        requestService.validIsShow(PublicCache.site_login,id).enqueue(callback);
    }

    public void getHaltSaleReason(int productId,int status,RequestCallback<HaltSaleProduct> callback){
        requestService.getHaltSaleReason(productId,PublicCache.site_login,status).enqueue(callback);
    }

    public void getStoreCategoryList(int storeId,RequestCallback<Eggs> callback){
        requestService.getStoreCategoryList(PublicCache.site_login,storeId).enqueue(callback);
    }
    public void customerCommunity_find(int storeId,Map<String,Object> map,RequestCallback<XiaoQuAddressItem> callback){
        requestService.customerCommunity_find(storeId,map).enqueue(callback);
    }
    public void customerCommunity_find_addresss(Map<String,Object> map,RequestCallback<CommunityAddress> callback){
        requestService.customerCommunity_find_addresss(PublicCache.site_login,map).enqueue(callback);
    }

    public void customerCommunity_update(Map<String,Object> map,RequestCallback<CommunityAddressUpdate> callback){
        requestService.customerCommunity_update(PublicCache.site_login,map).enqueue(callback);
    }
    public void specialMerchants( Map<String, Object> params ,RequestCallback<HomeStore> callback) {
        requestService.specialMerchants(params,PublicCache.site).enqueue(callback);
    }

    public void pageListByVisitor( Map<String, Object> params ,RequestCallback<PickUpOrder> callback) {
        requestService.pageListByVisitor(PublicCache.site,params).enqueue(callback);
    }

    public void findCustomerCommunityDeliveryCode( Map<String, Object> params ,RequestCallback<DeliveryCode> callback) {
        requestService.findCustomerCommunityDeliveryCode(PublicCache.site,params).enqueue(callback);
    }
    public void getDeliverFee( Map<String, Object> params ,RequestCallback<DeliverFee> callback) {
        requestService.getDeliverFee(PublicCache.site,params).enqueue(callback);
    }
    public void getTzServiceFee( Map<String, Object> params ,RequestCallback<TzServiceFee> callback) {
        requestService.getTzServiceFee(PublicCache.site,params).enqueue(callback);
    }
    public void getDeliverTime(  RequestCallback<DeliverTime> callback) {
        requestService.getDeliverTime(PublicCache.site).enqueue(callback);
    }
    public void updateCommunityRef( Map<String,Object> map ,RequestCallback<UpdateCommunityRef> callback) {
        requestService.updateCommunityRef(map).enqueue(callback);
    }
    public void updateDeliverAddress( Map<String,Object> map ,RequestCallback<UpdateCommunityRef> callback) {
        requestService.updateDeliverAddress(map).enqueue(callback);
    }

}

