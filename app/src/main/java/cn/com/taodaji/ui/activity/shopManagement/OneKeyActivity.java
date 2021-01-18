package cn.com.taodaji.ui.activity.shopManagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.base.listener.UploadPicturesDoneListener;
import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.AddCategory;
import cn.com.taodaji.model.entity.GoodsInformation;
import cn.com.taodaji.model.entity.ShopDetail_Goods;
import cn.com.taodaji.model.event.PrintStatus;
import cn.com.taodaji.model.event.SearchGoodsEvent;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.ui.fragment.TodayDeliverGoodsOrderFragment;
import cn.com.taodaji.viewModel.adapter.SearchGoodsAdapter;
import cn.com.taodaji.viewModel.adapter.TodayDeliverGoodsOrderAdapter;
import tools.activity.SimpleToolbarActivity;
import tools.fragment.AddedPicturesFragment;

public class OneKeyActivity extends SimpleToolbarActivity implements UploadPicturesDoneListener {
    private View mainView;
    private AddedPicturesFragment addedPicturesFragment;
    private List<GoodsInformation> list = new ArrayList<>();
    private SearchGoodsAdapter adapter;
    private RecyclerView recyclerView;
    private Button confirm;
    private LinearLayout ll;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("批量上传资质");
    }

    @Override
    protected void initMainView() {
        mainView = ViewUtils.getLayoutView(this,R.layout.activity_one_key);
        body_other.addView(mainView);

        addedPicturesFragment = (AddedPicturesFragment) getSupportFragmentManager().findFragmentById(R.id.addedPicturesFragment);
        addedPicturesFragment.setMaxSelect(99);

        recyclerView = mainView.findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchGoodsAdapter(list,this);
        adapter.setStatus(1);
        recyclerView.setAdapter(adapter);
        ll = mainView.findViewById(R.id.ll_order_title);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OneKeyActivity.this,OneKeySearchActivity.class);
                startActivity(intent);
            }
        });

        confirm = mainView.findViewById(R.id.btn_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmData();
            }
        });

    }

    public void confirmData(){
        if (addedPicturesFragment.getImageStr()==null){
            UIUtils.showToastSafe("请添加图片");
            return;
        }
        if (list.size()==0){
            UIUtils.showToastSafe("请添加商品");
            return;
        }

        StringBuffer sb = new StringBuffer();
        for (GoodsInformation information : list){
            sb.append(information.getEntityId()+",");
        }

        RequestPresenter.getInstance().batchUpdCredentialImgs(PublicCache.loginSupplier.getStore(), addedPicturesFragment.getImageStr(), sb.substring(0, sb.length() - 1),"", new RequestCallback<AddCategory>() {
            @Override
            protected void onSuc(AddCategory body) {
                UIUtils.showToastSafe("上传成功");
                finish();
            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafe(msg);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SearchGoodsEvent event){
        list.clear();
        for (GoodsInformation information : event.getList()){
            if (!list.contains(information)){
                list.add(information);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void uploadPicturesIsDone(Object obj) {
        if (isDestroyed()) return;
    }
}
