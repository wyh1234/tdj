package cn.com.taodaji.ui.activity.shopManagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.retrofit.RequestCallback;
import com.base.utils.DialogUtils;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.AddCategory;
import cn.com.taodaji.model.entity.CurrentStoreCategory;
import cn.com.taodaji.model.entity.DeleteCommodity;
import cn.com.taodaji.model.entity.ProblemItem;
import cn.com.taodaji.model.entity.ReceiveFee;
import cn.com.taodaji.model.event.CategoryEvent;
import cn.com.taodaji.model.event.ShopTypeSearchListEvent;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.ui.activity.myself.PickupFeeActivity;
import cn.com.taodaji.ui.activity.myself.SupplyAskNewSaleTypeActivity;
import cn.com.taodaji.ui.fragment.CartFragment;
import cn.com.taodaji.viewModel.adapter.MainCategoryAdapter;
import cn.com.taodaji.viewModel.adapter.SaleCategoryAdapter;
import cn.com.taodaji.viewModel.adapter.VerifyCategoryAdapter;
import tools.activity.SimpleToolbarActivity;

public class SaleCategoryManagementActivity extends SimpleToolbarActivity implements View.OnClickListener {

    private RecyclerView recyclerCurrent,recyclerReview,recyclerRefuse,recyclerView;
    private SaleCategoryAdapter currentAdapter,reviewAdapter,refuseAdapter;
    private MainCategoryAdapter adapter;
    private LinearLayout llview;
    private List<ProblemItem> currentList = new ArrayList<>();
    private List<ProblemItem> reviewList = new ArrayList<>();
    private List<ProblemItem> refuseList = new ArrayList<>();
    private List<ProblemItem> itemList = new ArrayList<>();
    private TextView saleType;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("出售类别管理");
    }

    @Override
    protected void initMainView() {
        View view = ViewUtils.getLayoutView(this,R.layout.activity_sale_category_management);
        body_scroll.addView(view);

        saleType = view.findViewById(R.id.tv_sale_type);

        llview = view.findViewById(R.id.ll_view);

        recyclerView = view.findViewById(R.id.rv_main_category);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        adapter = new MainCategoryAdapter(itemList,this);
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                RequestPresenter.getInstance().deleteCommdityApply(1, itemList.get(position).getId(), new RequestCallback<DeleteCommodity>() {
                    @Override
                    protected void onSuc(DeleteCommodity body) {
                        DialogUtils.getInstance(SaleCategoryManagementActivity.this).getSimpleDialog("当前该分类下：出售商品"+body.getData().getCszNum()+"个，下架商品"+body.getData().getYxjNum()+"个，删除后该商品将全部被驳回。",
                                itemList.get(position).getNickname()+">"+itemList.get(position).getText()).setPositiveButton("确定删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getRequestPresenter().cancelCommodityApply(2, itemList.get(position).getId(), new RequestCallback<AddCategory>() {
                                    @Override
                                    protected void onSuc(AddCategory body) {
                                        initUI();
                                    }
                                });
                            }
                        },R.color.red_dark).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        },R.color.gray_6a).show();
                    }

                    @Override
                    public void onFailed(int errCode, String msg) {
                        UIUtils.showToastSafe(msg);
                    }
                });
            }
        });
        recyclerView.setAdapter(adapter);

        recyclerCurrent = view.findViewById(R.id.rv_current_sale);
        recyclerCurrent.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        currentAdapter = new SaleCategoryAdapter(currentList,this);
        currentAdapter.setType(1);
        recyclerCurrent.setAdapter(currentAdapter);

        recyclerReview = view.findViewById(R.id.rv_review_sale);
        recyclerReview.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        reviewAdapter = new SaleCategoryAdapter(reviewList,this);
        reviewAdapter.setType(0);
        recyclerReview.setAdapter(reviewAdapter);

        recyclerRefuse = view.findViewById(R.id.rv_refuse_sale);
        recyclerRefuse.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        refuseAdapter = new SaleCategoryAdapter(refuseList,this);
        refuseAdapter.setType(2);
        recyclerRefuse.setAdapter(refuseAdapter);
    }

    public void initUI(){
        reviewList.clear();
        currentList.clear();
        refuseList.clear();
        itemList.clear();
        RequestPresenter.getInstance().getCurrentStoreCategoryList(PublicCache.loginSupplier.getStore(), -1, new RequestCallback<CurrentStoreCategory>() {
            @Override
            protected void onSuc(CurrentStoreCategory body) {
                for (CurrentStoreCategory.DataBean.StoreCategoryListBean bean : body.getData().getStoreCategoryList()){
                    ProblemItem item = new ProblemItem();
                    switch (bean.getCheckStatus()){
                        case 0://待审核
                            item.setText(bean.getCategoryName());
                            reviewList.add(item);
                            break;
                        case 1://审核通过（可出售）
                            item.setText(bean.getCategoryName());
                            currentList.add(item);
                            break;
                        case 2://审核驳回
                            item.setText(bean.getCategoryName());
                            refuseList.add(item);
                            break;
                            default:break;
                    }
                }

                if (body.getData().getSupplierSaleType()==1){
                    saleType.setText("零售");
                }else if (body.getData().getSupplierSaleType()==2){
                    saleType.setText("整件批");
                }else {
                    llview.setVisibility(View.GONE);
                }

                for (CurrentStoreCategory.DataBean.StoreCommodityListBean bean :body.getData().getStoreCommodityList()){
                    ProblemItem item = new ProblemItem();
                    item.setText(bean.getCategoryName());
                    item.setNickname(bean.getCommodityName());
                    item.setId(bean.getEntityId());
                    itemList.add(item);
                }

                adapter.notifyDataSetChanged();
                refuseAdapter.notifyDataSetChanged();
                reviewAdapter.notifyDataSetChanged();
                currentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafesShort(msg);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_apply_new_type:
                Intent intent1 = new Intent(SaleCategoryManagementActivity.this, SupplyAskNewSaleTypeActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_apply_new_category:
                Intent intent2 = new Intent(SaleCategoryManagementActivity.this,VegetablesCategoryActivity.class);
                intent2.putExtra("saleType",true);
                startActivity(intent2);
                break;
            case R.id.tv_review_category:
                Intent intent = new Intent(SaleCategoryManagementActivity.this,VerifyMainCategoryActivity.class);
                startActivity(intent);
                break;
                default:break;
        }
    }

    @Subscribe
    public void onEvent(CategoryEvent event) {
        RequestPresenter.getInstance().saveAddApply(PublicCache.loginSupplier.getStore(), PublicCache.loginSupplier.getRealname(), PublicCache.loginSupplier.getPhoneNumber(), event.getSubCategoryJson(), new RequestCallback<AddCategory>() {
            @Override
            protected void onSuc(AddCategory body) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
        initUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }
}
