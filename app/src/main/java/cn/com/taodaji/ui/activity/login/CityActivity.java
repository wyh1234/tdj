package cn.com.taodaji.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.base.retrofit.RequestCallback;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.BaseVM;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;
import com.base.retrofit.ResultInfoCallback;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.FindByIsActive;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.model.presenter.RequestPresenter;
import tools.activity.SimpleToolbarActivity;

/**
 * Created by yangkuo on 2018-05-04.
 */

public class CityActivity extends SimpleToolbarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("地区选择");
    }

    private SingleRecyclerViewAdapter recyclerViewAdapter;
    private FindByIsActive findByIsActive;

    @Override
    protected void initMainView() {
        RecyclerView recyclerView = getLayoutView(R.layout.t_recyclerview);
        recyclerView.setPadding(UIUtils.dip2px(10), UIUtils.dip2px(10), UIUtils.dip2px(10), UIUtils.dip2px(10));
        recyclerView.addItemDecoration(new DividerItemDecoration(1, R.color.gray_db));
        body_other.addView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new SingleRecyclerViewAdapter() {
            @Override
            public void initBaseVM() {
                putBaseVM(TYPE_CHILD, new BaseVM() {
                    @Override
                    protected void dataBinding() {
                        putRelation(R.id.place_text, "name");
                    }

                    @Override
                    protected void addOnclick() {
                        putViewOnClick(R.id.item_view);
                    }
                });
            }

            @Override
            public View onCreateHolderView(ViewGroup parent, int viewType) {
                return ViewUtils.getFragmentView(parent, R.layout.item_region_selection);
            }

            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                if (onclickType == 0) {
                    FindByIsActive.ListBean listBean = (FindByIsActive.ListBean) bean;
                    if (listBean == null) return true;
                    Intent intent = new Intent();
                    intent.putExtra("id", listBean.getId());
                    intent.putExtra("name", listBean.getName());
                    intent.putExtra("cityCode",listBean.getAdcode());
                    intent.putExtra("lat",listBean.getLat());
                    intent.putExtra("lon",listBean.getLon());
                    setResult(RESULT_OK, intent);
//                    EventBus.getDefault().post(new CityChangeEvent(listBean.getId(), listBean.getName()));
                    finish();


                    return true;
                }
                return super.onItemClick(view, onclickType, position, bean);
            }
        };

        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected void initData() {
        findByIsActive = (FindByIsActive) getIntent().getSerializableExtra("data");
        if (findByIsActive != null) {
            recyclerViewAdapter.notifyDataSetChanged(findByIsActive.getList());
        } else {
            setTitle("选择分站城市");
            if (PublicCache.findByIsActive != null) {
                recyclerViewAdapter.notifyDataSetChanged(PublicCache.findByIsActive.getList());
            } else getCitysite();
        }
    }

    //获取已开启城市信息
    public void getCitysite() {
        onStartLoading();
//        loading();
        RequestPresenter.getInstance().findByIsActive(3, new ResultInfoCallback<FindByIsActive>(this) {
            @Override
            public void onSuccess(FindByIsActive body) {
                PublicCache.findByIsActive = body;
                if (recyclerViewAdapter != null)
                    recyclerViewAdapter.notifyDataSetChanged(body.getList());
            }

            @Override
            public void onFailed(int findByIsActiveResultInfo, String msg) {
//                loadingDimss();
            }
        });
    }
}
