package cn.com.taodaji.ui.activity.shopManagement;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apkfuns.logutils.LogUtils;
import com.base.glide.ImageLoaderUtils;
import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;
import com.base.utils.ADInfo;
import com.base.utils.DensityUtil;
import com.base.utils.JavaMethod;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.BaseVM;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.adapter.splite_line.DividerGridItemDecoration;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.AddCategory;
import cn.com.taodaji.model.entity.CommodityAliasVariety;
import cn.com.taodaji.model.entity.CommodityLabel;
import cn.com.taodaji.model.entity.FindProductDetail;
import cn.com.taodaji.model.entity.GoodsInformation;
import cn.com.taodaji.model.entity.GoodsSpecification;
import cn.com.taodaji.model.entity.GoodsUpdate;
import cn.com.taodaji.model.entity.ModifyInventory;
import cn.com.taodaji.model.entity.NameValue;
import cn.com.taodaji.model.entity.Property;
import cn.com.taodaji.model.entity.TakeUp;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.model.presenter.RequestPresenter2;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.ui.fragment.MyselfShopManagement;
import cn.com.taodaji.ui.ppw.SimpleButtonBottomPPW;
import cn.com.taodaji.viewModel.adapter.GoodsPropertiesAdapter;
import cn.com.taodaji.viewModel.adapter.NewGoodsPropertiesAdapter;
import cn.com.taodaji.viewModel.adapter.ReleaseGoodsImageAdapter;
import tools.activity.SimpleToolbarActivity;

public class ReleaseGoodsActivity extends SimpleToolbarActivity {
    private View mainView;
    private List<String> adInfos = new ArrayList<>();
    private List<String> strings = new ArrayList<>();
    private TextView tv_goods_species,tv_pice_type,tv_num_type;
    private ReleaseGoodsImageAdapter releaseGoodsImageAdapter,dreleaseGoodsImageAdapter;
    private List<ADInfo> goods_spec_list = new ArrayList<>();
    private SimpleButtonBottomPPW sale_type_ppw;
    private SingleRecyclerViewAdapter sale_type_adapter;
    private CommodityAliasVariety mbody;
    private ImageView c_image;
    private NewGoodsPropertiesAdapter adapter;
    private List<CommodityLabel.DataBean.ListBean> labelList = new ArrayList<>();
    private String gallery,c_image_data,name,dgallery;
    private int goodsEditState;
    private int isP = 0;//0零售，1事件批，-1全部
    private int productCriteria = 1;//  商品标准， 1：通货商品 2 ： 精品商品 '
    private int isF,varietyEntityId,commodityId,categoryId,typeId;//是否有押金0：无，1：有
    private Button bt_goods_create_ok;
    private CommodityAliasVariety.DataBean.VarietyListBean.SpecInfo mspecInfo;
    private EditText et_pice,et_num;
    private ModifyInventory modifyInventory = new ModifyInventory();
    private LinearLayout ll_goods_species;
    private int index;
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
    }

    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.activity_release_goods_list);
        body_scroll.addView(mainView);
        tv_goods_species=mainView.findViewById(R.id.tv_goods_species);
        ll_goods_species=mainView.findViewById(R.id.ll_goods_species);
        RecyclerView  rv_goods_property=mainView.findViewById(R.id.rv_goods_property);
        RecyclerView  d_list=mainView.findViewById(R.id.d_list);
        bt_goods_create_ok=mainView.findViewById(R.id.bt_goods_create_ok);
        et_pice=mainView.findViewById(R.id.et_pice);
        et_num=mainView.findViewById(R.id.et_num);
        tv_pice_type=mainView.findViewById(R.id.tv_pice_type);
        tv_num_type=mainView.findViewById(R.id.tv_num_type);
        c_image=mainView.findViewById(R.id.c_image);
        RecyclerView recyclerView = ViewUtils.findViewById(mainView, R.id.added_image_group);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.addItemDecoration(new DividerGridItemDecoration(DensityUtil.dp2px(5)));
        d_list.setLayoutManager(new GridLayoutManager(this, 4));
        d_list.addItemDecoration(new DividerGridItemDecoration(DensityUtil.dp2px(5)));
        goodsEditState = getIntent().getIntExtra("goodsEditState", 0);
        bt_goods_create_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ListUtils.isNullOrZeroLenght(et_pice.getText().toString().trim())){
                    UIUtils.showToastSafe("价格不能为空");
                    return;
                }
                if (ListUtils.isNullOrZeroLenght(et_num.getText().toString().trim())){
                    UIUtils.showToastSafe("库存不能为空");
                    return;
                }
                if (et_num.getText().toString().trim().length()>6){
                    UIUtils.showToastSafe("库存不能大于6位数");
                    return;
                }
                if (goodsEditState==0){
                    upload();
                }else {
                    setPice_stock();
                }

            }
        });

        releaseGoodsImageAdapter = new ReleaseGoodsImageAdapter(adInfos,this);
        recyclerView.setAdapter(releaseGoodsImageAdapter);

        dreleaseGoodsImageAdapter = new ReleaseGoodsImageAdapter(strings,this);
        d_list.setAdapter(dreleaseGoodsImageAdapter);
        if (goodsEditState==0){
            RequestPresenter.getInstance().getCommodityAliasVariety( getIntent().getIntExtra("commodityId", 0),1, new RequestCallback<CommodityAliasVariety>() {
                @Override
                protected void onSuc(CommodityAliasVariety body) {
                    if (body.getData().getVarietyList().get(0).getSpecInfo().getTemplateId()==0){
                        UIUtils.showToastSafe("三级分类未设置模板，请联系管理员");
                          finish();
                            return;
                    }
                    mbody=body;
                    for (CommodityAliasVariety.DataBean.VarietyListBean bean : body.getData().getVarietyList()){
                        ADInfo adInfo = new ADInfo();
                        adInfo.setImageName(bean.getVarietyName());
                        adInfo.setEntity_id(bean.getVarietyEntityId());
                        goods_spec_list.add(adInfo);
                    }


                    tv_goods_species.setText(body.getData().getVarietyList().get(0).getVarietyName());
                    setSpecInfo(body.getData().getVarietyList().get(0).getSpecInfo());
                    varietyEntityId=body.getData().getVarietyList().get(0).getVarietyEntityId();
                    getCommodityLabel(body.getData().getVarietyList().get(0).getVarietyEntityId());
                    initPPW();
                    sale_type_adapter.setList(goods_spec_list);
                }
                @Override
                public void onFailed(int errCode, String msg) {
                    super.onFailed(errCode, msg);
                    if (errCode==1){
                        UIUtils.showToastSafe(msg);
                        finish();
                        return;
                    }
                }
            });
        }


        tv_goods_species.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (goodsEditState==0){
                    if (sale_type_adapter != null && sale_type_ppw != null && !sale_type_ppw.isShowing()) {

                        sale_type_ppw.showAtLocation(ll_goods_species, Gravity.CENTER, 0, 0);
                    }
                }


            }
        });

        rv_goods_property.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewGoodsPropertiesAdapter(labelList,this);

        rv_goods_property.setAdapter(adapter);


        if (goodsEditState==0){

            name = getIntent().getStringExtra("name");
            setTitle( name);
            productCriteria = 1;//商品标准
            isP = 0;//售卖方式
            isF = 0;//是否收押金
            commodityId = getIntent().getIntExtra("commodityId", 0);
            categoryId =  getIntent().getIntExtra("categoryId", 0);
            typeId =  getIntent().getIntExtra("typeId", 0);
        }else {
            setTitle( MyselfShopManagement.goodsInformation.getName());
            getPice_stock();
            d_list();

            getCommodityLabel(getIntent().getIntExtra("varietyEntityId", 0));
            setTitle(MyselfShopManagement.goodsInformation.getVarietyName());
            tv_goods_species.setText(MyselfShopManagement.goodsInformation.getVarietyName());
            if (MyselfShopManagement.goodsInformation.getSpecs().get(0).getLevelType()==3){
                tv_pice_type.setText("元/"+MyselfShopManagement.goodsInformation.getSpecs().get(0).getLevel1Unit()+"("+MyselfShopManagement.goodsInformation.getSpecs().get(0).getLevel2Value() +
                        ""+ MyselfShopManagement.goodsInformation.getSpecs().get(0).getLevel2Unit()+"*"+MyselfShopManagement.goodsInformation.getSpecs().get(0).getLevel3Value()+  MyselfShopManagement.goodsInformation.getSpecs().get(0).getLevel3Unit()+ ""+")");

            }else {
                tv_pice_type.setText("元/"+MyselfShopManagement.goodsInformation.getSpecs().get(0).getLevel1Unit()+"("+MyselfShopManagement.goodsInformation.getSpecs().get(0).getLevel2Value()+MyselfShopManagement.goodsInformation.getSpecs().get(0).getLevel2Unit()+")");

            }
            if (MyselfShopManagement.goodsInformation.getSpecs().get(0).getLevelType()==1){
                tv_pice_type.setText("元/"+MyselfShopManagement.goodsInformation.getSpecs().get(0).getLevel1Unit());

            }
            tv_num_type.setText(MyselfShopManagement.goodsInformation.getSpecs().get(0).getLevel1Unit());
            ImageLoaderUtils.loadImage(c_image,MyselfShopManagement.goodsInformation.getCustomerExhibitionImage());
            adInfos.addAll(MyselfShopManagement.goodsInformation.getGallery());
            StringBuffer sb = new StringBuffer();
            for (int i=0;i<MyselfShopManagement.goodsInformation.getGallery().size();i++){
                sb.append(MyselfShopManagement.goodsInformation.getGallery().get(i)).append(",");
            }
            gallery= sb.toString().substring( 0 ,sb.toString().length()- 1 );
            c_image_data=MyselfShopManagement.goodsInformation.getCustomerExhibitionImage();
            releaseGoodsImageAdapter.notifyDataSetChanged();
//            dreleaseGoodsImageAdapter.notifyDataSetChanged();
        }

    }

    public void setSpecInfo(CommodityAliasVariety.DataBean.VarietyListBean.SpecInfo specInfo){
        LogUtils.e(specInfo);
        mspecInfo=specInfo;
        if (specInfo.getLevelType()==3){
            tv_pice_type.setText("元/"+specInfo.getLevel1Unit()+"("+specInfo.getLevel2Value() + ""+ specInfo.getLevel2Unit()+"*"+specInfo.getLevel3Value()+  specInfo.getLevel3Unit()+ ""+")");

        }else {
            tv_pice_type.setText("元/"+specInfo.getLevel1Unit()+"("+specInfo.getLevel2Value()+specInfo.getLevel2Unit()+")");

        }
        if (specInfo.getLevelType()==1){
            tv_pice_type.setText("元/"+specInfo.getLevel1Unit());

        }
        if (adInfos.size()>0){
            adInfos.clear();
        }
        if (strings.size()>0){
            strings.clear();
        }
        tv_num_type.setText(specInfo.getLevel1Unit());
        ImageLoaderUtils.loadImage(c_image,specInfo.getcImage());
        adInfos.addAll(Arrays.asList(specInfo.getbImage().split(",")));
        strings.addAll(Arrays.asList(specInfo.getdImage().split(",")));
        gallery=specInfo.getbImage();
        dgallery=specInfo.getdImage();
        c_image_data=specInfo.getcImage();
        LogUtils.e(strings);

        releaseGoodsImageAdapter.notifyDataSetChanged();
        dreleaseGoodsImageAdapter.notifyDataSetChanged();

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
                    if (index==1||index==2){
                        index=0;
                        return true;
                    }

                    if (mbody.getData().getVarietyList().get(position).getSpecInfo().getTemplateId()!=0){
                            setSpecInfo(mbody.getData().getVarietyList().get(position).getSpecInfo());
                            tv_goods_species.setText(mbody.getData().getVarietyList().get(position).getVarietyName());
                            setTitle(mbody.getData().getVarietyList().get(0).getVarietyName());
                            varietyEntityId=mbody.getData().getVarietyList().get(position).getVarietyEntityId();
                            getCommodityLabel(mbody.getData().getVarietyList().get(position).getVarietyEntityId());
                        index=2;
                        }else {
                        index=1;
                            UIUtils.showToastSafe(" 当前品种未设置模板，请选择其他品种！");

                        }
                    sale_type_ppw.dismiss();

                    return false;
                }
            };
            RecyclerView recyclerView = view.findViewById(R.id.rv_recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.addItemDecoration(new DividerItemDecoration(this));
            recyclerView.setAdapter(sale_type_adapter);

        }
    }

    public void getCommodityLabel(int varietyEntityId){
        RequestPresenter.getInstance().getCommodityLabel(getIntent().getIntExtra("commodityId", 0),varietyEntityId, new RequestCallback<CommodityLabel>() {
            @Override
            protected void onSuc(CommodityLabel body) {
                labelList.clear();
                for (CommodityLabel.DataBean.ListBean bean :body.getData().getList()){
                        labelList.add(bean);
                        if (bean.getDatas().size()>0){
                            bean.setContent(bean.getDatas().get(0).getDataValue());
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

    public void upload(){
        JSONArray jsonArray = new JSONArray();

        if (goodsEditState==0){
            for (int i=0;i<labelList.size();i++){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("pName", labelList.get(i).getPropertyZhName());
                jsonObject.put("pValue", ListUtils.isNullOrZeroLenght(labelList.get(i).getContent())?"":labelList.get(i).getContent());
                jsonArray.add(jsonObject);
            }
        }else {
            List<NameValue> parseArray_2= JSON.parseArray(MyselfShopManagement.goodsInformation.getProductProperty(), NameValue.class);
            if (parseArray_2!=null) {
                for (NameValue value : parseArray_2) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("pName", value.getPName());
                    jsonObject.put("pValue",  ListUtils.isNullOrZeroLenght(value.getPValue())?"":value.getPValue());
                    jsonArray.add(jsonObject);
                }
            }
        }






        HashMap  map = new HashMap<>();
        map.put("gallery", gallery);
        map.put("name", name);
        map.put("nickName", "");

        map.put("productCriteria", productCriteria);
        map.put("packageName", "");

        map.put("varietyEntityId",varietyEntityId);
        map.put("customerExhibitionImage",c_image_data);

        LogUtils.e(labelList);
        map.put("isCanteen", 0);
        map.put("isP", isP);
        map.put("isF", isF);
            if (PublicCache.loginSupplier == null) {
                loadingDimss();
                return;
            }
           List<GoodsSpecification> specs=new ArrayList<>();
        GoodsSpecification goodsSpecification=new GoodsSpecification();
        goodsSpecification.setPrice(new BigDecimal(et_pice.getText().toString().trim()));
        goodsSpecification.setStock(Integer.parseInt(et_num.getText().toString().trim()));
        goodsSpecification.setSalesNumber(Integer.parseInt(et_num.getText().toString().trim()));
        goodsSpecification.setLevel1Unit(mspecInfo.getLevel1Unit());
        goodsSpecification.setLevel2Unit(mspecInfo.getLevel2Unit());
        goodsSpecification.setLevel2Value(mspecInfo.getLevel2Value());
        goodsSpecification.setLevel3Unit(mspecInfo.getLevel3Unit());
        goodsSpecification.setLevel3Value(mspecInfo.getLevel3Value());
        goodsSpecification.setLevelType(mspecInfo.getLevelType());
        goodsSpecification.setSiteId(PublicCache.site);
        if (mspecInfo.getLevelType()==1){
            goodsSpecification.setAvgUnit(mspecInfo.getLevel1Unit());
            goodsSpecification.setAvgPrice(mspecInfo.getPrice());
        }else if (mspecInfo.getLevelType()==2){
            if (specification_unit_base.contains(mspecInfo.getLevel2Unit())) {
                goodsSpecification.setAvgPrice(goodsSpecification.getPrice().divide(mspecInfo.getLevel2Value(), 2, BigDecimal.ROUND_HALF_UP));
                goodsSpecification.setAvgUnit(mspecInfo.getLevel2Unit());
            } else {
                goodsSpecification.setAvgUnit(mspecInfo.getLevel1Unit());
                goodsSpecification.setAvgPrice(goodsSpecification.getPrice());
            }
        }else {
            if (specification_unit_base.contains(mspecInfo.getLevel3Unit())) {
                goodsSpecification.setAvgPrice(goodsSpecification.getPrice().divide(mspecInfo.getLevel2Value(), 2, BigDecimal.ROUND_HALF_UP).divide(mspecInfo.getLevel3Value(), 2, BigDecimal.ROUND_HALF_UP));
                goodsSpecification.setAvgUnit(mspecInfo.getLevel3Unit());
            } else {
                goodsSpecification.setAvgPrice(goodsSpecification.getPrice().divide(mspecInfo.getLevel2Value(), 2, BigDecimal.ROUND_HALF_UP));
                goodsSpecification.setAvgUnit(mspecInfo.getLevel2Unit());
            }
        }


        specs.add(goodsSpecification);


            map.put("typeId", typeId);
            map.put("commodityId", commodityId);
            map.put("categoryId", categoryId);
            map.put("store", PublicCache.loginSupplier.getStore());
            map.put("specs", JavaMethod.transBean2Json(specs));
            map.put("productProperty",jsonArray.toJSONString());
                LogUtils.e( JavaMethod.transBean2Json(specs));
        LogUtils.e(jsonArray.toJSONString());
            getRequestPresenter().goodsCreate(map, new ResultInfoCallback<GoodsInformation>(this) {
                @Override
                public void onFailed(int goodsInformationResultInfo, String msg) {
                    UIUtils.showToastSafesShort(msg);
                }

                @Override
                public void onSuccess(GoodsInformation body) {
                    save_d();


                }
            });
    }

    public void setPice_stock(){
        RequestPresenter.getInstance().modifyInventory(getIntent().getIntExtra("id", 0), new BigDecimal(et_pice.getText().toString().trim())
                ,  Integer.valueOf(et_num.getText().toString().trim()),new RequestCallback<AddCategory>() {
            @Override
            protected void onSuc(AddCategory body) {
                UIUtils.showToastSafe("商品编辑成功");
//                EventBus.getDefault().post(new TakeUp());
                EventBus.getDefault().post(new GoodsInformation());
               finish();
            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafe(msg);
            }
        });
    }
    public void getPice_stock(){
        RequestPresenter2.getInstance().getSpecificationIdByEntityId(getIntent().getIntExtra("id", 0), new RequestCallback<ModifyInventory>() {
            @Override
            protected void onSuc(ModifyInventory body) {
                if (body.getErr()==0) {
                    modifyInventory = body;
                    et_num.setText(body.getData().getStock() + "");
                    et_pice.setText(body.getData().getPrice()+"");
                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafe(msg);
            }
        });
    }

    public void d_list(){
        getRequestPresenter().product_findProductDetail(getIntent().getIntExtra("id", 0), new RequestCallback<FindProductDetail>(this) {
            @Override
            protected void onSuc(FindProductDetail body) {
                StringBuffer sb = new StringBuffer();
                for (int i=0;i<body.getData().size();i++){
                    strings.add(body.getData().get(i).getDescription());
                    sb.append(body.getData().get(i).getDescription()).append(",");
                }
                dgallery= sb.toString().substring( 0 ,sb.toString().length()- 1 );
                dreleaseGoodsImageAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailed(int findProductDetail, String msg) {
                UIUtils.showToastSafesShort(msg);
            }
        });

    }

    public void save_d(){
        loading();
        getRequestPresenter().product_saveDetail(getIntent().getIntExtra("id", 0), dgallery, new ResultInfoCallback() {
            @Override
            public void onSuccess(Object body) {
                loadingDimss();
                    UIUtils.showToastSafesShort("商品发布成功");
                    startActivity(new Intent(ReleaseGoodsActivity.this, ReleaseGoodsHelpActivity.class));
                EventBus.getDefault().post(new GoodsInformation());
                finish();
            }

            @Override
            public void onFailed(int o, String msg) {
                UIUtils.showToastSafesShort(msg);
                loadingDimss();
            }
        });
    }


}
