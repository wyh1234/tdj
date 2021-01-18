package cn.com.taodaji.model.presenter;

import com.base.entity.ResultInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.model.entity.*;
import cn.com.taodaji.model.event.EmpoleeStoreList;
import cn.com.taodaji.model.event.FenceGid;
import cn.com.taodaji.model.event.OrderDeleteEvent;
import cn.com.taodaji.model.event.OrderStatusEvent;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 *
 */
public interface RequestService {

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

    @GET("dict/findAll")
    Call<DictFindAll> dictFindAll(@Query("site") int site);


    /**
     * 1、查询出该区域所有的专题活动
     * <p/>
     * url	方法	说明
     * /specialActivities/findAll	GET	查询出该区域所有的专题活动
     */
    @GET("specialActivities/findAll")
    Call<ResultInfo<SpecialActivities>> specialActivities_findAll(@Query("site") int site, @Query("flag") int flag, @Query("isShowC") int isShowC);


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
    @GET("store/recommend/{type}")
    Call<ResultInfo<StoreRecommend>> store_recommend(@Path("type") int type, @Query("site") int site, @Query("pn") int pn, @Query("ps") int ps);

    //9宫格数据请求
    @GET("product/commendForHomePage")
    Call<ResultInfo<HomepageGridDatas>> commendForHomePage(@Query("site") int site, @Query("pn") int pn, @Query("ps") int ps);

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
    @GET("product/commendCategory")
    Call<HomePageFuncationButton> commendCategory(@Query("site") int site);

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

    @GET("product/findCommendProduct/{categoryId}")
    Call<ResultInfo<FindCommendProduct>> findCommendProduct(@Path("categoryId") int categoryId, @Query("site") int site, @Query("pn") int pn, @Query("ps") int ps);


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

    @GET("specialActivitiesProducts/findByProductType/{specialActivitiesId}")
    Call<FindByActivitiesID> findByActivitiesID(@Path("specialActivitiesId") int specialActivitiesId, @QueryMap Map<String, Object> map, @Query("site") int site);


    @GET("specialActivitiesProducts/findByActivitiesID/{specialActivitiesId}")
    Call<FindByActivitiesID> findByActivitiesIDs(@Path("specialActivitiesId") int specialActivitiesId, @QueryMap Map<String, Object> map, @Query("site") int site);
    /**
     * 检查是否需要更新
     */
    @GET("androidUpdate/findAll")
    Call<AndroidUpdate> androidUpdate();


    //图片上传
    @Multipart
    @POST("image/upload")
    Call<ImageUpload> upload(@Part MultipartBody.Part part);

    //图片上传
    @Multipart
    @POST("image/uploadImgUrl")
    Call<ImageUpload> imageUpload(@Part MultipartBody.Part part);


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
    @GET("common/cityList/findByIsActive")
    Call<ResultInfo<FindByIsActive>> findByIsActive(@Query("isActive") int isActive);


    @GET("common/cityList/findByIsActive")
    Call<ResultInfo<Object>> findByIsActive1(@Query("isActive") int isActive);

    //忘记密码
    @FormUrlEncoded
    @PUT("{customer}/password/forget")
    Call<ForgetPassword> forget_pwd(@Path("customer") String customer, @FieldMap Map<String, Object> params, @Field("site") int site);

    //密码修改
    @FormUrlEncoded
    @PUT("{supplier}/password/modify")
    Call<UpdatePassword> update_password(@Path("supplier") String path, @FieldMap Map<String, Object> params, @Field("site") int site);


    /**
     * 查询所有的市场信息
     * <p/>
     * url	方法	说明
     * /market/findAll	GET	查询所有的市场信息
     */
    @GET("market/findAll")
    Call<MarketLocal> getMarket_ListAll(@Query("site") int site);

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
    @GET("market/{marketId}/stores")
    Call<ResultInfo<MarketShopList>> getMarket_shopInformation(@Path("marketId") int marketId, @Query("site") int site, @Query("pn") int pn, @Query("ps") int ps);

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
    @GET("store/findOne/{shopId}")
    Call<ResultInfo<ShopDetail>> getShop_detail(@Path("shopId") int shopId, @Query("userType") int userType, @Query("personId") int personId, @Query("site") int site);

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
    @GET("store/{shopId}/products?status=1")
    Call<ResultInfo<ShopDetail_Goods>> getShop_goods_detail(@Path("shopId") int shopId, @Query("site") int site, @Query("pn") int pn, @Query("ps") int ps);

    @GET("store/{shopId}/products?")
    Call<ResultInfo<ShopDetail_Goods>> getnewShop_goods_detail(@Path("shopId") int shopId, @QueryMap Map<String, Object> map,@Query("site") int site, @Query("pn") int pn, @Query("ps") int ps);


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
    @GET("product/findOne/{entityId}")
    Call<ResultInfo<GoodsInformation>> showGoodsInformation(@Path("entityId") int entityId, @Query("userType") int userType, @Query("personId") int personId, @Query("site") int site);

    /**
     * 获取商品提成及参考规格
     * url	方法	说明
     * /product/findProductCommission	POST	获取商品提成及参考规格gd
     * 请求参数
     * 参数名	类型	必须(1是/0否)	说明
     * commodityId	num	1	三级分类id
     * site	num	1	站点id
     * categoryId	num	1	二级分类id
     */
    @FormUrlEncoded
    @POST("product/findProductCommission")
    Call<ResultInfo<FindProductCommission>> findProductCommission(@Field("categoryId") int categoryId, @Field("commodityId") int commodityId, @Field("site") int site,@Field("store") int store);


    //购物车刷新接口
    @GET("product/findOneBase")
    Call<CartNet> product_findOneBase(@Query("specIds") String specIds, @Query("customerId") int customerId, @Query("site") int site);

    //意见反馈
    @FormUrlEncoded
    @POST("feedback/save")
    Call<FeedbackSave> feedback_save(@FieldMap Map<String, Object> params, @Field("site") int site);

    //新品需求
    @FormUrlEncoded
    @POST("fooddemand/save")
    Call<FooddemandSave> fooddemand_save(@FieldMap Map<String, Object> params, @Field("site") int site);

    @FormUrlEncoded
    @POST("specialMerchants/findIcon")
    Call<HomeStore> specialMerchants(@FieldMap Map<String, Object> params, @Field("site") int site);

    /**
     * 获取订单列表
     * <p/>
     * url	方法	说明
     * /order/pageList	GET	获取订单列表
     * 请求参数
     * <p/>
     * PS:【采购商的待发货状态包括wait_seller_confirm_goods,wait_seller_send_goods,seller_print_goods三个状态】
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * userType	int	1	1表示批发商 0表示酒店
     * status	str	0	订单状态筛选，多个以逗号分隔 ：wait_buyer_pay-待付款，wait_seller_confirm_goods-待确认，wait_seller_send_goods-待发货，wait_buyer_confirm_goods-待收货
     * pn	int	0	页数，默认为1
     * ps	int	0	每页显示数量，默认为10
     */
    @GET("order/pageList")
    Call<ResultInfo<OrderList>> order_pageList(@Query("site") int site, @Query("userType") int userType, @Query("status") String status, @Query("pn") int pn, @Query("ps") int ps);

    /**
     * //修改订单状态
     * 请求参数
     * <p/>
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * orderIds	string	1		订单ID - orderIds值
     * status	string	1		修改订单的状态 - trade_canceled：取消订单，trade_success：确认收货，wait_seller_send_goods：确认订单
     * userType	int	1		用户类型 - 0：酒店；1：供应商
     */
    @FormUrlEncoded
    @POST("order/modifyStatus")
    Call<OrderStatusEvent> modifyStatus(@Field("site") int site, @Field("userType") int userType, @Field("status") String status, @Field("orderIds") String orderIds, @Field("orderNo") String orderNo);

    /**
     * 获取订单详情
     * <p/>
     * url	方法	说明
     * /order/findOne	GET	获取订单详情
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * orderId	int	0	批发商使用订单id查询
     * orderNO	String	0	采购商通过订单编号查询
     */
    @GET("order/findOne")
    Call<ResultInfo<OrderDetail>> order_findOne(@QueryMap Map<String, Object> map, @Query("site") int site);

    // 删除订单
    @GET("order/delete/{orderId}")
    Call<OrderDeleteEvent> order_delete(@Path("orderId") String orderIds, @Query("site") int site);

    //撤销订单到购物车
    @GET("order/deleteReally/{orderIds}")
    Call<ResultInfo> order_deleteReally(@Path("orderIds") String orderIds, @Query("site") int site);


    //支付方式
    @GET("order/paymethod")
    Call<ResultInfo<OrderPayMethod>> paymethod(@Query("site") int site);

    /**
     * 配送方式
     * <p/>
     * url	方法	说明
     * order/shipmethod	GET	支付方式
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * addressId	int	1	收货地址id
     */
    @GET("order/shipmethod")
    Call<ResultInfo<OrderShipmethod>> shipmethod(@Query("site") int site, @Query("addressId") String addressId);

    /**
     * * 打印发货
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * orderId	int	1	订单id
     * orderItemIds	String	1	订单详情中的items下的itemId,多个用逗号分隔
     */
    @FormUrlEncoded
    @POST("order/item/bindQrCode")
    Call<DeliverGoods> bindQrCode(@Field("site") int site, @Field("orderId") int orderId, @Field("orderItemIds") String orderItemIds);


    @GET("searchItem/findAll/findListItems")
    Call<Searchhost> getSearchhost(@Query("site") int site);

    @GET("product/android/search/findListByName/{goodsName}")
    Call<SearchGoods_Resu> getSearchGoods(@Query("site") int site, @Path("goodsName") String goodsName);


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
    @GET("product/findListByName")
    Call<ResultInfo<SearchGoods3>> getSearchGood(@Query("userType") int userType, @Query("site") int site,
                                                 @Query("name") String name, @Query("sort") String sort, @Query("isAsc") int isAsc,
                                                 @Query("isP") int isP, @Query("productCriteria") String productCriteria, @Query("pn") int pn, @Query("ps") int ps,
                                                 @Query("isCanteen") int isCanteen);

    /**
     * 查询店铺出售相关商品的信息
     * <p/>
     * url	方法	说明
     * /store/storeFind/:site/:name	GET	查询所有的市场信息
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * :site	num	1	商圈ID，默认2
     * :name	str	1	商品的名字，可以是全拼，或者简拼
     * pn	num	0	分页起始页，默认pn=1
     * ps	num	0	分页每页查询条数，默认ps=10
     */

    @GET("store/storeFind/{shopName}")
    Call<ResultInfo<SearchShop>> getSearchShop(@Path("shopName") String shopName, @Query("userType") int userType, @Query("site") int site, @Query("pn") int pn, @Query("ps") int ps);

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
    @GET("store/findName/{site}/{name}")
    Call<StoreFindName> getStoreFindName(@Path("site") int site, @Path("name") String name);

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
    @GET("product/search/findName/{name}")
    Call<ProductFindName> getProductFindName(@Path("name") String name, @Query("site") int site,@Query("isCanteen") int isCanteen);


    /**
     * 查询出全部商品信息
     * <p/>
     * url	方法	说明
     * /product/search/findPageList	GET	查询出全部商品信息
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * site	int	1	商圈ID、site=2表示襄阳地区
     * status	int	1	商品的状态、status=1表示上架、status=2表示下架、status=3表示待审核
     * pn	int	1	分页、pn默认1
     * ps	int	1	分页、pn默认10
     * sort	String	0	排序、sort={'price':0} 表示价格升序排序、sort={'qty':1}表示按销量排序、sort={'popularity':0}表示按照人气排序、注意：值0表示升序 1 表示降序
     * name	String	0	按照名称搜索出商品信息
     * categoryId	int	0	商品的类别
     * includeSubCategory	int	0	商品的类别是否有子类别，includeSubCategory=1 表示有子类别， 0 表示没有子类别
     * 具体用法
     * <p/>
     * 1、【价格排序】 /product/search/findPageList?site=2&status=1&pn=1&ps=10&sort={'price':0}
     * 2、【销量排序】 /product/search/findPageList?site=2&status=1&pn=1&ps=10&sort={'qty':1}
     * 3、【人气排序】 /product/search/findPageList?site=2&status=1&pn=1&ps=10&sort={'popularity':0}
     * 4、【根据其名称（昵称）查询出商品信息】 /product/search/findPageList?site=2&status=1&pn=1&ps=10&name=土豆
     * 5、【根据类别查询出商品信息（一级）】 /product/search/findPageList?site=2&status=1&categoryId=14&includeSubCategory=1&pn=1&ps=10
     * 6、【根据类别查询出商品信息（二级）】 /product/search/findPageList?site=2&status=1&categoryId=14&pn=1&ps=10
     * 7、【查询出全部商品信息】 /product/search/findPageList?site=2&status=1&pn=1&ps=10
     */
    @GET("product/search/findPageList")
    Call<ResultInfo<PickFoodGoodsList>> findPageList(@QueryMap Map<String, Object> map, @Query("site") int site);

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
    @GET("product/oftenBuy")
    Call<ResultInfo<PickFoodGoodsList>> oftenBuy(@Query("site") int site, @Query("catalogId") int catalogId, @Query("entityId") int entityId, @Query("entityType") int entityType, @Query("pn") int pn, @Query("ps") int ps);

    @GET("product/oftenBuy")
    Call<ResultInfo<PickFoodGoodsList>> oftenBuy(@Query("site") int site, @Query("catalogId") int catalogId, @Query("entityId") int entityId, @Query("entityType") int entityType,
                                                 @Query("isCanteen") int isCanteen,@Query("pn") int pn, @Query("ps") int ps);

    //商品管理搜索
    @GET("store/{store}/products")
    Call<ResultInfo<HomepageGridData>> getShopManagementListSearch(@Path("store") int store,
                                                                   @Query("site") int site, @Query("status") int status,
                                                                   @Query("pn") int pn, @Query("ps") int ps, @Query("productName") String productName,
                                                                   @Query("sort")String sort, @Query("isCanteen")int isCanteen);

    //商品单位
    @GET("product/unitList")
    Call<GoodsUnit_Resu> getUnitList(@Query("site") int site);


    //下架
    @GET("product/takeDown/{storeId}/{productId}")
    Call<TakeDown> takeDown(@Path("storeId") int storeId, @Path("productId") int productId, @Query("site") int site);

    //上架
    @GET("product/takeUp/{storeId}/{productId}")
    Call<TakeUp> takeUp(@Path("storeId") int storeId, @Path("productId") int productId, @Query("site") int site);

    //编辑商品,重新编辑
    @PUT("product/update")
    @FormUrlEncoded
    Call<GoodsUpdate> goodsUpdate(@FieldMap Map<String, Object> map, @Field("site") int site);

    //发布商品
    @POST("product/save")
    @FormUrlEncoded
    Call<ResultInfo<GoodsInformation>> goodsCreate(@FieldMap Map<String, Object> map, @Field("site") int site);

    //删除商品
    @PUT("product/deleteOneProduct")
    @FormUrlEncoded
    Call<GoodsDelete> goodsDelete(@FieldMap Map<String, Object> map, @Field("site") int site);

    /**
     * 查询商品分类(一级、二级)
     * <p/>
     * url	方法	说明
     * /product/findCategoryList	GET	查询商品分类(一级、二级)
     */
    @GET("product/findStoreCategoryList")
    Call<GoodsCategoryList_Resu> newfindCategoryList(@Query("site") int site,@Query("flag") int flag,@Query("storeId") int storeId);



    /**
     * 查询商品分类(一级、二级)
     * <p/>
     * url	方法	说明
     * /product/findCategoryList	GET	查询商品分类(一级、二级)
     */
    @GET("product/findCategoryList")
    Call<GoodsCategoryList_Resu> findCategoryList(@Query("site") int site,@Query("fenceGid") String fenceGid);


    /**
     * 查询商品分类(三级分类)
     * <p/>
     * url	方法	说明
     * /commodity/:categoryId/list	GET	查询商品分类(三级分类)
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * categoryId	int	1	父级菜单的categoryId
     */
    @GET("commodity/{categoryId}/list")
    Call<GoodsCategoryListNext_Resu> findCategoryList3(@Path("categoryId") int categoryId, @Query("site") int site);

    //自定义商品类别添加
    @FormUrlEncoded
    @POST("commodity/addCategory")
    Call<AddCategory> addCategory(@FieldMap Map<String, Object> map, @Field("site") int site, @Field("siteName") String siteName);

    //判断商品类别名是否存在
    @GET("commodity/checkIfCategoryExist")
    Call<CheckIfCategoryExist> checkIfCategoryExist(@Query("name") String name, @Query("site") int site);


    //发送短信
    @FormUrlEncoded
    @POST("common/sendSms")
    Call<SmsCode> smsCode(@FieldMap Map<String, Object> params, @Field("site") int site);

    //短信验证
    @FormUrlEncoded
    @POST("/common/checkSmsCode")
    Call<CheckSmsCode> checkSmsCode(@Field("site") int site, @Field("telephone") String phone, @Field("smsCode") String smsCode);

    //手机号换绑
    @FormUrlEncoded
    @PUT("{customer}/toChangeTel")
    Call<ChangeTelUrl> changeTelUrl(@Path("customer") String path, @FieldMap Map<String, Object> params, @Field("site") int site);


    /**
     * 添加银行账户
     * 参数名	类型	必须(1是/0否)	说明
     * provinceId	int	1	省ID
     * cityId	int	1	城市ID
     * storeId	int	1	店铺ID|
     * storeName	String	1	店铺名称
     * marketId	int	1	市场ID
     * bankName	String	1	银行名字
     * accountNo	String	1	银行账号
     * ownerName	String	1	银行卡真实户名
     * bankAddress	String	1	银行卡开户行地址
     * bankType	int	1	银行类型，1、工行 2、建行 3、农行 4、中行
     */
    @FormUrlEncoded
    @POST("finance/bankAccount/save")
    Call<BankAccount> bankAccount(@FieldMap Map<String, Object> map, @Field("site") int site);

    //解绑银行卡
    @FormUrlEncoded
    @POST("finance/bankUnbundling")
    Call<BankUnbundling> bankUnbundling(@Field("site") int site, @Field("entityId") int entityId, @Field("storeId") int storeId);


    /**
     * 申请售后退款或者退货
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * customerId	int	1	采购商的ID
     * storeId	int	1	供应商店铺ID
     * orderId	int	1	订单ID
     * orderItemId	int	1	订单条目ID
     * productImg  	str	1	产品图片
     * sku       	str	1	商品SKU编号
     * unit       	str	1	商品单位
     * name      	str	1	商品名称
     * nickName  	str	1	商品昵称名称
     * price     	double	1	商品价格
     * amount    	int	1	退货的数量
     * problemDescription	str	1	问题描述
     * certificatePhotos 	str	1	上传的凭证照片
     * status            	int	1	售后处理：status: 2退款中 3换货中 4拒绝退款 5拒绝换货 6完成售后操作
     * applyType         	int	1	申请类别:1表示申请退款、:2表示申请退货
     * customerName      	str	1	采购商姓名
     */
    @FormUrlEncoded
    @POST("afterSalesApplication/create")
    Call<ResultInfo<AfterSales>> afterSalesApplication(@FieldMap Map<String, Object> map, @Field("site") int site);

    /**
     * 2、修改申请售后订单状态
     * url	方法	说明
     * /afterSalesApplication/afterSaleHandler	POST	修改申请售后订单状态
     * 请求参数
     * 参数名	类型	必须(1是/0否)	说明
     * id	int	1	申请售后订单的ID
     * status	int	1	售后处理：status: 2退款中 3换货中 4拒绝退款  5拒绝换货  6完成售后操作
     */
    @FormUrlEncoded
    @POST("afterSalesApplication/afterSaleHandler")
    Call<AfterSaleHandler> afterSaleHandler(@Field("site") int site, @Field("id") int id, @Field("status") int status);

    /**
     * 3、采购商、供应商 查询出供货款、退款列表
     * url	方法	说明
     * /afterSalesApplication/findPageByCSIdList	GET	修改申请售后订单状态
     * 请求参数
     * 参数名	类型	必须(1是/0否)	说明
     * type	int	1	type表示0、采购商类型 1、供应商类型
     * id	int	1	采购商id 或者 店铺ID
     * pn	int	1	分页页数
     * ps	int	1	分页每页条数
     */

    @GET("afterSalesApplication/findPageByCSIdList")
    Call<PageByCSIdList> findPageByCSIdList(@Query("site") int site, @Query("type") int type, @Query("id") int id, @Query("pn") int pn, @Query("ps") int ps);


    /**
     * 4、根据entityId注销售后订单
     * <p/>
     * url	方法	说明
     * /afterSalesApplication/deleteSalesAppByEntityId	POST	根据entityId注销售后订单
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * entityId	int	1	申请售后订单的entityId
     * 返回值
     * <p/>
     * {
     * "err": 0,
     * "data": 1,
     * "msg": "success"
     * }
     */
    @FormUrlEncoded
    @POST("afterSalesApplication/deleteSalesAppByEntityId")
    Call<DeleteSalesAppByEntityId> deleteSalesAppByEntityId(@Field("site") int site, @Field("entityId") int entityId,@Field("orderId") int orderId,@Field("orderItemId") int orderItemId);


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
    @FormUrlEncoded
    @POST("recharge/create")
    Call<RechargeCreate> recharge_create(@Field("site") int site, @Field("customerId") int customerId, @Field("subUserId") int sub_userId, @Field("customerName") String customerName, @Field("paymentMethod") String paymentMethod, @Field("remarks") String remarks, @Field("capital") float capital);


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
    @FormUrlEncoded
    @POST("order/toEvaluation")
    Call<EvaluateIntegral> toEvaluation(@FieldMap Map<String, Object> map, @Field("site") int site);

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
    @GET("order/evaluation/pageList")
    Call<EvaluationList> evaluation_pageList(@QueryMap Map<String, Object> map, @Query("site") int site);


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

    @GET("order/evaluation/pPageList")
    Call<EvaluationList> evaluation_pPageList(@Query("site") int site, @Query("creditLevel") int creditLevel, @Query("hasImg") int hasImg, @Query("productId") int productId, @Query("pn") int pn, @Query("ps") int ps);

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

    @GET("order/evaluation/statics")
    Call<EvaluationStatics> evaluation_statics(@Query("site") int site, @Query("productId") int productId);

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
    @FormUrlEncoded
    @POST("order/evaluation/update")
    Call<ResultInfo<EvaluationUpdate>> evaluation_update(@Field("site") int site, @Field("entityId") int entityId, @Field("replyContent") String replyContent);


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
    @FormUrlEncoded
    @POST("order/evaluation/update")
    Call<ResultInfo> buyer_evaluation_update(@Field("site") int site, @FieldMap Map<String, Object> map);


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
    @FormUrlEncoded
    @PUT("supplier/update")
    Call<ResultInfo> qq_update(@Field("site") int site, @Field("entityId") int entityId, @Field("qqNumber") String qqNumber);


    /**
     * 我的代金券统计
     * <p>
     * url	方法	说明
     * /coupons/statistics?userId=45&userType=0	GET	统计该用户下的代金券个数
     */
    @GET("coupons/statistics")
    Call<CouponsStatistics> coupons_statistics(@Query("site") int site, @Query("userId") int userId, @Query("userType") int userType);


    /**
     * 我的代金券列表
     * <p>
     * url	方法	说明
     * /coupons/findByUser?userId=45&userType=0&status=0	GET	查询该用户下的代金券，status：0未使用，1已使用，2已过期， -1或者空为所有
     */

    @GET("coupons/findByUser")
    Call<CouponsFindByUser> coupons_findByUser(@Query("site") int site, @Query("userId") int userId, @Query("userType") int userType, @Query("status") int status, @Query("pn") int pn, @Query("ps") int ps);
    @GET("coupons/findVoucherList")
    Call<CouponsFindByUser> coupons_findVoucher(@Query("site") int site, @Query("userId") int userId, @Query("amountIsAsc") int amountlsAsc,@Query("createTimeIsAsc") int createTimelsAsc,
                                                @Query("isC") int isC,
                                                @Query("userType") int userType, @Query("pn") int pn, @Query("ps") int ps);


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
    @FormUrlEncoded
    @POST("coupons/getCouponByCode")
    Call<ResultInfo> coupons_getCouponByCode(@Field("site") int site, @Field("userId") int userId,@Field("isC") int isC, @Field("userType") int userType, @Field("code") String code);

    /**
     * 可领取代金券列表
     * <p>
     * url	方法	说明
     * /coupons/findReceiveList?userId=45&userType=0	GET	查询该用户可以领取的代金券
     */

    @GET("coupons/findReceiveList")
    Call<CouponsFindreciveList> coupons_chooseCouponList(@Query("site") int site, @Query("userId") int userId,@Query("isC") int isC, @Query("userType") int userType, @Query("pn") int pn, @Query("ps") int ps);


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

    @FormUrlEncoded
    @POST("coupons/received")
    Call<ResultInfo> coupons_received(@FieldMap Map<String, Object> map, @Field("site") int site);


    /**
     * 6、 去结算时选择代金券列表
     * <p>
     * url	方法	说明
     * /coupons/chooseCouponList	POST	查询该用户可以领取的代金券
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
    @FormUrlEncoded
    @POST("coupons/chooseCouponList")
    Call<CouponsChooseCouponList> coupons_chooseCouponList(@Field("site") int site, @Field("userId") int userId, @Field("userType") int userType,
                                                           @Field("productInfo") String productInfo);
    @FormUrlEncoded
    @POST("coupons/chooseNewCouponList")
    Call<NewCouponsChooseCouponList> coupons_chooseNewCouponList(@Field("site") int site, @Field("userId") int userId, @Field("userType") int userType,
                                                           @Field("productInfo") String productInfo);


    @FormUrlEncoded   //如果是post请求加上这个，如果是get请求不加
    @POST("order/calculate/freight")
    Call<FreightParticulars> freight_particulars(@Field("site") int site, @Field("productInfo") String productInfo);

    @GET("shippingFeeRecord/deliveryFee/description")
    Call<ResultInfo<List<DistributionFeeStatementBean>>> distribution_fee_statement(@Query("site") int site);

    @GET("order/freight/detail")
    Call<FreightParticularsNew> freight_particulars_new(@Query("site") int site, @Query("orderNo") String orderNo);

    @GET("afterSalesApplication/findAfterSalesAppyDetailById/{id}")
    Call<ResultInfo<RefundDetail>> after_details(@Path("id") int entity_id, @Query("site") int site);

    @GET("afterSalesApplication/findAfterSalesAppyDetailByAfterSalesNo/{afterSalesNo}")
    Call<ResultInfo<RefundDetail>> after_details_salesNo(@Path("afterSalesNo") String afterSalesNo, @Query("site") int site);

    @GET("afterSalesApplication/findAfterSalesAppyDetailByItemId/{orderItemId}")
    Call<ResultInfo<RefundDetail>> after_details_order(@Path("orderItemId") int orderItemId, @Query("site") int site);

    @GET("commodity/findByName")
    Call<GoodsClassifySearchBean> goods_classify_search(@Query("site") int site, @Query("name") String name, @Query("storeId") String storeId);

    @GET("order/batch/confirm/{storeId}")
    Call<YiJianQueRenBean> yi_jian_que_ren(@Path("storeId") int storeId, @Query("site") int site);

    @GET("order/{storeId}/sendList")
    @Headers("charset:UTF-8")
    Call<TodayDeliverGoodsOrderBean> today_deliver_goods_order(@Path("storeId") int storeId, @Query("site") int site,
                                                               @Query("truckTime") String trucktime,@Query("stationId") int stationId,
                                                               @Query("rwId") int rwId,@Query("regionCollectionId") int regionId,@Query("productId") int productId,
                                                               @Query("printStatus") int printStatus,@Query("productNickName") String productNickName, @Query("isC") int isC,@Query("expectDeliveredDate") String expectDeliveredDate);


    /**
     * 供应商实名认证
     * <p>
     * url	方法	说明
     * /supplier/reversion/toAuth	PUT	供应商实名认证
     * 参数说明
     * <p>
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * entityId	int	1		用户entityId
     * address	str	1		用户身份证地址
     * gender	str	1		字符串'男'或者'女'
     * frontageIdcardImageUrl	str	1		身份证前端的图片
     * backIdcardImageUrl	str	1		身份证后端的图片
     * birthday	str	1		供应商生日，格式：19910124
     * expirationDate	str	1		超期的日期，格式：19910124
     * isAuth	str	1		是否认证1表示认证，0表示未认证
     * idcardNumber	str	1		身份证号码
     */
    @FormUrlEncoded
    @PUT("supplier/reversion/toAuth")
    Call<ResultInfo> supplier_reversion(@FieldMap Map<String, Object> map, @Field("site") int site);

    /**
     * 采购商实名认证
     * <p>
     * url	方法	说明
     * /customer/reversion/toAuth	PUT	采购商实名认证
     * 参数说明
     * <p>
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * entityId	int	1		用户entityId
     * address	str	1		用户身份证地址
     * gender	str	1		字符串'男'或者'女'
     * frontageIdcardImageUrl	str	1		身份证前端的图片
     * backIdcardImageUrl	str	1		身份证后端的图片
     * birthday	str	1		供应商生日，格式：19910124
     * expirationDate	str	1		超期的日期，格式：19910124
     * isAuth	str	1		是否认证1表示认证，0表示未认证
     * identificationCard	str	1		身份证号码
     */
    @FormUrlEncoded
    @PUT("customer/reversion/toAuth")
    Call<ResultInfo> customer_reversion(@FieldMap Map<String, Object> map, @Field("site") int site);


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

    @FormUrlEncoded
    @POST("pushMessage/customer/relation/token")
    Call<PushMessageCustomerToken> pushMessageCustomer(@FieldMap Map<String, Object> map, @Field("site") int site);

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
    @FormUrlEncoded
    @POST("pushMessage/supplier/relation/token")
    Call<PushMessageCustomerToken> pushMessageSupplier(@FieldMap Map<String, Object> map, @Field("site") int site);

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

    @FormUrlEncoded
    @POST("pushMessage/logout/delete/token")
    Call<ResultInfo<Object>> pushMessageLogout(@Field("site") int site, @Field("userType") int userType, @Field("phoneNumber") String phoneNumber);

    /**
     * 4 分类推荐商品
     * <p/>
     * URL	方法	说明
     * /pushMessageRecords/pageList	GET	查询出列表消息
     * 请求参数
     * <p/>
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * entityId	int	1		采购商或者供应商的entityId
     * pn	    int	1		分页起始页
     * ps	    int	1		分页每页条数
     */

    @GET("pushMessageRecords/pageList")
    Call<ResultInfo<GetNews>> getNews(@Query("site") int site, @Query("entityId") int entityId, @Query("pn") int pn, @Query("ps") int ps);


    /**
     * url	方法	说明
     * /pushMessageRecords/update	POST	更新推送列表已经读过的消息
     * <p>
     * 参数说明
     * <p>
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * entityId	int	1		记录的entityId
     * isRead	int	1		如果是1表示已读，0表示未读
     */
    @FormUrlEncoded
    @POST("pushMessageRecords/update")
    Call<ResultInfo> getReadNews(@Field("site") int site, @Field("entityId") int entityId, @Field("isRead") int isRead);

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
    @FormUrlEncoded
    @POST("product/saveDetail")
    Call<ResultInfo> product_saveDetail(@Field("site") int site, @Field("product_id") int product_id, @Field("details") String details);

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

    @GET("product/findProductDetail/{product_id}")
    Call<FindProductDetail> product_findProductDetail(@Path("product_id") int product_id, @Query("site") int site);

    /**
     * url	方法	说明
     * /product/unitList	GET	获取规格列表
     * 返回值
     * data：baseUnit 基础规格单位，level1Unit一级可选规格列表，listUnit全部规格列表
     */
    @GET("product/unitList")
    Call<GetUnitList> getUnitsList(@Query("site") int site);

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
    @FormUrlEncoded
    @POST("productSpecification/save")
    Call<ResultInfo> saveSpecification(@FieldMap Map<String, Object> map, @Field("site") int site);


    /**
     * url	方法	说明
     * /productSpecification/delete	POST	删除规格
     * <p>
     * 参数名	类型	必须(1是/0否)	说明
     * productId	num	1	商品id
     * entityId	num	1	规格id
     */
    @FormUrlEncoded
    @POST("productSpecification/delete")
    Call<ResultInfo> delete(@Field("site") int site, @Field("productId") int productId, @Field("entityId") int entityId);

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
    @FormUrlEncoded
    @POST("productSpecification/update")
    Call<ResultInfo> update(@FieldMap Map<String, Object> map, @Field("site") int site);


    //招商入驻
    @FormUrlEncoded
    @POST("specialMerchants/findAll")
    Call<FindBusiness> find_business(@FieldMap Map<String, Object> params, @Field("site") int site);


    //招商入驻
    @FormUrlEncoded
    @POST("customerInvoice/updateStatus")
    Call<ResultInfo> open_or_close_ticket(@FieldMap Map<String, Object> params, @Field("site") int site);


    /**
     * 头像信息上传，食品资格证修改，营业执照修改,身份证上传
     */
    @FormUrlEncoded
    @PUT("supplier/{update}")
    Call<ImageUploadOk> supplier_update(@Path("update") String update, @FieldMap Map<String, Object> params, @Field("site") int site);


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
    @GET("qualificationImage/getImageListByProductId/{productId}")
    Call<ImageListByProductId> getImageListByProductId(@Path("productId") int productId);

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
    @FormUrlEncoded
    @PUT("qualificationImage/deleteActualByImageURL")
    Call<QualificationImage> deleteActualByImageURL(@Field("imageUrl") String imageUrl, @Field("site") int site);

    /**
     * 上传资质图片（修改资质、上传图片）
     * <p/>
     * url	方法	说明
     * /image/android/qualification/save	POST	上传资质图片（修改资质、上传图片）
     * 参数名	类型	必须(1是/0否)	说明
     * productId	int	1	商品ID
     * image	String	1
     */
    @FormUrlEncoded
    @POST("image/android/qualification/save")
    Call<QualificationUpload> qualification_upload(@Field("productId") int productId, @Field("image") String image, @Field("site") int site);


    //供应商登录
    @FormUrlEncoded
    @POST("supplier/login")
    Call<SupplierBean> loginData_supplier(@FieldMap Map<String, Object> params);

    //注册
    @FormUrlEncoded
    @POST("supplier/register")
    Call<Register> supplier_registerData(@FieldMap Map<String, Object> params, @Field("site") int site);

    //获取本地市场
    @GET("market/findListByRegion")
    Call<MarketLocal> getMarket_local(@QueryMap Map<String, Object> params, @Query("site") int site);


    // 查询店铺的处罚记录
    @GET("store/punish/history/{storeId}")
    Call<PunishScoreRecord> getPunishScored(@Path("storeId") int storeId, @Query("site") int site);


    //共应商店铺状态设置
    @FormUrlEncoded
    @PUT("supplier/offToday")
    Call<OffToday> offToday(@FieldMap Map<String, Object> params, @Field("site") int site);

    /**
     * 获取订单的状态统计数量
     * <p/>
     * URL	方法	说明
     * /order/number	GET	获取订单的状态数据数量
     * 请求参数
     * <p/>
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * store	int	0		商铺ID 获取商铺的订单的状态数量
     * customerId	int	0		用户ID 获取用户的订单的状态数量
     */
    @GET("order/number")
    Call<ResultInfo<OrderStatus>> order_number(@Query("site") int site);

    /**
     * 查询供应商的银行账户
     * <p/>
     * url	方法	说明
     * /finance/getAccountByStoreId	GET	添加供应商银行账户
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * pn	int	1	分页页数
     * ps	int	1	分页每页数量
     * storeId	int	1	店铺的ID
     */
    @GET("finance/getAccountByStoreId")
    Call<AccountByStoreId_Resu> getAccountByStoreId(@Query("site") int site, @Query("storeId") int storeId);


    /**
     * 查询出供应商的货款(供应商登陆后也可以获得资金信息)
     * <p/>
     * url	方法	说明
     * /supplier/findOne/:entityId	GET	查询出供应商的货款
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * :entityId	int	1	商品entityId
     */
    @GET("supplier/findOne/{entityId}")
    Call<ResultInfo<SupplyMoney>> getSupplyMoney(@Path("entityId") int entityId, @Query("site") int site);


    /**
     * //申请提现
     * 参数名	类型	必须(1是/0否)	说明
     * supplierId	int	1	供应商ID
     * provinceName	Str	1	省名称
     * cityName	Str	1	城市名称
     * bankName	Str	1	银行名称
     * bankId	Str	1	银行卡id
     * bankAddress	Str	1	开户行地址
     * capitalWithdrawal	double	1	提现金额
     */
    @FormUrlEncoded
    @POST("SupplierCashWithdrwalApplication/create")
    Call<ResultInfo<SupplierCash>> getSupplierCashWithdrwalApplication(@FieldMap Map<String, Object> map, @Field("site") int site);


    /**
     * 查询出供应商默认的银行卡
     * <p/>
     * url	方法	说明
     * /finance/bankAccount/getDefaultAccount/:storeId	GET	查询出供应商默认的银行卡
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * :storeId	int	1	店铺ID
     */
    @GET("finance/bankAccount/getDefaultAccount/{storeId}")
    Call<ResultInfo<DefaultAccount>> getDefaultAccount(@Path("storeId") int storeId, @Query("site") int site);

    /**
     * 设置供应商默认的银行卡
     * <p/>
     * url	方法	说明
     * /finance/bankAccount/getDefaultAccount/:storeId	post	设置供应商默认的银行卡
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * :storeId	int	1	店铺ID
     */
    @FormUrlEncoded
    @POST("finance/bankAccount/setDefaultAccount")
    Call<DefaultAccountSet> setDefaultAccount(@Field("site") int site, @Field("entityId") int entityId, @Field("storeId") int storeId);

    /**
     * 供货款列表
     * <p/>
     * url	方法	说明
     * /logSupplierCapitalFlow/pageList	GET	供货款列表
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * pn	int	1	分页第几页
     * ps	int	1	分页页数
     */

    @GET("logSupplierCapitalFlow/pageList")
    Call<ResultInfo<LogSupplierCapitalFlow>> logSupplierCapitalFlow(@Query("site") int site, @Query("pn") int pn, @Query("ps") int ps);


    /**
     * 订单收款明细详情【域名： test.51taodj.com:3001】
     * <p/>
     * url	方法	说明
     * /supplierFinanceRecord/order/findOne	GET	供货款新版列表
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * orderid int 订单ID，entityid
     * orderNO Str  订单编号
     * pn	int	1	分页第几页
     * ps	int	1	分页页数
     */

    @GET("/supplierFinanceRecord/order/findOne")
    Call<SupplyMoneyDetailBean> getSupplierOrderPayment(@Query("site") int site, @Query("storeId") int storeId, @Query("orderNO") String orderNO);


    /**
     * 查询出供应商订单条目相关信息【域名： test.51taodj.com:3001】
     * <p/>
     * url	方法	说明
     * /supplierFinanceRecord/orderItems/findAll	GET	供货款新版列表
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * orderid int 订单ID，entityid
     * orderNO Str  订单编号
     * pn	int	1	分页第几页
     * ps	int	1	分页页数
     */

    @GET("/supplierFinanceRecord/orderItems/findAll")
    Call<SupplierOrderFormItemBean> getSupplierOrderFormItemDetailList(@Query("site") int site, @Query("storeId") int storeId, @Query("orderid") int orderid, @Query("orderNO") String orderNO);


    /**
     * 订单售后明细详情【域名： test.51taodj.com:3001】
     * <p/>
     * url	方法	说明
     * /supplierFinanceRecord/order/findOne	GET	供货款新版列表
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * afterSaleApplicationId String
     */

    @GET("/supplierFinanceRecord/afterSale/info")
    Call<SupplyMoneyDetailBean> getSupplierOrderAfterSale(@Query("site") int site, @Query("afterSaleApplicationId") String afterSaleApplicationId);

    /**
     * 订单提现明细详情【域名： test.51taodj.com:3001】
     * <p/>
     * url	方法	说明
     * /supplierFinanceRecord/order/findOne	GET	供货款新版列表
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * afterSaleApplicationId String
     */

    @GET("/supplierFinanceRecord/withdrawal/info")
    Call<SupplyMoneyDetailBean> getSupplierOrderGetCash(@Query("site") int site, @Query("withdrawalStatus") int withdrawalStatus, @Query("cashWithdrawlEntityId") String cashWithdrawlEntityId);

    /**
     * 判断供应商手机号码是否存在
     * <p/>
     * url	方法	说明
     * /supplier/checkSupplierPhoneExist	GET	判断供应商手机号码是否存在
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * telephone	str	1	供应商的手机号码
     */
    @GET("supplier/checkSupplierPhoneExist")
    Call<CheckPhoneExist> checkSupplierPhoneExist(@Query("site") int site, @Query("telephone") String telephone);


    /**
     * 供应商 - 用户信息刷新
     * <p/>
     * url	方法	说明
     * /supplier/refreshInfo	GET	刷新用户信息
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * entityId	int	1	登录用户id
     * uniqueId	String	1	登录时uniqueId设备号
     */
    @GET("supplier/refreshInfo")
    Call<MyselftUpdateS> supplier_refreshInfo(@Query("site") int site, @Query("entityId") int entityId, @Query("uniqueId") String uniqueId);

    /**
     * 酒店查询出收货地址列表
     * <p/>
     * url	方法	说明
     * /address/list/all	GET	酒店查询出收货地址列表
     */
    @GET("address/list/all")
    Call<ResultInfo<GoodsReceiptAddress>> getAddressList(@Query("site") int site, @Query("pn") int pn, @Query("ps") int ps,@Query("customerId") int customerId,@Query("userId") int userId);

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
    @FormUrlEncoded
    @POST("address/save")
    Call<AddressSave> addAddress(@FieldMap Map<String, Object> map, @Field("site") int site);

    //删除收货地址
    @GET("address/delete/{addressId}")
    Call<AddressDelete> deleteAddress(@Path("addressId") int addressId, @Query("site") int site);

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
    @FormUrlEncoded
    @PUT("address/update")
    Call<AddressUpdate> updateAddress(@FieldMap Map<String, Object> map, @Field("site") int site);

    /**
     * 酒店查询出默认收货地址列表
     * <p/>
     * url	方法	说明
     * /address/getDefaultOne	GET	酒店查询出收货地址列表
     */
    @GET("address/getDefaultOne")
    Call<ResultInfo<GoodsReceiptAddressBean>> getDefaultOne(@Query("site") int site);


    /**
     * 头像信息上传，食品资格证修改，营业执照修改,身份证上传
     */
    @FormUrlEncoded
    @PUT("customer/{update}")
    Call<ImageUploadOk> customer_update(@Path("update") String update, @FieldMap Map<String, Object> params, @Field("site") int site);

    //采购商登录
    @FormUrlEncoded
    @POST("customer/login")
    Call<PurchaseBean> loginData_purchase(@FieldMap Map<String, Object> params);

    //注册
    @FormUrlEncoded
    @POST("customer/register")
    Call<Register> customer_registerData(@FieldMap Map<String, Object> params, @Field("site") int site);

    /**
     * 结算生成订单
     * <p/>
     * URL	方法	说明
     * /order/toOrder	POST	结算生成订单
     * 请求参数
     * <p/>
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * customerId	int	1		用户ID
     * addressId	int	1		收货地址Id
     * paymentCode	str	1		支付方式，目前默认为在线支付"online_payment"
     * shippingAddressInfo	json对象的字符串	1		配送的附加信息。配送方式(目前默认为快递免邮"system_default")，配送时间（时间起、止、日期）{"shippingMethodCode":,"expectDeliveredETime":,"expectDeliveredLTime":,"expectDeliveredDate"}
     * productInfo	json数组的字符串	1		商品信息productId、qty
     * 参数e.g
     * <p/>
     * {
     * "customerId":2,
     * "addressId":1,
     * "paymentCode":"online_payment",
     * "shippingAddressInfo":"{'shippingMethodCode':'system_default','expectDeliveredETime':'9:00','expectDeliveredLTime':'11:30','expectDeliveredDate':'2017-02-10'}",
     * "productInfo":"[{'productId':366,'qty':20},{'productId':367,'qty':100}]",
     * "returnFields":"createdAt"
     * }
     */
    @FormUrlEncoded
    @POST("order/toOrder")
    Call<ResultInfo<OrderPlaceBack>> toOrder(@Field("site") int site,@Field("loginUserId") int loginUserId  ,
                                             @Field("paymentCustomerId") int paymentCustomerId, @Field("returnFields") String returnFields,
                                             @Field("customerId") int customerId, @Field("addressId") int addressId,
                                             @Field("paymentCode") String paymentCode, @Field("shippingAddressInfo") String shippingAddressInfo,
                                             @Field("productInfo") String productInfo, @Field("couponItemInfo") String couponItemInfo, @Field("productCount") int productCount,
                                             @Field("freightItemInfo") String freightItemInfo);
    Call<ResultInfo<OrderPlaceBack>> toOrder(@Field("site") int site,@Field("paymentCustomerId") int paymentCustomerId,
                                             @Field("returnFields") String returnFields, @Field("customerId") int customerId, @Field("addressId") int addressId,
                                             @Field("paymentCode") String paymentCode, @Field("shippingAddressInfo") String shippingAddressInfo,
                                             @Field("productInfo") String productInfo, @Field("couponItemInfo") String couponItemInfo, @Field("productCount") int productCount, @Field("freightItemInfo") String freightItemInfo);

    @FormUrlEncoded
    @POST("order/toPersonalOrder")
    Call<ResultInfo<OrderPlaceBack>> toPersonalOrder(@Field("site") int site,@Field("loginUserId") int loginUserId  ,
                                             @Field("paymentCustomerId") int paymentCustomerId, @Field("returnFields") String returnFields,
                                             @Field("customerId") int customerId, @Field("addressId") int addressId,
                                             @Field("paymentCode") String paymentCode, @Field("shippingAddressInfo") String shippingAddressInfo,
                                             @Field("productInfo") String productInfo, @Field("couponItemInfo") String couponItemInfo, @Field("productCount") int productCount,
                                             @Field("freightItemInfo") String freightItemInfo,@Field("timeEntityId") int timeEntityId,@Field("deliveryType") int deliveryType,
                                                     @Field("communityId") int communityId);

    /**
     * 采购商提现
     * <p/>
     * url	方法	说明
     * /CustomerCashWithdrwalApplication/create	POST	采购商提现
     * 请求参数
     * <p/>
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * customerId	int	1	customerId
     * provinceName	int	1	省
     * cityName	int	1	城市
     * bankName	int	1	银行名称、支付宝
     * accountNo	int	1	银行账号、支付宝账号
     * bankAddress	int	1	银行支行地址
     * capitalWithdrawal	int	1	提现金额
     * moneyType	int	1	提现资金类型 1、表示退款的资金 2、表示充值的资金
     */
    @FormUrlEncoded
    @POST("CustomerCashWithdrwalApplication/create")
    Call<ResultInfo<CustomerCash>> getCustomerCashWithdrwalApplication(@FieldMap Map<String, Object> map, @Field("site") int site);

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
     * :accountNo	int	1	可以是 支付宝账、银行账号卡号
     * :ownerName	int	1	持卡姓名 或者 支付宝姓名
     * :bankAddress	int	1	银行地址
     */
    @FormUrlEncoded
    @POST("customerFinance/bankAccount/save")
    Call<ResultInfo> customerFinance_bankAccount(@FieldMap Map<String, Object> map, @Field("site") int site);

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

    @GET("customerFinance/getAccountByCustomerId")
    Call<AccountByCustomerId> getAccountByCustomerId(@Query("site") int site, @Query("customerId") int customerId);

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

    @GET("customerFinance/bankAccount/getDefaultAccount/{customerId}")
    Call<CustomerFinanceDefaultAccount> customerFinance_getDefaultAccount(@Path("customerId") int customerId, @Query("site") int site);

    @GET("WithdrawFeeRule/getWithdrawFeeRule")
    Call<WithdrawFeeRule> getWithdrawFeeRule( @Query("site") int site);

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
    @FormUrlEncoded
    @POST("customerFinance/bankAccount/setDefaultAccount")
    Call<ResultInfo> customerFinance_setDefaultAccount(@Field("site") int site, @Field("entityId") int entityId, @Field("customerId") int customerId);


    /**
     * 1、查询某采购商的资金明细
     * <p>
     * url	方法	说明
     * /customerFinanceRecord/capitalDetail/findByCustomerId/:customerId	GET	查询某采购商的资金明细
     * 请求参数
     * <p>
     * 参数名	类型	必须(1是/0否)	说明
     * :customerId	num	1	采购商的entityId
     * pn	num	0	分页起始页，默认pn=1
     * ps	num	0	分页每页查询条数，默认ps=10
     * 返回值说明
     * <p>
     * 参数名	类型	说明
     * status	num	表示数据类型： (1) 充值、(2)支付、(3)退款、(4)提现
     * outTradeNo	str	支付outTradeNo
     * paymentMethod	num	0、退款  1、支付宝 2、微信支付 3、余额支付 4、微信+余额支付 5、支付宝+余额支付 6、提现
     * moneyAmount	num	消费、充值、提现、退款之后采购商账号剩余的总的资金额度
     * rechargeAmount	num	消费、充值、提现、退款之后采购商账号剩余的充值资金额度
     * refundAmount	num	消费、充值、提现、退款之后采购商账号剩余的退款资金额度
     * paymentAmount	num	(消费、充值)总的支付的金额
     * onlinePaymentAmount	num	  (消费、充值) 支付宝或者微信支付的金额
     * accountPaymentAmount	num	  (消费) 余额支付的金额
     * transactionNumber	str	  (消费) 第三方支付的流水号码
     * extOrderId	str	  (消费) 订单的ID
     * productId	num	  (退款) 商品的ID
     * refundTotalMoney	num	  (退款)总的金额
     * refundQty	num	  (退款)商品退的数量
     * refundMoney	num	  (退款)商品退的金额
     * originalQty	num	  (退款)商品采购商退款之前订购的数量
     * originalTotalMoney	num	  (退款)商品采购商退款之前订购的金额
     * orderCreateTime	str	订单创建时间
     * orderPayTime	str	订单支付的时间 |
     * afterSaleApplyTime	str	  (退款)售后申请的时间
     * afterSaleHandleTime	str	  (退款)售后处理的时间
     * afterSaleApplicationId	num	  (退款) 售后的ID
     * afterSaleApplicationNo	num	  (退款) 售后的编号
     * storeId	num	  (退款) 售后的商品所属的商家ID  |
     * withdrawalRechargeAmount	num	  (提现) 采购商提现中的充值金额
     * withdrawalRechargeAmountFee	num	  (提现) 采购商提现中的充值金额手续费
     * withdrawalAfterSaleAmount	num	  (提现) 采购商提现中的售后金额
     * withdrawalAfterSaleAmountFee	num	  (提现) 采购商提现中的售后金额手续费
     * year	num	  (提现)  采购商提现的年
     * month	num	(提现) 采购商提现的月
     */
    @GET("customerFinanceRecord/capitalDetail/findByCustomerId/{customerId}")
    Call<CustomerFinanceRecord> customerFinanceRecord(@Path("customerId") int customerId, @Query("site") int site, @Query("pn") int pn, @Query("ps") int ps);

    /**
     * 查询某采购商的余额明细
     * 和上面相同
     * url	方法	说明
     * /customerFinanceRecord/detail/capitalDetail/findByCustomerId/:customerId	GET	查询某采购商的资金明细
     */
    @GET("customerFinanceRecord/detail/capitalDetail/findByCustomerId/{customerId}")
    Call<CustomerFinanceRecord> customerFinanceRecord_balance(@Path("customerId") int customerId, @Query("site") int site, @Query("pn") int pn, @Query("ps") int ps);

    /**
     * 根据customerID查询出该用户的资金明细记录、分页(按照条件查询)
     * <p>
     * url	方法	说明
     * /customerFinanceRecord/capitalDetail/findByCondition	POST	根据customerID查询出该用户的资金明细记录、分页(按照条件查询)
     * 请求参数
     * <p>
     * 参数名	类型	必须(1是/0否)	说明
     * customerId	num	1	采购商的资金明细的entityId
     * type	num	1	表示数据类型： (0)全部、(1) 充值、(2)支付、(3)退款、(4)提现
     * startTime	str	1	查询起始时间 “2017-01-01”
     * endTime	str	1	查询起始时间 “2017-12-01”
     * pn	num	1	分页第几页
     * ps	num	1	分页每页多少条
     */

    @GET("customerFinanceRecord/capitalDetail/findByCondition")
    Call<CustomerFinanceCondition> customerFinanceCondition(@QueryMap Map<String, Object> map, @Query("site") int site, @Query("pn") int pn, @Query("ps") int ps);

    /**
     * 根据customerID查询出该用户的资金明细记录、分页(按照条件查询) 【余额】
     * <p>
     * url	方法	说明
     * /customerFinanceRecord/detail/capitalDetail/findByCondition	get	根据customerID查询出该用户的资金明细记录、分页(按照条件查询)
     */

    @GET("customerFinanceRecord/detail/capitalDetail/findByCondition")
    Call<CustomerFinanceCondition> customerFinanceCondition_balance(@QueryMap Map<String, Object> map, @Query("site") int site, @Query("pn") int pn, @Query("ps") int ps);

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
    @GET("customerFinanceRecord/orderDetail/findOneById/{id}")
    Call<CustomerFinanceRecordOrderDetail> customerFinanceRecordOrderDetail(@Path("id") int id, @Query("site") int site);


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
    @GET("customerFinanceRecord/orderItemDetail/findOneById/{id}")
    Call<CustomerFinanceRecordOrderItemDetail> customerFinanceRecordOrderItemDetail(@Path("id") int id, @Query("site") int site);

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
    @GET("customerFinanceRecord/rechargeDetail/findOneById/{id}")
    Call<CustomerFinanceRecordRechargeDetail> customerFinanceRecordRechargeDetail(@Path("id") int id, @Query("site") int site);


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
    @GET("customerFinanceRecord/withdrawalDetail/findOneById/{id}")
    Call<CustomerFinanceRecordWithdrawalDetail> customerFinanceRecordWithdrawalDetail(@Path("id") int id, @Query("site") int site);

    /**
     * 查询账单退款详情
     * <p>
     * url	方法	说明
     * /customerFinanceRecord/refundDetail/findOneById/:id	GET	根据ID查询提现账单详情
     * 请求参数
     * <p>
     * 参数名	类型	必须(1是/0否)	说明
     * :id	num	1	采购商的资金明细的entityId
     */

    @GET("customerFinanceRecord/refundDetail/findOneById/{id}")
    Call<CustomerFinanceRecordRefundDetail> customerFinanceRecordRefundDetail(@Path("id") int id, @Query("site") int site);


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
    @GET("customerFinanceRecord/findMonthBillDetailNew/{customerId}")
    Call<CustomerFinanceRecordFindMonthBillDetail> customerFinanceRecordFindMonthBillDetail(@Path("customerId") int customerId, @Query("site") int site, @Query("year") int year, @Query("month") int month);


    /**
     * 判断采购商手机号码是否存在
     * <p/>
     * url	方法	说明
     * /customer/checkCustomerPhoneExist	GET	判断采购商手机号码是否存在
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * telephone	str	1	采购商的手机号码
     */
    @GET("customer/checkCustomerPhoneExist")
    Call<CheckPhoneExist> checkCustomerPhoneExist(@Query("site") int site, @Query("telephone") String telephone);


    /**
     * 根据markCode查询出所有的子账户
     * <p/>
     * url	方法	说明
     * /subUser/list/:markCode	GET	根据markCode查询出所有的子账户
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * :markCode	Str	1	酒店的
     */
    @GET("customer/subUser/list/{markCode}")
    Call<SubAccount_Resu> getSubUserList(@Path("markCode") String markCode, @Query("site") int site);

    //创建子账号
    @FormUrlEncoded
    @POST("customer/subUser/create")
    Call<SubUserCreate> subUserCreate(@FieldMap Map<String, Object> map, @Field("site") int site);

    //[使用验证码激活子账号]
    @FormUrlEncoded
    @POST("customer/activation/SubUser")
    Call<ResultInfo<Object>> activation(@Field("site") int site, @Field("entityId") int entityId, @Field("verifyCode") String verifyCode);

    //拒绝激活
    @GET("customer/subUser/refuse/{subUserId}")
    Call<ResultInfo<Object>> subuser_refuse(@Path("subUserId") int subUserId, @Query("site") int site);


    //重新激活
    @FormUrlEncoded
    @POST("/common/sendSmsToSubUser")
    Call<ResultInfo<Object>> sendSmsToSubUser(@Field("site") int site, @Field("entityId") int subUserId);


    //删除子账号
    @GET("customer/delete")
    Call<SubUserDelete> subUserDelete(@Query("site") int site, @Query("customerId") int customerId, @Query("flag") int flag, @Query("markCode") String markCode);

    /**
     * //启用停用子账号
     * // entityId
     * // isActive 1启用   0停用
     */
    @FormUrlEncoded
    @PUT("customer/onOrOffSubUser")
    Call<OnOrOffSubUser> onOrOffSubUser(@FieldMap Map<String, Object> map, @Field("site") int site);


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
    @FormUrlEncoded
    @POST("customerPaymentStatusRecord/balanceQuery/verify")
    Call<BalanceQuery> balanceQuery(@FieldMap Map<String, Object> map, @Field("site") int site);


    /**
     * 微信APP预支付
     * <p/>
     * url	方法	说明
     * /common/system/wxpay	POST	微信APP预支付
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * orderId	str	1	订单编号
     * actualPay	float	1	付款金额
     * type	int	1	1、表示安卓 2、表示IOS
     */
    @FormUrlEncoded
    @POST("common/system/wxpay")
    Call<ResultInfo<WXPay>> wxpay_repayId(@Field("site") int site, @Field("orderId") String orderId, @Field("actualPay") String actualPay, @Field("type") int type);


    /**
     * 微信充值预支付
     * <p/>
     * url	方法	说明
     * /common/recharge/wxpay POST	微信APP预支付
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * payRecordId	str	1
     * actualPay	float	1	付款金额
     * type	int	1	1、表示安卓 2、表示IOS
     */
    @FormUrlEncoded
    @POST("common/recharge/wxpay")
    Call<ResultInfo<WXPay>> wxpay_recharge(@Field("site") int site, @Field("payRecordId") String payRecordId, @Field("actualPay") float actualPay, @Field("type") int type);

    /**
     * 修改是否支持余额支付、默认关闭状态;
     * <p>
     * url	方法	说明
     * /paymentBalanceRecord/updateRecordsIsSupportBalance	POST	修改是否支持余额支付、默认关闭状态;
     * 请求参数
     * <p>
     * 参数名	类型	必须(1是/0否)	说明
     * outTradeNo	int	1	outTradeNo
     * isSupportBalance	int	1	1表示支持 0表示不支持
     */
    @FormUrlEncoded
    @POST("paymentBalanceRecord/updateRecordsIsSupportBalance")
    Call<ResultInfo> updateRecordsIsSupportBalance(@Field("site") int site, @Field("outTradeNo") String outTradeNo, @Field("isSupportBalance") int isSupportBalance);

    /**
     * 采购商消费接口;
     * <p>
     * url	方法	说明
     * /order/tradeSuccess/balancePay	PUT	采购商消费接口;
     * 请求参数
     * <p>
     * 参数名	类型	必须(1是/0否)	说明
     * outTradeNo	int	1	outTradeNo
     * totalAmount	int	1	消费的金额
     * customerId	int	1	采购商的customerId
     */
    @FormUrlEncoded
    @PUT("order/tradeSuccess/balancePay")
    Call<ResultInfo> balancePay(@Field("site") int site, @Field("outTradeNo") String outTradeNo, @Field("totalAmount") BigDecimal totalAmount, @Field("customerId") int customerId);

    /**
     * 采购商 - 用户信息刷新
     * <p/>
     * url	方法	说明
     * /customer/refreshInfo	GET	刷新用户信息
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * entityId	int	1	主账号身份时取登录信息的entityId，子账户时取subUserId
     * uniqueId	String	1	登录时uniqueId设备号
     *  flag //0-刷新门店信息 1-获取当前登陆用户信息
     */
    @GET("customer/refreshInfo")
    Call<MyselftUpdateP> customer_refreshInfo(@Query("site") int site, @Query("flag") int flag, @Query("entityId") int entityId, @Query("loginUserId") int loginUserId, @Query("uniqueId") String uniqueId);

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

    @FormUrlEncoded
    @POST("customerInvoice/create")
    Call<ResultInfo> customerInvoice_create(@FieldMap Map<String, Object> map, @Field("site") int site);


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
    @FormUrlEncoded
    @POST("customerInvoice/update")
    Call<ResultInfo> customerInvoice_update(@FieldMap Map<String, Object> map, @Field("site") int site);

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
    @FormUrlEncoded
    @POST("customerInvoice/updateStatus")
    Call<ResultInfo> customerInvoice_updateStatus(@Field("entityId") int entityId, @Field("customerId") int customerId, @Field("isActive") int isActive, @Field("site") int site);


    /**
     * 4、 查看采购商发票抬头信息
     * url	方法	说明
     * /customerInvoice/findOne	GET	开启/关闭发票
     * 参数说明
     * <p>
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * customerId	int	1		采购商id
     */
    @GET("customerInvoice/findOne")
    Call<CustomerInvoice> customerInvoice_findOne(@Query("customerId") int customerId, @Query("site") int site);

    /**
     * 供货款新版列表
     * <p/>
     * url	方法	说明
     * /findPageListByStoreId	GET	供货款新版列表
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * storeId 店铺名
     * pn	int	1	分页第几页
     * ps	int	1	分页页数
     */

    @GET("fund/fund-record/findPageListByStoreId")
    Call<SupplyMoneyListBean> getSupplyMoneyListNew(@Query("site") int site, @Query("storeId") int storeId, @Query("pn") int pn, @Query("ps") int ps);

    /**
     * 筛选供货款列表
     * <p/>
     * url	方法	说明
     * /logSupplierCapitalFlow/pageList	GET	供货款列表
     * 请求参数
     * <p/>
     * 参数名	类型	必须(1是/0否)	说明
     * pn	int	1	分页第几页
     * ps	int	1	分页页数
     */

    @GET("fund/fund-record/findPageListByCondition")
    Call<SupplyMoneyListBean> getSupplyMoneyListFilter(@Query("site") int site, @QueryMap Map<String, Object> params);


    /**
     * 根据storeID查询出供应商月账单相关信息
     * url	方法	说明
     * fund/fund-record/findPageMonthlyBill	GET	根据storeID查询出供应商月账单相关信息
     * 例如：http://finance.51taodj.com/fund/fund-record/findPageMonthlyBill?storeId=23&year=2018&month=9
     * GET请求参数说明
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * storeId	int	1		供应商店铺ID
     * year	int	1		年份
     * month	int	1		月份
     */

    @GET("fund/fund-record/findPageMonthlyBill")
    Call<SupplyMonthlyBillBean> findPageMonthlyBill(@Query("storeId") int storeId, @Query("year") int year, @Query("month") int month);


    @GET("/packageForegiftAfterSale/pageList")
    Call<PackingCashBean> getPackageForegiftList(@Query("site") int site, @QueryMap Map<String, Object> params);

    @GET("/customer/findHotelList")
    Call<HotelList> getHotelList(@Query("site") int site,@Query("customerId") int customerId);

    @GET("/customer/findHoteInfo")
    Call<ShopDetailBean> getShopDetail(@Query("site") int site,@Query("customerId") int customerId);

    @FormUrlEncoded
    @POST("/customer/changeHotel")
    Call<PurchaseBean> getChangeHotel(@Field("site") int site,@FieldMap Map<String,Object> map);

    @GET("/customer/employees/list")
    Call<EmployeesListBean> getEmployeesLsit(@Query("site") int site,@Query("customerEntityId") int loginUserId);

    @FormUrlEncoded
    @POST("/customer/subUser/create")
    Call<AddSubuserBean> addSubUser(@Field("site") int site, @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @PUT("/customer/update")
    Call<UpdateCustomerBean> updateCustomer(@Field("site") int site, @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/customer/employees/delete")
    Call<DeleteCustomerBean> deleteCustomer(@Field("site") int site,@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @PUT("/customer/updateLeader")
    Call<UpdateLeaderBean> updateLeader(@Field("site") int site,@FieldMap Map<String, Object> params);

    @GET("/dict/findAll")
    Call<MapSearchRange> getSearchRange(@Query("site") int site);

    @GET("/customer/gmo/list")
    Call<ChainShopList> getChainList(@Query("site") int site,@Query("customerEntityId") int customerId,@Query("ownedStores") String ownedStores);

    @GET("/customer/updateHotelType")
    Call<UpdateCustomerBean> updateShopType(@Query("site") int site,@QueryMap Map<String, Object> params);

    @FormUrlEncoded
    @PUT("/address/update")
    Call<UpdateAddressBean> updateConsigneeOrTime(@Field("site") int site,@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @PUT("/address/updateAddress")
    Call<UpdateAddressBean> updateAddress(@Field("site") int site,@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/customer/gmoffice/edit")
    Call<CreateHeadquartersBean> createHeadquarters(@Field("site") int site,@FieldMap Map<String, Object> params);

    @GET("/customer/store/list")
    Call<EmpoleeStoreList> getEmpStoreList(@Query("site") int site,@Query("customerEntityId") int id);

    @GET("address/findFenceGid")
    Call<FenceGid> getFenceGid(@Query("site") int site,@Query("diu") String deviceId, @Query("lon") String lon, @Query("lat") String lat);

    @GET("customer/GmoEditStore/List")
    Call<GmoEditStore> getEidtGmoList(@Query("site") int site,@Query("customerEntityId") int id,@Query("gmoEntityId") int gmoId);

    @GET("customer/employee/info")
    Call<EidtEmployeeInfo> getEidtEmployeeInfo(@Query("site") int site,@Query("customerEntityId") int id,@Query("parentCustomerEntityId") int pid);

    @GET("order/{storeId}/stationList")
    Call<StationBean> getStationList(@Path("storeId")int storeId,@Query("site") int site);

    @FormUrlEncoded
    @POST("supplierAnnalFee/fee/info")
    Call<SupplierAnnalFeeInfo> supplierAnnalFee(@Field("site") int site, @Field("storeId") String storeId);
    @FormUrlEncoded
    @POST("supplierAnnalFee/fee/pay")
    Call<SupplierAnnalFeePayInfo> getSupplierAnnalFeePay(@Field("siteId") int site, @FieldMap Map<String, Object> params,@Field("storeId") String storeId);


    @FormUrlEncoded
    @POST("supplierAnnalFee/fee/privileges")
    Call<PrivilegesInfo> getSupplierAnnalFeePrivileges(@Field("site") int site, @Field("storeId") String storeId);

    @FormUrlEncoded
    @POST("supplierAnnalFee/fee/tips")
    Call<FeeTips> getSupplierAnnalFeeTips(@Field("site") int site, @Field("storeId") String storeId);


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
    @GET("product/oftenBuy")
    Call<ResultInfo<PickFoodGoodsList>> oftenBuy(@Query("site") int site, @Query("catalogId") int catalogId, @Query("entityId") int entityId, @Query("entityType") int entityType, @Query("pn") int pn, @Query("ps") int ps,@Query("name") String name,@Query("storeName") String storeName);

    @GET("/fund/punishment/getNewAndCount")
    Call<NewsAndCount> getNewAndCount(@Query("site") int site,@Query("receiverType") int type,@Query("userId") int userId);

    @GET("fund/punishment/getPunishMents")
    Call<PunishmentMessage> getPunishmentList(@Query("site") int site,@Query("userId") int userId,@Query("receiverType") int receiverType,@Query("pn") int pn,@Query("ps") int ps);

    @FormUrlEncoded
    @POST("fund/punishment/upadtePunishMent4ReadedOrStatus")
    Call<PunishmentReaded> getPunishmentReaded(@Field("site") int site,@Field("updType")int type, @Field("entityId") int entityId,@Field("userId") int userId);


    @FormUrlEncoded
    @POST("tdj-store/store/commodity/queryList")
    Call<IntegralShop> commodity_queryList( @Field("siteId") int site,@FieldMap Map<String, Object> params);
    @FormUrlEncoded
    @POST("tdj-store/store/cart/query")
    Call<IntegralShopCart> cart_queryList( @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("tdj-store/store/cart/update/quantity")
    Call<CartQuantitys> update_quantity( @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("tdj-store/store/cart/delete")
    Call<CartQuantitys> delete_cart( @FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("tdj-store/store/cart/verifyUserIntegral")
    Call<CartQuantity> verifyUserIntegral( @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("tdj-user/user/shipAddress/getDefaultAddress")
    Call<AddressInfo> getDefaultAddress( @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("tdj-user/user/shipAddress/getAddress")
    Call<RedactAddress> getAddress( @FieldMap Map<String, Object> params);
    @FormUrlEncoded
    @POST("tdj-user/user/shipAddress/delete")
    Call<BaseIntegral> shipAddress_delete( @FieldMap Map<String, Object> params);
    @FormUrlEncoded
    @POST("tdj-user/user/shipAddress/update")
    Call<AddressInfo> shipAddress_update( @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("tdj-user/user/shipAddress/add")
    Call<AddressInfo> shipAddress_add( @FieldMap Map<String, Object> params);
    @FormUrlEncoded
    @POST("tdj-store/store/order/create")
    Call<IntegralOrder> order_create( @FieldMap Map<String, Object> params);
    @FormUrlEncoded
    @POST("tdj-store/store/ali/pay")
    Call<IntegralAliPay> order_ali_pay( @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("tdj-store/store/wx/pay")
    Call<IntegralWXPay> order_wx_pay( @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("tdj-store/store/integral/pay")
    Call<IntegralOrder> integral_pay( @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("tdj-store/store/wx/orderQuery")
    Call<IntegralOrder> orderQuery( @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("tdj-store/store/ali/orderQuery")
    Call<IntegralOrder> aliorderQuery( @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("tdj-user/user/user/getUserAndApproach")
    Call<IntegralShopMy> getUserAndApproach( @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("tdj-user/user/user/getUserAndPrivilege")
    Call<UserPrivilegeinfo> getUserAndPrivilege( @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("tdj-user/user/integral/signIn")
    Call<CartQuantity> integral_signIn( @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("tdj-user/user/integral/queryInviteList")
    Call<IntegralItem> integral_queryInviteList( @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("tdj-user/user/integral/item")
    Call<IntegralItem> integral_item( @FieldMap Map<String, Object> params);
    @FormUrlEncoded
    @POST("tdj-user/user/user/checkUser")
    Call<BaseIntegral> checkUser( @FieldMap Map<String, Object> params);
    @FormUrlEncoded
    @POST("tdj-store/store/package/query/list")
    Call<QueryIntergral> query_intergral_list( @FieldMap Map<String, Object> params);
    @FormUrlEncoded
    @POST("tdj-store/store/order/create")
    Call<IntegralOrder> buyIntegral( @FieldMap Map<String, Object> params);
    @FormUrlEncoded
    @POST("tdj-store/store/order/again")
    Call<AgainOrder> order_again( @FieldMap Map<String, Object> params);

    @GET("order/getDriverLocation")
    Call<DriverLocation> getDriverLocation(@Query("site") int site,@Query("orderNo") String orderNo,@Query("driverTel") String driverTel,@Query("customerAddrId") int customerAddrId);

    @GET("fund/shippingEvaluation/getDegreeOfSatisfaction")
    Call<DegreeOfSatisfaction> getDegreeOfSatisfaction(@Query("site") int site,@Query("driverId") int driverId,@Query("extOrderId") String extOrderId);

    @GET("logisticsProblem/getLogisticsProblem")
    Call<ProblemList> getLogisticsProblem(@Query("site")int site,@Query("type") int type,@Query("loginUserId") int id);

    @FormUrlEncoded
    @POST("fund/shippingComplaint/add")
    Call<AddCategory> addComplaint(@Field("site")int site,@Field("driverId") int driverId,@Field("extOrderId") String extOrderId,@Field("type") int type,@Field("content") String content,@Field("img") String img,@Field("loginUserId") int loginUserId,@Field("customerId") int customerId);

    @FormUrlEncoded
    @POST("fund/shippingEvaluation/add")
    Call<AddCategory> addEvaluation(@Field("orderId") String orderId,@Field("customerId") int customerId,@Field("userId") int userId,@Field("driverId") int driverId,@Field("evaType") int evaType,@Field("evaContent") String evaContent,@Field("anonymous") int anonymous,@Field("websiteId") int websiteId,@Field("customerAddrId") int customerAddrId,@Field("problemIdStr") String problemIdStr);

    @GET("fund/shippingEvaluation/findShippingEvaluationDetail")
    Call<EvaluationDetail> findShippingEvaluationDetail(@Query("site") int site,@Query("driverId") int driverId,@Query("extOrderId") String extOrderId);

    @FormUrlEncoded
    @POST("specialMerchants/findPoverty")
    Call<Poverty> findPoverty(@Field("site") int site,@Field("flag") int flag);

    @GET("povertyAlleviationRecommend/findAllByType")
    Call<PovertyAlleviationRecommend> findRecommend(@Query("siteId") int site,@Query("type") int type);

    @GET("fund/shippingComplaint/findShippingComplaintDetail")
    Call<ComplaintDetail> findComplaintDetail(@Query("site") int site,@Query("driverId") int driverId,@Query("extOrderId") String extOrderId);

    @FormUrlEncoded
    @POST("tdj-user/user/user/getDuibaRegisterUrl")
    Call<DuiBaRegisterUrl> getDuiBaRegisterUrl( @FieldMap Map<String, Object> params);

    @GET("order/{storeId}/receiveList")
    Call<ReceiveList> getReceiveList(@Path("storeId")int storeId,@Query("site") int site);

    @FormUrlEncoded
    @POST("fund/store/setAutomaticRenewal")
    Call<AddressUpdate> setAutomaticRenewal(@Field("storeId") int storeId,@Field("isAutomaticRenewal") int flag);

    @FormUrlEncoded
    @POST("fund/store/setAutomaticRenewalFee")
    Call<AddressUpdate> setAutomaticRenewalFee(@Field("storeId") int storeId,@Field("automaticRenewalFee") int flag);

    @GET("receiveStore/receiveWarehouseRecommendList")
    Call<ReceiveWarehouseRecommendList> getReceiveWarehouseRecommendList(@Query("site") int site,@Query("storeId") int storeId);

    @GET("receiveStore/openReceiveWarehouse")
    Call<AddressUpdate> openReceiveWarehouse(@Query("site")int site,@Query("storeId") int storeId,@Query("stationId") int stationId,@Query("receiveWarehouseId") int receiveWarehouseId,@Query("receiveType") int receiveType,@Query("isStoreReceive") int isStoreReceive,@Query("isOpen") int isOpen);

    @GET("receiveStore/closeReceiveWarehouse")
    Call<AddressUpdate> closeReceiveWarehouse(@Query("site")int site,@Query("storeId") int storeId,@Query("stationId") int stationId,@Query("receiveWarehouseId") int receiveWarehouseId,@Query("isOpen") int isOpen,@Query("receiveType") int receiveType);

    @FormUrlEncoded
    @POST("store/receive/fee")
    Call<ReceiveFee> receiveFee(@Field("storeId") int storeId,@Field("siteId") int siteId);

    @GET("fund/receivestation/rechargepackage/list")
    Call<PickupPackage> getPickupPackageList(@Query("site") int site);

    @FormUrlEncoded
    @POST("fund/financeCheck/receiveStationFeeInfo/List")
    Call<PickupFeeList> getPickupFeeList(@Field("storeId") int storeId,@Field("pn") int pn,@Field("ps") int ps);

    @FormUrlEncoded
    @POST("fund/financeCheck/findReceiveStationFeeDetail")
    Call<PickupOrderDetail> findReceiveStationFeeDetail(@Field("storeId") int storeId, @Field("feePercent") Double feePercent, @Field("expectDeliveredDate")String expectDeliveredDate,@Field("receiveType") int receiveType,@Field("receiveStationId") int receiveStationId,@Field("pn") int pn,@Field("ps") int ps);

    @FormUrlEncoded
    @POST("receiveStationFeeInfo/feeinfo/saveAndPay")
    Call<BuyPackageFee> saveAndPay(@Field("packageId") int packageId, @Field("store_id") int store_id, @Field("recharge_type") int recharge_type, @Field("pay_money") double pay_money, @Field("buy_money") double buy_money, @Field("website_id") int website_id, @Field("is_automatic_renewal") int is_automatic_renewal, @Field("automatic_renewal_fee") double automatic_renewal_fee, @Field("store_name") String store_name, @Field("store_mobile") String store_mobile);

    @GET("fund/store/prduct/getSpecificationIdByEntityId")
    Call<ModifyInventory> getSpecificationIdByEntityId(@Query("site") int site,@Query("productId") int productId);

    @FormUrlEncoded
    @POST("productSpecification/update")
    Call<AddCategory> modifyInventory(@Field("entityId") int entityId,@Field("productId") int productId,@Field("price") BigDecimal price,@Field("level2Value") BigDecimal level2Value,@Field("level3Value") BigDecimal level3Value,@Field("stock") int stock,@Field("avgPrice") BigDecimal avgPrice,@Field("levelType") int levelType,@Field("level1Unit") String level1Unit,@Field("level2Unit") String level21Unit,@Field("level3Unit") String level3Unit,@Field("avgUnit") String avgUnit);


    @FormUrlEncoded
    @POST("productSpecification/updatePrice")
    Call<AddCategory> modifyInventory(@Field("productId") int productId,@Field("price") BigDecimal price,@Field("stock") int stock);

    @GET("fund/store/getRwInfoByStoreId")
    Call<RefreshPickupFee> refreshPickupFee(@Query("storeId") int storeId,@Query("site") int site);

    @GET("order/getProductToBeStored")
    Call<TodayDeliveryProduct> getProductNameList(@Query("store_id") int storeId,@Query("station_id") int stationId,@Query("site_id") int site,@Query("rw_id") int rwId,@Query("truck_time") String truckTime);

    @GET("order/getRegionToBeStored")
    Call<TodayDeliveryArea> getProductAreaList(@Query("store_id") int storeId,@Query("station_id") int stationId,@Query("site_id") int site,@Query("rw_id") int rwId,@Query("truck_time") String truckTime);

    @GET("order/updateOrderItemPrintStatus")
    Call<AddCategory> updatePrintStatus(@Query("itemIds") String itemIds);

    @FormUrlEncoded
    @POST("tdj-partner/partner/userApply/getPartnerUser")
    Call<BuyIntegral> getPartnerUser( @FieldMap Map<String, Object> params);


    @GET("fine/findFineOrderList")
    Call<PunishData> findFineOrderList(@Query("site") int site, @QueryMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("payManage/center/item")
    Call<PayManage> payManage( @Field("siteId") int siteId,@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("advertisement/findList")
    Call<MarketingManage> advfindList( @Field("site") int siteId,@FieldMap Map<String, Object> params);


    @GET("store/{storeId}/products?status=1")
    Call<SelGoods> products(@Path("storeId") int storeId, @Query("site") int site,@Query("productName") String productName,
                                        @Query("pn") int pn, @Query("ps") int ps);

    @FormUrlEncoded
    @POST("advertisement/addMyAdvertisement")
    Call<AvdOrder> addMyAdvertisement( @Field("site") int siteId,@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("advertisement/myAdvertisementList")
    Call<TfAdvertisement> myAdvertisementList( @Field("site") int siteId,@FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("payManage/fee/pay")
    Call<SupplierAnnalFeePayInfo> adv_fee_pay( @Field("siteId") int siteId,@FieldMap Map<String, Object> params);

    @GET("fine/findPaymentList")
    Call<PaymentList> findPaymentList( @Query("siteId") int siteId, @QueryMap Map<String, Object> params);

    @GET("supplierFinanceRecord/advertisementOrFine/info")
    Call<AdvMoneyDetails> advertisementOrFine( @Query("siteId") int siteId, @QueryMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("fine/addFinePopout")
    Call<AddFinePopout> addFinePopout( @Field("siteId") int siteId,@FieldMap Map<String, Object> params);

    @GET("supplier/currentStoreCategoryList")
    Call<CurrentStoreCategory> getCurrentStoreCategoryList( @Query("site") int site, @Query("storeId") int storeId,@Query("checkStatus") int checkStatus);

    @GET("storeCommodityApply/findStoreCategoryCommodityList")
    Call<StoreCategoryCommodity> getStoreCategoryCommodityList(@Query("site") int site,@Query("storeId") int storeId,@Query("checkStatus") int status);

    @GET("storeCommodityApply/deleteOrCancel")
    Call<DeleteCommodity> deleteCommodityApply(@Query("flag") int flag,@Query("entityId") int entityId);

    @GET("storeCommodityApply/deleteOrCancel")
    Call<AddCategory> cancelCommodityApply(@Query("flag") int flag,@Query("entityId") int entityId);

    @FormUrlEncoded
    @POST("product/searchCategoryList")
    Call<GoodsCategorySelect> searchCategoryList(@Field("categoryId") int categoryId,@Field("commodityName") String commodityName,@Field("site") int site);

    @GET("supplier/getCommodityLimit")
    Call<CommodityLimit> getCommodityLimit(@Query("parentCategoryId") int parentCategoryId,@Query("site") int site,@Query("storeId") int storeId,@Query("flag") int flag);

    @FormUrlEncoded
    @POST("storeCommodityApply/saveAddApply")
    Call<AddCategory> saveAddApply(@Field("site") int site,@Field("storeId") int storeId,@Field("username") String username,@Field("phone") String phone,@Field("subCategoryJson") String subCategoryJson);

    @GET("product/getCommodityLabel")
    Call<CommodityLabel> getCommodityLabel(@Query("websiteId") int site,@Query("commodityEntityId") int id);

    @GET("product/getCommodityLabel")
    Call<CommodityLabel> getCommodityLabel(@Query("websiteId") int site,@Query("commodityEntityId") int id,@Query("varietyEntityId") int varietyEntityId,@Query("isDrainageArea") int isDrainageArea);

    @GET("product/getCommodityAliasVariety")
    Call<CommodityAliasVariety> getCommodityAliasVariety(@Query("commodityEntityId") int id,@Query("websiteId") int site);

    @GET("product/getCommodityAliasVariety")
    Call<CommodityAliasVariety> getCommodityAliasVariety(@Query("commodityEntityId") int id,@Query("isDrainageArea") int isDrainageArea,@Query("websiteId") int site);

    @GET("product/getStandardList")
    Call<StandardList> getStandardList(@Query("websiteId") int site,@Query("storeId") int storeId,@Query("commodityEntityId") int commodityEntityId,@Query("categoryId") int categoryId,@Query("parentCategoryId") int parentCategoryId);

    @FormUrlEncoded
    @POST("product/batchUpdCredentialImgs")
    Call<AddCategory> batchUpdCredentialImgs(@Field("storeId") int id,@Field("credentialImgs") String imgs,@Field("prodIds") String products,@Field("qrCodes") String qrCodes);

    @GET("store/{storeId}/products")
    Call<ResultInfo<ShopDetail_Goods>> searchProducts(@Path("storeId") int id,@Query("productName") String productName,@Query("pn") int pn,@Query("ps") int ps,@Query("status") int status,@Query("categoryId") int categoryId,@Query("includeSubCategory") int includeSubCategory);

    @GET("supplier/getCommodityShowStatus")
    Call<ShowStatus> getCommodityShowStatus(@Query("userId") int userId);

    @FormUrlEncoded
    @POST("supplier/setMainCategory")
    Call<AddCategory> setMainCategory(@Field("loginUserId") int loginUserId,@Field("username") String username,@Field("phone") String phone,@Field("storeId") int storeId,@Field("site") int site,@Field("categoryId") int categoryId,@Field("categoryName") String categoryName,@Field("supplierSaleType") int supplierSaleType,@Field("subCategoryJson") String subCategoryJson);

    @FormUrlEncoded
    @POST("haltSaleProduct/getHaltSaleProduct")
    Call<HaltSaleProduct> getHaltSaleReason(@Field("productId") int id,@Field("site") int site,@Field("status") int status);

    @GET("product/validIsShow")
    Call<ValidIsShow> validIsShow(@Query("websiteId") int site,@Query("storeId") int storeId);

    @GET("storeCategory/getStoreCategoryList")
    Call<Eggs> getStoreCategoryList(@Query("site") int site,@Query("storeId") int storeId);

    @GET("storeScannerFee/scannerFeeHome")
    Call<StoreDiyFee> storeDiyFee( @Query("siteId") int siteId, @QueryMap Map<String, Object> params);

    @GET("storeScannerFee/scannerFeeList")
    Call<ScannerFeeList> storeDiyFeeList( @Query("siteId") int siteId, @QueryMap Map<String, Object> params);


    @GET("storeScannerFee/scannerFeeDetail")
    Call<ScannerFeeListDetail> scannerFeeDrtail( @Query("siteId") int siteId, @QueryMap Map<String, Object> params);

    @GET("customerCommunity/find")
    Call<XiaoQuAddressItem> customerCommunity_find( @Query("site") int siteId,@QueryMap Map<String, Object> params);

    @GET("customerCommunity/find/community")
    Call<CommunityAddress> customerCommunity_find_addresss( @Query("siteId") int siteId,@QueryMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("customerCommunity/update")
    Call<CommunityAddressUpdate> customerCommunity_update( @Field("siteId") int siteId,@FieldMap Map<String, Object> params);


    @GET("order/pageListByVisitor")
    Call<PickUpOrder> pageListByVisitor( @Query("site") int siteId,@QueryMap Map<String, Object> params);


    @GET("customerCommunityDelivery/findCustomerCommunityDeliveryCode")
    Call<DeliveryCode> findCustomerCommunityDeliveryCode( @Query("site") int siteId,@QueryMap Map<String, Object> params);


    @GET("order/getDeliverFee")
    Call<DeliverFee> getDeliverFee( @Query("websiteId") int siteId,@QueryMap Map<String, Object> params);

    @GET("order/getTzServiceFee")
    Call<TzServiceFee> getTzServiceFee( @Query("websiteId") int siteId,@QueryMap Map<String, Object> params);

    @GET("order/getDeliverTime")
    Call<DeliverTime> getDeliverTime( @Query("websiteId") int siteId);

    @FormUrlEncoded
    @POST("customerCommunity/updateCommunityRef")
    Call<UpdateCommunityRef> updateCommunityRef( @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("order/updateDeliverAddress")
    Call<UpdateCommunityRef> updateDeliverAddress( @FieldMap Map<String, Object> params);

    @GET("tdj-user/user/user/getShareDuibaRegisterUrl")
    Call<DuiBaRegisterUrl> getShareDuibaRegisterUrl( @QueryMap Map<String, Object> params);



}

