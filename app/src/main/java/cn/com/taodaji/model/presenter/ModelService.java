package cn.com.taodaji.model.presenter;

import com.base.entity.ResultInfo;

import java.util.Map;

import cn.com.taodaji.model.entity.FavoriteCount;
import cn.com.taodaji.model.entity.FavoriteProducts;
import cn.com.taodaji.model.entity.FindByCustomerRecordId;
import cn.com.taodaji.model.entity.GoodsCategoryList_Resu;
import cn.com.taodaji.model.entity.Inforeaded;
import cn.com.taodaji.model.entity.MarketShopList;
import cn.com.taodaji.model.entity.NoticeGetCashResultBean;
import cn.com.taodaji.model.entity.PackageForegift;
import cn.com.taodaji.model.entity.PackingCashProgressBean;
import cn.com.taodaji.model.entity.PackingReturnMaxBean;
import cn.com.taodaji.model.entity.PayerListResultBean;
import cn.com.taodaji.model.entity.RegisterSaleTypeBean;
import cn.com.taodaji.model.entity.ProductStatus;
import cn.com.taodaji.model.entity.ScanQRCode;
import cn.com.taodaji.model.entity.ShareInfoBean;
import cn.com.taodaji.model.entity.ShareShopListResult;
import cn.com.taodaji.model.entity.ShopTypeListResultBean;
import cn.com.taodaji.model.entity.SpecialMerchants;
import cn.com.taodaji.model.entity.StoreRoleBean;
import cn.com.taodaji.model.entity.SupplySaleTypeBean;
import cn.com.taodaji.model.entity.StoreLimit;
import cn.com.taodaji.model.entity.WaitCountResultBean;
import cn.com.taodaji.model.entity.WaitNoticeResultBean;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by yangkuo on 2018-10-15.
 */
public interface ModelService {

    @GET("test/error11")
    Call<ResultInfo> test_error11();

    /**
     * apk自动下载更新
     */
    @Streaming //大文件
    @GET("")
    Call<ResponseBody> loadFile(@Url String url);


    /**
     * 行业信息数量
     *
     * @return
     */
    @GET("http://msg.taodaji.com.cn/inforeaded.php")
    Call<Inforeaded> inforeaded(@Query("mobile") String mobile, @Query("cityid") String cityid);

    /**
     * 二维码扫描获取数据接口
     *
     * @param map
     * @return
     */
    @GET("order/scan")
    Call<ScanQRCode> scanQRCode(@QueryMap Map<String, String> map);


    /**
     * 供应商注册时可选择的一级分类列表
     *
     * @return
     */
    @GET("common/supplier/categoryWebsiteFind")
    Call<RegisterSaleTypeBean> saleType(@QueryMap Map<String, String> map);

    /**
     * 供应商注册时可选择的一级分类列表
     *
     * @return
     */
    @GET("supplier/currentStoreCategoryList")
    Call<SupplySaleTypeBean> currentStoreCategoryList(@QueryMap Map<String, String> map);

    /**
     * 我的名片-申请分类-可申请的分类列表
     *
     * @return
     */
    @GET("supplier/applyStoreCategoryList")
    Call<RegisterSaleTypeBean> applyStoreCategoryList(@QueryMap Map<String, String> map);


    /**
     * 我的名片-申请分类-提交申请的一级分类
     *
     * @return
     */
    @FormUrlEncoded
    @POST("supplier/applyStoreCategory")
    Call<ResultInfo> applyStoreCategory(@FieldMap Map<String, Object> params);


    /**
     * 供应商发布商品时可选择的分类
     * site
     * storeId
     *
     * @return
     */
    @GET("product/findStoreCategoryList")
    Call<GoodsCategoryList_Resu> findStoreCategoryList(@QueryMap Map<String, String> map);

    /**
     * 获取供应商橱窗数量及分类数量限制
     * url	方法	说明
     * /common/store/limit	POST	获取供应商橱窗数量及分类数量限制
     * 参数说明
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * site	int	1		站点id
     * storeId	int	1		店铺id
     */
    @FormUrlEncoded
    @POST("common/store/limit")
    Call<StoreLimit> store_limit(@Field("site") int site, @Field("storeId") int storeId);

    /**
     * 获取供应商橱窗产品各状态统计数量
     * url	方法	说明
     * /common/product/status	POST	获取供应商橱窗产品各状态统计数量
     * 参数说明
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * site	int	1		站点id
     * storeId	int	1		店铺id
     */
    @FormUrlEncoded
    @POST("common/product/status")
    Call<ProductStatus> product_status(@Field("site") int site, @Field("storeId") int storeId);

    /**
     * 1、添加关注或收藏
     * url	方法	说明
     * /favorite/addFavorite	GET	添加商品或关注店铺，具体添加类型以type区分
     * 参数说明
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * storeOrProdId	int	1		店铺或商品id
     * personId	int	1		收藏人id
     * type	String	1		收藏类型，店铺：“1”； 商品 ：“2”
     */

    @GET("favorite/addFavorite")
    Call<ResultInfo<FavoriteCount>> favorite_addFavorite(@Query("userType") int userType, @Query("storeOrProdId") int storeOrProdId, @Query("personId") int personId, @Query("type") int type, @Query("site") int site);


    /**
     * 取消关注
     * url	方法	说明
     * /favorite/delFavorite	GET	取消收藏商品或取消关注店铺
     * 参数说明
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * storeOrProdId	int	1		店铺或商品id
     * personId	int	1		收藏人id
     * type	String	1		收藏类型，店铺：“1”； 商品 ：“2”
     */
    @GET("favorite/delFavorite")
    Call<ResultInfo<FavoriteCount>> favorite_delFavorite(@Query("userType") int userType, @Query("storeOrProdId") int storeOrProdId, @Query("personId") int personId, @Query("type") int type, @Query("site") int site);

    /**
     * 3、查看关注的店铺
     * url	方法	说明
     * /favorite/findFavoriteStores	GET	查看关注的店铺
     * 参数说明
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * site	int	1		站点id
     * personId	int	1		收藏人id
     * type	String	1		收藏类型，店铺：“1”； 商品 ：“2”
     * pn	int	1		起始页
     * ps	int	1		页大小
     */
    @GET("favorite/findFavoriteStores")
    Call<ResultInfo<MarketShopList>> favorite_findFavoriteStores(@Query("userType") int userType, @Query("personId") int personId, @Query("type") int type, @Query("site") int site, @Query("pn") int pn, @Query("ps") int ps);


    /**
     * 查看收藏的商品
     * url	方法	说明
     * /favorite/findFavoriteProducts	GET	查看收藏的商品
     * 参数说明
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * site	int	1		站点id
     * personId	int	1		收藏人id
     * type	String	1		收藏类型，店铺：“1”； 商品 ：“2”
     * pn	int	1		起始页
     * ps	int	1		页大小
     */

    @GET("favorite/findFavoriteProducts")
    Call<ResultInfo<FavoriteProducts>> favorite_findFavoriteProducts(@Query("userType") int userType, @Query("personId") int personId, @Query("type") int type, @Query("site") int site, @Query("pn") int pn, @Query("ps") int ps);

    /**
     * 退押金申请
     *
     * @return
     */
    @FormUrlEncoded
    @POST("packageForegiftAfterSale/applyReturn")
    Call<ResultInfo> applyPackingCash(@FieldMap Map<String, Object> params);


    /**
     * 获取退押金最大数量
     * site
     * storeId
     *
     * @return
     */
    @GET("packageForegiftAfterSale/toApply")
    Call<ResultInfo<PackingReturnMaxBean>> getCashMaxCount(@QueryMap Map<String, Object> map);

    /**
     * 获取退押金最大数量
     * site
     * storeId
     *
     * @return
     */
    @GET("packageForegiftAfterSale/afterSaleSchedule")
    Call<PackingCashProgressBean> getPackingCashProgress(@QueryMap Map<String, Object> map);


    /**
     * 撤销退押
     *
     * @return
     */
    @GET("packageForegiftAfterSale/cancleApply")
    Call<ResultInfo> cancelPackingCash(@QueryMap Map<String, Object> params);


    /**
     * customerFinanceRecord/detail/capitalDetail/findByCustomerRecordId/:entityId
     */
    @GET("customerFinanceRecord/detail/capitalDetail/findByCustomerRecordId/{entityId}")
    Call<FindByCustomerRecordId> findByCustomerRecordId(@Path("entityId") int entityId, @Query("site") int site);

    /**
     * 押金支付详情信息 【域名：http://test.51taodj.com:3001】
     * url	方法	说明
     * /supplierFinanceRecord/packageForegift/info	GET	押金支付详情信息
     * 例如：http://test.51taodj.com:3001/supplierFinanceRecord/packageForegift/info?packOrderId=1&site=2
     * GET请求参数说明
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * packOrderId	int	1		订单押金关联ID
     * site	int	1		城市ID
     * 返回值
     */
    @GET("supplierFinanceRecord/packageForegift/info")
    Call<PackageForegift> packageForegift(@Query("packOrderId") int packOrderId, @Query("site") int site);

    /**
     * 查询出所有的启动页广告
     * url	方法	说明
     * /specialMerchants/findAllAds	POST	启动页广告
     * 参数说明
     * 参数名	类型	非空(1是/0否)	默认值	说明
     * site	int	1		站点id
     * flag	int	1		标志状态：0启动页 1弹出层
     */
    @FormUrlEncoded
    @POST("specialMerchants/findAllAds")
    Call<ResultInfo<SpecialMerchants>> specialMerchants_findAllAds(@Field("site") int site, @Field("flag") int flag);

    /**
     * 门店类型
     */
    @GET("customer/findHotelTypeList")
    Call<ShopTypeListResultBean> findHotelTypeList(@Query("site") int site);

    /**
     * 支付人列表
     */
    @GET("customer/employees/payer")
    Call<PayerListResultBean> getPayerList(@Query("site") int site, @Query("customerEntityId") int customerEntityId, @Query("loginUserEntityId") int loginUserEntityId);

    /**
     * 查询出待办通知总条数
     */
    @GET("fund/agencyAffairNotice/getAgencyAffairNoticeCount")
    Call<WaitCountResultBean> getWaitCount(@Query("role") int role ,@Query("site") int site, @Query("customerId") int customerId);

    /**
     * 查询出待办通知总条数
     */
    @GET("fund/agencyAffairNotice/findPageList")
    Call<WaitNoticeResultBean> getWaitList(@Query("role") int role ,@Query("customerId") int customerId, @Query("site") int site, @Query("pn") int pn, @Query("ps") int ps);

    /**
     * 查询出消息通知列表
     */
    @GET("fund/noticeMessage/findPageList")
    Call<WaitNoticeResultBean> getNoticeList(@Query("role") int role ,@Query("customerId") int customerId, @Query("site") int site, @Query("pn") int pn, @Query("ps") int ps);

    /**
     * 查询出通知提现详情（采购商 提现在管理后台进行提现）
     * 提现记录的Id  entityId
     */
    @GET("CustomerCashWithdrwalApplication/findCustomerWithdrawalDetailById/{entityId}")
    Call<NoticeGetCashResultBean> getCashResultDetail(@Path("entityId") int entityId,@Query("site") int site);

    /**
     * 查询出已邀请店铺列表
     */
    @GET("customer/findApplyHotelList")
    Call<ShareShopListResult> getShareShopList(@Query("applyCode") String applyCode, @Query("site") int site,@Query("queryDate") String queryDate, @Query("type")int type,@Query("pn") int pn,@Query("ps") int ps);

    /**
     * 查询某采购商的未读消息通知条数  采购商Id  customerId
     */
    @GET("fund/noticeMessage/noReadCount")
    Call<WaitNoticeResultBean> getNoticeUnReadCount(@Query("role") int role ,@Query("customerId") int customerId, @Query("site") int site);

    /**
     * 更新消息通知已读
     */
    @GET("fund/noticeMessage/updateIsRead")
    Call<WaitNoticeResultBean> updateNoticeUnReadCount(@Query("entityId") int entityId, @Query("site") int site);

    /**
     * 获取分享推荐图片地址，分享地址
     */
    @GET("fund/qrcode/createStr")
    Call<ShareInfoBean> getShareInfoData(@Query("verifyCode") String verifyCode, @Query("site") int site);
}
