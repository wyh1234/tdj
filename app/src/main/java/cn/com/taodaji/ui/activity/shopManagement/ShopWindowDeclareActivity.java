package cn.com.taodaji.ui.activity.shopManagement;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.base.retrofit.RequestCallback;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.StoreLimit;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.viewModel.adapter.SimpleShopWindowDeclareAdapter;
import tools.activity.SimpleToolbarActivity;

/**
 * Created by yangkuo on 2018/11/23.
 */
public class ShopWindowDeclareActivity extends SimpleToolbarActivity {

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setToolBarColor();
        setStatusBarColor();
        setTitle("橱窗说明");
    }

    private SimpleShopWindowDeclareAdapter shopWindowDeclareAdapter;

    private TextView tv_description;

    @Override
    protected void initMainView() {
        View mainView = getLayoutView(R.layout.activity_shop_window_declare);
        body_other.addView(mainView);

        RecyclerView rv_recyclerView = mainView.findViewById(R.id.rv_recyclerView);
        rv_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        shopWindowDeclareAdapter = new SimpleShopWindowDeclareAdapter();
        rv_recyclerView.setAdapter(shopWindowDeclareAdapter);

        tv_description = mainView.findViewById(R.id.tv_description);

    }

    @Override
    protected void initData() {
        super.initData();
        onStartLoading();
        if (PublicCache.loginSupplier != null)
            addRequest(ModelRequest.getInstance().store_limit(PublicCache.site_login, PublicCache.loginSupplier.getStore()), new RequestCallback<StoreLimit>(this) {
                @Override
                protected void onSuc(StoreLimit body) {

                    if (body.getData().getList() != null) {
                        for (int size = body.getData().getList().size() - 1; size >= 0; size--) {
                            if (body.getData().getList().get(size).getIs_active() == 2)
                                body.getData().getList().remove(size);
                        }
                    }
                    if (shopWindowDeclareAdapter != null) {
                        shopWindowDeclareAdapter.setList(body.getData().getList());
                    }
                    if (tv_description != null) {
                        if (body.getData().getCurrentList() == null) {
                            tv_description.setText("当前贵店无可出售商品分类");
                        } else {
                            tv_description.setText("当前贵店最多可审核通过商品" + body.getData().getCurrentList().getTotal_number() + "个,出售商品" + body.getData().getCurrentList().getSales_number() + "个");
                        }
                    }

                }
            });

    }
}
