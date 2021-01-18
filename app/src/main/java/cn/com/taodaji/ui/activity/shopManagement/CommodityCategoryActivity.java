package cn.com.taodaji.ui.activity.shopManagement;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.adapter.splite_line.DividerGridItemDecoration;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;

import java.util.HashMap;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.GoodsCategoryList;
import cn.com.taodaji.model.entity.GoodsCategoryList_Resu;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.viewModel.vm.CommodityCategoryViewModel;
import tools.activity.SimpleToolbarActivity;

public class CommodityCategoryActivity extends SimpleToolbarActivity implements View.OnClickListener {

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("商品分类");
        right_icon.setImageResource(R.mipmap.search_big);
        right_icon.setVisibility(View.VISIBLE);

    }

    private View mainView;
    private SingleRecyclerViewAdapter customerSimpleRecyclerViewAdapter_left;
    private SingleRecyclerViewAdapter customerSimpleRecyclerViewAdapter_right;
    private boolean isCallback = false;
    private int parentCategoryId;
    private String parentCategoryName;
    private LinearLayout line_tips_search_goods;
    private TextView tips_txt;
    private  RecyclerView recyclerView_left;
    private  RecyclerView recyclerView_right;
    private int isDrainageArea;

    @Override
    protected void initMainView() {
        right_icon.setOnClickListener(this);
        mainView = getLayoutView(R.layout.activity_shop_management_commodity_category);
        line_tips_search_goods = ViewUtils.findViewById(mainView, R.id.line_tips_search_goods);
        tips_txt = ViewUtils.findViewById(mainView, R.id.tips_txt);
        body_other.addView(mainView);
         recyclerView_left = ViewUtils.findViewById(mainView, R.id.recyclerView_left);
        recyclerView_left.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_left.addItemDecoration(new DividerItemDecoration(this));

        customerSimpleRecyclerViewAdapter_left = new SingleRecyclerViewAdapter() {
            @Override
            public void initBaseVM() {
                putBaseVM(TYPE_CHILD, new CommodityCategoryViewModel());
            }

            @Override
            public View onCreateHolderView(ViewGroup parent, int viewType) {
                return ViewUtils.getFragmentView(parent, R.layout.item_shop_management_commodity_category_left);
            }

            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                if (onclickType == 2) {
                    GoodsCategoryList gbean = (GoodsCategoryList) bean;
                    if (!gbean.isSelected()) {
                        parentCategoryId = gbean.getCategoryId();
                        parentCategoryName = gbean.getCategoryName();
                        setSelected(position);
                        customerSimpleRecyclerViewAdapter_right.setList(gbean.getChildren());
                    }
                    return true;
                } else return super.onItemClick(view, onclickType, position, bean);
            }
        };
        customerSimpleRecyclerViewAdapter_left.setRadio(true);
        recyclerView_left.setAdapter(customerSimpleRecyclerViewAdapter_left);

        recyclerView_right = ViewUtils.findViewById(mainView, R.id.recyclerView_right);
        recyclerView_right.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView_right.addItemDecoration(new DividerGridItemDecoration(UIUtils.dip2px(5), R.color.gray_f2));

        customerSimpleRecyclerViewAdapter_right = new SingleRecyclerViewAdapter() {
            @Override
            public void initBaseVM() {
                putBaseVM(TYPE_CHILD, new CommodityCategoryViewModel());
            }

            @Override
            public View onCreateHolderView(ViewGroup parent, int viewType) {
                return ViewUtils.getFragmentView(parent, R.layout.item_shop_management_commodity_category_right);
            }

            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                if (onclickType == 0) {
                    if (getIntent().getIntExtra("GoodsNeed",0)==1){
                        Intent mIntent = new Intent();
                        mIntent.putExtra("parentCategoryId",parentCategoryId );
                        GoodsCategoryList.ChildrenBean childrenBean = (GoodsCategoryList.ChildrenBean) bean;
                        mIntent.putExtra("categoryId",childrenBean.getCategoryId());
                        mIntent.putExtra("categoryName",childrenBean.getCategoryName());
                        CommodityCategoryActivity.this.setResult(RESULT_OK, mIntent);
                        CommodityCategoryActivity.this.finish();
                        return true;
                    }
                    Intent intent = new Intent(getBaseActivity(), CommodityCategoryNextActivity.class);
                    intent.putExtra("isCallback", isCallback);
                    intent.putExtra("parentCategoryId", parentCategoryId);
                    intent.putExtra("parentCategoryName", parentCategoryName);
                    intent.putExtra("data", (GoodsCategoryList.ChildrenBean) bean);
                    intent.putExtra("isForceTemplate",  ((GoodsCategoryList.ChildrenBean) bean).getIsForceTemplate());

                    intent.putExtra("isDrainageArea",  ((GoodsCategoryList.ChildrenBean) bean).getIsDrainageArea());
                    if (isCallback) startActivityForResult(intent, 100);
                    else startActivity(intent);
                    return true;
                } else return super.onItemClick(view, onclickType, position, bean);
            }
        };

        recyclerView_right.setAdapter(customerSimpleRecyclerViewAdapter_right);
    }


    @Override
    protected void initData() {
        onStartLoading();

        isCallback = getIntent().getBooleanExtra("isCallback", false);
//        loading();

        Map<String, String> map = new HashMap();
        map.put("site",PublicCache.site_login +"");
        map.put("storeId",PublicCache.loginSupplier!=null?PublicCache.loginSupplier.getStore()+"": "0");
        addRequest(ModelRequest.getInstance().findStoreCategoryList(map), new RequestCallback<GoodsCategoryList_Resu>(this) {
            @Override
            protected void onSuc(GoodsCategoryList_Resu body) {
                if (!body.getData().isEmpty()) {
                    recyclerView_right.setVisibility(View.VISIBLE);
                    recyclerView_left.setVisibility(View.VISIBLE);
                    line_tips_search_goods.setVisibility(View.GONE);
                    customerSimpleRecyclerViewAdapter_left.setList(body.getData());
                    customerSimpleRecyclerViewAdapter_right.setList(body.getData().get(0).getChildren());
                    parentCategoryId = body.getData().get(0).getCategoryId();
                    parentCategoryName = body.getData().get(0).getCategoryName();
                }else{

                    recyclerView_right.setVisibility(View.GONE);
                    recyclerView_left.setVisibility(View.GONE);
                    tips_txt.setText("还没有可以添加的出售分类");
                    line_tips_search_goods.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailed(int errCode, String msg) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //放大镜按钮
            case R.id.right_icon:
                Intent intent = new Intent(this, GoodsSearchReleaseActivity.class);
                startActivity(intent);
                break;
        }
    }


}
