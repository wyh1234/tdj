package cn.com.taodaji.ui.activity.shopManagement;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.model.entity.CommodityAliasVariety;
import cn.com.taodaji.model.entity.CommodityLabel;
import cn.com.taodaji.model.entity.GoodsSpecification;
import cn.com.taodaji.model.entity.ImageUpload;
import cn.com.taodaji.model.entity.NameValue;
import cn.com.taodaji.model.entity.Property;
import cn.com.taodaji.model.entity.StandardList;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.ui.ppw.SimpleButtonBottomPPW;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.FindProductCommission;
import cn.com.taodaji.model.entity.GoodsUpdate;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apkfuns.logutils.LogUtils;
import com.base.common.Config;
import com.base.glide.ImageLoaderUtils;
import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;

import cn.com.taodaji.ui.fragment.MyselfShopManagement;
import cn.com.taodaji.model.entity.GoodsInformation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.ui.ppw.TakePhotoPopupWindow;
import cn.com.taodaji.viewModel.adapter.GoodsPropertiesAdapter;
import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tools.activity.SimpleToolbarActivity;

import com.base.listener.UploadPicturesDoneListener;

import tools.activity.TakePhotosActivity;
import tools.activity.TakePhotosDataActivity;
import tools.extend.GifSizeFilter;
import tools.extend.MyGlideEngine;
import tools.extend.TakePhotoUtils;
import tools.fragment.AddedPicturesFragment;
import tools.jni.JniMethod;

import com.base.takephoto.model.TResult;
import com.base.utils.ADInfo;
import com.base.utils.BitmapUtil;
import com.base.utils.IOUtils;
import com.base.utils.JavaMethod;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.BaseVM;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;


import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tdj.zxinglibrary.decode.ImageUtil;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.android.agoo.common.CallBack;


/*发布商品activity
 * */
public class ReleaseCommodityGoodsCreateActivity extends TakePhotosActivity implements UploadPicturesDoneListener, View.OnClickListener {

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
    }

    private View mainView;
    private CityPickerView cityPickerView = new CityPickerView();
    private AddedPicturesFragment addedPicturesFragment;

    private Map<String, Object> map;
    private int goodsEditState = 0;//商品编辑状态 0，发布,1,编辑，2，重新编辑

    private SimpleButtonBottomPPW sale_type_ppw;
    private SingleRecyclerViewAdapter sale_type_adapter;
    private TextView tv_goods_name,tv_view_nickname, tv_goods_species,tv_goods_origin,tv_goods_standard,
            tv_sale_type, tv_rate_count, tv_specififcation;
    private EditText et_goods_feature;

    private String credentialsImage;//商品资质
    private int productId = -1;
    private String gallery = "";//商品图片
    private String name="",label="",color="",origin="",size="",shape="";
    private int varietyEntityId=-1;

    private int categoryId;
    private int typeId;
    private int commodityId;

    private FindProductCommission isp_data;
    private  int parentCategoryId;

    private int isP = 0;//0零售，1事件批，-1全部
    private int productCriteria = 1;//  商品标准， 1：通货商品 2 ： 精品商品 '
    private int isF;//是否有押金0：无，1：有

    private Button bt_goods_create_ok;

    private List<ADInfo> sale_type_list = new ArrayList<>();//售卖方式数据源
    private List<ADInfo> goods_type_list = new ArrayList<>();//商品标准
    private List<ADInfo> goods_nickname_list = new ArrayList<>();
    private List<ADInfo> goods_spec_list = new ArrayList<>();
    private List<CommodityLabel.DataBean.ListBean> labelList = new ArrayList<>();
    private GoodsPropertiesAdapter adapter;
    private JSONArray jsonArray = new JSONArray();
    private Bitmap bitmap;
    private Switch sw_switch;
    private View ll_cash_pledge_context, ll_goods_standard, ll_cash_pledge_group,ll_goods_species,
            ll_goods_origin;

    private EditText et_cash_pledge_packageName, et_cash_pledge_foregift;
    private RecyclerView recyclerView;
    private TextView tv_goods_feature;
    private RelativeLayout rl_c_image;
    private ImageView c_image,delete_image;
    private  RxPermissions rxPermissions;
    public static final int REQUEST_CODE_CHOOSE_GRIDE = 23;
    private String c_image_url;
    private TakePhotoPopupWindow takePhotoPopupWindow;
    private LinearLayout ll_isCanteen;
    private CheckBox checkBox;
    public String getC_image_url() {
        return c_image_url;
    }

    public void setC_image_url(String c_image_url) {
        this.c_image_url = c_image_url;
    }

    @Override
    protected void initMainView() {
         rxPermissions= new RxPermissions(this);
        mainView = getLayoutView(R.layout.activity_release_commodity_goods_create);
        body_scroll.addView(mainView);
        addedPicturesFragment = (AddedPicturesFragment) getSupportFragmentManager().findFragmentById(R.id.addedPicturesFragment);
        addedPicturesFragment.setCrop(true);
        addedPicturesFragment.setType(1);
        tv_goods_name = mainView.findViewById(R.id.tv_goods_name);//商品类目
        rl_c_image= mainView.findViewById(R.id.rl_c_image);
        c_image= mainView.findViewById(R.id.c_image);
        delete_image= mainView.findViewById(R.id.delete_image);
        tv_view_nickname = mainView.findViewById(R.id.tv_view_nickname);//查看别名
        ll_isCanteen= mainView.findViewById(R.id.ll_isCanteen);//查看别名
        checkBox=mainView.findViewById(R.id.tv_isCanteen);
        tv_view_nickname.setOnClickListener(this);
        rl_c_image.setOnClickListener(this);
        ll_isCanteen.setOnClickListener(this);
        delete_image.setOnClickListener(this);
        tv_goods_species = mainView.findViewById(R.id.tv_goods_species);//商品品种
        et_goods_feature = mainView.findViewById(R.id.et_goods_feature);//商品特性
        tv_goods_feature= mainView.findViewById(R.id.tv_goods_feature);//商品特性
        tv_goods_origin = mainView.findViewById(R.id.tv_goods_origin);//商品产地
        tv_sale_type = mainView.findViewById(R.id.tv_sale_type);//售卖方式
        tv_rate_count = mainView.findViewById(R.id.tv_rate_count);//售卖费率
        tv_goods_standard = mainView.findViewById(R.id.tv_goods_standard);//商品标准
        tv_specififcation = mainView.findViewById(R.id.tv_specififcation);//出售规格

        bt_goods_create_ok = mainView.findViewById(R.id.bt_goods_create_ok);
        ll_goods_standard = mainView.findViewById(R.id.ll_goods_standard);
        ll_goods_standard.setOnClickListener(this);
        ll_goods_species = mainView.findViewById(R.id.ll_goods_species);
        ll_goods_species.setOnClickListener(this);
        ll_goods_origin = mainView.findViewById(R.id.ll_goods_origin);
        ll_goods_origin.setOnClickListener(this);

        sw_switch = mainView.findViewById(R.id.sw_switch);
        ll_cash_pledge_context = mainView.findViewById(R.id.ll_cash_pledge_context);
        et_cash_pledge_packageName = mainView.findViewById(R.id.et_cash_pledge_packageName);
        et_cash_pledge_foregift = mainView.findViewById(R.id.et_cash_pledge_foregift);
        ll_cash_pledge_group = mainView.findViewById(R.id.ll_cash_pledge_group);

        cityPickerView.init(this);
        CityConfig cityConfig = new CityConfig.Builder().build();
        cityPickerView.setConfig(cityConfig);

        cityPickerView.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                origin = province.getName()+city.getName();
                tv_goods_origin.setText(origin);
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject temp = jsonArray.getJSONObject(i);
                    if (temp.getString("pName").equals("产地")) {
                        jsonArray.remove(temp);
                    }
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("pName","产地");
                jsonObject.put("pValue",origin);
                jsonArray.add(jsonObject);
            }

            @Override
            public void onCancel() {
                ToastUtils.showLongToast(ReleaseCommodityGoodsCreateActivity.this, "已取消");
            }
        });

        sw_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ll_cash_pledge_context.setVisibility(View.VISIBLE);
                    isF = 1;
                } else {
                    ll_cash_pledge_context.setVisibility(View.GONE);
                    isF = 0;
                }
            }
        });

        goodsEditState = getIntent().getIntExtra("goodsEditState", 0);

        getRootView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (bt_goods_create_ok != null) {
                    ViewUtils.setViewBottom(bt_goods_create_ok, -1);
                }
            }
        });

        initPPW();

        parentCategoryId = getIntent().getIntExtra("parentCategoryId", 0);
    /*    if (parentCategoryId!=10){
            ll_goods_species.setVisibility(View.GONE);
            ll_goods_origin.setVisibility(View.GONE);
        }*/
        recyclerView = mainView.findViewById(R.id.rv_goods_property);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GoodsPropertiesAdapter(labelList,this);
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                LogUtils.e(labelList);
//                    if (!labelList.get(position).isF())return;

                List<ADInfo> list = new ArrayList<>();
                if (labelList.get(position).getDatas()!=null){
                    for (Property property : labelList.get(position).getDatas()){
                        ADInfo adInfo = new ADInfo();
                        adInfo.setImageName(property.getDataValue());
                        adInfo.setImageId("");
                        adInfo.setEntity_id(property.getDataId());
                        adInfo.setGoodsCount(position);
                        list.add(adInfo);
                    }
                }

                if (sale_type_adapter != null && sale_type_ppw != null && !sale_type_ppw.isShowing()) {
                    sale_type_adapter.setList(list);
//                    if (!ListUtils.isNullOrZeroLenght(list.get(position).getImageName()))return;
                    if (goodsEditState>0){
//                        if (MyselfShopManagement.goodsInformation.getIsEdit()==1){
                            sale_type_ppw.showAtLocation(v, Gravity.CENTER, 0, 0);
//                        }

                    }else {
                        sale_type_ppw.showAtLocation(v, Gravity.CENTER, 0, 0);
                    }

                }
            }
        });
        recyclerView.setAdapter(adapter);


    }
    @Override
    protected void initData() {
        if (PublicCache.loginSupplier == null) return;

        switch (goodsEditState){
            case 0:
                setTitle("发布商品");
                if (PublicCache.loginSupplier == null) return;
                MyselfShopManagement.goodsInformation = new GoodsInformation();

                Intent data = getIntent();
                name = data.getStringExtra("name");
                categoryId = data.getIntExtra("categoryId", 0);
                typeId = data.getIntExtra("typeId", 0);
                commodityId = data.getIntExtra("commodityId", 0);
                isP = 0;//售卖方式
                productCriteria = 1;//商品标准
                isF = 0;//是否收押金

                MyselfShopManagement.goodsInformation.setName(name);
                MyselfShopManagement.goodsInformation.setCategoryId(categoryId);
                MyselfShopManagement.goodsInformation.setCommodityId(commodityId);
                MyselfShopManagement.goodsInformation.setStore(PublicCache.loginSupplier.getStore());

                tv_goods_name.setText(name);
                checkBox.setEnabled(true);
                break;
            case 1:
                checkBox.setEnabled(false);
                parentCategoryId=MyselfShopManagement.goodsInformation.getParentCategoryId();
                ll_goods_standard.setEnabled(false);
                setTitle("编辑商品");
                ll_goods_species.setVisibility(View.VISIBLE);




                if (MyselfShopManagement.goodsInformation==null){
                    ll_cash_pledge_group.setVisibility(View.GONE);
                }else{
                    if (MyselfShopManagement.goodsInformation!=null&&TextUtils.isEmpty(MyselfShopManagement.goodsInformation.getPackageName())){
                        ll_cash_pledge_group.setVisibility(View.GONE);
                    }
                }
                LogUtils.e(getIntent().getIntExtra("isCanteen",0)==1);
                    checkBox.setChecked(getIntent().getIntExtra("isCanteen",0)==1);

                if (MyselfShopManagement.goodsInformation == null) return;
                //初始化数据
                productId = MyselfShopManagement.goodsInformation.getEntityId();
                credentialsImage = MyselfShopManagement.goodsInformation.getCredentialsImage();
                name = MyselfShopManagement.goodsInformation.getName();
                isP = MyselfShopManagement.goodsInformation.getIsP();

                List<NameValue> parseArray = JSON.parseArray(MyselfShopManagement.goodsInformation.getProductProperty(), NameValue.class);
                if (parseArray!=null){
                    for (NameValue value : parseArray){
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("pName",value.getPName());
                        jsonObject.put("pValue",value.getPValue());
//                        jsonArray.add(jsonObject);
                        if (value.getPName().equals("产地")){
                            tv_goods_origin.setText(value.getPValue());
                        }
                        jsonArray.add(jsonObject);

                        if (parentCategoryId==10&&MyselfShopManagement.goodsInformation.getIsEdit()==0){
                            CommodityLabel.DataBean.ListBean listBean=new CommodityLabel.DataBean.ListBean();
                            if (!value.getPName().equals("产地")){
                                listBean.setPropertyZhName(value.getPName());
                                listBean.setContent(value.getPValue());
                                labelList.add(listBean);
                            }else {
                                tv_goods_origin.setText(value.getPValue());
                            }


                        }
                    }
                    adapter.notifyDataSetChanged();


                }


                if (TextUtils.isEmpty(MyselfShopManagement.goodsInformation.getPackageName())){
                    isF = 0;
                } else{
                    isF = 1;
                    et_cash_pledge_packageName.setText(MyselfShopManagement.goodsInformation.getPackageName());
                    et_cash_pledge_foregift.setText(MyselfShopManagement.goodsInformation.getForegift().toString());
                }

                productCriteria = MyselfShopManagement.goodsInformation.getProductCriteria();//商品标准
                if (productCriteria == 0) productCriteria = 1;


                et_goods_feature.setText(MyselfShopManagement.goodsInformation.getNickName());

                if (!ListUtils.isNullOrZeroLenght(MyselfShopManagement.goodsInformation.getVarietyName())){

                    tv_goods_species.setText(MyselfShopManagement.goodsInformation.getVarietyName());

                }
                tv_goods_name.setText(name);
                varietyEntityId=MyselfShopManagement.goodsInformation.getVarietyEntityId();
                if (getIntent().getStringExtra("custmerExhibitionImage")!=null){
                    setC_image_url(getIntent().getStringExtra("custmerExhibitionImage"));
                    ImageLoaderUtils.loadImage(c_image,getIntent().getStringExtra("custmerExhibitionImage"));
                    delete_image.setVisibility(View.VISIBLE);
                }else {
                    setC_image_url("");
                    c_image.setBackgroundResource(R.mipmap.c_image);
                    delete_image.setVisibility(View.GONE);
                }
                List<String> imageurl = MyselfShopManagement.goodsInformation.getGallery();
                if (imageurl != null && !imageurl.isEmpty()) {
                    List<ADInfo> adInfoList = new ArrayList<>();
                    for (int i = 0; i < imageurl.size(); i++) {
                        ADInfo adInfo = new ADInfo();
                        adInfo.setImageId(i + "");
                        adInfo.setImageUrl(imageurl.get(i));
                        adInfo.setImageObject(imageurl.get(i));
                        adInfoList.add(adInfo);
                    }
                    addedPicturesFragment.setList(adInfoList);
                }
                specififcationUpdate();
                break;
            case 2:
                checkBox.setEnabled(false);
                parentCategoryId=MyselfShopManagement.goodsInformation.getParentCategoryId();
                ll_goods_standard.setEnabled(false);
                setTitle("编辑商品");
                ll_goods_species.setVisibility(View.VISIBLE);


                if (MyselfShopManagement.goodsInformation==null){
                    ll_cash_pledge_group.setVisibility(View.GONE);
                }else{
                    if (MyselfShopManagement.goodsInformation!=null&&TextUtils.isEmpty(MyselfShopManagement.goodsInformation.getPackageName())){
                        ll_cash_pledge_group.setVisibility(View.GONE);
                    }
                }
                checkBox.setChecked(getIntent().getIntExtra("isCanteen",0)==1);
                if (MyselfShopManagement.goodsInformation == null) return;
                //初始化数据
                productId = MyselfShopManagement.goodsInformation.getEntityId();
                credentialsImage = MyselfShopManagement.goodsInformation.getCredentialsImage();
                name = MyselfShopManagement.goodsInformation.getName();
                isP = MyselfShopManagement.goodsInformation.getIsP();

                List<NameValue> parseArray_2= JSON.parseArray(MyselfShopManagement.goodsInformation.getProductProperty(), NameValue.class);
                if (parseArray_2!=null){
                    for (NameValue value : parseArray_2){
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("pName",value.getPName());
                        jsonObject.put("pValue",value.getPValue());
//                        jsonArray.add(jsonObject);
                        if (value.getPName().equals("产地")){
                            tv_goods_origin.setText(value.getPValue());

                        }
                        jsonArray.add(jsonObject);
                        if (parentCategoryId==10&&MyselfShopManagement.goodsInformation.getIsEdit()==0){
                            CommodityLabel.DataBean.ListBean listBean=new CommodityLabel.DataBean.ListBean();
                            if (!value.getPName().equals("产地")){
                                listBean.setPropertyZhName(value.getPName());
                                listBean.setContent(value.getPValue());
                                labelList.add(listBean);
                            }else {
                                tv_goods_origin.setText(value.getPValue());
                            }


                        }
                    }
                    adapter.notifyDataSetChanged();
                }


                if (TextUtils.isEmpty(MyselfShopManagement.goodsInformation.getPackageName())){
                    isF = 0;
                } else{
                    isF = 1;
                    et_cash_pledge_packageName.setText(MyselfShopManagement.goodsInformation.getPackageName());
                    et_cash_pledge_foregift.setText(MyselfShopManagement.goodsInformation.getForegift().toString());
                }

                productCriteria = MyselfShopManagement.goodsInformation.getProductCriteria();//商品标准
                if (productCriteria == 0) productCriteria = 1;


                et_goods_feature.setText(MyselfShopManagement.goodsInformation.getNickName());
                if (!ListUtils.isNullOrZeroLenght(MyselfShopManagement.goodsInformation.getVarietyName())){

                    tv_goods_species.setText(MyselfShopManagement.goodsInformation.getVarietyName());

                }
                varietyEntityId=MyselfShopManagement.goodsInformation.getVarietyEntityId();
                tv_goods_name.setText(name);

                if (getIntent().getStringExtra("custmerExhibitionImage")!=null){
                    setC_image_url(getIntent().getStringExtra("custmerExhibitionImage"));
                    ImageLoaderUtils.loadImage(c_image,getIntent().getStringExtra("custmerExhibitionImage"));
                    delete_image.setVisibility(View.VISIBLE);
                }else {
                    setC_image_url("");
                    ImageLoaderUtils.loadImage(c_image,R.mipmap.c_image);
                    delete_image.setVisibility(View.GONE);
                }

                List<String> imageurl_2 = MyselfShopManagement.goodsInformation.getGallery();
                if (imageurl_2 != null && !imageurl_2.isEmpty()) {
                    List<ADInfo> adInfoList = new ArrayList<>();
                    for (int i = 0; i < imageurl_2.size(); i++) {
                        ADInfo adInfo = new ADInfo();
                        adInfo.setImageId(i + "");
                        adInfo.setImageUrl(imageurl_2.get(i));
                        adInfo.setImageObject(imageurl_2.get(i));
                        adInfoList.add(adInfo);
                    }
                    addedPicturesFragment.setList(adInfoList);
                }
                specififcationUpdate();

                    break;
            default: break;



        }
        if(parentCategoryId==10) {
            ll_isCanteen.setVisibility(View.VISIBLE);
        }else {
            ll_isCanteen.setVisibility(View.GONE);
        }

        if (isF == 0) {
            sw_switch.setChecked(false);
            ll_cash_pledge_context.setVisibility(View.GONE);
        } else {
            sw_switch.setChecked(true);
            ll_cash_pledge_context.setVisibility(View.VISIBLE);
        }

        if (MyselfShopManagement.goodsInformation != null) {
            loading();
            //初始化类别 费率
            getRequestPresenter().findProductCommission(MyselfShopManagement.goodsInformation.getCategoryId(), MyselfShopManagement.goodsInformation.getCommodityId(), new ResultInfoCallback<FindProductCommission>(this) {
                @Override
                public void onSuccess(FindProductCommission body) {
                    loadingDimss();
                    String[] li_str = UIUtils.getStringArray(R.array.is_P);

                    //发面商品时已默认的是0，编辑时isP已初始化
                    if (isP == 0) {
                        tv_sale_type.setText(li_str[0]);
                        tv_rate_count.setText(body.getRetailCommission());
                    } else {
                        tv_sale_type.setText(li_str[1]);
                        tv_rate_count.setText(body.getWholesaleCommission());
                    }
                    isp_data = body;

                    int iiisp;
                    if (TextUtils.isEmpty(body.getWholesaleLevel1())) {
                        iiisp = 0;
                    } else {
                        iiisp = 1;
                    }

                    if (sale_type_adapter != null && sale_type_adapter.getRealCount() - 1 == iiisp)
                        return;

                    if (sale_type_list.size() > 0) sale_type_list.clear();

                    for (int i = 0; i <= iiisp; i++) {

                        if (i == 0) {
                            ADInfo adInfo = new ADInfo();
                            adInfo.setImageName(li_str[i]);
                            adInfo.setImageGoodsType(i);
                            adInfo.setImageContent(body.getRetailCommission());
                            // adInfo.setImageUrl("(交易费率" + adInfo.getImageContent() + "%)");
                            adInfo.setImageId("sale_type");
                            sale_type_list.add(adInfo);
                        }
                        if (i == 1) {
                            ADInfo adInfo = new ADInfo();
                            adInfo.setImageName(li_str[i]);
                            adInfo.setImageGoodsType(i);
                            adInfo.setImageContent(body.getWholesaleCommission());
                            //adInfo.setImageUrl("(交易费率" + adInfo.getImageContent() + "%)");
                            adInfo.setImageId("sale_type");
                            sale_type_list.add(adInfo);
                        }

                    }

                }

                @Override
                public void onFailed(int errCode, String msg) {
                    super.onFailed(errCode, msg);
                    loadingDimss();
                }
            });
            if (PublicCache.loginSupplier.getSupplierSaleType()==0||PublicCache.loginSupplier.getSupplierSaleType()==1){
                isP = 0;
                tv_sale_type.setText("零售");
            }else {
                isP = 1;
                tv_sale_type.setText("整件批");
            }
            LogUtils.e(MyselfShopManagement.goodsInformation.getIsEdit());
            if (getIntent().getIntExtra("isForceTemplate", 0)==1){
                ll_goods_species.setVisibility(View.VISIBLE);
                ll_goods_origin.setVisibility(View.VISIBLE);
            }else {
                ll_goods_species.setVisibility(View.GONE);
                ll_goods_origin.setVisibility(View.GONE);
            }

            if ((parentCategoryId==10&&goodsEditState>0)||(goodsEditState>0&&getIntent().getIntExtra("isForceTemplate", 0)==1)){

                RequestPresenter.getInstance().getCommodityLabel(MyselfShopManagement.goodsInformation.getCommodityId(), new RequestCallback<CommodityLabel>() {
                    @Override
                    protected void onSuc(CommodityLabel body) {
                        labelList.clear();
                        for (CommodityLabel.DataBean.ListBean bean :body.getData().getList()){
                            if (!bean.getPropertyZhName().equals("产地")){
                                labelList.add(bean);
                            }
                        }

                        if ((goodsEditState==1||goodsEditState==2)){
                            List<NameValue> parseArray = JSON.parseArray(MyselfShopManagement.goodsInformation.getProductProperty(), NameValue.class);
                            if (parseArray!=null){
                                for (NameValue value : parseArray){
                                    for (CommodityLabel.DataBean.ListBean bean : labelList){
                                        if (value.getPName().equals(bean.getPropertyZhName())){
                                            bean.setContent(value.getPValue());

                                        }

                                    }

                                }
                            }
                        }

                        for (CommodityLabel.DataBean.ListBean bean : labelList){
                            if (ListUtils.isNullOrZeroLenght(bean.getContent())) {
                                bean.setF(true);
                            }else {
                                bean.setF(false);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailed(int errCode, String msg) {
                        UIUtils.showToastSafe(msg);
                        finish();
                    }
                });
            }
            if (goodsEditState==0&&getIntent().getIntExtra("isForceTemplate", 0)==1){

                    RequestPresenter.getInstance().getCommodityLabel(MyselfShopManagement.goodsInformation.getCommodityId(), new RequestCallback<CommodityLabel>() {
                        @Override
                        protected void onSuc(CommodityLabel body) {
                            labelList.clear();
                            for (CommodityLabel.DataBean.ListBean bean :body.getData().getList()){
                                if (!bean.getPropertyZhName().equals("产地")){
                                    labelList.add(bean);
                                }
                            }

                            if (goodsEditState==0){
                                List<NameValue> parseArray = JSON.parseArray(MyselfShopManagement.goodsInformation.getProductProperty(), NameValue.class);
                                if (parseArray!=null){
                                    for (NameValue value : parseArray){
                                        for (CommodityLabel.DataBean.ListBean bean : labelList){
                                            if (value.getPName().equals(bean.getPropertyZhName())){
                                                bean.setContent(value.getPValue());
                                            }
                                        }

                                    }
                                }
                            }
                            for (CommodityLabel.DataBean.ListBean bean : labelList){
                                if (ListUtils.isNullOrZeroLenght(bean.getContent())) {
                                    bean.setF(true);
                                }else {
                                    bean.setF(false);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailed(int errCode, String msg) {
                            UIUtils.showToastSafe(msg);
                            finish();
                        }
                    });
            }
            RequestPresenter.getInstance().getCommodityAliasVariety(MyselfShopManagement.goodsInformation.getCommodityId(), new RequestCallback<CommodityAliasVariety>() {
                @Override
                protected void onSuc(CommodityAliasVariety body) {

                    goods_nickname_list.clear();
                    goods_spec_list.clear();
                    for (CommodityAliasVariety.DataBean.AliasListBean bean : body.getData().getAliasList()){
                        ADInfo adInfo = new ADInfo();
                        adInfo.setImageName(bean.getAliasName());
                        adInfo.setImageId("nickname");
                        adInfo.setEntity_id(bean.getAliasEntityId());
                        goods_nickname_list.add(adInfo);
                    }
                    for (CommodityAliasVariety.DataBean.VarietyListBean bean : body.getData().getVarietyList()){
                        ADInfo adInfo = new ADInfo();
                        adInfo.setImageName(bean.getVarietyName());
                        adInfo.setImageId("spec");
                        adInfo.setEntity_id(bean.getVarietyEntityId());
                        goods_spec_list.add(adInfo);
                    }



                }

          /*      @Override
                public void onFailed(int errCode, String msg) {
                    super.onFailed(errCode, msg);
                    if (errCode==1){
                        UIUtils.showToastSafe(msg);
                        finish();
                        return;
                    }
                }*/
            });


            RequestPresenter.getInstance().getStandardList(PublicCache.loginSupplier.getStore(), MyselfShopManagement.goodsInformation.getCommodityId(), MyselfShopManagement.goodsInformation.getCategoryId(), parentCategoryId, new RequestCallback<StandardList>() {
                @Override
                protected void onSuc(StandardList body) {
                    for (StandardList.DataBean.CriteriaListBean bean : body.getData().getCriteriaList()){
                        ADInfo adInfo = new ADInfo();
                        adInfo.setImageName(bean.getProductCriteriaName());
                        adInfo.setImageId("goods_type");
                        adInfo.setImageGoodsType(bean.getProductCriteria());
                        goods_type_list.add(adInfo);
                    }
                }
            });

        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            //发布商品规格
            case R.id.ll_specififcation:
                if (isp_data == null) return;
                intent = new Intent(this, SpecificationActivity.class);
                intent.putExtra("isP", isP);
                intent.putExtra("data", isp_data);
                intent.putExtra("isEdit", goodsEditState > 0);
                intent.putExtra("goodsNameEdit", goodsEditState == 2);
                startActivity(intent);
                break;
            //资质上传
            case R.id.linearLayout_1:
                if (goodsEditState > 0) {
                    intent = new Intent(this, LookQualificationsActivity.class);
                    intent.putExtra("productId", productId);
                    startActivity(intent);
                } else {
                    intent = new Intent(this, ImageUploadMoreActivity.class);
                    intent.putExtra("data", credentialsImage);
                    intent.putExtra("productId", productId);
                    startActivityForResult(intent, 200);
                }
                break;
            //提交
            case R.id.bt_goods_create_ok:
                loading();
                //如果图片上传完成，直接上传
                if (addedPicturesFragment.isUploadDone()) {
                    uploadPicturesIsDone(null);
                    //如果图片上传未完成，设置上传完成后自动回调
                } else {
                    addedPicturesFragment.setCallBack(true);
                }
                break;
            //售卖方式
            case R.id.ll_sale_type://sale_type_ppw   sale_type_adapter
                if (parentCategoryId==10) return;
                if (goodsEditState != 0) return;
                if (sale_type_adapter != null && sale_type_ppw != null && !sale_type_ppw.isShowing()) {
                    sale_type_adapter.setList(sale_type_list);
                    sale_type_ppw.showAtLocation(v, Gravity.CENTER, 0, 0);
                }
                break;
            case R.id.ll_goods_standard:
                if (goodsEditState != 0) return;
                if (sale_type_adapter != null && sale_type_ppw != null && !sale_type_ppw.isShowing()) {
                    sale_type_adapter.setList(goods_type_list);
                    sale_type_ppw.showAtLocation(v, Gravity.CENTER, 0, 0);
                }
                break;
            case R.id.ll_goods_origin:
//                if ((goodsEditState>0&&MyselfShopManagement.goodsInformation.getIsEdit()==1)||goodsEditState==0){
                    cityPickerView.showCityPicker( );
//                }

                break;
            case R.id.ll_goods_species:
                LogUtils.e(goodsEditState);

                if (goodsEditState==1){
                    return;
                }
//                if (goodsEditState==2){
//                    if (!ListUtils.isNullOrZeroLenght(MyselfShopManagement.goodsInformation.getVarietyName()))return;
//                }
                if (sale_type_adapter != null && sale_type_ppw != null && !sale_type_ppw.isShowing()) {
                    sale_type_adapter.setList(goods_spec_list);
                    sale_type_ppw.showAtLocation(v, Gravity.CENTER, 0, 0);
                }
                break;
            case R.id.tv_view_nickname:
                if (goods_nickname_list.size()>0){
                    if (sale_type_adapter != null && sale_type_ppw != null && !sale_type_ppw.isShowing()) {
                        sale_type_adapter.setList(goods_nickname_list);
                        sale_type_ppw.showAtLocation(v, Gravity.CENTER, 0, 0);
                    }
                }else {
                    UIUtils.showToastSafe("没有其他名称");
                }

                break;

            case R.id.rl_c_image:
                if (UIUtils.isNullOrZeroLenght(getC_image_url())){


                rxPermissions.request(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            //从相册中选择图片 此处使用知乎开源库Matisse
                 /*           Matisse.from(ReleaseCommodityGoodsCreateActivity.this).choose(MimeType.ofImage())
                                    .theme(R.style.Matisse_Dracula)
                                    .countable(false)//true:选中后显示数字;false:选中后显示对号
                                    .maxSelectable(1)
                                    .capture(true)
                                    .captureStrategy(new CaptureStrategy(true, "cn.com.taodaji.fileprovider")) //是否拍照功能，并设置拍照后图片的保存路径; FILE_PATH = 你项目的包名.fileprovider,必须配置不然会抛异常
                                    .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                    .originalEnable(true)
                                    .maxOriginalSize(10)
                                    .thumbnailScale(0.85f)
                                    .imageEngine(new MyGlideEngine())
                                    .forResult(REQUEST_CODE_CHOOSE_GRIDE);*/
                            String fileName = System.currentTimeMillis() + ".jpg";
                            Uri imageUri = Uri.fromFile(IOUtils.createFile(Config.imageSaveDir, fileName));
                            if (takePhotoPopupWindow == null) {
                                takePhotoPopupWindow = new TakePhotoPopupWindow(v) {
                                    @Override
                                    public void goCamera() {
                                        TakePhotoUtils.getInstance().setCrop(true).setCorpOwnTool(true).setCorpSize(false,700,400).setImageUri(imageUri).openCamera(getTakePhoto());

                                    }

                                    @Override
                                    public void goAlbum() {

                                        TakePhotoUtils.getInstance().setCrop(true).setCorpOwnTool(true).setCorpSize(false,700,400).setImageUri(imageUri).openPhotoAlbum(getTakePhoto(), 1, true);

                                    }
                                };
                                takePhotoPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                            } else {
                                if (!takePhotoPopupWindow.isShowing()) {
                                    takePhotoPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0 );
                                }

                            }
                        }
                    }
                });
                }

                break;

            case R.id.delete_image:
                setC_image_url("");
                ImageLoaderUtils.loadImage(c_image,R.mipmap.c_image);
                delete_image.setVisibility(View.GONE);
                break;
            case R.id.ll_isCanteen:


                break;
        }
    }

    private void initPPW() {
        if (sale_type_ppw == null) {
            View view = ViewUtils.getLayoutView(this, R.layout.ppw_goods_type_selector);
            sale_type_ppw = new SimpleButtonBottomPPW(view);
            sale_type_adapter = new SingleRecyclerViewAdapter() {

                @Override
                public View onCreateHolderView(ViewGroup parent, int viewType) {
                    return ViewUtils.getFragmentView(parent, R.layout.ppw_goods_type_item);
                }

                @Override
                public void initBaseVM() {
                    putBaseVM(TYPE_CHILD, new BaseVM() {
                        @Override
                        protected void dataBinding() {
                            putRelation("imageName", R.id.tv_name);
                            putRelation("imageUrl", R.id.tv_description);

                            putViewOnClick(R.id.item_view);
                        }

                        @Override
                        public void setValue(@NonNull TextView textView, @NonNull Object value) {
                            if (textView.getId() == R.id.tv_description) {
                                if (value.toString().equals("")) {
                                    textView.setVisibility(View.GONE);
                                } else textView.setVisibility(View.VISIBLE);
                            }
                            super.setValue(textView, value);
                        }
                    });

                }

                @Override
                public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                    if (onclickType == 0) {
                        sale_type_ppw.dismiss();
                        ADInfo adInfo = (ADInfo) bean;
                        switch (adInfo.getImageId()){
                            case "goods_type":
                                productCriteria = adInfo.getImageGoodsType();
                                tv_goods_standard.setText(adInfo.getImageName());
                                break;
                            case "nickname":
                                break;
                            case "spec":
                                tv_goods_species.setText(adInfo.getImageName());
                                varietyEntityId = adInfo.getEntity_id();
                                break;
                            case "sale_type":
                                int isPP = adInfo.getImageGoodsType();
                                //如果选择是当前售卖类型，则返回
                                if (isPP == isP) return true;
                                tv_sale_type.setText(adInfo.getImageName());
                                tv_rate_count.setText(adInfo.getImageContent());
                                isP = isPP;
                                //清除规格
                                if (MyselfShopManagement.goodsInformation != null && MyselfShopManagement.goodsInformation.getSpecs() != null)
                                    MyselfShopManagement.goodsInformation.getSpecs().clear();
                                if (tv_specififcation != null) tv_specififcation.setText("");
                                break;
                            default:
                                labelList.get(adInfo.getGoodsCount()).setContent(adInfo.getImageName());
                                JSONObject jsonObject = new JSONObject();
                                for (int i = 0; i < jsonArray.size(); i++) {
                                    JSONObject temp = jsonArray.getJSONObject(i);
                                    if (temp.getString("pName").equals(labelList.get(adInfo.getGoodsCount()).getPropertyZhName())) {
                                        jsonArray.remove(temp);
                                    }
                                }
                                jsonObject.put("pName", labelList.get(adInfo.getGoodsCount()).getPropertyZhName());
                                jsonObject.put("pValue", adInfo.getImageName());
                                jsonArray.add(jsonObject);
                                adapter.notifyDataSetChanged();
                                break;
                        }
                    }
                    return false;
                }
            };
            RecyclerView recyclerView = view.findViewById(R.id.rv_recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.addItemDecoration(new DividerItemDecoration(this));
            recyclerView.setAdapter(sale_type_adapter);
        }
    }

    public void specififcationUpdate() {
        if (MyselfShopManagement.goodsInformation == null || MyselfShopManagement.goodsInformation.getSpecs() == null || tv_specififcation == null)
            return;

        if (MyselfShopManagement.goodsInformation.getSpecs().size() > 0) {
            GoodsSpecification gs = MyselfShopManagement.goodsInformation.getSpecs().get(0);
            String str = gs.getPrice().toString() + "元/" + gs.getLevel1Unit();
            if (gs.getLevelType() == 2) {
                str += "(" + gs.getLevel2Value() + gs.getLevel2Unit() + ")";
            } else if (gs.getLevelType() == 3) {
                str += "(" + gs.getLevel2Value() + gs.getLevel2Unit() + "*" + gs.getLevel3Value() + gs.getLevel3Unit() + ")";
            }
            tv_specififcation.setText(str);
        } else tv_specififcation.setText("");
    }

    //AddedPicturesFragment 上传完成后回调的方法
    @Override
    public void uploadPicturesIsDone(Object obj) {
        /**发布商品按钮点击事件* */
        gallery = addedPicturesFragment.getImageStr();
        if (gallery.length() == 0) {
            UIUtils.showToastSafesShort("请最少上传一张图片");
            loadingDimss();
            return;
        }
        if (UIUtils.isNullOrZeroLenght(getC_image_url())){
            UIUtils.showToastSafesShort("请上传C端展示图");
            loadingDimss();
            return;
        }
        if (ll_goods_species.getVisibility()==View.VISIBLE) {
            if (varietyEntityId < 0 || (ListUtils.isNullOrZeroLenght(tv_goods_species.getText().toString().trim()) && (goodsEditState == 2 || goodsEditState == 0))) {
                UIUtils.showToastSafe("品种为必填！");
                loadingDimss();
                return;
            }
        }
        if (ll_goods_origin.getVisibility()==View.VISIBLE){
            if (ListUtils.isNullOrZeroLenght(tv_goods_origin.getText().toString().trim())){
                UIUtils.showToastSafe("产地为必填！");
                loadingDimss();
                return;
            }
        }


        map = new HashMap<>();
        map.put("gallery", gallery);
        map.put("name", tv_goods_name.getText().toString().trim());
            map.put("nickName", et_goods_feature.getText().toString().trim());
   /*     }else {
            map.put("nickName", tv_goods_feature.getText().toString().trim());
        }*/

        map.put("productCriteria", productCriteria);
        if (checkBox.isChecked()){
            map.put("isCanteen", 1);
        }else {
            map.put("isCanteen", 0);
        }
        LogUtils.e(checkBox.isChecked());
        if (isF == 1) {
            String packageName = et_cash_pledge_packageName.getText().toString().trim();
            if (packageName.equals("")) {
                loadingDimss();
                UIUtils.showToastSafesShort("请填写包装物质量与外形");
                return;
            } else {
                map.put("packageName", packageName);
            }

            String str = et_cash_pledge_foregift.getText().toString().trim();

            if (str.equals("")) {
                loadingDimss();
                UIUtils.showToastSafesShort("请填写单个包装物押金");
                return;
            } else {
                try {
                    BigDecimal foregift = new BigDecimal(str);
                    if (foregift.compareTo(BigDecimal.ZERO) > 0) {
                        map.put("foregift", foregift);
                    } else {
                        loadingDimss();
                        UIUtils.showToastSafesShort("请填写单个包装物押金");
                        return;
                    }
                } catch (Exception e) {
                    loadingDimss();
                    UIUtils.showToastSafesShort("包装物押金输入有误");
                    return;
                }
            }
        }

        Map<String, Object> diffent;
        //编辑
        if (goodsEditState > 0) {
            diffent = JavaMethod.getDiffrentMap(map, JavaMethod.transBean2Map(MyselfShopManagement.goodsInformation));
            if (diffent.size() == 0) {
                UIUtils.showToastSafesShort("请确认商品是否进行修改");
                loadingDimss();
                return;
            }
            map.clear();
            map.putAll(diffent);
        } else {
            if (!TextUtils.isEmpty(credentialsImage)) {
                map.put("credentialsImage", credentialsImage);
            }
        }

        if (MyselfShopManagement.goodsInformation == null || MyselfShopManagement.goodsInformation.getSpecs() == null || MyselfShopManagement.goodsInformation.getSpecs().isEmpty()) {
            UIUtils.showToastSafesShort("请添加商品规格");
            loadingDimss();
            return;
        }

        LogUtils.e(labelList);
        LogUtils.e(jsonArray);
        for (int i = 0;i < labelList.size();i++){
            int flag = 0;
            if (labelList.get(i).getRequired()==1){
                for (int j =0;j<jsonArray.size();j++){
                    JSONObject object = jsonArray.getJSONObject(j);
                    if (labelList.get(i).getPropertyZhName().equals(object.getString("pName"))){
                        flag++;
                    }
                }
                if (flag == 0){
                    loadingDimss();
                    UIUtils.showToastSafe(labelList.get(i).getPropertyZhName()+"为必填项！");
                    return;
                }
            }
        }


        map.put("isP", isP);
        map.put("isF", isF);

        //编辑商品
        if (goodsEditState > 0) {
            map.put("entityId", MyselfShopManagement.goodsInformation.getEntityId());
            map.put("store", MyselfShopManagement.goodsInformation.getStore());

            if (MyselfShopManagement.goodsInformation.getStatus() == 3 && MyselfShopManagement.goodsInformation.getAuthStatus() == 3)
                map.put("authStatus", 1);


            //重新编辑了商品
            if (goodsEditState == 2) {
                if (commodityId != 0 && commodityId != MyselfShopManagement.goodsInformation.getCommodityId()) {
                    map.put("name", name);
                    map.put("typeId", typeId);
                    map.put("commodityId", commodityId);
                    map.put("categoryId", categoryId);
                }
            }

            map.put("productProperty",jsonArray.toJSONString());
            LogUtils.e(varietyEntityId);
            map.put("varietyEntityId",varietyEntityId);
            map.put("customerExhibitionImage",getC_image_url());
            LogUtils.e(getC_image_url());
            LogUtils.e(map);
            getRequestPresenter().goodsUpdate(map, new RequestCallback<GoodsUpdate>(this) {
                @Override
                public void onSuc(GoodsUpdate body) {
                    loadingDimss();
                    if (gallery.length() > 0) {
                        map.put("gallery", Arrays.asList(gallery.split(",")));
                    }
                    JavaMethod.transMap2Bean(MyselfShopManagement.goodsInformation, map);

                    Intent intent1 = new Intent(getBaseActivity(), GoodsUploadDetailActivity.class);
                    intent1.putExtra("isUpdate", true);
                    intent1.putExtra("product_id", Integer.valueOf(map.get("entityId").toString()));
                    intent1.putExtra("goodsName", map.get("name") == null ? name : map.get("name").toString());
                    intent1.putExtra("goodsNickName", map.get("nickName") == null ? "" : map.get("nickName").toString());
                    startActivity(intent1);
                    finish();
                }

                @Override
                public void onFailed(int goodsUpdate, String msg) {
                    loadingDimss();
                    UIUtils.showToastSafesShort(msg);
                }
            });
        } else {
            if (PublicCache.loginSupplier == null) {
                loadingDimss();
                return;
            }


            map.put("typeId", typeId);
            map.put("commodityId", commodityId);
            map.put("categoryId", categoryId);
            map.put("store", PublicCache.loginSupplier.getStore());
            map.put("specs", JavaMethod.transBean2Json(MyselfShopManagement.goodsInformation.getSpecs()));
            map.put("productProperty",jsonArray.toJSONString());
            map.put("varietyEntityId",varietyEntityId);
            map.put("name",tv_goods_name.getText().toString().trim());
            map.put("customerExhibitionImage",getC_image_url());
            LogUtils.e(getC_image_url());
            getRequestPresenter().goodsCreate(map, new ResultInfoCallback<GoodsInformation>(this) {
                @Override
                public void onFailed(int goodsInformationResultInfo, String msg) {
                    loadingDimss();
                    UIUtils.showToastSafesShort(msg);
                }

                @Override
                public void onSuccess(GoodsInformation body) {
                    loadingDimss();

                    MyselfShopManagement.goodsInformation = body;

                    Intent intent1 = new Intent(getBaseActivity(), GoodsUploadDetailActivity.class);
                    intent1.putExtra("isUpdate", false);
                    intent1.putExtra("product_id", MyselfShopManagement.goodsInformation.getEntityId());
                    intent1.putExtra("goodsName", map.get("name") == null ? name : map.get("name").toString());
                    intent1.putExtra("goodsNickName", map.get("nickName") == null ? "" : map.get("nickName").toString());
                    startActivity(intent1);

                    goodsEditState = 2;
                    initData();
                }
            });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
            name = data.getStringExtra("name");
            categoryId = data.getIntExtra("categoryId", 0);
            typeId = data.getIntExtra("typeId", 0);
            commodityId = data.getIntExtra("commodityId", 0);
            tv_goods_name.setText(name);
            initData();
        } else if (requestCode == 200 && resultCode == RESULT_OK) {
            credentialsImage = data.getStringExtra("data");
        } else if (requestCode == REQUEST_CODE_CHOOSE_GRIDE && resultCode == RESULT_OK) {//storage/emulated/0/Pictures/JPEG_20181011_155709.jpg
            LogUtils.i(Matisse.obtainPathResult(data).get(0));
            Log.e("OnActivityResult ", String.valueOf(Matisse.obtainPathResult(data).get(0)));
//            imageUpload(Matisse.obtainPathResult(data).get(0));
        }
    }


    public void imageUpload(String path,Bitmap b){
        getRequestPresenter().imageUpload(path,BitmapUtil.bitmapTobyte(b, true), new Callback<ImageUpload>() {
            @Override
            public void onResponse(Call<ImageUpload> call, Response<ImageUpload> response) {
                if (response.body() == null) {
                    UIUtils.showToastSafesShort("图片太大了");
                    return;
                }
                if (response.body().getErr() != 0) {
                    UIUtils.showToastSafesShort("图片上传失败" + response.body().getMsg());
                }else {
                    ImageUpload body = response.body();
                    setC_image_url(body.getData());
//                    ImageLoaderUtils.loadImage(c_image,body.getData());
                    delete_image.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<ImageUpload> call, Throwable t) {
                UIUtils.showToastSafesShort("图片上传失败" + t.getMessage());
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        specififcationUpdate();
    }

    @Override
    protected void onDestroy() {
        MyselfShopManagement.goodsInformation = null;
        super.onDestroy();
    }


    @Override
    public void takeSuccess(TResult result) {
        String path = result.getImage().getCompressPath();
        if (TextUtils.isEmpty(path)) return;
        String imageName = UIUtils.getFileNames(path);
        bitmap = BitmapUtil.getSmallBitmap(path);
        if (bitmap == null) return;

        ImageLoaderUtils.loadImage(c_image,  path);
        imageUpload(imageName,bitmap);
    }

    @Override
    public void takeFail(TResult result, String msg) {

        UIUtils.showToastSafesShort(msg);
    }

    @Override
    public void takeCancel() {
    }

}
