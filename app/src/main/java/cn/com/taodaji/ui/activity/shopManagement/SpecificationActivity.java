package cn.com.taodaji.ui.activity.shopManagement;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.base.viewModel.adapter.OnItemClickListener;
import com.base.utils.JavaMethod;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.viewModel.adapter.SpecificationListAdapter;
import cn.com.taodaji.model.entity.FindProductCommission;
import cn.com.taodaji.model.entity.GetUnitList;
import cn.com.taodaji.model.entity.GoodsInformation;
import cn.com.taodaji.model.entity.GoodsSpecification;

import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;

import cn.com.taodaji.ui.fragment.MyselfShopManagement;
import tools.activity.SimpleToolbarActivity;

/**
 * Created by Administrator on 2018/2/26.
 * 发布产品规格
 */

public class SpecificationActivity extends SimpleToolbarActivity implements View.OnClickListener, OnItemClickListener {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("以什么价格出售？");
    }

    private View mainView;
    private TextView produceName;
    private SpecificationListAdapter specificationListAdapter;
    private boolean isUpdate;//是编辑还是发布
    private boolean goodsNameEdit;//是重新编辑？
    public static List<String> baseUnit, level1Unit;
    private AlertDialog alertDialog;
    private TextView title;
    private TextView tv_detailed;
    private TextView tv_sale_type;
    private int position_click;
    private boolean isClose;//是否关闭

    private TextView tv_whole_show, tv_whole_value, tv_whole_detailed;
    private int isP = 0;//0零售，1事件批，-1全部
    private View footerView;

    @Override
    protected void initMainView() {

        mainView = ViewUtils.getLayoutViewMatch(this, R.layout.activity_specification);
        body_other.addView(mainView);

        produceName = ViewUtils.findViewById(mainView, R.id.produceName);
        tv_detailed = ViewUtils.findViewById(mainView, R.id.tv_detailed);
        tv_detailed.setOnClickListener(this);

        RecyclerView recyclerView = ViewUtils.findViewById(mainView, R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));

        specificationListAdapter = new SpecificationListAdapter();
        specificationListAdapter.setItemClickListener(this);
        recyclerView.setAdapter(specificationListAdapter);

        footerView = ViewUtils.getFragmentView(recyclerView, R.layout.item_shop_management_footer_view);
        TextView bt_ok = ViewUtils.findViewById(footerView, R.id.bt_ok);
        bt_ok.setText("添加规格");
        bt_ok.setOnClickListener(this);


        //文字弹窗
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = ViewUtils.getLayoutView(this, R.layout.popup_window_simple_intput);
        ViewUtils.findViewById(view, R.id.et_input).setVisibility(View.GONE);
        title = ViewUtils.findViewById(view, R.id.title);
        title.setText("是否确认删除该规格");
        title.setPadding(0, 100, 0, 100);
        builder.setView(view);
        alertDialog = builder.create();
        ViewUtils.findViewById(view, R.id.no).setOnClickListener(this);
        ViewUtils.findViewById(view, R.id.yes).setOnClickListener(this);


        tv_whole_show = mainView.findViewById(R.id.tv_whole_show);
        tv_whole_value = mainView.findViewById(R.id.tv_whole_value);
        tv_whole_detailed = mainView.findViewById(R.id.tv_whole_detailed);

        tv_sale_type = mainView.findViewById(R.id.tv_sale_type);

        tv_whole_detailed.setOnClickListener(v -> startActivity(new Intent(getBaseActivity(), WholesaleExplainActivity.class)));

        isP = getIntent().getIntExtra("isP", 0);
        goodsNameEdit = getIntent().getBooleanExtra("goodsNameEdit", false);
        isUpdate = getIntent().getBooleanExtra("isEdit", false);

        if (isP == 0) {
            tv_whole_detailed.setVisibility(View.GONE);
        }
        FindProductCommission body = (FindProductCommission) getIntent().getSerializableExtra("data");
        if (body != null) {
            if (isP == 0) {
                tv_sale_type.setText("最多发布1个零售规格，");
                tv_whole_show.setText(body.getRetailCommission());

                String test = "参考规格一、" + "(  )" + getTypeResult(body.getRetailLevel1());
                if (!TextUtils.isEmpty(body.getRetailLevel2())) {
                    test += "\n参考规格二、" + "(  )" + getTypeResult(body.getRetailLevel2());
                }
                tv_whole_value.setText(test);
            } else {
                tv_sale_type.setText("最多发布1个整件批规格，");
                tv_whole_show.setText(body.getWholesaleCommission());
                String test = "参考规格一、" + "(  )" + getTypeResult(body.getWholesaleLevel1());
                if (!TextUtils.isEmpty(body.getWholesaleLevel2())) {
                    test += "\n参考规格二、" + "(  )" + getTypeResult(body.getWholesaleLevel2());
                }
                tv_whole_value.setText(test);
            }
        }

    }

    private String getTypeResult(String arg0) {
        String result = "";
        if (!TextUtils.isEmpty(arg0)) {
            int index = arg0.indexOf("元");
            result = arg0.substring(index);
        }
        return result;
    }

    @Override
    protected void initData() {

        if (MyselfShopManagement.goodsInformation == null) return;

        produceName.setText(MyselfShopManagement.goodsInformation.getName());

        if (MyselfShopManagement.goodsInformation.getSpecs() != null) {
            specificationListAdapter.setList(MyselfShopManagement.goodsInformation.getSpecs());
            if (MyselfShopManagement.goodsInformation.getSpecs().size() >= 1) {
                specificationListAdapter.removeFooter(footerView);
            } else specificationListAdapter.addFooterView(footerView);
        } else specificationListAdapter.addFooterView(footerView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }


    //刷新规格数据
    private void refreshData() {
//        if (isUpdate) loading();
//        if (isClose) isClose = false;
        if (baseUnit == null || level1Unit == null) {
            getData();
        }
//        else isClose = true;

        if (MyselfShopManagement.goodsInformation != null && isUpdate) {
            onStartLoading();
            loadingClear();
            int userType = 0;
            int personId = 0;

            if (PublicCache.loginPurchase != null) {
                if (PublicCache.loginPurchase.getFlag() == 1)
                    personId = PublicCache.loginPurchase.getEntityId();
                else personId = PublicCache.loginPurchase.getSubUserId();
            } else if (PublicCache.loginSupplier != null) {
                personId = PublicCache.loginSupplier.getEntityId();
                userType = 1;
            }

            getRequestPresenter().showGoodsInformation(MyselfShopManagement.goodsInformation.getEntityId(), userType, personId, new ResultInfoCallback<GoodsInformation>(this) {
                @Override
                public void onSuccess(GoodsInformation event) {
//                    if (isClose) loadingDimss();
//                    if (!isClose) isClose = true;
                    JavaMethod.copyValue(MyselfShopManagement.goodsInformation, event);
                    specificationListAdapter.notifyDataSetChanged(event.getSpecs());
                    if (event.getSpecs().size() >= 1) {
                        specificationListAdapter.removeFooter(footerView);
                    } else specificationListAdapter.addFooterView(footerView);
                    compute();
                }

                @Override
                public void onFailed(int goodsInformationResultInfo, String msg) {
//                    if (isClose) loadingDimss();
//                    if (!isClose) isClose = true;
                }
            });
        }

//        else {
//            if (isClose) loadingDimss();
//            else isClose = true;
//        }

    }


    //获取基本单位
    private void getData() {
        getRequestPresenter().getUnitsList(new RequestCallback<GetUnitList>(this) {
            @Override
            protected void onSuc(GetUnitList body) {
                level1Unit = body.getData().getLevel1Unit();
                baseUnit = body.getData().getBaseUnit();
//                if (isClose) loadingDimss();
//                if (!isClose) isClose = true;

            }

            @Override
            public void onFailed(int getUnitList, String msg) {
                UIUtils.showToastSafesShort(msg);
//                if (isClose) loadingDimss();
//                if (!isClose) isClose = true;
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_ok:

                if (specificationListAdapter != null && specificationListAdapter.getRealCount() >= 1) {
                    UIUtils.showToastSafesShort("只能添加一个规格");
                    return;
                }

                position_click = -1;
                Intent intent = new Intent(this, SpecificationAddActivity.class);
                intent.putExtra("isEdit", isUpdate);
                intent.putExtra("isP", isP);
                startActivityForResult(intent, 100);
                break;
            case R.id.yes:
                if (position_click > specificationListAdapter.getLastPosition() || position_click < specificationListAdapter.getFirstPosition()) {
                    return;
                }
                GoodsSpecification itBean = (GoodsSpecification) specificationListAdapter.getListBean(position_click);
                if (!isUpdate) {
                    if (MyselfShopManagement.goodsInformation.getSpecs() == null) return;
                    MyselfShopManagement.goodsInformation.getSpecs().remove(position_click);
                    specificationListAdapter.remove(position_click);
                    if (alertDialog != null && alertDialog.isShowing()) alertDialog.dismiss();
                    compute();
                    if (specificationListAdapter.getRealCount() < 1)
                        specificationListAdapter.addFooterView(footerView);
                    return;
                }

                loading();
                Log.e("delSpecId", itBean.getSpecId() + "");
                getRequestPresenter().delete(itBean.getProductId(), itBean.getSpecId(), new ResultInfoCallback() {
                    @Override
                    public void onSuccess(Object body) {
                   /*     if (MyselfShopManagement.goodsInformation.getSpecs() == null) return;
                        if (position_click >= MyselfShopManagement.goodsInformation.getSpecs().size())
                            return;
                        MyselfShopManagement.goodsInformation.getSpecs().remove(position_click);
                        specificationListAdapter.remove(position_click);
                        if (alertDialog != null && alertDialog.isShowing()) alertDialog.dismiss();
                        compute();*/
                        if (alertDialog != null && alertDialog.isShowing()) alertDialog.dismiss();
                        loadingDimss();
                        refreshData();
                    }

                    @Override
                    public void onFailed(int o, String msg) {
                        loadingDimss();
                        UIUtils.showToastSafesShort(msg);
                        if (alertDialog != null && alertDialog.isShowing()) alertDialog.dismiss();
                    }
                });
                break;
            case R.id.no:
                if (alertDialog != null && alertDialog.isShowing()) alertDialog.dismiss();
                break;

            case R.id.tv_detailed:
                startActivity(new Intent(this, DetailedActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {

            GoodsSpecification specification = (GoodsSpecification) data.getSerializableExtra("data");
            if (specification != null) {
                List<GoodsSpecification> list = MyselfShopManagement.goodsInformation.getSpecs();
                if (list == null) {
                    list = new ArrayList<>();
                    MyselfShopManagement.goodsInformation.setSpecs(list);
                }

                if (specification.getSpecId() != 0) {
    /*                if (position_click == -1) {
                        list.add(specification);
                        specificationListAdapter.loadMore(specification);
                    } else {
                        list.clear();
                        list.add(specification);
                        specificationListAdapter.update(position_click, specification);
                    }*/

                    if (isUpdate) {
                        refreshData();
                    } else {
                        list.set(position_click, specification);
                        specificationListAdapter.update(position_click, specification);
                        compute();
                    }
                } else {
                    specification.setSpecId(getSpecId());
                    list.add(specification);
                    specificationListAdapter.loadMore(specification);
                    compute();

                    if (specificationListAdapter.getRealCount() >= 1) {
                        specificationListAdapter.removeFooter(footerView);
                    }
                }

            }
        }
    }

    private int getSpecId() {
        List<GoodsSpecification> list = MyselfShopManagement.goodsInformation.getSpecs();
        int id = 0;
        if (list == null || list.isEmpty()) return -1000000;
        else {
            for (GoodsSpecification goodsSpecification : list) {
                if (id == 0) id = goodsSpecification.getSpecId();
                else {
                    if (id < goodsSpecification.getSpecId()) {
                        id = goodsSpecification.getSpecId();
                    }
                }
            }
        }
        return ++id;
    }


    private void compute() {
        if (MyselfShopManagement.goodsInformation == null) return;
        List<GoodsSpecification> list = MyselfShopManagement.goodsInformation.getSpecs();
        if (list == null || list.isEmpty()) return;
        GoodsSpecification bean = list.get(0);

        MyselfShopManagement.goodsInformation.setMinPrice(bean.getAvgPrice());
        MyselfShopManagement.goodsInformation.setUnit(bean.getAvgUnit());
        MyselfShopManagement.goodsInformation.setStock(bean.getStock());


        if (list.size() == 1) {
            MyselfShopManagement.goodsInformation.setMaxPrice(BigDecimal.valueOf(-1));
            return;
        } else {
            MyselfShopManagement.goodsInformation.setUnit(bean.getAvgUnit());
            MyselfShopManagement.goodsInformation.setMaxPrice(bean.getAvgPrice());
        }

        for (GoodsSpecification goodsSpecification : list) {
            if (MyselfShopManagement.goodsInformation.getMinPrice().compareTo(goodsSpecification.getAvgPrice()) > 0) {
                MyselfShopManagement.goodsInformation.setMinPrice(goodsSpecification.getAvgPrice());
            }

            if (MyselfShopManagement.goodsInformation.getMaxPrice().compareTo(goodsSpecification.getAvgPrice()) < 0) {
                MyselfShopManagement.goodsInformation.setMaxPrice(goodsSpecification.getAvgPrice());
            }
            if (MyselfShopManagement.goodsInformation.getStock() < goodsSpecification.getStock()) {
                MyselfShopManagement.goodsInformation.setStock(goodsSpecification.getStock());
            }


        }
    }


    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object bean) {
        if (onclickType == 0) {
            switch (view.getId()) {
                case R.id.bt_delete:
                    position_click = position;
                    if (alertDialog != null && !alertDialog.isShowing()) alertDialog.show();
                    break;

                case R.id.bt_update:
                    position_click = position;
                    Intent intent = new Intent(this, SpecificationAddActivity.class);
                    intent.putExtra("isEdit", isUpdate);

                    //只有一个的时侯是替换,且是审核驳回
                    if (specificationListAdapter.getRealCount() == 1 && isUpdate && goodsNameEdit)
                        intent.putExtra("replace", true);

                    intent.putExtra("GoodsSpecification", (GoodsSpecification) bean);
                    startActivityForResult(intent, 100);
                    break;


            }
            return true;
        }
        return false;
    }
}
