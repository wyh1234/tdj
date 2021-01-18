package cn.com.taodaji.ui.activity.shopManagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;
import com.lljjcoder.style.citylist.Toast.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.AddCategory;
import cn.com.taodaji.model.entity.CommodityLimit;
import cn.com.taodaji.model.entity.GoodsCategorySelect;
import cn.com.taodaji.model.entity.GoodsLevel1;
import cn.com.taodaji.model.entity.GoodsLevel2;
import cn.com.taodaji.model.event.CategoryEvent;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.viewModel.adapter.CategoryLevel1Adapter;
import cn.com.taodaji.viewModel.adapter.CategoryLevel2Adapter;
import cn.com.taodaji.viewModel.adapter.VegetablesGoodsAdapter;
import tools.activity.SimpleToolbarActivity;

public class VegetablesCategoryActivity extends SimpleToolbarActivity implements View.OnClickListener {

    private RecyclerView level1,level2,selectGoods;
    private TextView total,confirm,viewGoods,limit,search;
    private RadioButton retail,whole;
    private RelativeLayout relativeLayout;
    private LinearLayout linearLayout;
    private CategoryLevel1Adapter level1Adapter;
    private CategoryLevel2Adapter level2Adapter;
    private VegetablesGoodsAdapter adapter;
    private List<GoodsLevel1> level1List = new ArrayList<>();
    private List<GoodsLevel2> selectedList = new ArrayList<>();
    private List<GoodsLevel2> level2List = new ArrayList<>();
    private int supplyType = 1,limitNum=5,flag;//供应商类型，1零售通货，2零售精品，3整件批
    private boolean status = true;
    private EditText addressSearch;
    private ImageView clearKeyword;
    private View mainView;


    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        if (getIntent().getBooleanExtra("saleType",false)) {
            simple_title.setText("添加主营类目");
        }else {
            simple_title.setText("请选择要出售的主营类目");
        }
    }

    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.activity_vegetables_category);
        body_other.addView(mainView);

        flag = getIntent().getIntExtra("flag",1);

        linearLayout = mainView.findViewById(R.id.ll_sale_type);
        if (getIntent().getBooleanExtra("saleType",false)){
            linearLayout.setVisibility(View.GONE);
        }

        initData("");

        limit = mainView.findViewById(R.id.tv_limit);
        total = mainView.findViewById(R.id.tv_total_select);
        search = mainView.findViewById(R.id.tv_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData(addressSearch.getText().toString().trim());
            }
        });
        confirm = mainView.findViewById(R.id.tv_confirm);
        confirm.setOnClickListener(this);

        retail = mainView.findViewById(R.id.rb_retail);
        retail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    supplyType=1;
                }
            }
        });

        addressSearch =(EditText)findViewById(R.id.et_shop_address);
        clearKeyword = (ImageView)findViewById(R.id.iv_clear_keyword);

        addressSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().equals("")){
                    clearKeyword.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().equals("")){
                    clearKeyword.setVisibility(View.INVISIBLE);
                }
            }
        });

        //清空搜索内容
        clearKeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addressSearch.setText("");
                clearKeyword.setVisibility(View.INVISIBLE);
            }
        });

        whole = (RadioButton) findViewById(R.id.rb_whole);
        whole.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    supplyType=3;
                }
            }
        });
        retail.setChecked(true);

        relativeLayout = (RelativeLayout) findViewById(R.id.rl_select_goods);
        viewGoods = (TextView) findViewById(R.id.tv_view_select_goods);
        viewGoods.setOnClickListener(this);
        selectGoods = (RecyclerView) findViewById(R.id.rv_select_goods);
        selectGoods.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
        adapter = new VegetablesGoodsAdapter(selectedList,this);
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                removeItem(selectedList.get(position).getPid(),selectedList.get(position).getId());
                selectedList.remove(position);
                total.setText("共"+selectedList.size()+"个分类");
                adapter.notifyDataSetChanged();
                level2Adapter.notifyDataSetChanged();
            }
        });
        selectGoods.setAdapter(adapter);

        level1 = (RecyclerView) findViewById(R.id.rv_level1);
        level1.setLayoutManager(new LinearLayoutManager(this));
        level1Adapter = new CategoryLevel1Adapter(level1List,this);
        level1Adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                level2Adapter.setmItemList(level1List.get(position).getLevel2List());
                level2Adapter.notifyDataSetChanged();
                level1Adapter.setSelected(position);
            }
        });
        level1.setAdapter(level1Adapter);

        total = (TextView) findViewById(R.id.tv_total_select);
        confirm = (TextView) findViewById(R.id.tv_confirm);
    }

    public void initData(String keyword){
        level1List.clear();
        level2List.clear();
        RequestPresenter.getInstance().searchCategoryList(10, keyword, new RequestCallback<GoodsCategorySelect>() {
            @Override
            protected void onSuc(GoodsCategorySelect body) {
                if (body.getErr()==0){
                    GoodsCategorySelect.DataBean.ListBean listBean = body.getData().getList().get(0);
                    for (GoodsCategorySelect.DataBean.ListBean.ChildrenBeanX childrenBeanX : listBean.getChildren()){
                        GoodsLevel1 level1 = new GoodsLevel1();
                        level1.setId(childrenBeanX.getCategoryId());
                        level1.setName(childrenBeanX.getName());
                        for (GoodsCategorySelect.DataBean.ListBean.ChildrenBeanX.ChildrenBean childrenBean : childrenBeanX.getChildren()){
                            GoodsLevel2 level2 = new GoodsLevel2();
                            level2.setId(childrenBean.getCategoryId());
                            level2.setName(childrenBean.getName());
                            level2.setpName(childrenBean.getParentName());
                            level2.setPid(childrenBean.getParentId());
                            level2.setStatus(childrenBean.getStatus());
                            level1.getLevel2List().add(level2);
                            level2List.add(level2);
                        }
                        if (childrenBeanX.getChildren().size()!=0){
                            level1List.add(level1);
                        }
                    }
                    if (level1List.size()==0){
                        ToastUtils.showShortToast(VegetablesCategoryActivity.this,"暂无该分类");
                    }else {
                        initLevel2();
                        level1Adapter.notifyDataSetChanged();
                        level2Adapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafe(msg);
            }
        });

        RequestPresenter.getInstance().getCommodityLimit(10, PublicCache.loginSupplier.getStore(),flag, new RequestCallback<CommodityLimit>() {
            @Override
            protected void onSuc(CommodityLimit body) {
                if (body.getData().getStoreType() == 1) {
                    limit.setText("最多可选择" + body.getData().getFlagCommodityLimit() + "个主营分类");
                    limitNum = body.getData().getFlagCommodityLimit();
                } else {
                    limit.setText("最多可选择" + body.getData().getCommodityLimit() + "个主营分类（旗舰店" + body.getData().getFlagCommodityLimit() + "个)");
                    limitNum = body.getData().getCommodityLimit();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_confirm:
                confirmData();
                finish();
                break;
            case R.id.tv_view_select_goods:
                if (status){
                    relativeLayout.setVisibility(View.VISIBLE);
                    viewGoods.setText("，点击关闭");
                    status = false;
                }else {
                    relativeLayout.setVisibility(View.GONE);
                    viewGoods.setText("，点击查看");
                    status = true;
                }
                break;
                default:break;
        }
    }

    public void initLevel2(){
        level2 = (RecyclerView) findViewById(R.id.rv_level2);
        level2.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        level2Adapter = new CategoryLevel2Adapter(level1List.get(0).getLevel2List(),this);
        level2Adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (level2Adapter.getmItemList().get(position).getStatus()==-1){
                    UIUtils.showToastSafe("商户出售名额已满，无法选择。");
                    return;
                }
                if (selectedList.contains(level2Adapter.getmItemList().get(position))){
                    UIUtils.showToastSafe("无法重复添加");
                }else{
                    if (selectedList.size()>=limitNum){
                        UIUtils.showToastSafe("已经到最大上限");
                    }else {
                        level2Adapter.getmItemList().get(position).setSelected(true);
                        level2Adapter.notifyDataSetChanged();
                        selectedList.add(level2Adapter.getmItemList().get(position));
                        total.setText("共"+selectedList.size()+"个分类");
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        level2.setAdapter(level2Adapter);
    }

    public void removeItem(int pid,int id){
        for (GoodsLevel1 level1 : level1List){
            if (level1.getId()==pid){
                for (GoodsLevel2 level2 : level1.getLevel2List()){
                    if (level2.getId()==id){
                        level2.setSelected(false);
                    }
                }
            }
        }
    }

    public void confirmData(){
        if (selectedList.size()==0){
            UIUtils.showToastSafe("请选择分类");
            return;
        }
        Set<Integer> set = new HashSet<>();
        List<Map<String,Object>> list = new ArrayList<>();
        for (GoodsLevel2 bean : selectedList){
            if (set.add(bean.getPid())){
                Map<String,Object> map = new HashMap<>();
                map.put("categoryName",bean.getpName());
                map.put("categoryId",bean.getPid()+"");
                list.add(map);
            }
        }
        for (Map<String,Object> map : list){
            JSONArray jsonArray = new JSONArray();
            for (GoodsLevel2 bean : selectedList){
                if (Integer.parseInt(map.get("categoryId").toString())==bean.getPid()){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("commodityId",bean.getId()+"");
                    jsonObject.put("commodityName",bean.getName());
                    jsonArray.add(jsonObject);
                }
            }
            map.put("children",jsonArray);
        }
        JSONArray data = JSONArray.parseArray(JSON.toJSONString(list));
        CategoryEvent event = new CategoryEvent();
        event.setSubCategoryJson(data.toJSONString());
        event.setSupplierSaleType(supplyType);
        if (getIntent().getBooleanExtra("saleType",false)){
            EventBus.getDefault().post(event);
        }else {
            RequestPresenter.getInstance().setMainCategory(PublicCache.loginSupplier.getEntityId(), PublicCache.loginSupplier.getRealname(), PublicCache.loginSupplier.getPhoneNumber(), PublicCache.loginSupplier.getStore(), supplyType, data.toJSONString(), new RequestCallback<AddCategory>() {
                @Override
                protected void onSuc(AddCategory body) {
                    finish();
                }
            });
        }
    }
}
