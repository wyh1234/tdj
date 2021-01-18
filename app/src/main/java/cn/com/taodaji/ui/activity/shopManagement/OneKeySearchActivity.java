package cn.com.taodaji.ui.activity.shopManagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.entity.ResultInfo;
import com.base.retrofit.RequestCallback;
import com.base.swipetoloadlayout.SwipeToLoadLayout;
import com.base.swipetoloadlayout.listener.OnLoadMoreListener;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.GoodsInformation;
import cn.com.taodaji.model.entity.ShopDetail_Goods;
import cn.com.taodaji.model.event.SearchGoodsEvent;
import cn.com.taodaji.viewModel.adapter.SearchGoodsAdapter;
import tools.activity.SimpleToolbarActivity;

public class OneKeySearchActivity extends SimpleToolbarActivity {

    private View mainView;
    private List<GoodsInformation> goodsList = new ArrayList<>();
    private List<GoodsInformation> confirmList = new ArrayList<>();
    private SearchGoodsAdapter adapter;
    private SwipeToLoadLayout swipe;
    private int pageNo =1;
    private String name="";
    private RecyclerView recyclerView;
    private TextView confirm,search,orderCount;
    private EditText addressSearch;
    private ImageView clearKeyword;
    private CheckBox selectAll;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("批量上传资质");
    }

    @Override
    protected void initMainView() {
        mainView = ViewUtils.getLayoutView(this, R.layout.activity_one_key_search);
        body_other.addView(mainView);

        recyclerView = mainView.findViewById(R.id.swipe_target);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchGoodsAdapter(goodsList,OneKeySearchActivity.this);
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (goodsList.get(position).getIsF()==1){
                    goodsList.get(position).setIsF(0);
                }else {
                    goodsList.get(position).setIsF(1);
                }
                totalOrderAndPrice();
            }
        });
        recyclerView.setAdapter(adapter);

        swipe = mainView.findViewById(R.id.swipeToLoadLayout);
        swipe.setLoadingMore(true);
        swipe.setRefreshEnabled(false);
        swipe.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                initData(pageNo);
                pageNo +=1;
            }
        });


        addressSearch = ViewUtils.findViewById(mainView,R.id.et_shop_address);
        clearKeyword = ViewUtils.findViewById(mainView,R.id.iv_clear_keyword);

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
                name = addressSearch.getText().toString().trim();
                pageNo=1;
                initData(pageNo);
            }
        });

        orderCount =ViewUtils.findViewById(mainView,R.id.tv_total_order) ;
        confirm = ViewUtils.findViewById(mainView, R.id.tv_print_all);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchGoodsEvent event = new SearchGoodsEvent();
                event.setList(confirmList);
                EventBus.getDefault().post(event);
                finish();
            }
        });
        search = ViewUtils.findViewById(mainView,R.id.tv_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = addressSearch.getText().toString().trim();
                pageNo=1;
                initData(pageNo);
            }
        });
        selectAll = ViewUtils.findViewById(mainView,R.id.cb_select_all);
        selectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    selectAllStatus(1);
                }else {
                    selectAllStatus(0);
                }
            }
        });
    }


    protected void initData(int pageNo) {
        getRequestPresenter().searchProducts(PublicCache.loginSupplier.getStore(), name, pageNo, 10, new RequestCallback<ResultInfo<ShopDetail_Goods>>() {
            @Override
            protected void onSuc(ResultInfo<ShopDetail_Goods> body) {
                if (swipe.isLoadingMore())swipe.setLoadingMore(false);
                goodsList.addAll(body.getData().getItems());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int errCode, String msg) {
                if (swipe.isLoadingMore())swipe.setLoadingMore(false);
                UIUtils.showToastSafe(msg);
            }
        });
    }

    public void selectAllStatus(int flag){
        for (int i=0;i<goodsList.size();i++){
            goodsList.get(i).setIsF(flag);
        }
        adapter.notifyDataSetChanged();
        totalOrderAndPrice();
    }

    public void totalOrderAndPrice(){
        int totalOrder = 0;
        for (int i=0;i<goodsList.size();i++){
            if (goodsList.get(i).getIsF()==1){
                confirmList.add(goodsList.get(i));
                totalOrder++;
            }
        }
        orderCount.setText("共"+totalOrder+"个");
    }
}
