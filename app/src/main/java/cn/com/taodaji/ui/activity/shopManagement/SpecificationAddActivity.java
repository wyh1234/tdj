package cn.com.taodaji.ui.activity.shopManagement;


import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.OnItemClickListener;
import com.base.utils.ADInfo;
import com.base.utils.JavaMethod;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.adapter.SimplePPWAdapter;
import cn.com.taodaji.model.entity.GoodsSpecification;

import com.base.retrofit.ResultInfoCallback;

import cn.com.taodaji.ui.fragment.MyselfShopManagement;
import cn.com.taodaji.ui.ppw.RecyclerViewPPW;
import cn.com.taodaji.viewModel.vm.SpecificationVM;
import tools.activity.SimpleToolbarActivity;

/**
 * Created by Administrator on 2018/2/26.
 * 添加规格
 */

public class SpecificationAddActivity extends SimpleToolbarActivity implements View.OnClickListener, OnItemClickListener {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("请选择商品出售的规格");
    }

    private View mainView;
    private TextView level1Unit, tv_inventory, level2Unit, tv_ll_text, level3Unit;
    private View im_pop, im_popup, im_popupwindow;
    private Button bt_cancle;
    private Button bt_add;
    private LinearLayout ll2, ll_lv3;


    private GoodsSpecification lastSpecification;//最后一个规格
    private BaseViewHolder viewHolder;
    private boolean isEdit, isReplace;
    // private ItemsBean goodsInformation;


    private RecyclerViewPPW ppw_listView;


    private List<ADInfo> listUnit1 = new ArrayList<>();//显示的列表数据
    private List<ADInfo> listUnit2 = new ArrayList<>();
    private List<ADInfo> listUnit3 = new ArrayList<>();


    private Map<String, Integer> mapMini, mapMax;//规格的集合

    private SimplePPWAdapter adapter;

    private int levelType;//第几个规格

    //             最小   二级  最大
    private String unit1, unit2, unit3;

    private GoodsSpecification specification;//需要更新的规格

    private int specId;
    private boolean isNeedReturn = false;//是否需要回退
    private GoodsSpecification needSpecification;//需要缓存的规格

    private int isP;

    @Override
    protected void initMainView() {

        if (SpecificationActivity.baseUnit == null || SpecificationActivity.level1Unit == null)
            return;

        mainView = ViewUtils.getLayoutView(this, R.layout.activity_add_specification);
        body_scroll.addView(mainView);

        ll2 = ViewUtils.findViewById(mainView, R.id.ll2);
        ll_lv3 = ViewUtils.findViewById(mainView, R.id.ll_lv3);

        im_popupwindow = ViewUtils.findViewById(mainView, R.id.im_popupwindow);
        im_pop = ViewUtils.findViewById(mainView, R.id.im_pop);
        im_popup = ViewUtils.findViewById(mainView, R.id.im_popup);


        tv_inventory = ViewUtils.findViewById(mainView, R.id.tv_inventory);

        level1Unit = ViewUtils.findViewById(mainView, R.id.level1Unit);
        level2Unit = ViewUtils.findViewById(mainView, R.id.level2Unit);
        tv_ll_text = ViewUtils.findViewById(mainView, R.id.tv_ll_text);
        level3Unit = ViewUtils.findViewById(mainView, R.id.level3Unit);


        bt_cancle = ViewUtils.findViewById(mainView, R.id.bt_cancle);
        bt_cancle.setOnClickListener(this);
        bt_add = ViewUtils.findViewById(mainView, R.id.bt_add);
        bt_add.setOnClickListener(this);


        isEdit = getIntent().getBooleanExtra("isEdit", false);
        isReplace = getIntent().getBooleanExtra("replace", false);
        specification = (GoodsSpecification) getIntent().getSerializableExtra("GoodsSpecification");
        isP = getIntent().getIntExtra("isP", 0);
        viewHolder = new BaseViewHolder(mainView, new SpecificationVM(), null);


        //如查是更新替换，记录specId,specification置空，走第一次添加规格流程
        if (isEdit) {

            if (isReplace) {
                needSpecification = specification;
                specId = specification.getSpecId();
                specification = null;
                isNeedReturn = true;
                if (MyselfShopManagement.goodsInformation.getSpecs() != null)
                    MyselfShopManagement.goodsInformation.getSpecs().clear();
            }
            //编缉时不能改规格
            else {
                viewHolder.findViewById(R.id.level2Value).setEnabled(false);
                viewHolder.findViewById(R.id.level3Value).setEnabled(false);
            }

        }


        mapMini = new LinkedHashMap<>();
        mapMax = new LinkedHashMap<>();

        for (String s : SpecificationActivity.baseUnit) {
            mapMini.put(s, 1);
        }
        for (String s : SpecificationActivity.level1Unit) {
            mapMax.put(s, 2);
        }
        //去掉重复的
        for (String s : mapMini.keySet()) {
            if (mapMax.get(s) != null) {
                mapMax.remove(s);
            }
        }
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isNeedReturn) {
            isNeedReturn = false;
            specification = needSpecification;
            if (MyselfShopManagement.goodsInformation == null) return;
            if (MyselfShopManagement.goodsInformation.getSpecs() == null) {
                MyselfShopManagement.goodsInformation.setSpecs(new ArrayList());
            }
            MyselfShopManagement.goodsInformation.getSpecs().add(needSpecification);
        }

    }

    //规格初始化
    private void init() {
        listUnit1.clear();
        if (MyselfShopManagement.goodsInformation == null) return;
        //第一次添加规格,默认的显示
        if (MyselfShopManagement.goodsInformation.getSpecs() == null || MyselfShopManagement.goodsInformation.getSpecs().isEmpty()) {
            //第一个规格
            for (String s : SpecificationActivity.level1Unit) {
                ADInfo adInfo = new ADInfo();
                adInfo.setImageName(s);
                listUnit1.add(adInfo);
            }
        } else {
            lastSpecification = getSpecification();
            if (lastSpecification == null) return;

            switch (lastSpecification.getLevelType()) {

                case 1:  //如果已发布的是最小的规格，那么本次第一级为lastSpecification.getLevel1Unit()+ mapMax
                    unit1 = lastSpecification.getLevel1Unit();
                    ADInfo adIn = new ADInfo();
                    adIn.setImageName(unit1);
                    listUnit1.add(adIn);


                    for (String s : mapMax.keySet()) {
                        ADInfo adInfo = new ADInfo();
                        adInfo.setImageName(s);
                        listUnit1.add(adInfo);
                    }
                    break;
                case 2://如果发布的二级规格，   如果unit1和均价单位不为同一个，那么第一级为mapMax，第二级为unit1
                    //否则 lastSpecification.getLevel2Unit()+ mapMax
                    unit1 = lastSpecification.getLevel2Unit();
                    unit2 = lastSpecification.getLevel1Unit();

                    if (unit1.equals(lastSpecification.getAvgUnit())) {

                        ADInfo adIn_11 = new ADInfo();
                        adIn_11.setImageName(unit1);
                        listUnit1.add(adIn_11);


                        for (String s : mapMax.keySet()) {
                            ADInfo adInfo = new ADInfo();
                            adInfo.setImageName(s);
                            listUnit1.add(adInfo);
                        }

                    } else {
                        ll2.setVisibility(View.VISIBLE);
                        tv_ll_text.setText(unit2);
                        level2Unit.setText(unit1);
                        for (String s : mapMax.keySet()) {
                            ADInfo adInfo = new ADInfo();
                            adInfo.setImageName(s);
                            listUnit1.add(adInfo);
                        }
                    }


                    break;
                case 3://如果上次发布的是三级规格，那么本次第一级只能为上次发布的三个单位，包含一二三级
                    // 如果unit1和均价单位不为同一个 那么第一级不能为最小单位，必须有第二级  第二级为unit1

                    unit1 = lastSpecification.getLevel3Unit();
                    unit2 = lastSpecification.getLevel2Unit();
                    unit3 = lastSpecification.getLevel1Unit();


                    if (unit1.equals(lastSpecification.getAvgUnit())) {
                        ADInfo adInfo1 = new ADInfo();
                        adInfo1.setImageName(unit1);
                        listUnit1.add(adInfo1);
                    } else {
                        ll2.setVisibility(View.VISIBLE);
                        tv_ll_text.setText(unit2);
                        level2Unit.setText(unit1);
                    }


                    ADInfo adInfo2 = new ADInfo();
                    adInfo2.setImageName(unit2);
                    listUnit1.add(adInfo2);

                    ADInfo adInfo3 = new ADInfo();
                    adInfo3.setImageName(unit3);
                    listUnit1.add(adInfo3);
                    break;
            }

            //默认显示的是均价单位
            level1Unit.setText(lastSpecification.getAvgUnit());
            tv_inventory.setText(lastSpecification.getAvgUnit());
        }


        if (specification != null) {
            if (specification.getLevelType() == 2) {
                ll2.setVisibility(View.VISIBLE);
            } else if (specification.getLevelType() == 3) {
                ll2.setVisibility(View.VISIBLE);
                ll_lv3.setVisibility(View.VISIBLE);
            }
            tv_inventory.setText(specification.getLevel1Unit());
            viewHolder.setValues(specification);
        } else {
            im_popupwindow.setOnClickListener(this);
            im_pop.setOnClickListener(this);
            im_popup.setOnClickListener(this);
        }


    }


    private GoodsSpecification getSpecification() {
        if (MyselfShopManagement.goodsInformation.getSpecs() == null) return null;
        GoodsSpecification tmp = null;
        for (GoodsSpecification goodsSpecification : MyselfShopManagement.goodsInformation.getSpecs()) {
            if (tmp == null) {
                tmp = goodsSpecification;
            } else {
                if (tmp.getLevelType() < goodsSpecification.getLevelType()) {
                    tmp = goodsSpecification;
                }
            }
        }
        return tmp;

    }


    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object bean) {
        if (onclickType == 0) {
            ppw_listView.dismiss();
            ADInfo adInfo = (ADInfo) bean;
            Object in = mapMini.get(adInfo.getImageName());
            if (MyselfShopManagement.goodsInformation == null) return true;
            //如果是首次发布
            if (MyselfShopManagement.goodsInformation.getSpecs() == null || MyselfShopManagement.goodsInformation.getSpecs().isEmpty()) {
                switch (levelType) {
                    case 1:
                        //如果是最小单位
                        if (in != null) {
                            ll2.setVisibility(View.GONE);
                        }
                        //如果不是最小单位，那么第二级的选择范围是所有的mapmini,加上mapMax(去掉本次选择的规格)
                        else {
                            ll2.setVisibility(View.VISIBLE);
                            listUnit2.clear();
                            for (String s : mapMini.keySet()) {
                                ADInfo adInfo1 = new ADInfo();
                                adInfo1.setImageName(s);
                                listUnit2.add(adInfo1);
                            }
                            for (String s : mapMax.keySet()) {
                                if (!s.equals(adInfo.getImageName())) {
                                    ADInfo adInfo1 = new ADInfo();
                                    adInfo1.setImageName(s);
                                    listUnit2.add(adInfo1);
                                }
                            }

                            tv_ll_text.setText(adInfo.getImageName());
                            level2Unit.setText(listUnit2.get(0).getImageName());
                        }
                        ll_lv3.setVisibility(View.GONE);
                        level1Unit.setText(adInfo.getImageName());
                        tv_inventory.setText(adInfo.getImageName());
                        break;
                    case 2:
                        //如果是最小单位
                        if (in != null) {
                            ll_lv3.setVisibility(View.GONE);
                        }
                        //如果二级选择的不是最小单位，那么第三级只能在最小单位中选取
                        else {
                            listUnit3.clear();
                            for (String s : mapMini.keySet()) {
                                ADInfo adInfo1 = new ADInfo();
                                adInfo1.setImageName(s);
                                listUnit3.add(adInfo1);
                            }
                            ll_lv3.setVisibility(View.VISIBLE);
                            level3Unit.setText(listUnit3.get(0).getImageName());
                        }
                        level2Unit.setText(adInfo.getImageName());
                        break;
                    case 3:
                        level3Unit.setText(adInfo.getImageName());
                        break;
                }
            }
            //如果不是首次发布
            else {
                switch (levelType) {
                    case 1:
                        //如果是最小单位
                        if (in != null) {
                            ll2.setVisibility(View.GONE);
                            ll_lv3.setVisibility(View.GONE);
                        }
                        //如果不是最小单位，
                        else {

                            ll2.setVisibility(View.VISIBLE);

                            //如果上次选择的是一级规格，那么和第一次添加规格是一样的，第二级的选择范围是listUnit1(去掉本次选择的规格)
                            listUnit2.clear();

                            if (lastSpecification == null) return true;

                            if (lastSpecification.getLevelType() == 1) {
                                for (ADInfo info : listUnit1) {
                                    if (!info.getImageName().equals(adInfo.getImageName())) {
                                        listUnit2.add(info);
                                    }
                                }

                                ll_lv3.setVisibility(View.GONE);
                            }
                            //如果上次选择的是二级规格，
                            else if (lastSpecification.getLevelType() == 2) {
                                //1，本次选择的和上次的第二级相同，第二级只能是最小单位 unit1
                                if (adInfo.getImageName().equals(unit2)) {
                                    ADInfo adInfo1 = new ADInfo();
                                    adInfo1.setImageName(unit1);
                                    listUnit2.add(adInfo1);

                                    ll_lv3.setVisibility(View.GONE);
                                }
                                //2, 本次选择的和上次的第二级不同，第二级只能是上次的二级单位  unit2，且本次必须是三级规格
                                else {
                                    ADInfo adInfo2 = new ADInfo();
                                    adInfo2.setImageName(unit2);
                                    listUnit2.add(adInfo2);

                                    ll_lv3.setVisibility(View.VISIBLE);
                                    level3Unit.setText(unit1);
                                }
                            }
                            //如果上次选择的是三级规格
                            else {
                                //1，本次选择的和上次的第三级相同，第二级只能是上次的选择的二级规格，unit2，且本次必须是三级规格
                                if (adInfo.getImageName().equals(unit3)) {
                                    ADInfo adInfo2 = new ADInfo();
                                    adInfo2.setImageName(unit2);
                                    listUnit2.add(adInfo2);

                                    ll_lv3.setVisibility(View.VISIBLE);
                                    level3Unit.setText(unit1);
                                }
                                // 2，本次选择的和上次的第二级相同，第二级只能是最小单位 unit1
                                else if (adInfo.getImageName().equals(unit2)) {
                                    ADInfo adInfo1 = new ADInfo();
                                    adInfo1.setImageName(unit1);
                                    listUnit2.add(adInfo1);

                                    ll_lv3.setVisibility(View.GONE);
                                }

                            }

                            tv_ll_text.setText(adInfo.getImageName());
                            level2Unit.setText(listUnit2.get(0).getImageName());

                        }
                        level1Unit.setText(adInfo.getImageName());
                        tv_inventory.setText(adInfo.getImageName());
                        break;
                    case 2:
                        //如果是最小单位
                        if (in != null) {
                            ll_lv3.setVisibility(View.GONE);
                        }
                        //如果不是最小单位，那么第三级必须为最小单位，且已确定，只能为unit1
                        else {
                            listUnit3.clear();
                            ADInfo adInfo1 = new ADInfo();
                            adInfo1.setImageName(unit1);
                            listUnit3.add(adInfo1);

                            ll_lv3.setVisibility(View.VISIBLE);
                            level3Unit.setText(unit1);
                        }
                        level2Unit.setText(adInfo.getImageName());
                        break;
                    case 3:
                        level3Unit.setText(adInfo.getImageName());
                        break;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {

        if (adapter == null) {
            adapter = new SimplePPWAdapter();
            adapter.setItemClickListener(this);
            ppw_listView = new RecyclerViewPPW(this, v, adapter);
        }

        switch (v.getId()) {
            case R.id.bt_cancle:
                finish();
                break;

            case R.id.bt_add:
                if (MyselfShopManagement.goodsInformation == null) return;

                Map<String, Object> map = viewHolder.getViewValues();
                final GoodsSpecification specification = new GoodsSpecification();

                JavaMethod.transMap2Bean(specification, map);


                if (specification.getStock() == 0) {
                    UIUtils.showToastSafesShort("请输入库存,库存为整数!");
                    return;
                }

                if (specification.getPrice().compareTo(BigDecimal.ZERO) == 0) {
                    UIUtils.showToastSafesShort("请输入商品价格");
                    return;
                }


                //一级规格
                if (viewHolder.findViewById(R.id.ll2).getVisibility() == View.GONE) {
                    specification.setLevelType(1);
                    specification.setLevel2Value(BigDecimal.ZERO);
                    specification.setLevel3Value(BigDecimal.ZERO);
                    specification.setLevel2Unit("");
                    specification.setLevel3Unit("");

                    specification.setAvgUnit(specification.getLevel1Unit());
                    specification.setAvgPrice(specification.getPrice());
                } else if (specification.getLevel2Value().compareTo(BigDecimal.ZERO) == 0) {
                    UIUtils.showToastSafesShort("请选择二级规格");
                    return;
                }

                //二级规格
                else if (viewHolder.findViewById(R.id.ll_lv3).getVisibility() == View.GONE) {
                    specification.setLevelType(2);
                    specification.setLevel3Value(BigDecimal.ZERO);
                    specification.setLevel3Unit("");

                    if (specification_unit_base.contains(specification.getLevel2Unit())) {
                        specification.setAvgPrice(specification.getPrice().divide(specification.getLevel2Value(), 2, BigDecimal.ROUND_HALF_UP));
                        specification.setAvgUnit(specification.getLevel2Unit());
                    } else {
                        specification.setAvgUnit(specification.getLevel1Unit());
                        specification.setAvgPrice(specification.getPrice());
                    }

                } else if (specification.getLevel3Value().compareTo(BigDecimal.ZERO) == 0) {
                    UIUtils.showToastSafesShort("请选择三级规格");
                    return;
                }
                //三级规格
                else {
                    specification.setLevelType(3);
                    if (specification_unit_base.contains(specification.getLevel3Unit())) {
                        specification.setAvgPrice(specification.getPrice().divide(specification.getLevel2Value(), 2, BigDecimal.ROUND_HALF_UP).divide(specification.getLevel3Value(), 2, BigDecimal.ROUND_HALF_UP));
                        specification.setAvgUnit(specification.getLevel3Unit());
                    } else {
                        specification.setAvgPrice(specification.getPrice().divide(specification.getLevel2Value(), 2, BigDecimal.ROUND_HALF_UP));
                        specification.setAvgUnit(specification.getLevel2Unit());
                    }
                }


                if (specification.getAvgPrice().compareTo(BigDecimal.valueOf(0.01)) < 0) {
                    UIUtils.showToastSafesShort("规格均价太低，请检查是否输入有误？");
                    return;
                }

                if (isP == 1 && specification.getLevelType() == 1) {
                    UIUtils.showToastSafesShort("整件批规格，不能是一级规格");
                    return;
                }


                if (isEdit) {
                    specification.setProductId(MyselfShopManagement.goodsInformation.getEntityId());
                    Map<String, Object> map1 = JavaMethod.transBean2Map(specification);
                    map1.remove("specId");
                    map1.remove("text");
                    loading();

                    //编辑商品的添加规格
                    if (this.specification == null && !isReplace) {
                        // Log.d("addSpecSave", JavaMethod.transMap2Json(map1));
                        getRequestPresenter().saveSpecification(map1, new ResultInfoCallback() {
                            @Override
                            public void onSuccess(Object body) {
                                try {
                                    int id = new BigDecimal(body.toString()).intValue();
                                    specification.setSpecId(id);
                                    Log.e("saveSpecId", id + "");
                                    UIUtils.showToastSafesShort("规格添加成功");
                                    loadingDimss();
                                    Intent intent = new Intent();
                                    intent.putExtra("data", specification);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                } catch (NumberFormatException e) {
                                    UIUtils.showToastSafesShort(String.valueOf(body));
                                }

                            }

                            @Override
                            public void onFailed(int o, String msg) {
                                loadingDimss();
                                UIUtils.showToastSafesShort(msg);
                            }
                        });
                    } else {
                        //编辑商品时的替换规格
                        if (this.specification == null) {
                            map1.put("entityId", specId);
                            specification.setSpecId(specId);
                            specification.setProductId(MyselfShopManagement.goodsInformation.getEntityId());
                        }
                        //编辑商品时的编辑规格
                        else {
                            map1.put("entityId", this.specification.getSpecId());
                            specification.setSpecId(this.specification.getSpecId());
                            specification.setProductId(this.specification.getProductId());
                        }

                        //  Log.d("addSpecUpdate", JavaMethod.transMap2Json(map1));
                        getRequestPresenter().update(map1, new ResultInfoCallback() {
                            @Override
                            public void onSuccess(Object body) {
                                loadingDimss();

                                if (SpecificationAddActivity.this.specification == null)
                                    isNeedReturn = false;

                                UIUtils.showToastSafesShort("规格修改成功");
                                Intent intent = new Intent();
                                intent.putExtra("data", specification);
                                setResult(RESULT_OK, intent);
                                finish();
                            }

                            @Override
                            public void onFailed(int o, String msg) {
                                loadingDimss();
                                UIUtils.showToastSafesShort(msg);
                            }
                        });
                    }
                }


                //发布商品的添加规格,和编辑规格
                else {
                    if (this.specification != null) {
                        specification.setSpecId(this.specification.getSpecId());
                        specification.setProductId(this.specification.getProductId());
                    }
                    Intent intent = new Intent();
                    intent.putExtra("data", specification);
                    setResult(RESULT_OK, intent);
                    finish();
                }

                break;

            case R.id.im_popupwindow://第一个规格
                levelType = 1;
                if (!ppw_listView.isShowing()) {
                    adapter.notifyDataSetChanged(listUnit1);
                    ppw_listView.showAsDropDown(v);
                }
                break;
            case R.id.im_pop://第二个规格
                levelType = 2;
                if (!ppw_listView.isShowing()) {
                    adapter.notifyDataSetChanged(listUnit2);
                    ppw_listView.showAsDropDown(v);
                }
                break;
            case R.id.im_popup://第三个规格
                levelType = 3;
                if (!ppw_listView.isShowing()) {
                    adapter.notifyDataSetChanged(listUnit3);
                    ppw_listView.showAsDropDown(v);
                }
                break;

        }
    }
}


