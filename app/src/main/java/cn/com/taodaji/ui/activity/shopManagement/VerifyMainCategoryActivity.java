package cn.com.taodaji.ui.activity.shopManagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.AddCategory;
import cn.com.taodaji.model.entity.CurrentStoreCategory;
import cn.com.taodaji.model.entity.ProblemItem;
import cn.com.taodaji.model.entity.StoreCategoryCommodity;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.viewModel.adapter.VerifyCategoryAdapter;
import tools.activity.SimpleToolbarActivity;

public class VerifyMainCategoryActivity extends SimpleToolbarActivity {

    private RecyclerView recyclerView;
    private VerifyCategoryAdapter adapter;
    private List<ProblemItem> itemList = new ArrayList<>();

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("审核中主营分类");
    }

    @Override
    protected void initMainView() {
        View mainView = ViewUtils.getLayoutViewMatch(this,R.layout.activity_verify_main_category);
        body_other.addView(mainView);
        recyclerView = mainView.findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VerifyCategoryAdapter(itemList,this);
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                loading("请稍等");
                if (itemList.size()==0)return;
                getRequestPresenter().cancelCommodityApply(3, itemList.get(position).getId(), new RequestCallback<AddCategory>() {
                    @Override
                    protected void onSuc(AddCategory body) {
                        initData();
                    }

                    @Override
                    public void onFailed(int errCode, String msg) {
                        loadingDimss();UIUtils.showToastSafe(msg);
                    }
                });
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        itemList.clear();
        RequestPresenter.getInstance().getStoreCategoryCommodityList(PublicCache.loginSupplier.getStore(), 0, new RequestCallback<StoreCategoryCommodity>() {
            @Override
            protected void onSuc(StoreCategoryCommodity body) {
                for (StoreCategoryCommodity.DataBean.ListBean bean : body.getData().getList()){
                    ProblemItem item = new ProblemItem();
                    item.setText(bean.getCategoryName());
                    item.setNickname(bean.getCommodityName());
                    item.setNum(millisecondToDate(bean.getCreateTime()));
                    item.setId(bean.getEntityId());
                    itemList.add(item);
                }
                adapter.notifyDataSetChanged();
                loadingDimss();
            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafesShort(msg);
            }
        });
    }

    public String millisecondToDate(long millisecond){
        Date date = new Date(millisecond);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
        return format.format(date);
    }
}
